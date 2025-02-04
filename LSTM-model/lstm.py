import os

from pydantic import BaseModel
from fastapi import Body
from fastapi import FastAPI, HTTPException
import mysql.connector
import pandas as pd
import numpy as np
from keras.src.saving import load_model
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, LSTM
import math
from sklearn.metrics import mean_squared_error
import uvicorn

# FastAPI 应用
app = FastAPI()

# 定义 POST 请求的输入模型
class PredictRequest(BaseModel):
    index: str  # 动态列名参数


# 数据库配置
db_config = {
    'user': 'root',
    'password': 'root',
    'host': 'localhost',
    'database': 'lstm',
    'raise_on_warnings': True
}
# 模型保存路径
MODEL_PATH = "saved_lstm_model.h5"


def train_and_save_model(column_name: str):
    df = load_data()
    df1, scaler = preprocess_data(df, column_name)

    training_size = int(len(df1) * 0.65)
    train_data, test_data = df1[0:training_size, :], df1[training_size:len(df1), :1]

    time_step = 10
    X_train, y_train = create_dataset(train_data, time_step)
    X_test, y_test = create_dataset(test_data, time_step)

    X_train = X_train.reshape(X_train.shape[0], X_train.shape[1], 1)
    X_test = X_test.reshape(X_test.shape[0], X_test.shape[1], 1)

    model = Sequential()
    model.add(LSTM(50, return_sequences=True, input_shape=(10, 1)))
    model.add(LSTM(50, return_sequences=True))
    model.add(LSTM(50))
    model.add(Dense(1))
    model.compile(loss='mean_squared_error', optimizer='adam')
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=100, batch_size=64, verbose=1)

    model.save(MODEL_PATH)
    return model, scaler


def load_saved_model(column_name: str):
    if not os.path.exists(MODEL_PATH):
        model, scaler = train_and_save_model(column_name)
    else:
        model = load_model(MODEL_PATH)
        df = load_data()
        _, scaler = preprocess_data(df, column_name)
    return model, scaler

# 加载数据
def load_data():
    try:
        conn = mysql.connector.connect(**db_config)
        query = "SELECT * FROM well_info;"
        df = pd.read_sql(query, conn)
        conn.close()
        return df
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"数据库连接失败: {str(e)}")

# 数据预处理
def preprocess_data(df, column_name: str):
    # 验证列名是否存在
    if column_name not in df.columns:
        raise HTTPException(status_code=400, detail=f"列名 '{column_name}' 不存在")

    df1 = df.reset_index()[column_name]  # 动态选择列
    scaler = MinMaxScaler(feature_range=(0, 1))
    df1 = scaler.fit_transform(np.array(df1).reshape(-1, 1))
    return df1, scaler

# 创建数据集
def create_dataset(dataset, time_step=1):
    dataX, dataY = [], []
    for i in range(len(dataset) - time_step - 1):
        a = dataset[i:(i + time_step), 0]
        dataX.append(a)
        dataY.append(dataset[i + time_step, 0])
    return np.array(dataX), np.array(dataY)

# 训练模型
def train_model(X_train, y_train, X_test, y_test):
    model = Sequential()
    model.add(LSTM(50, return_sequences=True, input_shape=(10, 1)))
    model.add(LSTM(50, return_sequences=True))
    model.add(LSTM(50))
    model.add(Dense(1))
    model.compile(loss='mean_squared_error', optimizer='adam')
    model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=100, batch_size=64, verbose=1)
    return model

# 预测
def predict_future(model, scaler, test_data, days=3):
    temp_input = list(test_data[-10:].reshape(-1))
    lst_output = []
    n_steps = 10

    for i in range(days):
        if len(temp_input) > 10:
            x_input = np.array(temp_input[1:])
            x_input = x_input.reshape(1, -1)
            x_input = x_input.reshape((1, n_steps, 1))
            yhat = model.predict(x_input, verbose=0)
            temp_input.extend(yhat[0].tolist())
            temp_input = temp_input[1:]
            lst_output.extend(yhat.tolist())
        else:
            x_input = np.array(temp_input).reshape(1, n_steps, 1)
            yhat = model.predict(x_input, verbose=0)
            temp_input.extend(yhat[0].tolist())
            lst_output.extend(yhat.tolist())

    return scaler.inverse_transform(lst_output)

# API 路由
@app.post("/predict")
def predict(request: PredictRequest):
    try:
        column_name = request.index  # 获取动态列名
        model, scaler = load_saved_model(column_name)
        df = load_data()
        df1, _ = preprocess_data(df, column_name)

        training_size = int(len(df1) * 0.65)
        test_data = df1[training_size:len(df1), :1]

        predictions = predict_future(model, scaler, test_data, days=3)
        return {"predictions": predictions.tolist()}
    except HTTPException as he:
        raise he  # 传递预定义的 HTTPException
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"预测失败: {str(e)}")

# 启动服务
if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
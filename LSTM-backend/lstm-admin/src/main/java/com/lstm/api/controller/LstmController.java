package com.lstm.api.controller;

import com.lstm.api.domain.PredictionResponse;
import com.lstm.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@Api("Lstm")
@RestController
@RequestMapping("/api")
public class LstmController {
    private final RestTemplate restTemplate = new RestTemplate();

    // ---------------------- 定义请求体模型 ----------------------
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PredictRequest {
        private String index;  // 对应 Python API 的 index 参数
    }

    // ---------------------- 修改为 POST 请求 ----------------------
    @ApiOperation("获取预测结果")
    @PostMapping("/predict")  // 使用 @PostMapping
    public R<String> getPrediction(@RequestBody PredictRequest request) {  // 接收请求体
        // Python API 的 URL
        String pythonApiUrl = "http://localhost:8000/predict";

        // ---------------------- 发送 POST 请求 ----------------------
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体（JSON格式）
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("index", request.getIndex());  // 传递 index 参数

        // 发送请求
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject(pythonApiUrl, entity, String.class);

        return R.ok(response);
    }
}

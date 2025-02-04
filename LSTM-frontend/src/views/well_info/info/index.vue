<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属井" prop="wellId">
        <el-input
            v-model="queryParams.wellId"
            placeholder="请输入所属井"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="日期" prop="date">
        <el-input
            v-model="queryParams.date"
            placeholder="请输入日期"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['well_info:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['well_info:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['well_info:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['well_info:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="所属井" align="center" prop="wellId" />
      <el-table-column label="日期" align="center" prop="date" />
      <el-table-column label="含水率" align="center" prop="waterCutSc" />
      <el-table-column label="含油率" align="center" prop="oilCutSc" />
      <el-table-column label="累计产油量" align="center" prop="cumulativeOilSc" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['well_info:info:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['well_info:info:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改井详细信息管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="infoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属井" prop="wellId">
          <el-input v-model="form.wellId" placeholder="请输入所属井" />
        </el-form-item>
        <el-form-item label="日期" prop="date">
          <el-input v-model="form.date" placeholder="请输入日期" />
        </el-form-item>
        <el-form-item label="含水率" prop="waterCutSc">
          <el-input v-model="form.waterCutSc" placeholder="请输入含水率" />
        </el-form-item>
        <el-form-item label="含油率" prop="oilCutSc">
          <el-input v-model="form.oilCutSc" placeholder="请输入含油率" />
        </el-form-item>
        <el-form-item label="累计产油量" prop="cumulativeOilSc">
          <el-input v-model="form.cumulativeOilSc" placeholder="请输入累计产油量" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Info">
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/well_info/info";

const { proxy } = getCurrentInstance();

const infoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    wellId: null,
    date: null,
  },
  rules: {
    wellId: [
      { required: true, message: "所属井不能为空", trigger: "blur" }
    ],
    date: [
      { required: true, message: "日期不能为空", trigger: "blur" }
    ],
    waterCutSc: [
      { required: true, message: "含水率不能为空", trigger: "blur" }
    ],
    oilCutSc: [
      { required: true, message: "含油率不能为空", trigger: "blur" }
    ],
    cumulativeOilSc: [
      { required: true, message: "累计产油量不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询井详细信息管理列表 */
function getList() {
  loading.value = true;
  listInfo(queryParams.value).then(response => {
    infoList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    wellId: null,
    date: null,
    waterCutSc: null,
    oilCutSc: null,
    cumulativeOilSc: null
  };
  proxy.resetForm("infoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加井详细信息管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改井详细信息管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["infoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除井详细信息管理编号为"' + _ids + '"的数据项？').then(function () {
    return delInfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('well_info/info/export', {
    ...queryParams.value
  }, `info_${new Date().getTime()}.xlsx`)
}

getList();
</script>

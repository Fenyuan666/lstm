<template>
  <div>
    <el-card>
      <ECharts :options="chartOptions" />
    </el-card>
  </div>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import ECharts from '@/components/ECharts.vue';

// 定义图表配置
const chartOptions = ref({
  title: {
    text: '数据统计',
  },
  tooltip: {},
  xAxis: {
    type: 'category',
    data: [],
  },
  yAxis: {
    type: 'value',
  },
  series: [
    {
      name: '用户数量',
      type: 'line',
      data: [],
    },
  ],
});


function getCookie(name) {
  // 将 Cookie 字符串拆分为键值对
  const cookieArray = document.cookie.split(';');

  // 遍历所有键值对
  for (let i = 0; i < cookieArray.length; i++) {
    const cookiePair = cookieArray[i].trim(); // 去除空格
    // 检查键名是否匹配
    if (cookiePair.startsWith(name + '=')) {
      // 返回键值
      return decodeURIComponent(cookiePair.substring(name.length + 1));
    }
  }

  // 如果未找到，返回 null
  return null;
}

// 获取数据的方法
const fetchData = async () => {
  try {
    const token = getCookie('Admin-Token');
    console.log("我是token",token);
    // 检查 Token 是否存在
    if (!token) {
      console.error('未找到 Token，请先登录');
      return;
    }
    const response = await axios.get('/dev-api/api/report/userStatistics', {
      headers: {
        Authorization: `Bearer ${token}`, // 将 Token 添加到请求头
      },
    });
    console.log("我是",response.data.data);
    const data = response.data.data;
    chartOptions.value.xAxis.data = data.map(item => item.date); // 更新 xAxis 数据
    chartOptions.value.series[0].data = data.map(item => item.cumulative_oil_sc); // 更新 series 数据
  } catch (error) {
    console.error('获取数据失败:', error);
  }
};

// 组件挂载时调用 fetchData
onMounted(() => {
  fetchData();
});
</script>



<style scoped lang="scss">

</style>
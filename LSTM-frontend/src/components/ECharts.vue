<template>

  <div ref="chartRef" :style="{ width: '100%', height: '400px' }"></div>
</template>



<script setup>
import { ref, onMounted, watch, onBeforeUnmount } from 'vue';
import * as echarts from 'echarts';

// 定义 props
const props = defineProps({
  options: {
    type: Object,
    required: true,
  },
});

// 引用图表 DOM 元素
const chartRef = ref(null);

// 图表实例
let chart = null;

// 组件挂载时初始化图表
onMounted(() => {
  chart = echarts.init(chartRef.value);
  chart.setOption(props.options);
});

// 监听 options 的变化，动态更新图表
watch(
    () => props.options,
    (newOptions) => {
      chart.setOption(newOptions);
    },
    { deep: true }
);

// 组件销毁前清理图表实例
onBeforeUnmount(() => {
  if (chart) {
    chart.dispose();
  }
});

</script>



<style scoped lang="scss">

</style>
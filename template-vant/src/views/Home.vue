<template>
  <div class="home">
    <van-nav-bar title="Vant 组件展示" fixed />
    
    <div class="content">
      <van-cell-group v-for="(group, index) in componentGroups" :key="index" :title="group.title">
        <van-cell
          v-for="item in group.items"
          :key="item.name"
          :title="item.title"
          is-link
          :to="'/component/' + item.name"
        />
      </van-cell-group>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const componentGroups = ref([
  {
    title: '基础组件',
    items: [
      { name: 'button', title: 'Button 按钮' },
      { name: 'cell', title: 'Cell 单元格' },
      { name: 'icon', title: 'Icon 图标' },
    ]
  },
  {
    title: '表单组件',
    items: [
      { name: 'field', title: 'Field 输入框' },
      { name: 'calendar', title: 'Calendar 日历' },
      { name: 'checkbox', title: 'Checkbox 复选框' },
      { name: 'radio', title: 'Radio 单选框' },
      { name: 'rate', title: 'Rate 评分' },
    ]
  },
  {
    title: '反馈组件',
    items: [
      { name: 'dialog', title: 'Dialog 弹出框' },
      { name: 'toast', title: 'Toast 轻提示' },
      { name: 'action-sheet', title: 'ActionSheet 动作面板' },
    ]
  }
])
</script>

<style scoped>
.home {
  padding-top: 46px;
  min-height: 100vh;
  background: #f7f8fa;
}

.content {
  padding: 12px;
}
</style> 
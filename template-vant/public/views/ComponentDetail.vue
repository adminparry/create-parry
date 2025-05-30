<template>
  <div class="component-detail">
    <van-nav-bar
      :title="currentComponent?.title || '组件详情'"
      left-arrow
      @click-left="$router.back()"
      fixed
    />
    
    <div class="content">
      <template v-if="$route.params.name === 'button'">
        <van-cell-group title="按钮类型">
          <div class="demo-button">
            <van-button type="primary">主要按钮</van-button>
            <van-button type="success">成功按钮</van-button>
            <van-button type="default">默认按钮</van-button>
            <van-button type="warning">警告按钮</van-button>
            <van-button type="danger">危险按钮</van-button>
          </div>
        </van-cell-group>
      </template>

      <template v-else-if="$route.params.name === 'cell'">
        <van-cell-group title="基础用法">
          <van-cell title="单元格" value="内容" />
          <van-cell title="单元格" value="内容" label="描述信息" />
        </van-cell-group>
      </template>

      <template v-else-if="$route.params.name === 'icon'">
        <van-cell-group title="基础图标">
          <div class="demo-icon">
            <van-icon name="chat-o" />
            <van-icon name="star-o" />
            <van-icon name="phone-o" />
            <van-icon name="setting-o" />
          </div>
        </van-cell-group>
      </template>

      <template v-else>
        <van-empty description="更多组件示例开发中..." />
      </template>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const componentList = [
  { name: 'button', title: 'Button 按钮' },
  { name: 'cell', title: 'Cell 单元格' },
  { name: 'icon', title: 'Icon 图标' },
  // ... 其他组件
]

const currentComponent = computed(() => {
  return componentList.find(item => item.name === route.params.name)
})
</script>

<style scoped>
.component-detail {
  padding-top: 46px;
  min-height: 100vh;
  background: #f7f8fa;
}

.content {
  padding: 12px;
}

.demo-button {
  padding: 16px;
  background: #fff;
  border-radius: 8px;
}

.demo-button .van-button {
  margin: 0 8px 8px 0;
}

.demo-icon {
  padding: 16px;
  background: #fff;
  border-radius: 8px;
  display: flex;
  gap: 16px;
}

.demo-icon .van-icon {
  font-size: 24px;
}
</style> 
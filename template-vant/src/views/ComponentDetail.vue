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

      <template v-else-if="$route.params.name === 'field'">
        <van-cell-group title="基础用法">
          <van-field v-model="fieldValue" label="文本" placeholder="请输入文本" />
          <van-field v-model="passwordValue" type="password" label="密码" placeholder="请输入密码" />
        </van-cell-group>
      </template>

      <template v-else-if="$route.params.name === 'calendar'">
        <van-cell title="选择单个日期" :value="calendarDate" @click="showCalendar = true" />
        <van-calendar v-model:show="showCalendar" @confirm="onConfirmCalendar" />
      </template>

      <template v-else-if="$route.params.name === 'checkbox'">
        <van-cell-group title="基础用法">
          <div class="demo-checkbox">
            <van-checkbox v-model="checked">复选框</van-checkbox>
          </div>
        </van-cell-group>
        <van-cell-group title="复选框组">
          <div class="demo-checkbox">
            <van-checkbox-group v-model="checkedFruits">
              <van-checkbox name="apple">苹果</van-checkbox>
              <van-checkbox name="banana">香蕉</van-checkbox>
              <van-checkbox name="orange">橙子</van-checkbox>
            </van-checkbox-group>
          </div>
        </van-cell-group>
      </template>

      <template v-else-if="$route.params.name === 'radio'">
        <van-cell-group title="基础用法">
          <div class="demo-radio">
            <van-radio-group v-model="radio">
              <van-radio name="1">单选框 1</van-radio>
              <van-radio name="2">单选框 2</van-radio>
            </van-radio-group>
          </div>
        </van-cell-group>
      </template>

      <template v-else-if="$route.params.name === 'rate'">
        <van-cell-group title="基础用法">
          <div class="demo-rate">
            <van-rate v-model="rateValue" />
          </div>
        </van-cell-group>
        <van-cell-group title="自定义颜色">
          <div class="demo-rate">
            <van-rate v-model="rateValue2" color="#ffd21e" void-icon="star" void-color="#eee" />
          </div>
        </van-cell-group>
      </template>

      <template v-else-if="$route.params.name === 'dialog'">
        <van-cell-group title="基础用法">
          <van-cell title="提示弹窗" @click="showDialog1" />
          <van-cell title="确认弹窗" @click="showConfirmDialog" />
        </van-cell-group>
      </template>

      <template v-else-if="$route.params.name === 'toast'">
        <van-cell-group title="基础用法">
          <van-cell title="文字提示" @click="showToast1" />
          <van-cell title="加载提示" @click="showLoadingToast" />
          <van-cell title="成功提示" @click="showSuccessToast" />
        </van-cell-group>
      </template>

      <template v-else-if="$route.params.name === 'action-sheet'">
        <van-cell-group title="基础用法">
          <van-cell title="显示操作表" @click="showActionSheet" />
        </van-cell-group>
      </template>

      <template v-else>
        <van-empty description="更多组件示例开发中..." />
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { showDialog, showToast, showNotify } from 'vant'

const route = useRoute()

// 字段演示数据
const fieldValue = ref('')
const passwordValue = ref('')

// 日历演示数据
const showCalendar = ref(false)
const calendarDate = ref('')
const onConfirmCalendar = (date) => {
  calendarDate.value = date.toLocaleDateString()
  showCalendar.value = false
}

// 复选框演示数据
const checked = ref(false)
const checkedFruits = ref(['apple'])

// 单选框演示数据
const radio = ref('1')

// 评分演示数据
const rateValue = ref(3)
const rateValue2 = ref(4)

// Dialog 演示方法
const showDialog1 = () => {
  showDialog({
    title: '标题',
    message: '这是一条消息提示',
  })
}

const showConfirmDialog = () => {
  showDialog({
    title: '标题',
    message: '这是一条确认消息',
    showCancelButton: true,
  })
}

// Toast 演示方法
const showToast1 = () => {
  showToast('提示内容')
}

const showLoadingToast = () => {
  showToast({
    message: '加载中...',
    type: 'loading',
  })
}

const showSuccessToast = () => {
  showToast({
    message: '成功提示',
    type: 'success',
  })
}

// ActionSheet 演示数据
const showActionSheet = () => {
  showDialog({
    title: '标题',
    message: '这是一个动作面板',
    actions: [
      { name: '选项一' },
      { name: '选项二' },
      { name: '选项三' },
    ],
  })
}

const componentList = [
  { name: 'button', title: 'Button 按钮' },
  { name: 'cell', title: 'Cell 单元格' },
  { name: 'icon', title: 'Icon 图标' },
  { name: 'field', title: 'Field 输入框' },
  { name: 'calendar', title: 'Calendar 日历' },
  { name: 'checkbox', title: 'Checkbox 复选框' },
  { name: 'radio', title: 'Radio 单选框' },
  { name: 'rate', title: 'Rate 评分' },
  { name: 'dialog', title: 'Dialog 弹出框' },
  { name: 'toast', title: 'Toast 轻提示' },
  { name: 'action-sheet', title: 'ActionSheet 动作面板' },
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

.demo-checkbox,
.demo-radio,
.demo-rate {
  padding: 16px;
  background: #fff;
  border-radius: 8px;
}

.demo-checkbox .van-checkbox,
.demo-radio .van-radio {
  margin-bottom: 8px;
}

.demo-rate .van-rate {
  margin: 8px 0;
}
</style> 
const app = getApp()

// 添加节流函数
function throttle(fn, delay) {
  let timer = null
  let lastTime = 0
  return function (...args) {
    const now = Date.now()
    if (now - lastTime >= delay) {
      fn.apply(this, args)
      lastTime = now
    } else if (!timer) {
      timer = setTimeout(() => {
        fn.apply(this, args)
        lastTime = Date.now()
        timer = null
      }, delay)
    }
  }
}

Page({
  data: {
    // 列表数据
    listData: [],
    isRefreshing: false,
    isLoadingMore: false,
    pageNum: 1,
    pageSize: 10,

    // 动画数据
    animationData: {},

    // 日历数据
    currentYear: new Date().getFullYear(),
    currentMonth: new Date().getMonth() + 1,
    weekdays: ['日', '一', '二', '三', '四', '五', '六'],
    days: [],
    events: {},

    // 图表数据
    chartLegend: [
      { name: '访问量', color: '#3498db' },
      { name: '销售额', color: '#2ecc71' }
    ],

    // 手势数据
    scale: 1,
    rotation: 0,
    translateX: 0,
    translateY: 0,
    lastTapTime: 0,

    // 虚拟滚动相关数据
    allListData: [], // 所有数据
    visibleData: [], // 可见数据
    startIndex: 0, // 当前可见区域的起始索引
    itemHeight: 120, // 每个列表项的高度(rpx)
    visibleItemCount: 10, // 可见区域能显示的列表项数量
    bufferSize: 5, // 上下缓冲区的数量
  },

  onLoad() {
    this.loadInitialData()
    this.initCalendar()
    this.initChart()
    this.animation = wx.createAnimation({
      duration: 1000,
      timingFunction: 'ease'
    })
    
    // 初始化节流后的滚动处理函数
    this.throttledScroll = throttle(this.handleScroll, 16) // 约60fps
    
    // 获取系统信息以计算可见区域
    wx.getSystemInfo({
      success: (res) => {
        const visibleItemCount = Math.ceil(600 / (this.data.itemHeight * res.windowWidth / 750)) + 1
        this.setData({ visibleItemCount })
      }
    })
  },

  // 列表相关方法
  loadInitialData() {
    this.loadListData(1)
  },

  async loadListData(page, isLoadMore = false) {
    if (isLoadMore) {
      this.setData({ isLoadingMore: true })
    }

    try {
      const newData = await this.mockAPIRequest(page)
      
      // 更新总数据
      const allListData = isLoadMore ? 
        [...this.data.allListData, ...newData] : 
        newData

      this.setData({
        allListData,
        pageNum: page,
        isRefreshing: false,
        isLoadingMore: false
      }, () => {
        // 更新可见区域数据
        this.updateVisibleData(this.data.startIndex)
      })
    } catch (error) {
      console.error('加载数据失败：', error)
      wx.showToast({
        title: '加载失败',
        icon: 'error'
      })
    }
  },

  mockAPIRequest(page) {
    return new Promise((resolve) => {
      setTimeout(() => {
        const startIndex = (page - 1) * this.data.pageSize
        const data = Array.from({ length: this.data.pageSize }, (_, i) => ({
          id: startIndex + i + 1,
          title: `项目 ${startIndex + i + 1}`,
          description: '这是一个示例项目描述',
          avatar: '/images/avatar.png',
          time: '2024-01-01',
          status: {
            text: ['进行中', '已完成', '已暂停'][Math.floor(Math.random() * 3)],
            color: ['#3498db', '#2ecc71', '#95a5a6'][Math.floor(Math.random() * 3)]
          },
          selected: false
        }))
        resolve(data)
      }, 1000)
    })
  },

  onRefresh() {
    this.setData({ isRefreshing: true })
    this.loadListData(1)
  },

  onLoadMore() {
    if (!this.data.isLoadingMore) {
      this.loadListData(this.data.pageNum + 1, true)
    }
  },

  onItemTap(e) {
    const { item } = e.currentTarget.dataset
    const index = this.data.allListData.findIndex(i => i.id === item.id)
    
    if (index !== -1) {
      const key = `allListData[${index}].selected`
      // 只更新被点击项的 selected 状态
      this.setData({
        [key]: !this.data.allListData[index].selected
      }, () => {
        // 更新可见区域数据
        this.updateVisibleData(this.data.startIndex)
      })
    }
  },

  // 动画相关方法
  runAnimation() {
    const animation = this.animation
      .scale(1.2)
      .rotate(45)
      .translate(30, 30)
      .step()
      .scale(1)
      .rotate(0)
      .translate(0, 0)
      .step()

    this.setData({
      animationData: animation.export()
    })
  },

  runRotate() {
    const animation = this.animation.rotate(360).step()
    this.setData({
      animationData: animation.export()
    })
  },

  runScale() {
    const animation = this.animation
      .scale(1.5)
      .step()
      .scale(1)
      .step()

    this.setData({
      animationData: animation.export()
    })
  },

  runTranslate() {
    const animation = this.animation
      .translate(50, 50)
      .step()
      .translate(0, 0)
      .step()

    this.setData({
      animationData: animation.export()
    })
  },

  // 日历相关方法
  initCalendar() {
    this.updateCalendar()
    // 模拟一些事件数据
    this.setData({
      events: {
        '2024-01-01': { title: '新年' },
        '2024-01-15': { title: '项目会议' }
      }
    })
  },

  updateCalendar() {
    const { currentYear, currentMonth } = this.data
    const days = []
    
    const firstDay = new Date(currentYear, currentMonth - 1, 1)
    const lastDay = new Date(currentYear, currentMonth, 0)
    
    // 上个月的天数
    for (let i = firstDay.getDay(); i > 0; i--) {
      const date = new Date(currentYear, currentMonth - 1, -i + 1)
      days.push({
        day: date.getDate(),
        date: this.formatDate(date),
        current: false
      })
    }
    
    // 当前月的天数
    for (let i = 1; i <= lastDay.getDate(); i++) {
      const date = new Date(currentYear, currentMonth - 1, i)
      days.push({
        day: i,
        date: this.formatDate(date),
        current: true,
        hasEvent: this.data.events[this.formatDate(date)] !== undefined
      })
    }
    
    this.setData({ days })
  },

  formatDate(date) {
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    return `${year}-${month}-${day}`
  },

  prevMonth() {
    let { currentYear, currentMonth } = this.data
    if (currentMonth === 1) {
      currentMonth = 12
      currentYear--
    } else {
      currentMonth--
    }
    this.setData({ currentYear, currentMonth }, () => {
      this.updateCalendar()
    })
  },

  nextMonth() {
    let { currentYear, currentMonth } = this.data
    if (currentMonth === 12) {
      currentMonth = 1
      currentYear++
    } else {
      currentMonth++
    }
    this.setData({ currentYear, currentMonth }, () => {
      this.updateCalendar()
    })
  },

  selectDate(e) {
    const { date } = e.currentTarget.dataset
    const days = this.data.days.map(day => ({
      ...day,
      selected: day.date === date
    }))
    this.setData({ days })

    if (this.data.events[date]) {
      wx.showToast({
        title: this.data.events[date].title,
        icon: 'none'
      })
    }
  },

  // 图表相关方法
  initChart() {
    const ctx = wx.createCanvasContext('lineChart')
    this.drawLineChart(ctx)
  },

  drawLineChart(ctx) {
    const data1 = [10, 20, 15, 25, 20, 30, 25]
    const data2 = [15, 10, 20, 15, 25, 20, 25]
    const width = 300
    const height = 200
    const padding = 30

    // 清空画布
    ctx.clearRect(0, 0, width, height)

    // 绘制坐标轴
    ctx.beginPath()
    ctx.moveTo(padding, height - padding)
    ctx.lineTo(width - padding, height - padding)
    ctx.moveTo(padding, height - padding)
    ctx.lineTo(padding, padding)
    ctx.setStrokeStyle('#999')
    ctx.stroke()

    // 绘制数据线
    this.drawLine(ctx, data1, '#3498db', width, height, padding)
    this.drawLine(ctx, data2, '#2ecc71', width, height, padding)

    ctx.draw()
  },

  drawLine(ctx, data, color, width, height, padding) {
    const step = (width - 2 * padding) / (data.length - 1)
    const max = Math.max(...data)
    const scale = (height - 2 * padding) / max

    ctx.beginPath()
    ctx.setStrokeStyle(color)
    ctx.setLineWidth(2)

    data.forEach((value, index) => {
      const x = padding + index * step
      const y = height - padding - value * scale
      if (index === 0) {
        ctx.moveTo(x, y)
      } else {
        ctx.lineTo(x, y)
      }
    })

    ctx.stroke()
  },

  // 手势相关方法
  touchStart(e) {
    if (e.touches.length === 1) {
      const currentTime = e.timeStamp
      const lastTapTime = this.data.lastTapTime
      
      // 检测双击
      if (currentTime - lastTapTime < 300) {
        this.setData({
          scale: 1,
          rotation: 0,
          translateX: 0,
          translateY: 0
        })
      }
      
      this.setData({ lastTapTime: currentTime })
    }
    
    this.startX = e.touches[0].clientX
    this.startY = e.touches[0].clientY
    this.startScale = this.data.scale
    this.startRotation = this.data.rotation
  },

  touchMove(e) {
    if (e.touches.length === 1) {
      // 单指拖动
      const deltaX = e.touches[0].clientX - this.startX
      const deltaY = e.touches[0].clientY - this.startY
      
      this.setData({
        translateX: deltaX,
        translateY: deltaY
      })
    } else if (e.touches.length === 2) {
      // 双指缩放和旋转
      const touch1 = e.touches[0]
      const touch2 = e.touches[1]
      
      const distance = Math.sqrt(
        Math.pow(touch2.clientX - touch1.clientX, 2) +
        Math.pow(touch2.clientY - touch1.clientY, 2)
      )
      
      const angle = Math.atan2(
        touch2.clientY - touch1.clientY,
        touch2.clientX - touch1.clientX
      ) * 180 / Math.PI
      
      if (!this.startDistance) {
        this.startDistance = distance
        this.startAngle = angle
      }
      
      const scale = this.startScale * (distance / this.startDistance)
      const rotation = this.startRotation + (angle - this.startAngle)
      
      this.setData({
        scale: Math.max(0.5, Math.min(2, scale)),
        rotation
      })
    }
  },

  touchEnd() {
    this.startDistance = null
    this.startAngle = null
  },

  // 虚拟滚动相关方法
  handleScroll(e) {
    const { scrollTop } = e.detail
    // 根据滚动位置计算起始索引
    const startIndex = Math.floor(scrollTop / (this.data.itemHeight * wx.getSystemInfoSync().windowWidth / 750))
    
    if (startIndex !== this.data.startIndex) {
      this.updateVisibleData(startIndex)
    }
  },

  updateVisibleData(startIndex) {
    const { visibleItemCount, bufferSize, allListData } = this.data
    
    // 计算实际的起始和结束索引，包含缓冲区
    const start = Math.max(0, startIndex - bufferSize)
    const end = Math.min(allListData.length, startIndex + visibleItemCount + bufferSize)
    
    // 只更新可见区域的数据
    this.setData({
      startIndex,
      visibleData: allListData.slice(start, end),
      listStyle: `transform: translateY(${start * this.data.itemHeight}rpx)`
    })
  },
}) 
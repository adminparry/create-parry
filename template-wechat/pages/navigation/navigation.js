Page({
  data: {
    currentTab: 0,
    refreshStatus: '下拉可以刷新',
    colors: ['#3498db', '#2ecc71', '#e74c3c', '#f1c40f', '#9b59b6']
  },

  onPullDownRefresh() {
    this.setData({
      refreshStatus: '正在刷新...'
    })
    
    setTimeout(() => {
      this.setData({
        refreshStatus: '刷新成功'
      })
      wx.stopPullDownRefresh()
    }, 2000)
  },

  showNavigationBarLoading() {
    wx.showNavigationBarLoading()
    setTimeout(() => {
      wx.hideNavigationBarLoading()
    }, 2000)
  },

  setNavigationBarTitle() {
    wx.setNavigationBarTitle({
      title: '新的标题'
    })
  },

  setNavigationBarColor() {
    const randomColor = this.data.colors[Math.floor(Math.random() * this.data.colors.length)]
    wx.setNavigationBarColor({
      frontColor: '#ffffff',
      backgroundColor: randomColor,
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
  },

  showToast() {
    wx.showToast({
      title: '操作成功',
      icon: 'success',
      duration: 2000
    })
  },

  showModal() {
    wx.showModal({
      title: '提示',
      content: '这是一个模态弹窗',
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定')
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  showActionSheet() {
    wx.showActionSheet({
      itemList: ['选项一', '选项二', '选项三'],
      success(res) {
        console.log(res.tapIndex)
      },
      fail(res) {
        console.log(res.errMsg)
      }
    })
  },

  switchTab(e) {
    const tab = parseInt(e.currentTarget.dataset.tab)
    this.setData({
      currentTab: tab
    })
  }
}) 
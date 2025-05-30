Page({
  data: {},

  onLoad() {
    this.ctx = wx.createCameraContext()
  },

  takePhoto() {
    this.ctx.takePhoto({
      quality: 'high',
      success: (res) => {
        wx.showToast({
          title: '拍照成功',
          icon: 'success'
        })
        console.log('拍照成功，临时文件路径：', res.tempImagePath)
      },
      fail: (error) => {
        console.error('拍照失败：', error)
        wx.showToast({
          title: '拍照失败',
          icon: 'error'
        })
      }
    })
  },

  error(e) {
    console.error('相机错误：', e.detail)
  }
}) 
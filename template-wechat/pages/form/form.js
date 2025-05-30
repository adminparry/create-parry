Page({
  data: {
    radioItems: [
      {value: '1', name: '选项1', checked: true},
      {value: '2', name: '选项2'},
      {value: '3', name: '选项3'}
    ],
    checkboxItems: [
      {value: '1', name: '选项1', checked: true},
      {value: '2', name: '选项2'},
      {value: '3', name: '选项3'}
    ],
    date: '2024-01-01',
    time: '12:00'
  },

  radioChange(e) {
    console.log('radio发生change事件，携带value值为：', e.detail.value)
  },

  checkboxChange(e) {
    console.log('checkbox发生change事件，携带value值为：', e.detail.value)
  },

  switchChange(e) {
    console.log('switch发生change事件，携带value值为：', e.detail.value)
  },

  sliderChange(e) {
    console.log('slider发生change事件，携带value值为：', e.detail.value)
  },

  dateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },

  timeChange(e) {
    this.setData({
      time: e.detail.value
    })
  }
}) 
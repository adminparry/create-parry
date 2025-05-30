Page({
  data: {
    longitude: 116.397390,
    latitude: 39.908860,
    scale: 14,
    markers: [],
    polyline: [],
    searchKeyword: '',
    searchResults: []
  },

  onLoad() {
    this.mapCtx = wx.createMapContext('myMap')
    this.getCurrentLocation()
  },

  getCurrentLocation() {
    wx.getLocation({
      type: 'gcj02',
      success: (res) => {
        this.setData({
          latitude: res.latitude,
          longitude: res.longitude
        })
      },
      fail: () => {
        wx.showToast({
          title: '获取位置失败',
          icon: 'error'
        })
      }
    })
  },

  moveToLocation() {
    this.mapCtx.moveToLocation()
  },

  addMarker() {
    const { latitude, longitude, markers } = this.data
    const newMarker = {
      id: markers.length,
      latitude,
      longitude,
      width: 30,
      height: 30,
      callout: {
        content: `标记 ${markers.length + 1}`,
        display: 'ALWAYS',
        padding: 10,
        borderRadius: 5
      }
    }

    this.setData({
      markers: [...markers, newMarker]
    })
  },

  drawRoute() {
    if (this.data.markers.length < 2) {
      wx.showToast({
        title: '需要至少2个标记点',
        icon: 'none'
      })
      return
    }

    const points = this.data.markers.map(marker => ({
      longitude: marker.longitude,
      latitude: marker.latitude
    }))

    this.setData({
      polyline: [{
        points,
        color: '#3498db',
        width: 4,
        arrowLine: true
      }]
    })
  },

  markerTap(e) {
    const marker = this.data.markers.find(m => m.id === e.markerId)
    if (marker) {
      wx.showModal({
        title: '标记信息',
        content: `标记 ${marker.id + 1}\n经度：${marker.longitude}\n纬度：${marker.latitude}`,
        showCancel: false
      })
    }
  },

  regionChange(e) {
    if (e.type === 'end' && e.causedBy === 'scale') {
      this.mapCtx.getScale({
        success: (res) => {
          this.setData({
            scale: res.scale
          })
        }
      })
    }
  },

  onSearchInput(e) {
    this.setData({
      searchKeyword: e.detail.value
    })
  },

  searchLocation() {
    if (!this.data.searchKeyword.trim()) {
      return
    }

    // 模拟搜索结果
    const mockResults = [{
      id: 1,
      name: this.data.searchKeyword,
      address: '模拟地址1',
      latitude: this.data.latitude + 0.01,
      longitude: this.data.longitude + 0.01
    }, {
      id: 2,
      name: this.data.searchKeyword + ' 附近',
      address: '模拟地址2',
      latitude: this.data.latitude - 0.01,
      longitude: this.data.longitude - 0.01
    }]

    this.setData({
      searchResults: mockResults
    })
  },

  selectLocation(e) {
    const location = e.currentTarget.dataset.location
    this.setData({
      latitude: location.latitude,
      longitude: location.longitude,
      scale: 16,
      searchResults: []
    })
  }
}) 
Page({
  data: {
    richTextNodes: [{
      name: 'div',
      attrs: {
        style: 'color: #3498db;'
      },
      children: [{
        type: 'text',
        text: '这是一段富文本内容，'
      }, {
        name: 'strong',
        children: [{
          type: 'text',
          text: '这里是加粗的文字'
        }]
      }]
    }]
  }
}) 
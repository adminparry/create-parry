import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { 
  Button, 
  Cell, 
  CellGroup,
  Icon,
  NavBar,
  Empty
} from 'vant'
import 'vant/lib/index.css'

const app = createApp(App)

// 注册 Vant 组件
app.use(Button)
   .use(Cell)
   .use(CellGroup)
   .use(Icon)
   .use(NavBar)
   .use(Empty)

app.use(router)
app.mount('#app') 
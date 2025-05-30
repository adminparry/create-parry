import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { 
  Button, 
  Cell, 
  CellGroup,
  Icon,
  NavBar,
  Empty,
  Field,
  Calendar,
  Checkbox,
  CheckboxGroup,
  Radio,
  RadioGroup,
  Rate,
  Dialog,
  Toast,
  Loading,
  Notify,
  ActionSheet
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
   .use(Field)
   .use(Calendar)
   .use(Checkbox)
   .use(CheckboxGroup)
   .use(Radio)
   .use(RadioGroup)
   .use(Rate)
   .use(Dialog)
   .use(Toast)
   .use(Loading)
   .use(Notify)
   .use(ActionSheet)

app.use(router)
app.mount('#app') 
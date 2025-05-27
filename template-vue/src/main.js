import { createApp } from "vue";
import App from "@/views/index.vue";

import router from "@/router";

import store from "@/stores";

import i18n from "@/i18n";

import { permissionDirective } from "@/utils/auth";

import ElementPlus from "element-plus";

import 'element-plus/dist/index.css'

// import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App);

// for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
//   app.component(key, component)
// }
app
  .use(router)
  .use(store)
  .use(i18n)
  .use(ElementPlus, { size: "small", })
  .directive("permission", permissionDirective)
  .mount("#app");

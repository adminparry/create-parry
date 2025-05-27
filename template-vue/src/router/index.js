import { createRouter, createWebHashHistory } from "vue-router";
import { routes } from "./routing.config";
import { isAuthenticated } from "@/utils/auth";


const router = createRouter({
  history: createWebHashHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  // to: Route 即将进入的目标路由对象
  // from: Route 当前导航离开的路由对象
  // next: Function 一定要调用该方法来 resolve 这个钩子。执行效果依赖 next 方法的调用参数。

  // 例如，权限验证
  if (to.meta.requiresAuth && !isAuthenticated()) {
    next({ name: "login" }); // 未认证时重定向到登录页面
  } else {
    next(); // 确保一定要调用 next()
  }
});

export default router;

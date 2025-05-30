import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/component/:name',
    name: 'ComponentDetail',
    component: () => import('../views/ComponentDetail.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 
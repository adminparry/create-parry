export const routes = [
  {
    path: "/",
    name: "home",
    requiresAuth: true,
    component: () => import("@/views/Home.vue"),
    children: [
      {
        path: "dashboard",
        name: "dashboard",
        requiresAuth: true,
        component: () => import("@/views/Dashboard.vue"),
      },
      {
        path: "profile",
        name: "profile",
        requiresAuth: true,
        component: () => import("@/views/Profile.vue"),
      },
   
    ],
  },
  {
    path: "/login",
    name: "login",
    requiresAuth: false,
    component: () => import("@/views/Login.vue"),
  },
  {
    path: "/app",
    name: "app",
    requiresAuth: true,
    component: () => import("@/App.vue"),
  },
];


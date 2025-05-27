import { useUserPermission } from "@/stores/permission";

export const isAuthenticated = () => {
  const token = localStorage.getItem("token");
  return token ? true : false;
};

export const permissionDirective = {
  mounted(el, binding) {
    const { hasPermission } = useUserPermission();
    const [code, curd] = binding.value.split(":");
    if (!hasPermission(code, curd)) {
      console.log(code, curd, "无权限");   
      el.style.display = "none";
      el.parentNode && el.parentNode.removeChild(el);
    } else {
      console.log(code, curd, "有权限");
    }
  },
};

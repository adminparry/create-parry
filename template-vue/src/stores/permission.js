import { defineStore } from "pinia";

export const useUserPermission = defineStore("permission", {
  state: () => ({
    permissions: [],
  }),
  actions: {
    hasPermission(code, curd) {
     

      const ret =
        this.permissions.filter(
          (item) => item.code === code && item.curd.split(".").includes(curd)
        ).length > 0;
      console.log(ret, "权限判断");
      return ret;
    },
    setPermissions(permissions) {
      this.permissions = permissions;
    },
  },
});

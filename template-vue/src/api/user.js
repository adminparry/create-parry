import request from "@/utils/request";

// 导出一个名为login的函数，用于用户登录
export function login(data) {
  // 发送一个post请求，请求的url为'/user/login'，请求的数据为data
  return request({
    url: "/user/login",
    method: "post",
    data,
  });
}

export function getInfo(token) {
  return request({
    url: "/src/assets/login.json",
    method: "get",
    params: { token },
  });
}

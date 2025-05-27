import axios from 'axios'


const request = axios.create({
    baseURL: import.meta.env.VUE_APP_BASE_API,
    timeout: 5000
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        // 在发送请求之前做些什么
        // config.headers['Authorization'] = 'Bearer ' + getToken()
        return config
    },
    error => {
        // 对请求错误做些什么
        return Promise.reject(error)
})

export default request
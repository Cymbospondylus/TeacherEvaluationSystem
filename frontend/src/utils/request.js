import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  timeout: 5000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在请求发送前附加 token
    if (store.getters.token) {
      config.headers['Authorization'] = getToken() // 使用 Bearer Token 格式
    }
    return config
  },
  error => {
    // 请求错误处理
    console.log(error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data

    // 当返回的 code 不为 200 时，认为是错误状态
    if (res.code !== 200) {
      Message({
        message: res.msg || '请求出错',
        type: 'error',
        duration: 5 * 1000
      })

      // 针对特定错误码进行处理
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // 50008: 非法的 token; 50012: 其他客户端登录了; 50014: token 过期;
        MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }

      // 返回 Promise.reject，并传递完整的响应对象
      //return Promise.reject(res) // 返回整个响应对象
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service

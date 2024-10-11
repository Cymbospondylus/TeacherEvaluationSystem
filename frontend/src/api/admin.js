// src/api/admin.js
import request from '@/utils/request'

// 获取管理员列表，并接收分页参数
export function fetchAdminList(data, token) {
  return request({
    url: '/admin/user/list',
    method: 'post', // 使用 POST 方法
    headers: {
      'Authorization': `Bearer ${token}`
    },
    data // 请求体中携带参数
  })
}


// 添加管理员
export function addAdmin(data, token) {
  return request({
    url: '/admin',
    method: 'post',
    headers: {
      'Authorization': `Bearer ${token}`
    },
    data // 请求体中携带表单数据
  })
}

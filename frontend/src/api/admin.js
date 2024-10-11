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


// 编辑管理员
export function editAdmin(data, token) {
  return request({
    url: `/admin/${data.userId}`, // 使用动态参数的方式构建 URL
    method: 'put',
    headers: {
      'Authorization': `Bearer ${token}`
    },
    data
  })
}

// 删除管理员
export function deleteAdmin(id, token) {
  return request({
    url: `/admin/${id}`, // 使用管理员的 ID 进行删除操作
    method: 'delete',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
}

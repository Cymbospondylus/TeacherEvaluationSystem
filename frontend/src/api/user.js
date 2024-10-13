import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'get'
  })
}

// 修改密码 API
export function updatePassword(data) {
  return request({
    url: '/user/updatePassword', // 根据实际后端 API 地址修改
    method: 'put',
    data
  })
}




import request from '@/utils/request'

// 获取学生列表，并接收分页参数
export function fetchStudentList(data, token) {
  return request({
    url: '/student/list',
    method: 'post',
    headers: {
      'Authorization': `Bearer ${token}`
    },
    data
  })
}

// 添加学生
export function addStudent(data, token) {
  return request({
    url: '/student',
    method: 'post',
    headers: {
      'Authorization': `Bearer ${token}`
    },
    data
  })
}

// 编辑学生信息
export function editStudent(data, token) {
  return request({
    url: `/student/${data.studentId}`,
    method: 'put',
    headers: {
      'Authorization': `Bearer ${token}`
    },
    data
  })
}

// 删除学生
export function deleteStudent(id, token) {
  return request({
    url: `/student/${id}`,
    method: 'delete',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
}

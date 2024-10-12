import request from '@/utils/request'

// 获取学生列表，并接收分页参数
export function fetchStudentList(data) {
  return request({
    url: '/student/list',
    method: 'post', // 使用 POST 方法获取带分页的学生列表
    data // 请求体中携带分页参数
  })
}

// 添加学生
export function addStudent(data) {
  return request({
    url: '/student',
    method: 'post',
    data // 请求体中携带表单数据
  })
}

// 编辑学生信息
export function editStudent(data) {
  return request({
    url: `/student/${data.userId}`, // 使用动态参数构建 URL
    method: 'put',
    data // 请求体中携带表单数据
  })
}

// 删除学生
export function deleteStudent(id) {
  return request({
    url: `/student/${id}`, // 使用学生ID删除学生信息
    method: 'delete',
  })
}

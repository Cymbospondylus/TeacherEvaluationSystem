import request from '@/utils/request'

// 获取教师列表，并接收分页参数
export function fetchTeacherList(data) {
  return request({
    url: '/teacher/list',
    method: 'post', // 使用 POST 方法获取带分页的教师列表
    data // 请求体中携带分页参数
  })
}

// 添加教师
export function addTeacher(data) {
  return request({
    url: '/teacher',
    method: 'post',
    data // 请求体中携带表单数据
  })
}

// 编辑教师信息
export function editTeacher(data) {
  return request({
    url: `/teacher/${data.teacherId}`, // 使用动态参数构建 URL
    method: 'put',
    data // 请求体中携带表单数据
  })
}

// 删除教师
export function deleteTeacher(id) {
  return request({
    url: `/teacher/${id}`, // 使用教师ID删除教师信息
    method: 'delete',
  })
}

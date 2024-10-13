import request from '@/utils/request'

// 获取需要评价的教师列表
export function fetchTeachers() {
  return request({
    url: '/evaluation/teachers',
    method: 'get'
  })
}

// 获取评价指标
export function fetchIndicators() {
  return request({
    url: '/evaluation/indicators',
    method: 'get'
  })
}

// 提交评价
export function submitEvaluation(data) {
  return request({
    url: '/evaluation/submit',
    method: 'post',
    data
  })
}

// 获取教师评价结果
export function fetchTeacherEvaluations(teacherId) {
  return request({
    url: `/evaluation/results/${teacherId}`,
    method: 'get'
  })
}

import request from '@/utils/request'

// 获取评价指标列表
export function fetchIndicatorList() {
  return request({
    url: '/indicator/list',
    method: 'get'
  })
}

// 添加评价指标
export function addIndicator(data) {
  return request({
    url: '/indicator',
    method: 'post',
    data
  })
}

// 编辑评价指标
export function editIndicator(data) {
  return request({
    url: `/indicator/${data.indicatorId}`,
    method: 'put',
    data
  })
}

// 删除评价指标
export function deleteIndicator(id) {
  return request({
    url: `/indicator/${id}`,
    method: 'delete'
  })
}

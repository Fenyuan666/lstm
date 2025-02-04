import request from '@/utils/request'

// 查询井详细信息管理列表
export function listInfo(query) {
  return request({
    url: '/well_info/info/list',
    method: 'get',
    params: query
  })
}

// 查询井详细信息管理详细
export function getInfo(id) {
  return request({
    url: '/well_info/info/' + id,
    method: 'get'
  })
}

// 新增井详细信息管理
export function addInfo(data) {
  return request({
    url: '/well_info/info',
    method: 'post',
    data: data
  })
}

// 修改井详细信息管理
export function updateInfo(data) {
  return request({
    url: '/well_info/info',
    method: 'put',
    data: data
  })
}

// 删除井详细信息管理
export function delInfo(id) {
  return request({
    url: '/well_info/info/' + id,
    method: 'delete'
  })
}

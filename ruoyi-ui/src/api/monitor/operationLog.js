import request from '@/utils/request'

// 查询操作日志列表
export function list(query) {
  return request({
    url: '/monitor/operationLog/list',
    method: 'get',
    params: query
  })
}

// 删除操作日志
export function delOperationLog(id) {
  return request({
    url: '/monitor/operationLog/' + id,
    method: 'delete'
  })
}

// 清空操作日志
export function cleanOperationLog() {
  return request({
    url: '/monitor/operationLog/clean',
    method: 'delete'
  })
}

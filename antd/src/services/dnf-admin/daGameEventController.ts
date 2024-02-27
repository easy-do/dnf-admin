// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 清空 GET /api/gameEvent/cleanGameEvent */
export async function cleanGameEvent(options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/gameEvent/cleanGameEvent', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页 POST /api/gameEvent/page */
export async function gameEventPage(body: API.DaGameEventQo, options?: { [key: string]: any }) {
  return request<API.RListDaGameEvent>('/api/gameEvent/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除单条 GET /api/gameEvent/remove/${param0} */
export async function removeGameEvent(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeGameEventParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameEvent/remove/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

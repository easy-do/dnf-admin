// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 GET /api/gameRole/allList */
export async function allList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.allListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListCharacInfo>('/api/gameRole/allList', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/gameRole/list */
export async function roleList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.roleListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListCharacInfo>('/api/gameRole/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

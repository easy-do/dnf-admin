// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 详情 GET /api/conf/info/${param0} */
export async function getConfInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getConfInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaGameConfig>(`/api/conf/info/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页 POST /api/conf/page */
export async function pageConf(body: API.DaGameConfigQo, options?: { [key: string]: any }) {
  return request<API.RListDaGameConfig>('/api/conf/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 添加 POST /api/conf/save */
export async function saveConf(body: API.DaGameConfig, options?: { [key: string]: any }) {
  return request<API.RObject>('/api/conf/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新 POST /api/conf/update */
export async function updateConf(body: API.DaGameConfig, options?: { [key: string]: any }) {
  return request<API.RObject>('/api/conf/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

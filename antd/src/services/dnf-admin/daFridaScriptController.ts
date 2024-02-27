// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 详情 GET /api/fridaScript/getInfo/${param0} */
export async function getFridaScriptInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getFridaScriptInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaFridaScript>(`/api/fridaScript/getInfo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 查询所有 GET /api/fridaScript/list */
export async function listFridaScript(options?: { [key: string]: any }) {
  return request<API.RListDaFridaScript>('/api/fridaScript/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页 POST /api/fridaScript/page */
export async function pageFridaScript(body: API.FridaScriptQo, options?: { [key: string]: any }) {
  return request<API.RListDaFridaScript>('/api/fridaScript/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 添加 POST /api/fridaScript/save */
export async function saveFridaScript(body: API.DaFridaScript, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/fridaScript/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新 POST /api/fridaScript/update */
export async function updateFridaScript(body: API.DaFridaScript, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/fridaScript/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

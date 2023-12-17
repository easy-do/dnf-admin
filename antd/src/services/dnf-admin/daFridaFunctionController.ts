// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 GET /api/fridaFunction/getChildrenFunction/${param0} */
export async function getChildrenFunction(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getChildrenFunctionParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RListDaFridaFunction>(`/api/fridaFunction/getChildrenFunction/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/fridaFunction/getInfo/${param0} */
export async function getFridaFunctionInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getFridaFunctionInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaFridaFunction>(`/api/fridaFunction/getInfo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/fridaFunction/list */
export async function listFridaFunction(options?: { [key: string]: any }) {
  return request<API.RListDaFridaFunction>('/api/fridaFunction/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/fridaFunction/page */
export async function pageFridaFunction(
  body: API.FridaFunctionQo,
  options?: { [key: string]: any },
) {
  return request<API.RListDaFridaFunction>('/api/fridaFunction/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/fridaFunction/save */
export async function saveFridaFunction(
  body: API.DaFridaFunction,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/fridaFunction/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/fridaFunction/update */
export async function updateFridaFunction(
  body: API.DaFridaFunction,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/fridaFunction/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

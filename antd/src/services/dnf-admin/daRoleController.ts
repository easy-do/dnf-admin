// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 详情 GET /api/role/info/${param0} */
export async function getRoleInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getRoleInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaRole>(`/api/role/info/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页 POST /api/role/page */
export async function pageRole(body: API.DaRoleQo, options?: { [key: string]: any }) {
  return request<API.RListDaRole>('/api/role/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 添加 POST /api/role/save */
export async function saveRole(body: API.DaRole, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/role/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新 POST /api/role/update */
export async function updateRole(body: API.DaRole, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/role/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

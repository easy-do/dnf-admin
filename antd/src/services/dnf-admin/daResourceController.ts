// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 授权角色资源 POST /api/resource/authRoleResource */
export async function authRoleResource(
  body: API.AuthRoleResourceDto,
  options?: { [key: string]: any },
) {
  return request<API.RObject>('/api/resource/authRoleResource', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取所有资源下拉树 GET /api/resource/resourceTree */
export async function resourceTree(options?: { [key: string]: any }) {
  return request<API.RListTreeLong>('/api/resource/resourceTree', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 加载对应角色资源列表 GET /api/resource/roleResource/${param0} */
export async function roleResource(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.roleResourceParams,
  options?: { [key: string]: any },
) {
  const { roleId: param0, ...queryParams } = params;
  return request<API.RListTreeLong>(`/api/resource/roleResource/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 加载对应角色资源id集合 GET /api/resource/roleResourceIds/${param0} */
export async function roleResourceIds(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.roleResourceIdsParams,
  options?: { [key: string]: any },
) {
  const { roleId: param0, ...queryParams } = params;
  return request<API.RListLong>(`/api/resource/roleResourceIds/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

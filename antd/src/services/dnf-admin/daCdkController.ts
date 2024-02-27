// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 添加cdk配置 POST /api/cdk/add */
export async function addCdk(body: API.DaCdk, options?: { [key: string]: any }) {
  return request<API.RListString>('/api/cdk/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 导出 POST /api/cdk/exportCdk */
export async function exportCdk(body: string[], options?: { [key: string]: any }) {
  return request<any>('/api/cdk/exportCdk', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** cdk详情 GET /api/cdk/getInfo/${param0} */
export async function getCdkInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getCdkInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaCdk>(`/api/cdk/getInfo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页查询 POST /api/cdk/page */
export async function pageCdk(body: API.DaCdkQo, options?: { [key: string]: any }) {
  return request<API.RListDaCdk>('/api/cdk/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除cdk POST /api/cdk/remove */
export async function removeCdk(body: string[], options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/cdk/remove', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 兑换cdk POST /api/cdk/useCdk */
export async function useCdk(body: API.UseCdkDto, options?: { [key: string]: any }) {
  return request<API.RString>('/api/cdk/useCdk', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 GET /api/accounts/disable/${param0} */
export async function disableAccounts(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.disableAccountsParams,
  options?: { [key: string]: any },
) {
  const { uid: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/accounts/disable/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/accounts/enable/${param0} */
export async function enableAccounts(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.enableAccountsParams,
  options?: { [key: string]: any },
) {
  const { uid: param0, ...queryParams } = params;
  return request<API.RString>(`/api/accounts/enable/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/accounts/info/${param0} */
export async function getAccounts(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAccountsParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RAccounts>(`/api/accounts/info/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/accounts/page */
export async function pageAccounts(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pageAccountsParams,
  options?: { [key: string]: any },
) {
  return request<API.RListAccounts>('/api/accounts/page', {
    method: 'POST',
    params: {
      ...params,
      page: undefined,
      ...params['page'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/accounts/resetPass/${param0} */
export async function resetPassword(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.resetPasswordParams,
  options?: { [key: string]: any },
) {
  const { uid: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/accounts/resetPass/${param0}`, {
    method: 'GET',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/accounts/save */
export async function saveAccounts(body: API.Accounts, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/accounts/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/accounts/update */
export async function updateAccounts(body: API.Accounts, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/accounts/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

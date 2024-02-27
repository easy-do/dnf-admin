// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 封禁 GET /api/accounts/disable/${param0} */
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

/** 解封 GET /api/accounts/enable/${param0} */
export async function enableAccounts(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.enableAccountsParams,
  options?: { [key: string]: any },
) {
  const { uid: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/accounts/enable/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 账户详情 GET /api/accounts/info/${param0} */
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

/** 开启全图王者 GET /api/accounts/openDungeon/${param0} */
export async function openDungeon(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.openDungeonParams,
  options?: { [key: string]: any },
) {
  const { uid: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/accounts/openDungeon/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页 POST /api/accounts/page */
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

/** 点券充值 GET /api/accounts/rechargeBonds */
export async function rechargeBonds(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.rechargeBondsParams,
  options?: { [key: string]: any },
) {
  return request<API.RString>('/api/accounts/rechargeBonds', {
    method: 'GET',
    params: {
      // type has a default value: 1
      type: '1',

      // count has a default value: 1
      count: '1',
      ...params,
    },
    ...(options || {}),
  });
}

/** 重置角色创建限制 GET /api/accounts/resetCreateRole/${param0} */
export async function resetCreateRole(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.resetCreateRoleParams,
  options?: { [key: string]: any },
) {
  const { uid: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/accounts/resetCreateRole/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 重置密码 GET /api/accounts/resetPass/${param0} */
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

/** 添加账户 POST /api/accounts/save */
export async function saveAccounts(body: API.RegDto, options?: { [key: string]: any }) {
  return request<API.RLong>('/api/accounts/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 设置角色栏最大 GET /api/accounts/setMaxRole/${param0} */
export async function setMaxRole(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.setMaxRoleParams,
  options?: { [key: string]: any },
) {
  const { uid: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/accounts/setMaxRole/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 编辑账户 POST /api/accounts/update */
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

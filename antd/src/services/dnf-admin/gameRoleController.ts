// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 所有角色列表 GET /api/gameRole/allList */
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

/** 清空所有时装 GET /api/gameRole/cleanItems/${param0} */
export async function cleanItems(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.cleanItemsParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/cleanItems/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 清空角色邮件 POST /api/gameRole/cleanMail/${param0} */
export async function cleanMail(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.cleanMailParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/cleanMail/${param0}`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 清空角色物品 GET /api/gameRole/cleanRoleItem/${param0}/${param1} */
export async function cleanRoleItem(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.cleanRoleItemParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, type: param1, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/cleanRoleItem/${param0}/${param1}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 开通账号金库 GET /api/gameRole/createAccountCargo/${param0} */
export async function createAccountCargo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.createAccountCargoParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/createAccountCargo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取账号金库信息 GET /api/gameRole/getAccountCargo/${param0} */
export async function getAccountCargo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getAccountCargoParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RAccountCargo>(`/api/gameRole/getAccountCargo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取角色其他信息 GET /api/gameRole/getOtherData/${param0} */
export async function getOtherData(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getOtherDataParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.ROtherDataVo>(`/api/gameRole/getOtherData/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取角色物品 GET /api/gameRole/getRoleItem/${param0}/${param1} */
export async function getRoleItem(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getRoleItemParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, type: param1, ...queryParams } = params;
  return request<API.RRoleItemVo>(`/api/gameRole/getRoleItem/${param0}/${param1}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 详情 GET /api/gameRole/info/${param0} */
export async function gameRoleInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.gameRoleInfoParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RCharacInfo>(`/api/gameRole/info/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取时装信息 GET /api/gameRole/items/${param0} */
export async function roleItems(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.roleItemsParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RListUserItems>(`/api/gameRole/items/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 所有角色列表 GET /api/gameRole/list */
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

/** 开启角色左右槽 GET /api/gameRole/openLeftAndRight/${param0} */
export async function openLeftAndRight(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.openLeftAndRightParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/openLeftAndRight/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页查询 POST /api/gameRole/page */
export async function gameRolePage(body: API.CharacInfoQo, options?: { [key: string]: any }) {
  return request<API.RListCharacInfo>('/api/gameRole/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 恢复角色 GET /api/gameRole/recover/${param0} */
export async function gameRoleRecover(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.gameRoleRecoverParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/recover/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 删除角色 GET /api/gameRole/remove/${param0} */
export async function gameRoleRemove(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.gameRoleRemoveParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/remove/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 删除账号金库 GET /api/gameRole/removeAccountCargo/${param0} */
export async function removeAccountCargo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeAccountCargoParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/removeAccountCargo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 删除单件时装 GET /api/gameRole/removeItems/${param0} */
export async function removeItems(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeItemsParams,
  options?: { [key: string]: any },
) {
  const { uiId: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/gameRole/removeItems/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 修改角色其他信息 POST /api/gameRole/setOtherData */
export async function setOtherData(body: API.OtherDataDto, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/gameRole/setOtherData', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新角色信息 POST /api/gameRole/update */
export async function gameRoleUpdate(body: API.CharacInfo, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/gameRole/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 修改账号金库信息 POST /api/gameRole/updateAccountCargo */
export async function updateAccountCargo(
  body: API.AccountCargoDto,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/gameRole/updateAccountCargo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新角色物品 POST /api/gameRole/updateRoleItem */
export async function updateRoleItem(
  body: API.EditGameRoleItemDto,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/gameRole/updateRoleItem', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

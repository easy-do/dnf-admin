// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** pc端签到 GET /api/signIn/characSign/${param0} */
export async function characSign(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.characSignParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RObject>(`/api/signIn/characSign/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 详情 GET /api/signIn/info/${param0} */
export async function signInInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.signInInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaSignInConfVo>(`/api/signIn/info/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页 POST /api/signIn/page */
export async function signInPage(body: API.DaSignInConfQo, options?: { [key: string]: any }) {
  return request<API.RListDaSignInConf>('/api/signIn/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 添加 POST /api/signIn/save */
export async function saveSignIn(body: API.DaSignInConfDto, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/signIn/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取角色签到列表 GET /api/signIn/signInList/${param0} */
export async function signList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.signListParams,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RListDaSignInConfVo>(`/api/signIn/signInList/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新 POST /api/signIn/update */
export async function updateSignIn(body: API.DaSignInConfDto, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/signIn/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

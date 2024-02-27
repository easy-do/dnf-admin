// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取当前用户信息 GET /api/user/currentUser */
export async function currentUser(options?: { [key: string]: any }) {
  return request<API.RCurrentUser>('/api/user/currentUser', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 登录 POST /api/user/login */
export async function login(body: API.LoginDto, options?: { [key: string]: any }) {
  return request<API.RString>('/api/user/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 退出 GET /api/user/logout */
export async function logout(options?: { [key: string]: any }) {
  return request<API.RString>('/api/user/logout', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 注册 POST /api/user/reg */
export async function reg(body: API.RegDto, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/user/reg', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取当前用户资源信息 GET /api/user/userResource */
export async function userResource(options?: { [key: string]: any }) {
  return request<API.RListTreeLong>('/api/user/userResource', {
    method: 'GET',
    ...(options || {}),
  });
}

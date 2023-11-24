// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 GET /api/gameTool/disableAcc */
export async function disableAcc(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.disableAccParams,
  options?: { [key: string]: any },
) {
  return request<API.RString>('/api/gameTool/disableAcc', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/gameTool/enableAcc */
export async function enableAcc(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.enableAccParams,
  options?: { [key: string]: any },
) {
  return request<API.RString>('/api/gameTool/enableAcc', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/gameTool/generateKeyPem */
export async function generateKeyPem(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/generateKeyPem', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/gameTool/getGameToken */
export async function getGameToken(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/getGameToken', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/gameTool/rechargeBonds */
export async function rechargeBonds(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.rechargeBondsParams,
  options?: { [key: string]: any },
) {
  return request<API.RString>('/api/gameTool/rechargeBonds', {
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

/** 此处后端没有提供注释 GET /api/gameTool/restartDa */
export async function restartAdmin(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/restartDa', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/gameTool/restartDb */
export async function restartDb(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/restartDb', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/gameTool/restartServer */
export async function restartServer(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/restartServer', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/gameTool/sendMail */
export async function sendMail(body: API.SendMailDto, options?: { [key: string]: any }) {
  return request<API.RObject>('/api/gameTool/sendMail', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

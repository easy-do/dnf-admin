// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 添加机器人脚本配置 POST /api/botScript/add */
export async function addBotScript(body: API.DaBotEventScript, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/botScript/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 所有脚本列表 GET /api/botScript/botScriptList */
export async function botScriptList(options?: { [key: string]: any }) {
  return request<API.RListDaBotEventScript>('/api/botScript/botScriptList', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 机器人脚本详情 POST /api/botScript/info/${param0} */
export async function infoBotScript(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.infoBotScriptParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaBotEventScript>(`/api/botScript/info/${param0}`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页查询 POST /api/botScript/page */
export async function pageBotScript(
  body: API.DaBotEventScriptQo,
  options?: { [key: string]: any },
) {
  return request<API.RListDaBotEventScript>('/api/botScript/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除机器人脚本 POST /api/botScript/remove */
export async function removeBotScript(body: string[], options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/botScript/remove', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新机器人脚本配置 POST /api/botScript/update */
export async function updateBotScript(
  body: API.DaBotEventScript,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/botScript/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

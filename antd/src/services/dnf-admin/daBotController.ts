// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 添加机器人配置 POST /api/bot/add */
export async function addBot(body: API.DaBotInfo, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/bot/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 添加机器人配置 POST /api/bot/addBotConf */
export async function addBotConf(body: API.DaBotConf, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/bot/addBotConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 启用脚本 POST /api/bot/enableBotScript */
export async function enableBotScript(
  body: API.EnableBotScriptDto,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/bot/enableBotScript', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取机器人配置 GET /api/bot/getBotConf/${param0} */
export async function getBotConf(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getBotConfParams,
  options?: { [key: string]: any },
) {
  const { botNumber: param0, ...queryParams } = params;
  return request<API.RListDaBotConf>(`/api/bot/getBotConf/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 已开启脚本 POST /api/bot/getEnableBotScript/${param0} */
export async function getEnableBotScript(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getEnableBotScriptParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RListLong>(`/api/bot/getEnableBotScript/${param0}`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 机器人详情 POST /api/bot/info/${param0} */
export async function infoBot(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.infoBotParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaBotInfo>(`/api/bot/info/${param0}`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页查询 POST /api/bot/page */
export async function pageBot(body: API.DaBotQo, options?: { [key: string]: any }) {
  return request<API.RListDaBotInfo>('/api/bot/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 分页查询消息记录 POST /api/bot/pageBotMessage */
export async function pageBotMessage(body: API.DaBotMessageQo, options?: { [key: string]: any }) {
  return request<API.RListDaBotMessage>('/api/bot/pageBotMessage', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 分页查询通知记录 POST /api/bot/pageBotNotice */
export async function pageBotNotice(body: API.DaBotNoticeQo, options?: { [key: string]: any }) {
  return request<API.RListDaBotNotice>('/api/bot/pageBotNotice', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 分页查询请求记录 POST /api/bot/pageBotRequest */
export async function pageBotRequest(body: API.DaBotRequestQo, options?: { [key: string]: any }) {
  return request<API.RListDaBotRequest>('/api/bot/pageBotRequest', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除机器人 POST /api/bot/remove */
export async function removeBot(body: string[], options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/bot/remove', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除机器人配置 GET /api/bot/removeBotConf/${param0} */
export async function removeBotConf(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeBotConfParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/bot/removeBotConf/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新机器人配置 POST /api/bot/update */
export async function updateBot(body: API.DaBotInfo, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/bot/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新机器人配置 POST /api/bot/updateBotConf */
export async function updateBotConf(body: API.DaBotConf, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/bot/updateBotConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

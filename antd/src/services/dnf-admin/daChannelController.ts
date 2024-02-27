// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 发送frida调试信息 POST /api/channel/debugFrida */
export async function debugFrida(body: API.DebugFridaDto, options?: { [key: string]: any }) {
  return request<API.RString>('/api/channel/debugFrida', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取frida的debug日志 GET /api/channel/getDebugLog/${param0} */
export async function getDebugLog(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getDebugLogParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RListString>(`/api/channel/getDebugLog/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取frida的容器日志 GET /api/channel/getFridaLog/${param0} */
export async function getFridaLog(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getFridaLogParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RListString>(`/api/channel/getFridaLog/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 频道详情 GET /api/channel/getInfo/${param0} */
export async function getChannelInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getChannelInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaChannel>(`/api/channel/getInfo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页查询 POST /api/channel/page */
export async function pageChannel(body: API.ChannelQo, options?: { [key: string]: any }) {
  return request<API.RListDaChannel>('/api/channel/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 重启frida容器 GET /api/channel/restartFrida/${param0} */
export async function restartFrida(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.restartFridaParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RListString>(`/api/channel/restartFrida/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 停止frida容器 GET /api/channel/stopFrida/${param0} */
export async function stopFrida(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.stopFridaParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RListString>(`/api/channel/stopFrida/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新frida脚本 POST /api/channel/updateFridaJs */
export async function updateFridaJs(body: API.UpdateScriptDto, options?: { [key: string]: any }) {
  return request<API.RListString>('/api/channel/updateFridaJs', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新python脚本 POST /api/channel/updatePythonScript */
export async function updatePythonScript(
  body: API.UpdateScriptDto,
  options?: { [key: string]: any },
) {
  return request<API.RListString>('/api/channel/updatePythonScript', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

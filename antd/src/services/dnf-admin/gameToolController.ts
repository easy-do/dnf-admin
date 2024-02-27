// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 清空全服邮件 GET /api/gameTool/cleanMail */
export async function cleanMail1(options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/gameTool/cleanMail', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 生成并更新密钥对 GET /api/gameTool/generateKeyPem */
export async function generateKeyPem(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/generateKeyPem', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取游戏登录token GET /api/gameTool/getGameToken */
export async function getGameToken(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/getGameToken', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 在线用户数量 GET /api/gameTool/onlineCount */
export async function onlineCount(options?: { [key: string]: any }) {
  return request<API.ROnlineCountVo>('/api/gameTool/onlineCount', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 重启后台 GET /api/gameTool/restartDa */
export async function restartAdmin(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/restartDa', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 重启数据库 GET /api/gameTool/restartDb */
export async function restartDb(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/restartDb', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 重启服务端 GET /api/gameTool/restartServer */
export async function restartServer(options?: { [key: string]: any }) {
  return request<API.RString>('/api/gameTool/restartServer', {
    method: 'GET',
    ...(options || {}),
  });
}

// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

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

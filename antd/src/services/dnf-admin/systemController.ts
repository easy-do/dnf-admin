// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取当前模式 GET /api/sys/mode */
export async function mode(options?: { [key: string]: any }) {
  return request<string>('/api/sys/mode', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取当前版本 GET /api/sys/version */
export async function version(options?: { [key: string]: any }) {
  return request<string>('/api/sys/version', {
    method: 'GET',
    ...(options || {}),
  });
}

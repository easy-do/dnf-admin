// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 数据库列表 GET /api/db/databases */
export async function databases(options?: { [key: string]: any }) {
  return request<API.RListString>('/api/db/databases', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 导出数据库备份 GET /api/db/export */
export async function exportDb(options?: { [key: string]: any }) {
  return request<any>('/api/db/export', {
    method: 'GET',
    ...(options || {}),
  });
}

// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 POST /api/cq/post */
export async function post(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/cq/post', {
    method: 'POST',
    ...(options || {}),
  });
}

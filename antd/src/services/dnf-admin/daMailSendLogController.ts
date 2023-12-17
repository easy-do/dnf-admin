// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 POST /api/mailSendLog/page */
export async function pageMailSendLog(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListDaMailSendLog>('/api/mailSendLog/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

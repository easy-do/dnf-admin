// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 POST /api/gameNotice/page */
export async function pageGameNotice(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListDaNoticeSendLog>('/api/gameNotice/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/gameNotice/sendNotice */
export async function sendNotice(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.sendNoticeParams,
  options?: { [key: string]: any },
) {
  return request<API.RObject>('/api/gameNotice/sendNotice', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 GET /api/dp/report */
export async function roleList1(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.roleList1Params,
  options?: { [key: string]: any },
) {
  return request<API.RObject>('/api/dp/report', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

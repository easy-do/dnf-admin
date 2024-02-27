// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 GET /api/captcha/v1 */
export async function generateCaptchaV1(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.generateCaptchaV1Params,
  options?: { [key: string]: any },
) {
  return request<any>('/api/captcha/v1', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/captcha/v2 */
export async function generateCaptchaV2(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.generateCaptchaV2Params,
  options?: { [key: string]: any },
) {
  return request<API.RCaptchaVo>('/api/captcha/v2', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 详情 GET /api/license */
export async function licenseDetails(options?: { [key: string]: any }) {
  return request<API.RLicenseDetails>('/api/license', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 许可注册 POST /api/license/reg */
export async function regLicense(body: API.RegLicenseDto, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/license/reg', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 重置注册 GET /api/license/resetLicense */
export async function resetLicense(options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/license/resetLicense', {
    method: 'GET',
    ...(options || {}),
  });
}

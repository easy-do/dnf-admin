// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 邮件发送记录分页 POST /api/mail/pageMailSendLog */
export async function pageMailSendLog(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListDaMailSendLog>('/api/mail/pageMailSendLog', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除邮件 POST /api/mail/removeMail/${param0} */
export async function removeMail(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeMailParams,
  options?: { [key: string]: any },
) {
  const { postalId: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/mail/removeMail/${param0}`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 角色邮件分页 POST /api/mail/roleMailPage/${param0} */
export async function roleMailPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.roleMailPageParams,
  body: API.RoleMailPageQo,
  options?: { [key: string]: any },
) {
  const { characNo: param0, ...queryParams } = params;
  return request<API.RListPostal>(`/api/mail/roleMailPage/${param0}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 发送邮件 POST /api/mail/sendRoleMail */
export async function sendMail(body: API.SendMailDto, options?: { [key: string]: any }) {
  return request<API.RObject>('/api/mail/sendRoleMail', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

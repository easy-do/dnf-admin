// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 GET /api/item/downloadTemplate */
export async function downloadTemplate(options?: { [key: string]: any }) {
  return request<any>('/api/item/downloadTemplate', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/item/importItem */
export async function importItem(body: {}, options?: { [key: string]: any }) {
  return request<any>('/api/item/importItem', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/item/importItemFor7z */
export async function importItemFor7Z(body: {}, options?: { [key: string]: any }) {
  return request<any>('/api/item/importItemFor7z', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/item/list */
export async function listItem(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listItemParams,
  options?: { [key: string]: any },
) {
  return request<API.RListDaItemEntity>('/api/item/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/item/page */
export async function pageItem(body: API.DaItemQo, options?: { [key: string]: any }) {
  return request<API.RListDaItemEntity>('/api/item/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/item/remove */
export async function removeItem(body: string[], options?: { [key: string]: any }) {
  return request<boolean>('/api/item/remove', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/item/save */
export async function saveItem(body: API.DaItemEntity, options?: { [key: string]: any }) {
  return request<boolean>('/api/item/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/item/update */
export async function updateItem(body: API.DaItemEntity, options?: { [key: string]: any }) {
  return request<boolean>('/api/item/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

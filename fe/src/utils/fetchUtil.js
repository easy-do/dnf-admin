import { Toast } from '@douyinfe/semi-ui';
import getConfig from 'next/config'
import cookie from 'react-cookies'

const { publicRuntimeConfig } = getConfig()

let baseUrl = "http://localhost:8080/api" //dev

if (process.env.NODE_ENV === "production") {
  // 为线上环境修改配置...
  //Modify the configuration for the online environment
  baseUrl = "/api"  //线上
}

export function fetchPost(url, body) {
  return fetch(baseUrl + url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-credentials': 'true', // 每次携带cookie 必须加,不然不会携带
      'Authorization': localStorage.getItem("token") ? localStorage.getItem("token") : "", // 从localStorageStorage中获取access token
    },
    body: JSON.stringify(body),
  }, { credentials: 'include' }).then((res) => res.json()).then((data) => {
    checkResult(data)
    return data
  })
}

export function fetchGet(url) {
  return fetch(baseUrl + url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-credentials': 'true', // 每次携带cookie 必须加,不然不会携带
      'Authorization': localStorage.getItem("token") ? localStorage.getItem("token") : "", // 从localStorageStorage中获取access token
    },
    body: undefined,
  }, { credentials: 'include' }).then((res) => res.json()).then((data) => {
    checkResult(data)
    return data
  })
}

function checkResult(data) {
  if (data.code == 200) {
    Toast.success(data.msg, 1);
  } else if (data.code == 403) {
    Toast.warning('登录过期,或未登录。', 3);
    cookie.remove('token');
    localStorage.clear()
    sessionStorage.clear()
    window.location.pathname = '/login'
  } else {
    Toast.error(data.msg, 3);
  }
}






import request from '@src/utils/request'

export async function loginRequest(body) {
    const res = await request({
        url: `/user/login`,
        method: 'post',
        body
    });
    return res.data;
}

export async function logoutRequest() {
    const res = await request({
        url: `/user/logout`,
        method: 'get'
    });
    return res.data;
}


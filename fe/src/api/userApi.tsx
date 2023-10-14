import { postRequest, getRequest } from '@src/utils/request'

export const loginRequest = (body) => {
	return postRequest(`/user/login`, body)
}

export const logoutRequest = () => {
	return getRequest(`/user/logout`)
}

export const getCurrentUserRequest = async () => {
	return await getRequest(`/user/currentUser`)
}


import { postRequest, getRequest } from '@src/utils/request'

export const signInList = (roleId) => {
	return getRequest('/signIn/signInList/'+roleId)
}

export const roleSign = (roleId) => {
	return getRequest('/signIn/roleSign/'+roleId)
}

export const configPageRequest = (param) => {
	return postRequest('/signIn/page', param)
}

export const configInfoReuet = (id) => {
	return getRequest('/signIn/info/'+id)
}

export const updateConfigRequest = (param) => {
	return postRequest('/signIn/update', param)
}
import { postRequest, getRequest } from '@src/utils/request'

export const signInList = (roleId) => {
	return getRequest('/signIn/signInList/'+roleId)
}

export const characSign = (roleId) => {
	return getRequest('/signIn/characSign/'+roleId)
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

export const inertConfigRequest = (param) => {
	return postRequest('/signIn/insert', param)
}
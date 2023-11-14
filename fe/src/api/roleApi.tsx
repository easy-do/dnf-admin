import { postRequest, getRequest } from '@src/utils/request'


export const rolePageRequest = (param) => {
	return postRequest('/role/page', param)
}

export const roleInfoRequest = (id) => {
	return getRequest('/role/info/'+id)
}

export const updateRoleRequest = (param) => {
	return postRequest('/role/update', param)
}

export const saveRoleRequest = (param) => {
	return postRequest('/role/save', param)
}
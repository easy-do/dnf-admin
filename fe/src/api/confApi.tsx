import { postRequest, getRequest } from '@src/utils/request'


export const confPageRequest = (param) => {
	return postRequest('/conf/page', param)
}

export const confInfoRequest = (id) => {
	return getRequest('/conf/info/'+id)
}

export const updateConfRequest = (param) => {
	return postRequest('/conf/update', param)
}

export const saveConfRequest = (param) => {
	return postRequest('/conf/save', param)
}
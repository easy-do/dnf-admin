import { postRequest, getRequest } from '@src/utils/request'


export const resourceTreeRequest = () => {
	return getRequest('/resource/resourceTree')
}

export const roleResourceIdsRequest = (id) => {
	return getRequest('/resource/roleResourceIds/'+id)
}

export const authRoleResourceRequest = (param) => {
	return postRequest('/resource/authRoleResource', param)
}

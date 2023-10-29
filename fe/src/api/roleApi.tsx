import { postRequest, getRequest } from '@src/utils/request'

export const roleList = (name) => {
	return getRequest(`/gameRole/list?name=`+name)
}

export const allRoleList = (name) => {
	return getRequest(`/gameRole/allList?name=`+name)
}


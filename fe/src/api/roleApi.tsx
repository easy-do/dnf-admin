import { postRequest, getRequest } from '@src/utils/request'

export const roleList = () => {
	return getRequest('/gameRole/list')
}

export const allRoleList = () => {
	return getRequest('/gameRole/allList')
}


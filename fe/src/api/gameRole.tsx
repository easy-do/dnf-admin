import { getRequest } from '@src/utils/request'

export const gameRoleList = () => {
	return getRequest('/gameRole/list')
}

export const allGameRoleList = () => {
	return getRequest('/gameRole/allList')
}


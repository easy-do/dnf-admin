import { postRequest, getRequest } from '@src/utils/request'

export const signInList = (roleId) => {
	return getRequest('/signIn/signInList/'+roleId)
}

export const roleSign = (roleId) => {
	return getRequest('/signIn/roleSign/'+roleId)
}
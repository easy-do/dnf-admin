import { postRequest, getRequest } from '@src/utils/request'

export const roleList = () => {
	return getRequest(`/gameRole/list`)
}

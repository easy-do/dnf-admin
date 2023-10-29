import { postRequest, getRequest } from '@src/utils/request'

export const sendNoticeMessage = (message) => {
	return getRequest('/gameNotice/send?message='+message)
}



export const getGameNoticePage = (param) => {
	return postRequest('/gameNotice/page', param)
}

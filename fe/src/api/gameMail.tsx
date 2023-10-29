import { postRequest, getRequest } from '@src/utils/request'

export const sendGameMemail = (param) => {
	return postRequest('/gameTool/sendMail', param)
}



export const getGameMailSendLog = (param) => {
	return postRequest('/mailSendLog/page', param)
}

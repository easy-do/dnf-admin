import { postRequest, getRequest, postFormData } from '@src/utils/request'

export const getItemList = (name) => {
	return getRequest('/item/list?name='+name)
}



export const importItem = (param) => {
	return postFormData('/item/importItem', param)
}


export const getItemPage = (param) => {
	return postRequest('/item/page', param)
}

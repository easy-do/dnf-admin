import create from 'zustand'
import { configInfoReuet, configPageRequest, signInList } from '@src/api/signInApi'

export interface signInState {
	signInList: any
	getSgnInList: (roleId) => void
	editShow:false
	listShow:false
	editId:undefined
	setEditId: (data) => void
	setEditShow: (data) => void
	setListShow: (data) => void
	getConfigPage: (param) => void
	cureentSignIn:any
	getSignInInfo: (id) => any
	setSignInInfo: (data) => void
	signInPageData:[]
}

const signInState = create<signInState>((set, get) => ({
	signInList: undefined,
	getSgnInList: async (roleId) => {
		const data = await signInList(roleId);
		set({ signInList: data })
	},
	editShow:false,
	editId:undefined,
	listShow:false,
	cureentSignIn:{
		configName:undefined,
		configDate:undefined,
		reanrk:undefined,
		configJson:{
			type:0,
			data:[]
		}
	},
	getSignInInfo: async (id) => {
		const data = await configInfoReuet(id);
		const jsonStr = data.configJson;
		data.configJson = JSON.parse(jsonStr)
		set({ cureentSignIn: data })
		return data
	},
	setSignInInfo: (data) => {
		set({ cureentSignIn: data })
	},
	signInPageData:[],
	getConfigPage: async (param) => {
		const data = await configPageRequest(param);
		set({ signInPageData: data.records })
	},
	setEditShow: (data) => {
		set({ editShow: data })
	},
	setListShow: (data) => {
		set({ listShow: data })
	},
	setEditId: (data) => {
		set({ editId: data })
	}
}))

export default signInState

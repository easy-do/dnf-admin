import create from 'zustand'
import { configInfoReuet, configPageRequest, signInList } from '@src/api/signInApi'

export interface signInState {
	signInList: any
	getSgnInList: (roleId) => void
	listShow:false
	editId:undefined
	setEditId: (data) => void
	editShow:false
	setEditShow: (data) => void
	addShow:false
	setAddShow: (data) => void
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
	editId:undefined,
	setEditId: (data) => {
		set({ editId: data })
	},
	editShow:false,
	setEditShow: (data) => {
		set({ editShow: data })
	},
	addShow:false,
	setAddShow: (data) => {
		set({ addShow: data })
	},
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
	setListShow: (data) => {
		set({ listShow: data })
	}
}))

export default signInState

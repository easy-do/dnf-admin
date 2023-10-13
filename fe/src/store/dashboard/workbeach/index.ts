import create from 'zustand'
import request from '@src/utils/request'

export interface workBeachState {
	headerData: any
	inProcessData: any
	recentActivityData: any
	getWorkBeachData: () => void
}

const useStore = create<workBeachState>((set, get) => ({
	headerData: [],
	inProcessData: [],
	recentActivityData: [],
	getWorkBeachData: async () => {
		const res = await request({
			url: `/workbeach`,
			method: 'get'
		})
		const { headerData, inProcessData, recentActivityData } = res.data
		set({
			headerData,
			inProcessData,
			recentActivityData
		})
	}
}))

export default useStore

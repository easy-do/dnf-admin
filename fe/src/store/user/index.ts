import create from 'zustand'
import request from '@src/utils/request'

export interface userState {
	uid
    accountname
    qq
    dzuid
    billing
    vip
	getCurrentUser: () => any
}

const useStore = create<userState>((set, get) => ({
	uid:undefined,
    accountname:undefined,
    qq:undefined,
    dzuid:undefined,
    billing:undefined,
    vip:undefined,
	getCurrentUser: async () => {
		const res = await request({
			url: `/user/currentUser`,
			method: 'get'
		})
		const {success} = res.data.success;
		
		const { uid, accountname, qq, dzuid, billing, vip } = res.data.data
		set({
			uid,
			accountname,
			qq,
			dzuid,
			billing,
			vip
		})
		return success;
	}
}))

export default useStore

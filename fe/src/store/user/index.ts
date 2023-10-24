import create from 'zustand'

import { getCurrentUserRequest } from '@src/api/userApi'

export interface userState {
	uid
    accountname
    qq
    dzuid
    billing
    vip
    isAdmin
	getCurrentUser: () => any
}

const useStore = create<userState>((set, get) => ({
	uid:undefined,
    accountname:undefined,
    qq:undefined,
    dzuid:undefined,
    billing:undefined,
    vip:undefined,
    isAdmin:false,
	getCurrentUser: async () => {
		const data = await getCurrentUserRequest();
		set(data)
		return data !== undefined;
	}
}))

export default useStore

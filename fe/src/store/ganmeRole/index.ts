import create from 'zustand'
import {roleList} from '@src/api/roleApi'

export interface gameRoleState {
	roleList:any
	getRoleList: ()=> void
}

const gameRoleState = create<gameRoleState>((set, get) => ({
    roleList:undefined,
	getRoleList: async () => {
			const data = await roleList();
			set({roleList:data})
		}
}))

export default gameRoleState

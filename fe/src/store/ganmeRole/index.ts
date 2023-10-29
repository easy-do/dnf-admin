import create from 'zustand'
import { roleList } from '@src/api/roleApi'

export interface gameRoleState {
	roleList: any
	getRoleList: () => void
	currentRole: any
	setCurrentRole: (role) => void
}

const gameRoleState = create<gameRoleState>((set) => ({
	roleList: undefined,
	currentRole: undefined,
	getRoleList: async () => {
		const data = await roleList('');
		set({ roleList: data })
	},
	setCurrentRole: (role) =>{
		set({ currentRole: role })
	}
}))

export default gameRoleState

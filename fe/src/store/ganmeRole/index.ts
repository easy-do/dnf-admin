import create from 'zustand'
import { gameRoleList } from '@src/api/gameRole'

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
		const data = await gameRoleList();
		set({ roleList: data })
	},
	setCurrentRole: (role) =>{
		set({ currentRole: role })
	}
}))

export default gameRoleState

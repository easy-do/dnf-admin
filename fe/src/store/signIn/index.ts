import create from 'zustand'
import { signInList } from '@src/api/signInApi'

export interface signInState {
	signInList: any
	getSgnInList: (roleId) => void
}

const signInState = create<signInState>((set, get) => ({
	signInList: undefined,
	getSgnInList: async (roleId) => {
		const data = await signInList(roleId);
		set({ signInList: data })
	}
}))

export default signInState

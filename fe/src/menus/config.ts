import {
	IconHome,
	IconHorn,
	IconTick,
	IconEdit,
	IconGridRectangle,
	IconApps,
	IconTickCircle,
	IconAlertTriangle,
	IconUser
} from '@douyinfe/semi-icons'

export interface MenuItem {
	itemKey: string
	text: string
	icon?: React.ReactNode
	path?: string
	items?: MenuItem[]
	component?: React.ComponentType<any>
}

// const MENU_CONFIG: MenuItem[] = [
// 	{
// 		itemKey: '1',
// 		text: 'app.menu.home',
// 		icon: IconHome,
// 		items: [
// 			{
// 				itemKey: '1-1',
// 				text: 'app.menu.dashboard.home',
// 				path: '/dashboard/home'
// 			},
// 			{
// 				itemKey: '1-4',
// 				text: 'app.menu.dashboard.signin',
// 				path: '/dashboard/signin'
// 			}
// 		]
// 	}
// ]

const MENU_CONFIG: MenuItem[] = [
	{
		itemKey: '1',
		text: 'app.menu.home',
		icon: IconHome,
		path: '/home'
	},
	{
		itemKey: '2',
		text: 'app.menu.signin',
		icon: IconTick,
		path: '/signin'
	},
	{
		itemKey: '3',
		text: 'app.menu.notice',
		icon: IconHorn,
		path: '/notice'
	}
]

export default MENU_CONFIG

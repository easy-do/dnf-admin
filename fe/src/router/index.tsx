import React, { lazy, FC } from 'react'
import { RouteObject } from 'react-router'
import { useRoutes } from 'react-router-dom'
import { WrapperRouteComponent, WrapperRouteWithOutLayoutComponent } from './config'
import LoginPage from '@pages/login'
import LayoutPage from '@pages/layout'
import Empty from '@components/empty'

const DashboardWorkbeach = lazy(() => import('@src/pages/home'))
const DashboardAnlyanis = lazy(() => import('@src/pages/dashboard/anlyanis'))
const Signin = lazy(() => import('@src/pages/signin'))


const routeList: RouteObject[] = [
	{
		path: '/',
		element: <WrapperRouteComponent element={<LayoutPage />} titleId="" auth />,
		children: [
			{
				path: '/home',
				element: <WrapperRouteComponent element={<DashboardWorkbeach />} titleId="首页" auth />
			},
			{
				path: 'dashboard/anlyanis',
				element: <WrapperRouteComponent element={<DashboardAnlyanis />} titleId="分析页" auth />
			},
			{
				path: '/signin',
				element: <WrapperRouteComponent element={<Signin />} titleId="签到" auth />
			}
		]
	},
	{
		path: 'login',
		element: <WrapperRouteWithOutLayoutComponent element={<LoginPage />} titleId="登录" />
	},
	{
		path: '*',
		element: (
			<WrapperRouteWithOutLayoutComponent
				element={<Empty title="找不到咯" description="这里什么也没有~" type="404" />}
				titleId="404"
			/>
		)
	}
]

const RenderRouter: FC = () => {
	const element = useRoutes(routeList)
	return element
}

export default RenderRouter

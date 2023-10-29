import Calendar from '@douyinfe/semi-ui/lib/es/calendar'
import React, { useEffect } from 'react'
import Popover from '@douyinfe/semi-ui/lib/es/popover'
import Tag from '@douyinfe/semi-ui/lib/es/tag'
import { Button, Card, List, Select, Space, Toast } from '@douyinfe/semi-ui'

import ganmeRoleStore from '@src/store/ganmeRole'

import userStore from '@src/store/user'

import signInStore from '@src/store/signIn'

import dayjs from 'dayjs'
import { characSign } from '@src/api/signInApi'
import SingInConfig from '../singInconfig'

const Index: React.FC = () => {


	const isAdmin = userStore((state) => state.isAdmin)

	const getCurrentUser = userStore((state) => state.getCurrentUser)

	const getRoleList = ganmeRoleStore((state) => state.getRoleList)

	const roleList = ganmeRoleStore((state) => state.roleList)

	const currentRole = ganmeRoleStore((state) => state.currentRole)

	const setCurrentRole = ganmeRoleStore((state) => state.setCurrentRole)
	

	const roleSelect = []
	if (roleList) {
		roleList.map((value, index) => {
			roleSelect.push(
				<Select.Option key={index} value={value.characNo}>
					{value.characName}
				</Select.Option>
			)
		})
	}


	

	const signInList = signInStore((state) => state.signInList)
	const getSgnInList = signInStore((state) => state.getSgnInList)
	const listShow = signInStore((state) => state.listShow)
	const setListShow = signInStore((state) => state.setListShow)


	const roleOnSelect = (value, option) => {
		setCurrentRole(value)
		getSgnInList(value)
	}

	const signIn = async () => {
		const res = await characSign(currentRole)
		if(res){
			Toast.success("签到成功,注意查看邮箱.")
			getSgnInList(currentRole)
		}
	}
	

	

	useEffect(() => {
		getCurrentUser()
		getRoleList()
	}, [])

	let importDateMap: Map<string, any> = new Map()

	if (signInList) {
		signInList.map((value, idnex) => {
			importDateMap.set(value.configDate, value)
		})
	}

	// 获取当前格式化后的今天的年月日
	const nowDate = dayjs(new Date()).format('YYYY-MM-DD')

	const dateRender = (dateString, date) => {
		//日历的时间格式化后
		const ymdDate = dayjs(date).format('YYYY-MM-DD')

		const conf = importDateMap.get(ymdDate)
		if (conf) {
			const { configName,configJson, signInTime } = conf
			const configJson1 = JSON.parse(configJson)
			const { type, data } = configJson1
			const listDataSource: any[] = []
			data.map((v, i) => {
				listDataSource.push(v.name)
			})

			return (
				<Popover
					content={
						<List
							header={
								<div>
									{configName}:{' '}
									{
										<>
											<Tag
												visible={ymdDate === nowDate && !signInTime}
												size="small"
												shape="circle"
												color="blue"
												onClick={signIn}
											>
												点击领取
											</Tag>
											<Tag visible={ymdDate <= nowDate && signInTime} shape="circle" color="cyan">
												{' '}
												已领取
											</Tag>
											<Tag visible={ymdDate < nowDate && !signInTime} shape="circle" color="red">
												{' '}
												已错过
											</Tag>
											<Tag visible={ymdDate > nowDate} shape="circle" color="orange">
												{' '}
												未开始
											</Tag>
										</>
									}
								</div>
							}
							footer={''}
							split={false}
							dataSource={listDataSource}
							renderItem={(item) => <List.Item>{item}</List.Item>}
						/>
					}
					position="right"
					showArrow
					style={{
						// backgroundColor: 'rgba(var(--semi-blue-2),1)',
						// borderColor: 'rgba(var(--semi-blue-2),1)',
						// color: 'var(--semi-color-white)',
						borderWidth: 1,
						borderStyle: 'solid'
					}}
				>
					<div
						style={{
							position: 'absolute',
							left: '0',
							right: '0',
							top: '0',
							bottom: '0',
							backgroundColor: signInTime
								? 'rgba(var(--semi-blue-1),1)'
								: ymdDate > nowDate || (ymdDate === nowDate && !signInTime)
								? 'rgba(var(--semi-orange-1),1)'
								: 'rgba(var(--semi-red-1),1)'
						}}
					/>
				</Popover>
			)
		}
		return null
	}


	return (
		<Card
			header={
				<Space>
					
					<div>选择角色:</div> <Select filter placeholder={'请先选择角色'} onSelect={roleOnSelect}>
						{roleSelect}
					</Select>
					{isAdmin ? <Button type='primary' onClick={()=>setListShow(true)}>签到配置</Button>:null}
				</Space>
			}
		>
			<Calendar mode="month" dateGridRender={dateRender}></Calendar>
			<SingInConfig visible={listShow} setVisible={setListShow} />
		</Card>
	)
}

export default Index

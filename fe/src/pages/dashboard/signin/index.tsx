import Calendar from '@douyinfe/semi-ui/lib/es/calendar'
import React, { useEffect, useState } from 'react'
import Popover from '@douyinfe/semi-ui/lib/es/popover'
import Tag from '@douyinfe/semi-ui/lib/es/tag'
import { Card, List, Select, Space, Toast } from '@douyinfe/semi-ui'

import ganmeRoleStore from '@src/store/ganmeRole'

import signInStore from '@src/store/signIn'

import dayjs from 'dayjs'
import { roleSign } from '@src/api/signInApi'

const Index: React.FC = () => {

	const getRoleList = ganmeRoleStore((state) => state.getRoleList)

	const roleList = ganmeRoleStore((state) => state.roleList)

	const [getCureentRole,setCureentRole] = useState(1);

	

	const roleSelect = []
	if (roleList) {
		roleList.map((value, index) => {
			roleSelect.push(
				<Select.Option value={value.characNo}>
					{value.characName + '-' + value.jobName + '-' + value.mid + '-' + value.characNo}
				</Select.Option>
			)
		})
	}


	

	const signInList = signInStore((state) => state.signInList)
	const getSgnInList = signInStore((state) => state.getSgnInList)


	const roleOnSelect = (value, option) => {
		setCureentRole(value)
		getSgnInList(value)
	}

	const signIn = async () => {
		const res = await roleSign(getCureentRole)
		if(res){
			Toast.success("签到成功")
			getSgnInList(getCureentRole)
		}
	}

	

	useEffect(() => {
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
			const { configJson, signInTime } = conf
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
									奖励:{' '}
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
					<div>选择角色:</div> <Select placeholder={'请先选择角色'} onSelect={roleOnSelect}>
						{roleSelect}
					</Select>
				</Space>
			}
		>
			<Calendar mode="month" dateGridRender={dateRender}></Calendar>
		</Card>
	)
}

export default Index

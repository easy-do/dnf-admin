import { Button, Card, Form, Modal, Table, useFormApi } from '@douyinfe/semi-ui'
import React, { useEffect, useState } from 'react'
import signInStore from '@src/store/signIn'
import SingInConfigEdit from './signConfigEdit'


const SingInConfig = (props) => {

    
	const signInPageData = signInStore((state) => state.signInPageData)

	const getConfigPage = signInStore((state) => state.getConfigPage)

	const setEditId = signInStore((state) => state.setEditId)

	const setEditShow = signInStore((state) => state.setEditShow)



	const columns = [
		{
			title: '配置名称',
			dataIndex: 'configName'
		},
		{
			title: '签到日期',
			dataIndex: 'configDate'
		},
		{
			title: '创建时间',
			dataIndex: 'createTime'
		},
		{
			title: '更新时间',
			dataIndex: 'updateTime'
		},
		{
			title: '备注',
			dataIndex: 'remark'
		},
		{
			title: '',
			dataIndex: 'operate',
			render: (text, record) => (
				<Button type="primary" onClick={() => editconfig(record.id)}>
					配置
				</Button>
			)
		}
	]
    
    
	const editconfig = (id) => {
		setEditId(id)
		setEditShow(true)
	}

    useEffect(() => {
		if(props.visible || !setEditShow){
			getConfigPage({})
		}
	}, [props.visible,setEditShow])

	return (
		<>
			<Modal
				title="签到配置"
				fullScreen
				visible={props.visible}
				onOk={() => props.setVisible(false)}
				onCancel={() => props.setVisible(false)}
			>
				<Card>
					<Table columns={columns} pagination={true} dataSource={signInPageData} />
				</Card>
			</Modal>
			<SingInConfigEdit/>
		</>
	)
}

export default SingInConfig

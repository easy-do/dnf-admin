import React, { useEffect, useState } from 'react'
import { Button, ButtonGroup, Card, Form, Table } from '@douyinfe/semi-ui'

import userStore from '@src/store/user'
import { confPageRequest } from '@src/api/confApi'
import { FormApi } from '@douyinfe/semi-ui/lib/es/form'

import ConfEdit from './configEdit'
import ConfAdd from './configAdd'

const Index: React.FC = () => {

	const [formApi, setFormApi] = useState<FormApi>()

	const onSearch = () => {
		handlePageChange(pageData.pageNumber)
	}

	const onRefurush = () => {
		handlePageChange(pageData.pageNumber)
	}

	const onReset = () => {
		formApi.setValues({}, { isOverride: true })
		handlePageChange(pageData.pageNumber)
	}

	const [addFlag,setAddFlag]=useState(false);

	const addConf = ()=>{
		setAddFlag(true)
	}
	
	const [editFlag,setEditFlag]=useState(false);
	const [editId,setEditId]=useState(false);

	const editConf = (id)=>{
		setEditFlag(true)
		setEditId(id)
	}


	const isAdmin = userStore((state) => state.isAdmin)

	const getCurrentUser = userStore((state) => state.getCurrentUser)

	const [pageData, setPageData] = useState({
		pageNumber: 1,
		pageSize: 10,
		totalRow: 0,
		records: []
	})

	const handlePageChange = (pageNumber) => {
		let values
		if (formApi) {
			values = formApi.getValues()
		} else {
			values = {}
		}

		confPageRequest({
			pageNumber: pageNumber,
			pageSize: pageData.pageSize,
			...values
		}).then((res) => {
			setPageData({ values, ...res })
		})
	}

	const columns = [
		{
			title: '配置名称',
			dataIndex: 'confName'
		},
		{
			title: '配置标签',
			dataIndex: 'confKey'
		},
		{
			title: '配置类型',
			dataIndex: 'confType'
		},
		{
			title: '备注',
			dataIndex: 'remark'
		},
		{
			title: '',
			dataIndex: 'operate',
			render: (text, record) => (
				<Button type="primary" onClick={() => editConf(record.id)}>
					配置
				</Button>
			)
		}
	]

	useEffect(() => {
		getCurrentUser()
		handlePageChange(1)
	}, [])

	return (
		<>
			<Card>
				<Form
					getFormApi={setFormApi}
					wrapperCol={{ span: 24 }}
					labelCol={{ span: 8 }}
					layout="horizontal"
					labelPosition="inset"
				>
					<Form.Input label="配置名称" field="name"></Form.Input>
					<Form.Input label="配置标签" field="type"></Form.Input>
					<ButtonGroup>
						<Button onClick={onSearch}>搜索</Button>
						<Button onClick={onReset}>重置</Button>
						<Button onClick={onRefurush}>刷新</Button>
					</ButtonGroup>
				</Form>
			</Card>
			<Card>
				<Button onClick={addConf}>添加</Button>
				<Table
					columns={columns}
					pagination={{
						currentPage: pageData.pageNumber,
						pageSize: pageData.pageSize,
						total: pageData.totalRow,
						onPageChange: handlePageChange
					}}
					dataSource={pageData.records}
				/>
				<ConfAdd addFlag={addFlag} setAddFlag={setAddFlag} handlePageChange={handlePageChange} />
				<ConfEdit editShow={editFlag} editId={editId} setEditShow={setEditFlag} handlePageChange={handlePageChange} />
			</Card>
		</>
	)
}

export default Index

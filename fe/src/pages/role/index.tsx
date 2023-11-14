import React, { useEffect, useState } from 'react'
import { Button, ButtonGroup, Card, Form, Table } from '@douyinfe/semi-ui'

import userStore from '@src/store/user'
import { rolePageRequest } from '@src/api/roleApi'
import { FormApi } from '@douyinfe/semi-ui/lib/es/form'

import RoleEdit from './roleEdit'
import RoleAdd from './roleAdd'
import RoleAuth from './roleAuth'

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

	const [addFlag, setAddFlag] = useState(false)

	const addRole = () => {
		setAddFlag(true)
	}

	const [editFlag, setEditFlag] = useState(false)
	const [editId, setEditId] = useState(false)

	const editRole = (id) => {
		setEditFlag(true)
		setEditId(id)
	}

	const [authFlag, setAuthFlag] = useState(false)
	const [authRoleId, setAuthRoleId] = useState(false)

	const authRole = (id) => {
		setAuthFlag(true)
		setAuthRoleId(id)
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

		rolePageRequest({
			pageNumber: pageNumber,
			pageSize: pageData.pageSize,
			...values
		}).then((res) => {
			setPageData({ values, ...res })
		})
	}

	const columns = [
		{
			title: '角色名称',
			dataIndex: 'roleName'
		},
		{
			title: '角色标识',
			dataIndex: 'roleKey'
		},
		{
			title: '备注',
			dataIndex: 'remark'
		},
		{
			title: '',
			dataIndex: 'operate',
			render: (text, record) => (
				<ButtonGroup>
					<Button type="primary" onClick={() => editRole(record.id)}>
						编辑
					</Button>
					<Button type="primary" onClick={() => authRole(record.id)}>
						授权
					</Button>
				</ButtonGroup>
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
					<Form.Input label="角色名称" field="roleName"></Form.Input>
					<ButtonGroup>
						<Button onClick={onSearch}>搜索</Button>
						<Button onClick={onReset}>重置</Button>
						<Button onClick={onRefurush}>刷新</Button>
					</ButtonGroup>
				</Form>
			</Card>
			<Card>
				<Button onClick={addRole}>添加</Button>
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
				<RoleAdd addFlag={addFlag} setAddFlag={setAddFlag} handlePageChange={handlePageChange} />
				<RoleEdit editShow={editFlag} editId={editId} setEditShow={setEditFlag} handlePageChange={handlePageChange} />
				<RoleAuth authShow={authFlag} authRoleId={authRoleId} setAuthShow={setAuthFlag} handlePageChange={handlePageChange} />
			</Card>
		</>
	)
}

export default Index

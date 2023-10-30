import React, { useEffect, useState } from 'react'
import {
	Button,
	ButtonGroup,
	Card,
	Form,
	Table,
	Toast,
	Upload
} from '@douyinfe/semi-ui'

import userStore from '@src/store/user'
import { getItemPage, importItem } from '@src/api/gameItem'
import { IconUpload } from '@douyinfe/semi-icons'
import { customRequestArgs } from '@douyinfe/semi-ui/lib/es/upload'
import { FormApi } from '@douyinfe/semi-ui/lib/es/form'

const Index: React.FC = () => {

	const [formApi,setFormApi] = useState<FormApi>();

	const onSearch = ()=>{
		handlePageChange(pageData.pageNumber)

	}

	const onRefurush = ()=>{
		handlePageChange(pageData.pageNumber)
	}


	const onReset= ()=>{
		formApi.setValues({},{isOverride:true})
		handlePageChange(pageData.pageNumber)
	}


	const uploadIem = (object: customRequestArgs) => {
		importItem({ file: object.fileInstance })
		Toast.success('操作成功,导入操作将在后台运行')
	}

	const isAdmin = userStore((state) => state.isAdmin)

	const getCurrentUser = userStore((state) => state.getCurrentUser)

	const downloadItemTemplate = () => {
		const w = window.open('about:blank')
		w.location.href = '/api/item/downloadTemplate'
	}

	const [pageData, setPageData] = useState({
		pageNumber: 1,
		pageSize: 10,
		totalRow: 0,
		records: []
	})

	const handlePageChange = (pageNumber) => {
		let values;
		if(formApi){
			values = formApi.getValues();
		}else{
			values = {}
		}
		 
		getItemPage({
			pageNumber: pageNumber,
			pageSize: pageData.pageSize,
			... values
		}).then((res) => {
			setPageData({values,...res})
		})
	}

	const columns = [
		{
			title: '物品id',
			dataIndex: 'id'
		},
		{
			title: '物品名称',
			dataIndex: 'name'
		},
		{
			title: '物品类型',
			dataIndex: 'type'
		},
		{
			title: '稀有度',
			dataIndex: 'rarity'
		}
	]


	useEffect(() => {
		getCurrentUser()
		handlePageChange(1)
	},[])

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
					<Form.Input label="名称" field="name"></Form.Input>
					<Form.Input label="类型" field="type"></Form.Input>
					<Form.Input label="稀有度" field="rarity"></Form.Input>
					<ButtonGroup>
						<Button onClick={onSearch}>搜索</Button>
						<Button onClick={onReset}>重置</Button>
						<Button onClick={onRefurush}>刷新</Button>
						<Button onClick={downloadItemTemplate}>下载模板</Button>
						<Upload showUploadList={false} accept=".xlsx" limit={1} customRequest={uploadIem}>
							<Button icon={<IconUpload />} theme="light">
								导入物品
							</Button>
						</Upload>
					</ButtonGroup>
				</Form>
			</Card>
			<Card>
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
			</Card>
		</>
	)
}

export default Index

import React, { useEffect, useState } from 'react'
import { Button, Card, Form, List, Select, Space, Table, TextArea, Toast, Upload } from '@douyinfe/semi-ui'

import userStore from '@src/store/user'
import { getItemPage, importItem } from '@src/api/gameItem'
import { IconUpload } from '@douyinfe/semi-icons';
import { customRequestArgs } from '@douyinfe/semi-ui/lib/es/upload';


const Index: React.FC = () => {


	const uploadIem = (object: customRequestArgs) =>{
		importItem({'file':object.fileInstance});
		Toast.success("操作成功,导入操作将在后台运行")
	}

	const isAdmin = userStore((state) => state.isAdmin)

	const getCurrentUser = userStore((state) => state.getCurrentUser)

	const downloadItemTemplate = () =>{
		const w=window.open('about:blank');
        w.location.href='/api/item/downloadTemplate' 
	}

	const [pageData, setPageData] = useState({
		pageNumber: 1,
		pageSize: 10,
		totalRow: 0,
		records: []
	})

	const handlePageChange = (pageNumber) => {
		setPageData({
			pageNumber: pageNumber,
			pageSize: pageData.pageSize,
			totalRow: pageData.totalRow,
			records: pageData.records
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

	const pageRequest = () => {
		getItemPage({ pageNumber: pageData.pageNumber, pageSize: pageData.pageSize }).then((res) => {
			setPageData(res)
		})
	}

	useEffect(() => {
		getCurrentUser()
		pageRequest()
	}, [JSON.stringify(pageData.pageNumber)])

	return (
		<Card>
			<Space>
			<Button onClick={downloadItemTemplate}>下载模板</Button>
			<Upload showUploadList={false} accept='.xlsx' limit={1} customRequest={uploadIem}>
            <Button icon={<IconUpload />} theme="light">
                导入物品
            </Button>
           </Upload>
			</Space>

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
	)
}

export default Index

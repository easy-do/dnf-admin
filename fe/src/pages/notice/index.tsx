import React, { useEffect, useState } from 'react'
import { Button, Card, Form, List, Select, Space, Table, TextArea, Toast } from '@douyinfe/semi-ui'

import userStore from '@src/store/user'
import { getGameNoticePage, sendNoticeMessage } from '@src/api/gameNotice'

const Index: React.FC = () => {
	const isAdmin = userStore((state) => state.isAdmin)

	const getCurrentUser = userStore((state) => state.getCurrentUser)

	const [pageData, setPageData] = useState({
		pageNumber: 1,
		pageSize: 10,
		totalRow: 0,
		records: []
	})

	const [message, setMessage] = useState('公告内容')

	const sendMessage = () => {
		sendNoticeMessage(message).then((res) => {
			Toast.success('发送成功')
			handlePageChange(1)
			pageRequest()
		})
	}

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
			title: '公告内容',
			dataIndex: 'message'
		},
		{
			title: '发送时间',
			dataIndex: 'createTime'
		}
	]

	const pageRequest = () => {
		getGameNoticePage({ pageNumber: pageData.pageNumber, pageSize: pageData.pageSize }).then((res) => {
			setPageData(res)
		})
	}

	useEffect(() => {
		getCurrentUser()
		pageRequest()
	}, [JSON.stringify(pageData.pageNumber)])

	return (
		<>
			<Card>
				<TextArea
					style={{ width: '90%' }}
					maxLength={64}
					rows={1}
					autosize
					placeholder="公告内容"
					onChange={setMessage}
				/>
				<Button onClick={sendMessage}>发送公告</Button>
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

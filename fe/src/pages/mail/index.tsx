import React, { useEffect, useState } from 'react'
import { Button, Card, List, Modal, Popover, Select, Space, Table, Tag, TextArea, Toast } from '@douyinfe/semi-ui'

import userStore from '@src/store/user'
import { getGameMailSendLog } from '@src/api/gameMail'
import SendMail from './sendMail'
import ListItem from '@douyinfe/semi-ui/lib/es/list/item'
import { IconInfoCircle } from '@douyinfe/semi-icons'

const Index: React.FC = () => {
	const isAdmin = userStore((state) => state.isAdmin)

	const getCurrentUser = userStore((state) => state.getCurrentUser)

	const [pageData, setPageData] = useState({
		pageNumber: 1,
		pageSize: 10,
		totalRow: 0,
		records: []
	})

	const [sendMail, setSendMail] = useState(false)

	const handlePageChange = (pageNumber) => {
		setPageData({
			pageNumber: pageNumber,
			pageSize: pageData.pageSize,
			totalRow: pageData.totalRow,
			records: pageData.records
		})
	}

	const [showItem,setShowItem] = useState(false)
	const [itemList,setItemList] = useState([])
	const showItemList = (text) => {
		const list = []
		const itemList = JSON.parse(text.sendDetails).itemList;
		itemList.map((v, i) => {
			list.push(v)
		})
		setItemList(list)
		setShowItem(true)
	}

	const columns = [
		{
			title: '标题',
			render: (text, record, index) => {
				return JSON.parse(text.sendDetails).title
			}
		},
		{
			title: '正文',
			render: (text, record, index) => {
				return JSON.parse(text.sendDetails).content
			}
		},
		{
			title: '物品',
			render: (text, record, index) => {
				return <Button onClick={()=>showItemList(text)}>查看</Button>
			}
		},
		{
			title: '发送时间',
			dataIndex: 'createTime'
		}
	]

	const pageRequest = () => {
		getGameMailSendLog({ pageNumber: pageData.pageNumber, pageSize: pageData.pageSize }).then((res) => {
			setPageData(res)
		})
	}

	useEffect(() => {
		if (!sendMail) {
			getCurrentUser()
			pageRequest()
		}
	}, [JSON.stringify(pageData.pageNumber), sendMail])

	return (
		<Card>
			<Button onClick={() => setSendMail(true)}>发送邮件</Button>
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
			<SendMail show={sendMail} setShow={setSendMail}></SendMail>
			<Modal visible={showItem} onCancel={()=>setShowItem(false)} onOk={()=>setShowItem(false)}>
				<List
					header={'物品信息'}
					dataSource={itemList}
					renderItem={(item, index) =>
                        <List.Item>{'物品:'+item[0]+',数量:'+item[1]}</List.Item>
                    }
				/>
			</Modal>
		</Card>
	)
}

export default Index

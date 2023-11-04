import { ArrayField, Button, Col, Form, Modal, Row, Space, Toast, useFormApi } from '@douyinfe/semi-ui'
import Section from '@douyinfe/semi-ui/lib/es/form/section'
import React, { useEffect, useMemo, useState } from 'react'
import { sendGameMemail } from '@src/api/gameMail'
import { getItemList } from '@src/api/gameItem'
import { debounce } from 'lodash-es';
import { allRoleList } from '@src/api/roleApi'

const { Input, Select, TextArea } = Form

const SendMail = (props) => {

	// let formApi = useFormApi()

	const [formApi,setFormApi] = useState();


	const [selectList,setSelectList] = useState([]);

	const itemHandleSearch = (inputValue) => {
		if(inputValue){
			getItemList(inputValue).then((res) => {
				const list =[]
				res.map((v, i) => {
					list.push(
						{ key:i, value:v.id, label: v.name, type: 1 }
					)
				})
				setSelectList(list)
			})
		}
	}

	const [roleSelectList,setRoleSelectList] = useState([]);


	useEffect(()=>{
			allRoleList().then((res) => {
				const list =[]
				res.map((v, i) => {
					list.push(
						{ key:i, value:v.characNo, label: v.characName, type: 1 }
					)
				})
				setRoleSelectList(list)
			})
	},[JSON.stringify(props)])



	const sendSubmit = () => {
		sendGameMemail(formApi.getValues()).then((res) => {
			Toast.success('发送成功')
			props.setShow(false)
		})
	}

	return (
		<>
			<Modal visible={props.show} onOk={sendSubmit} onCancel={() => props.setShow(false)}>
				<Form getFormApi={setFormApi}>
					<Section text={'基本信息'}>
						<Select
													style={{ width: '150px' }}
													filter
													field="characNo"
													label={'角色'}
													optionList={roleSelectList}
													emptyContent={null}
												/>
						<Input required={true} field="title" initValue={'dnf-admin'} label="邮件标题" />
						<TextArea
							required={true}
							field="content"
							initValue={'dnf-admin'}
							label={{ text: '正文', required: true }}
						/>
						<Input required={true} field="gold" initValue={0} type="number" label="金币" />
					</Section>
					<Section text={'物品信息'}>
						<Form />
						<ArrayField field="itemList">
							{({ add, arrayFields }) => (
								<React.Fragment>
									<Button onClick={add} theme="light">
										添加
									</Button>
									{arrayFields.map(({ field, key, remove }, i) => (
										<div key={key}>
											<Space spacing={'loose'}>
												<Select
													style={{ width: '150px' }}
													filter
													field={`${field}[0]`}
													label={'物品'}
													remote={true}
													onSearch={debounce(itemHandleSearch, 1000)}
													optionList={selectList}
													emptyContent={null}
												/>
												<Input width={'15%'} required={true} field={`${field}[1]`} type="number" label={'数量'} />
												<Button type="danger" theme="borderless" onClick={remove} style={{ margin: 12 }}>
													删除
												</Button>
											</Space>
										</div>
									))}
								</React.Fragment>
							)}
						</ArrayField>
					</Section>
				</Form>
			</Modal>
		</>
	)
}

export default SendMail

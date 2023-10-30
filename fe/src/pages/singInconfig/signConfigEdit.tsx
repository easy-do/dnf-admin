import { ArrayField, Button, Col, Form, Modal, Row, Space, Toast, useFormApi } from '@douyinfe/semi-ui'
import Section from '@douyinfe/semi-ui/lib/es/form/section'
import React, { useEffect, useRef, useState } from 'react'
import signInStore from '@src/store/signIn'
import { updateConfigRequest } from '@src/api/signInApi'
import { getItemList } from '@src/api/gameItem'
import { debounce } from 'lodash-es';

const { Input, DatePicker, TextArea, Select } = Form

const SingInConfigEdit = () => {
	const getSignInInfo = signInStore((state) => state.getSignInInfo)

	const setSignInInfo = signInStore((state) => state.setSignInInfo)

	const cureentSignIn = signInStore((state) => state.cureentSignIn)

	const editId = signInStore((state) => state.editId)

	const editShow = signInStore((state) => state.editShow)

	const setEditShow = signInStore((state) => state.setEditShow)

	let formApi = useFormApi()

	const setFormApi = (api) => {
		formApi = api
	}

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

	const configSubmit = () => {
		let cumitData = cureentSignIn
		cumitData.configJson = JSON.stringify(cumitData.configJson)
		updateConfigRequest(cumitData).then((res) => {
			if (res) {
				Toast.success('配置成功')
				setEditShow(false)
			}
		})
	}

	const changeForm = (form) => {
		setSignInInfo(form.values)
	}

	useEffect(() => {
		if (editShow) {
			// getItemList('').then((res) => {
			// 	const list =[]
			// 	res.map((v, i) => {
			// 		list.push(
			// 			{ key:i, value:v.id, label: v.name, type: 1 }
			// 		)
			// 	})
			// 	setSelectList(list)
			// })
			getSignInInfo(editId).then((res) => {
				formApi.setValues(res, { isOverride: true })
			})
		}
	}, [editShow, editId])


	return (
		<>
			<Modal
				visible={editShow}
				onOk={configSubmit}
				style={{ minWidth: '50%', minHeight: '80%' }}
				onCancel={() => setEditShow(false)}
			>
				<Form onChange={changeForm} id={'edit-form'} getFormApi={setFormApi}>
					<Section text={'基本信息'}>
						<Input field="configName" label="配置名称" />
						<DatePicker
							initValue={cureentSignIn.configDate}
							field="configDate"
							type="date"
							label={{ text: '开始时间', required: true }}
						/>
						<TextArea field="reanrk" label={{ text: '备注', required: true }} />
					</Section>
					<Section text={'物品配置'}>
						<Form />
						<Select
							field={'configJson.type'}
							label={'签到类型'}
							initValue={0}
							optionList={[{ label: '默认类型', value: 0 }]}
						></Select>
						<ArrayField field="configJson.data">
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
													field={'${field}[itemId]'}
													label={'物品'}
													remote={true}
													onSearch={debounce(itemHandleSearch, 1000)}
													optionList={selectList}
													emptyContent={null}
												/>
												<Input width={'15%'} field={`${field}[quantity]`} type="number" label={'数量'} />
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

export default SingInConfigEdit

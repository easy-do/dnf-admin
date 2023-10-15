import { ArrayField, Button, Col, Form, Modal, Row, Space, Toast, useFormApi } from '@douyinfe/semi-ui'
import Section from '@douyinfe/semi-ui/lib/es/form/section'
import React, { useEffect, useRef } from 'react'
import signInStore from '@src/store/signIn'
import { updateConfigRequest } from '@src/api/signInApi'

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
			getSignInInfo(editId).then((res) => {
				console.log('getSignInInfo')
				formApi.setValues(res, { isOverride: true })
				console.log(res, 'set configJson.data')
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
												<Input width={'15%'} field={`${field}[name]`} label={'显示名称'} />
												<Select
													style={{minWidth:'150px'}}
													field={`${field}[itemType]`}
													label={'物品类型'}
													initValue={1}
													optionList={[
														{ label: '装备', value: 1 },
														{ label: '消耗品', value: 2 },
														{ label: '材料', value: 3 },
														{ label: '任务材料', value: 4 },
														{ label: '宠物', value: 5 },
														{ label: '宠物装备', value: 6 },
														{ label: '宠物消耗品', value: 7 },
														{ label: '时装', value: 8 },
														{ label: '副职业', value: 9 }
													]}
												/>
												<Input width={'15%'} field={`${field}[itemId]`} type="number" min={1} label={'物品id'} />
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

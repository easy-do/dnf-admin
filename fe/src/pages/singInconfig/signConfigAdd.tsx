import { ArrayField, Button, Col, Form, Modal, Row, Space, Toast, useFormApi } from '@douyinfe/semi-ui'
import Section from '@douyinfe/semi-ui/lib/es/form/section'
import React, { useEffect, useRef, useState } from 'react'
import signInStore from '@src/store/signIn'
import { inertConfigRequest } from '@src/api/signInApi'
import { debounce } from 'lodash-es';
import { getItemList } from '@src/api/gameItem'

const { Input, DatePicker, TextArea, Select } = Form

const SingInConfigAdd = () => {

	const [selectList,setSelectList] = useState([]);
	
	const itemHandleSearch = (inputValue) => {
		if(inputValue){
			getItemList(inputValue).then((res) => {
				const itemMap = {}

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

	const setSignInInfo = signInStore((state) => state.setSignInInfo)

	const cureentSignIn = signInStore((state) => state.cureentSignIn)

	const addShow = signInStore((state) => state.addShow)

	const setAddShow = signInStore((state) => state.setAddShow)

	let formApi = useFormApi()

	const setFormApi = (api) => {
		formApi = api
	}

	const configSubmit = () => {
		let cumitData = cureentSignIn
		cumitData.configJson = JSON.stringify(cumitData.configJson)
		inertConfigRequest(cumitData).then((res) => {
			if (res) {
				Toast.success('添加成功')
				setAddShow(false)
			}
		})
	}

	const changeForm = (form) => {
		setSignInInfo(form.values)
	}

	return (
		<>
			<Modal
				visible={addShow}
				onOk={configSubmit}
				style={{ minWidth: '50%', minHeight: '80%' }}
				onCancel={() => setAddShow(false)}
			>
				<Form onChange={changeForm} id={'edit-form'} getFormApi={setFormApi}>
					<Section text={'基本信息'}>
						<Input field="configName" label="配置名称" />
						<DatePicker
							initValue={cureentSignIn.configDate}
							field="configDate"
							type="date"
							label={{ text: '签到时间', required: true }}
						/>
						<TextArea field="remark" label={{ text: '备注', required: true }} />
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
													field={`${field}[itemId]`}
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

export default SingInConfigAdd

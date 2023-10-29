import { ArrayField, Button, Col, Form, Modal, Row, Space, Toast, useFormApi } from '@douyinfe/semi-ui'
import Section from '@douyinfe/semi-ui/lib/es/form/section'
import React from 'react'
import { sendGameMemail } from '@src/api/gameMail'

const { Input, TextArea } = Form

const SendMail = (props) => {



	let formApi = useFormApi()

	const setFormApi = (api) => {
		formApi = api
	}

	const sendSubmit = () => {
		sendGameMemail(formApi.getValues()).then((res) => {
			Toast.success('发送成功')
			props.setShow(false)
		})
	}

	return (
		<>
			<Modal
				visible={props.show}
				onOk={sendSubmit}
				style={{ minWidth: '50%', minHeight: '80%' }}
				onCancel={() => props.setShow(false)}
			>
				<Form getFormApi={setFormApi}>
					<Section text={'基本信息'}>
                        <Input required={true} field="characNo" label="角色编号" />
						<Input required={true} field="title" initValue={'dnf-admin'} label="邮件标题" />
						<TextArea required={true} field="content" initValue={'dnf-admin'} label={{ text: '正文', required: true }} />
                        <Input required={true} field="gold" initValue={0} type='number' label="金币" />
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
												<Input width={'15%'} required={true} field={`${field}[0]`} type="number" min={1} label={'物品id'} />
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

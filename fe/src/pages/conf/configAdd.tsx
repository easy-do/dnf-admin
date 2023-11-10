import { Form, Modal, Toast } from '@douyinfe/semi-ui'
import React, { useState } from 'react'
import { useFormApi } from '@douyinfe/semi-ui/lib/es/form'
import { saveConfRequest } from '@src/api/confApi'

const ConfigEdit = (props) => {
	let formApi = useFormApi()

	const setFormApi = (api) => {
		formApi = api
	}

	const [confType, setConfType] = useState(0)

	const onChangeConfType = (value) => {
		setConfType(value)
	}

	const configSubmit = () => {
		saveConfRequest(formApi.getValues()).then((res) => {
			if (res) {
				Toast.success('配置成功')
				props.setAddFlag(false)
				props.handlePageChange(1)
			}
		})
	}

	return (
		<>
			<Modal visible={props.addFlag} onOk={configSubmit} onCancel={() => props.setAddFlag(false)}>
				<Form getFormApi={setFormApi}>
					<Form.Input field="confName" label="配置名称" />
					<Form.Select initValue={1} onChange={onChangeConfType} field="confType" label="配置类型">
						<Form.Select.Option value={1}>数值</Form.Select.Option>
						<Form.Select.Option value={2}>字符串</Form.Select.Option>
						<Form.Select.Option value={3}>是否</Form.Select.Option>
						<Form.Select.Option value={4}>JSON</Form.Select.Option>
					</Form.Select>
					<Form.Input field="confKey" label="配置标签" />

					{confType == 1 ? <Form.Input type="number" field="confData" label="配置参数" /> : null}
					{confType == 2 ? <Form.Input field="confData" label="配置参数" /> : null}
					{confType == 3 ? (
						<Form.Select initValue={'false'} field="confData" label="配置类型">
							<Form.Select.Option value={'true'}>是</Form.Select.Option>
							<Form.Select.Option value={'false'}>否</Form.Select.Option>
						</Form.Select>
					) : null}
					{confType == 4 ? <Form.TextArea field="confData" label="配置参数" /> : null}
					<Form.Input field="remark" label="备注" />
				</Form>
			</Modal>
		</>
	)
}

export default ConfigEdit

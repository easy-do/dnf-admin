import { Form, Modal, Toast } from '@douyinfe/semi-ui'
import React from 'react'
import { useFormApi } from '@douyinfe/semi-ui/lib/es/form'
import {  saveConfRequest } from '@src/api/confApi'

const ConfigEdit = (props) => {



	let formApi = useFormApi()

	const setFormApi = (api) => {
		formApi = api
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
			<Modal
				visible={props.addFlag}
				onOk={configSubmit}
				onCancel={() => props.setAddFlag(false)}
			>
				<Form getFormApi={setFormApi}>
						<Form.Input  field="confName" label="配置名称" />
						<Form.Input  field="confKey" label="配置标签" />
						<Form.Input  field="confData" label="配置参数" />
						<Form.Input  field="remark" label="备注" />
				</Form>
			</Modal>
		</>
	)
}

export default ConfigEdit

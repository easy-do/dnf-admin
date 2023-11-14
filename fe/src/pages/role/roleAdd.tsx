import { Form, Modal, Toast } from '@douyinfe/semi-ui'
import React, { useRef } from 'react'
import { saveRoleRequest } from '@src/api/roleApi'

const RoleEdit = (props) => {
	const formRef = useRef()

	const configSubmit = () => {
		saveRoleRequest(formRef.current.formApi.getValues()).then((res) => {
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
				<Form ref={formRef}>
					<Form.Input field="roleName" label="角色名称" />
					<Form.Input field="roleKey" label="角色标识" />
					<Form.Input field="roleSort" label="排序" />
					<Form.Switch defaultValue={false} field="isDefault" label="默认角色" />
					<Form.Switch defaultValue={true} field="status" label="启用角色" />
					<Form.Input field="remark" label="备注" />
				</Form>
			</Modal>
		</>
	)
}

export default RoleEdit

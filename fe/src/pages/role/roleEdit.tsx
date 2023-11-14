import { Form, Modal, Toast } from '@douyinfe/semi-ui'
import React, { useEffect, useRef, useState } from 'react'
import { roleInfoRequest, updateRoleRequest } from '@src/api/roleApi'

const RoleEdit = (props) => {
	const formRef = useRef()

	const configSubmit = () => {
		updateRoleRequest(formRef.current.formApi.getValues()).then((res) => {
			if (res) {
				Toast.success('配置成功')
				props.setEditShow(false)
				props.handlePageChange(1)
			}
		})
	}

	useEffect(() => {
		if (props.editShow) {
			roleInfoRequest(props.editId).then((res) => {
				formRef.current.formApi.setValues(res, { isOverride: true })
				setConfType(res.confType)
			})
		}
	}, [props.editShow, props.editId])

	return (
		<>
			<Modal visible={props.editShow} onOk={configSubmit} onCancel={() => props.setEditShow(false)}>
				<Form ref={formRef}>
					<Form.Input field="roleName" label="角色名称" />
					<Form.Input field="roleKey" label="角色标识" />
					<Form.Input field="roleSort" label="排序" />
					<Form.Switch defaultValue={false} field="isDefault" label="默认角色" />
					<Form.Switch defaultValue={false} field="status" label="启用角色" />
					<Form.Input field="remark" label="备注" />
				</Form>
			</Modal>
		</>
	)
}

export default RoleEdit

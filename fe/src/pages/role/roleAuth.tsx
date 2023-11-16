import { Form, Modal, Toast, Tree } from '@douyinfe/semi-ui'
import React, { useEffect, useState } from 'react'
import { resourceTreeRequest, authRoleResourceRequest, roleResourceIdsRequest } from '@src/api/resourceApi'



const RoleAuth = (props) => {


	const [treeData,setTreeDara] = useState([])

	const [defaultSelect,setDefaultSelect] = useState([])
	

	const configSubmit = () => {
		authRoleResourceRequest({"roleId":props.authRoleId,"resourceIds":defaultSelect}).then((res) => {
			if (res) {
				Toast.success('配置成功')
				props.setAuthShow(false)
				props.handlePageChange(1)
			}
		})
	}

	useEffect(() => {
		if (props.authShow) {
			resourceTreeRequest().then((res) => {
				setTreeDara(res)
			})
			roleResourceIdsRequest(props.authRoleId).then((res) => {
				setDefaultSelect(res)
			})
		}
	}, [props.authShow, props.authRoleId])

	return (
		<>
			<Modal visible={props.authShow} onOk={configSubmit} onCancel={() => props.setAuthShow(false)}>
				<Tree
					treeData={treeData}
					value={defaultSelect}
					multiple
					checkRelation='unRelated'
					defaultExpandAll
					onChange={setDefaultSelect}
				/>
			</Modal>
		</>
	)
}

export default RoleAuth

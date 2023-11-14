import { Form, Modal, Toast, Tree } from '@douyinfe/semi-ui'
import React, { useEffect, useState } from 'react'
import { resourceTreeRequest, authRoleResourceRequest, roleResourceIdsRequest } from '@src/api/resourceApi'



const RoleAuth = (props) => {


	const [treeData,setTreeDara] = useState([])

	const [roleResource,setRoleResource] = useState([])
	

	const configSubmit = () => {
		authRoleResourceRequest({"roleId":props.authRoleId,"resourceIds":roleResource}).then((res) => {
			if (res) {
				Toast.success('配置成功')
				props.setAuthShow(false)
				props.handlePageChange(1)
			}
		})
	}

	useEffect(() => {
		if (props.authShow) {
			roleResourceIdsRequest(props.authRoleId).then((res) => {
				setRoleResource(res)
			})
			resourceTreeRequest().then((res) => {
				setTreeDara(res)
			})
		}
	}, [props.authShow, props.authRoleId])

	return (
		<>
			<Modal visible={props.authShow} onOk={configSubmit} onCancel={() => props.setAuthShow(false)}>
				<Tree
					treeData={treeData}
					defaultValue={roleResource}
					multiple
					checkRelation='unRelated'
					defaultExpandAll
					onChange={(value)=>setRoleResource(value)}
				/>
			</Modal>
		</>
	)
}

export default RoleAuth

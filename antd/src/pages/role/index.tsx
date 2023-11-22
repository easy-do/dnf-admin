import { Button, message } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { pageRole, saveRole, updateRole } from '@/services/dnf-admin/daRoleController';
import { ModalForm, ProFormCheckbox, ProFormSelect, ProFormText, ProFormTreeSelect } from '@ant-design/pro-form';
import { PlusOutlined } from '@ant-design/icons';
import { authRoleResource, resourceTree, roleResourceIds } from '@/services/dnf-admin/daResourceController';
import { Access, useAccess } from 'umi';

const RoleList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaRole>();
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const [authRoleModalVisible, handleAuthRoleModalVisible] = useState<boolean>(false);
  const [currentRowSelect, setCurrentRowSelect] = useState<number[]>([]);
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const access = useAccess();

  /**
   * 添加节点
   *
   * @param fields
   */
  const handleAdd = async (fields: API.DaRole) => {
    const hide = message.loading('正在添加');

    try {
      await saveRole({ ...fields });
      hide();
      message.success('添加成功');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('添加失败请重试！');
      return false;
    }
  };
  /**
   * 更新节点
   *
   * @param fields
   */

  const handleUpdate = async (currentRow?: API.DaRole) => {
    const hide = message.loading('正在配置');

    try {
      await updateRole({
        ...currentRow,
      });
      hide();
      message.success('配置成功');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('配置失败请重试！');
      return false;
    }
  };

  /**
   * 角色授权
   *
   * @param fields
   */
  const authRole = async (values?: API.AuthRoleResourceDto) => {
    const hide = message.loading('正在配置');

    try {
      const resourceIds = [];
      values?.resourceIds.map((v,i)=>{
        resourceIds.push(v.value);
      })
      await authRoleResource({roleId:values?.roleId,resourceIds:resourceIds});
      hide();
      message.success('配置成功');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('配置失败请重试！');
      return false;
    }
  };

  /** 国际化配置 */

  const columns: ProColumns<API.DaRole>[] = [
    {
      title: '角色名称',
      dataIndex: 'roleName',
    },
    {
      title: '权限字符串',
      dataIndex: 'roleKey',
    },
    {
      title: '默认角色',
      dataIndex: 'isDefault',
      hideInForm: true,
      valueEnum: {
        false: {
          text: '否',
          status: 'Error',
        },
        true: {
          text: '是',
          status: 'Success',
        },
      },
    },
    {
      title: '备注',
      dataIndex: 'remark',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Access accessible={access.hashPre('role.authRoleResource')}>
          <a
            key="id"
            onClick={() => {
              if (record.roleKey == 'admin') {
                message.error('不能修改管理员角色！');
              } else {
                roleResourceIds({ roleId: record.id }).then((res) => {
                  setCurrentRowSelect(res.data)
                  setCurrentRow(record);
                  handleAuthRoleModalVisible(true);
                })
              }
            }}
          >
            授权
          </a>
        </Access>,
        <Access accessible={access.hashPre('role.update')}>
          <a 
            key="id"
            onClick={() => {
              if (record.roleKey == 'admin') {
                message.error('不能修改管理员角色！');
              } else {
                setCurrentRow(record);
                handleUpdateModalVisible(true);
              }
            }}
          >
            编辑
          </a>
        </Access>,

      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaRole, API.DaRoleQo>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        request={pageRole}
        columns={columns}
        toolBarRender={() => [
          <Access accessible={access.hashPre('role.save')}>
            <Button
              type="primary"
              key="primary"
              onClick={() => {
                handleModalVisible(true);
              }}
            >
              <PlusOutlined /> 新建
            </Button>
          </Access>,
        ]}
      />
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="添加角色"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.DaRole);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="roleName"
          label="角色名称"
          rules={[
            {
              required: true,
              message: '请输入角色名称！',
            },
          ]}
        />
        <ProFormText
          name="roleKey"
          label="权限字符串"
          rules={[
            {
              required: true,
              message: '请输入配置标签！',
            },
          ]}
        />
        <ProFormCheckbox name="isDefault" label="是否默认角色" />
        <ProFormText
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入备注！',
            },
          ]}
        />
      </ModalForm>
      <ModalForm
        title="编辑角色"
        visible={updateModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        onVisibleChange={handleUpdateModalVisible}
        onFinish={handleUpdate}
      >
        <ProFormText name="id" hidden />
        <ProFormText
          name="roleName"
          label="角色名称"
          rules={[
            {
              required: true,
              message: '请输入角色名称！',
            },
          ]}
        />
        <ProFormText
          name="roleKey"
          label="权限字符串"
          rules={[
            {
              required: true,
              message: '请输入配置标签！',
            },
          ]}
        />
        <ProFormCheckbox name="isDefault" label="是否默认角色" />
        <ProFormText
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入备注！',
            },
          ]}
        />
      </ModalForm>
      <ModalForm
        title="角色授权"
        visible={authRoleModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        onVisibleChange={handleAuthRoleModalVisible}
        onFinish={authRole}
      >
        <ProFormText name="roleId" initialValue={currentRow?.id} hidden />
        <ProFormTreeSelect
          name="resourceIds"
          label="权限列表"
          request={() => resourceTree().then((res) => res.data)}
          initialValue={currentRowSelect}
          fieldProps={{
            treeCheckable: true,
            treeCheckStrictly:true,
            labelInValue: false,
            autoClearSearchValue: true,
            multiple: true,
            fieldNames: {
              label: 'name',
              value: 'id',
            },
          }}
        />
      </ModalForm>
    </PageContainer>
  );
};

export default RoleList;

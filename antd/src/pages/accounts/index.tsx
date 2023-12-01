import { Button, message } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { ModalForm, ProFormSelect, ProFormText } from '@ant-design/pro-form';
import { PlusOutlined } from '@ant-design/icons';
import { Access, useAccess } from 'umi';
import { disableAccounts, enableAccounts, pageAccounts, resetPassword, saveAccounts, updateAccounts } from '@/services/dnf-admin/accountsController';
import { rechargeBonds } from '@/services/dnf-admin/accountsController';

const Accounts: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.Accounts>();
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const [restPassModalVisible, handleResetPassModalVisible] = useState<boolean>(false);
  const [rechargeModalVisible, handleRechargeModalVisible] = useState<boolean>(false);
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const access = useAccess();
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.RegDto) => {
    const hide = message.loading('正在添加');

    try {
      await saveAccounts({ ...fields });
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

  const handleUpdate = async (currentRow?: API.Accounts) => {
    const hide = message.loading('正在配置');

    try {
      await updateAccounts({
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
   * 重置密码
   *
   * @param fields
   */

  const handleResetPass = async (fields) => {
    const hide = message.loading('正在配置');

    try {
      await resetPassword({
        'uid': currentRow.uid,
        'password': fields.password
      });
      hide();
      message.success('重置成功');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('重置失败请重试！');
      return false;
    }
  };

  /**
 * 充值
 *
 * @param fields
 */

  const handleRecharge = async (fields) => {

    const hide = message.loading('正在充值');
    try {
      await rechargeBonds({
        'uid': currentRow.uid,
        ...fields
      });
      hide();
      message.success('重置成功');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('重置失败请重试！');
      return false;
    }
  };

  /** 国际化配置 */

  const columns: ProColumns<API.Accounts>[] = [
    {
      title: 'UID',
      dataIndex: 'uid',
    },
    {
      title: '账号',
      dataIndex: 'accountname',
    },
    {
      title: 'QQ',
      dataIndex: 'qq',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Access accessible={access.hashPre('accounts.update')}>
          <a
            key="id"
            onClick={() => {
              setCurrentRow(record);
              handleUpdateModalVisible(true);
            }}
          >
            编辑
          </a>
        </Access>
        ,
        <Access accessible={access.hashPre('accounts.resetPass')}>
          <a
            key="id"
            onClick={() => {
              setCurrentRow(record);
              handleResetPassModalVisible(true);
            }}
          >
            重置密码
          </a>
        </Access>
        ,
        <Access accessible={access.hashPre('accounts.recharge')}>
        <a
          key="id"
          onClick={() => {
            setCurrentRow(record);
            handleRechargeModalVisible(true);
          }}
        >
          充值
        </a>
      </Access>
      ,
        <Access accessible={access.hashPre('accounts.disable')}>
          <a
            key="id"
            onClick={() => {
              disableAccounts({ "uid": record.uid }).then(res => {
                if (res.success) {
                  message.success(res.message)
                } else {
                  message.warn(res.errorMessage)
                }
              })
            }}
          >
            封号
          </a>
        </Access>
        ,
        <Access accessible={access.hashPre('accounts.enable')}>
          <a
            key="id"
            onClick={() => {
              enableAccounts({ "uid": record.uid }).then(res => {
                if (res.success) {
                  message.success(res.message)
                } else {
                  message.warn(res.errorMessage)
                }
              })
            }}
          >
            解封
          </a>
        </Access>
        ,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.Accounts, API.AccountsQo>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="uid"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: false,
        }}
        request={pageAccounts}
        columns={columns}
        toolBarRender={() => [
          <Access accessible={access.hashPre('conf.update')}>
            <Button
              type="primary"
              key="primary"
              onClick={() => {
                handleModalVisible(true);
              }}
            >
              <PlusOutlined /> 新建
            </Button>
          </Access>
          ,
        ]}
      />
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="添加账号"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.Accounts);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="userName"
          label="账号"
          rules={[
            {
              required: true,
              message: '请输入账号！',
            },
          ]}
        />
        <ProFormText
          name="password"
          label="密码"
          rules={[
            {
              required: true,
              message: '请输入密码！',
            },
          ]}
        />
      </ModalForm>
      <ModalForm
        title="编辑账号"
        visible={updateModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        onVisibleChange={(value) => {
          handleUpdateModalVisible(value);
        }}
        onFinish={handleUpdate}
      >
        <ProFormText name="uid" hidden />
        <ProFormText
          name="accountname"
          label="账号名"
          rules={[
            {
              required: true,
              message: '请输入账号名！',
            },
          ]}
        />
        <ProFormText
          name="qq"
          label="qq"
        />
      </ModalForm>
      <ModalForm
        title="重置密码"
        visible={restPassModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        onVisibleChange={(value) => {
          handleResetPassModalVisible(value);
        }}
        onFinish={handleResetPass}
      >
        <ProFormText name="uid" hidden />
        <ProFormText
          name="password"
          label="新密码"
          rules={[
            {
              required: true,
              message: '请输入新密码！',
            },
          ]}
        />
      </ModalForm>
      <ModalForm
        title="充值"
        visible={rechargeModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        onVisibleChange={(value) => {
          handleRechargeModalVisible(value);
        }}
        onFinish={handleRecharge}
      >
        <ProFormText name="uid" hidden />
        <ProFormSelect
          name="type"
          label="充值类型"
          initialValue={1}
          valueEnum={{
            1: '代币券',
            2: '点券',
          }}
        />
        <ProFormText
          name="count"
          label="数量"
          rules={[
            {
              required: true,
              message: '请输入数量！',
            },
          ]}
        />
      </ModalForm>
    </PageContainer>
  );
};

export default Accounts;

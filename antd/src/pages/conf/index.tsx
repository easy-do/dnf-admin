import { Button, message } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { pageConf, saveConf, updateConf } from '@/services/dnf-admin/daGameConfigController';
import { ModalForm, ProFormSelect, ProFormText } from '@ant-design/pro-form';
import { PlusOutlined } from '@ant-design/icons';

const ConfList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaGameConfig>();
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.DaGameConfig) => {
    const hide = message.loading('正在添加');

    try {
      await saveConf({ ...fields });
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

  const handleUpdate = async (currentRow?: API.DaGameConfig) => {
    const hide = message.loading('正在配置');

    try {
      await updateConf({
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

  /** 国际化配置 */

  const columns: ProColumns<API.DaGameConfig>[] = [
    {
      title: '名称',
      dataIndex: 'confName',
    },
    {
      title: '标签',
      dataIndex: 'confKey',
    },
    {
      title: '类型',
      dataIndex: 'confType',
      hideInForm: true,
      valueEnum: {
        1: {
          text: '数值',
        },
        2: {
          text: '字符串',
        },
        3: {
          text: '是/否',
        },
        4: {
          text: 'JSON',
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
        <a
          key="id"
          onClick={() => {
            setCurrentRow(record);
            handleUpdateModalVisible(true);
          }}
        >
          编辑
        </a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaGameConfig, API.DaGameConfigQo>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        request={pageConf}
        columns={columns}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              handleModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
      />
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="添加配置"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.DaGameConfig);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="confName"
          label="配置名称"
          rules={[
            {
              required: true,
              message: '请输入配置名称！',
            },
          ]}
        />
        <ProFormSelect
          name="confType"
          label="配置类型"
          options={[
            {
              label: '数值',
              value: 1,
            },
            {
              label: '字符串',
              value: 2,
            },
            {
              label: '是/否',
              value: 3,
            },
            {
              label: 'JSON',
              value: 4,
            },
          ]}
        />
        <ProFormText
          name="confKey"
          label="配置标签"
          rules={[
            {
              required: true,
              message: '请输入配置标签！',
            },
          ]}
        />
        <ProFormText
          name="confData"
          label="配置参数"
          rules={[
            {
              required: true,
              message: '配置参数！',
            },
          ]}
        />
        <ProFormText
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入配置名称！',
            },
          ]}
        />
      </ModalForm>
      <ModalForm
        title="编辑配置"
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
          name="confName"
          label="配置名称"
          rules={[
            {
              required: true,
              message: '请输入配置名称！',
            },
          ]}
        />
        <ProFormSelect
          name="confType"
          label="配置类型"
          options={[
            {
              label: '数值',
              value: 1,
            },
            {
              label: '字符串',
              value: 2,
            },
            {
              label: '是/否',
              value: 3,
            },
            {
              label: 'JSON',
              value: 4,
            },
          ]}
        />
        <ProFormText
          name="confKey"
          label="配置标签"
          rules={[
            {
              required: true,
              message: '请输入配置标签！',
            },
          ]}
        />
        <ProFormText
          name="confData"
          label="配置参数"
          rules={[
            {
              required: true,
              message: '配置参数！',
            },
          ]}
        />
        <ProFormText
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入配置名称！',
            },
          ]}
        />
      </ModalForm>
    </PageContainer>
  );
};

export default ConfList;

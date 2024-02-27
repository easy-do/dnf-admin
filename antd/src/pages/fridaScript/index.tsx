import { Button, message } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { ModalForm, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { PlusOutlined } from '@ant-design/icons';
import { Access, useAccess } from 'umi';
import {
  getFridaScriptInfo,
  pageFridaScript,
  saveFridaScript,
  updateFridaScript,
} from '@/services/dnf-admin/daFridaScriptController';
import Modal from 'antd/lib/modal/Modal';
import { listFridaFunction } from '@/services/dnf-admin/daFridaFunctionController';

import loader from '@monaco-editor/loader';
import Editor from '@monaco-editor/react';
loader.config({ paths: { vs: 'https://cdn.staticfile.org/monaco-editor/0.43.0/min/vs' } });

const ConfList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaFridaScript>();
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const access = useAccess();

  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.DaFridaScript) => {
    const hide = message.loading('正在添加');

    try {
      const childrenFunction = fields.childrenFunction ? fields.childrenFunction.join(',') : '';

      await saveFridaScript({ ...fields, childrenFunction: childrenFunction });
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

  const openUpdateModal = (id: number) => {
    getFridaScriptInfo({ id: id }).then((res) => {
      if (res.success) {
        const childrenFunction = res.data.childrenFunction
          ? res.data.childrenFunction.split(',').map(Number)
          : [];
        setCurrentRow({ ...res.data, childrenFunction: childrenFunction });
        handleUpdateModalVisible(true);
      }
    });
  };
  /**
   * 更新节点
   *
   * @param fields
   */

  const handleUpdate = async (fields?: API.DaFridaScript) => {
    const hide = message.loading('正在配置');

    try {
      const childrenFunction = fields.childrenFunction ? fields.childrenFunction.join(',') : '';
      await updateFridaScript({
        ...fields,
        childrenFunction: childrenFunction,
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
  const columns: ProColumns<API.DaFridaScript>[] = [
    {
      title: '脚本名',
      dataIndex: 'scriptName',
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
        <Access accessible={access.hashPre('fridaScript.update')}>
          <a
            key="id"
            onClick={() => {
              openUpdateModal(record.id);
            }}
          >
            编辑信息
          </a>
        </Access>,
        <Access accessible={access.hashPre('fridaScript.update')}>
          <a
            key="id"
            onClick={() => {
              setCurrentRow(record);
              openScriptEditor(record.id);
            }}
          >
            编辑脚本
          </a>
        </Access>,
      ],
    },
  ];

  const [updateScriptModalVisible, handleUpdateScriptModalVisible] = useState<boolean>(false);
  const [confirmLoading, setConfirmLoading] = useState<boolean>(false);
  const [cureentScriptContext, setCureentScriptContext] = useState<string>('// 什么都没有');

  const handleEditorChange = (value: string, event: any) => {
    setCureentScriptContext(value);
  };

  const openScriptEditor = (id: number) => {
    getFridaScriptInfo({ id: id }).then((res) => {
      if (res.success) {
        setCureentScriptContext(res.data?.scriptContext);
        setCurrentRow(res.data);
        handleUpdateScriptModalVisible(true);
      }
    });
  };

  const editorOK = () => {
    setConfirmLoading(true);
    updateFridaScript({ id: currentRow.id, scriptContext: cureentScriptContext }).then((res) => {
      if (res.success) {
        message.success('保存成功');
        setCureentScriptContext('');
        setCurrentRow(undefined);
        handleUpdateScriptModalVisible(false);
      }
      setConfirmLoading(false);
    });
  };

  return (
    <PageContainer>
      <ProTable<API.DaFridaScript, API.FridaFunctionQo>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: false,
        }}
        request={pageFridaScript}
        columns={columns}
        toolBarRender={() => [
          <Access accessible={access.hashPre('fridaScript.save')}>
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
        title="添加配置"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.DaFridaScript);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="scriptName"
          label="脚本名"
          rules={[
            {
              required: true,
              message: '请输入脚本名！',
            },
          ]}
        />
        <ProFormSelect
          name="childrenFunction"
          label="引入函数"
          mode="multiple"
          fieldProps={{
            suffixIcon: null,
            showSearch: true,
            labelInValue: false,
            autoClearSearchValue: true,
            fieldNames: {
              label: 'functionName',
              value: 'functionKey',
            },
          }}
          request={() =>
            listFridaFunction({}).then((res) => {
              return res.data;
            })
          }
        />
        <ProFormTextArea
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
        title="编辑配置"
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
        <ProFormText name="id" hidden />
        <ProFormText
          name="scriptName"
          label="脚本名"
          rules={[
            {
              required: true,
              message: '请输入脚本名！',
            },
          ]}
        />
        <ProFormTextArea
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
      <Modal
        style={{
          minHeight: '80%',
          minWidth: '70%',
        }}
        keyboard={false}
        open={updateScriptModalVisible}
        onOk={editorOK}
        onCancel={() => handleUpdateScriptModalVisible(false)}
        title="编辑脚本"
        destroyOnClose={true}
        maskClosable={false}
        centered
        okText="保存"
        cancelText="取消"
        confirmLoading={confirmLoading}
      >
        <Editor
          height="65vh"
          theme={'vs-dark'}
          width="100%"
          defaultValue={cureentScriptContext}
          onChange={handleEditorChange}
          defaultLanguage="javascript"
        />
      </Modal>
    </PageContainer>
  );
};

export default ConfList;

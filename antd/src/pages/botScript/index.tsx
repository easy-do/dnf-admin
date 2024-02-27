import { PlusOutlined } from '@ant-design/icons';
import { Button, Modal, message } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { Access, useAccess } from 'umi';
import { ModalForm, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { addBotScript, infoBotScript, pageBotScript, removeBotScript, updateBotScript } from '@/services/dnf-admin/daBotScriptController';
import loader from '@monaco-editor/loader';
import Editor from '@monaco-editor/react';
loader.config({ paths: { vs: 'https://cdn.staticfile.org/monaco-editor/0.43.0/min/vs' } });

const botScript: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaBotEventScript>();
  const access = useAccess();
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.DaBotEventScript) => {
    const hide = message.loading('正在添加');

    try {
      await addBotScript({ ...fields });
      hide();
      message.success('添加成功');
      return true;
    } catch (error) {
      hide();
      message.error('添加失败请重试！');
      return false;
    }
  };

  /** 编辑窗口的弹窗 */
  const [editModalVisible, handleEditModalVisible] = useState<boolean>(false);

  const openEditModal = (id: string) => {
    infoBotScript({ id: id }).then((res) => {
      if (res.success) {
        setCurrentRow(res.data);
        handleEditModalVisible(true);
      } else {
        message.warn(res.errorMessage);
      }
    });
  };

 /** 编辑脚本 */
 const [updateScriptModalVisible, handleUpdateScriptModalVisible] = useState<boolean>(false);
 const [confirmLoading, setConfirmLoading] = useState<boolean>(false);
  const [cureentScriptContext, setCureentScriptContext] = useState<string>('// 什么都没有');

  const handleEditorChange = (value: string, event: any) => {
    setCureentScriptContext(value);
  };

  const openScriptEditor = (id: string) => {
    infoBotScript({ id: id }).then((res) => {
      if (res.success) {
        setCureentScriptContext(res.data?.scriptContent);
        setCurrentRow(res.data);
        handleUpdateScriptModalVisible(true);
      }
    });
  };

  const editorOK = () => {
    setConfirmLoading(true);
    updateBotScript({ id: currentRow.id, scriptContent: cureentScriptContext }).then((res) => {
      if (res.success) {
        message.success('保存成功');
        // setCureentScriptContext('');
        // setCurrentRow(undefined);
        // handleUpdateScriptModalVisible(false);
      }
      setConfirmLoading(false);
    });
  };

  /** 国际化配置 */

  const columns: ProColumns<API.DaBotEventScript>[] = [
    {
      title: '脚本名',
      dataIndex: 'scriptName',
    },
    {
      title: '事件类型',
      dataIndex: 'eventType',
      search: false,
      valueEnum:{
        'QQ':'qq',
      }
    },
    {
      title: '脚本类型',
      dataIndex: 'scriptType',
      search: false,
      valueEnum:{
        'cqhttp':'cqhttp',
      }
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
        <Access accessible={access.hashPre('botScript.update')}>
        <a
          key="id"
          onClick={() => {
            openEditModal(record.id);
          }}
        >
          编辑信息
        </a>
      </Access>,
      <Access accessible={access.hashPre('botScript.update')}>
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
        <Access accessible={access.hashPre('botScript.remove')}>
          <a
            key="id"
            onClick={() => {
              removeBotScript([record.id]).then((res) => {
                actionRef.current.reload();
              });
            }}
          >
            删除
          </a>
        </Access>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaBotEventScript, API.RListDaBotEventScript>
        headerTitle="机器人脚本"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: false,
        }}
        toolBarRender={() => [
          <Access accessible={access.hashPre('item.save')}>
            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={() => {
                handleModalVisible(true);
              }}
            >
              添加脚本
            </Button>
          </Access>,
        ]}
        request={pageBotScript}
        columns={columns}
      />
      <ModalForm
        title="添加脚本"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.DaBotEventScript);
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
              message: '请输入脚本名',
            },
          ]}
        />
        <ProFormSelect
          name="eventType"
          label="事件类型"
          rules={[
            {
              required: true,
              message: '请选择事件类型',
            },
          ]}
          options={[
            {
              label: '接消息',
              value: 'message',
            },
            {
              label: '通知',
              value: 'notice',
            },
            {
              label: '元事件',
              value: 'meta_event',
            },
          ]}
        />
        <ProFormSelect
          name="scriptType"
          label="脚本类型"
          rules={[
            {
              required: true,
              message: '请选择脚本类型',
            },
          ]}
          options={[
            {
              label: 'AviatorScript',
              value: 'AviatorScript',
            },
          ]}
        />
        <ProFormTextArea
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入备注',
            },
          ]}
        />
      </ModalForm>
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="编辑机器人"
        visible={editModalVisible}
        initialValues={currentRow}
        onVisibleChange={handleEditModalVisible}
        onFinish={(value) => {
          value.scriptContent = undefined;
          updateBotScript(value).then((res) => {
            if (res.success) {
              message.success('修改成功');
              handleEditModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }
          });
        }}
      >
        <ProFormText hidden name="id" />
        <ProFormText
          name="scriptName"
          label="脚本名"
          rules={[
            {
              required: true,
              message: '请输入脚本名',
            },
          ]}
        />
        <ProFormSelect
          name="eventType"
          label="事件类型"
          rules={[
            {
              required: true,
              message: '请选择事件类型',
            },
          ]}
          options={[
            {
              label: '接受到消息',
              value: 'message',
            },
            {
              label: '接到系统通知',
              value: 'notice',
            },
          ]}
        />
        <ProFormSelect
          name="scriptType"
          label="脚本类型"
          rules={[
            {
              required: true,
              message: '请选择脚本类型',
            },
          ]}
          options={[
            {
              label: 'AviatorScript',
              value: 'AviatorScript',
            },
          ]}
        />
        <ProFormTextArea
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入备注',
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
          defaultLanguage="shell"
        />
      </Modal>
    </PageContainer>
  );
};

export default botScript;

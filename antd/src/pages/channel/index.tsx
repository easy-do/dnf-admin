import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { Access, useAccess } from 'umi';
import {
  debugFrida,
  getChannelInfo,
  getDebugLog,
  getFridaLog,
  pageChannel,
  restartFrida,
  updateFridaJs,
  updatePythonScript,
} from '@/services/dnf-admin/daChannelController';
import { stopFrida } from '@/services/dnf-admin/daChannelController';
import Modal from 'antd/lib/modal/Modal';
import Editor from '@monaco-editor/react';
import ProForm, { ProFormInstance, ProFormItem, ProFormTextArea, } from '@ant-design/pro-form';
import Button from 'antd/lib/button';
import message from 'antd/lib/message';
import Tabs from 'antd/lib/tabs';
import { Checkbox, Space } from 'antd';

const Channel: React.FC = () => {
  const actionRef = useRef<ActionType>();

  const access = useAccess();

  const formRef = useRef<ProFormInstance>();
  const formRef1 = useRef<ProFormInstance>();

  const startChannelFridaFunc = (id: string) => {
    const loading = message.loading('重启Friday...');
    loading();
    try {
      restartFrida({ id: id }).then((res) => {
        if (res.success) {
          loading();
          message.success('重启Friday成功');
          actionRef.current?.reload();
        }
      });
    } catch { }
  };

  const stopChannelFridaFunc = (id: string) => {
    console.log(id);
    const loading = message.loading('关闭Frida...');
    loading();
    stopFrida({ id: id }).then((res) => {
      if (res.success) {
        loading();
        message.success('关闭Frida成功');
        actionRef.current?.reload();
      }
    });
  };

  /** 国际化配置 */

  const columns: ProColumns<API.DaChannel>[] = [
    {
      title: 'PID',
      dataIndex: 'pid',
    },
    {
      title: '频道名称',
      dataIndex: 'channelName',
    },
    {
      title: '频道状态',
      dataIndex: 'channelStatus',
      valueEnum: {
        true: { text: '运行', status: 'Success' },
        false: { text: '停止', status: 'Error' },
      },
    },
    {
      title: 'firda状态',
      dataIndex: 'fridaStatus',
      valueEnum: {
        true: { text: '运行', status: 'Success' },
        false: { text: '停止', status: 'Error' },
      },
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Access accessible={access.hashPre('channel.frida')}>
          {record.fridaStatus ? (
            <a
              key="id"
              onClick={() => {
                stopChannelFridaFunc(record.id);
              }}
            >
              关闭frida
            </a>
          ) : (
            <a
              key="id"
              onClick={() => {
                startChannelFridaFunc(record.id);
              }}
            >
              重启frida
            </a>
          )}
        </Access>,
        <Access accessible={access.hashPre('channel.frida')}>
          <a
            key="id"
            onClick={() => {
              setDebugId(record.id)
              openDebugModal(record.id);
            }}
          >
            frida调试
          </a>
        </Access>,
      ],
    },
  ];

  const [updateDebugModalVisible, handleUpdateDebugModalVisible] = useState<boolean>(false);
  const [confirmLoading, setConfirmLoading] = useState<boolean>(false);
  const [cureentScriptContext, setCureentScriptContext] = useState<string>('// 什么都没有');
  const [cureentPythonContext, setCureentPythonContext] = useState<string>('// 什么都没有');
  const [cureentJsonContext, setCureentJsonContext] = useState<string>('// 什么都没有');
  const [isRestartFrida, setIsRestartFrida] = useState<boolean>(false);

  const handleFridaEditorChange = (value: string, event: any) => {
    setCureentScriptContext(value);
  };

  const handlePythonEditorChange = (value: string, event: any) => {
    setCureentPythonContext(value);
  };
  const handleJsonEditorChange = (value: string, event: any) => {
    setCureentJsonContext(value);
  };

  const openDebugModal = (id: number) => {
    const loading = message.loading('加载firda数据中...');
    loading();
    getChannelInfo({ id: id }).then((res) => {
      if (res.success) {
        setCureentScriptContext( res.data?.scriptContext ? res.data?.scriptContext : '// 什么都没有');
        setCureentPythonContext(res.data?.mainPython ? res.data?.mainPython : '// 什么都没有');
        setCureentJsonContext(res.data?.fridaJsonContext ? res.data?.fridaJsonContext : '// 什么都没有');
        handleUpdateDebugModalVisible(true);
        refrashDebug(id);
        refrashFridaLog(id);
        loading();
        message.success('frida数据加载完成');
      }
    });
  };

  const fridaEditorOK = () => {
    setConfirmLoading(true);
    const loading = message.loading('保存...');
    loading();
    updateFridaJs({
      channelId: debugId,
      context: cureentScriptContext,
      restartFrida: isRestartFrida,
    }).then((res) => {
      if (res.success) {
        loading();
        if(isRestartFrida){
          message.success('保存并重启成功');
          openDebugModal(debugId);
          actionRef.current?.reload();
        }else{
          message.success('保存成功')
        }
      }
      setConfirmLoading(false);
    });
  };


  const pythonEditorOK = () => {
    setConfirmLoading(true);
    const loading = message.loading('保存...');
    loading();
    updatePythonScript({
      channelId: debugId,
      context: cureentPythonContext,
      restartFrida: isRestartFrida,
    }).then((res) => {
      if (res.success) {
        loading();
        if(isRestartFrida){
          message.success('保存并重启成功');
          handleUpdateDebugModalVisible(false)
          actionRef.current?.reload();
        }else{
          message.success('保存成功')
        }
      }
      setConfirmLoading(false);
    });
  };


  const [debugId, setDebugId] = useState<number>();

  const refrashDebug = (id) => {
    const loading = message.loading('刷新消息日志...');
    loading();
    getDebugLog({ id: id }).then((res) => {
      if (res.data) {
        formRef.current?.setFieldValue('debugLog', res.data.join('\r\n'));
      } else {
        formRef.current?.setFieldValue('debugLog', '暂无日志');
      }
      loading();
    });
  };

  const refrashFridaLog = (id) => {
    const loading = message.loading('刷新frida日志...');
    loading();
    getFridaLog({ id: id }).then((res) => {
      if (res.data) {
        formRef1.current?.setFieldValue('debugLog', res.data.join('\r\n'));
      } else {
        formRef1.current?.setFieldValue('debugLog', '暂无日志');
      }
      loading();
    });
  };

  const sendDebug = (fileds: any) => {
    const loading = message.loading('调试中...');
    loading();
    debugFrida({ channelId: debugId, debugData: fileds.debugData }).then((res) => {
      if (res.success) {
        message.success('调试完成');
        refrashDebug(debugId);
      }
    });
  };

  return (
    <PageContainer>
      <ProTable<API.ChannelQo, API.DaChannel>
        headerTitle="频道信息"
        actionRef={actionRef}
        rowKey="uid"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: false,
        }}
        request={pageChannel}
        columns={columns}
        toolBarRender={() => []}
      />
      <Modal
        style={{
          minHeight: '90%',
          minWidth: '90%',
        }}
        footer={null}
        cancelButtonProps={{ disabled: true }}
        keyboard={false}
        open={updateDebugModalVisible}
        onCancel={() => handleUpdateDebugModalVisible(false)}
        title="frida调试"
        destroyOnClose={true}
        maskClosable={false}
        centered
        okText="确定"
        cancelText="关闭"
        confirmLoading={confirmLoading}
      >
        <Tabs
          type="card"
          items={[
            {
              label: `调试`,
              key: 'debug',
              children: (
                <ProForm
                onFinish={sendDebug}
                formRef={formRef}
                submitter={{ 
                // 配置按钮文本
                searchConfig: {
                  submitText: '运行调试',
                },  
                  submitButtonProps:{
                  },
                  resetButtonProps: {
                    style: {
                      // 隐藏重置按钮
                      display: 'none',
                    },
                  },
                 }}
                >
                  <ProFormTextArea label="调试数据" name="debugData" />
                  <ProFormItem>
                    <Button onClick={() => refrashDebug(debugId)}>刷新通信日志</Button>
                  </ProFormItem>
                  <ProFormTextArea label="通信日志" name="debugLog" />
                </ProForm>
              ),
            },
            {
              label: `frida日志`,
              key: 'frida-client-log',
              children: (
                <ProForm
                formRef={formRef1}
                submitter={{ 
                // 配置按钮文本
                  submitButtonProps:{
                    style: {
                      // 隐藏重置按钮
                      display: 'none',
                    },
                  },
                  resetButtonProps: {
                    style: {
                      // 隐藏重置按钮
                      display: 'none',
                    },
                  },
                 }}
                >
                  <ProFormItem>
                    <Button onClick={() => refrashFridaLog(debugId)}>刷新日志</Button>
                  </ProFormItem>
                  <ProFormTextArea label="日志" name="debugLog" />
                </ProForm>
              ),
            },
            {
              label: `frida脚本`,
              key: 'frida',
              
              children: (
              <ProForm
              submitter={{
                render(props, dom) {
                  return(
                    <Space>
                      <Checkbox checked={isRestartFrida} onChange={(e)=>setIsRestartFrida(e.target.checked)} />重启frida
                      <Button type='primary' onClick={fridaEditorOK}>保存frida脚本</Button>
                    </Space>

                  )
                },
               }}
              >
              <Editor
                height="65vh"
                theme={'vs-dark'}
                width="100%"
                value={cureentScriptContext}
                onChange={handleFridaEditorChange}
                defaultLanguage="javascript"
              />
              </ProForm>
              ),
            },
            {
              label: `pthon脚本`,
              key: 'python',
              children: (
              <ProForm
                 submitter={{
                  render(props, dom) {
                    return(
                      <Space>
                        <Checkbox checked={isRestartFrida} onChange={(e)=>setIsRestartFrida(e.target.checked)}/> 重启frida
                        <Button type='primary' onClick={pythonEditorOK}>保存python脚本</Button>
                      </Space>

                    )
                  },
                 }}
                >
                  <Editor
                      height="65vh"
                      theme={'vs-dark'}
                      width="100%"
                      value={cureentPythonContext}
                      onChange={handlePythonEditorChange}
                      defaultLanguage="python" />
                </ProForm>
              ),
            },
            {
              label: `json配置`,
              key: 'json',
              children: (
              <ProForm
                 submitter={{
                  render(props, dom) {
                    return(
                      <Space>
                        <Checkbox checked={isRestartFrida} onChange={(e)=>setIsRestartFrida(e.target.checked)}/> 重启frida
                        <Button type='primary' onClick={pythonEditorOK}>保存json配置</Button>
                      </Space>

                    )
                  },
                 }}
                >
                  <Editor
                      height="65vh"
                      theme={'vs-dark'}
                      width="100%"
                      value={cureentJsonContext}
                      onChange={handleJsonEditorChange}
                      defaultLanguage="json" />
                </ProForm>
              ),
            },
          ]}
        />

      </Modal>
    </PageContainer>
  );
};

export default Channel;

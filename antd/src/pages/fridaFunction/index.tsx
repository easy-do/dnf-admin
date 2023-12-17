import { Button, Col, List, message, Row } from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { ModalForm, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { CheckOutlined, DeleteOutlined, EditOutlined, PlusOutlined } from '@ant-design/icons';
import { Access, useAccess } from 'umi';
import VirtualList from 'rc-virtual-list';

import {
  getChildrenFunction,
  pageFridaFunction,
  saveFridaFunction,
  updateFridaFunction,
} from '@/services/dnf-admin/daFridaFunctionController';
import Modal from 'antd/lib/modal/Modal';
import Editor from '@monaco-editor/react';
import { getFridaFunctionInfo } from '@/services/dnf-admin/daFridaFunctionController';
import { listFridaFunction } from '@/services/dnf-admin/daFridaFunctionController';

const ConfList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaFridaFunction>();
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const access = useAccess();

  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.DaFridaFunction) => {
    const hide = message.loading('正在添加');

    try {
      const childrenFunction = fields.childrenFunction ? fields.childrenFunction.join(',') : '';

      await saveFridaFunction({ ...fields, childrenFunction: childrenFunction });
      flushFunctionList();
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
    getFridaFunctionInfo({ id: id }).then((res) => {
      if (res.success) {
        const childrenFunction = res.data.childrenFunction
          ? res.data.childrenFunction.split(',').map(String)
          : [];
        setCurrentRow({ ...res.data, childrenFunction: childrenFunction });
        handleUpdateModalVisible(true);
      }
    });
  };

  const [functionList, setFunctionList] = useState<API.DaFridaFunction[]>();
  const [imortFunctionList, setImortFunctionList] = useState<API.DaFridaFunction[]>();
  const [imortFunctionKeys, setImortFunctionKeys] = useState<String[]>();

  const flushFunctionList = () => {
    listFridaFunction({}).then((res) => {
      setFunctionList(res.data);
    });
  };

  const flushImortFunctionList = (id) => {
    getChildrenFunction({ id: id }).then((res) => {
      setImortFunctionList(res.data);
      const tem = [];
      res.data?.map((item, index) => {
        tem.push(item.functionKey);
      });
      setImortFunctionKeys(tem);
    });
  };

  const addImportFunction = (fileds) => {
    console.log(fileds);
    updateFridaFunction({ id: currentRow?.id, childrenFunction: fileds.funKeys.join(',') }).then(
      (res) => {
        if (res.success) {
          message.success('添加函数成功');
          handImportFunctionModalVisible(false);
          openScriptEditor(currentRow?.id);
        }
      },
    );
  };

  const removeImportFunction = (id) => {
    const childFun = [];
    imortFunctionList?.map((item, index) => {
      if (item.id != id) {
        childFun.push(item.functionKey);
      }
    });
    updateFridaFunction({ id: currentRow.id, childrenFunction: childFun.join(',') }).then((res) => {
      if (res.success) {
        message.success('删除函数成功');
        openScriptEditor(currentRow.id);
      }
    });
  };

  const [importFunctionModalVisible, handImportFunctionModalVisible] = useState<boolean>(false);

  const openImportFunctionModal = () => {
    handImportFunctionModalVisible(true);
  };

  /**
   * 更新节点
   *
   * @param fields
   */

  const handleUpdate = async (fields?: API.DaFridaFunction) => {
    const hide = message.loading('正在配置');

    try {
      const childrenFunction = fields.childrenFunction ? fields.childrenFunction.join(',') : '';
      await updateFridaFunction({
        ...fields,
        childrenFunction: childrenFunction,
      });
      hide();
      message.success('配置成功');
      actionRef.current?.reload();
      if (updateScriptModalVisible) {
        openScriptEditor(fields.id);
      }
      return true;
    } catch (error) {
      hide();
      message.error('配置失败请重试！');
      return false;
    }
  };

  /** 国际化配置 */
  /**
   * 编号
   */
  const columns: ProColumns<API.DaFridaFunction>[] = [
    {
      title: '函数名',
      dataIndex: 'functionName',
    },
    {
      title: '唯一标识',
      dataIndex: 'functionKey',
    },
    {
      title: '系统函数',
      dataIndex: 'isSystemFun',
      valueEnum: {
        true: {
          text: '是',
        },
        false: {
          text: '否',
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
        <Access accessible={access.hashPre('function.update')}>
          <a
            key="id"
            onClick={() => {
              openUpdateModal(record.id);
            }}
          >
            编辑信息
          </a>
        </Access>,
        <Access accessible={access.hashPre('function.update')}>
          <a
            key="id"
            onClick={() => {
              openScriptEditor(record.id);
            }}
          >
            编辑函数
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
    flushFunctionList();
    flushImortFunctionList(id);
    getFridaFunctionInfo({ id: id }).then((res: API.RDaFridaFunction) => {
      if (res.success) {
        setCureentScriptContext(res.data.functionContext);
        setCurrentRow(res.data);
        handleUpdateScriptModalVisible(true);
      }
    });
  };

  const editorOK = () => {
    setConfirmLoading(true);
    updateFridaFunction({ id: currentRow.id, functionContext: cureentScriptContext }).then(
      (res: API.RDaFridaFunction) => {
        if (res.success) {
          openScriptEditor(currentRow.id);
          message.success('保存成功');
        }
        setConfirmLoading(false);
      },
    );
  };

  return (
    <PageContainer>
      <ProTable<API.DaFridaFunction, API.PageQo>
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
        request={pageFridaFunction}
        columns={columns}
        toolBarRender={() => [
          <Access accessible={access.hashPre('function.save')}>
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
        title="添加函数"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.DaFridaFunction);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="functionName"
          label="函数名"
          rules={[
            {
              required: true,
              message: '请输入函数名！',
            },
          ]}
        />
        <ProFormText
          name="functionKey"
          label="唯一标识"
          rules={[
            {
              required: true,
              message: '请输入唯一标识！',
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
          name="functionName"
          label="函数名"
          rules={[
            {
              required: true,
              message: '请输入函数名！',
            },
          ]}
        />
        <ProFormText
          name="functionKey"
          label="唯一标识"
          rules={[
            {
              required: true,
              message: '请输入唯一标识！',
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
              res.data.forEach((item, index) => {
                if (item.functionKey === currentRow?.functionKey) {
                  res.data[index].disabled = true;
                }
              });
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
      <Modal
        style={{
          minHeight: '100%',
          minWidth: '100%',
        }}
        keyboard={false}
        open={updateScriptModalVisible}
        onOk={editorOK}
        onCancel={() => handleUpdateScriptModalVisible(false)}
        title="编辑函数"
        destroyOnClose={true}
        maskClosable={false}
        centered
        okText="保存函数内容"
        cancelText="关闭窗口"
        confirmLoading={confirmLoading}
      >
        <Row>
          <Col span={4}>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => handleModalVisible(true)}>
              添加函数
            </Button>
            <div
              style={{
                maxHeight: '400px',
                overflow: 'auto',
              }}
            >
              <List>
                <VirtualList data={functionList} itemKey="id">
                  {(item: API.DaFridaFunction) => (
                    <List.Item
                      actions={[
                        <Button
                          disabled={item.functionKey === currentRow?.functionKey}
                          type="primary"
                          icon={<CheckOutlined />}
                          onClick={() => openScriptEditor(item.id)}
                        />,
                        <Button
                          type="primary"
                          icon={<EditOutlined />}
                          onClick={() => openUpdateModal(item.id)}
                        />,
                      ]}
                    >
                      {item.functionName}
                    </List.Item>
                  )}
                </VirtualList>
              </List>
            </div>
          </Col>
          <Col span={16}>
            <Editor
              height="70vh"
              theme={'vs-dark'}
              width="100%"
              value={cureentScriptContext}
              // defaultValue={cureentScriptContext}
              onChange={handleEditorChange}
              defaultLanguage="javascript"
            />
          </Col>
          <Col span={4}>
            <div
              style={{
                padding: '0 16px',
              }}
            >
              <Button type="primary" icon={<PlusOutlined />} onClick={openImportFunctionModal}>
                引入函数
              </Button>
            </div>
            <div
              style={{
                maxHeight: '400px',
                padding: '0 10px',
                overflow: 'auto',
              }}
            >
              <List>
                <VirtualList data={imortFunctionList} itemKey="id">
                  {(item: API.DaFridaFunction) => (
                    <List.Item
                      actions={[
                        <Button
                          disabled={item.functionKey === currentRow?.functionKey}
                          type="primary"
                          icon={<DeleteOutlined />}
                          onClick={() => removeImportFunction(item.id)}
                        />,
                      ]}
                    >
                      {item.functionName}
                    </List.Item>
                  )}
                </VirtualList>
              </List>
            </div>
          </Col>
        </Row>
      </Modal>
      <ModalForm
        title="引入函数"
        visible={importFunctionModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        onVisibleChange={(value) => {
          handImportFunctionModalVisible(value);
        }}
        onFinish={addImportFunction}
      >
        <ProFormSelect
          name="funKeys"
          label="引入函数"
          mode="multiple"
          initialValue={imortFunctionKeys}
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
              res.data?.forEach((item, index) => {
                imortFunctionList?.map((item1, index1) => {
                  if (
                    item.functionKey === currentRow?.functionKey ||
                    item.functionKey === item1.functionKey
                  ) {
                    res.data[index].disabled = true;
                  }
                });
              });
              return res.data;
            })
          }
        />
      </ModalForm>
    </PageContainer>
  );
};

export default ConfList;

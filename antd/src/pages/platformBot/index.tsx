import { PlusOutlined } from '@ant-design/icons';
import { Button, message, Drawer } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import { Access, useAccess } from 'umi';
import { ModalForm, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { enableBotScript, getEnableBotScript, infoBot, pageBot, removeBot, updateBot } from '@/services/dnf-admin/daBotController';
import { addBot } from '@/services/dnf-admin/daBotController';
import Modal from 'antd/lib/modal/Modal';
import EditBotConf from './EditBotConf';
import { botScriptList } from '@/services/dnf-admin/daBotScriptController';

const platfromBot: React.FC = () => {
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaBotInfo>();
  const access = useAccess();
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.DaBotInfo) => {
    const hide = message.loading('正在添加');

    try {
      await addBot({ ...fields });
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
    infoBot({ id: id }).then((res) => {
      if (res.success) {
        setCurrentRow(res.data);
        handleEditModalVisible(true);
      } else {
        message.warn(res.errorMessage);
      }
    });
  };

    /** 编辑配置窗口的弹窗 */
    const [editBotConfModalVisible, handleEditBotConfModalVisible] = useState<boolean>(false);

    const openEditBotConfModal = (id: string) => {
      infoBot({ id: id }).then((res) => {
        if (res.success) {
          setCurrentRow(res.data);
          handleEditBotConfModalVisible(true);
        } else {
          message.warn(res.errorMessage);
        }
      });
    };

        /** 启用脚本窗口的弹窗 */
        const [enableBotScriptModalVisible, handleEnableBotScriptModalVisible] = useState<boolean>(false);
        const [enableScriptData, setEnableScriptData] = useState<API.EnableBotScriptDto>({
          botId: '',
          scriptIds: [],
        });

        const openEnableBotScriptModal = (id: string) => {
          getEnableBotScript({ id: id }).then((res) => {
            if (res.success) {
              setEnableScriptData({botId:id,scriptIds:res.data});
              handleEnableBotScriptModalVisible(true);
            } else {
              message.warn(res.errorMessage);
            }
          });
        };

  /** 国际化配置 */

  const columns: ProColumns<API.DaBotInfo>[] = [
    {
      title: 'bot账号',
      dataIndex: 'botNumber',
    },
    {
      title: '最后心跳时间',
      dataIndex: 'lastHeartbeatTime',
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
        <Access accessible={access.hashPre('platformBot.update')}>
        <a
          key="id"
          onClick={() => {
            openEditModal(record.id);
          }}
        >
          编辑信息
        </a>
      </Access>,
      <Access accessible={access.hashPre('platformBot.update')}>
              <a
                key="id"
                onClick={() => {
                  openEditBotConfModal(record.id);
                }}
              >
                编辑配置
              </a>
      </Access>,
      <Access accessible={access.hashPre('platformBot.update')}>
      <a
        key="id"
        onClick={() => {
          openEnableBotScriptModal(record.id);
        }}
      >
        启用脚本
      </a>
      </Access>,
        <Access accessible={access.hashPre('platformBot.remove')}>
          <a
            key="id"
            onClick={() => {
              removeBot([record.id]).then((res) => {
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
      <ProTable<API.DaBotInfo, API.RListDaBotInfo>
        headerTitle="机器人列表"
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
          <Access accessible={access.hashPre('platformBot.add')}>
            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={() => {
                handleModalVisible(true);
              }}
            >
              添加bot
            </Button>
          </Access>,
        ]}
        request={pageBot}
        columns={columns}
      />
      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.DaItemEntity>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.DaItemEntity>[]}
          />
        )}
      </Drawer>
      <ModalForm
        title="添加机器人"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.DaItemEntity);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="botNumber"
          label="bot账号"
          rules={[
            {
              required: true,
              message: '请输入bot账号',
            },
          ]}
        />
        <ProFormText
          name="botUrl"
          label="通讯地址"
          rules={[
            {
              required: true,
              message: '请输入通讯地址',
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
          updateBot(value).then((res) => {
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
          name="botNumber"
          label="bot账号"
          rules={[
            {
              required: true,
              message: '请输入bot账号',
            },
          ]}
        />
        <ProFormText
          name="botUrl"
          label="通讯地址"
          rules={[
            {
              required: true,
              message: '请输入通讯地址',
            },
          ]}
        />
        <ProFormText
          name="botSecret"
          label="密钥"
          rules={[
            {
              required: true,
              message: '请输入密钥',
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
        title="机器人配置信息"
        style={{ minHeight: '50%', minWidth: '80%' }}
        open={editBotConfModalVisible}
        destroyOnClose
        onOk={() => handleEditBotConfModalVisible(false)}
        onCancel={() => handleEditBotConfModalVisible(false)}
        okText={'确定'}
        cancelText={'关闭'}
      >
        <EditBotConf botNumber={currentRow?.botNumber} visible={editBotConfModalVisible}/>
      </Modal>
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="启用脚本"
        visible={enableBotScriptModalVisible}
        initialValues={enableScriptData}
        onVisibleChange={handleEnableBotScriptModalVisible}
        onFinish={(value) => {
          console.log(value);
          enableBotScript(value).then((res) => {
            if (res.success) {
              message.success('更新成功');
              handleEnableBotScriptModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }
          });
        }}
      >
        <ProFormText hidden name="botId" />
        <ProFormSelect
          name="scriptIds"
          label="启用脚本"
          mode="multiple"
          fieldProps={{
            suffixIcon: null,
            showSearch: true,
            labelInValue: false,
            autoClearSearchValue: true,
            fieldNames: {
              label: 'scriptName',
              value: 'id',
            },
          }}
          request={() => botScriptList({}).then(res => {
            return res.data
          })}
        />
      </ModalForm>
    </PageContainer>
  );
};

export default platfromBot;

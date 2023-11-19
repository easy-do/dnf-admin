import { PlusOutlined } from '@ant-design/icons';
import { Button, Card, message } from 'antd';
import React, { useRef, useState } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType, EditableFormInstance } from '@ant-design/pro-table';
import ProTable, { EditableProTable } from '@ant-design/pro-table';
import ProForm, { ModalForm, ProFormMoney, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { sendMail } from '@/services/dnf-admin/gameToolController';
import { pageMailSendLog } from '@/services/dnf-admin/daMailSendLogController';
import { roleList } from '@/services/dnf-admin/gameRoleController';
import { listItem } from '@/services/dnf-admin/daItemController';

const ItemList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const editableFormRef = useRef<EditableFormInstance>();
  
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.SendMailDto) => {
    const hide = message.loading('正在添加');

    try {
      await sendMail({ ...fields });
      hide();
      message.success('添加成功');
      return true;
    } catch (error) {
      hide();
      message.error('添加失败请重试！');
      return false;
    }
  };

  /** 国际化配置 */

  const columns: ProColumns<API.DaMailSendLog>[] = [
    {
      title: '标题',
      dataIndex: 'sendDetails.title',
      render: (_,record) => {
        const sendDetails = JSON.parse(record.sendDetails);
        return sendDetails.title;
      }
    },
    {
      title: '正文',
      dataIndex: 'sendDetails.content',
      render: (_,record) => {
        const sendDetails = JSON.parse(record.sendDetails);
        return sendDetails.content;
      }
    },
    {
      title: '发送时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
    },
  ];

  const itemColumns: ProColumns<API.MailItemDto>[] = [
    {
      title: '物品',
      dataIndex: 'itemId',
      renderFormItem: () => {
        return <ProFormSelect 
        name="itemId"
        label="物品"
        fieldProps={{
          suffixIcon: null,
          showSearch: true,
          labelInValue: false,
          autoClearSearchValue: true,
          fieldNames: {
            label: 'name',
            value: 'id',
          },
        }}
        request={()=>listItem({}).then(res=>{
          return res.data
        })}/>
      }
    },
    {
      title: '数量',
      dataIndex: 'count',
    },
    {
      title: '操作',
      key: 'action',
      valueType: 'option',
      render: (_, record: API.MailItemDto, index, action) => {
        return [
          <a
            key="eidit"
            onClick={() => {
              action?.startEditable(record.key);
            }}
          >
            编辑
          </a>,
        ];
      },
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaMailSendLog, API.PageQo>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button type="primary" icon={<PlusOutlined />} onClick={() => handleModalVisible(true)}>
            发送邮件
          </Button>,
        ]}
        request={pageMailSendLog}
        columns={columns}
      />
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="发送邮件"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.SendMailDto);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormSelect
          name="characNo"
          label="角色"
          fieldProps={{
            suffixIcon: null,
            showSearch: true,
            labelInValue: false,
            autoClearSearchValue: true,
            fieldNames: {
              label: 'characName',
              value: 'characNo',
            },
          }}
          request={()=>roleList({}).then(res=>{
            return res.data
          })}
          rules={[
            {
              required: true,
              message: '请选择角色',
            },
          ]}
        />
        <ProFormText
          name="title"
          label="邮件标题"
          initialValue={'dnf-admin'}
          rules={[
            {
              required: true,
              message: '请输入邮件标题',
            },
          ]}
        />
        <ProFormTextArea
          name="content"
          label="邮件正文"
          initialValue={"dnf-admin 系统邮件"}
          rules={[
            {
              required: true,
              message: '请输入邮件标题',
            },
          ]}
        />

        <ProFormMoney
          name="gold"
          label="金币"
          rules={[
            {
              required: true,
              message: '请输入金币',
            },
          ]}
        />
        <Card title="物品信息" bordered={false}>
          <ProForm.Item name="itemList">
            <EditableProTable<API.MailItemDto>
              editableFormRef={editableFormRef}
              recordCreatorProps={{
                record: () => {
                  return {
                    key: `0${Date.now()}`,
                  };
                },
              }}
              columns={itemColumns}
              rowKey="key"
            />
          </ProForm.Item>
        </Card>
      </ModalForm>
    </PageContainer>
  );
};

export default ItemList;

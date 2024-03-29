import { CloseCircleOutlined, CopyOutlined, PlusOutlined, DeleteOutlined } from '@ant-design/icons';
import { Button, message } from 'antd';
import React, { useEffect, useRef, useState } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { ModalForm, ProFormGroup, ProFormList, ProFormMoney, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { cleanMail1} from '@/services/dnf-admin/gameToolController';
import { pageMailSendLog, sendMail } from '@/services/dnf-admin/daMailController';
import { roleList } from '@/services/dnf-admin/gameRoleController';
import { listItem } from '@/services/dnf-admin/daItemController';
import { Access, useAccess } from 'umi';

const Email: React.FC = () => {
  const actionRef = useRef<ActionType>();

  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const [itemList, setItemList] = useState<any>([]);
  const access = useAccess();
  useEffect(() => {
    listItem({}).then(res => {
      setItemList(res.data);
    })
  }, [createModalVisible]);

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
      render: (_, record) => {
        const sendDetails = JSON.parse(record.sendDetails);
        return sendDetails.title;
      }
    },
    {
      title: '正文',
      dataIndex: 'sendDetails.content',
      render: (_, record) => {
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


  return (
    <PageContainer>
      <ProTable<API.DaMailSendLog, API.PageQo>
        headerTitle="发送记录"
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
          <Access accessible={access.hashPre('mail.sendMail')}>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => handleModalVisible(true)}>
              发送邮件
            </Button>
          </Access>,
          <Access accessible={access.hashPre('tool.cleanMail')}>
            <Button type="primary" icon={<DeleteOutlined />} onClick={() => {
              cleanMail1().then(res=>{
                if(res.success){
                  message.success(res.message)
                }
                })
            }}>
              清空全服邮件
            </Button>
          </Access>,
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
          request={() => roleList({}).then(res => {
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
          initialValue={0}
          rules={[
            {
              required: true,
              message: '请输入金币',
            },
          ]}
        />
        <ProFormList
          name="itemList"
          label="发送物品"
          initialValue={[
          ]}
          copyIconProps={{ Icon: CopyOutlined, tooltipText: '复制' }}
          deleteIconProps={{
            Icon: CloseCircleOutlined,
            tooltipText: '删除',
          }}
        >
          <ProFormGroup key="group">
            <ProFormSelect
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
              request={() => itemList} />
        <ProFormSelect
          name="itemType"
          label="物品类型"
          options={[
            {'label':'装备','value':1},
            {'label':'消耗品','value':2},
            {'label':'材料','value':3},
            {'label':'任务材料','value':4},
            {'label':'宠物','value':5},
            {'label':'宠物装备','value':6},
            {'label':'宠物消耗品','value':7},
            {'label':'时装','value':8},
            {'label':'副职业','value':10},
          ]}
        /> 
            <ProFormText fieldProps={{
              type: 'number',
              min: 1,
            }} initialValue={1} name="count" label="数量" />
          </ProFormGroup>
        </ProFormList>
      </ModalForm>
    </PageContainer>
  );
};

export default Email;

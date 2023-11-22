import { PlusOutlined } from '@ant-design/icons';
import { Button, message } from 'antd';
import React, { useRef, useState } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { pageGameNotice, sendNotice } from '@/services/dnf-admin/gameNoticeController';
import { ModalForm, ProFormTextArea } from '@ant-design/pro-form';
import { Access, useAccess } from 'umi';

const ItemList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const access = useAccess();
  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.sendNoticeParams) => {
    const hide = message.loading('正在添加');

    try {
      await sendNotice({ ...fields });
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

  const columns: ProColumns<API.DaNoticeSendLog>[] = [
    {
      title: '发送内容',
      dataIndex: 'message',
    },
    {
      title: '发送时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaNoticeSendLog, API.PageQo>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Access accessible={access.hashPre('notice.sendNotice')}>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => handleModalVisible(true)}>
              发送公告
            </Button></Access>,
        ]}
        request={pageGameNotice}
        columns={columns}
      />
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="发送公告"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.sendNoticeParams);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormTextArea
          name="message"
          label="公告内容"
          rules={[
            {
              required: true,
              message: '请输入公告内容',
            },
          ]}
        />
      </ModalForm>
    </PageContainer>
  );
};

export default ItemList;

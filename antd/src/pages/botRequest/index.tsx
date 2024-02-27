import React, { useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useAccess } from 'umi';
import { pageBotRequest } from '@/services/dnf-admin/daBotController';

const botRequest: React.FC = () => {
  
  const actionRef = useRef<ActionType>();
  const access = useAccess();

  /** 国际化配置 */

  const columns: ProColumns<API.DaBotRequest>[] = [
    {
      title: '请求类型',
      dataIndex: 'requestType',
      valueEnum: {
        friend: "好友请求",
        group: "群请求",
      }
    },
    {
      title: '群组',
      dataIndex: 'groupId',
    },
    {
      title: '发送人',
      dataIndex: 'sendUser',
    },
    {
      title: '接收bot',
      dataIndex: 'selfUser',
    },
    {
      title: '接收时间',
      dataIndex: 'selfTime',
    },
    {
      title: '验证信息',
      dataIndex: 'comment',
      ellipsis: true,
      width: 200,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaBotRequest, API.RListDaBotRequest>
        headerTitle="历史请求记录"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={pageBotRequest}
        columns={columns}
      />
    </PageContainer>
  );
};

export default botRequest;

import React, { useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { useAccess } from 'umi';
import { pageBotNotice } from '@/services/dnf-admin/daBotController';

const botNotice: React.FC = () => {
  
  const actionRef = useRef<ActionType>();
  const access = useAccess();

  /** 国际化配置 */

  const columns: ProColumns<API.DaBotNotice>[] = [
    {
      title: '通知类型',
      dataIndex: 'noticeType',
      valueEnum: {
        'group_admin': '群管理员变更',
        'group_decrease': '群成员减少',
        'group_increase': '群成员增加',
        'group_ban': '群成员禁言',
        'friend_add': '添加好友',
        'group_add': '群添加',
        'group_recall': '群消息撤回',
        'notify': '系统通知',
        'friend_recall': '好友消息撤回',
        'group_card': '群名片变更',
        'offline_file': '离线文件上传',
        'client_status': '客户端状态变更',
        'group_upload': '群文件上传',
      },
    },
    {
      title: '子类型',
      dataIndex: 'subType',
      valueEnum: {
        'honor': '群荣誉变更',
        'poke': '戳一戳',
        'lucky_king': '群红包幸运王',
        'group_ban': '群成员头衔变更',
        'approve': '管理员同意入群',
        'invite': '管理员邀请入群',
        'leave': '主动退群',
        'kick': '成员被踢',
        'kick_me': '登录号被踢',
        'set': '设置管理员',
        'unset': '取消管理员',
        'ban': '禁言',
        'lift_ban': '解除禁言',
      }
    },
    {
      title: '接收bot',
      dataIndex: 'selfUser',
    },
    {
      title: '操作人',
      dataIndex: 'operatorId',
    },
    {
      title: '变动人',
      dataIndex: 'userId',
    },
    {
      title: '接收时间',
      dataIndex: 'selfTime',
      search: false,
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
      <ProTable<API.DaBotNotice, API.RListDaBotNotice>
        headerTitle="历史通知"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
        }}
        request={pageBotNotice}
        columns={columns}
      />
    </PageContainer>
  );
};

export default botNotice;

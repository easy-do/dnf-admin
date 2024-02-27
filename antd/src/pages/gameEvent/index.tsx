import { PlusOutlined } from '@ant-design/icons';
import { Button, message, Drawer } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import { Access, useAccess } from 'umi';
import {
  cleanGameEvent,
  gameEventPage,
  removeGameEvent,
} from '@/services/dnf-admin/daGameEventController';

const ItemList: React.FC = () => {
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaItemEntity>();
  const [selectedRowsState, setSelectedRows] = useState<API.DaItemEntity[]>([]);
  const access = useAccess();

  /** 国际化配置 */

  const columns: ProColumns<API.DaItemEntity>[] = [
    {
      title: '账户ID',
      dataIndex: 'accountId',
    },
    {
      title: '频道',
      dataIndex: 'channel',
    },
    {
      title: '角色ID',
      dataIndex: 'charcaNo',
    },
    {
      title: '角色名',
      dataIndex: 'charcaName',
    },
    {
      title: '等级',
      dataIndex: 'level',
    },
    {
      title: '操作类型',
      dataIndex: 'optionType',
      valueEnum: {
        'Item+': '获得物品',
        'Item-': '物品减少',
        'Level+': '升级',
        'Avatar+': '获得时装',
        'Money+': '金币增加',
        'Money-': '金币减少',
        'IP+': '登录',
        'IP-': '退出',
        'MailR': '读取邮件',
        'KillMob': '击杀怪物',
        'DungeonLeave': '退出副本',
        'DungeonEnter': '进入副本',
        'MoveArea': '区域移动',
        'BuyCashItem': '商城购买',
        'Inven+': '存仓库',
        'Inven-': '取仓库',
        'AccountCargo+': '存金库',
        'AccountCargo-': '取金库',
        // 'SealItem': '解除封印',
        'UseSelectBooster': '使用礼包',
        'Upgrade+': '强化成功',
        'Upgrade-': '强化失败',
        'ApplyItem': '使用消耗品',
        'RandomOptionItem': '随机装备属性',
        'QuestComplete': '完成任务',
      },
    },
    {
      title: '操作详情',
      dataIndex: 'optionInfo',
      search: false,
    },
    // {
    //   title: '参数1',
    //   dataIndex: 'param1',
    //   search: false,
    // },
    // {
    //   title: '参数2',
    //   dataIndex: 'param2',
    //   search: false,
    // },
    // {
    //   title: '参数3',
    //   dataIndex: 'param3',
    //   search: false,
    // },
    {
      title: '客户端IP',
      dataIndex: 'clientIp',
      search: false,
    },
    {
      title: '时间',
      dataIndex: 'optionTime',
      search: false,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Access accessible={access.hashPre('gameEvent.remove')}>
          <a
            key="id"
            onClick={() => {
              removeGameEvent({ id: record.id }).then((res) => {
                if (res.success) {
                  message.success(res.message);
                } else {
                  message.warning(res.errorMessage);
                }
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
      <ProTable<API.DaItemEntity, API.RListDaItemEntity>
        headerTitle="游戏事件"
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
          <Access accessible={access.hashPre('gameEvent.remove')}>
            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={() => {
                cleanGameEvent({}).then((res) => {
                  if (res.success) {
                    message.success(res.message);
                  } else {
                    message.warning(res.errorMessage);
                  }
                });
              }}
            >
              清空
            </Button>
          </Access>,
        ]}
        request={gameEventPage}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              已选择{' '}
              <a
                style={{
                  fontWeight: 600,
                }}
              >
                {selectedRowsState.length}
              </a>{' '}
              项 &nbsp;&nbsp;
            </div>
          }
        >
          <Access accessible={access.hashPre('gameEvent.remove')}>
            <Button
              onClick={async () => {
                await handleRemove(selectedRowsState);
                setSelectedRows([]);
                actionRef.current?.reloadAndRest?.();
              }}
            >
              批量删除
            </Button>
          </Access>
        </FooterToolbar>
      )}
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
    </PageContainer>
  );
};

export default ItemList;

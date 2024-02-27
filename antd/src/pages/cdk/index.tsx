import { PlusOutlined } from '@ant-design/icons';
import { Button, message, Drawer, Typography } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import { Access, request, useAccess } from 'umi';
import { ModalForm, ProFormDigit, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { addCdk, getCdkInfo, pageCdk, removeCdk } from '@/services/dnf-admin/daCdkController';

const { Paragraph } = Typography;

const ItemList: React.FC = () => {
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaCdk>();
  const [selectedRowsState, setSelectedRows] = useState<API.DaCdk[]>([]);
  const access = useAccess();

  /** 详情窗口的弹窗 */
  const [infoModalVisible, handleInfoModalVisible] = useState<boolean>(false);

  /**
 * 详情
 *
 * @param cdkCode
 */

  const openInfoModal = async (cdkCode: string) => {
    getCdkInfo({ 'id': cdkCode }).then(res => {
      if (res.success) {
        const data = res.data;
        let cdkConf = data?.cdkConf;
        if(cdkConf){
          cdkConf = JSON.parse(cdkConf);
          data.gold = cdkConf.gold;
          data.bonds = cdkConf.bonds;
          data.itemId = cdkConf.itemId;
          data.itemType = cdkConf.itemType;
        }
        setCurrentRow(data);
        handleInfoModalVisible(true);
      } else {
        message.warning('获取CDK详情失败');
      }
    })

  };

  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
 * 添加节点
 *
 * @param fields
 */

  const handleAdd = async (fields: any) => {
    const hide = message.loading('正在添加');

    try {
      const cdkConf= {'gold':0,'bonds':0,'itemId':0,'itemType':1};
      cdkConf.gold=fields.gold;
      cdkConf.bonds=fields.bonds;
      cdkConf.itemId=fields.itemId;
      cdkConf.itemType=fields.itemType;
      await addCdk({ 'cdkConf': JSON.stringify(cdkConf), 'remark': fields.remark,'number': fields.number });
      hide();
      message.success('添加成功');
      return true;
    } catch (error) {
      hide();
      message.error('添加失败请重试！');
      return false;
    }
  };
  /**
 * 删除节点
 *
 * @param selectedRows
 */

  const handleRemove = async (selectedRows: API.DaCdk[]) => {
    const hide = message.loading('正在删除');
    console.log(selectedRows)
    if (!selectedRows) return true;

    try {
      await removeCdk(selectedRows.map((row) => row.cdkCode));
      hide();
      message.success('删除成功，即将刷新');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('删除失败，请重试');
      return false;
    }
  };

    /**
 * 导出节点
 *
 * @param selectedRows
 */
     const handleExport = async (selectedRows: API.DaCdk[]) => {
      const hide = message.loading('开始导出');
      console.log(selectedRows)
      if (!selectedRows) return true;
  
      try {
          request('/api/cdk/exportCdk', {
            method: 'POST',
            responseType: 'blob',
            data: selectedRows.map((row) => row.cdkCode),
          }).then(res => {
            const blob = new Blob([res]);
            const objectURL = URL.createObjectURL(blob);
            let btn = document.createElement('a');
            btn.download = 'CDK导出.txt';
            btn.href = objectURL;
            btn.click();
            URL.revokeObjectURL(objectURL);
            hide();
            message.success('导出成功，即将刷新');
            actionRef.current?.reload();
          });
        return true;
      } catch (error) {
        hide();
        message.error('导出失败，请重试');
        return false;
      }
    };

  /** 国际化配置 */

  const columns: ProColumns<API.DaCdk>[] = [
    {
      title: 'CDK码',
      dataIndex: 'cdkCode',
      renderText(text, record, index, action) {
        return (<Paragraph>{record.cdkCode}</Paragraph>)
      },
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueEnum: {
        true: '已使用',
        false: '未使用'
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
    },
    {
      title: '备注',
      dataIndex: 'remark',
    },
    {
      title: '是否有效',
      dataIndex: 'deleteFlag',
      valueEnum: {
        true: '已作废',
        false: '有效'
      },
    },

    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Access accessible={access.hashPre('cdk.remove')}>
          <a
            key="cdkCode"
            onClick={() => {
              openInfoModal(record.cdkCode);
            }}
          >
            详情
          </a></Access>,
        <Access accessible={access.hashPre('cdk.remove')}>
          <a
            key="cdkCode"
            onClick={() => {
              removeCdk([record.cdkCode]).then((res) => {
                actionRef.current.reload();
              });
            }}
          >
            删除
          </a></Access>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaCdk, API.RListDaCdk>
        headerTitle="CDK信息"
        actionRef={actionRef}
        rowKey="cdkCode"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: false,
        }}
        toolBarRender={() => [
          <Access accessible={access.hashPre('cdk.add')}>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => {
              handleModalVisible(true);
            }}>
              生成CDK
            </Button>
          </Access>
        ]}
        request={pageCdk}
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
          <Access accessible={access.hashPre('cdk.remove')}>
            <Button
              onClick={async () => {
                await handleRemove(selectedRowsState);
                setSelectedRows([]);
                actionRef.current?.reloadAndRest?.();
              }}
            >
              批量作废
            </Button>
          </Access>
          <Access accessible={access.hashPre('cdk')}>
            <Button
              onClick={async () => {
                await handleExport(selectedRowsState);
                setSelectedRows([]);
                actionRef.current?.reloadAndRest?.();
              }}
            >
              批量导出
            </Button>
          </Access>
        </FooterToolbar>
      )}
      <Drawer
        width={600}
        visible={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.cdkCode && (
          <ProDescriptions<API.DaCdk>
            column={2}
            title={currentRow?.cdkCode}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.cdkCode,
            }}
            columns={columns as ProDescriptionsItemProps<API.DaCdk>[]}
          />
        )}
      </Drawer>
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="生成CDK"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
          <ProFormDigit
            name="gold"
            label="金币"
            min={0}
            initialValue={0}
            rules={[
              {
                required: true,
                message: '金币不能为空',
              },
            ]}
          />
          <ProFormDigit
            name="bonds"
            label="点券"
            min={0}
            initialValue={0}
            rules={[
              {
                required: true,
                message: '点券不能为空',
              },
            ]}
          />
          <ProFormDigit
            name="itemId"
            label="物品ID(-1代表无)"
            min={-1}
            initialValue={-1}
            rules={[
              {
                required: true,
                message: '物品id不能为空',
              },
            ]}
          />
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
          rules={[
            {
              required: true,
              message: '物品类型不能为空',
            },
          ]}
        /> 
        <ProFormTextArea
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '备注不能为空',
            },
          ]}
        />
          <ProFormDigit
            name="number"
            label="生成数量"
            initialValue={1}
            min={1}
            rules={[
              {
                required: true,
                message: '生成数量不能为空',
              },
            ]}
          />
      </ModalForm>
      <ModalForm
        modalProps={{
          destroyOnClose: true,
        }}
        title="CDK详情"
        visible={infoModalVisible}
        onVisibleChange={handleInfoModalVisible}
        initialValues={currentRow}
        onFinish={(value) => {
          handleInfoModalVisible(false);
          return true;
        }}
      >
        <ProFormText
          name="cdkCode"
          label="CDK码"
          readonly
        />
        <ProFormText
          name="gold"
          label="金币"
          readonly
        />
        <ProFormText
          name="bonds"
          label="点券"
          readonly
        />
        <ProFormText
          name="itemId"
          label="物品ID"
          readonly
        />
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
          readonly
        /> 
        <ProFormText
          name="status"
          label="状态"
          readonly
          valueEnum={{
            'true': '已使用',
            'false': '未使用'
          }}
        />
        <ProFormText
          name="createTime"
          label="创建时间"
          readonly
        />
        <ProFormText
          name="createBy"
          label="创建账户"
          readonly
        />
        <ProFormText
          name="useTime"
          label="使用时间"
          readonly
        />
        <ProFormText
          name="useUser"
          label="使用账户"
          readonly
        />
        <ProFormText
          name="deleteFlag"
          label="是否有效"
          readonly
          valueEnum={{
            'true': '已作废',
            'false': '有效'
          }}
        />
        <ProFormText
          name="remark"
          label="备注"
          readonly
        />
      </ModalForm>
    </PageContainer>
  );
};

export default ItemList;

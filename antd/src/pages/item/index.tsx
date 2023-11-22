import { DownloadOutlined, PlusOutlined, UploadOutlined } from '@ant-design/icons';
import { Button, message, Drawer, Upload } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import { pageItem, removeItem, saveItem } from '@/services/dnf-admin/daItemController';
import { UploadProps } from 'antd/es/upload/interface';
import { Access, request, useAccess } from 'umi';
import { ModalForm, ProFormText } from '@ant-design/pro-form';



const ItemList: React.FC = () => {
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaItemEntity>();
  const [selectedRowsState, setSelectedRows] = useState<API.DaItemEntity[]>([]);
  const access = useAccess();
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
 * 添加节点
 *
 * @param fields
 */

  const handleAdd = async (fields: API.DaItemEntity) => {
    const hide = message.loading('正在添加');

    try {
      await saveItem({ ...fields });
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

  const handleRemove = async (selectedRows: API.DaItemEntity[]) => {
    const hide = message.loading('正在删除');
    if (!selectedRows) return true;

    try {
      await removeItem(selectedRows.map((row) => row.id));
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

  function downloadTemplateFile() {
    request('/api/item/downloadTemplate', {
      method: 'GET',
      responseType: 'blob',
    }).then(res => {
      const blob = new Blob([res]);
      const objectURL = URL.createObjectURL(blob);
      let btn = document.createElement('a');
      btn.download = '导入模板.xlsx';
      btn.href = objectURL;
      btn.click();
      URL.revokeObjectURL(objectURL);
    });
  }

  const props: UploadProps = {
    name: 'file',
    action: '/api/item/importItem',
    onChange(info) {
      if (info.file.status !== 'uploading') {
      }
      if (info.file.status === 'done') {
        message.success(`${info.file.name} 文件上传成功`);
        actionRef.current?.reload();
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} 文件上传失败.`);
      }
    },
    beforeUpload: (file) => {
      const isPNG = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
      if (!isPNG) {
        message.error(`只支持.xlsx文件`);
      }
      return isPNG || Upload.LIST_IGNORE;
    },
  };

  const zpProps: UploadProps = {
    name: 'file',
    action: '/api/item/importItemFor7z',
    onChange(info) {
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList);
      }
      if (info.file.status === 'done') {
        message.success(`${info.file.name} 文件上传成功`);
        actionRef.current?.reload();
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} 文件上传失败`);
      }
    },
    beforeUpload: (file) => {
      const isPNG = file.type === '7z';
      if (!isPNG) {
        message.error(`只支持.7z文件`);
      }
      return isPNG || Upload.LIST_IGNORE;
    },
  };

  /** 国际化配置 */

  const columns: ProColumns<API.DaItemEntity>[] = [
    {
      title: '物品ID',
      dataIndex: 'id',
    },
    {
      title: '物品名称',
      dataIndex: 'name',
    },
    {
      title: '类型',
      dataIndex: 'type',
    },
    {
      title: '稀有度',
      dataIndex: 'rarity',
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <Access accessible={access.hashPre('item.remove')}>
          <a
            key="id"
            onClick={() => {
              removeItem([record.id]).then((res) => {
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
      <ProTable<API.DaItemEntity, API.RListDaItemEntity>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Access accessible={access.hashPre('conf.save')}>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => {
              handleModalVisible(true);
            }}>
              添加物品
            </Button>
          </Access>,
          <Access accessible={access.hashPre('item.importItem')}>
            <Button type="primary" icon={<DownloadOutlined />} onClick={downloadTemplateFile}>
              下载模板
            </Button>
          </Access>,
          <Access accessible={access.hashPre('item.importItem')}>
            <Upload {...props}>
              <Button type="primary" icon={<UploadOutlined />}>
                模板导入
              </Button>
            </Upload>
          </Access>,
          <Access accessible={access.hashPre('conf.update')}>
            <Upload {...zpProps}>
              <Button type="primary" icon={<UploadOutlined />}>
                pvfUtility7z导入
              </Button>
            </Upload></Access>,

        ]}
        request={pageItem}
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
          <Access accessible={access.hashPre('item.remove')}>
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
        visible={showDetail}
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
        modalProps={{
          destroyOnClose: true,
        }}
        title="添加物品"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
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
          name="id"
          label="物品id"
          fieldProps={{
            type: 'number',
          }}
          rules={[
            {
              required: true,
              message: '请输入物品id',
            },
          ]}
        />
        <ProFormText
          name="name"
          label="物品名称"
          fieldProps={{
            type: 'number',
          }}
          rules={[
            {
              required: true,
              message: '请输入物品名称',
            },
          ]}
        />
        <ProFormText
          name="type"
          label="物品类型"
          fieldProps={{
            type: 'number',
          }}
          rules={[
            {
              required: true,
              message: '请输入物品类型',
            },
          ]}
        />
        <ProFormText
          name="rarity"
          label="稀有度"
          fieldProps={{
            type: 'number',
          }}
          rules={[
            {
              required: true,
              message: '请输入稀有度',
            },
          ]}
        />
      </ModalForm>
    </PageContainer>
  );
};

export default ItemList;

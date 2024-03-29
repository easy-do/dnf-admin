import { Button, message } from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import ProTable, { ProColumns, ActionType } from '@ant-design/pro-table';
import { ModalForm, ProFormDatePicker, ProFormGroup, ProFormList, ProFormSelect, ProFormText } from '@ant-design/pro-form';
import { CloseCircleOutlined, CopyOutlined, PlusOutlined } from '@ant-design/icons';
import { saveSignIn, signInPage, updateSignIn } from '@/services/dnf-admin/signInController';
import { listItem } from '@/services/dnf-admin/daItemController';
import { Access, useAccess } from 'umi';

const Signin: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.DaGameConfig>();
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  /** 新建窗口的弹窗 */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);

  const [itemList, setItemList] = useState<any>([]);

  const access = useAccess();

  useEffect(() => {
    listItem({}).then(res => {
      setItemList(res.data);
    })
  }, [createModalVisible, updateModalVisible]);


  /**
   * 添加节点
   *
   * @param fields
   */

  const handleAdd = async (fields: API.DaGameConfig) => {
    const hide = message.loading('正在添加');

    try {
      await saveSignIn({ ...fields });
      hide();
      actionRef.current?.reload();
      message.success('添加成功');
      return true;
    } catch (error) {
      hide();
      message.error('添加失败请重试！');
      return false;
    }
  };
  /**
   * 更新节点
   *
   * @param fields
   */

  const handleUpdate = async (currentRow?: API.DaSignInConfDto) => {
    const hide = message.loading('正在配置');

    try {
      await updateSignIn({
        ...currentRow,
      });
      hide();
      message.success('配置成功');
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('配置失败请重试！');
      return false;
    }
  };

  /** 国际化配置 */

  const columns: ProColumns<API.DaSignInConf>[] = [
    {
      title: '签到名称',
      dataIndex: 'configName',
    },
    {
      title: '签到日期',
      dataIndex: 'configDate',
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
        <Access accessible={access.hashPre('signIn.update')}>
          <a
            key="id"
            onClick={() => {
              if (record.configJson) {
                try {
                  record.configJson = JSON.parse(record.configJson);
                } catch {

                }
              }
              setCurrentRow(record);
              handleUpdateModalVisible(true);
            }}
          >
            编辑
          </a>
        </Access>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.DaSignInConf, API.DaSignInConfQo>
        headerTitle="配置列表"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: false,
        }}
        request={signInPage}
        columns={columns}
        toolBarRender={() => [
          <Access accessible={access.hashPre('signIn.save')}>
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
        title="添加签到配置"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.DaSignInConf);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          name="configName"
          label="签到名称"
          rules={[
            {
              required: true,
              message: '请输入签到名称！',
            },
          ]}
        />
        <ProFormDatePicker
          name="configDate"
          label="签到日期"
          rules={[
            {
              required: true,
              message: '请选择签到日期！',
            },
          ]}
        />
        <ProFormText
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入备注！',
            },
          ]}
        />
        <ProFormList
          name="configJson"
          label="签到奖励"
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
            <ProFormText fieldProps={{
              type: 'number',
              min: 1,
            }} initialValue={1} name="quantity" label="数量" />
          </ProFormGroup>
        </ProFormList>
      </ModalForm>
      <ModalForm
        title="编辑签到配置"
        visible={updateModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        onVisibleChange={handleUpdateModalVisible}
        onFinish={handleUpdate}
      >
        <ProFormText name="id" hidden />
        <ProFormText
          name="configName"
          label="签到名称"
          rules={[
            {
              required: true,
              message: '请输入签到名称！',
            },
          ]}
        />
        <ProFormDatePicker
          name="configDate"
          label="签到日期"
          rules={[
            {
              required: true,
              message: '请选择签到日期！',
            },
          ]}
        />
        <ProFormText
          name="remark"
          label="备注"
          rules={[
            {
              required: true,
              message: '请输入备注！',
            },
          ]}
        />
        <ProFormList
          name="configJson"
          label="签到奖励"
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
            <ProFormText fieldProps={{
              type: 'number',
              min: 1,
            }} initialValue={1} name="quantity" label="数量" />
          </ProFormGroup>
        </ProFormList>
      </ModalForm>
    </PageContainer>
  );
};

export default Signin;


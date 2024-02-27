import React, { useState, useEffect, useRef } from 'react';
import type { EditableFormInstance, ProColumns } from '@ant-design/pro-table';
import { EditableProTable } from '@ant-design/pro-table';
import { Button, message } from 'antd';
import { addBotConf, getBotConf, removeBotConf, updateBotConf } from '@/services/dnf-admin/daBotController';
import { PlusOutlined } from '@ant-design/icons';
import { Access, useAccess } from 'umi';
import { ModalForm, ProFormText, ProFormTextArea } from '@ant-design/pro-form';


const EditBotConf = (props: any) => {

  const access = useAccess();

  const editableFormRef = useRef<EditableFormInstance>();

  const [currentData, setCurrentData] = useState<API.DaBotConf[]>();

  const [editableKeys, setEditableRowKeys] = useState<React.Key[]>([]);

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
        await addBotConf({ ...fields });
        hide();
        message.success('添加成功');
        return true;
      } catch (error) {
        hide();
        message.error('添加失败请重试！');
        return false;
      }
    };

  const getBotConfData = (botNumber: string) => {
    const hide = message.loading('加载配置信息...');
    getBotConf({ 'botNumber': botNumber }).then(res => {
      hide();
      if (res.success) {
        hide();
        setCurrentData(res.data)
        message.success('加载配置信息成功');
      }
    })
  }

  useEffect(() => {
    console.log(props)
    if (props.botNumber && props.visible) {
      getBotConfData(props.botNumber);
    }

  }, [props.botNumber, props.visible]);



  const columns: ProColumns<API.DaBotConf>[] = [
    {
      title: '配置编码',
      dataIndex: 'id',
      tooltip: '唯一标识,非编辑项',
      formItemProps: (form, { rowIndex }) => {
        return {
          rules:
            rowIndex > 1 ? [{ required: true, message: '此项为必填项' }] : [],
        };
      },
      readonly: true,
      width: '15%',
    },
    {
      title: '机器人编码',
      dataIndex: 'botNumber',
      tooltip: '机器人编码(必须是当前机器人)',
      readonly:true,
      width: '15%',
    },
    {
      title: '配置名称',
      key: 'confKey',
      dataIndex: 'confKey',
      tooltip: '英文字母',
      ellipsis:true,
      width: '15%',
    },
    {
      title: '配置参数',
      key: 'confValue',
      dataIndex: 'confValue',
      tooltip: '字符串存储',
      ellipsis:true,
      width: '15%',
    },
    {
      title: '备注',
      key: 'remark',
      dataIndex: 'remark',
      ellipsis:true,
      width: '20%',
    },
    {
      title: '操作',
      valueType: 'option',
      width: 200,
      render: (text, record, _, action) => [
        <a
          key="editable"
          onClick={() => {
            action?.startEditable?.(record.id);
          }}
        >
          编辑
        </a>,
      ],
    },
  ];


  return (
    <>
    <EditableProTable<API.DaBotConf>
      editableFormRef={editableFormRef}
      rowKey="id"
      headerTitle={'机器人配置'}
      recordCreatorProps={false}
      maxLength={5}
      scroll={{
        x: 960,
      }}
      toolBarRender={() => [
        <Access accessible={access.hashPre('platformBot.update')}>
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => {
              handleModalVisible(true);
            }}
          >
            添加配置
          </Button>
        </Access>,
      ]}
      loading={false}
      columns={columns}
      value={currentData}
      onChange={setCurrentData}
      editable={{
        type: 'multiple',
        editableKeys,
        onSave: (rowKey, data, row) => {
          updateBotConf(data).then(res => {
            if (res.success && res.data) {
              message.success('修改成功');
              getBotConf(props.botNumber);
            } else {
              message.success('修改失败');
            }

          })
          setEditableRowKeys([]);
        },
        onCancel: (key, record, originRow, _newLineConfig) => {
          console.log(key, record, originRow);
          setEditableRowKeys([]);
        },
        onDelete(key, row) {
          removeBotConf({id:key}).then(res => {
            if (res.success && res.data) {
              message.success('删除成功');
              getBotConf(props.botNumber);
              setEditableRowKeys([]);
            } else {
              message.success('删除失败');
            }
          })
        },
        onChange: setEditableRowKeys,
      }}
    />
   <ModalForm
        title="添加机器人配置"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.DaItemEntity);
          if (success) {
            handleModalVisible(false);
            getBotConfData(props.botNumber);
          }
        }}
      >
        <ProFormText
          name="botNumber"
          label="bot编码"
          initialValue={props.botNumber}
          readonly
          rules={[
            {
              required: true,
              message: '请输入bot编码',
            },
          ]}
        />
        <ProFormText
          name="confKey"
          label="配置名称"
          rules={[
            {
              required: true,
              message: '请输入配置名称',
            },
          ]}
        />
        <ProFormTextArea
          name="confValue"
          label="配置参数"
          rules={[
            {
              required: true,
              message: '请输入配置参数',
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
    </>
  );
};

export default EditBotConf;

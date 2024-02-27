import React, { useState, useEffect, useRef } from 'react';
import type { EditableFormInstance, ProColumns } from '@ant-design/pro-table';
import { EditableProTable } from '@ant-design/pro-table';
import { getRoleItem, updateRoleItem } from '@/services/dnf-admin/gameRoleController';
import { message } from 'antd';


const EditRoleItem = (props: any) => {


    const editableFormRef = useRef<EditableFormInstance>();
    
    const [currentData, setCurrentData] = useState<API.GameItemVo[]>();

    const [editableKeys, setEditableRowKeys] = useState<React.Key[]>([]);

    const getRoleInventory = (characNo: string)=>{
      const hide = message.loading('加载物品信息...');
        getRoleItem({'characNo':characNo,'type': props.editType}).then(res=>{
            hide();
            if(res.success){
                if(props.editType == 'eq'){
                    setCurrentData(res.data?.equipslot);
                    editableFormRef.current?.resetFields(res.data?.equipslot);
                }
                if(props.editType == 'inventory'){
                    setCurrentData(res.data?.inventory);
                    editableFormRef.current?.resetFields(res.data?.inventory);
                }
                if(props.editType  == 'creature'){
                    setCurrentData(res.data?.creature);
                    editableFormRef.current?.resetFields(res.data?.creature);
                }
                if(props.editType  == 'cargo'){
                    setCurrentData(res.data?.cargo);
                    editableFormRef.current?.resetFields(res.data?.cargo);
                }
                if(props.editType  == 'account_cargo'){
                    setCurrentData(res.data?.accountCargo);
                    editableFormRef.current?.resetFields(res.data?.accountCargo);
                }
                hide();
                message.success('加载物品信息成功');
            }
        })
    }

    useEffect(()=>{
        console.log(props)
        if(props.characNo && props.visible){
            getRoleInventory(props.characNo);
        }

       },[props.characNo,props.visible]);


       const columns: ProColumns<API.GameItemVo>[] = [
        {
          title: '格子位置',
          dataIndex: 'index',
          tooltip: '格子位置',
          formItemProps: (form, { rowIndex }) => {
            return {
              rules:
                rowIndex > 1 ? [{ required: true, message: '此项为必填项' }] : [],
            };
          },
          render: (text, record)=>{
            return record.index +1;
          }
          ,
          readonly: true,
          width: '15%',
        },
        {
          title: '物品id',
          dataIndex: 'itemId',
          tooltip: '物品的id,清空填0',
          width: '15%',
        },
        {
          title: '物品名称',
          dataIndex: 'itemName',
          tooltip: '物品名称(非修改项)',
          width: '15%',
          readonly: true
        },
        {
          title: '数量/品质',
          key: 'quality',
          dataIndex: 'quality',
          tooltip:'装备品级 消耗品代表数量,清空填0',
          width: '15%',
        },
        {
          title: '强化等级',
          key: 'upgrade',
          dataIndex: 'upgrade',
          tooltip:'道具填0,清空填0',
          width: '15%',
        },
        {
          title: '增幅类型',
          key: 'increaseType',
          dataIndex: 'increaseType',
          width: '15%',
          valueType: 'select',
          valueEnum: {
            0: { text: '无'},
            1: { text: '体力'},
            2: { text: '精神'},
            3: { text: '力量'},
            4: { text: '智力'},
        }
        },
        {
          title: '增幅附加值',
          key: 'increaseLevel',
          dataIndex: 'increaseLevel',
          tooltip:'最大65536,清空填0',
          width: '15%',
        },
        {
          title: '操作',
          valueType: 'option',
          width: 200,
          render: (text, record, _, action) => [
            <a
              key="editable"
              onClick={() => {
                action?.startEditable?.(record.index);
              }}
            >
              编辑
            </a>,
          ],
        },
      ];


  return (
    <EditableProTable<API.GameItemVo>
    editableFormRef={editableFormRef}
    rowKey="index"
    headerTitle={'编辑角色物品数据'}
    recordCreatorProps={false}
    maxLength={5}
    scroll={{
      x: 960,
    }}
    loading={false}
    columns={columns}
    value={currentData}
    onChange={setCurrentData}
    editable={{
      type: 'multiple',
      editableKeys,
      onSave: (rowKey, data, row) => {
        console.log(rowKey, data, row);
        updateRoleItem({
          'characNo':props.characNo,
          'type':props.editType,
          'gameItemVo':data,
        }).then(res=>{
          if(res.success && res.data){
            message.success('修改成功');
          }else{
            message.success('修改失败');
          }

        })
        setEditableRowKeys([]);
      },
      onCancel: (key, record, originRow, _newLineConfig)=>{
        console.log(key, record, originRow);
        setEditableRowKeys([]);
      },
      onDelete(key, row) {
        console.log(key, row);
        setEditableRowKeys([]);
      },
      onChange: setEditableRowKeys,
    }}
  />
  );
};

export default EditRoleItem;

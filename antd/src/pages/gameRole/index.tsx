import { Button, Dropdown, message } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { ModalForm, ProFormDigit, ProFormText } from '@ant-design/pro-form';
import { Access, useAccess } from 'umi';

import { cleanMail, gameRoleInfo, gameRolePage, gameRoleRecover, gameRoleRemove, gameRoleUpdate, openLeftAndRight, getOtherData, setOtherData, cleanRoleItem, cleanItems, roleItems, removeItems, removeAccountCargo, createAccountCargo, getAccountCargo, updateAccountCargo } from '@/services/dnf-admin/gameRoleController';
import EditRoleItem from './EditRoleItem';
import Modal from 'antd/lib/modal/Modal';
import { DeleteOutlined } from '@ant-design/icons';
import { removeMail, roleMailPage } from '@/services/dnf-admin/daMailController';

const Accounts: React.FC = () => {


  const actionRef = useRef<ActionType>();

  const actionRef1 = useRef<ActionType>();

  const actionRef2 = useRef<ActionType>();

  const access = useAccess();

  const [currentRow, setCurrentRow] = useState<API.CharacInfo>();




  /** 详情弹窗 */
  const [infoModalVisible, handleInfoModalVisible] = useState<boolean>(false);


  const openInfoModal = (characNo: string) => {
    gameRoleInfo({ 'characNo': characNo }).then(res => {
      if (res.success) {
        message.success('加载角色数据');
        setCurrentRow(res.data);
        handleInfoModalVisible(true);
      } else {
        message.error(res.errorMessage);
      }
    });
  }

  /** 装备弹窗 */
  const [eqModalVisible, handleEqModalVisible] = useState<boolean>(false);
  const [editType, setEditType] = useState<string>();


  const openEqModal = (characNo: string, editTypeParam: string) => {
    gameRoleInfo({ 'characNo': characNo }).then(res => {
      if (res.success) {
        // message.success('加载角色信息成功');
        setCurrentRow(res.data);
        setEditType(editTypeParam);
        handleEqModalVisible(true);
      } else {
        message.error(res.errorMessage);
      }
    });
  }

  const updateRole = async (fileds: API.CharacInfo) => {
    gameRoleUpdate(fileds).then(res => {
      if (res.success) {
        message.success('操作成功');
        handleInfoModalVisible(false);
        actionRef.current?.reload();
      } else {
        message.error(res.errorMessage);
      }
    })
  };



  const removeRole = (characNo: string) => {
    gameRoleRemove({ 'characNo': characNo }).then(res => {
      if (res.success) {
        message.success('操作成功');
        actionRef.current?.reload();
      } else {
        message.error(res.errorMessage);
        actionRef.current?.reload();
      }

    });
  }

  const recoverRole = (characNo: string) => {
    gameRoleRecover({ 'characNo': characNo }).then(res => {
      if (res.success) {
        message.success('操作成功');
        actionRef.current?.reload();
      } else {
        message.error(res.errorMessage);
        actionRef.current?.reload();
      }

    });
  }

  /** 其他修改弹窗 */
  const [otherEditModalVisible, handleOtherEdiModalVisible] = useState<boolean>(false);
  const [otherEditData, setOtherDataRES] = useState<API.OtherDataVo>();

  const openOtherEditModal = (characNo: string) => {
    getOtherData({ 'characNo': characNo }).then(res => {
      if (res.success) {
        message.success('加载成功');
        setOtherDataRES(res.data)
        handleOtherEdiModalVisible(true)
      } else {
        message.warning(res.errorMessage);
      }
    });
  }

    /** 时装弹窗 */
    const [roleItemsModalVisible, handleRoleItemsModalVisible] = useState<boolean>(false);
    const [currentRole, setCurrentRole] = useState();
  
    const openRoleItemsModal = (characNo: string) => {
      handleRoleItemsModalVisible(true)
      setCurrentRole(characNo);
    }


      /** 金库其他修改弹窗 */
  const [accCargoEditModalVisible, handleAccCargoEdiModalVisible] = useState<boolean>(false);
  const [accCargoEditData, setAccCargoData] = useState<API.AccountCargo>();

  const openAccCargoEditModal = (characNo: string) => {
    getAccountCargo({ 'characNo': characNo }).then(res => {
      if (res.success) {
        message.success('加载成功');
        setAccCargoData(res.data)
        handleAccCargoEdiModalVisible(true)
      } else {
        message.warning(res.errorMessage);
      }
    });
  }

    /** 角色邮件弹窗 */
    const [roleMaiModalVisible, handleRoleMaiModalVisible] = useState<boolean>(false);
      
  /** 国际化配置 */

  const columns: ProColumns<API.CharacInfo>[] = [
    {
      title: '是否在线',
      dataIndex: 'online',
      valueEnum: {
        true: '在线',
        false: '所有'
      },
      hideInTable: true,
    },
    {
      title: '账号编号',
      dataIndex: 'mid',
    },
    {
      title: '角色编号',
      dataIndex: 'characNo',
    },
    {
      title: '角色名',
      dataIndex: 'characName',
    },
    {
      title: '状态',
      dataIndex: 'deleteFlag',
      valueEnum: {
        0: { text: '正常', status: 'Success' },
        1: { text: '已删除', status: 'Error' },
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
    },
    {
      title: '修改操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) =>
          <Dropdown.Button menu={{
            items: [
              {
                key: '1',
                label: '属性',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  setCurrentRow(record);
                  openInfoModal(record.characNo);
                }
              },
              {
                key: '2',
                label: '穿戴栏',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  openEqModal(record.characNo, 'eq');
                }
              },
              {
                key: '3',
                label: '背包',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  openEqModal(record.characNo, 'inventory');
                }
              },
              {
                key: '4',
                label: '宠物栏',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  openEqModal(record.characNo, 'creature');
                }
              },
              {
                key: '5',
                label: '角色仓库',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  openEqModal(record.characNo, 'cargo');
                }
              },
              {
                key: '6',
                label: '账号金库',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  openEqModal(record.characNo, 'account_cargo');
                }
              },
              {
                key: 'create_acc_cargo',
                label: '开通金库',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  createAccountCargo({'characNo':record.characNo}).then(res=>{
                    if(res.success){
                      message.success(res.message)
                    }else{
                      message.warn(res.errorMessage)
                    }
                  })
                }
              },
              {
                key: 'update_acc_cargo',
                label: '金库信息',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  openAccCargoEditModal(record.characNo);
                }
              },
              {
                key: '7',
                label: '时装栏',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  openRoleItemsModal(record.characNo);
                }
              },
              {
                key: '8',
                label: '其他修改',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  openOtherEditModal(record.characNo)
                }
              },
              {
                key: '9',
                label: '开启左右槽',
                disabled: !access.hashPre('gameRole.openLeftAndRight'),
                onClick: (e) => {
                  openLeftAndRight({ 'characNo': record.characNo }).then(res => {
                    if (res.success) {
                      message.success(res.message)
                    } else {
                      message.warn(res.errorMessage)
                    }
                  })
                }
              },
            ], onClick: (e) => console.log(e)
          }}>
            修改
          </Dropdown.Button>
    },
    {
      title: '删除操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) =>
          <Dropdown.Button menu={{
            items: [
              {
                key: 'remove_acc_cargo',
                label: '删除金库',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  removeAccountCargo({'characNo':record.characNo}).then(res=>{
                    if(res.success){
                      message.success(res.message)
                    }else{
                      message.warn(res.errorMessage)
                    }
                  })
                }
              },
              {
                key: '10',
                label: '恢复角色',
                disabled: record.deleteFlag == 0 || !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  recoverRole(record.characNo);
                }
              },
              {
                key: '11',
                label: '删除角色',
                disabled: record.deleteFlag == 1 || !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  removeRole(record.characNo);
                }
              },
              {
                key: 'role_mail',
                label: '邮件列表',
                disabled: !access.hashPre('mail'),
                onClick: (e) => {
                  handleRoleMaiModalVisible(true);
                  setCurrentRole(record.characNo)
                }
              },
              {
                key: '12',
                label: '清空邮件',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => {
                  cleanMail({ 'characNo': record.characNo }).then(res => {
                    if (res.success) {
                      message.success(res.message)
                    } else {
                      message.warning(res.errorMessage)
                    }
                  })
                }
              },
              {
                key: '13',
                label: '清空穿戴',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => cleanRoleItem({ 'characNo': record.characNo, 'type': 1 }).then(res => {
                  if (res.success) {
                    message.success(res.message)
                  } else {
                    message.warning(res.errorMessage)
                  }
                })

              },
              {
                key: '14',
                label: '清空背包',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => cleanRoleItem({ 'characNo': record.characNo, 'type': 2 }).then(res => {
                  if (res.success) {
                    message.success(res.message)
                  } else {
                    message.warning(res.errorMessage)
                  }
                })

              },
              {
                key: '15',
                label: '清空宠物栏',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => cleanRoleItem({ 'characNo': record.characNo, 'type': 3 }).then(res => {
                  if (res.success) {
                    message.success(res.message)
                  } else {
                    message.warning(res.errorMessage)
                  }
                })

              },
              {
                key: '16',
                label: '清空仓库',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => cleanRoleItem({ 'characNo': record.characNo, 'type': 4 }).then(res => {
                  if (res.success) {
                    message.success(res.message)
                  } else {
                    message.warning(res.errorMessage)
                  }
                })

              },
              {
                key: '17',
                label: '清空金库',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => cleanRoleItem({ 'characNo': record.characNo, 'type': 5 }).then(res => {
                  if (res.success) {
                    message.success(res.message)
                  } else {
                    message.warning(res.errorMessage)
                  }
                })

              },
              {
                key: '18',
                label: '清空时装栏',
                disabled: !access.hashPre('gameRole.update'),
                onClick: (e) => cleanItems({ 'characNo': record.characNo}).then(res => {
                  if (res.success) {
                    message.success(res.message)
                  } else {
                    message.warning(res.errorMessage)
                  }
                })

              },
            ], onClick: (e) => console.log(e)
          }}>
            删除
          </Dropdown.Button>
    },
  ];

  return (
    <PageContainer>
      <ProTable<API.CharacInfo, API.CharacInfoQo>
        headerTitle="角色列表"
        actionRef={actionRef}
        rowKey="uid"
        search={{
          labelWidth: 120,
        }}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: false,
        }}
        request={gameRolePage}
        columns={columns}
      />
      <ModalForm
        title="角色详情"
        visible={infoModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={currentRow}
        onVisibleChange={(value) => {
          handleInfoModalVisible(value);
        }}
        onFinish={updateRole}
      >
        <ProFormText name="characNo" hidden />
        <ProFormText
          name="characName"
          label="角色名"
        />
        <ProFormDigit
          min={1}
          name="lev"
          label="等级"
        />
        <ProFormDigit
          min={1}
          name="maxHP"
          label="HP"
        />
        <ProFormDigit
          min={1}
          name="maxMP"
          label="MP"
        />
        <ProFormDigit
          min={0}
          name="hpRegen"
          label="HP恢复"
        />
        <ProFormDigit
          min={0}
          name="mpRegen"
          label="MP恢复"
        />
        <ProFormDigit
          min={0}
          name="phyAttack"
          label="力量"
        />
        <ProFormDigit
          min={0}
          name="magAttack"
          label="智力"
        />
        <ProFormDigit
          min={0}
          name="phyDefense"
          label="体力"
        />
        <ProFormDigit
          min={0}
          name="magDefense"
          label="精神"
        />
        <ProFormDigit
          min={1}
          name="attackSpeed"
          label="攻击速度"
        />
        <ProFormDigit
          min={1}
          name="moveSpeed"
          label="移动速度"
        />
        <ProFormDigit
          min={1}
          name="castSpeed"
          label="释放速度"
        />
        <ProFormDigit
          min={1}
          name="invenWeight"
          label="负重"
        />
        <ProFormDigit
          min={1}
          name="hitRecovery"
          label="硬直度"
        />
        <ProFormDigit
          min={1}
          name="jump"
          label="跳跃力"
        />
      </ModalForm>
      <Modal
        title="角色物品信息"
        style={{ minHeight: '50%', minWidth: '80%' }}
        open={eqModalVisible}
        destroyOnClose
        onOk={() => handleEqModalVisible(false)}
        onCancel={() => handleEqModalVisible(false)}
        okText={'确定'}
        cancelText={'关闭'}
      >
        <EditRoleItem characNo={currentRow?.characNo} visible={eqModalVisible} editType={editType} />
      </Modal>
      <Modal
        title="时装信息"
        style={{ minHeight: '50%', minWidth: '50%' }}
        open={roleItemsModalVisible}
        destroyOnClose
        onOk={() => {
          handleRoleItemsModalVisible(false)
        }
        }
        onCancel={() => {
          handleRoleItemsModalVisible(false)
        }
        }
        okText={'确定'}
        cancelText={'关闭'}
      >
        <ProTable<API.UserItems, API.CharacInfoQo>
        actionRef={actionRef1}
        headerTitle="时装列表"
        rowKey="uiId"
        search={false}
        pagination={{pageSize:10}}
        request={      
          ()=>roleItems({ 'characNo': currentRole }).then(res => {
          if (res.success) {
            message.success('加载成功');
            return res;
          } else {
            message.warning(res.errorMessage);
            return [];
          }
        })}
        columns={[
          {
            title: '位置',
            dataIndex: 'slot',
          },
          {
            title: '时装ID',
            dataIndex: 'itId',
          },
          {
            title: '名称',
            dataIndex: 'itemName',
          },
          {
            title: '操作',
            dataIndex: 'option',
            valueType: 'option',
            render: (_, record) =>{             
              return <Button type="primary" 
                icon={<DeleteOutlined />}
                onClick={() => {
                  removeItems({'uiId':record.uiId}).then(res=>{
                    if(res.success){
                      actionRef1.current?.reload()
                      message.success(res.message)
                    }else{
                      message.warning(res.errorMessage)
                    }
                  })
                }}
              >
              删除
            </Button>
            }
          }
        ]}
      />
      </Modal>
      <ModalForm
        title="其他修改"
        visible={otherEditModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={otherEditData}
        onVisibleChange={(value) => {
          handleOtherEdiModalVisible(value);
        }}
        onFinish={(values) => {
          values.oldData = otherEditData;
          setOtherData(values).then(res => {
            if (res.success) {
              message.success('操作成功');
              handleOtherEdiModalVisible(false);
              actionRef.current?.reload();
            } else {
              message.error(res.errorMessage);
            }
          })
        }}
      >
        <ProFormDigit
          hidden
          name="characNo"
        />
        <ProFormDigit
          min={0}
          name="pvpGrade"
          label="段位"
        />
        <ProFormDigit
          min={0}
          name="pvpPoint"
          label="胜点"
        />
        <ProFormDigit
          min={0}
          name="win"
          label="胜场"
        />
        <ProFormDigit
          min={0}
          name="money"
          label="金币"
        />
        <ProFormDigit
          min={0}
          name="sp"
          label="SP点"
        />
        <ProFormDigit
          min={0}
          name="tp"
          label="TP点"
        />
        <ProFormDigit
          min={0}
          name="qp"
          label="QP点"
        />
        <ProFormDigit
          min={0}
          name="avatarCoin"
          label="时装币"
        />
      </ModalForm>
      <ModalForm
        title="金库其他信息"
        visible={accCargoEditModalVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        initialValues={accCargoEditData}
        onVisibleChange={(value) => {
          handleAccCargoEdiModalVisible(value);
        }}
        onFinish={(values) => {
          updateAccountCargo(values).then(res => {
            if (res.success) {
              message.success('操作成功');
              handleAccCargoEdiModalVisible(false);
              actionRef.current?.reload();
            } else {
              message.error(res.errorMessage);
            }
          })
        }}
      >
        <ProFormDigit
          hidden
          name="mid"
        />
        <ProFormDigit
          min={8}
          max={120}
          name="capacity"
          label="格子"
        />
        <ProFormDigit
          min={0}
          name="money"
          label="金币"
        />
     
      </ModalForm>
      <Modal
        title="角色邮件"
        style={{ minHeight: '50%', minWidth: '50%' }}
        open={roleMaiModalVisible}
        destroyOnClose
        onOk={() => {
          handleRoleMaiModalVisible(false)
        }
        }
        onCancel={() => {
          handleRoleMaiModalVisible(false)
        }
        }
        okText={'确定'}
        cancelText={'关闭'}
      >
        <ProTable<API.Postal, API.RoleMailPageQo>
        actionRef={actionRef2}
        headerTitle="邮件列表"
        rowKey="postalId"
        search={false}
        pagination={{pageSize:10}}
        request={      
          (param)=> roleMailPage({ 'characNo': currentRole },param).then(res => {
          if (res.success) {
            message.success('加载成功');
            return res;
          } else {
            message.warning(res.errorMessage);
            return [];
          }
        })}
        columns={[
          {
            title: '发送人',
            dataIndex: 'sendCharacName',
          },
          {
            title: '物品id',
            dataIndex: 'itemId',
          },
          {
            title: '物品名',
            dataIndex: 'itemName',
          },
          {
            title: '发送时间',
            dataIndex: 'occTime',
          },
          {
            title: '操作',
            dataIndex: 'option',
            valueType: 'option',
            render: (_, record) =>{             
              return <Button type="primary" 
                icon={<DeleteOutlined />}
                onClick={() => {
                  removeMail({'postalId':record.postalId}).then(res=>{
                    if(res.success){
                      actionRef2.current?.reload()
                      message.success(res.message)
                    }else{
                      message.warning(res.errorMessage)
                    }
                  })
                }}
              >
              删除
            </Button>
            }
          }
        ]}
      />
      </Modal>
    </PageContainer>
  );
};

export default Accounts;

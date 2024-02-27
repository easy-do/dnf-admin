import { FC, useEffect, useState } from 'react';
import { Skeleton, Statistic, Button, Tag, message, Modal } from 'antd';

import { Access, request, useAccess, useModel } from 'umi';
import { PageContainer } from '@ant-design/pro-layout';
import styles from './style.less';
import { roleList } from '@/services/dnf-admin/gameRoleController';
import {
  generateKeyPem,
  getGameToken,
  onlineCount,
  restartAdmin,
  restartDb,
  restartServer,
} from '@/services/dnf-admin/gameToolController';
import ProList from '@ant-design/pro-list';
import { ModalForm, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form';
import { useCdk } from '@/services/dnf-admin/daCdkController';
import { generateCaptchaV2 } from '@/services/dnf-admin/captchaController';
import React from 'react';

const PageHeaderContent: FC<{ currentUser: API.CurrentUser }> = ({ currentUser }) => {
  const loading = currentUser && Object.keys(currentUser).length;
  if (!loading) {
    return <Skeleton avatar paragraph={{ rows: 1 }} active />;
  }
  return (
    <div className={styles.pageHeaderContent}>
      {/* <div className={styles.avatar}>
        <Avatar size="large" src={''} />
      </div> */}
      <div className={styles.content}>
        <div className={styles.contentTitle}>
          你好，
          {currentUser.accountname}
          ，祝你开心每一天！
        </div>
        {/* <div>
          {currentUser.title} |{currentUser.group}
        </div> */}
      </div>
    </div>
  );
};

const Home: React.FC = () => {
  const { initialState } = useModel('@@initialState');

  const [cardActionProps, setCardActionProps] = useState<'actions' | 'extra'>('extra');

  const [roleListData, setRoleListData] = useState<any>([]);

  const [roleCardData, setRoleCardData] = useState<any>([]);

  const access = useAccess();

  const [imageData, setImageData] = useState<API.CaptchaVo>();

  const onClickImage = () => {
    generateCaptchaV2({ key: imageData?.key }).then((res) => {
      setImageData(res.data);
    });
  };

  const startGame = () => {
    const client = window.daGameClient;
    const path = localStorage.getItem('daClientPath');
    getGameToken().then((res) => {
      if (res.success) {
        const args = path + ' ' + res.data;
        client.startGame(args);
        message.success('启动客户端。。。。');
      } else {
        message.success('获取客户端token失败');
      }
    });
  };

  const [useCdkVisible, handleUseCdkVisible] = useState<boolean>(false);

  const useCdkFun = (values) => {
    try {
      useCdk({
        characNo: values.characNo,
        cdks: values.cdks.split('\n'),
        verificationCode: values.verificationCode,
        captchaKey: imageData?.key,
      }).then((res) => {
        if (res.success) {
          message.success(res.message);
          handleUseCdkVisible(false);
        }
      });
    } catch (error) {}
  };

  const restartMysql = () => {
    restartDb().then((res) => {
      message.success(res.message);
    });
  };
  const restartServer1 = () => {
    restartServer().then((res) => {
      message.success(res.message);
    });
  };
  const restartDa = () => {
    restartAdmin().then((res) => {
      message.success(res.message);
    });
  };
  const generateKey = () => {
    generateKeyPem().then((res) => {
      message.success(res.message);
    });
  };

  const [adVisible, handleAdVisible] = useState<boolean>(true);

  function getAdList(options?: { [key: string]: any }) {
    return request<any>('https://magic.easydo.plus/api/adlist', {
      method: 'GET',
      ...(options || {}),
    });
  }

  const [dataSource, setDatasource] = useState([]);

  const ExtraContent: FC<Record<string, any>> = () => (
    <div className={styles.extraContent}>
      <Access accessible={access.hashPre('tool.startGame') && !access.utilsMode()}>
        <div className={styles.statItem}>
          <Button size="large" type="primary" onClick={startGame}>
            启动DNF
          </Button>
        </div>
      </Access>
      <div className={styles.statItem}>
        <Button size="large" type="primary" onClick={() => handleUseCdkVisible(true)}>
          CDK兑换
        </Button>
      </div>
      <Access accessible={access.hashPre('tool.restartServer') && !access.utilsMode()}>
        <div className={styles.statItem}>
          <Button size="large" type="primary" onClick={restartServer1}>
            重启服务端
          </Button>
        </div>
      </Access>
      <Access accessible={access.hashPre('tool.restartDb') && !access.utilsMode()}>
        <div className={styles.statItem}>
          <Button size="large" type="primary" onClick={restartMysql}>
            重启数据库
          </Button>
        </div>
      </Access>
      {/* <Access accessible={access.hashPre('tool.restartDa')}>
      <div className={styles.statItem}>
        <Button size='large' type='primary' onClick={restartDa}>重启后台</Button>
      </div>
      </Access> */}
      <Access accessible={access.hashPre('tool.generateKeyPem') && !access.utilsMode()}>
        <div className={styles.statItem}>
          <Button size="large" type="primary" onClick={generateKey}>
            更换密钥
          </Button>
        </div>
      </Access>

      <div className={styles.statItem}>
        <Statistic title="角色数" value={roleListData.length} />
      </div>
      <div className={styles.statItem}>
        <Statistic title="在线" value={onlineCountData.online} suffix={'/'+onlineCountData.count} />
      </div>
    </div>
  );

  const [onlineCountData, setOnlineCountData] = useState<API.OnlineCountVo>({
    'count' :'0',
    'online' :'0'
  });

  useEffect(() => {
    roleList({}).then((res) => {
      if (res.success) {
        setRoleListData(res.data);
        const data = res.data.map((role) => ({
          title: (
            <>
              <Tag color="#5BD8A6">{role.characName}</Tag>
            </>
          ),
          subTitle: (
            <>
              职业: <Tag color="#5BD8A6">{role.jobName}</Tag>
            </>
          ),
          actions: [
            <>
              等级: <Tag color="#5BD8A6">{role.lev}</Tag>
            </>,
          ],
          content: (
            <div
              style={{
                flex: 1,
              }}
            >
              <div
                style={{
                  width: 200,
                }}
              >
                副职: <Tag color="#5BD8A6">{role.expertJobName}</Tag>
              </div>
            </div>
          ),
        }));
        setRoleCardData(data);
      }
    });
    getAdList().then((res) => {
      if (res.success) {
        try {
          const data = [];
          for (let index = 0; index < res.data.length; index++) {
            const ad = res.data[index];
            const tags = [];
            if (ad.tag) {
              const tagStrs = ad.tag.split(',');
              for (let index1 = 0; index1 < tagStrs.length; index1++) {
                const element = tagStrs[index1];
                tags.push(<Tag color={'blue'}>{element}</Tag>);
              }
            }
            data.push({
              title: (
                <span
                  style={{ fontWeight: 'bold' }}
                  onClick={() => {
                    window.open(ad.url);
                  }}
                >
                  {ad.title}
                </span>
              ),
              description: <>{tags}</>,
              content: <span style={{ color: 'red' }}>{ad.content}</span>,
              // extra: <img width={160} alt="logo" src={ad.logo} />,
            });
          }
          setDatasource(data);
        } catch (error) {}
      }
    });
    onlineCount().then(res=>{
      if(res.success){
        setOnlineCountData(res.data);
      }
    })
  }, []);

  return (
    <PageContainer
      content={<PageHeaderContent currentUser={initialState?.currentUser} />}
      extraContent={<ExtraContent />}
    >
      <ProList<any>
        // ghost={ghost}
        // itemCardProps={{
        //   ghost,
        // }}
        pagination={{
          defaultPageSize: 6,
          showSizeChanger: false,
        }}
        showActions="hover"
        grid={{ gutter: 16, column: 2 }}
        onItem={(record: any) => {
          return {
            onMouseEnter: () => {
              console.log(record);
            },
            onClick: () => {
              console.log(record);
            },
          };
        }}
        metas={{
          title: {},
          subTitle: {},
          type: {},
          avatar: {},
          content: {},
          actions: {
            cardActionProps,
          },
        }}
        headerTitle="角色列表"
        dataSource={roleCardData}
      />
      <ModalForm
        title="兑换CDK"
        visible={useCdkVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        onVisibleChange={(value) => {
          handleUseCdkVisible(value);
        }}
        onFinish={useCdkFun}
      >
        <ProFormSelect
          name="characNo"
          label="角色"
          fieldProps={{
            suffixIcon: null,
            showSearch: true,
            labelInValue: false,
            autoClearSearchValue: true,
            fieldNames: {
              label: 'characName',
              value: 'characNo',
            },
          }}
          request={() =>
            roleList({}).then((res) => {
              return res.data;
            })
          }
          rules={[
            {
              required: true,
              message: '请选择角色',
            },
          ]}
        />
        <ProFormTextArea
          name="cdks"
          label="CDK码,一行一个"
          rules={[
            {
              required: true,
              message: 'CDK码不能为空',
            },
          ]}
        />
        <ProFormText
          name="verificationCode"
          label="验证码"
          placeholder={'请输入验证码'}
          fieldProps={{
            onMouseDown: () => {
              onClickImage();
            },
          }}
          rules={[
            {
              required: true,
              message: '请输入验证码',
            },
          ]}
        />
        <img
          style={{
            width: '50%',
            height: '60px',
            verticalAlign: 'middle',
            padding: '0px 0px 0px 0px',
          }}
          src={imageData?.img}
          onClick={onClickImage}
        />
      </ModalForm>
      <Modal
        open={adVisible}
        onCancel={() => handleAdVisible(false)}
        okText={undefined}
        destroyOnClose
        // title={'广告'}
        footer={null}
        style={{ minHeight: '50%', minWidth: '30%' }}
      >
        <ProList<{ title: any }>
          // toolBarRender={() => {
          //   return [
          //     <Button key="1" type="link">
          //       加入
          //     </Button>,
          //   ];
          // }}
          itemLayout="vertical"
          rowKey="id"
          headerTitle=""
          dataSource={dataSource}
          itemCardProps={{ onClick: () => {} }}
          metas={{
            title: {},
            description: {},
            extra: {},
            content: {},
          }}
        />
      </Modal>
    </PageContainer>
  );
};

export default Home;

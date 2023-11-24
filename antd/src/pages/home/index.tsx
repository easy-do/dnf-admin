import { FC, useEffect, useState } from 'react';
import { Skeleton,  Statistic, Button, Tag, message } from 'antd';

import { Access, useAccess, useModel } from 'umi';
import { PageContainer } from '@ant-design/pro-layout';
import styles from './style.less';
import { roleList } from '@/services/dnf-admin/gameRoleController';
import { generateKeyPem, getGameToken, restartAdmin, restartDb, restartServer } from '@/services/dnf-admin/gameToolController';
import ProList from '@ant-design/pro-list';

const PageHeaderContent: FC<{ currentUser: API.CurrentUser}> = ({ currentUser }) => {



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

  const [ghost, setGhost] = useState<boolean>(false);

  const [cardActionProps, setCardActionProps] = useState<'actions' | 'extra'>(
    'extra',
  );

   const [roleListData,setRoleListData] = useState<any>([]);

   const [roleCardData,setRoleCardData] = useState<any>([]);

   const access = useAccess();

   const startGame = ()=> {
    const client = window.daGameClient;
    const path = localStorage.getItem('daClientPath');
    getGameToken().then((res)=>{
      if(res.success){
        const args = (path+' '+res.data)
        client.startGame(args)
        message.success('启动客户端。。。。')
      }else{
        message.success('获取客户端token失败')
      }

    })
  }


  const restartMysql = ()=> {
    restartDb().then((res)=>{
      message.success(res.message)
    })
  }
  const restartServer1 = ()=> {
    restartServer().then((res)=>{
      message.success(res.message)
    })
  }
  const restartDa = ()=> {
    restartAdmin().then((res)=>{
      message.success(res.message)
    })
  }
  const generateKey = ()=> {
    generateKeyPem().then((res)=>{
      message.success(res.message)
    })
  }


   const ExtraContent: FC<Record<string, any>> = () => (
    <div className={styles.extraContent}>
      <div className={styles.statItem}>
        <Button size='large' type='primary' onClick={startGame}>启动DNF</Button>
      </div>
      <Access accessible={access.hashPre('tool.restartServer')}>
      <div className={styles.statItem}>
        <Button size='large' type='primary' onClick={restartServer1}>重启服务端</Button>
      </div>
      </Access>
      <Access accessible={access.hashPre('tool.restartDb')}>
      <div className={styles.statItem}>
        <Button size='large' type='primary' onClick={restartMysql}>重启数据库</Button>
      </div>
      </Access>
      <Access accessible={access.hashPre('tool.restartDa')}>
      <div className={styles.statItem}>
        <Button size='large' type='primary' onClick={restartDa}>重启后台</Button>
      </div>
      </Access>
      <Access accessible={access.hashPre('tool.generateKeyPem')}>
      <div className={styles.statItem}>
        <Button size='large' type='primary' onClick={generateKey}>更换密钥</Button>
      </div>
      </Access>

      <div className={styles.statItem}>
        <Statistic title="角色数" value={roleListData.length} />
      </div>
      <div className={styles.statItem}>
        <Statistic title="排名" value={1} suffix="/ 88" />
      </div>
      <div className={styles.statItem}>
        <Statistic title="战力" value={999999999} />
      </div>
    </div>
  );


   useEffect(()=>{
    roleList({}).then((res)=>{
      if(res.success){
        setRoleListData(res.data)
       const data =  res.data.map((role) => ({
          title: <><Tag color="#5BD8A6">{role.characName}</Tag></>,
          subTitle: <>职业: <Tag color="#5BD8A6">{role.jobName}</Tag></>,
          actions: [<>等级: <Tag color="#5BD8A6">{role.lev}</Tag></>],
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
        }))
        setRoleCardData(data)
      }
    })
   },[])


  return (
    <PageContainer
      content={
        <PageHeaderContent
          currentUser={initialState?.currentUser}
        />
      }
      extraContent={<ExtraContent />}
    >
      <ProList<any>
        // ghost={ghost}
        // itemCardProps={{
        //   ghost,
        // }}
        pagination={{
          defaultPageSize: 100,
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
    </PageContainer>
  );
};

export default Home;




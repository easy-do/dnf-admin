import { FC, useEffect, useState } from 'react';
import { Skeleton,  Statistic, Button, Tag, Progress } from 'antd';

import { useModel } from 'umi';
import { PageContainer } from '@ant-design/pro-layout';
import styles from './style.less';
import { roleList } from '@/services/dnf-admin/gameRoleController';
import { getGameToken } from '@/services/dnf-admin/gameToolController';
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
          早安，
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

const ExtraContent: FC<Record<string, any>> = () => (
  <div className={styles.extraContent}>
    <div className={styles.statItem}>
      <Button size='large' type='primary'>DNF,启动!</Button>
    </div>
    <div className={styles.statItem}>
      <Statistic title="角色数" value={88} />
    </div>
    <div className={styles.statItem}>
      <Statistic title="排名" value={1} suffix="/ 88" />
    </div>
    <div className={styles.statItem}>
      <Statistic title="战力" value={999999999} />
    </div>
  </div>
);

const Home: React.FC = () => {

  const { initialState } = useModel('@@initialState');

  const [ghost, setGhost] = useState<boolean>(false);

  const [cardActionProps, setCardActionProps] = useState<'actions' | 'extra'>(
    'extra',
  );
  

  const startGame = () => {
    const client = window.daGameClient;
    const path = localStorage.getItem('daClientPath');
    getGameToken().then((res)=>{
      const args = (path+' '+res.data)
      client.startGame(args)
    })
   }
   const [roleListData,setRoleListData] = useState<any>([]);

   const [roleCardData,setRoleCardData] = useState<any>([]);


   useEffect(()=>{
    roleList({}).then((res)=>{
      if(res.success){
        setRoleListData(res.data)
       const data =  res.data.map((role) => ({
          title: role.characName,
          subTitle: <Tag color="#5BD8A6">{role.expertJobName}</Tag>,
          actions: [<a key="delete">详情</a>],
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
                <Progress percent={role.lev} />
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
        ghost={ghost}
        itemCardProps={{
          ghost,
        }}
        pagination={{
          defaultPageSize: 100,
          showSizeChanger: false,
        }}
        showActions="hover"
        rowSelection={{}}
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

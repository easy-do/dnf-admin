import type { FC } from 'react';
import { Avatar, Card, Skeleton, Row, Statistic } from 'antd';

import { Link, useRequest,useModel } from 'umi';
import { PageContainer } from '@ant-design/pro-layout';
import moment from 'moment';
import styles from './style.less';
import { queryProjectNotice} from './service';
import { roleList } from '@/services/dnf-admin/gameRoleController';


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


  const { loading: roleLoading, data: roleListData = [] } = useRequest(roleList);

  return (
    <PageContainer
      content={
        <PageHeaderContent
          currentUser={initialState?.currentUser}
        />
      }
      extraContent={<ExtraContent />}
    >
      <Row gutter={24}>
          {/* <Card
            className={styles.projectList}
            style={{ marginBottom: 24 }}
            title="角色信息"
            bordered={false}
            loading={roleLoading}
            bodyStyle={{ padding: 0 }}
          >
            {roleListData.map((item) => (
              <Card.Grid className={styles.projectGrid} key={item.characNo}>
                <Card bodyStyle={{ padding: 0 }} bordered={false}>
                  <Card.Meta
                    title={
                      <div className={styles.cardTitle}>
                        <Avatar size="small" src={item.logo} />
                        <Link to='/singin'>{item.characName}</Link>
                      </div>
                    }
                    description={item.jobName}
                  />
                  <div className={styles.projectItemContent}>
                    <Link to={item.memberLink}>{item.member || ''}</Link>
                    {item.createTime && (
                      <span className={styles.datetime} title={item.createTime}>
                        {moment(item.createTime).fromNow()}
                      </span>
                    )}
                  </div>
                </Card>
              </Card.Grid>
            ))}
          </Card> */}
      </Row>
    </PageContainer>
  );
};

export default Home;

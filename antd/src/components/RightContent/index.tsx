import { Space } from 'antd';
import { QqOutlined, QuestionCircleOutlined } from '@ant-design/icons';
import React from 'react';
import { useModel, SelectLang } from 'umi';
import Avatar from './AvatarDropdown';
import HeaderSearch from '../HeaderSearch';
import styles from './index.less';
import NoticeIconView from '../NoticeIcon';

export type SiderTheme = 'light' | 'dark';

const GlobalHeaderRight: React.FC = () => {
  const { initialState } = useModel('@@initialState');

  if (!initialState || !initialState.settings) {
    return null;
  }

  const { navTheme, layout } = initialState.settings;
  let className = styles.right;

  if ((navTheme === 'dark' && layout === 'top') || layout === 'mix') {
    className = `${styles.right}  ${styles.dark}`;
  }

  return (
    <Space className={className}>
      <HeaderSearch
        className={`${styles.action} ${styles.search}`}
        placeholder="站内搜索"
        defaultValue="dnf-admin"
        options={[
          {
            label: <a href="http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=jKliJIxAFvZoZxBSw1NnlMjOj8pRR42f&authKey=vnozKSs2ou1MO68VXH1ct2AReURSyIj4jlVe%2BVAlA5h%2F0M1BsdhQP0YN6MqwRwBB&noverify=0&group_code=154213998">交流群</a>,
            value: '交流群',
          },
          {
            label: <a href="https://github.com/easy-do/dnf-admin">github仓库</a>,
            value: 'github',
          },
          {
            label: <a href="https://gitee.com/yuzhanfeng/dnf-admin">gitee仓库</a>,
            value: 'gitee',
          },
          {
            label: <a href="https://www.bilibili.com/video/BV1ju4y187SS/">视频教程</a>,
            value: 'video',
          },
          {
            label: <a href="https://blog.easydo.plus">主页</a>,
            value: 'home',
          },
        ]} // onSearch={value => {
        //   console.log('input', value);
        // }}
      />
      <span
        className={styles.action}
        onClick={() => {
          window.open('http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=jKliJIxAFvZoZxBSw1NnlMjOj8pRR42f&authKey=vnozKSs2ou1MO68VXH1ct2AReURSyIj4jlVe%2BVAlA5h%2F0M1BsdhQP0YN6MqwRwBB&noverify=0&group_code=154213998');
        }}
      >
        <QqOutlined />
      </span>
      <span
        className={styles.action}
        onClick={() => {
          window.open('https://pro.ant.design/docs/getting-started');
        }}
      >
        <QuestionCircleOutlined />
      </span>
      <NoticeIconView />
      <Avatar menu />
      <SelectLang className={styles.action} />
    </Space>
  );
};

export default GlobalHeaderRight;

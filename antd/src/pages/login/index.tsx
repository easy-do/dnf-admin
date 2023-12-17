import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { message, Space, Tabs } from 'antd';
import React, { useState } from 'react';
import { ProFormText, LoginForm, ModalForm } from '@ant-design/pro-form';
import { useIntl, history, FormattedMessage, SelectLang, useModel } from 'umi';
import Footer from '@/components/Footer';
import { login, reg } from '@/services/dnf-admin/userController';

import styles from './index.less';
import Link from 'antd/lib/typography/Link';


const Login: React.FC = () => {
  const [type, setType] = useState<string>('account');
  const { initialState, setInitialState } = useModel('@@initialState');
  const [settingModal, handlerSettingModal] = useState<boolean>(false);
  const [regModal, handlerRegModal] = useState<boolean>(false);

  const intl = useIntl();

  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();
    if (userInfo) {
      await setInitialState((s) => ({
        ...s,
        currentUser: userInfo,
      }));
    }
  };

  const handleSubmit = async (values: API.LoginDto) => {
    // 登录
    const res = await login({ ...values });
    if (res.success) {
      localStorage.setItem('Authorization', res.data);
      const defaultLoginSuccessMessage = intl.formatMessage({
        id: 'pages.login.success',
        defaultMessage: '登录成功！',
      });
      message.success(defaultLoginSuccessMessage);
      await fetchUserInfo();
      /** 此方法会跳转到 redirect 参数所在的位置 */
      if (!history) return;
      const { query } = history.location;
      const { redirect } = query as { redirect: string };
      history.push(redirect || '/');
      return;
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.lang} data-lang>
        {SelectLang && <SelectLang />}
      </div>
      <div className={styles.content}>
        <LoginForm
          title="DNF-ADMIN"
          subTitle={intl.formatMessage({ id: 'pages.layouts.userLayout.title' })}
          initialValues={{
            autoLogin: true,
          }}
          onFinish={async (values) => {
            await handleSubmit(values as API.LoginDto);
          }}
          actions={
            <Space>
              <Link onClick={() => handlerSettingModal(true)} >服务设置 </Link>
              <Link onClick={() => handlerRegModal(true)} >账号注册 </Link>
            </Space>
          }
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane
              key="account"
              tab={intl.formatMessage({
                id: 'pages.login.accountLogin.tab',
                defaultMessage: '账户密码登录',
              })}
            />
          </Tabs>

          <>
            <ProFormText
              name="userName"
              fieldProps={{
                size: 'large',
                prefix: <UserOutlined className={styles.prefixIcon} />,
              }}
              placeholder={intl.formatMessage({
                id: 'pages.login.username.placeholder',
                defaultMessage: '用户名: 游戏账户',
              })}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.username.required"
                      defaultMessage="请输入用户名!"
                    />
                  ),
                },
              ]}
            />
            <ProFormText.Password
              name="password"
              fieldProps={{
                size: 'large',
                prefix: <LockOutlined className={styles.prefixIcon} />,
              }}
              placeholder={intl.formatMessage({
                id: 'pages.login.password.placeholder',
                defaultMessage: '密码',
              })}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.password.required"
                      defaultMessage="请输入密码！"
                    />
                  ),
                },
              ]}
            />
          </>
        </LoginForm>
        <ModalForm
          modalProps={{
            destroyOnClose: true,
          }}
          title="服务配置"
          visible={settingModal}
          onVisibleChange={handlerSettingModal}
          onFinish={(values) => {
            localStorage.setItem('daCustomUrl', values.daCustomUrl);
            localStorage.setItem('daClientPath', values.daClientPath);
            message.success('配置成功');
            handlerSettingModal(false);
          }}

        >
          <ProFormText
            name="daCustomUrl"
            label="服务地址"
            initialValue={localStorage.getItem('daCustomUrl')}
            placeholder={'http://localhost:8888'}
          />
          <ProFormText
           name="daClientPath"
           label="客户端地址"
           initialValue={localStorage.getItem('daClientPath')}
           placeholder={'客户端根目录'}
          />
        </ModalForm>
        <ModalForm
          modalProps={{
            destroyOnClose: true,
          }}
          title="账号注册"
          visible={regModal}
          onVisibleChange={handlerRegModal}
          onFinish={(values: API.RegDto)=>{
            reg(values).then((res=>{
              if(res.success){
                message.success('注册成功')
                handlerRegModal(false)
              }
            }))
          }
          }
        >
          <ProFormText
            name="userName"
            label="账户"
            rules={[
              {
                required: true,
                message: '请输入账号'
              },
            ]}
          />
          <ProFormText
            name="password"
            label="密码"
            rules={[
              {
                required: true,
                message: '请输入密码'
              },
            ]}
          />
        </ModalForm>
      </div>
      <Footer />
    </div>
  );
};

export default Login;

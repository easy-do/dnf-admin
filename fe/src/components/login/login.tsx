'use client';
import { Button, Checkbox, Form } from '@douyinfe/semi-ui';
import React, { useState } from 'react';
import cookie from 'react-cookies';

import styles from './index.module.scss';

import loginRequest from '../../pages/api/userApi';

const Login = () => {
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');

  const loginSubmit = async () => {
    const data = await loginRequest('/user/login', {
      userName: userName,
      password: password,
    });
    console.log(data);
    if (data.success) {
      cookie.save('token', data.data.token);
      localStorage.setItem('token', data.data.token);
    }
  };

  return (
    <div className={styles.rootMain}>
      <div className={styles.login}>
        <div className={styles.component66}>
          <img
            src='https://p26-semi-asset.byteimg.com/tos-cn-i-acvclvrq33/b300a4b292184d44942077fecd924a23.png'
            className={styles.logo}
          />
          <div className={styles.header}>
            <p className={styles.title}>欢迎回来</p>
          </div>
        </div>
        <div className={styles.form}>
          <Form className={styles.inputs}>
            <Form.Input
              label={{ text: '用户名' }}
              field='input'
              defaultValue={userName}
              onChange={setUserName}
              placeholder='输入用户名'
              style={{ width: '100%' }}
              fieldStyle={{ alignSelf: 'stretch', padding: 0 }}
            />
            <Form.Input
              label={{ text: '密码' }}
              field='field1'
              defaultValue={password}
              onChange={setPassword}
              placeholder='输入密码'
              style={{ width: '100%' }}
              fieldStyle={{ alignSelf: 'stretch', padding: 0 }}
            />
          </Form>
          <Checkbox type='default'>记住我</Checkbox>
          <Button theme='solid' className={styles.button} onClick={loginSubmit}>
            登录
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Login;

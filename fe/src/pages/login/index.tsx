import { Button, Checkbox, Form } from '@douyinfe/semi-ui';
import React, { useEffect, useState } from 'react'
import styles from './index.module.scss';
import {loginRequest} from '@src/api/userApi';
import useStore from '@src/store/user'
import systemConfig from '@src/config'
import { removeLocalStorage, setLocalStorage } from '../../utils/storage'
import { useParams } from 'react-router-dom';

const Index: React.FC = () => {

	const getCurrentUser = useStore((state) => state.getCurrentUser)

	useEffect(()=>{
		//判断是否已经登陆了
		// const cacheToken = getLocalStorage(systemConfig.authKey);
		// if(cacheToken){
		// 	//尝试获取用户信息
		// 	if(getCurrentUser()){
		// 		window.location.pathname = form? form : '/';
		// 	}else{
		// 		removeLocalStorage(systemConfig.authKey);
		// 	}
		// }
		removeLocalStorage(systemConfig.authKey);
	})

	const [userName, setUserName] = useState('');
	const [password, setPassword] = useState('');
	const {form} = useParams();



	const loginSubmit = async () => {
	  const loginResult = await loginRequest({
		userName: userName,
		password: password,
	  })
	  setLocalStorage(systemConfig.authKey,loginResult);
	  if(getCurrentUser()){
		  window.location.pathname = form? form : '/';
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
				defaultValue={'123456789'}
				onChange={setUserName}
				placeholder='输入用户名'
				style={{ width: '100%' }}
				fieldStyle={{ alignSelf: 'stretch', padding: 0 }}
				rules={[
                    { required: true, message : '请输入用户名'}
                ]}
			  />
			  <Form.Input
				label={{ text: '密码' }}
				type='password'
				field='field1'
				defaultValue={'123456789'}
				onChange={setPassword}
				placeholder='输入密码'
				style={{ width: '100%' }}
				fieldStyle={{ alignSelf: 'stretch', padding: 0 }}
				rules={[
                    { required: true, message : '请输入密码'}
                ]}
			  />
			</Form>
			<Checkbox >记住我</Checkbox>
			<Button theme='solid' className={styles.button} onClick={loginSubmit}>
			  登录
			</Button>
		  </div>
		</div>
	  </div>
	);
}

export default Index

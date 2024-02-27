import { generateCaptchaV2 } from '@/services/dnf-admin/captchaController';
import { licenseDetails, regLicense, resetLicense } from '@/services/dnf-admin/licenseController';
import { ModalForm, ProFormText } from '@ant-design/pro-form';
import { PageContainer } from '@ant-design/pro-layout';
import { Button, Descriptions, message } from 'antd';
import { useEffect, useState } from 'react';
import { Access, useAccess } from 'umi';



const License: React.FC = () => {

  const access = useAccess();

  const [license, setLicense] = useState<API.LicenseDetails>();

  useEffect(()=>{
    licenseDetails().then(res=>{
      setLicense(res.data)
    })
  },[]);



  const [imageData, setImageData] = useState<API.CaptchaVo>();

  const onClickImage = () => {
    generateCaptchaV2({'key':imageData?.key}).then(res=>{
      setImageData(res.data);
    })
  };
  
  const [regLicenseVisible, handleRegLicenseVisible] = useState<boolean>(false);

  const reg = (values)=>{
    try {
      regLicense({ license: values.license ,verificationCode:values.verificationCode,captchaKey:imageData?.key}).then((res) => {
        if (res.success) {
          message.success(res.message);
          licenseDetails().then(res1=>{
            setLicense(res1.data)
          })
          handleRegLicenseVisible(false);
        }
      });
    } catch (error) {}
  }

  const reset = ()=>{
    try {
      resetLicense().then((res) => {
        if (res.success) {
          message.success(res.message);
          licenseDetails().then(res1=>{
            setLicense(res1.data)
          })
        }
      });
    } catch (error) {}
  }

  return (
      <PageContainer
        content={
              <Descriptions column={2}>
              <Descriptions.Item label="许可模式">
                {license?.type == 1 ? '免费授权': '未授权或过期'}
              </Descriptions.Item>
              <Descriptions.Item label="有效期">
                {license?.startTime} - {license?.endTime}
              </Descriptions.Item>
          </Descriptions>
        }
        extra={[
          <Access accessible={access.hashPre('license')}>
          <Button size="large" type="primary" onClick={()=>{reset()}} >
            重置许可
          </Button>
        </Access>,
          <Access accessible={access.hashPre('license')}>
            <Button size="large" type="primary" onClick={()=>{handleRegLicenseVisible(true)}} >
            注册许可
            </Button>
          </Access>
        ]}
        footer={[
        ]}
      >

     <ModalForm
        title="注册许可"
        visible={regLicenseVisible}
        modalProps={{
          destroyOnClose: true,
        }}
        onVisibleChange={(value) => {
          handleRegLicenseVisible(value);
        }}
        onFinish={reg}
      >
        <ProFormText
          name="license"
          label="许可参数"
          rules={[
            {
              required: true,
              message: '许可码不能为空',
            },
          ]}
        />
        <ProFormText
          name="verificationCode"
          label="验证码"
          placeholder={'请输入验证码'}
          fieldProps={
            {
              onMouseDown: ()=>{onClickImage()}
            }
          }
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
      </PageContainer>
      
  );
};

export default License;

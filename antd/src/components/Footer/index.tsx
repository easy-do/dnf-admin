import { useIntl } from 'umi';
import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-layout';
import { version } from '@/services/dnf-admin/systemController';
import { useMemo, useState } from 'react';
import { request } from 'umi';
import { mode } from '@/services/dnf-admin/systemController';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: '隔壁老于',
  });

  const currentYear = new Date().getFullYear();

  function getLastVersion(options?: { [key: string]: any }) {
    return request<any>('https://magic.easydo.plus/api/latestVersion', {
      method: 'GET',
      ...(options || {}),
    });
  }
  function getDesktopDownloadUrl(options?: { [key: string]: any }) {
    return request<any>('https://magic.easydo.plus/api/desktopDownloadUrl', {
      method: 'GET',
      ...(options || {}),
    });
  }

  function getStartCount(options?: { [key: string]: any }) {
    return request<any>('https://magic.easydo.plus/api/startCount', {
      method: 'GET',
      ...(options || {}),
    });
  }


  const [currentVersion,setCurrentVersion] = useState<string>();

  const [currentMode,setCurrentMode] = useState<string>();

  const [lastVersion,setLastVersion] = useState<string>();

  const [desktopDownloadUrl,setDesktopDownloadUrl] = useState<string>('');

  const [startCount,setStartCount] = useState<string>('');

  useMemo(() => {
    getLastVersion().then(res=>{
      setLastVersion(res.data);
    });
    version().then(res=>{
      setCurrentVersion(res);
    });
    mode().then(res=>{
      setCurrentMode(res);
    });
    getDesktopDownloadUrl().then(res=>{
      setDesktopDownloadUrl(res.data);
    });
    getStartCount().then(res=>{
      setStartCount(res.data);
    });
}, [])


  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: '交流群',
          title: '交流群',
          href: 'http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=jKliJIxAFvZoZxBSw1NnlMjOj8pRR42f&authKey=vnozKSs2ou1MO68VXH1ct2AReURSyIj4jlVe%2BVAlA5h%2F0M1BsdhQP0YN6MqwRwBB&noverify=0&group_code=154213998',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/easy-do/dnf-admin',
          blankTarget: true,
        },
        {
          key: 'gitee',
          title: 'gitee仓库',
          href: 'https://gitee.com/yuzhanfeng/dnf-admin',
          blankTarget: true,
        },
        {
          key: 'currentVersion',
          title: currentVersion === lastVersion ? '无需更新:('+currentVersion+')':'当前版本:'+currentVersion+'(最新版本'+lastVersion+')',
          href: 'https://gitee.com/yuzhanfeng/dnf-admin',
          blankTarget: true,
        },
        {
          key: 'currentMode',
          title: '当前模式:'+currentMode,
          href: 'https://gitee.com/yuzhanfeng/dnf-admin',
          blankTarget: true,
        },
        {
          key: 'startCount',
          title: '累积启动'+startCount+'次',
          href: 'https://gitee.com/yuzhanfeng/dnf-admin',
          blankTarget: true,
        },
        {
          key: 'desktopDurl',
          title: '下载桌面端',
          href: desktopDownloadUrl,
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;

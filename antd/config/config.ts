// https://umijs.org/config/
import { defineConfig } from 'umi';
import { join } from 'path';
import defaultSettings from './defaultSettings';
import proxy from './proxy';

const { REACT_APP_ENV } = process.env;

export default defineConfig({
  hash: true,
  antd: {},
  dva: {
    hmr: true,
  },
  layout: {
    // https://umijs.org/zh-CN/plugins/plugin-layout
    locale: false,
    siderWidth: 208,
    ...defaultSettings,
  },
  // https://umijs.org/zh-CN/plugins/plugin-locale
  locale: {
    // default zh-CN
    default: 'zh-CN',
    antd: false,
    // default true, when it is true, will use `navigator.language` overwrite default
    baseNavigator: false,
  },
  dynamicImport: {
    loading: '@ant-design/pro-layout/es/PageLoading',
  },
  targets: {
    ie: 11,
  },
  // umi routes: https://umijs.org/docs/routing
  routes: [
    {
      path: '/login',
      layout: false,
      name: 'login',
      component: './login',
    },
    {
      path: '/',
      redirect: '/home',
    },
    {
      path: '/home',
      component: './home',
    },
    {
      path: '/item',
      component: './item',
    },
    {
      path: '/conf',
      component: './conf',
    },
    {
      path: '/role',
      component: './role',
    },
    {
      path: '/mail',
      component: './mail',
    },
    {
      path: '/notice',
      component: './notice',
    },
    {
      path: '/signin',
      component: './signin',
    },
    {
      path: '/accounts',
      component: './accounts',
    },
    {
      path: '/channel',
      component: './channel',
    },
    {
      path: '/function',
      component: './fridaFunction',
    },
    {
      path: '/fridaScript',
      component: './fridaScript',
    },
    {
      component: '404',
    },
  ],
  access: {},
  // Theme for antd: https://ant.design/docs/react/customize-theme-cn
  theme: {
    // 如果不想要 configProvide 动态设置主题需要把这个设置为 default
    // 只有设置为 variable， 才能使用 configProvide 动态设置主色调
    // https://ant.design/docs/react/customize-theme-variable-cn
    'root-entry-name': 'variable',
  },
  // esbuild is father build tools
  // https://umijs.org/plugins/plugin-esbuild
  esbuild: {},
  title: false,
  ignoreMomentLocale: true,
  proxy: proxy[REACT_APP_ENV || 'dev'],
  manifest: {
    basePath: '/',
  },
  // Fast Refresh 热更新
  fastRefresh: {},
  openAPI: [
    {
      requestLibPath: "import { request } from 'umi'",
      schemaPath: 'http://localhost:8888/v3/api-docs',
      projectName: 'dnf-admin',
    },
  ],
  nodeModulesTransform: {
    type: 'none',
  },
  mfsu: {},
  webpack5: {},
  exportStatic: {},
  history: {
     type: 'hash',
   },
   publicPath: './',
});

/**
 * 在生产环境 代理是无法生效的，所以这里没有生产环境的配置
 * -------------------------------
 * The agent cannot take effect in the production environment
 * so there is no configuration of the production environment
 * For details, please see
 * https://pro.ant.design/docs/deploy
 */
 const serveUrlMap = {
  // dev: 'http://da.easydo.plus',
  dev: 'http://localhost:8888',
  pre: 'http://da.easydo.plus',
  test: 'http://da.easydo.plus',
  idc: 'http://da.easydo.plus',
};

const { SERVE_ENV = 'dev' } = process.env;

export default {
  dev: {
    // localhost:8000/api/** -> https://preview.pro.ant.design/api/**
    '/api/': {
      // 要代理的地址
      // target: 'https://da.easydo.plus',
      target: serveUrlMap[SERVE_ENV],
      // 配置了这个可以从 http 代理到 https
      // 依赖 origin 的功能可能需要这个，比如 cookie
      changeOrigin: true,
    },
  },
  test: {
    '/api/': {
      target: serveUrlMap[SERVE_ENV],
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  },
  pre: {
    '/api/': {
      target: 'your pre url',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  },
};

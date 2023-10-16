import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

export default defineConfig({
	resolve: {
		alias: {
			'@src': resolve(__dirname, './src'),
			'@assets': resolve(__dirname, './src/assets'),
			'@components': resolve(__dirname, './src/components'),
			'@pages': resolve(__dirname, './src/pages'),
			'@uitls': resolve(__dirname, './src/uitls'),
			'@styles': resolve(__dirname, './src/styles'),
			'@config': resolve(__dirname, './src/config'),
			'@mock': resolve(__dirname, './mock')
		}
	},
	plugins: [
		react(),
	],
	build: {
		target: 'es2015',
		minify: 'terser',
		cssCodeSplit: true,
		rollupOptions: {
			plugins: []
		}
	},
	server: {	
	    host: '0.0.0.0',//自定义主机名
		port: 8989,//自定义端口
		// 是否开启 https
		https: false,
		proxy: {
			'/api': {
				target: 'http://localhost:8888/api',
				changeOrigin: true,
				rewrite: (path: string) => path.replace(/^\/api/, '')
			}
		},
		hmr: {
			overlay: false
		}
	},
	base:'./'
})

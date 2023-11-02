# dnf-admin

#### 介绍
某不可描述的勇士game后台，本项目为用爱发电，如果觉得不错欢迎分享给身边的朋友。
本项目致力于打造更便捷可动态配置游戏参数、拓展性更强的游戏增强类后台，解决dp插件配置麻烦，需要到处找教程，小白看了教程很蒙圈的情况，逐步集成常用插件功能。
本项目无二开限制，商用还请注明出处。
欢迎感兴趣的同学加入贡献代码，本项目保证一直开源，不会进行停更、闭源收费等操作，但不保证以后不会加入广告，哈哈，毕竟为爱发电。
演示视频: https://www.bilibili.com/video/BV1UH4y1r7Z8/


#### 软件架构
服务端：spring boot + mybatisflex + sa-token
前端：React Semi Admin
dp插件：2.8.2集成frida通信

#### 特色功能
首次在java语言上(可能是首次)变向实现了后台与dp和frida的通信能力，支持调用脚本函数，接受游戏事件并做出对应处理，为实现各种功能提供了更多可能，欢迎对lua和frida有丰富使用经验的大佬来指点wensocket通信方式的实现，目前的实现方式为http轮询。

#### 更新记录&支持功能
1.使用游戏账号密码登录
2.对指定功能进行权限控制，目前仅区分普通用户和超级管理员，后期添加所有功能权限动态配置(可以配置指定权限账号，方便发布权限后台)
3.角色登录进行回调记录
4.支持每日签到配置，可指定特定日期的签到标题，奖励内容（物品无上线，可无限添加），玩家可在网页端签到，登录游戏角色自动签到，方便打卡。
5.支持发送游戏公告


#### 安装教程
一、独立部署
1.  下载dp2文件夹，将全部文件替换到你的服务器原dp2目录内（首次安装dp需配置run文件），修改 dp2/lua/reportDp.lua的gmKey为服务端配置的 DP_GM_KEY ，修改adminAddr为服务端的通信ip,端口默认8888
2.  拉取镜像： docker pull registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.0
3.  运行镜像(需注意配置环境变量)： docker run -dit -e MYSQL_HOST=dnfmysql -p 8888:8888 --name dnf-admin registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.0

二、与docker一键端结合部署（比较完美）
待补充

#### 使用说明

1.  后台镜像目前支持的环境变量： 
MYSQL_HOST 游戏数据库ip 默认 dnfmysql
MYSQL_PORT 游戏数据库端口 默认 3306
MYSQL_PASS 游戏数据库密码 默认 88888888
ADMIN_USER 超级管理员对应的游戏账号 默认123456789
DP_GM_KEY 与dp插件通信的安全密钥 默认123456789
2.  服务启动后访问 ip:8888 输入账号密码登录
3.  其他教程待补充

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request



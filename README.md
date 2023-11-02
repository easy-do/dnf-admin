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
挪到下面了
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


#### 独立部署简易教程

此教程针对已经部署服务端，集成或未集成dp插件的场景


1. 下载或克隆仓库
   仓库地址：https://gitee.com/yuzhanfeng/dnf-admin

克隆命令 ： git clone https://gitee.com/yuzhanfeng/dnf-admin

2.进入项目目录，将dp2目录内的全部文件上传至服务端的/dp2目录内

3.如果未集成dp2插件则需要修改run文件  在启动频道代码前添加 LD_PRELOAD="=/dp2/libdp2pre.so"
如果已经集成了dp2只要确认正确替换了原dp插件相关文件就无需修改
修改/dp2/lua/dpReport.lua的adminAddr为你要部署后台机器的ip地址和端口,端口默认8888，如果是同一台机器并且非docker版服务端则修改ip为127.0.0.1
重启服务端。

4.使用docker命令运行后台程序

镜像的环境变量说明
MYSQL_HOST 服务端的数据库ip 默认 dnfmysql
MYSQL_PORT 服务端的数据库端口 默认 3306
MYSQL_USER 服务端的数据库账号 默认 root
MYSQL_PASS 服务端的数据库密码 默认 88888888
ADMIN_USER 后台超级管理员对应的游戏账号 默认123456789
DP_GM_KEY 与dp插件通信的安全密钥 默认123456789  如果修改需要同步修改 /dp2/lua/dpReport.lua里面的密钥


启动命令 注意替换对应环境变量
docker pull registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.0
docker run -dit  -e MYSQL_HOST=dnfmysql  -e MYSQL_PORT=3306 -e MYSQL_USER=root -e MYSQL_PASS=88888888 -e ADMIN_USER=123456789 -e DP_GM_KEY =123456789 -p 8888:8888 --name dnf-admin registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.0

查看日志
docker logs -f dnf-admin

5.访问后台： 你的部署服务器ip:8888     账号密码为游戏的账号密码，超管权限账号是环境变量ADMIN_USER 设置的账号

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request



# dnf-admin

#### 介绍
某不可描述的勇士game后台，本项目为用爱发电，如果觉得不错欢迎分享给身边的朋友。
本项目致力于打造更便捷可动态配置游戏参数、拓展性更强的游戏增强类后台，解决dp插件配置麻烦，需要到处找教程，小白看了教程很蒙圈的情况，逐步集成常用插件功能。
本项目无二开限制，商用还请注明出处。
欢迎感兴趣的同学加入贡献代码，本项目保证一直开源，不会进行停更、闭源收费等操作，但不保证以后不会加入广告，哈哈，毕竟为爱发电。
- 演示视频: https://www.bilibili.com/video/BV1UH4y1r7Z8/
- 技术交流群: 154213998  418505204

#### 软件架构
- 服务端：spring boot + mybatisflex + sa-token
- 前端：React Semi Admin
- dp插件：2.8.2集成frida通信

#### 特色功能
首次在java语言上(可能是首次)变向实现了后台与dp和frida的通信能力，支持调用脚本函数，接受游戏事件并做出对应处理，为实现各种功能提供了更多可能，欢迎对lua和frida有丰富使用经验的大佬来指点wensocket通信方式的实现，目前的实现方式为http轮询。

#### 更新记录&支持功能
- 使用游戏账号密码登录
- 对指定功能进行权限控制，目前仅区分普通用户和超级管理员，后期添加所有功能权限动态配置(可以配置指定账号的权限)
- 角色登录进行回调记录
- 支持每日签到配置，可指定特定日期的签到标题，奖励内容（物品无上线，可无限添加），玩家可在网页端签到，登录游戏角色自动签到，方便打卡。
- 支持发送游戏公告
- 支持发送邮件,无需小退
- 支持后台配置游戏相关脚本功能并实时生效
- 支持配置一键完成主线任务的道具
- 支持配置指定强化增幅指定等级不失败


#### docker服务端加后台一键部署教程

- 视频教程： 
https://www.bilibili.com/video/BV1ju4y187SS/

- 整体流程为下载 docker-compose.yaml  编辑环境变量、启动容器编排

- 克隆代码
```shell
cd /root
git clone https://gitee.com/yuzhanfeng/dnf-admin.git  
cd dnf-admin
```
- 编辑 docker-compose.yaml的内容，配置环境变量

```yaml
# 请修改这里的环境变量
x-env: &env
  environment:
    - TZ=Asia/Shanghai
    # game账户ip白名单 默认即可
    - ALLOW_IP=172.20.0.%
    # game账户密码 默认即可
    - GAME_PASSWORD=uu5!^%jg
    # root账户密码 外网则需要修改
    - MYSQL_ROOT_PASSWORD=88888888
    - MYSQL_PASS=88888888
    # 自动获取mysql容器的ip 默认即可
    - AUTO_MYSQL_IP=true
    # mysql容器名称 默认即可
    - MYSQL_NAME=dnfmysql
    # mysql的IP地址 默认即可
    - MYSQL_IP=dnfmysql
    # 自动获取公网ip 推荐false 手动填写服务器ip
    - AUTO_PUBLIC_IP=false
    # 这里填写你的服务器公网或局域网IP地址 
    - PUBLIC_IP=192.168.123.88
    # 以下是统一登录器相关配置
    # 网关的登录账号
    - GM_ACCOUNT=gm_user
    # 网关的登录密码
    - GM_PASSWORD=123456
    # 网关的通信密钥
    - GM_CONNECT_KEY=763WXRBW3PFTC3IXPFWH
    # 网关的登录器版本
    - GM_LANDER_VERSION=20180307
    # 以下是后台dnf-admin的相关配置
    # 拥有管理员权限的游戏账号
    - ADMIN_USER=123456789
    # dp插件的通信密钥 非外网默认即可，因为还需要同步修改服务端的/dp2/lua/reportDp.lua
    - DP_GM_KEY=123456789

```
- 执行命令启动容器编排
```shell
docker-compose up -d
```

查看数据库日志

```shell
docker logs -f dnfmysql  
```
- 查看服务端日志

```shell
docker logs -f dnfserver  
```
- 查看后台日志

```shell
docker logs -f dnf-admin 
```
- 后台地址

```yaml
http://ip:8888 请使用游戏注册的账号密码登录，管理员为第一步环境变量内配置的超级管理员账号，其他游戏账号夜客登录权限为普通用户
```

- pvf及等级补丁替换路径

```shell
/data/dnf/server/data
```

- 已部署旧版升级到最新版后台:

```shell
cd /root/dnf-admin
docker pull registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.0
docker rm -f dnf-admin
docker-compose up -d
docker restart dnfserver
```


#### 其他说明

- 后台镜像目前支持的环境变量
``` yaml
MYSQL_HOST #游戏数据库ip 默认 dnfmysql
MYSQL_PORT #游戏数据库端口 默认 3306
MYSQL_PASS 3游戏数据库密码 默认 88888888
ADMIN_USER 3超级管理员对应的游戏账号 默认123456789
DP_GM_KEY #与dp插件通信的安全密钥 默认123456789
```
- 后台端口默认为 8888 使用游戏的账号密码登录
- 其他教程待补充


#### 独立部署简易教程

- 此教程针对已经部署服务端，集成或未集成dp插件的情况下需要单独使用网页GM工具的场景

- 下载项目文件或git克隆仓库
  仓库地址：https://gitee.com/yuzhanfeng/dnf-admin
- 克隆方式
```shell
cd /root
git clone https://gitee.com/yuzhanfeng/dnf-admin
cd dnf-admin
```
- 将dp2插件的全部文件复制到服务器/dp2目录内

```shell
cp -r /root/dnf-admin/dp2 /dp2
```

- 如果未集成dp2插件则需要修改run文件  在启动频道代码前添加 LD_PRELOAD="=/dp2/libdp2pre.so"
  例如：
```yaml
LD_PRELOAD=/dp2/libdp2pre.so ./df_game_r siroco11 start &
sleep 2
LD_PRELOAD=/lib/libdp2pre.so ./df_game_r siroco52 start &
```
- 如果已经集成了dp2只要确认正确替换了原dp插件相关文件就无需修改
- 修改/dp2/lua/dpReport.lua的相关参数

``` yaml
    -- gmKey:为了通讯安全请与服务端同步设置复杂的密钥
    local gmKey = "123456789"
    --改为dnf-admin的ip和端口
    local adminAddr = "http://dnf-admin:8888"

```
- 以上操作完成后重启服务端。

- 运行GM网页后台程序 注意替换启动命令的环境变量

```shell 
docker pull registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.0
docker run -dit  -e MYSQL_HOST=dnfmysql  -e MYSQL_PORT=3306 -e MYSQL_USER=root -e MYSQL_PASS=88888888 -e ADMIN_USER=123456789 -e DP_GM_KEY =123456789 -p 8888:8888 --name dnf-admin registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.0
```

- 查看后台日志

```shell
docker logs -f dnf-admin
```


- 访问后台
```yaml
http://服务器ip:8888     使用游戏的账号密码登录，超管权限账号是环境变量ADMIN_USER设置的账号，其他账号为普通权限
```

#### 参与贡献

1.  Fork 本仓库
2.  新建  分支
3.  提交代码
4.  新建 Pull Request



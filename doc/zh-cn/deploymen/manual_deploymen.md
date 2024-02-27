## 手动部署



#### docker服务端加后台手动部署教程

- 服务端的最佳兼容系统版本为centos7 x86系统,debian系统测试正常，其他系统内核是否兼容未知
- 所有服务都是使用docker容器云行
- 整体流程为下载 docker-compose.yaml  编辑环境变量、启动容器编排

- 下载编排文件
```shell
yum install wget -y
mkdir /root/dnf-admin
cd /root/dnf-admin
wget https://gitee.com/yuzhanfeng/dnf-admin/releases/download/1.0.4/docker-compose.yaml
```

- 安装 docker和docker-compose,如果服务器已经安装则直接跳过

推荐安装1panel面板,会自动安装docker,否则自行百度自己系统如何安装docker

- 编辑 docker-compose.yaml的内容，配置环境变量，哪些需要改看注释

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
    # 以下是后台dnf-admin的相关配置
    # 拥有管理员权限的游戏账号
    - ADMIN_USER=123456789
    # 服务端读取pvf文件的路径、docker端编排方式保持默认值即可
    - PVF_PATH=/data/server/data/Script.pvf

```

- 拉取镜像
```shell
docker pull registry.cn-hangzhou.aliyuncs.com/gebilaoyu/frida-client:latest
docker-compose pull
```

- 启动数据库
```shell
docker-compose up -d dnfmysql
```
查看数据库日志,使用相关工具连接数据库，因为首次加载比较慢，需要确保数据库初始化完成在继续启动其他服务才能启动服务端和后台

```shell
docker logs -f dnfmysql  
```
- 数据库初始化完成后启动服务端

```shell
docker-compose up -d dnfserver
```
- 查看服务端日志
```shell
docker logs -f dnfserver
```

- 启动dnfadmin

```shell
docker-compose up -d dnfadmin
```
当日志里面出现 "Completed initialization in 1 ms" 字样则代表正常启动完成,初次启动会加载pvf文件并导入数据库
,如果看到 INSERT INTO `da_item`(`id`, `name`, `type`, `rarity`) VALUES 这样的日志则代表正在导入数据库,可以不用管，往下走启动服务端

- 查看dnfadmin日志
```shell
docker logs -f dnfadmin 
```


一般看到"server has been started successfully."字样停留不动，并且cpu占用骤降就代表服务端基本启动完成，再过一阵还能看到五国字样

- 服务端的详细日志日志一般存放在 /data/dnf/server/log下, 可以使用命令tail -f查看详细日志
```shell
tail -f 这里换成具体日志的位置 比如 /data/dnf/server/log/siroco11/Logxxxxxx.log
```
- 数据库文件存在 /data/dnf/mysql,如果需要删档或或者初始化数据库长时间不成功就删掉这个目录下的所有文件，然后重启数据库服务

- 后台地址

```yaml
http://你的服务器ip:8888 # 请使用游戏注册的账号密码登录，管理员为第一步环境变量内配置的超级管理员账号，其他游戏账号夜客登录权限为普通用户
```

- pvf及等级补丁替换路径

```shell
/data/dnf/server/data
```
- 登录器
  如果没有特殊需求直接使用dnf-admin的桌面端，支持注册和登录启动


- 升级到最新版:

```shell
cd /root/dnf-admin
docker pull registry.cn-hangzhou.aliyuncs.com/gebilaoyu/frida-client
docker-compose pull 
docker rm -f dnfadmin
docker restart dnfserver
docker-compose up -d dnfadmin
```

- 卸载所有数据，彻底清除
```shell
cd /root/dnf-admin
docker-compose stop
docker-compose rm -f
rm -rf /data/dnf/*
docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnfmysql:5.6
docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.2
docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/frida-client:latest
docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.3
docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnfserver:frida-server
docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnfserver:latest
```

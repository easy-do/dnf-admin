#!/bin/bash

# 添加颜色变量
RED="\e[31m"           # 红色
GREEN="\e[32m"         # 绿色
YELLOW="\e[33m"        # 黄色
RESET="\e[0m"          # 重置颜色

# 添加函数以显示不同颜色的消息
print_message() {
    local message="$1"
    local color="$2"
    echo -e "${color}${message}${RESET}"
}


install_wget(){
    print_message "安装 wget..." "$GREEN"
    if command -v apt &> /dev/null; then
        apt install -y wget > /dev/null
    elif command -v apt-get &> /dev/null; then
        apt-get install -y wget > /dev/null
    elif command -v dnf &> /dev/null; then
        dnf install -y wget > /dev/null
    elif command -v yum &> /dev/null; then
        yum install -y wget > /dev/null
    else
        print_message "无法确定操作系统的包管理器，请手动安装 wget" "$RED"
        exit 1
    fi    
}

check_docker(){
    # 检查是否安装 Docker
    if ! command -v docker &> /dev/null; then
        print_message "未检测到 Docker 环境，开始安装 Docker..." "$YELLOW"

        if command -v apt &> /dev/null; then
            apt update > /dev/null
            apt install -y docker.io > /dev/null
        elif command -v apt-get &> /dev/null; then
            apt-get update > /dev/null
            apt-get install -y docker.io > /dev/null
        elif command -v dnf &> /dev/null; then
            dnf install -y docker > /dev/null
            systemctl enable --now docker > /dev/null
        elif command -v yum &> /dev/null; then
            yum install -y docker-ce > /dev/null
            systemctl enable --now docker > /dev/null
        elif command -v pacman &> /dev/null; then
            pacman -Syu --noconfirm docker > /dev/null
            systemctl enable --now docker > /dev/null
        else
            print_message "无法确定操作系统的包管理器，请手动安装 Docker" "$RED"
            exit 1
        fi
        print_message "Docker 环境安装完成" "$GREEN"
    else
        print_message "已安装 Docker 环境，跳过安装" "$GREEN"
    fi
}


# 检查是否具有 root 权限
if [ "$(whoami)" != "root" ]; then
    print_message "请使用 root 权限执行本脚本！" "$RED"
    exit 1
fi

# 选择安装模式
print_message "欢迎使用dnf-admin安装脚本! v20240224" "$GREEN"
print_message "项目地址：" "$GREEN"
print_message "github https://gitee.com/yuzhanfeng/dnf-admin" "$GREEN"
print_message "gitee https://github.com/easy-do/dnf-admin" "$GREEN"
print_message "交流群 154213998" "$GREEN"
print_message "防失联频道  https://im.easydo.plus/invite/l_7xH88A" "$GREEN"
print_message "请选择执行选项（默认1 ,提示：所有安装模式均需要daocker）" "$YELLOW"
echo "1. 安装1panel管理面板（自带docker）"
echo "2. 全量安装：包含配套服务端和dnf-admin,所有功能可用"
echo "3. 工具模式：frida和频道管理功能不可用"
echo "4. 独立模式：自行对接服务端配置和frida"
echo "5. 单独下载最新编排文件(会覆盖旧文件)"
echo "6. 重启服务端"
echo "7. 重启数据库"
echo "8. 重启dnfadmin"
echo "9. 升级最新版"
echo "10. 卸载并删除数据"
read -p "请输入数字以选择执行方式: " choice_manage
choice_manage=${choice_manage:-1}

# 如果选择了 配套docker模式
if [ "$choice_manage" == "1" ]; then
	print_message "开始安装1panel管理面板..." "$GREEN"
	curl -sSL https://resource.fit2cloud.com/1panel/package/quick_start.sh -o quick_start.sh && sh quick_start.sh
elif [ "$choice_manage" == "2" ]; then
	print_message "开始执行全量安装" "$GREEN"
	check_docker
	install_wget
	print_message "创建文件夹" "$YELLOW"
	mkdir /root/dnf-admin
	print_message "切换到/root/dnf-admin目录" "$YELLOW"
	cd /root/dnf-admin
	print_message "下载编排文件...." "$YELLOW"
	wget https://gitee.com/yuzhanfeng/dnf-admin/releases/download/1.0.3/docker-compose.yaml -O docker-compose.yaml
	print_message "开始设置环境变量...." "$YELLOW"
	
	print_message  "数据库game用户密码 外网建议修改" "$GREEN"
	read -p "请输入(默认 uu5!^%jg): " GAME_PASSWORD
	print_message  "数据库root用户密码 外网建议修改" "$GREEN"
	read -p "请输入(默认 88888888): " MYSQL_ROOT_PASSWORD
	print_message "服务器公网或局域网IP地址" "$GREEN"
	read -p "请输入(默认 192.168.123.88): " PUBLIC_IP
	print_message "后台访问端口" "$GREEN"
	read -p "请输入(默认 8888): " ADMIN_PORT
	print_message "后台管理员账号" "$GREEN"
	read -p "请输入(默认 123456789): " ADMIN_USER
	print_message "其他配置请修改/root/dnf-admin/docker-compose.yaml 后重启docker编排" "$RED"
	
	if [ -z "$GAME_PASSWORD" ]; then
     	GAME_PASSWORD="uu5!^%jg"
     fi
     if [ -z "$MYSQL_ROOT_PASSWORD" ]; then
     	MYSQL_ROOT_PASSWORD="88888888"
     fi

     if [ -z "$PUBLIC_IP" ]; then
     	PUBLIC_IP="192.168.123.88"
     fi
     if [ -z "$ADMIN_PORT" ]; then
     	ADMIN_PORT="8888"
     fi
     if [ -z "$ADMIN_USER" ]; then
     	ADMIN_USER="123456789"
     fi

     
     sed -i "s/uu5!^%jg/${GAME_PASSWORD}/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/88888888/${MYSQL_ROOT_PASSWORD}/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/192.168.123.88/${PUBLIC_IP}/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/8888:8888/${ADMIN_PORT}:8888/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/123456789/${ADMIN_USER}/g" /root/dnf-admin/docker-compose.yaml

	print_message  "开始拉取docker镜像..." "$GREEN"
	docker pull registry.cn-hangzhou.aliyuncs.com/gebilaoyu/frida-client:latest
	docker-compose pull
	print_message  "docker镜像拉取完成" "$GREEN"
	print_message  "启动数据库" "$GREEN"
	docker-compose up -d dnfmysql
	print_message  "启动服务端" "$GREEN"
	docker-compose up -d dnfserver
	print_message  "启动dnfadmin" "$GREEN"
	docker-compose up -d dnfadmin
	print_message "=========================" "$GREEN"
	print_message "pvf及等级补丁替换路径: /data/dnf/server/data" "$GREEN"
	print_message "查看dnfadmin日志: docker logs -f dnfadmin" "$GREEN"
	print_message "查看服务端日志: docker logs -f dnfserver" "$GREEN"
	print_message "查看数据库日志: docker logs -f dnfmysql " "$GREEN"
	print_message "数据库密码: $MYSQL_ROOT_PASSWORD" "$GREEN"
	print_message "服务器ip: $PUBLIC_IP" "$GREEN"
	print_message "管理员账号: $ADMIN_USER"（登录前先注册相同账号） "$GREEN"
	print_message "后台地址: http://$PUBLIC_IP":$ADMIN_PORT "$GREEN"
	print_message "=========================" "$GREEN"
	
elif [ "$choice_manage" == "3" ]; then
	print_message "以工具模式运行dnf-admin...." "$GREEN"
	print_message "创建文件夹" "$YELLOW"
	mkdir /root/dnf-admin
	check_docker
	print_message "切换到/root/dnf-admin目录" "$YELLOW"
	cd /root/dnf-admin
	print_message "下载编排文件...." "$YELLOW"
	install_wget
	wget https://gitee.com/yuzhanfeng/dnf-admin/releases/download/1.0.3/docker-compose-standalone.yaml -O /root/dnf-admin/docker-compose.yaml
	print_message "开始设置环境变量...." "$YELLOW"
	

	print_message  "数据库root用户密码 外网建议修改" "$GREEN"
	read -p "请输入(默认 88888888): " MYSQL_ROOT_PASSWORD
	print_message "后台访问端口" "$GREEN"
	read -p "请输入(默认 8888): " ADMIN_PORT
	print_message "后台管理员账号" "$GREEN"
	read -p "请输入(默认 123456789): " ADMIN_USER
	print_message "其他配置请修改/root/dnf-admin/docker-compose.yaml 后重启docker编排" "$RED"
	
     if [ -z "$MYSQL_ROOT_PASSWORD" ]; then
     	MYSQL_ROOT_PASSWORD="88888888"
     fi
     if [ -z "$ADMIN_PORT" ]; then
     	ADMIN_PORT="8888"
     fi
     if [ -z "$ADMIN_USER" ]; then
     	ADMIN_USER="123456789"
     fi

     sed -i "s/88888888/${MYSQL_ROOT_PASSWORD}/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/8888:8888/${ADMIN_PORT}:8888/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/123456789/${ADMIN_USER}/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/MODE=default/MODE=utils/g" /root/dnf-admin/docker-compose.yaml
     
	print_message  "开始拉取docker镜像..." "$GREEN"
	docker-compose pull
	print_message  "docker镜像拉取完成" "$GREEN"

	print_message  "启动dnfadmin" "$GREEN"
	docker-compose up -d dnfadmin
	print_message "=========================" "$GREEN"
	print_message "查看dnfadmin日志: docker logs -f dnfadmin" "$GREEN"
	print_message "数据库密码: $MYSQL_ROOT_PASSWORD" "$GREEN"
	print_message "管理员账号: $ADMIN_USER"（登录前先注册相同账号） "$GREEN"
	print_message "后台地址: http://$PUBLIC_IP":$ADMIN_PORT "$GREEN"
	print_message "=========================" "$GREEN"
elif [ "$choice_manage" == "4" ]; then
	print_message "独立模式与工具模式暂时一致,待相关文档补齐后开放" "$GREEN"
	exit 0
elif [ "$choice_manage" == "5" ]; then
	print_message "开始下载编排文件...." "$YELLOW"
	install_wget
	mkdir /root/dnf-admin
	wget https://gitee.com/yuzhanfeng/dnf-admin/releases/download/1.0.4/docker-compose.yaml -O /root/dnf-admin/docker-compose.yaml
	print_message "编排文件文件已下载到/root/dnf-admin目录" "$GREEN"
elif [ "$choice_manage" == "6" ]; then
     docker restart dnfserver
elif [ "$choice_manage" == "7" ]; then
	docker restart dnfmysql
elif [ "$choice_manage" == "8" ]; then
	docker restart dnfadmin
elif [ "$choice_manage" == "9" ]; then
     print_message "开始升级..." "$YELLOW"
     LAST_VERSION='dnf-admin:1.0.4'
     sed -i "s/dnf-admin:1.0.1/${LAST_VERSION}/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/dnf-admin:1.0.2/${LAST_VERSION}/g" /root/dnf-admin/docker-compose.yaml
     sed -i "s/dnf-admin:1.0.3/${LAST_VERSION}/g" /root/dnf-admin/docker-compose.yaml
	cd /root/dnf-admin
	docker pull registry.cn-hangzhou.aliyuncs.com/gebilaoyu/frida-client
	docker-compose pull 
	docker rm -f dnfadmin
	docker-compose up -d dnfadmin
	print_message "镜像升级完成,请自行重启容器" "$GREEN"
elif [ "$choice_manage" == "10" ]; then
     print_message "开始卸载并删除数据" "$YELLOW"
	cd /root/dnf-admin
	print_message "停止所有容器..." "$YELLOW"
	docker-compose stop
	print_message "删除所有容器..." "$YELLOW"
	docker-compose rm -f
	print_message "删除本地缓存..." "$YELLOW"
	rm -rf /data/dnf/*
	print_message "删除历史镜像..." "$YELLOW"
	docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.2
	docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.2
	docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.3
	docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnf-admin:1.0.4
	docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/frida-client:latest
	docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnfserver:frida-server
	docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnfserver:latest
		docker rmi registry.cn-hangzhou.aliyuncs.com/gebilaoyu/dnfmysql:5.6
	print_message "卸载并删除数据完成" "$GREEN"
else
	print_message "请输入正确的选项" "$RED"
fi

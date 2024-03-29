# dnf-admin

## 介绍
- 某不可描述的勇士game后台，本项目为用爱发电，如果觉得不错欢迎分享给身边的朋友。
- 本项目致力于打造更便捷可动态配置游戏参数、拓展性更强的增强类后台，解决插件脚本配置麻烦，需要到处找教程，小白看了教程很蒙圈的情况，被倒卖者圈钱的情况，会逐步集成常用插件功能。
- 欢迎兴趣相投的朋友加入群组一起交流技术
- 2024.02.27 正式发布1.0.4版本,代码同步更新,新增OneBot功能支持,基于OneBotV11反向ws对接,具体请看下方演示图片。
- 目前已添加centos一键安装脚本，主要支持两种模式：1、完全模式(带服务端) 2.GM工具模式(可以独立运行,网页访问,不支持frida相关特性功能)
- 新增windows独立使用版，下载地址在release的网盘分享


#### 本项目仅供学习交流，请于下载后的24小时内删除，不得用于商业牟利行为。

- 你们的支持是我更新的动力,如果觉得不错还请点个star，这对我很重要。
- 在线网页演示地址： https://da.easydo.plus/  账号：123456789 密码：123456789
- 最新视频教程： https://www.bilibili.com/video/BV1LN4y1B77b/
- gitee仓库：https://github.com/easy-do/dnf-admin
- github仓库 https://gitee.com/yuzhanfeng/dnf-admin
- 常用教程、游戏版本、其他工具：https://daf.ink/
- 吹水群1: 154213998  架设和使用遇到问题或意见反馈来此群
- 吹水群2（防失联）： https://im.easydo.plus/invite/l_7xH88A
- TG频道: https://t.me/+jg121NZWl51mZTQ9


## 软件架构
- 服务端：spring boot
- 前端：ANTD PRO
- 插件：frida

## 特色功能

- 使用docker容器运行,安装卸载方便,与宿主机隔离,减少垃圾文件污染
- 按钮级别的权限控制
- 支持frida在线调试、热更代码
- frida脚本的在线编辑、方便拆分和组合函数
- 不间断的长期更新支持、免费试用、代码开源、无后门


## 使用文档
http://doc.easydo.plus/

## 图片介绍

#### 角色管理

<img src="./zh-cn/img/角色管理1.png">

<img src="./zh-cn/img/角色管理2.png">

<img src="./zh-cn/img/角色管理3.png">

#### 函数管理

<img src="./zh-cn/img/函数管理1.png">

<img src="./zh-cn/img/函数管理2.png">

#### frida调试
<img src="./zh-cn/img/frida调试.png">

#### 频道列表
<img src="./zh-cn/img/频道列表.png">

#### 首页
<img src="./zh-cn/img/首页.png">

#### 签到
<img src="./zh-cn/img/签到.png">

#### 物品
<img src="./zh-cn/img/物品.png">

#### 公告
<img src="./zh-cn/img/公告.png">

#### 邮件
<img src="./zh-cn/img/邮件.png">

#### 配置
<img src="./zh-cn/img/配置.png">

#### 机器人菜单
<img src="./zh-cn/img/机器人菜单.png">

#### 群管
<img src="./zh-cn/bot/img/群管1.png">




#### 其他说明

- 后台镜像目前支持的环境变量
``` yaml
MYSQL_HOST #游戏数据库ip 默认 dnfmysql
MYSQL_PORT #游戏数据库端口 默认 3306
MYSQL_PASS #游戏数据库密码 默认 88888888
ADMIN_USER #超级管理员对应的游戏账号 默认123456789
```
- 后台端口默认为 8888 使用游戏的账号密码登录
- 其他教程待补充



## 系统API
### 简介

* 框架支持机器人脚本调用javeapi以及自定义实现的对象方法,以下是相关接口说明

###  BotScriptUtils 
#### 机器人脚本工具


* 判断一个逗号分割的字符串是否包含指定字符串

```javascript
let superAdmin = "1,2,3";
let senderUser = "1";
BotScriptUtils.listStrContains(superAdmin,senderUser);

```
以上代码执行结果为true

* 删除一个逗号分割的字符串内的某个元素

```javascript
let superAdmin = "1,2,3";
let senderUser = "1";
BotScriptUtils.removeListStr(superAdmin,senderUser);

```
以上代码执行结果为 "2,3"


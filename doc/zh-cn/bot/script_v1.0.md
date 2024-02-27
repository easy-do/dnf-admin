## 脚本V1.0

### 介绍

这是一个脚本示例，基于dnf-admin的机器人框架实现了简单的群组授权和群员管理,示例内包含调用系统的内容可供二开参考

### 脚本


```javascript

println('AviatorScript start ...');

##println(postData);
##println(botNumber);
## println(res);
## println('1231');

let conf = BotConfUtil.getAllBotConf(botNumber);

let senderUser = postData.sender.user_id+''+'';

let messageType = postData.message_type;

let message = postData.message;

println(message);

let groupId = '';

let groupStatus = true;

try {
    groupId = postData.group_id+'';
} catch(e) {
    groupStatus = false;
}

## 是否超管
let superAdmin = '-';
try {
    superAdmin = conf.super_admin_user;
} catch(e) {
    BotConfUtil.saveBotConf(botNumber,'super_admin_user','-');
}

## 是否管理员
let adminUser = '-';
try {
    adminUser = conf.admin_user;
} catch(e) {
    BotConfUtil.saveBotConf(botNumber,'admin_user','-');
}

if(messageType == 'private'){
## 处理私聊信息
    if(groupStatus){
    ## 群发起的私聊
    }else{
    ## 直接私聊
    }
}else{
## 群消息

    let botAuthGroupList = '';
    try{
        botAuthGroupList = conf.bot_auth_groupList;
    }catch(e){
    }

## 优先监测添加删除群权限命令
    if(BotScriptUtils.listStrContains(superAdmin,senderUser)){
        let addGroupCmd = '!添加群授权';
        if(message.segmentSize == 1 && message.type == 'text' &&  message.simpleMessage == addGroupCmd){
            if(BotScriptUtils.listStrContains(botAuthGroupList,groupId)){
                OneBotUtils.sendGroupMessage(botNumber,groupId, '[CQ:at,qq='+ postData.sender.user_id +']'+'该群已授权,请勿重复授权',true);
            }else{
                BotConfUtil.saveBotConf(botNumber,'bot_auth_groupList',botAuthGroupList+','+groupId);
                OneBotUtils.sendGroupMessage(botNumber,groupId, '[CQ:at,qq='+ postData.sender.user_id +']'+'添加群【'+groupId+'】授权',true);
            }
        }
    }
    if(BotScriptUtils.listStrContains(superAdmin,senderUser)){
        let removeGroupCmd = '!删除群授权';
        if(message.segmentSize == 1 && message.type == 'text' && message.simpleMessage == removeGroupCmd){
            if(!BotScriptUtils.listStrContains(botAuthGroupList,groupId)){
                OneBotUtils.sendGroupMessage(botNumber,groupId, '[CQ:at,qq='+ postData.sender.user_id +']'+'该群未授权',true);
            }else{
                BotConfUtil.saveBotConf(botNumber,'bot_auth_groupList',BotScriptUtils.removeListStr(botAuthGroupList,groupId));
                OneBotUtils.sendGroupMessage(botNumber,groupId, '[CQ:at,qq='+ postData.sender.user_id +']'+'已删除群【'+groupId+'】授权',true);
            }
        }
    }

## 群有权限才往下走
    if(BotScriptUtils.listStrContains(botAuthGroupList,groupId)){
    ##-------------------------------------------------超管功能-------------------------------------------------------------
        if(BotScriptUtils.listStrContains(superAdmin,senderUser)){
            let addAdminCmd = '!添加bot管理员';
            if(message.segmentSize == 1 && message.type == 'text' && StrUtil.startWith(message.simpleMessage,bindAccountCmd)){
                let bfStr = CharSequenceUtil.subAfter(message.simpleMessage,addAdminCmd,false);
                if(BotScriptUtils.listStrContains(adminUser,bfStr)){
                    OneBotUtils.sendGroupMessage(botNumber,groupId,  '[CQ:at,qq='+ postData.sender.user_id +']'+ bfStr +'已经是bot管理员',true);
                }else{
                    BotConfUtil.updateBotConf(botNumber,'admin_user',adminUser+','+StrUtil.trim(bfStr));
                    OneBotUtils.sendGroupMessage(botNumber,groupId, '[CQ:at,qq='+ postData.sender.user_id +']'+'已添加'+ bfStr + '为bot管理员',true);
                }
            }
            let removeAdminCmd = '!删除bot管理员';
            if(message.segmentSize == 1 && message.type == 'text' && StrUtil.startWith(message.simpleMessage,removeAdminCmd)){
                let bfStr = CharSequenceUtil.subAfter(message.simpleMessage,removeAdminCmd,false);
                if(BotScriptUtils.listStrContains(adminUser,bfStr)){
                    BotConfUtil.updateBotConf(botNumber,'admin_user',BotScriptUtils.removeListStr(adminUser,StrUtil.trim(bfStr)));
                    OneBotUtils.sendGroupMessage(botNumber,groupId,'[CQ:at,qq='+ postData.sender.user_id +']'+'已删除bot管理员'+ bfStr,true);
                }
            }
        }
    ##---------------------------------------------------------------------------------------------------------------------


    ##-------------------------------------------------超管和管理员功能-------------------------------------------------------------
        if(BotScriptUtils.listStrContains(adminUser,senderUser) || BotScriptUtils.listStrContains(superAdmin,senderUser)){

            if(message.segmentSize == 1 && message.type == 'text' && message.simpleMessage == '!全体禁言'){
                OneBotUtils.setGroupWholeBan(botNumber,groupId,true);
                OneBotUtils.sendGroupMessage(botNumber,groupId,'已开启全体禁言',true);
            }

            if(message.segmentSize == 1 && message.type == 'text' && message.simpleMessage == '!全体解禁'){
                OneBotUtils.setGroupWholeBan(botNumber,groupId,false);
                OneBotUtils.sendGroupMessage(botNumber,groupId,'已解除全体禁言',true);
            }

            if(message.segmentSize == 3 && message.type == 'text_at_text' && message.atBeforeText  == '!禁言'){
                OneBotUtils.setGroupBan(botNumber,groupId,message.atUser,Long.parseLong(StrUtil.trim(message.atAfterText)));
                OneBotUtils.sendGroupMessage(botNumber,groupId,'[CQ:at,qq='+ postData.sender.user_id +']'+'已将'+message.atUser+'禁言',true);
            }
            if(message.segmentSize == 2 && message.type == 'text_at' && message.atBeforeText  == '!解禁'){
                OneBotUtils.setGroupBan(botNumber,groupId,message.atUser,0);
            }
            if(message.segmentSize == 2 && message.type == 'text_at' && message.atBeforeText  == '!叉出去'){
                OneBotUtils.setGroupKick(botNumber,groupId,message.atUser,false);
                OneBotUtils.sendGroupMessage(botNumber,groupId,'[CQ:at,qq='+ postData.sender.user_id +']'+'已将'+message.atUser+'移出群组',true);

            }
        }
    ##-------------------------------------------------------------------------------------------------------------------------

    ##-------------------------------------------------普通成员可用功能----------------------------------------------------------

        if(message.segmentSize == 1 && message.type == 'text' && message.simpleMessage == '!游戏签到'){
        ## 先判断是否绑定
            if(!BotGameSignInUtil.isBindAccount(postData.sender.user_id+'')){
            ## println('请先绑定账号');
                OneBotUtils.sendGroupMessage(botNumber,groupId,'[CQ:at,qq='+ postData.sender.user_id +']'+'请先绑定账号，【命令】绑定账号+账号 示例:绑定账号xxxx',true);
            }elsif(!BotGameSignInUtil.isBindGameRole(postData.sender.user_id+'')){
                OneBotUtils.sendGroupMessage(botNumber,groupId,'[CQ:at,qq='+ postData.sender.user_id +']'+'请先绑定角色,'+BotGameSignInUtil.getGameRole(postData.sender.user_id+'')+',【命令】绑定角色+角色编号 示例:绑定角色11',true);
            }else{
                let res = BotGameSignInUtil.botSingIn(postData.sender.user_id+'');
                OneBotUtils.sendGroupMessage(botNumber,groupId,'[CQ:at,qq='+ postData.sender.user_id +']'+res,true);
            }
        }

        let bindAccountCmd = '!绑定账号';
        if(message.segmentSize == 1 && message.type == 'text' && StrUtil.startWith(message.simpleMessage,bindAccountCmd)){
            let account = CharSequenceUtil.subAfter(message.simpleMessage,bindAccountCmd,false);
            let res = BotGameSignInUtil.bindAccount(postData.sender.user_id+'',account);
            OneBotUtils.sendGroupMessage(botNumber,groupId,'[CQ:at,qq='+ postData.sender.user_id +']'+res,true);
        }

        let bindGameRoleCmd = '!绑定角色';
        if(message.segmentSize == 1 && message.type == 'text' && StrUtil.startWith(message.simpleMessage,bindGameRoleCmd)){
            let roleId = CharSequenceUtil.subAfter(message.simpleMessage,bindGameRoleCmd,false);
            if(BotScriptUtils.isLong(roleId)){
                let res = BotGameSignInUtil.bindGameRole(postData.sender.user_id+'',roleId);
                OneBotUtils.sendGroupMessage(botNumber,groupId,'[CQ:at,qq='+ postData.sender.user_id +']'+res,true);
            }
        }

    ##-------------------------------------------------------------------------------------------------------------------------
    }
}

println('AviatorScript end ...');

```

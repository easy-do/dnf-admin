-- 存储后台设置参数
AdminConfData = {}

---@type DPXGame
local dpx = _DPX
local dpReport = require("dpReport")
local logger = require("df.logger")
local json = require("json")

local DpAdminConf = {}

DpAdminConf.getConf = function(key)
    return AdminConfData[key]
end
DpAdminConf.AdminConfData = function()
    return AdminConfData
end

local flushed = function ()
       ---解除100级及以上的限制
    if DpAdminConf.getConf("disable_security_protection") then
        dpx.disable_security_protection()
    end
    --设置等级上限
    if DpAdminConf.getConf("max_level") then
        dpx.set_max_level(DpAdminConf.getConf("max_level"))
    end
    --绝望之塔通关后仍可继续挑战(需门票)
    if DpAdminConf.getConf("unlimit_towerofdespair") then
        dpx.set_unlimit_towerofdespair()
    end
    --物品免确认(异界装备不影响)
    if DpAdminConf.getConf("disable_item_routing") then
        dpx.disable_item_routing()
    end
    ---退出副本免虚弱
    if DpAdminConf.getConf("disable_giveup_panalty") then
        dpx.disable_giveup_panalty()
    end
    ---解锁镇魂开门任务
    if DpAdminConf.getConf("open_timegate") then
        dpx.open_timegate()
    end
    ---允许创建缔造者
    if DpAdminConf.getConf("enable_creator") then
        dpx.enable_creator()
    end
    ---开启GM功能, 注意添加管理员账号ID至数据库中
    if DpAdminConf.getConf("enable_game_master") then
        dpx.enable_game_master()
    end
    ---关闭新账号发送的契约邮件
    if DpAdminConf.getConf("disable_mobile_rewards") then
        dpx.disable_mobile_rewards()
    end
    ---解除交易限额, 已达上限的第二天生效
    if DpAdminConf.getConf("disable_trade_limit") then
        dpx.disable_trade_limit()
    end
    ---设置使用拍卖行的最低等级
    if DpAdminConf.getConf("set_auction_min_level") then
        dpx.set_auction_min_level(DpAdminConf.getConf("set_auction_min_level"))
    end
    ---修复拍卖行消耗品上架, 设置最大总价, 建议值2E
    if DpAdminConf.getConf("fix_auction_regist_item") then
        dpx.fix_auction_regist_item(DpAdminConf.getConf("fix_auction_regist_item"))
    end
    ---关闭NPC回购系统
    if DpAdminConf.getConf("disable_redeem_item") then
        dpx.disable_redeem_item()
    end
    ---禁用支援兵
    if DpAdminConf.getConf("disable_striker") then
        dpx.disable_striker()
    end
    ---禁用道具掉落随机强化
    if DpAdminConf.getConf("disable_drop_random_upgrade") then
        dpx.disable_drop_random_upgrade()
    end
    ---修改装备解锁时间
    if DpAdminConf.getConf("set_item_unlock_time") then
        dpx.set_item_unlock_time(DpAdminConf.getConf("set_item_unlock_time"))
    end
    ---修改掉落时随机赋予红字的装备最低等级 (默认55)
    ---此功能也会影响洗红字的等级
    if DpAdminConf.getConf("set_drop_amplify_level") then
        dpx.set_drop_amplify_level(DpAdminConf.getConf("set_drop_amplify_level"))
    end
end


DpAdminConf.flushedConf = function()
    local adminResult = dpReport.run('get_conf', 'ping');
    logger.info("dp flushedConf: %s", json.encode(adminResult.value))
    if adminResult ~= nil then
        for i, v in ipairs(adminResult.value) do
            local key = v.confKey;
            local confType = v.confType;
            local value = v.confData;
            if confType == 1 then
                AdminConfData[key] = tonumber(value)
            elseif confType == 3 then
                AdminConfData[key] = (value == "true")
            elseif confType == 4 and type(value) == "string" then
                AdminConfData[key] = json.decode(value)
            else
                AdminConfData[key] = value
            end
        end
        flushed()
        logger.info("dp flushedConf success: %s", json.encode(AdminConfData))
    end
end


return DpAdminConf

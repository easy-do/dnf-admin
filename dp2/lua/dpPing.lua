---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local json = require("json")
local luv = require("luv")
local dpReport = require("dpReport")

local dpPing = {}


local function pingAdmin()
    local adminValue = dpReport.run('ping', 'ping');
    if not nil then
        --将获得的信息交给frida处理,解决热重载冲突
        local ok, jsonStr = pcall(json.encode, adminValue)
        if ok then
            local arg0 = 9
            local arg1 = 9999
            local callOk, callResult = pcall(dp.frida.call, arg0,arg1,jsonStr)
            if callOk then
                logger.info("frida.call success: %d", callResult)
            else
                logger.info("frida.call fail: %s", callResult)
            end
        else
            logger.info("adminValue encode fail : %s", adminValue)
        end

    else
        logger.info("adminValue is nil")
    end
end

dpPing.run = function()
    -- 设置一个每秒的定时器
    local paodianTimer = luv.new_timer()
    -- 启动定时器
    paodianTimer:start(1000, 1000, pingAdmin)
end


return dpPing

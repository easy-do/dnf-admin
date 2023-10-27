---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local json = require("json")
local luv = require("luv")
local dpReport = require("dpReport")
local logger = require("df.logger")

local dpPing = {}

local function pingAdmin()
    local adminValue = dpReport.run('ping', 'ping');
    if adminValue ~= nil then
        --将获得的信息交给frida处理
        local ok, jsonStr = pcall(json.encode, adminValue.arg2)
        if ok then
            local debug = adminValue.debug and true or false
            if debug then
                logger.info("adminValue debug : %s", adminValue)
            end
            local callDp = adminValue.callDp and true or false
            if callDp then
                if debug then
                    logger.info("callDp debug : %s", adminValue)
                end
            end
            local callFrida = adminValue.callFrida and true or false
            if callFrida then
                local callOk, callResult = pcall(dp.frida.call, tonumber(adminValue.arg0),tonumber(adminValue.arg1),jsonStr)
                if not callOk then
                    logger.info("frida.call fail: %s", callResult)
                end       
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

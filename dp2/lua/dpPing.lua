---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local json = require("json")
local luv = require("luv")
local dpReport = require("dpReport")
local logger = require("df.logger")

local dpPing = {}


local function checkArg(arg)
    if arg ~= nil then
        local ok, jsonStr = pcall(json.encode, arg)
        if not ok then
            jsonStr = ''
        end
        return jsonStr
    end
    return ''
end

local function fridaCall(adminResult)
    local arg2 = checkArg(adminResult.arg2)
    local callOk, callResult = pcall(dp.frida.call, tonumber(adminResult.arg0), tonumber(adminResult.arg1), arg2)
    if not callOk then
        logger.info("frida.call fail: %s", callResult)
    end
end

local function execFridaCall(v)
    local callOk, callResult = pcall(dp.frida.call, 9999, 9999, checkArg(v))
    if not callOk then
        logger.info("frida.call fail: %s", callResult)
    end
end


local function dpCall(adminResult)
    local arg2 = checkArg(adminResult.arg2)
end

local function execDpCall(v)

end




local function pingAdmin()
    local adminResult = dpReport.run('ping', 'ping');
    if adminResult ~= nil then
        local debug = adminResult.debug and true or false
        if debug then
            logger.info("adminValue debug : %s", adminResult)
        end
        local callDp = adminResult.callDp and true or false
        if callDp then
            if debug then
                logger.info("callDp debug : %s", adminResult)
            end
            dpCall(adminResult)
        end
        local callFrida = adminResult.callFrida and true or false
        if callFrida then
            fridaCall(adminResult);
        end
        local execList = adminResult.arg2
        if adminResult.type == 'ping' and type(execList) == "table" then
            if debug then
                logger.info("exec execList debug : %s", execList)
            end
            for i, v in ipairs(execList) do
                logger.info("exec execList v debug : %s", v)
                local execDebug = v.debug and true or false
                local execCallDp = v.callDp and true or false
                local execCallFrida = v.callFrida and true or false
                if execDebug then
                    logger.info("exec debug : %d %s", i, v)
                    logger.info("exec execList v.debug debug : %s", v.debug)
                    logger.info("exec execList v.callDp : %s", v.callDp)
                    logger.info("exec execList v.callFrida debug : %s", v.callFrida)
                    logger.info("exec execList v.funName debug : %s", v.funName)
                    logger.info("exec execList v.args debug : %s", v.args)
                end 
                if execCallDp then
                    if debug then
                        logger.info("exec callDp debug : %s", v)
                    end
                    execDpCall(v)
                end
                if execCallFrida then
                    if debug then
                        logger.info("exec callFrida debug : %s", v)
                    end
                    execFridaCall(v)
                end
            end
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

-- 存储后台设置参数
AdminConfData = {}

local dpReport = require("dpReport")
local logger = require("df.logger")
local json = require("json")

local DpAdminConf = {}


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
        logger.info("dp flushedConf success: %s", json.encode(AdminConfData))
    end
end

DpAdminConf.getConf = function(key)
    return AdminConfData[key]
end


return DpAdminConf

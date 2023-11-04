

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
        for key, value in pairs(adminResult.value) do
            AdminConfData[key] = value
        end
    end
end

DpAdminConf.getConf = function (key)
    return AdminConfData[key]
end


return DpAdminConf

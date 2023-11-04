
---@type DPXGame
local dpx = _DPX
local http = require("socket.http")
local url = require("socket.url")
local json = require("json")
local logger = require("df.logger")

local opt = dpx.opt()
local dpReport = {}

dpReport.run = function(type, value)
    -- gmKey:为了通讯安全请与服务端同步设置复杂的密钥
    local gmKey = "123456789"
    --改为dnf-admin的ip和端口
    local adminAddr = "http://dnf-admin:8888"
    -- 开始通信
    local res = http.request(string.format("%s/api/dp/report?gmKey=%s&opt=%s&type=%s&value=%s", adminAddr, gmKey, opt, type, url.escape(value)))
    --    logger.info("send admin result: %s", res)
    if res ~= nil then
        local ok, resJson = pcall(json.decode, res)
        if ok then
            local code = resJson.code
            if code == 200 and resJson.success then
                return resJson.data
            else
                logger.info("send admin error: %s", resJson.message)
                return nil
            end
        else
            logger.info("json.decode admin error: %s", resJson)
        end
    else
        return nil
    end
end


return dpReport

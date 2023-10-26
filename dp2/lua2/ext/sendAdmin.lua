---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local world = require("df.game.mgr.world")
local game = require("df.game")
local logger = require("df.logger")
local http = require("socket.http")
local json = require("json")
local luv = require("luv")


local _M = {}
-- 向dnf-admin发送消息获取响应 type:消息类型 value:参数
function _M:run(type, value)
     -- 为了通讯安全请与服务端同步设置负责的密钥
     local gmKey = "123456789";
     local res = http.request(string.format("http://172.20.0.4:8888/api/test/test?gmkey=%s&type=%s&value=%s",gmKey,type,value))
     logger.info("send admin result: %s",b)
     if not nil then
         local ok, resJson = pcall(json.decode, res)
         if ok then  
             local code = resJson.code
             if code == 200 then
                 return resJson.data
             else
                 logger.info("send admin error: %s", resJson.message)
                 return nil
             end
		else  
		  logger.info("json.decode error: %s", resJson) 
		end
     else
         return nil 
     end
end

return _M

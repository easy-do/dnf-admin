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


-- 向dnf-admin发送消息保活获取相关信息
function _M:run(online)
    local key = "ext.sendAdmin";
    local sendAdmin = require(key)
	-- 设置一个每秒的定时器
    local paodianTimer = luv.new_timer()

    local function pingAdmin()
	    local adminValue = sendAdmin:run('ping','ping');
	    if not nil then
	        logger.info("adminValue: %s", adminValue) 
	    else
             logger.info("adminValue is nil")
	    end
	end
	paodianTimer:start(1000, 1000, pingAdmin)
end

return _M

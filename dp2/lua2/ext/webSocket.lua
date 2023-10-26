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

function _M:run(_user, input)

	-- 设置一个每秒的定时器
	local paodianTimer = luv.new_timer()

	local function pingAdmin()
	     local b = http.request("http://172.20.0.4:8888/api/test/test?type=ping&value=null")
    		logger.info("requet admin result: %s",b)
    		local bjson = json.decode(b)
    		local code = bjson.code
    		local message = bjson.message
    		local success = bjson.success
	end

	paodianTimer:start(1000, 1000, pingAdmin)

end

return _M

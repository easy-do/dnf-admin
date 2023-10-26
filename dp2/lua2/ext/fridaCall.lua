---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local world = require("df.game.mgr.world")
local game = require("df.game")
local logger = require("df.logger")

local _M = {}

function _M:run(arg1, arg2, arg3)
    logger.warn("from frida call, arg1=%d, arg2=%f, arg3=%s", arg1, arg2, arg3)
end

return _M

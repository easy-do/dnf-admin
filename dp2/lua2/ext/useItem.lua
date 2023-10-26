---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local world = require("df.game.mgr.world")
local game = require("df.game")
local logger = require("df.logger")

local _M = {}

function _M:run(_user, item_id, online)
    local user = game.fac.user(_user)
    logger.info("道具消耗事件: %s %d",user:GetCharacName(), item_id)
end

return _M

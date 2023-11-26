---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local world = require("df.game.mgr.world")
local game = require("df.game")
local logger = require("df.logger")

local _M = {}

function _M:run(type, _party, param, online)
    logger.info("游戏事件: %d %s",type,param)
    --    if type == 7 then
    --        local party = game.fac.party(_party)
    --	   local dungeon = party:GetDungeon()
    --	   local dungeonName = dungeon:GetName()
    --        --遍历在线玩家
    --        for k, v in pairs(online) do
    --            local uid = k
    --            local ptr = world.FindUserByAcc(uid)
    --            local user = game.fac.user(ptr)
    --            user:SendNotiPacketMessage(string.format("玩家【%s】无可匹敌,通关了【%s】",user:GetCharacName(),dungeonName),14)
    --        end
    --    end
end

return _M

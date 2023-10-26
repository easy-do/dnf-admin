---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local world = require("df.game.mgr.world")
local game = require("df.game")
local logger = require("df.logger")

local _M = {}

function _M:run(_party,monster_id,online)
    -- logger.info("副本掉落事件,怪物id %d",monster_id)
    -- local party = game.fac.party(_party)
    -- local dungeon = party:GetDungeon()
    -- local dungeonName = dungeon:GetName()
    -- --遍历在线玩家
    -- for k, v in pairs(online) do
    --     local uid = k
    --     local ptr = world.FindUserByAcc(uid)
    --     local user = game.fac.user(ptr)
    --     user:SendNotiPacketMessage(string.format("玩家【%s】运气爆棚,在【%s】中掉落物品",user:GetCharacName(),dungeonName),14)
    -- end
    return true
end

return _M

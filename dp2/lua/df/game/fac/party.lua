local _M = {}
local setmetatable = setmetatable

local ffi = require("ffi")
local sym = require("df.game.symbols.core")

---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX
---@type DF.Game.Factory
local ufac -- upper fac

-- dp2
ffi.cdef [[
    unsigned int CParty_GetDungeon(CParty* party); // return CDungeon*
    unsigned int CParty_GetBattleField(CParty* party); // return CBattle_Field*
    int CParty_GetPlayTick(CParty* party);
    int CParty_GetCurMapPlayTick(CParty* party);
    int CParty_IsUserAlive(CParty* party, int pos);
]]

-- game
ffi.cdef [[
    int CParty_CheckValidUser(CParty* party, int pos);
    unsigned int CParty_GetUser(CParty* party, int pos); // return CUser*
    int CParty_ClearDungeon(CParty* party);
    int CParty_GetMapPlayingTime(CParty* party);
    int CParty_UseAncientDungeonItems(CParty* party, CDungeon* dungeon, Inven_Item* item, int*);
    int CParty_GetState(CParty* party);
    int CParty_GetDungeonClearState(CParty* party);
    int CParty_CheckBossRoom(CParty* party);
    int CParty_GetLiveCount(CParty* party);
    int CParty_GetMemberCount(CParty* party);
    int CParty_IsStartRoom(CParty* party);
    int CParty_GetPartyIndex(CParty* party);
    int CParty_ReturnToVillage(CParty* party);
    unsigned int CParty_GetManager(CParty* party); // return CUser*
]]

sym.alias("CParty_GetUser", "_ZN6CParty8get_userEi")
sym.alias("CParty_CheckValidUser", "_ZN6CParty14checkValidUserEi")
sym.alias("CParty_ClearDungeon", "_ZN6CParty12ClearDungeonEv")
sym.alias("CParty_GetMapPlayingTime", "_ZN6CParty17GetMapPlayingTimeEv")
sym.alias("CParty_UseAncientDungeonItems", "_ZN6CParty22UseAncientDungeonItemsEPK8CDungeonP10Inven_ItemPi")
sym.alias("CParty_GetState", "_ZN6CParty9get_stateEv")
sym.alias("CParty_GetDungeonClearState", "_ZN6CParty23get_dungeon_clear_stateEv")
sym.alias("CParty_CheckBossRoom", "_ZN6CParty13checkBossRoomEv")
sym.alias("CParty_GetLiveCount", "_ZN6CParty14get_live_countEv")
sym.alias("CParty_GetMemberCount", "_ZN6CParty16get_member_countEv")
sym.alias("CParty_IsStartRoom", "_ZN6CParty14checkStartRoomEv")
sym.alias("CParty_GetPartyIndex", "_ZN6CParty13GetPartyIndexEv")
sym.alias("CParty_ReturnToVillage", "_ZN6CParty15ReturnToVillageEv")
sym.alias("CParty_GetManager", "_ZN6CParty10getManagerEv")

---@class CParty
---@field cptr userdata @原始C指针
---@field fptr userdata @用于ffi的指针
local mt = {}
local mtt = {
    __index = mt
}

---当前状态
---@return integer
function mt:GetState()
    return ffi.C.CParty_GetState(self.fptr)
end

---小队编号
---@return integer
function mt:GetIndex()
    return  ffi.C.CParty_GetPartyIndex(self.fptr)
end

---获得N号位玩家
---@param pos integer
---@return CUser @nullable
function mt:GetUser(pos)
    if not self:CheckValidUser(pos) then
        return nil
    end

    local p = ffi.C.CParty_GetUser(self.fptr, pos)

    if p ~= 0 then
        return ufac.user(dp.int2ptr(p))
    end

    return nil
end

---获得队长
---@return CUser
function mt:GetManager()
    local nptr = ffi.C.CParty_GetManager(self.fptr)

    if nptr ~= 0 then
        return ufac.user(dp.int2ptr(nptr))
    end

    return nil
end

---检查N号位玩家
---@param pos integer
---@return boolean
function mt:CheckValidUser(pos)
    if pos < 0 or pos > 3 then return false end
    return ffi.C.CParty_CheckValidUser(self.fptr, pos) == 1
end

---获得副本对象
---@return CDungeon
function mt:GetDungeon()
    local p = ffi.C.CParty_GetDungeon(self.fptr)

    if p ~= 0 then
        return ufac.dungeon(dp.int2ptr(p))
    end

    return nil
end

---获得副本战斗对象
---@return CBattle_Field
function mt:GetBattleField()
    local p = ffi.C.CParty_GetBattleField(self.fptr)

    if p ~= 0 then
        return ufac.battle_field(dp.int2ptr(p))
    end

    return nil
end

---获得副本内存活玩家数量
---@return integer
function mt:GetLiveCount()
    return ffi.C.CParty_GetLiveCount(self.fptr)
end

---获得副本内玩家数量
---@return integer
function mt:GetMemberCount()
    return ffi.C.CParty_GetMemberCount(self.fptr)
end

---玩家遍历迭代器
---
---for pos, user in Members() do end
---@return fun():integer, CUser
function mt:Members()
    local i = 0
    local iter

    iter = function()
        i = i + 1
        if i > 4 then
            return
        end

        local pos = i - 1
        local user = self:GetUser(pos)
        if user then
            return pos, user
        end

        return iter()
    end

    return iter
end

---遍历小队玩家
---@alias TEnumPartyUserHandler fun(user:CUser, pos:integer):boolean
---@param func TEnumPartyUserHandler
---@return boolean @遍历是否完成
function mt:ForEachMember(func)
    for i = 1, 4 do
        local pos = i - 1
        local user = self:GetUser(pos)

        if user then
            if not func(user, pos) then
                return false
            end
        end
    end

    return true
end

---是否在初始地图
---@return boolean
function mt:IsStartRoom()
    return (ffi.C.CParty_IsStartRoom(self.fptr) == 1)
end

---当前是否在BOSS房
---@return boolean
function mt:CheckBossRoom()
    return (ffi.C.CParty_CheckBossRoom(self.fptr) == 1)
end

---副本时长
---@return integer @milliseconds
function mt:GetPlayTick()
    return ffi.C.CParty_GetPlayTick(self.fptr)
end

---当前地图时长
---@return integer @milliseconds
function mt:GetCurMapPlayTick()
    return ffi.C.CParty_GetCurMapPlayTick(self.fptr)
end

---玩家是否存活
---@param pos integer @ 0~3
---@return boolean
function mt:IsUserAlive(pos)
    return ffi.C.CParty_IsUserAlive(self.fptr, pos) == 1
end

---返回城镇
---@return void
function mt:ReturnToVillage()
    ffi.C.CParty_ReturnToVillage(self.fptr)
end

local obj_t = ffi.typeof("CParty*")
local weakCache = setmetatable({}, {__mode = "v"})

---@param ptr userdata
---@return CParty
function _M.fac(ptr)
    local obj = weakCache[ptr]
    if obj then
        return obj
    end

    local t = {
        cptr = ptr,
        fptr = ffi.cast(obj_t, ptr),
    }

    obj = setmetatable(t, mtt)
    weakCache[ptr] = obj
    return obj
end

---@param fac DF.Game.Factory
---@return void
function _M.set_ufac(fac)
    ufac = fac
end

return _M

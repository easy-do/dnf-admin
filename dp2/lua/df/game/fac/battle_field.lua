local _M = {}
local setmetatable = setmetatable

local ffi = require("ffi")
local sym = require("df.game.symbols.core")

---@type DP
local dp = _DP
---@type DF.Game.Factory
local ufac -- upper fac

ffi.cdef [[
    // dp2
    int CBattleField_IsHellParty(CBattle_Field* ptr);
    unsigned int CBattleField_GetDungeon(CBattle_Field *battle); // CDungeon*

    // game
    int CBattle_Field_IsQuestMaze(CBattle_Field* ptr);
    int CBattle_Field_GetDungeonDiff(CBattle_Field* ptr);
    int CBattle_Field_IsEnableHellDungeon(CBattle_Field* ptr);
    int CBattle_Field_GetCurPosXY(CBattle_Field* ptr, int *a2, int *a3);
    int CBattle_Field_GetCurrentMapIndex(CBattle_Field* ptr);
]]

sym.alias("CBattle_Field_IsQuestMaze", "_ZN13CBattle_Field11isQuestMazeEv")
sym.alias("CBattle_Field_GetDungeonDiff", "_ZN13CBattle_Field16get_dungeon_diffEv")
sym.alias("CBattle_Field_IsEnableHellDungeon", "_ZN13CBattle_Field19IsEnableHellDungeonEv")
sym.alias("CBattle_Field_GetCurPosXY", "_ZN13CBattle_Field11getCurPosXYERiS0_")
sym.alias("CBattle_Field_GetCurrentMapIndex", "_ZN13CBattle_Field18GetCurrentMapIndexEv")

local int_t = ffi.typeof("int[1]")

---@class CBattle_Field
---@field cptr userdata @原始C指针
---@field fptr userdata @用于ffi的指针
local mt = {}
local mtt = {
    __index = mt
}

---是否为任务副本
---@return boolean
function mt:IsQuestMaze()
    return (ffi.C.CBattle_Field_IsQuestMaze(self.fptr) == 1)
end

---副本难度
---@return integer
function mt:GetDungeonDiff()
    return ffi.C.CBattle_Field_GetDungeonDiff(self.fptr)
end

---是否开启深渊 (未确认)
---@return boolean
function mt:IsEnableHellDungeon()
    return (ffi.C.CBattle_Field_IsEnableHellDungeon(self.fptr) == 1)
end

---是否为开启深渊
---@return boolean
function mt:IsHellParty()
    return (ffi.C.CBattleField_IsHellParty(self.fptr) == 1)
end

--- 获得当前房间坐标
---@return integer, integer
function mt:GetCurPos()
    local cache = self.cache
    if not cache then
        cache = {}
        self.cache = cache
    end

    if not cache.x then
        cache.x = ffi.new(int_t)
        cache.y = ffi.new(int_t)
    end

    cache.x[0] = -1
    ffi.C.CBattle_Field_GetCurPosXY(self.fptr, cache.x, cache.y)
    local x, y = cache.x[0], cache.y[0]
    return x, y
end

---获得副本对象
---@return CDungeon
function mt:GetDungeon()
    local ptr = ffi.C.CBattleField_GetDungeon(self.fptr)

    return ufac.dungeon(dp.int2ptr(ptr))
end

---获得当前地图ID
---@return integer
function mt:GetCurrentMapIndex()
    return ffi.C.CBattle_Field_GetCurrentMapIndex(self.fptr)
end

local obj_t = ffi.typeof("CBattle_Field*")
local weakCache = setmetatable({}, {__mode = "v"})

---@param ptr userdata
---@return CBattle_Field
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

local _M = {}
local setmetatable = setmetatable

local ffi = require("ffi")
local sym = require("df.game.symbols.core")

ffi.cdef [[
    // impl at dp2
    int CDungeon_IsEventDungeon(CDungeon* dungeon);
    int CDungeon_IsAncientDungeon(CDungeon* dungeon);

    // impl at game
    int CDungeon_GetIndex(CDungeon* dungeon);
    int CDungeon_GetMinLevel(CDungeon* dungeon);
    int CDungeon_GetStandardLevel(CDungeon* dungeon);
    int CDungeon_GetKind(CDungeon* dungeon);
    const char* CDungeon_GetName(CDungeon* dungeon);
    int CDungeon_IsRiskDungeon(CDungeon* dungeon);
    int CDungeon_GetDimensionPossible(CDungeon* dungeon);
    ]]

sym.alias("CDungeon_GetIndex", "_ZNK8CDungeon9get_indexEv")
sym.alias("CDungeon_GetMinLevel", "_ZNK8CDungeon13get_min_levelEv")
sym.alias("CDungeon_GetStandardLevel", "_ZNK8CDungeon18get_standard_levelEv")
sym.alias("CDungeon_GetKind", "_ZNK8CDungeon14getDungeonKindEv")
sym.alias("CDungeon_GetName", "_ZNK8CDungeon14GetDungeonNameEv")
sym.alias("CDungeon_IsRiskDungeon", "_ZNK8CDungeon13isRiskDungeonEv")
sym.alias("CDungeon_GetDimensionPossible", "_ZNK8CDungeon22get_dimension_possibleEv")

---@class CDungeon
---@field cptr userdata @原始C指针
---@field fptr userdata @用于ffi的指针
local mt = {}
local mtt = {
    __index = mt
}

---副本名称
---@return string
function mt:GetName()
    local str = ffi.C.CDungeon_GetName(self.fptr)

    return ffi.string(str)
end

---副本类型
---@return integer
function mt:GetKind()
    return ffi.C.CDungeon_GetKind(self.fptr)
end

---副本编号
---@return integer
function mt:GetIndex()
    return ffi.C.CDungeon_GetIndex(self.fptr)
end

---副本进入等级
---@return integer
function mt:GetMinLevel()
    return ffi.C.CDungeon_GetMinLevel(self.fptr)
end

---副本标准等级
---@return integer
function mt:GetStandardLevel()
    return ffi.C.CDungeon_GetStandardLevel(self.fptr)
end

---魂图?
---@return boolean
function mt:IsRiskDungeon()
    return (ffi.C.CDungeon_IsRiskDungeon(self.fptr) == 1)
end

---是否为活动副本
---@return boolean
function mt:IsEventDungeon()
    return (ffi.C.CDungeon_IsEventDungeon(self.fptr) == 1)
end

---是否为远古副本
---@return boolean
function mt:IsAncientDungeon()
    return (ffi.C.CDungeon_IsAncientDungeon(self.fptr) == 1)
end

---是否为异界副本
---@return boolean
function mt:IsDimensionDungeon()
    -- 这个接口实际是获取异界副本编号
    return (ffi.C.CDungeon_GetDimensionPossible(self.fptr) > 0)
end

local obj_t = ffi.typeof("CDungeon*")
local weakCache = setmetatable({}, {__mode = "v"})

---@param ptr userdata
---@return CDungeon
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

return _M

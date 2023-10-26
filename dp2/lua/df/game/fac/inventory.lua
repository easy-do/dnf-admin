local _M = {}
local setmetatable = setmetatable

local ffi = require("ffi")
local sym = require("df.game.symbols.core")

ffi.cdef [[
int CInventory_check_item_exist(CInventory*, int);
int CInventory_check_empty_count(CInventory*, int, int);
]]
sym.alias("CInventory_check_item_exist", "_ZNK10CInventory16check_item_existEi")
sym.alias("CInventory_check_empty_count", "_ZNK10CInventory17check_empty_countEN10Inven_Item9ITEM_TYPEEi")

---@class CInventory
---@field cptr userdata @原始C指针
---@field fptr userdata @用于ffi的指针
local mt = {}
local mtt = {
    __index = mt
}

---@param item_id integer
---@return integer @slot or -1
function mt:CheckItemExist(item_id)
    return ffi.C.CInventory_check_item_exist(self.fptr, item_id)
end

---@param item_type integer
---@param item_count integer
---@return boolean
function mt:CheckEmptyCount(item_type, item_count)
    return ffi.C.CInventory_check_empty_count(self.fptr, item_type, item_count or 1) == 1
end

local obj_t = ffi.typeof("CInventory*")
local weakCache = setmetatable({}, {__mode = "v"})

---@param ptr userdata
---@return CInventory
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

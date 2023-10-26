local _M = {}
local setmetatable = setmetatable

local ffi = require("ffi")
local sym = require("df.game.symbols.core")

ffi.cdef[[
    struct CExpertJob;

    int CExpertJob_GetType(CExpertJob*);
]]

sym.alias("CExpertJob_GetType", "_ZN10expert_job10CExpertJob7GetTypeEv")

---@class CExpertJob
---@field cptr userdata @原始C指针
---@field fptr userdata @用于ffi的指针
local mt = {}
local mtt = {
    __index = mt
}

---副职业类型
---@return integer
function mt:GetType()
    return ffi.C.CExpertJob_GetType(self.fptr)
end

local obj_t = ffi.typeof("CExpertJob*")
local weakCache = setmetatable({}, {__mode = "v"})

---@param ptr userdata
---@return CExpertJob
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

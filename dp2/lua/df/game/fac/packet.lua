local _M = {}
local setmetatable = setmetatable

local ffi = require("ffi")
local sym = require("df.game.symbols.core")

ffi.cdef [[
    int PacketBuf_Clear(PacketBuf* obj);
    int PacketBuf_GetIndex(PacketBuf* obj);
    void PacketBuf_SetIndex(PacketBuf* obj, int pos);
    char* PacketBuf_GetPacket(PacketBuf* obj, int offset);

    int PacketBuf_GetInt8(PacketBuf* obj, char* v);
    int PacketBuf_GetInt16(PacketBuf* obj, short* v);
    int PacketBuf_GetInt32(PacketBuf* obj, int* v);
    int PacketBuf_PutInt8(PacketBuf* obj, int v);
    int PacketBuf_PutInt16(PacketBuf* obj, int v);
    int PacketBuf_PutInt32(PacketBuf* obj, int v);
    int PacketBuf_PutHeader(PacketBuf* obj, int type, int id);
    int PacketBuf_Finalize(PacketBuf* obj, int v);
    int PacketBuf_IsFinalized(PacketBuf* obj);
    ]]

sym.alias("PacketBuf_Clear", "_ZN9PacketBuf5clearEv")
sym.alias("PacketBuf_GetIndex", "_ZN9PacketBuf9get_indexEv")
sym.alias("PacketBuf_SetIndex", "_ZN9PacketBuf9set_indexEi")
sym.alias("PacketBuf_GetPacket", "_ZN9PacketBuf10get_packetEi")
sym.alias("PacketBuf_GetInt8", "_ZN9PacketBuf8get_byteERc")
sym.alias("PacketBuf_GetInt16", "_ZN9PacketBuf9get_shortERs")
sym.alias("PacketBuf_GetInt32", "_ZN9PacketBuf7get_intERi")
sym.alias("PacketBuf_PutInt8", "_ZN9PacketBuf8put_byteEi")
sym.alias("PacketBuf_PutInt16", "_ZN9PacketBuf9put_shortEi")
sym.alias("PacketBuf_PutInt32", "_ZN9PacketBuf7put_intEi")
sym.alias("PacketBuf_PutHeader", "_ZN9PacketBuf10put_headerEii")
sym.alias("PacketBuf_Finalize", "_ZN9PacketBuf8finalizeEb")
sym.alias("PacketBuf_IsFinalized", "_ZNK9PacketBuf13is_finallizedEv")

---@class PacketBuf
---@field cptr userdata @原始C指针
---@field fptr userdata @用于ffi的指针
local mt = {}
local mtt = {
    __index = mt
}

---@return void
function mt:Clear()
    return ffi.C.PacketBuf_Clear(self.fptr)
end

---@return integer
function mt:GetIndex()
    return ffi.C.PacketBuf_GetIndex(self.fptr)
end

---@param pos integer
---@return void
function mt:SetIndex(pos)
    ffi.C.PacketBuf_SetIndex(self.fptr, pos)
end

---@param offset integer
---@return userdata @ffi cdata
function mt:GetPacket(offset)
    return ffi.C.PacketBuf_GetPacket(self.fptr, offset or 0)
end

---@return integer
function mt:GetInt8()
    local v = ffi.new('char[1]')
    local n = ffi.C.PacketBuf_GetInt8(self.fptr, v)
    if n ~= 1 then return nil end
    return v[0]
end

---@return integer
function mt:GetInt16()
    local v = ffi.new('short[1]')
    local n = ffi.C.PacketBuf_GetInt16(self.fptr, v)
    if n ~= 1 then return nil end
    return v[0]
end

---@return integer
function mt:GetInt32()
    local v = ffi.new('int[1]')
    local n = ffi.C.PacketBuf_GetInt32(self.fptr, v)
    if n ~= 1 then return nil end
    return v[0]
end

---@param b boolean
function mt:Finalize(b)
    return ffi.C.PacketBuf_Finalize(self.fptr, b and 1 or 0)
end

--[[
function mt:PutHeader(type, id)
    return ffi.C.PacketBuf_PutHeader(self.fptr, type, id)
end

function mt:PutInt8(v)
    return ffi.C.PacketBuf_PutInt8(self.fptr, v)
end

function mt:PutInt16(v)
    return ffi.C.PacketBuf_PutInt16(self.fptr, v)
end

function mt:PutInt32(v)
    return ffi.C.PacketBuf_PutInt32(self.fptr, v)
end

function mt:IsFinalized()
    return (ffi.C.PacketBuf_IsFinalized(self.fptr) == 1);
end
--]]

local obj_t = ffi.typeof("PacketBuf*")
local weakCache = setmetatable({}, {__mode = "v"})

---@param ptr userdata
---@return PacketBuf
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

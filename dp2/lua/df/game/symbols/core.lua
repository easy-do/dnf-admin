---@class DF.sym
local _M = {}

---@type DP
local dp = _DP
local dpx = _DPX
local ffi = require("ffi")
local alien = require("alien")

local tonumber = tonumber

local symtable = { }

---@param name string
---@param addr userdata
local function add(name, addr)
    symtable[name] = addr
end
---@param name string
---@return userdata
local function get(name)
    return symtable[name]
end

do
    local records = require("df.game.symbols.records")

    for k, v in pairs(records) do
        local va = tonumber(k);

        if va then
            local name = v;
            local addr = dpx.reloc(va)

            add(name, dp.int2ptr(addr))
        end
    end

    dpx.for_sym(function(name, addr)
        add(name, addr)
    end)
end

-- 设置符号查找器
ffi.resolver(ffi.C, get)

_M.add = add
_M.get = get

---@param dst string @新符号
---@param src string @老符号
---@return void
function _M.alias(dst, src)
    symtable[dst] = symtable[src]
end

---@param name string @符号名称
---@return alien.func
function _M.alienfunc(name)
    return alien.funcptr(get(name))
end

return _M

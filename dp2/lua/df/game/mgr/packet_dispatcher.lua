---@class PacketDispatcher
local _M = {}
---@type DP
local dp = _DP
local sym = require("df.game.symbols.core")
local alien = require("alien")

local dispCache = {}

local GetPtr = sym.alienfunc("_Z18G_PacketDispatcherv")
GetPtr:types("pointer")

local GetDispatcher = sym.alienfunc("_ZN16PacketDispatcher14get_dispatcherEi")
GetDispatcher:types("pointer", "pointer", "int")

---@param id integer
---@param user userdata @CUser ptr
---@param buf userdata @PacketBuf ptr
---@return integer
function _M.Dispatch(id, user, buf)
    if id < 0 or id > 605 then
        return 0
    end

    ---@type fun(u:userdata, b:userdata):integer
    local doDispatch = dispCache[id]
    if not doDispatch then
        local ptr = GetDispatcher(GetPtr(), id)
        if not ptr then
            doDispatch = function(u, b) return 0 end
        else
            -- [[ptr] + 0]
            local func = dp.mem.read(ptr, 0, 0)
            local dispatch = alien.funcptr(dp.int2ptr(func))
            dispatch:types("int", "pointer", "pointer", "pointer")

            doDispatch = function(u, b)
                return dispatch(ptr, u, b)
            end
        end

        dispCache[id] = doDispatch
    end

    return doDispatch(user, buf)
end

---@param id integer
---@return userdata @ptr
function _M.GetDispatcher(id)
    return GetDispatcher(GetPtr(), id)
end

return _M

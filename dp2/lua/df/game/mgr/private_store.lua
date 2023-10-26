---@class PrivateStore
local _M = {}
local sym = require("df.game.symbols.core")

local GetPtr = sym.alienfunc("_ZN13private_store26GetInstancePrivateStoreMgrEv")
local lpfnIsBusyPrivateStore = sym.alienfunc("_ZN13private_store16CPrivateStoreMgr18IsBusyPrivateStoreEP5CUser")

GetPtr:types("pointer")
lpfnIsBusyPrivateStore:types("char", "pointer", "pointer")

---@param userptr userdata
---@return boolean
function _M.IsBusyPrivateStore(userptr)
    return lpfnIsBusyPrivateStore(GetPtr(), userptr) ~= 0
end

return _M

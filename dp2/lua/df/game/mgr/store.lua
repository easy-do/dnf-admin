---@class Store
local _M = {}
local sym = require("df.game.symbols.core")

local G_Store = sym.alienfunc("_Z7G_Storev")
local lpfn_user_sell_item = sym.alienfunc("_ZN5Store14user_sell_itemEP5CUsercss")

G_Store:types("pointer")
lpfn_user_sell_item:types("int", "pointer", "pointer", "int", "int", "int")

---@param userptr userdata
---@param space ItemSpace
---@param slot integer
---@param count integer
---@return boolean
function _M.user_sell_item(userptr, space, slot, count)
    return lpfn_user_sell_item(G_Store(), userptr, space, slot, count) == 0
end

return _M

---@class CEnvironment
local _M = {}
local sym = require("df.game.symbols.core")

local G_CEnvironment = sym.alienfunc("_Z14G_CEnvironmentv")
G_CEnvironment:types("pointer")

local lpfn_get_server_group = sym.alienfunc("_ZN12CEnvironment16get_server_groupEv")
lpfn_get_server_group:types("int", "pointer")

---@return integer
function _M.get_server_group()
    return lpfn_get_server_group(G_CEnvironment())
end

return _M

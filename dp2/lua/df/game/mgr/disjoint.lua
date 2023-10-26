---@class Disjoint
local _M = {}
local sym = require("df.game.symbols.core")

local disjoint = sym.alienfunc("_ZN23DisPatcher_DisJointItem9_disjointEP5CUserii14ENUM_CMDPACKETS1_t")

disjoint:types("int", "pointer", "int", "int", "int", "pointer", "int")

---@param caller userdata
---@param slot integer
---@param space ItemSpace
---@param callee userdata @nullable
---@return void
function _M.Invoke(caller, slot, space, callee)
    disjoint(caller, slot, space, 239, callee, 0xFFFF)
end

return _M

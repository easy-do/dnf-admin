---@class GameWorld
local _M = {}
local sym = require("df.game.symbols.core")

local G_GameWorld = sym.alienfunc("_Z11G_GameWorldv")

local find_session = sym.alienfunc("_ZN9GameWorld12find_sessionEj")
local find_from_world = sym.alienfunc("_ZN9GameWorld15find_from_worldEt")
local find_user_by_charac_name = sym.alienfunc("_ZN9GameWorld24find_user_by_charac_nameEPKc")
local get_UserCount_InWorld = sym.alienfunc("_ZN9GameWorld21get_UserCount_InWorldEv")
local send_all = sym.alienfunc("_ZN9GameWorld8send_allER11PacketGuard")
local is_pvp_channel = sym.alienfunc("_ZNK9GameWorld12IsPVPChannelEv")

G_GameWorld:types("pointer")

find_session:types("ushort", "pointer", "uint")
find_from_world:types("pointer", "pointer", "ushort")
find_user_by_charac_name:types("pointer", "pointer", "string")
get_UserCount_InWorld:types("uint", "pointer")
send_all:types("void", "pointer", "pointer")
is_pvp_channel:types("int", "pointer")

---@param accId integer
---@return integer @session
function _M.FindSession(accId)
    return find_session(G_GameWorld(), accId)
end

---@param session integer
---@return userdata @nullable
function _M.FindFromWorld(session)
    if session == 0 then return nil end
    return find_from_world(G_GameWorld(), session)
end

---@param accId integer
---@return userdata @nullable
function _M.FindUserByAcc(accId)
    local g = G_GameWorld()
    local session = find_session(g, accId)

    if session == 0 then
        return nil
    end

    return find_from_world(g, session)
end

---@param name string
---@return userdata @nullable
function _M.FindUserByName(name)
    return find_user_by_charac_name(G_GameWorld(), name)
end

---@return integer
function _M.GetUserCount()
    return get_UserCount_InWorld(G_GameWorld())
end

---@param pack DPXGame.PacketGuard
---@return void
function _M.SendAll(pack)
    send_all(G_GameWorld(), pack)
end

---@return boolean
function _M.IsPvpChannel()
    return is_pvp_channel(G_GameWorld()) ~= 0
end

return _M

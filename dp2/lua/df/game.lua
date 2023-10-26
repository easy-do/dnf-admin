---@class DF.Game
local _M = {}

local ffi = require("ffi")
local sym = require("df.game.symbols.core")

ffi.cdef [[
    struct Inven_Item;

    struct PacketBuf;
    struct CUser;
    struct CParty;
    struct CDungeon;
    struct CBattle_Field;
    struct CInventory;
]]

local pbuf = require("df.game.fac.packet")
local user = require("df.game.fac.user")
local party = require("df.game.fac.party")
local dungeon = require("df.game.fac.dungeon")
local battle_field = require("df.game.fac.battle_field")
local expert_job = require("df.game.fac.expert_job")
local inventory = require("df.game.fac.inventory")

--- @class DF.Game.Factory
local fac = {}

---@param ptr userdata
---@return PacketBuf
function fac.buf(ptr) return pbuf.fac(ptr) end
---@param ptr userdata
---@return CUser
function fac.user(ptr) return user.fac(ptr) end
---@param ptr userdata
---@return CParty
function fac.party(ptr) return party.fac(ptr) end
---@param ptr userdata
---@return CDungeon
function fac.dungeon(ptr) return dungeon.fac(ptr) end
---@param ptr userdata
---@return CBattle_Field
function fac.battle_field(ptr) return battle_field.fac(ptr) end
---@param ptr userdata
---@return CExpertJob
function fac.exjob(ptr) return expert_job.fac(ptr) end
---@param ptr userdata
---@return CInventory
function fac.inventory(ptr) return inventory.fac(ptr) end

_M.fac = fac

user.set_ufac(fac)
party.set_ufac(fac)
battle_field.set_ufac(fac)

local hook = require("df.game.hook")

_M.HookType = hook.HookType
_M.GameEventType = hook.HookGameEventType

local enum = require("df.game.enum")

_M.DBType = enum.DBType
_M.ItemSpace = enum.ItemSpace
_M.ItemRarity = enum.ItemRarity
_M.QuestType = enum.QuestType
_M.AttachType = enum.AttachType
_M.InheritMask = enum.InheritMask
_M.ExpertJobType = enum.ExpertJobType

-- global misc function
do
    ffi.cdef [[
    int sleep_ext(int, int);
    const char* GetPacketName(int type, int id);
    int GetInvenTypeFromItemSpace(int);
    int IsLightServer();
    ]]

    sym.alias("sleep_ext", "_Z9sleep_extii")
    sym.alias("GetPacketName", "_Z13GetPacketName16ENUM_PACKETCLASSt")
    sym.alias("GetInvenTypeFromItemSpace", "_Z25GetInvenTypeFromItemSpace14ENUM_ITEMSPACE")
    sym.alias("IsLightServer", "_Z13IsLightServerv")

    ---@param sec integer
    ---@param usec integer
    ---@return void
    function _M.sleep_ext(sec, usec)
        ffi.C.sleep_ext(sec or 0, usec or 0)
    end

    ---获得包名
    ---@param type integer @0 or 1
    ---@param id integer
    function _M.GetPacketName(type, id)
        local str = ffi.C.GetPacketName(type, id)
        return ffi.string(str)
    end

    ---包裹类型转换
    ---@param space ItemSpace
    ---@return integer @InvenType
    function _M.GetInvenTypeFromItemSpace(space)
        return ffi.C.GetInvenTypeFromItemSpace(space)
    end

    ---@return boolean
    function _M.IsLightServer()
        return ffi.C.IsLightServer() ~= 0
    end
end

-- test ffi work fine
_M.sleep_ext(0, 0)

return _M

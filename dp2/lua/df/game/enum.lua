local _M = {}

---@class ItemSpace
local ItemSpace = {
    INVENTORY = 0,
    AVATAR = 1,
    CARGO = 2,
    EQUIPPED = 3,
    TRADE = 4,
    PRIVATE_STORE = 5,
    MAIL = 6,
    CREATURE = 7,
    COMPOUND_AVATAR = 8,
    USE_EMBLEM = 9,
    AVATAR_CONVERT = 10,
    ACCOUNT_CARGO = 12,
}

---装备品级
---@class ItemRarity
local ItemRarity = {
    common = 0,
    uncommon = 1,
    rare = 2,
    unique = 3,
    epic = 4,
    chronicle = 5,
}

---@class QuestType
local QuestType = {
    epic = 0,
    training = 1,
    achievement = 2,
    daily = 3,
    normaly_repeat = 4,
    common_unique = 5,
    special = 6,
    title = 7,
    urgent = 8,
}

---@class AttachType
local AttachType = {
    free = 0,
    trade = 1,
    trade_delete = 2,
    sealing = 3,
    sealing_trade = 4,
    account = 5,
}

---@class GlobMask
local GlobMask = {
    FLAG_EQUIP = 0x01,      -- 装备位
    FLAG_INVEN = 0x02,      -- 背包
    FLAG_CARGO = 0x04,      -- 角色仓库
    FLAG_ACC_CARGO = 0x08,  -- 账号金库
}

---@class InheritMask
---FLAG_XXX表示处理哪个属性,
---对应的FLAG_MOVE_XXX表示移除原属性, 不MOVE则复制属性
local InheritMask = {
    FLAG_UPGRADE = 0x01,
    FLAG_AMPLIFY = 0x02,
    FLAG_ENCHANT = 0x04,
    FLAG_SEPARATE = 0x08,

    FLAG_MOVE_UPGRADE = 0x0100,
    FLAG_MOVE_AMPLIFY = 0x0200,
    FLAG_MOVE_ENCHANT = 0x0400,
    FLAG_MOVE_SEPARATE = 0x0800,

    DEFAULT = 0x0303
}

---@class ExpertJobType
local ExpertJobType = {
    ---无
    None = 0,
    ---附魔师
    Enchanter = 1,
    ---炼金师
    Alchemist = 2,
    ---分解师
    Disjointer = 3,
    ---控偶师
    DollController = 4,
}

---@class DBType
local DBType = {
    d_taiwan = 1,
    taiwan_cain = 2,
    taiwan_cain_2nd = 3,
    d_guild = 8,
}

_M.DBType = DBType
_M.ItemSpace = ItemSpace
_M.ItemRarity = ItemRarity
_M.QuestType = QuestType
_M.AttachType = AttachType
_M.GlobMask = GlobMask
_M.InheritMask = InheritMask
_M.ExpertJobType = ExpertJobType

return _M

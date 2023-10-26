local _M = {}

---@class HookType
---每种类型后面的注释是回调函数的默认实现
---
---类型上面的注解是对回调函数的特别解释
local HookType = {
    ---副本扣除门票
    ---@param fnext fun():boolean
    ---@param _party userdata @CParty
    ---@param _dungeon userdata @CDungeon
    ---@param iitem DPXGame.InvenItem @nullable
    ---@return boolean
    CParty_UseAncientDungeonItems = 1,  -- function(fnext, _party, _dungeon, iitem) return fnext() end

    ---下线保存城镇ID
    ---@param _user userdata @CUser
    ---@param pre_town_id integer @下线的城镇
    ---@param post_town_id integer @即将保存的城镇
    ---@return integer @最终要保存的城镇ID
    CUser_SaveTown = 8,                 -- function(_user, pre_town_id, post_town_id) return post_town_id end

    ---副本怪物掉落过滤
    ---@param _party userdata @CParty
    ---@param monster_id integer @怪物ID
    ---@return boolean @是否掉落物品
    CParty_DropItem = 9,                -- function(_party, monster_id) return true end

    ---放弃副本
    ---@param fnext fun():void
    ---@param _party userdata @CParty
    ---@param _user userdata @CUser
    ---@return void
    CParty_GiveUpGame = 10,             -- function(fnext, _party, _user) fnext() end

    ---(*) 魔法封印自定义合成
    ---@param fnext fun(rarity:integer):integer @从哪个稀有度池中随机, 原版只有紫
    ---@param _user userdata @CUser
    ---@param item1 DPXGame.ItemInfo
    ---@param item2 DPXGame.ItemInfo
    ---@return integer @item_id
    RandomOption_Regen = 11,            -- function(fnext, _user, item1, item2) return fnext(game.ItemRarity.rare) end

    ---是否为可回购道具
    ---与dpx.disable_redeem_item()冲突
    ---@param fnext fun():boolean
    ---@param item_id integer
    ---@return boolean
    CItem_IsRedeemItem = 12,            -- function(fnext, item_id) return fnext() end

    ---是否开启副本, 如11007极限祭坛
    ---@param fnext fun(idx:integer):boolean
    ---@param dgn_idx integer @副本ID
    ---@return boolean @是否开启
    Open_Dungeon = 13,                  -- function(fnext, dgn_idx) return fnext(dgn_idx) end

    ---玩家上线
    ---@param _user userdata @CUser
    ---@return void
    Reach_GameWord = 15,                -- function(_user) return end

    ---玩家下线
    ---@param _user userdata @CUser
    ---@return void
    Leave_GameWord = 16,                -- function(_user) return end

    ---消耗道具A
    ---@param _user userdata @CUser
    ---@param item_id integer
    ---@return void
    UseItem1 = 17,                      -- function(_user, item_id) return end

    ---消耗道具B
    ---@param _user userdata @CUser
    ---@param item_id integer
    ---@return void
    UseItem2 = 18,                      -- function(_user, item_id) return end

    ---内置反外挂通知
    ---@param _user userdata @CUser
    ---@param type integer @触发类型
    ---@param count integer @触发计数
    ---@return void
    HackCounter = 21,                   -- function(_user, type, count) return end

    ---玩家获得经验
    ---@param fnext fun(_user:userdata,exp:integer,reason:integer):boolean
    ---@param _user userdata @CUser
    ---@param exp integer @经验值
    ---@param reason integer @事件来源
    ---@param info table @额外信息
    ---@return boolean
    ExpGain = 22,                       -- function(fnext, _user, exp, reason, info) return fnext(_user, exp, reason) end

    ---特权指令
    ---@param fnext fun():integer
    ---@param _user userdata @CUser
    ---@param input string @输入内容
    ---@return integer @错误码, 非0玩家掉线
    GmInput = 23,                       -- function(fnext, _user, input) return fnext() end

    ---虚弱惩罚, 替换惩罚逻辑
    ---什么都不做则不虚弱
    ---与dpx.disable_giveup_panalty()冲突
    ---@param fnext fun():void
    ---@param _user userdata @CUser
    ---@return void
    GiveupPanalty = 25,                 -- function(fnext, _user) return fnext() end

    ---游戏事件, 此功能较复杂
    ---@param fnext fun():any
    ---@param _party userdata @CParty
    ---@param type GameEventType
    ---@param param table @type不同内容不同
    ---@return any
    GameEvent = 26,                     -- function(fnext, type, _party, param) return fnext() end

    ---玩家交易通知
    ---@param info TradeEventParam
    ---@return void
    TradeEvent = 27,                    -- function(info) return end

    ---(v1) 个人商店交易通知
    ---@param info StoreEventParam
    ---@return void
    StoreEvent = 28,                    -- function (info) return end

    ---(v1) 锻造过滤
    ---@param fnext fun():boolean
    ---@param _user userdata @CUser
    ---@param iitem DPXGame.InvenItem
    ---@return boolean
    UpgradeSeparate = 29,               -- function(fnext, _user, iitem) return fnext() end

    ---副本开始通知
    ---@param _party userdata @CParty
    ---@return void
    DungeonAfterLoading = 30,           -- function(_party) end

    ---强化/增幅 过滤
    ---@param fnext fun():boolean
    ---@param _user userdata @CUser
    ---@param iitem DPXGame.InvenItem
    ---@return boolean
    Upgrade = 31,                       -- function(fnext, _user, iitem) return fnext() end

    -- 以下为未开放功能

    Packet_Send = 100,                   -- function(fnext, _user, _buf) return fnext() end
    Packet_Recv = 101,                   -- function(fnext, _user, cls, id, src, len) return fnext(cls, id, src, len) end
    Packet_Update = 102,                 -- function(fnext, _user, header) return fnext() end
    Packet_DisConnSig = 103,             -- no handler
}

---@class GameEventType
local GameEventType = {
    PARTY_CREATE = 1, -- 小队创建
    PARTY_DESTORY = 2, -- 小队销毁 (单人副本会未重置直接销毁)
    PARTY_JOIN_USER = 3, -- 小队玩家加入
    PARTY_LEAVE_USER = 4, -- 小队玩家离开
    PARTY_DUNGEON_START = 5, -- 副本开始
    PARTY_DUNGEON_CLEAR = 6, -- 副本通关
    PARTY_DUNGEON_FINISH = 7, -- 副本完成(翻牌)
    PARTY_DUNGEON_DESTORY = 8, -- 副本销毁/重置
    PARTY_DUNGEON_MAP_CLEAR = 9, -- 副本房间清理完成 (开门)
    PARTY_DUNGEON_MAP_MOVE = 10, -- 副本房间移动
    PARTY_DUNGEON_MOB_DIE = 11, -- 副本房间怪物死亡
    PARTY_DUNGEON_USER_DIE = 12, -- 副本玩家死亡
    PARTY_DUNGEON_USER_REVIVE = 13, -- 副本玩家复活
    PARTY_DUNGEON_USE_SKILL = 14, -- 副本玩家使用技能
    PARTY_DUNGEON_USE_STACKABLE = 15, -- 副本玩家使用消耗品
    PARTY_DUNGEON_GET_MAZE = 16, -- (v1) 选择地牢
    PARTY_DUNGEON_GET_ITEM = 17, -- (v1) 捡取道具
    PARTY_DUNGEON_CAN_PICK_ROLL = 18, -- (v1) 是否参与ROLL道具
    PARTY_DUNGEON_END_LOADING = 19, -- 副本即将开始
    PARTY_DUNGEON_USER_DROP_ITEM = 20, -- (v1) 副本玩家丢弃道具
    PARTY_DUNGEON_USER_GAIN_ITEM = 21, -- (v1) 副本玩家获得道具
}

_M.HookType = HookType
_M.HookGameEventType = GameEventType

---@class TradeInfo
---@field user userdata userdata @CUser
---@field money integer
---@field items DPXGame.ItemInfo[]
local _TradeInfo

---@class TradeEventParam
---@field info1 TradeInfo
---@field info2 TradeInfo
local _TradeEventParam

---@class StoreEventParam
---@field buyer userdata @CUser
---@field seller userdata @CUser
---@field item DPXGame.ItemInfo
---@field money integer
local _StoreEventParam

return _M

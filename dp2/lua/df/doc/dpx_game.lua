-- this file only for idea + EmmyLua

error("cannot require or run this file!")

-- 宿主为df_game_r时的_DPX功能

---@class DPXGame
---@field item DPXGame.item
---@field mail DPXGame.mail
---@field quest DPXGame.quest
local dpx = {}

---获得运行参数
---
---如 df_game_r cain01 start
---
---opt(0) = df_game_r
---
---opt(1) = cain01
---
---opt(2) = start
---@param idx integer @ 默认1
---@return string @nullable
function dpx.opt(idx) end
---校正地址
---@param v integer @ida addr
---@return integer @real addr
function dpx.reloc(v) end
---设置功能过滤, 根据type的不同handler也不同
---@param hookType HookType
---@param handler function
---@return void
function dpx.hook(hookType, handler) end
---创建数据包, 切记使用delete销毁!
---
---type=1时, 最前面有一个byte错误码
---@param packId integer
---@param packType integer
---@return DPXGame.PacketGuard
function dpx.new_packet(packId, packType) end
---获得PacketBuf剩余内容, 不影响pos标记
---@param buf userdata @PacketBuf*
---@return string
function dpx.get_packet_buffer(buf) end
---设置等级上限
---@param lv integer
---@return void
function dpx.set_max_level(lv) end
---绝望之塔通关后仍可继续挑战(需门票)
---@return void
function dpx.set_unlimit_towerofdespair() end
---设置物品免确认(异界装备不影响)
---@return void
function dpx.disable_item_routing() end
---设置退出副本免虚弱
---@return void
function dpx.disable_giveup_panalty() end
---解锁镇魂开门任务
---@return void
function dpx.open_timegate() end
---允许创建缔造者
---@return void
function dpx.enable_creator() end
---禁用安全机制, 解除100级及以上的限制
---@return void
function dpx.disable_security_protection() end
---开启GM功能, 注意添加管理员账号ID至数据库中
---@return void
function dpx.enable_game_master() end
---关闭新账号发送的契约邮件 (感谢蛐蛐)
---@return void
function dpx.disable_mobile_rewards() end
---解除交易限额, 已达上限的第二天生效
---@return void
function dpx.disable_trade_limit() end
---设置使用拍卖行的最低等级
---@param lv integer
---@return void
function dpx.set_auction_min_level(lv) end
---修复拍卖行消耗品上架, 设置最大总价, 建议值2E
---@param maxPrice integer
---@return void
function dpx.fix_auction_regist_item(maxPrice) end
---关闭NPC回购系统
---@return void
function dpx.disable_redeem_item() end
---禁用支援兵
---@return void
function dpx.disable_striker() end
---扩展移动瞬间药剂ID (感谢蛐蛐). 2600014/2680784/2749064
---@return void
function dpx.extend_teleport_item() end
---禁用道具掉落随机强化
---@return void
function dpx.disable_drop_random_upgrade() end
---不要设置core大小 (设置unlimit再用它)
---@return void
function dpx.skip_setrlimit() end
---游戏日志增加时间前缀
---@return void
function dpx.log_time() end
---修改装备解锁时间
---@param seconds integer
---@return void
function dpx.set_item_unlock_time(seconds) end
---修改掉落时随机赋予红字的装备最低等级 (默认55)
---此功能也会影响洗红字的等级
---@param level integer @1~125
---@return void
function dpx.set_drop_amplify_level(level) end
---异步执行sql语句
---可以 insert/delete/update
---不能 select (因为拿不到结果集)
---需要结果集请用luv.work+luasql库实现
---@param type DBType
---@param sql string
---@return void
function dpx.sqlexec(type, sql) end


---@return void
---开启自定义收包功能
function dpx.enable_custom_dispatcher() end
---@param packId integer
---@param handler THandler0|THandler1
---@param handleType integer @optional
---@return void
---@alias THandler0 fun(user:userdata, buf:userdata):integer
---@alias THandler1 fun(user:userdata, buf:string):integer
---自定义收包, 需先启用, handleType默认0
---
---若type=0则handler的第2个参数是PacketBuf指针
---
---若type=1则handler的第2个参数是转为string的二进制
function dpx.register_custom_dispatcher(packId, handler, handleType) end
---@param packId integer
---@return boolean
---取消自定义收包, 恢复原始流程
function dpx.unregister_custom_dispatcher(packId) end

---道具信息
---
---从count开始的字段, 仅在获取背包内道具信息时有效
---
---也就是说从count开始的字段可能不存在 (取决于获取方式)
---@class DPXGame.ItemInfo
---@field id integer
---@field type integer
---@field name string
---@field grade integer
---@field rarity integer
---@field attach_type AttachType
---@field usable_level integer
---@field is_stackable boolean @stackable or equipment
---@field is_avatar boolean @equipment only
---@field is_creature boolean @equipment only
---@field count integer
---@field bead integer @附魔, equipment only
---@field upgrade integer @强化等级, equipment only
---@field separate integer @锻造等级, equipment only
---@field amplify DPXGame.ItemInfoAmplify @红字信息, equipment only
local ItemInfo

--装备增幅信息
---@class DPXGame.ItemInfoAmplify
---@field type integer @红字类型, 0=none, 1~4=type
---@field identified boolean @是否已净化
local ItemInfoAmplify

---@class DPXGame.item
dpx.item = {}

---增加道具, 失败时 slot < 0
---
---若添加时装, count-1表示剩余天数(1=无期限,2=1天,3=2天,以此类推)
---@param user userdata
---@param id integer
---@param count integer @default 1
---@return integer, integer @slot, space
function dpx.item.add(user, id, count) end
---获得道具详细信息
---@param user userdata
---@param space ItemSpace
---@param slot integer
---@return DPXGame.ItemInfo @nullable
function dpx.item.info(user, space, slot) end
---删除道具
---
---注意! 不可以删除刚刚添加的时装和宠物
---@param user userdata
---@param space ItemSpace
---@param slot integer
---@param count integer @默认1
---@return boolean
function dpx.item.delete(user, space, slot, count) end
---装备继承, space = INVENTORY
---@param user userdata
---@param srcSlot integer
---@param dstSlot integer
---@param mask InheritMask
---@return boolean
function dpx.item.inherit(user, srcSlot, dstSlot, mask) end
---统计道具数量
---@param user userdata
---@param item_id integer
---@param where GlobMask @optional 默认 FLAG_EQUIP|FLAG_INVEN
---@return integer
function dpx.item.glob_count(user, item_id, where) end
---查询pvf道具.
---@param item_id @integer
---@return DPXGame.ItemInfo @nullable
function dpx.item.query_by_id(item_id) end
---查询pvf道具.
---若有重名, 则随便返回1个
---@param item_name @string
---@return DPXGame.ItemInfo @nullable
function dpx.item.query_by_name(item_name) end

---@class DPXGame.mail.item
---@field id integer @item id
---@field count integer @default 1
local mail_item

---@class DPXGame.mail
dpx.mail = {}

---发送系统邮件 (最多1个道具)
---
---若没有道具则发送一个只有文字的空邮件
---@param characNo integer @角色ID
---@param serverGroup integer @服务器组
---@param title string @标题
---@param content string @内容
---@param id integer @default 0 (no item)
---@param count integer @default 1
---@return boolean
function dpx.mail.item(characNo, serverGroup, title, content, id, count) end
---multi item
---
---发送系统邮件 (最多10个道具), 若没有道具则不会产生邮件
---@param characNo integer
---@param serverGroup integer
---@param title string
---@param content string
---@param items DPXGame.mail.item[] @nullable
---@param gold integer @nullable, 金币, 默认0
---@return void
function dpx.mail.mitem(characNo, serverGroup, title, content, items, gold) end

---任务信息
---@class DPXGame.QuestInfo
---@field id integer
---@field type QuestType
---@field min_level integer
---@field max_level integer
---@field is_cleared boolean @是否已完成
---@field is_acceptable boolean @是否可接受
local QuestInfo

---@class DPXGame.quest
dpx.quest = {}

---获得pvf中所有任务ID
---@param user userdata
---@return table @integer数组
function dpx.quest.all(user) end
---是否已接受任务
---@param user userdata
---@param questId integer
---@return boolean
function dpx.quest.has(user, questId) end
---获得任务详情
---@param user userdata
---@param questId integer
---@return DPXGame.QuestInfo @nullable
function dpx.quest.info(user, questId) end
---完成任务 (无奖励)
---@param user userdata
---@param questId integer
---@param hasExpose boolean @default false, 是否处理未导出任务
---@return boolean
function dpx.quest.clear(user, questId, hasExpose) end
---接受任务
---@param user userdata
---@param questId integer
---@param isForce boolean @是否强制 (忽略前置条件)
---@return boolean
function dpx.quest.accept(user, questId, isForce) end
---更新/同步任务列表
---@param user userdata
---@return void
function dpx.quest.update(user) end

---@class DPXGame.PacketGuard
local PacketGuard = {}

---@param v integer
---@return void
function PacketGuard:put_byte(v) end
---@param v integer
---@return void
function PacketGuard:put_short(v) end
---@param v integer
---@return void
function PacketGuard:put_dword(v) end
---@param v string
---@return void
function PacketGuard:put_binary(v) end
---@param v boolean @default true
---@return void
function PacketGuard:finalize(v) end
---@param usercptr userdata
---@return void
function PacketGuard:send(usercptr) end
---@return void
function PacketGuard:delete() end

---背包物品
---@class DPXGame.InvenItem
local InvenItem = {}

---获得物品原值指针, 用于配合ffi扩展
---@return userdata
function InvenItem:ptr() end

---获得物品ID
---@return integer
function InvenItem:get_id() end

---获得物品详情
---@return DPXGame.ItemInfo
function InvenItem:get_info() end

---强化+1
---@return void
function InvenItem:inc_upgrade() end

---获得强化数值
---@return integer
function InvenItem:get_upgrade() end

---设置强化数值
---@param v integer @1~31
---@return void
function InvenItem:set_upgrade(v) end

---是否有红字
---@return boolean
function InvenItem:has_amplify_ability() end

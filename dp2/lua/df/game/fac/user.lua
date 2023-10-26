local _M = {}
local setmetatable = setmetatable

local ffi = require("ffi")

local sym = require("df.game.symbols.core")
local store = require("df.game.mgr.store")
local pstore = require("df.game.mgr.private_store")
local disjoint = require("df.game.mgr.disjoint")

---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX
---@type DF.Game.Factory
local ufac -- upper fac

-- dp2
ffi.cdef [[
    void CUser_ResetDimensionInout(CUser* user, int idx);
    int CUser_MoveToAccCargo(CUser *user, int space, int slot);
    int CUser_ClearAchievement(CUser* user);
    int CUser_SetCharacLevel(CUser* user, int new_level);
    int CUser_AddCharacExpPercent(CUser* user, float percent);
    int CUser_CanPickItem(CUser* user, int item_id);
    int CUser_ChangeGrowType(CUser* user, int first_grow_type, int second_grow_type, int reason);
]]

-- game
ffi.cdef [[
    int CUser_GetState(CUser*);
    int CUser_GetAccId(CUser* user);
    int CUser_GetCharacNo(CUser* user);
    const char* CUser_GetCharacName(CUser* user);
    int CUser_GetCharacLevel(CUser* user);
    int CUser_GetCharacJob(CUser* user);
    int CUser_GetCharacGrowType(CUser* user);
    int CUser_GetCharacSecondGrowType(CUser* user);
    int CUser_GetFatigue(CUser*);
    int CUser_GetMaxFatigue(CUser*);
    unsigned int CUser_GetParty(CUser* user); // return CParty*
    int CUser_DisConnSig(CUser* user, int type, int is, int err);
    int CUser_CheckInBossTower(CUser*);
    int CUser_CheckInBlueMarble(CUser*);
    int CUser_IsGmMode(CUser*);
    unsigned int CUser_getCurCharacInvenR(CUser*); // return CInventory*
    int CUser_CheckItemLock(CUser*, int, int);
    unsigned int CUser_GetCurCharacExpertJob(CUser*); // return CExpertJob*
    int CUser_SendUpdateItemList(CUser*, int target, int space, int pos);
    int CUser_SendNotiPacketMessage(CUser* user, const char* str, int type);
    int CUser_SendItemSpace(CUser* user, int space);
    int CUser_SendMoneyFullReason(CUser* user, int reason, int p3, int p4);
    int CUser_IsAffectedPremium(CUser* user, int type);
    int CExpertJob_CAlchemist_IsExistContinuousEffectItem(CUser*, int);
    int CUser_SetCurCharacStamina(CUser*, int);
    int CUser_GetMoveSpace(CUser*);
    int CUser_GetCurCharacExpertJobType(CUser*);
    int CUser_GetCurCharacExpertJobExp(CUser*);
    int CUser_GetCurExpertJobLevel(CUser*, int);
    int CUser_GetCurCharacVill(CUser*);
    int CUser_GetArea(CUser*,int);
    int CUser_GetPosX(CUser*);
    int CUser_GetPosY(CUser*);
    unsigned int CUser_GetIpAddress(CUser*);
    int CUser_CheckInTrade(CUser*);
    unsigned char CUser_GetCharacSlotLimit(CUser* user);
    int CUser_SetCharacSlotLimit(CUser* user, unsigned char);
    int CUser_IncCharacSlotLimit(CUser* user, unsigned char);
    int CUser_SendCmdErrorPacket(CUser* user, int, int);
    int CUser_GetCharacCount(CUser* user);
    int CUser_GetCera(CUser*);
    int CUser_GetCeraPoint(CUser*);
    int CUser_GetWinPoint(CUser*);
    int CUser_UseWinPoint(CUser*, int, int);
    int CUser_GainWinPoint(CUser*, int, int);
]]

sym.alias('CUser_GetParty', '_ZN5CUser8GetPartyEv')
sym.alias('CUser_GetAccId', '_ZNK5CUser10get_acc_idEv')
sym.alias('CUser_GetCharacNo', '_ZNK15CUserCharacInfo14getCurCharacNoEv')
sym.alias('CUser_GetCharacName', '_ZNK15CUserCharacInfo16getCurCharacNameEv')
sym.alias("CUser_GetCharacLevel", "_ZNK15CUserCharacInfo16get_charac_levelEv")
sym.alias("CUser_GetCharacJob", "_ZNK15CUserCharacInfo14get_charac_jobEv")
sym.alias("CUser_GetCharacGrowType", "_ZNK15CUserCharacInfo20getCurCharacGrowTypeEv")
sym.alias("CUser_GetCharacSecondGrowType", "_ZNK15CUserCharacInfo24getCurCharSecondGrowTypeEv")
sym.alias("CUser_GetFatigue", "_ZNK5CUser24getCurCharacTotalFatigueEv")
sym.alias("CUser_GetMaxFatigue", "_ZNK5CUser27getCurCharacTotalMaxFatigueEv")
sym.alias("CUser_DisConnSig", "_ZN5CUser10DisConnSigE11DISCONN_SIGbi")
sym.alias("CUser_CheckInBossTower", "_ZN5CUser16checkInBossTowerEv")
sym.alias("CUser_CheckInBlueMarble", "_ZN5CUser17checkInBlueMarbleEv")
sym.alias("CUser_IsGmMode", "_ZN5CUser8isGMUserEv")
sym.alias("CUser_getCurCharacInvenR", "_ZNK15CUserCharacInfo18getCurCharacInvenREv")
sym.alias("CUser_GetState", "_ZN5CUser9get_stateEv")
sym.alias("CUser_CheckItemLock", "_ZNK5CUser13CheckItemLockEii")
sym.alias("CUser_GetCurCharacExpertJob", "_ZNK15CUserCharacInfo21GetCurCharacExpertJobEv")
sym.alias("CUser_SendUpdateItemList", "_ZN5CUser18SendUpdateItemListENS_11eSendTargetE14ENUM_ITEMSPACEi")
sym.alias("CUser_SendNotiPacketMessage", "_ZN5CUser21SendNotiPacketMessageEPKc17ENUM_MESSAGE_TYPE")
sym.alias("CUser_SendItemSpace", "_ZN5CUser14send_itemspaceEi")
sym.alias("CUser_SendMoneyFullReason", "_ZN5CUser19SendMoneyFullReasonE22ENUM_MONEY_FULL_REASONmm")
sym.alias("CUser_IsAffectedPremium", "_ZNK5CUser17isAffectedPremiumE17ENUM_PREMIUM_TYPE")
sym.alias("CExpertJob_CAlchemist_IsExistContinuousEffectItem", "_ZN10expert_job10CAlchemist27IsExistContinuousEffectItemEP5CUseri")
sym.alias("CUser_SetCurCharacStamina", "_ZN15CUserCharacInfo19setCurCharacStaminaEh")
sym.alias("CUser_GetMoveSpace", "_ZNK5CUser12getMoveSpaceEv")
sym.alias("CUser_GetCurCharacExpertJobType", "_ZNK15CUserCharacInfo25GetCurCharacExpertJobTypeEv")
sym.alias("CUser_GetCurCharacExpertJobExp", "_ZNK15CUserCharacInfo24GetCurCharacExpertJobExpEv")
sym.alias("CUser_GetCurExpertJobLevel", "_ZN5CUser20GetCurExpertJobLevelEi")
sym.alias("CUser_GetCurCharacVill", "_ZNK15CUserCharacInfo16getCurCharacVillEv")
sym.alias("CUser_GetArea", "_ZN5CUser8get_areaEb")
sym.alias("CUser_GetPosX", "_ZN5CUser8get_posXEv")
sym.alias("CUser_GetPosY", "_ZN5CUser8get_posYEv")
sym.alias("CUser_GetIpAddress", "_ZN5CUser21get_public_ip_addressEv")
sym.alias("CUser_CheckInTrade", "_ZN5CUser12CheckInTradeEv")
sym.alias("CUser_GetCharacSlotLimit", "_ZNK5CUser18getCharacSlotLimitEv")
sym.alias("CUser_SetCharacSlotLimit", "_ZN5CUser18setCharacSlotLimitEh")
sym.alias("CUser_IncCharacSlotLimit", "_ZN5CUser18incCharacSlotLimitEh")
sym.alias("CUser_SendCmdErrorPacket", "_ZN5CUser18SendCmdErrorPacketE14ENUM_CMDPACKETh")
sym.alias("CUser_GetCharacCount", "_ZN5CUser16get_charac_countEv")
sym.alias("CUser_GetCera", "_ZN5CUser7GetCeraEv")
sym.alias("CUser_GetCeraPoint", "_ZN5CUser12GetCeraPointEv")
sym.alias("CUser_GetWinPoint", "_ZN15CUserCharacInfo11GetWinPointEv")
sym.alias("CUser_UseWinPoint", "_ZN5CUser11UseWinPointEi12eWPSubReason")
sym.alias("CUser_GainWinPoint", "_ZN5CUser12gainWinPointEi12eWPAddReason")

---@class CUser
---@field cptr userdata @原始C指针
---@field fptr userdata @用于ffi的指针
local mt = {}
local mtt = {
    __index = mt
}

---账号状态
---@return integer @after login >= 3
function mt:GetState()
    return ffi.C.CUser_GetState(self.fptr)
end

---角色数量
---@return integer
function mt:GetCharacCount()
    return ffi.C.CUser_GetCharacCount(self.fptr)
end

---账号ID
---@return integer
function mt:GetAccId()
    return ffi.C.CUser_GetAccId(self.fptr)
end

---角色ID
---@return integer
function mt:GetCharacNo()
    return ffi.C.CUser_GetCharacNo(self.fptr)
end

---角色职业
---@return integer
function mt:GetCharacJob()
    return ffi.C.CUser_GetCharacJob(self.fptr)
end

---角色名称
---@return string
function mt:GetCharacName()
    local str = ffi.C.CUser_GetCharacName(self.fptr)

    return ffi.string(str)
end

---角色等级
---@return integer
function mt:GetCharacLevel()
    return ffi.C.CUser_GetCharacLevel(self.fptr)
end

---角色转职职业
---@return integer
function mt:GetCharacGrowType()
    return ffi.C.CUser_GetCharacGrowType(self.fptr)
end

---角色觉醒职业
---@return integer
function mt:GetCharacSecondGrowType()
    return ffi.C.CUser_GetCharacSecondGrowType(self.fptr)
end

---已用疲劳值
---@return integer
function mt:GetFatigue()
    return ffi.C.CUser_GetFatigue(self.fptr)
end

---最大疲劳值
---@return integer
function mt:GetMaxFatigue()
    return ffi.C.CUser_GetMaxFatigue(self.fptr)
end

---踢人
---@param src integer @渠道
---@param p2 integer
---@param p3 integer
---@return integer @错误码?
function mt:Kick(src, p2, p3)
    return ffi.C.CUser_DisConnSig(self.fptr, src or 0, p2 or 0, p3 or 0)
end

---踢人
---@param err @错误号
---@return integer
function mt:DisConn(err)
    return self:Kick(10, 1, err or 1)
end

---当前小队/副本
---@return CParty
function mt:GetParty()
    local p = ffi.C.CUser_GetParty(self.fptr)

    if p ~= 0 then
        return ufac.party(dp.int2ptr(p))
    end

    return nil
end

---是否在领主塔
---@return boolean
function mt:CheckInBossTower()
    return (ffi.C.CUser_CheckInBossTower(self.fptr) == 1)
end

---是否在龙之路
---@return boolean
function mt:CheckInBlueMarble()
    return (ffi.C.CUser_CheckInBlueMarble(self.fptr) == 1)
end

---重置异界次数
---@param idx integer @异界编号 0~5
---@return void
function mt:ResetDimensionInout(idx)
    ffi.C.CUser_ResetDimensionInout(self.fptr, idx)
end

---移动物品至账号金库
---@param space ItemSpace
---@param slot integer @格子序号
---@return boolean
function mt:MoveToAccCargo(space, slot)
    return (ffi.C.CUser_MoveToAccCargo(self.fptr, space, slot) == 0)
end

---设置角色等级
---@param new_level integer
---@return boolean
function mt:SetCharacLevel(new_level)
    return ffi.C.CUser_SetCharacLevel(self.fptr, new_level) == 0
end

---增加角色百分比经验, 最多恰好升级
---@param percent number @ 0.0 < x <= 1.0
---@return boolean
function mt:AddCharacExpPercent(percent)
    return ffi.C.CUser_AddCharacExpPercent(self.fptr, percent) == 0
end

---是否开启GM权限
---@return boolean
function mt:IsGmMode()
    return (ffi.C.CUser_IsGmMode(self.fptr) ~= 0)
end

---获得角色背包对象
---@return CInventory @nullable
function mt:GetCurCharacInvenR()
    local nptr = ffi.C.CUser_getCurCharacInvenR(self.fptr)
    if nptr ~= 0 then
        return ufac.inventory(dp.int2ptr(nptr))
    end

    return nil
end

---能够获得物品 (检测满包裹)
---@param item_id integer
---@return boolean
function mt:CanPickItem(item_id)
    return (ffi.C.CUser_CanPickItem(self.fptr, item_id) ~= 0)
end

---出售
---@param space ItemSpace
---@param slot integer @格子序号
---@param count integer @出售个数
---@return boolean
function mt:Sell(space, slot, count)
    return store.user_sell_item(self.cptr, space, slot, count)
end

---是否在私有仓库
---@return boolean
function mt:IsBusyPrivateStore()
    return pstore.IsBusyPrivateStore(self.cptr)
end

---物品是否已锁定
---@return boolean
function mt:CheckItemLock(inven, slot)
    return (ffi.C.CUser_CheckItemLock(self.fptr, inven, slot) ~= 0)
end

---副职业对象
---分解机摆摊才有值
---@return CExpertJob @nullable
function mt:GetCurCharacExpertJob()
    local p = ffi.C.CUser_GetCurCharacExpertJob(self.fptr)

    if p ~= 0 then
        return ufac.exjob(dp.int2ptr(p))
    end

    return nil
end

---分解道具
---@param space ItemSpace
---@param slot integer
---@param callee CUser @谁的分解机, 传nil表示诺顿
---@return void
function mt:Disjoint(space, slot, callee)
    disjoint.Invoke(self.cptr, slot, space, callee and callee.cptr or nil)
end

---更新格子
---@param space ItemSpace
---@param slot integer
---@return boolean
function mt:SendUpdateItemList(space, slot)
    return ffi.C.CUser_SendUpdateItemList(self.fptr, 1, space, slot) == 1
end

---发送通知
---@param str string
---@param type integer @default 0
---@return void
function mt:SendNotiPacketMessage(str, type)
    ffi.C.CUser_SendNotiPacketMessage(self.fptr, str, type or 0)
end

---更新包裹
---@param space ItemSpace
---@return void
function mt:SendItemSpace(space)
    ffi.C.CUser_SendItemSpace(self.fptr, space)
end

---@param reason integer
---@param m1 integer
---@param m2 integer
function mt:SendMoneyFullReason(reason, m1, m2)
    ffi.C.CUser_SendMoneyFullReason(self.fptr, reason, m1 or 0, m2 or 0)
end

---是否存在契约
---如黑钻 12
---@param type integer
---@return boolean
function mt:IsAffectedPremium(type)
    return ffi.C.CUser_IsAffectedPremium(self.fptr, type) == 1
end

---是否存在道具效果
---如强化秘药 2600025
---@param item_id integer
---@return boolean
function mt:IsExistContinuousEffectItem(item_id)
    return ffi.C.CExpertJob_CAlchemist_IsExistContinuousEffectItem(self.fptr, item_id) == 1
end

---设置虚弱度
---@param percent integer @百分比.整数
---@return void
function mt:SetCurCharacStamina(percent)
    ffi.C.CUser_SetCurCharacStamina(self.fptr, percent)

    local pack = dpx.new_packet(33, 0)
    pack:put_byte(percent)
    pack:finalize(true)
    pack:send(self.cptr)
    pack:delete()
end

---@return boolean
function mt:GetMoveSpace()
    return ffi.C.CUser_GetMoveSpace(self.fptr) ~= 0
end

---获得副职业类型
---@return ExpertJobType
function mt:GetCurCharacExpertJobType()
    return ffi.C.CUser_GetCurCharacExpertJobType(self.fptr)
end

---获得副职业经验
---@return integer
function mt:GetCurCharacExpertJobExp()
    return ffi.C.CUser_GetCurCharacExpertJobExp(self.fptr)
end

---获得副职业等级, 或把某个经验值转换为等级
---@return integer
function mt:GetCurExpertJobLevel(exp)
    if not exp then
        exp = self:GetCurCharacExpertJobExp()
    end
    return ffi.C.CUser_GetCurExpertJobLevel(self.fptr, exp)
end

---获得当前城镇
---@return integer
function mt:GetCurCharacVillage()
    return ffi.C.CUser_GetCurCharacVill(self.fptr)
end

---获得当前区域
---@param b boolean
---@return integer
function mt:GetArea(b)
    return ffi.C.CUser_GetArea(self.fptr, b and 1 or 0)
end

---获得当前区域位置
---@return integer, integer
function mt:GetAreaPos()
    local x = ffi.C.CUser_GetPosX(self.fptr)
    local y = ffi.C.CUser_GetPosY(self.fptr)

    return x, y
end

---获得当前城镇位置
---@return integer, integer, integer, integer @城镇, 区域, X, Y
function mt:GetLocation()
    local v = self:GetCurCharacVillage()
    local a = self:GetArea()
    local x, y = self:GetAreaPos()

    return v, a, x, y
end

---获得公网地址
---@return integer
function mt:GetIpAddress()
    return ffi.C.CUser_GetIpAddress(self.fptr)
end

---转职与觉醒
---@param first_grow_type integer @转职类型
---@param second_grow_type integer @觉醒进度, 0=未觉醒, 1=觉醒, 2=二次觉醒
---@param reason integer
---@return void
function mt:ChangeGrowType(first_grow_type, second_grow_type, reason)
    ffi.C.CUser_ChangeGrowType(self.fptr, first_grow_type, second_grow_type or 0, reason or 1)
end

---是否在交易状态
---@return boolean
function mt:CheckInTrade()
    local v = ffi.C.CUser_CheckInTrade(self.fptr)
    return (v & 0x01) ~= 0
end

---获得角色槽位限制
---@return integer
function mt:GetCharacSlotLimit()
    return ffi.C.CUser_GetCharacSlotLimit(self.fptr)
end

---@param cnt integer
---@return void
function mt:SetCharacSlotLimit(cnt)
    if cnt < 1 or cnt > 24 then
        assert(false, "bad args")
    end
    return ffi.C.CUser_SetCharacSlotLimit(self.fptr, cnt)
end

---@param cnt integer
---@return void
function mt:IncCharacSlotLimit(cnt)
    if cnt < 1 then
        assert(false, "bad args")
    end
    if self:GetCharacSlotLimit() + cnt > 24 then
        assert(false, "limit maximum")
    end
    return ffi.C.CUser_IncCharacSlotLimit(self.fptr, cnt)
end

---设置道具效果
---@param item_id integer @item id
---@param keep_time integer @seconds
---@return void
function mt:SetItemEffect(item_id, keep_time)
    local pack = dpx.new_packet(256, 0)

    pack:put_byte(1)
    pack:put_dword(item_id)
    pack:put_dword(keep_time or 3600)
    pack:finalize(true)

    pack:send(self.cptr)
    pack:delete()
end

---@param pack_id integer
---@param err_code integer
---@return void
function mt:SendCmdErrorPacket(pack_id, err_code)
    ffi.C.CUser_SendCmdErrorPacket(self.fptr, pack_id, err_code)
end

---获得点券
---@return integer
function mt:GetCera()
    return ffi.C.CUser_GetCera(self.fptr)
end

---充值点券
---@param count integer
---@return void
function mt:ChargeCera(count)
    dpx.cash.add(self.cptr, count)
end

---获得代币
---@return integer
function mt:GetCeraPoint()
    return ffi.C.CUser_GetCeraPoint(self.fptr)
end

---充值代币
---@param count integer
---@return void
function mt:ChargeCeraPoint(count)
    dpx.cash.add_point(self.cptr, count)
end

---获得胜点
---@return integer
function mt:GetWinPoint()
    return ffi.C.CUser_GetWinPoint(self.fptr)
end

---消耗胜点
---@param count integer
---@return integer 消耗后的数量
function mt:UseWinPoint(count)
    local n = ffi.C.CUser_UseWinPoint(self.fptr, count, 5)
    self:SendUpdateItemList(0, 2)
    return n
end

---增加胜点
---@param count integer
---@return void
function mt:GainWinPoint(count)
    ffi.C.CUser_GainWinPoint(self.fptr, count, 6)
    self:SendUpdateItemList(0, 2)
end

local obj_t = ffi.typeof("CUser*")
local weakCache = setmetatable({}, {__mode = "v"})

---@param ptr userdata
---@return CUser
function _M.fac(ptr)
    local obj = weakCache[ptr]
    if obj then
        return obj
    end

    local t = {
        cptr = ptr,
        fptr = ffi.cast(obj_t, ptr),
    }

    obj = setmetatable(t, mtt)
    weakCache[ptr] = obj
    return obj
end

---@param fac DF.Game.Factory
---@return void
function _M.set_ufac(fac)
    ufac = fac
end

return _M

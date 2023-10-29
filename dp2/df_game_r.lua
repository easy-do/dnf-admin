---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local game = require("df.game")
local logger = require("df.logger")
local json = require("json")
local dpPing = require("dpPing")
local dpReport = require("dpReport")

logger.info("opt: %s", dpx.opt())
-- see dp2/lua/df/doc for more information !

--------------------------------------开启 frida , frida调用dp的函数走 on_frida_call------------------------------------------------
local frida = require("df.frida")
---@param arg1 integer
---@param arg2 number
---@param arg3 string
---@return integer 返回到js的值
local function on_frida_call(arg1, arg2, arg3)
    --脚本文件位置: /dp2/lua/ext/fridaCall.lua
    local key = "ext.fridaCall"
    local fridaCall = require(key)
    fridaCall:run(arg1, arg2, arg3)
    return 0
end

frida.load("load args!", on_frida_call)
--------------------------------------开启 frida , frida调用dp的函数走 on_frida_call-------------------------------------------------


------------------------------------------------ 开启dnf-admin通信----------------------------------------------------------------------
dpPing.run()
------------------------------------------------ 开启dnf-admin通信----------------------------------------------------------------------

------------------------------------------------ 记录在线账号----------------------------------------------------------------------
local online = {}

local function onLogin(_user)
    local user = game.fac.user(_user)
    local uid = user:GetAccId()
    local characNo = user:GetCharacNo()
    local name = user:GetCharacName()
    online[uid] = true
    local reportValue = {
        uid = uid,
        name = name,
        characNo=characNo
    }
    dpReport.run('login',json.encode(reportValue))
end
dpx.hook(game.HookType.Reach_GameWord, onLogin)

local function onLogout(_user)
    local user = game.fac.user(_user)
    local uid = user:GetAccId()
    local name = user:GetCharacName()
    online[uid] = nil
    local reportValue = {
        uid = uid,
        name = name;
    }
    dpReport.run('logout',json.encode(reportValue))
end
dpx.hook(game.HookType.Leave_GameWord, onLogout)
------------------------------------------------ 记录在线账号----------------------------------------------------------------------


-------------------------------------------------玩家指令监听----------------------------------------------------------------------
local on_input = function(fnext, _user, input)
    --脚本文件位置: /dp2/lua/ext/gmInput.lua
    local key = "ext.gmInput"           --这里对应的是想要热加载的脚本内容的路径 例如 local key = "ext.gmInput"; 对应的文件位置: /dp2/lua/ext/gmInput.lua
    local gmInput = require(key)
    gmInput:run(_user, input)             --gmInput:run(_user, input) 对应的是脚本文件的run函数(写法参考gmInput.lua)，注入传参要对应上，你想传递哪些参数，这边就传那些参数，对应的脚本run函数就要接收哪些参数
end
dpx.hook(game.HookType.GmInput, on_input) -- 这是把on_input这个函数hook到游戏主线程

-------------------------------------------------玩家指令监听----------------------------------------------------------------------


-------------------------------------------------监听副本掉落事件----------------------------------------------------------------------
local drop_item = function(_party, monster_id)
    --脚本文件位置: /dp2/lua/ext/partyDropItem.lua
    local key = "ext.partyDropItem"
    local partyDropItem = require(key)
    return partyDropItem:run(_party, monster_id, online)
end
dpx.hook(game.HookType.CParty_DropItem, drop_item)
-------------------------------------------------监听副本掉落事件----------------------------------------------------------------------


-------------------------------------------------监听游戏事件----------------------------------------------------------------------
local game_event = function(fnext, type, _party, param)
    --脚本文件位置: /dp2/lua/ext/gameEvent.lua
    local key = "ext.gameEvent"
    local partyDropItem = require(key)
    partyDropItem:run(type, _party, param, online)
    return fnext()
end
dpx.hook(game.HookType.GameEvent, game_event)
-------------------------------------------------监听游戏事件----------------------------------------------------------------------

--
-------------------------------------------------监听道具消耗----------------------------------------------------------------------
local function useitem(_user, item_id)
    --脚本文件位置: /dp2/lua/ext/useItem.lua
    local key = "ext.useItem"
    local useitem = require(key)
    useitem:run(_user, item_id, online)
end
-- 消耗道具A
dpx.hook(game.HookType.UseItem1, useitem)
--消耗道具B
dpx.hook(game.HookType.UseItem2, useitem)
-------------------------------------------------监听道具消耗----------------------------------------------------------------------

-------------------------------------------------强化事件监听----------------------------------------------------------------------
-- 强化事件监听
---@param _user userdata
---@param iitem DPXGame.InvenItem
---@return boolean
local function upgrade(fnext, _user, iitem)
    --脚本文件位置: /dp2/lua/ext/upgrade.lua
    local key = "ext.upgrade"
    local upgrade = require(key)
    return upgrade:run(fnext, _user, iitem, online)
end


dpx.hook(game.HookType.Upgrade, upgrade)
-------------------------------------------------强化事件监听----------------------------------------------------------------------

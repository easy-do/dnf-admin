---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local game = require("df.game")
local logger = require("df.logger")
local dpAdminConf = require("dpAdminConf")

local _M = {}

function _M:run(fnext, _user, iitem, online)
    local user = game.fac.user(_user)
    -- 装备的强化等级
    local upgrade = iitem:get_upgrade()

    -- 先正常调用原强化函数
    local ok = fnext()

    -- 如果强化了失败了走这里进行处理 
    if not ok then
        local upgrade_success_level = dpAdminConf.getConf("upgrade_success_level");
        if upgrade <= upgrade_success_level then
            iitem:inc_upgrade()
            --ok=true 代表强化结果为成功 ok=false代表强化失败
            ok = true
        end
    end
    return ok
end

return _M

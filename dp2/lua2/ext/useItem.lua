---@type DP
local dp = _DP
---@type DPXGame
local dpx = _DPX

local world = require("df.game.mgr.world")
local game = require("df.game")
local logger = require("df.logger")
local dpAdminConf = require("dpAdminConf")


local _M = {}

-- 以下是主线任务完成券代码!
local completeTask = function(user)
    local quest = dpx.quest
    local lst = quest.all(user.cptr)
    local chr_level = user:GetCharacLevel()
    --下面{}内写要排除的任务编号即可，就不会自动完成如下编号的任务，例子是辅助装备魔法石
    local evade_lst = {}
    for i, v in ipairs(lst) do
        for j, w in ipairs(evade_lst) do
            if v == w then
                table.remove(lst, i)
            end
        end
    end
    for i, v in ipairs(lst) do
        local id = v
        local info = quest.info(user.cptr, id)
        if info then
            if not info.is_cleared and info.type == game.QuestType.epic and info.min_level <= chr_level then
                quest.clear(user.cptr, id)
            end
        end
    end
    quest.update(user.cptr)
end
-- 以上是任务完成券代码!

function _M:run(_user, item_id, online)
    local user = game.fac.user(_user)
    logger.info("道具消耗事件: %s %d",user:GetCharacName(), item_id)
    local complete_task_item_id = dpAdminConf.getConf("complete_task_item_id");
    if item_id == tonumber(complete_task_item_id) then
        completeTask(user)
    end

end

return _M

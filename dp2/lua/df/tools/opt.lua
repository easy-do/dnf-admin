--[[
sample:

local opt = require("opt")
opt.handle("//foo", function(args)
    print(args[1]) -- jkl
    print(args[2]) -- abc
    print(args[3]) -- 666
    print(args[4]) -- 999
end)
opt.proc("//foo jkl abc 666       999")

]]--

local _M = {}

local table = table
local string = string

local handlers = {}
local pre_hook = function(user, name, argv)
    return false
end

local function split(s, p)
    local t = {}
    local r = string.format("[^%s]+", p)
    string.gsub(s, r, function(w)
        table.insert(t, w)
    end)
    return t
end

---@param user CUser
---@param str string
---@return boolean
function _M.proc(user, str)
    if #str < 3 then
        return false
    end

    local argv = split(str, " ")
    local argc = #argv
    if argc < 1 then
        return false
    end

    local name  = argv[1]
    table.remove(argv, 1)
    local handler = handlers[name]
    if not handler then
        return false
    end

    if not pre_hook(user, name, argv) then
        return false
    end

    handler(user, argv)
    return true
end

---@param func fun(user, name, argv):boolean
---@return void
function _M.hook(func)
    pre_hook = func
end

---@return void
function _M.clear()
    handlers = {}
end

---@param name string
---@param func fun(user:CUser, argv:string[]):void
---@return void
function _M.handle(name, func)
    handlers[name] = func
end

return _M

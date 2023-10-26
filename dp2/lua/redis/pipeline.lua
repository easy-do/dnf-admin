local _M = {}
local luv = require("luv")
local codec = require("redis.codec")
local serpent = require("serpent")

local os = os
local type = type
local next = next
local table = table
local debug = debug
local ipairs = ipairs
local string = string
local xpcall = xpcall
local setmetatable = setmetatable

local dbgmode = false
local errfunc = print

local function msgh(err)
    return debug.traceback(err)
end

---@class redis.pipe
---@field host string
---@field port integer
---@field timer userdata
---@field sock userdata
---@field is_active boolean
---@field last_active integer
---@field recvd string
---@field reqlist redis.request[]
---@alias TRedisHandler fun(err:string, res):void
local mt = {}
local mtt = {
    __index = mt
}

function mt:open()
    if self.timer then
        return false
    end

    self.timer = luv.new_timer()
    self.timer:start(0, 1000, function()
        self:onTimer()
    end)

    if dbgmode then
        print("[REDIS] open with ", self.host, self.port)
    end

    return true
end

function mt:close()
    if not self.timer then
        return false
    end

    self:reset()
    self.timer:close()
    self.timer = nil

    if dbgmode then
        print("[REDIS] close")
    end

    return true
end

function mt:reset()
    if self.sock then
        self.sock:close()
        self.sock = nil
    end

    self.recvd = ""
    self.is_active = false

    if next(self.reqlist) then
        local reqlist = self.reqlist

        self.reqlist = {}

        for i, v in ipairs(reqlist) do
            local func = v.func

            if func then
                func("reset", nil)
            end
        end
    end

    if dbgmode then
        print("[REDIS] reset")
    end
end

function mt:onConn(err)
    if err then
        errfunc("conn err " .. err)
        self:reset()
        return
    end

    local func = function(err1, chunk)
        local ok, res = xpcall(self.onData, msgh, self, err1, chunk)
        if not ok then
            errfunc(res)
            self:reset()
        end
    end

    if self.sock:read_start(func) then
        self.is_active = true
        self.last_active = os.time()

        if dbgmode then
            print("[REDIS] active")
        end
    else
        errfunc("conn read_start err")
        self:reset()
    end
end

function mt:onData(err, chunk)
    if err or not chunk then
        if err then
            errfunc("ondata err " .. err)
        end
        self:reset()
        return
    end

    self.last_active = os.time()
    self.recvd = self.recvd .. chunk

    self:process()
end

function mt:process()
    local msg
    local npos
    local curpos = 1

    for i = 1, 1000 do
        msg, npos = codec.decode(self.recvd, curpos)
        if not npos then
            break
        end

        if dbgmode then
            if not msg then
                print("[REDIS] recv null")
            else
                print("[REDIS] recv ", serpent.block(msg))
            end
        end

        ---@type redis.request
        local req = table.remove(self.reqlist, 1)

        if not req then
            error("rsp without req!")
        end

        local func = req.func

        if func then
            local ok, res

            if type(msg) == "table" and msg.error then
                ok, res = xpcall(func, msgh, msg.error, nil)
            else
                ok, res = xpcall(func, msgh, nil, msg)
            end

            if not ok then
                errfunc(res)
            end
        end

        curpos = npos
    end

    if curpos > 1 then
        if curpos > #self.recvd then
            self.recvd = ""
        else
            self.recvd = string.sub(self.recvd, curpos)
        end
    end
end

---@param argv table
---@param func TRedisHandler
function mt:post(argv, func)
    if not self.is_active then
        return false
    end

    if self.sock:write(codec.encode(argv)) then
        if dbgmode then
            print("[REDIS] post ", serpent.block(argv))
        end

        ---@class redis.request
        local req = {
            func = func,
        }
        table.insert(self.reqlist, req)
        return true
    end

    return false
end

function mt:onTimer()
    if self.sock then
        if self.is_active then
            local curTime = os.time()

            -- heart-beat
            if curTime - self.last_active >= 60 then
                self:post({"PING"})
            end
        end

        return
    end

    self.sock = luv.new_tcp()
    self.sock:connect(self.host, self.port, function(err)
        self:onConn(err)
    end)
end

---@return redis.pipe
function _M.new(host, port)
    local obj = {
        host = host,
        port = port or 6379,

        recvd = "",
        reqlist = {},
    }

    return setmetatable(obj, mtt)
end

function _M.set_dbgmode(b)
    dbgmode = b
end

function _M.set_errfunc(func)
    errfunc = func
end

return _M

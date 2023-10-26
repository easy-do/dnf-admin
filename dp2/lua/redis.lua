---@class redis
local _M = {}
local pipeline = require("redis.pipeline")

---@type redis.pipe
local conn

---@param host string
---@param port integer
function _M.open(host, port)
    if conn then
        return false
    end

    conn = pipeline.new(host, port)
    return conn:open()
end

function _M.close()
    if conn then
        conn:close()
        conn = nil
    end

    return true
end

function _M.set_dbgmode(b)
    pipeline.set_dbgmode(b)
end

---@param func fun(err:string):void
function _M.set_errfunc(func)
    pipeline.set_errfunc(func)
end

---@param func TRedisHandler
function _M.ping(func)
    if not conn then
        return false
    end

    return conn:post({"PING"}, func)
end

---@param func TRedisHandler
---@param key string
---@return boolean
function _M.del(func, key, ...)
    if not conn then
        return false
    end

    return conn:post({"DEL", key, ...}, func)
end

---@param func TRedisHandler
function _M.rpush(func, key, data)
    if not conn then
        return false
    end

    return conn:post({"RPUSH", key, data}, func)
end

---@param func TRedisHandler
function _M.lpush(func, key, data)
    if not conn then
        return false
    end

    return conn:post({"LPUSH", key, data}, func)
end

---@param func TRedisHandler
function _M.lpop(func, key)
    if not conn then
        return false
    end

    return conn:post({"LPOP", key}, func)
end

---@param func TRedisHandler
function _M.rpop(func, key)
    if not conn then
        return false
    end

    return conn:post({"RPOP", key}, func)
end

return _M

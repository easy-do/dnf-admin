---@class DF.logger
local _M = {}
---@type DP
local dp = _DP
local log = dp.log

---@param str string
function _M.null(str, ...)

end

---@param str string
function _M.trace(str, ...)
    log.trace(str:format(...))
end

---@param str string
function _M.debug(str, ...)
    log.debug(str:format(...))
end

---@param str string
function _M.info(str, ...)
    log.info(str:format(...))
end

---@param str string
function _M.warn(str, ...)
    log.warn(str:format(...))
end

---@param str string
function _M.error(str, ...)
    log.error(str:format(...))
end

---@param str string
function _M.critical(str, ...)
    log.critical(str:format(...))
end

return _M

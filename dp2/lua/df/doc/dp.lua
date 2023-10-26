-- this file only for idea + EmmyLua

error("cannot require or run this file!")

---@class integer
local integer

---@class DP
---@field log DP.log @日志功能
---@field mem DP.mem @内存功能
local dp = {}

---生成[[min~max]]随机数
---@param min integer
---@param max integer
---@return integer
function dp.rand(min, max) end
---获得当前系统时间 (毫秒)
---@return integer
function dp.mstime() end
---把int转为指针, 若已经是ptr则直接返回
---@param v integer
---@return userdata
function dp.int2ptr(v) end
---把指针转为int, 若已经是int则直接返回
---@param v userdata
---@return integer
function dp.ptr2int(v) end
---@param nlen integer @最大长度
---@return integer[]
function dp.backtrace(nlen) end
---添加停服回调
---@alias TUnloadHandler fun():void
---@param h TUnloadHandler | "function() end"
---@return void
function dp.add_unload_handler(h) end

---@class DP.log
dp.log = {}

---@param str string
---@return void
function dp.log.trace(str) end
---@param str string
---@return void
function dp.log.debug(str) end
---@param str string
---@return void
function dp.log.info(str) end
---@param str string
---@return void
function dp.log.warn(str) end
---@param str string
---@return void
function dp.log.error(str) end
---@param str string
---@return void
function dp.log.critical(str) end

---@class DP.mem
dp.mem = {}

---根据基址和偏移读取4字节, 支持多级
---
---若需1/2字节请自行读到4字节后进行位运算
---
---NOTE: 非法读取会造成程序崩溃!
---@param base userdata|integer
---@param ... integer
---@return integer
function dp.mem.read(base, ...) end
---和read一样, 但会验证内存有效性, 代价是效率损失
---@param base userdata|integer
---@param ... integer
---@return integer
function dp.mem.readsafe(base, ...) end
--[=[
例: base = read(base)
例: [base] = read(base, 0)
例: [base + x] = read(base, x)
例: [[base + x] + y] = read(base, x, y)
例: [[[[base] + x]] + y] = read(base, 0, x, 0, y)
--]=]

---若写入数值, size=1/2/4, data=integer
---
---若写入二进制, size=字节数, data=string
---@param addr integer
---@param size integer
---@param data string|integer
---@return boolean
function dp.mem.hotfix(addr, size, data) end

---@class DP.frida
dp.frida = {}

---加载
---@param path string
---@return boolean
function dp.frida.load(path) end

---初始化
---@param args string
---@return integer
function dp.frida.main(args) end

---通讯 (调用js)
---@param arg1 integer
---@param arg2 number
---@param arg3 string
---@return integer
function dp.frida.call(arg1, arg2, arg3) end

---设置回调, 当js调用lua时触发
---@param f TFridaLuaHandler
---@return void
---@alias TFridaLuaHandler fun(arg1:integer, arg2:number, arg3:string):integer
function dp.frida.set_handler(f) end

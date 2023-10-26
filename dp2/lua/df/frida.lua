-- 本模块是对dp.frida的封装
-- 本模块负责与frida结合, 并支持dp2与frida相互调用 ( js<-->lua )

--[[
实现原理

1. libdp2.so公开了1个函数, 用于frida调用它并获得某些符号的地址
  1.1 void* dp2_frida_resolver(const char* fname); // 返回地址或nullptr

2. 导出的符号
  2.1 lua.call          int lua_call(int arg1, float arg2, const char* arg3);
                        用于js调用lua, dp2会保证lua内线程安全

  2.2 frida.main        int frida_main(lua_State* ls, const char* args);
                        用于frida初始化, 因为frida加载时会调用rpc.exports.init()
                        而执行init()时上下文处于dlopen()内, 环境受限, 所以要从dlopen()出来后再做真正的工作
                        dp2实现的frida.main没有任何功能(默认返回-1000), 它的存在就是被hook, 让frida接管具体实现

  2.3 frida.handler     int frida_handler(lua_State* ls, int arg1, float arg2, const char* arg3);
                        用于frida接收来自lua的消息(调用)
                        同样, 它没有任何功能, 让frida hook它并接管具体实现

3. 关于ls参数
  在frida_main和frida_handler中提供了ls参数, 它是来自lua调用的虚拟机指针
  这个属于高级扩展, 一般用不到, 使得frida也可以操作lua虚拟机 (比如设置lua环境)

  // 举个栗子 (js)
  // LUA_API int   (lua_gettop) (lua_State *L);
  var addr = Module.getExportByName('liblua53.so', 'lua_gettop');
  var lua_gettop = new NativeFunction(addr, 'int', ['pointer']);
  lua_gettop(ls);
--]]

--[[
工作流程

1. dp2加载
2. dp2加载frida模块, 加载frida时frida应当hook "frida.main"函数
3. dp2调用"frida.main"函数, 由于这个函数已经被frida接管, 所以是让frida初始化一些其他东西
4. 此时dp2和frida已经可以同时工作
--]]

--[[
通讯流程

上面的工作流程只是让这两个框架同时工作, 若想相互通讯, 还需要 发送和接收(调用和处理) 流程
所以一个需要相互通讯的工作流程如下
1. dp2加载
2. dp2设置lua handler
3. dp2加载frida, 此时frida应当hook "frida.main"和"frida.handler"两个函数
4. dp2调用"frida.main"函数
5. 此时dp2和frida已经可以同时工作

当通讯时 (js call lua)
1. js发起调用, firda执行"lua.call"函数
2. dp2会执行对应的lua函数(由外部设置)
3. dp2把lua的返回值传递给frida
所以这个流程是: js -> frida -> dp2 -> lua

当通讯时 (lua call js)
1. lua发起调用, dp2会执行"frida.handler"函数
2. 由于"frida.handler"已经被frida接管, 所以是执行js代码
3. frida把js的返回值传递给dp2
所以这个流程是: lua -> dp2 -> frida -> js
--]]

--[[
综上所述, 它俩的搭配始于dp2_frida_resolver函数, 然后相互暴露给对方函数, 最终协同工作
关于通讯, 框架只定义了3个参数: int/float/char*, 若需传递复杂数据, 请使用第3个参数并用json格式
当然一般情况下 不需要通讯, 各干各的事儿应该比较常见
--]]

local M = {}
---@type DP
local dp = _DP
local lfs = require("lfs")
local logger = require("df.logger")

local function defproc(arg1, arg2, arg3)
    logger.warn("call from frida, arg1 = %d, arg2 = %f, arg3 = %s", arg1, arg2, arg3)
    return -1000
end

---加载frida模块
---
---默认返回-1000即失败
---
---否则返回js中main的返回值
---@param args string @js main的参数
---@param hproc TFridaLuaHandler @当js调动lua时触发
---@return integer
function M.load(args, hproc)
    dp.frida.set_handler(hproc or defproc)

    local pwd = lfs.currentdir()

    lfs.chdir("/dp2/frida")
    assert(dp.frida.load("/dp2/frida/frida.so"))
    lfs.chdir(pwd)

    return dp.frida.main(args or "")
end

---调用js
---@param arg1 integer
---@param arg2 number
---@param arg3 string
---@return integer
function M.call(arg1, arg2, arg3)
    return dp.frida.call(arg1 or 0, arg2 or 0, arg3 or "")
end

return M

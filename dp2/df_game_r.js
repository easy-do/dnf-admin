


//加载主功能
function start() {
	console.log('==========================================================frida start function start =============================================================');
    //这里执行相关配置
	console.log('==========================================================frida start function end =============================================================');
}



//=============================================以下是dp集成frida==============================================================================================================


/*
frida 官网地址: https://frida.re/

frida提供的js api接口文档地址: https://frida.re/docs/javascript-api/

关于dp2支持frida的说明, 请参阅: /dp2/lua/df/frida.lua
*/

// 入口点
// int frida_main(lua_State* ls, const char* args);
function frida_main(ls, _args) {
	// args是lua调用时传过来的字符串
	// 建议约定lua和js通讯采用json格式
	const args = _args.readUtf8String();

	// 在这里做你需要的事情
	console.log('frida main, args = ' + args);

	return 0;
}

// 当lua调用js时触发
// int frida_handler(lua_State* ls, int arg1, float arg2, const char* arg3);
function frida_handler(ls, arg1, arg2, _arg3) {
	const arg3 = _arg3.readUtf8String();

	// 如果需要通讯, 在这里编写逻辑
	// 比如: arg1是功能号, arg3是数据内容 (建议json格式)

	// just for test
	dp2_lua_call(arg1, arg2, arg3)

	return 0;
}

// 获取dp2的符号
// void* dp2_frida_resolver(const char* fname);
var __dp2_resolver = null;
function dp2_resolver(fname) {
	return __dp2_resolver(Memory.allocUtf8String(fname));
}

// 通讯 (调用lua)
// int lua_call(int arg1, float arg2, const char* arg3);
var __dp2_lua_call = null;
function dp2_lua_call(arg1, arg2, _arg3) {
	var arg3 = null;
	if (_arg3 != null) {
		arg3 = Memory.allocUtf8String(_arg3);
	}
	return __dp2_lua_call(arg1, arg2, arg3);
}

// 准备工作
function setup() {
	//dp 安装 frida的
	var addr = Module.getExportByName('libdp2.so', 'dp2_frida_resolver');
	__dp2_resolver = new NativeFunction(addr, 'pointer', ['pointer']);

	addr = dp2_resolver('lua.call');
	__dp2_lua_call = new NativeFunction(addr, 'int', ['int', 'float', 'pointer']);

	addr = dp2_resolver('frida.main');
	Interceptor.replace(addr, new NativeCallback(frida_main, 'int', ['pointer', 'pointer']));

	addr = dp2_resolver('frida.handler');
	Interceptor.replace(addr, new NativeCallback(frida_handler, 'int', ['pointer', 'int', 'float', 'pointer']));

	Interceptor.flush();
	console.log('================================================frida setup ok ================================================================');

	// fida自己的配置
	start()

}

//延迟加载插件
function awake() {
	//Hook check_argv
	console.log('================================================ frida awake ================================================================');
	Interceptor.attach(ptr(0x829EA5A),
		{
			onEnter: function (args) { },
			onLeave: function (retval) {
				//等待check_argv函数执行结束 再加载插件
				console.log('================================================ frida setup ================================================================');
				setup();
			}
		});
}

rpc.exports = {
	init: function (stage, parameters) {
		console.log('frida init ' + stage);

		if (stage == 'early') {
			awake();
		} else {
			//热重载:  直接加载
			console.log('================================================ frida reload ================================================================');
			setup();
		}
	},
	dispose: function () {
		console.log('================================================ frida dispose ================================================================');
	}
};

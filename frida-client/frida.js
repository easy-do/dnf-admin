
//linux读本地文件
var fopen = new NativeFunction(Module.getExportByName(null, 'fopen'), 'int', ['pointer', 'pointer'], { "abi": "sysv" });
var fread = new NativeFunction(Module.getExportByName(null, 'fread'), 'int', ['pointer', 'int', 'int', 'int'], { "abi": "sysv" });
var fclose = new NativeFunction(Module.getExportByName(null, 'fclose'), 'int', ['int'], { "abi": "sysv" });
function api_read_file(path, mode, len) {
    var path_ptr = Memory.allocUtf8String(path);
    var mode_ptr = Memory.allocUtf8String(mode);
    var f = fopen(path_ptr, mode_ptr);
    if (f == 0)
        return null;
    var data = Memory.alloc(len);
    var fread_ret = fread(data, 1, len, f);
    fclose(f);
    //返回字符串
    if (mode == 'r')
        return data.readUtf8String(fread_ret);
    //返回二进制buff指针
    return data;
}

//加载本地配置文件(json格式)
var global_config = {};
function load_config(channel) {
    var data = api_read_file('/data/frida/' + channel + '/frida_conf.json', 'r', 10 * 1024 * 1024);
    global_config = JSON.parse(data);
}

//初始化加载函数
function start(channel) {
    send("start init frida ");
    load_config(channel);
    send("frida start success");
}

function debugfrida(paramJson) {
    //执行debug消息的逻辑
    send("handler frida debug message:" + paramJson);
    try {
        send("db_config:" + global_config['db_config'].account);
        var debugData = JSON.parse(paramJson.data);
        send("handler frida debug message data:" + debugData);
    } catch (error) {
        send(" json parer debug data error :" + error);
    }
}

//函数导出、参数调试
rpc.exports = {
    start: function (channel) {
        start(channel);
    },
    handlerFcMessage: function (param) {
        send(" message type :" + typeof param);
        try {
            var paramJson = JSON.parse(param);
            if (paramJson.type === 'debug') {
                debugfrida(paramJson)
            } else {
                //其他类型消息处理逻辑
                send("handler frida client message orthe:" + param);
            }
        } catch (error) {
            send(" json parse message error :" + error);
            //json格式化失败的消息在这里处理
        }
    },
};

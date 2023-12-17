import json
import frida
import sys
import asyncio
import websockets
import logging




## 全局变量，用于存储连接成功后的WebSocket对象
websocket_client = None

script = None

handler_fc_message = None

##firda消息处理
def on_message(message,data):
    if message['type'] == 'send':
        logging.info(f'prida message {message}')
        data = {
            'type':'frida',
            'channel': sys.argv[4],
            'secret': sys.argv[5],
            'data': message['payload']
        }
        asyncio.run(websocket_client.send(json.dumps(data)))

def init_frida(channel):
    sip=sys.argv[1]
    device = frida.get_device_manager().add_remote_device(sip+":27042")
    # 读取 Frida 脚本文件内容
    with open('/data/frida.js', 'r') as file:
        script_code = file.read()
        # 附加到目标进程
        pid=int(sys.argv[2])
        session = device.attach(pid)
        # 创建脚本并加载
        global script
        script = session.create_script(script_code)
        script.load()
        ##注册消息处理回调
        script.on('message',on_message)
        # 获取已注入脚本中导出的函数
        global handler_fc_message
        handler_fc_message=script.exports.handler_fc_message
        # 执行初始化函数
        start=script.exports.start
        start(channel)

async def start_websocket_client():
    for i in range(len(sys.argv)):
        print(sys.argv[i])
    wss_url = sys.argv[3]
    conn_handler = await websockets.connect(wss_url)
    global websocket_client
    websocket_client = conn_handler
    # 连接并初始化frida
    data = {
        'type':'client_auth',
        'channel': sys.argv[4],
        'data': '客户端连接鉴权',
        'secret': sys.argv[5]
    }
    await conn_handler.send(json.dumps(data))
    init_frida(sys.argv[4])
    while True:
        response = await conn_handler.recv()
        handler_fc_message(response)
        # try:
        #     json_obj = json.loads(response)
        #     handler_fc_message(json_obj)
        # except json.JSONDecodeError:
        #     # 传递原始字符串，而不是抛出异常
        #     handler_fc_message(response)

# 运行WebSocket客户端
asyncio.get_event_loop().run_until_complete(start_websocket_client())

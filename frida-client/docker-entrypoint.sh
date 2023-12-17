
# 判断frida.js文件是否初始化过
if [ ! -f "/data/frida.js" ];then
  # 拷贝版本文件到持久化目录
  cp /usr/src/app/frida.js /data/

  echo "copy frida.js success"
else
  echo "frida.js has already initialized, do nothing!"
fi

# 判断main函数是否初始化过
if [ ! -f "/data/main.py" ];then
  # 拷贝版本文件到持久化目录
  cp /usr/src/app/main.py /data/

  echo "copy main.py success"
else
  echo "main.py has already initialized, do nothing!"
fi

chmod -R 777 /data/*

# 启动主程序
python /data/main.py $SERVER_IP $PID $WS $CHANNEL $SECRET

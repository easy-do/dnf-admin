FROM python:3

WORKDIR /usr/src/app

#主程序
ADD main.py /usr/src/app/

# frida脚本
ADD frida.js /usr/src/app/

# 启动脚本
ADD docker-entrypoint.sh /

# frida-python
COPY frida-16.1.7-cp37-abi3-manylinux_2_5_x86_64.manylinux1_x86_64.whl ./
COPY requirements.txt ./
RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple --no-cache-dir -r  requirements.txt
RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple frida-16.1.7-cp37-abi3-manylinux_2_5_x86_64.manylinux1_x86_64.whl

RUN mkdir /data && chmod -R 777 /data

CMD ["/bin/bash", "/docker-entrypoint.sh"]

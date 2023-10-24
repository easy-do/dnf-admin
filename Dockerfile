FROM registry.cn-hangzhou.aliyuncs.com/gebilaoyu/openjdk:18.0.1-oraclelinux8
MAINTAINER gebilaoyu

ENV JVM_OPTS = ""
ENV PARAMS = ""
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY be/target/app.jar /app.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /app.jar $PARAMS"]

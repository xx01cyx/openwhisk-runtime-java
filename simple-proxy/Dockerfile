FROM xx01cyx/zcot-jdk:v3.3

# RUN apt-get update && apt-get install -y build-essential gdb && apt-get -y install iproute2

RUN apt-get update \
    && apt-get install -y bsdmainutils \
    && apt-get -y install iproute2 \
    && apt-get install -y build-essential gdb

COPY gson-2.9.0.jar /proxy/
COPY http-20070405.jar /proxy/
COPY SimpleSender.java /proxy/
COPY SimpleProxy.java /proxy/
RUN cd /proxy && javac -cp ".:./gson-2.9.0.jar" SimpleSender.java && javac -cp ".:./http-20070405.jar:./gson-2.9.0.jar" SimpleProxy.java

COPY prepare.sh /proxy/
RUN cd /proxy && ./prepare.sh

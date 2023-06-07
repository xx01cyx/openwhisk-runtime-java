#!/bin/bash

java -Xshare:off -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+UseAppCDS -XX:DumpLoadedClassList=sender.lst -cp ".:./gson-2.9.0.jar" SimpleSender
java -Xshare:dump -XX:-UseCompressedOops -XX:+UseAppCDS -XX:SharedClassListFile=sender.lst -XX:SharedArchiveFile=sender.jsa -Xlog:class+path=info -cp "./gson-2.9.0.jar" SimpleSender

# java -Xshare:off -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+UseAppCDS -XX:DumpLoadedClassList=proxy.lst SimpleProxy &
# HTTP_PID=$!
# sleep 2
# kill $HTTP_PID
# java -Xshare:dump -XX:-UseCompressedOops -XX:+UseAppCDS -XX:SharedClassListFile=proxy.lst -XX:SharedArchiveFile=proxy.jsa -Xlog:class+path=info SimpleProxy

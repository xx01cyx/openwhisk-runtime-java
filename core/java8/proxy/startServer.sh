#!/bin/bash

# v1.8-debug
# java -XX:-UseCompressedOops -XX:+UseAppCDS -XX:SharedArchiveFile=/javaAction/build/libs/proxy.jsa --enable-preview -jar /javaAction/build/libs/javaAction-all.jar &

# v1.9
hostip=$(ip route show | awk '/default/ {print $3}')
echo $hostip
# java -XX:-UseCompressedOops -XX:+UseAppCDS -XX:MaxDSMDepSize=0 -XX:DSMHost=$hostip -XX:DSMPort=9034 -XX:LOG2_DSM_THREAD_SPLIT=8 -XX:SharedArchiveFile=/javaAction/build/libs/proxy.jsa --enable-preview -jar /javaAction/build/libs/javaAction-all.jar &

#v1.10
java -XX:+IgnoreUnrecognizedVMOptions -XX:+UnlockExperimentalVMOptions -XX:-UseBiasedLocking -XX:-TieredCompilation -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+UseRDMA -XX:+UseAppCDS \
    -XX:MaxDSMDepSize=0 -XX:DSMHost=$hostip -XX:DSMPort=9034 -XX:ListenPort=20001 -XX:SharedArchiveFile=/javaAction/proxy.jsa -XX:RPCThreadCnt=1 --enable-preview -jar /javaAction/build/libs/javaAction-all.jar &

tail -f /dev/null

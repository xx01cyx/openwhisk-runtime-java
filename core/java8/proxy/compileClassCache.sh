#!/bin/bash
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#### Construct Class Cache with HTTP Server classes by starting the server ####
cd /javaAction/build/libs

# java "-Xshareclasses:cacheDir=/javaSharedCache/" "-Xquickstart" "-jar" "/javaAction/build/libs/javaAction-all.jar" &
# v1.2-debug v1.6-debug
# java -Xshare:off -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+UseAppCDS -XX:+PrintClassHistogramBeforeFullGC -XX:DumpLoadedClassList=/proxy/proxy.lst -jar javaAction-all.jar &
# v1.3-debug v1.4-debug v1.5-debug
# java -Xshare:off -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+UseAppCDS -XX:DumpLoadedClassList=proxy.lst -cp javaAction-all.jar org.apache.openwhisk.runtime.java.action.Proxy &
# HTTP_PID=$!
# sleep 2
# kill $HTTP_PID
java -Xshare:dump -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+UseAppCDS -XX:SharedClassListFile=/javaAction/proxy.lst -XX:SharedArchiveFile=/javaAction/proxy.jsa -Xlog:class+path=info -jar javaAction-all.jar
# java -Xshare:dump -XX:-UseCompressedOops -XX:+UseAppCDS -XX:SharedClassListFile=proxy.lst -XX:SharedArchiveFile=proxy.jsa -Xlog:class+path=info -cp javaAction-all.jar org.apache.openwhisk.runtime.java.action.Proxy

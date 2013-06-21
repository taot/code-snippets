#!/usr/bin/bash

DIR=$(dirname $0)
cd $DIR

# compile java
javac src/HelloWorld.java -d target

# generate header file
javah -classpath target -d target HelloWorld

# compile
gcc src/HelloWorld.c -shared -o target/libhelloworld.so \
  -I/usr/lib/jvm/java-7-openjdk/include \
  -I/usr/lib/jvm/java-7-openjdk/include/linux \
  -I./target

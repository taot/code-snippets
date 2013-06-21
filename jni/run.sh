#!/usr/bin/bash

DIR=$(dirname $0)
cd $DIR

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./target
java -classpath target HelloWorld

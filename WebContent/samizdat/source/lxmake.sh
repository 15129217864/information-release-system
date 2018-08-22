#!/bin/sh

export DISPLAY=:0
export SAMHOME=/home/jconfig/samizdat
export JAVA_HOME=/usr/local/jdk118
export PATH=$JAVA_HOME/bin:$PATH
export LINUX=1
export DO_JNI=1
export COMMON_DIR=files/common
export NIX_DIR=files/nix
export OUT_DIR=lxout
export JDK_INCLUDE=$JAVA_HOME/include
export STATBUF_INC=/usr/i386-glibc20-linux/include

cd $SAMHOME/source
mkdir $OUT_DIR

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -I$COMMON_DIR -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$NIX_DIR/AppUtilsNixLinux.cpp -o$OUT_DIR/AppUtilsNixLinux.o

g++ -I$JDK_INCLUDE -I$STATBUF_INC -I$JDK_INCLUDE/linux -I$COMMON_DIR -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$NIX_DIR/CNixUtils.cpp -o$OUT_DIR/CNixUtils.o

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$COMMON_DIR/Debugger.cpp -o$OUT_DIR/Debugger.o

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$COMMON_DIR/CDateBundle.cpp -o$OUT_DIR/CDateBundle.o

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$COMMON_DIR/CStringVector.cpp -o$OUT_DIR/CStringVector.o

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$COMMON_DIR/CVector.cpp -o$OUT_DIR/CVector.o

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$COMMON_DIR/JNIUtils.cpp -o$OUT_DIR/JNIUtils.o

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$COMMON_DIR/CMemory.cpp -o$OUT_DIR/CMemory.o

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$COMMON_DIR/CStrA.cpp -o$OUT_DIR/CStrA.o

g++ -I$JDK_INCLUDE -I$JDK_INCLUDE/linux -DLINUX -DDO_JNI -fhandle-exceptions -fPIC -c \
$COMMON_DIR/CDateBundle.cpp -o$OUT_DIR/CDateBundle.o

cd $OUT_DIR

gcc -shared -Wl,-soname,libjconfiglx0.so.1 -o libjconfiglx0.so.1.0 AppUtilsNixLinux.o \
 	CMemory.o CStrA.o CVector.o CStringVector.o Debugger.o JNIUtils.o CDateBundle.o CNixUtils.o

cp libjconfiglx0.so.1.0 libjconfiglx0.so


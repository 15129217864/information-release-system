#!/bin/sh

export DISPLAY=:0
export SAMHOME=/home/jconfig/samizdat
export JAVA_HOME=/System/Library/Frameworks/JavaVM.framework
export PATH=$JAVA_HOME/Commands:$PATH
export JDK_INCLUDE=$JAVA_HOME/Headers
export CARBON_INCS=/Developer/Headers/FlatCarbon

export COMMON_DIR=files/common
export MAC_DIR=files/mac
export MACJNI_DIR=files/mac/cw
export MACOSX_DIR=files/mac/osx
export SFILES_DIR=files/mac/sfiles
export STUBS_DIR=files/mac/stubs

export MORE_FILES_DIR=MoreFiles
export MORE_FILES_INCLUDES=$MORE_FILES_DIR/CHeaders
export MORE_FILES_SOURCE=$MORE_FILES_DIR/Sources

export OUT_DIR=osxout

export INCLUDES="-I$CARBON_INCS -I$JDK_INCLUDE -I$COMMON_DIR -I$MAC_DIR -I$MORE_FILES_INCLUDES -I$MORE_FILES_SOURCE -I$MACJNI_DIR"
export DEFINES="-D__osx__ -DDO_JNI"
export FLAGS="-fPIC -fpascal-strings -Wall"

cd $SAMHOME/source
mkdir $OUT_DIR

gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SAppUtils.cpp -o$OUT_DIR/SAppUtils.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SAppUtilsOSX.cpp -o$OUT_DIR/SAppUtilsOSX.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SIConfig.cpp -o$OUT_DIR/SIConfig.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SIconDrawer.cpp -o$OUT_DIR/SIconDrawer.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SFlags.cpp -o$OUT_DIR/SFlags.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SProcesses.cpp -o$OUT_DIR/SProcesses.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SAppFinder.cpp -o$OUT_DIR/SAppFinder.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SAliases.cpp -o$OUT_DIR/SAliases.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SFiles.cpp -o$OUT_DIR/SFiles.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SVolumes.cpp -o$OUT_DIR/SVolumes.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SIcons.cpp -o$OUT_DIR/SIcons.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SIterate.cpp -o$OUT_DIR/SIterate.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SMonitors.cpp -o$OUT_DIR/SMonitors.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SResFile.cpp -o$OUT_DIR/SResFile.o
gcc $INCLUDES $DEFINES $FLAGS -c $SFILES_DIR/SWebBrowser.cpp -o$OUT_DIR/SWebBrowser.o

gcc $INCLUDES -I$SFILES_DIR $DEFINES $FLAGS -c $MACOSX_DIR/OSXmain.cpp -o$OUT_DIR/OSXmain.o

gcc $INCLUDES -I$SFILES_DIR -I$MACOSX_DIR $DEFINES $FLAGS -c $STUBS_DIR/XUtils.cpp -o$OUT_DIR/XUtils.o
gcc $INCLUDES -I$SFILES_DIR $DEFINES $FLAGS -c $STUBS_DIR/IConfig.cpp -o$OUT_DIR/IConfig.o
gcc $INCLUDES -I$SFILES_DIR $DEFINES $FLAGS -c $STUBS_DIR/AppUtils.cpp -o$OUT_DIR/AppUtils.o
gcc $INCLUDES -I$SFILES_DIR $DEFINES $FLAGS -c $STUBS_DIR/AppFinder.cpp -o$OUT_DIR/AppFinder.o
gcc $INCLUDES -I$SFILES_DIR $DEFINES $FLAGS -c $STUBS_DIR/ResFile.cpp -o$OUT_DIR/ResFile.o

gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/MoreFilesExtras.cpp -o$OUT_DIR/MoreFilesExtras.o
gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/DirectoryCopy.c -o$OUT_DIR/DirectoryCopy.o
gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/FSpCompat.c -o$OUT_DIR/FSpCompat.o
gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/FileCopy.c -o$OUT_DIR/FileCopy.o
gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/FullPath.c -o$OUT_DIR/FullPath.o
gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/IterateDirectory.c -o$OUT_DIR/IterateDirectory.o
gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/MoreDesktopMgr.c -o$OUT_DIR/MoreDesktopMgr.o
gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/MoreFiles.c -o$OUT_DIR/MoreFiles.o
gcc $INCLUDES $DEFINES $FLAGS -c $MORE_FILES_SOURCE/Search.c -o$OUT_DIR/Search.o

gcc $INCLUDES $DEFINES $FLAGS -c $COMMON_DIR/CStrA.cpp -o$OUT_DIR/CStrA.o
gcc $INCLUDES -I$SFILES_DIR $DEFINES $FLAGS -c $COMMON_DIR/JNIUtils.cpp -o$OUT_DIR/JNIUtils.o
gcc $INCLUDES $DEFINES $FLAGS -c $COMMON_DIR/Debugger.cpp -o$OUT_DIR/Debugger.o
gcc $INCLUDES $DEFINES $FLAGS -c $COMMON_DIR/CDateBundle.cpp -o$OUT_DIR/CDateBundle.o
gcc $INCLUDES $DEFINES $FLAGS -c $COMMON_DIR/CStringVector.cpp -o$OUT_DIR/CStringVector.o
gcc $INCLUDES $DEFINES $FLAGS -c $COMMON_DIR/CVector.cpp -o$OUT_DIR/CVector.o
gcc $INCLUDES $DEFINES $FLAGS -c $COMMON_DIR/CMemory.cpp -o$OUT_DIR/CMemory.o

gcc $INCLUDES $DEFINES $FLAGS -c $MAC_DIR/CFilePath.cpp -o$OUT_DIR/CFilePath.o
gcc $INCLUDES $DEFINES $FLAGS -c $MAC_DIR/Debug.cpp -o$OUT_DIR/Debug.o
gcc $INCLUDES $DEFINES $FLAGS -c $MAC_DIR/getCDROMAndEjectableStatus.cpp -o$OUT_DIR/getCDROMAndEjectableStatus.o
gcc $INCLUDES $DEFINES $FLAGS -c $MAC_DIR/CFilePathOSX.cpp -o$OUT_DIR/CFilePathOSX.o
gcc $INCLUDES $DEFINES $FLAGS -c $MAC_DIR/CFilePath.cpp -o$OUT_DIR/CFilePath.o
gcc $INCLUDES $DEFINES $FLAGS -c $MAC_DIR/CUtils.cpp -o$OUT_DIR/CUtils.o
gcc $INCLUDES $DEFINES $FLAGS -c $MAC_DIR/MiniBackground.cpp -o$OUT_DIR/MiniBackground.o
gcc $INCLUDES $DEFINES $FLAGS -c $MAC_DIR/CFSpec.cpp -o$OUT_DIR/CFSpec.o

cd $OUT_DIR

gcc -framework Carbon -framework ApplicationServices -framework System -framework IOKit -L/usr/lib -bundle -o libJConfigOSX.temp *.o

nmedit -s ../exported_symbols.exp libJConfigOSX.temp -o libJConfigOSX.jnilib

cp libJConfigOSX.jnilib ../../jconfig


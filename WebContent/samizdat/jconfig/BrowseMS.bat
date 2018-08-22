set SAMDRIVE=c:
set SAMHOME=c:\samizdat
set JDKHOME=f:\jdk1.1.4

%SAMDRIVE%
cd %SAMHOME%\jconfig
set classpath=.;%SAMHOME%\common\jconfig.zip

set path=%path%;%SAMHOME%\common\
set LD_LIBRARY_PATH=%LD_LIBRARY_PATH%;%SAMHOME%\common\

jview com.tolstoy.testjc.DiskBrowser

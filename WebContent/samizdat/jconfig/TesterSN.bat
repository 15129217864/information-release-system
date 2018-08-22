set SAMDRIVE=c:
set SAMHOME=c:\samizdat
set JDKHOME=f:\jdk1.1.4

%SAMDRIVE%
cd %SAMHOME%\jconfig
set classpath=.;%SAMHOME%\common\JConfig.zip;%JDKHOME%\lib\classes.zip

set path=%path%;%SAMHOME%\common\
set LD_LIBRARY_PATH=%LD_LIBRARY_PATH%;%SAMHOME%\common\

%JDKHOME%\bin\java com.tolstoy.testjc.Tester

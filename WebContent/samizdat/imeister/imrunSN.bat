set SAMDRIVE=c:
set SAMHOME=c:\samizdat
set JDKHOME=f:\jdk1.1.4

%SAMDRIVE%
cd %SAMHOME%\imeister
set classpath=.;%SAMHOME%\common\jconfig.zip;%SAMHOME%\common\imeister.zip;%JDKHOME%\lib\classes.zip

REM add your filters to the classpath here
REM for instance, if you write a plugin called "vztv9832.zip",
REM you would add it to the classpath as follows:
REM set classpath=%classpath%;%SAMHOME%\imeister\plugins\vztv9832.zip
REM you would also add the full class name of the plugin to the
REM file "plugins.txt", which is inside the "data" folder
REM see the file tutorial.html for more information

set path=%path%;%SAMHOME%\common\
set LD_LIBRARY_PATH=%LD_LIBRARY_PATH%;%SAMHOME%\common\

rem jview com.tolstoy.imagemeister.Starter
%JDKHOME%\bin\java com.tolstoy.imagemeister.Starter

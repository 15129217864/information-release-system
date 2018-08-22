set SAMDRIVE=c:
set SAMHOME=c:\samizdat
set JDKHOME=f:\jdk1.1.4

%SAMDRIVE%
cd %SAMHOME%\imeister\SDK\example

set classpath=.;%SAMHOME%\common\JConfig.zip;%SAMHOME%\common\imeister.zip;%JDKHOME%\lib\classes.zip

%JDKHOME%\bin\javac com\valzavala\TextViewer\*.java

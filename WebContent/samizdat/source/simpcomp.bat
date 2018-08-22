@ECHO OFF

	REM    This file creates JConfig.zip in {SRCDIR}\jcout
	REM
	REM    You'll need to edit the following properties depending on
	REM    the locations where you have things stored.
	REM
	REM    See 'wininstall.html' for details.
	REM
	REM    SRCDRV and SRCDIR are the locations of the JConfig source
	REM
	REM    ZIPHOME is the location of InfoZip's zip.exe; replace
	REM    with another command line zip program if you don't have
	REM    this


set SRCDRV=c:
set SRCDIR=c:\samizdat\source
set JDK120=G:\myaps\jdk1.2
set ZIPHOME=e:\infozip
set JJIKES=f:\jikes\jikes

%SRCDRV%
cd %SRCDIR%

deltree/y jcout
mkdir jcout
set classpath=.;%JDK120%\lib\rt.jar;applestubs.zip
%JJIKES% -nowarn -d jcout -Xstdout com\jconfig\mac\*.java com\jconfig\win\*.java com\jconfig\nix\*.java com\jconfig\*.java com\tolstoy\imagemeister\*.java com\tolstoy\testjc\*.java >jvcerrors.txt

cd jcout
%ZIPHOME%\zip -0 -q -r JConfig.zip com

copy JConfig.zip f:\bingo
copy JConfig.zip c:\samizdat\common
type ..\jvcerrors.txt

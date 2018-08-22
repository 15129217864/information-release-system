REM @ECHO OFF


	REM    This file compiles the Windows DLLs
	REM
	REM    It uses the .mak files at the root of the JConfig distribution
	REM
	REM    You'll need to edit the following properties depending on
	REM    the locations where you have things stored.
	REM
	REM    See 'wininstall.html' for details.
	REM
	REM    SRCDRV and SRCDIR are the locations of the JConfig source
	REM
	REM    NMAKE is the location of the nmake application
	REM
	REM    JDKHOME is the location of Java 1.1.4
	REM
	REM    MS1HOME is the location of the MS Java SDK version 1.5
	REM
	REM    MS2HOME is the location of the MS Java SDK version 2.0
	REM
	REM    ZIPHOME is the location of InfoZip's zip.exe; replace
	REM    with another command line zip program if you don't have
	REM    this



set SRCDRV=c:
set SRCDIR=c:\samizdat\source
set NMAKE=d:\msdev\bin\NMAKE
set JDKHOME=f:\jdk1.1.4
set MS1HOME=e:\sdk-java
set MS2HOME=e:\sdk-java.20

set MSDevDir=d:\MSDEV
set PSDK=e:\psdk



%SRCDRV%
cd %SRCDIR%


	REM ** delete the log file

del dll_results.txt


	REM ** the output directories

REM deltree sna
REM deltree snw
REM deltree r1a
REM deltree r1w
REM deltree r2a
REM deltree r2w



set VcOsDir=WIN95
if "%OS%" == "Windows_NT" set VcOsDir=WINNT
if "%OS%" == "Windows_NT" set PATH=%PSDK%\bin;%PSDK%\bin\link;%MSDevDir%\BIN;%MSDevDir%\BIN\%VcOsDir%;%PATH%
if "%OS%" == "" set PATH=%PSDK%\bin;%PSDK%\bin\link;"%MSDevDir%\BIN";"%MSDevDir%\BIN\%VcOsDir%";"%PATH%"
set VcOsDir=

set INCLUDE=.;%JDKHOME%\include\;%JDKHOME%\include\win32\;
set INCLUDE=%PSDK%\include;%MSDevDir%\INCLUDE;%MSDevDir%\MFC\INCLUDE;%INCLUDE%
set LIB=%PSDK%\lib;%MSDevDir%\LIB;%MSDevDir%\MFC\LIB;%LIB%





		ECHO doing jcSNA... >>dll_results.txt

%NMAKE% /S /f "jcSNA.mak" CFG="jcSNA - Win32 Release" >>dll_results.txt

		ECHO doing jcSNW... >>dll_results.txt

%NMAKE% /S /f "jcSNW.mak" CFG="jcSNW - Win32 Release" >>dll_results.txt



set INCLUDE=%MS1HOME%\include\;%JDKHOME%\include\;%JDKHOME%\include\win32\;
set LIB=%MS1HOME%\lib\i386\
set INCLUDE=%PSDK%\include;%MSDevDir%\INCLUDE;%MSDevDir%\MFC\INCLUDE;%INCLUDE%
set LIB=%PSDK%\lib;%MSDevDir%\LIB;%MSDevDir%\MFC\LIB;%LIB%


		ECHO doing R1A... >>dll_results.txt

%NMAKE% /S /f "R1A.mak" CFG="R1A - Win32 Release" >>dll_results.txt

		ECHO doing R1W... >>dll_results.txt

%NMAKE% /S /f "R1W.mak" CFG="R1W - Win32 Release" >>dll_results.txt




set INCLUDE=%MS2HOME%\include\;%JDKHOME%\include\;%JDKHOME%\include\win32\;
set LIB=%MS2HOME%\lib\i386\
set INCLUDE=%PSDK%\include;%MSDevDir%\INCLUDE;%MSDevDir%\MFC\INCLUDE;%INCLUDE%
set LIB=%PSDK%\lib;%MSDevDir%\LIB;%MSDevDir%\MFC\LIB;%LIB%


		ECHO doing R2A... >>dll_results.txt

%NMAKE% /S /f "R2A.mak" CFG="R2A - Win32 Release" >>dll_results.txt


		ECHO doing R2W... >>dll_results.txt

%NMAKE% /S /f "R2W.mak" CFG="R2W - Win32 Release" >>dll_results.txt
:end








REM copy D:\MSDEV\projects\Config\SNA\jcnfigSN.dll C:\samizdat\common  

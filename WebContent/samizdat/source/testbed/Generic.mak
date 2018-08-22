# Microsoft Developer Studio Generated NMAKE File, Format Version 4.20
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Application" 0x0101
# TARGTYPE "Win32 (MIPS) Application" 0x0501

!IF "$(CFG)" == ""
CFG=Generic - Win32 (80x86) Debug
!MESSAGE No configuration specified.  Defaulting to Generic - Win32 (80x86)\
 Debug.
!ENDIF 

!IF "$(CFG)" != "Generic - Win32 (80x86) Release" && "$(CFG)" !=\
 "Generic - Win32 (80x86) Debug" && "$(CFG)" != "Generic - Win32 (MIPS) Debug"\
 && "$(CFG)" != "Generic - Win32 (MIPS) Release"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE on this makefile
!MESSAGE by defining the macro CFG on the command line.  For example:
!MESSAGE 
!MESSAGE NMAKE /f "Generic.mak" CFG="Generic - Win32 (80x86) Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "Generic - Win32 (80x86) Release" (based on "Win32 (x86) Application")
!MESSAGE "Generic - Win32 (80x86) Debug" (based on "Win32 (x86) Application")
!MESSAGE "Generic - Win32 (MIPS) Debug" (based on "Win32 (MIPS) Application")
!MESSAGE "Generic - Win32 (MIPS) Release" (based on "Win32 (MIPS) Application")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 
################################################################################
# Begin Project
# PROP Target_Last_Scanned "Generic - Win32 (80x86) Debug"

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

# PROP BASE Use_MFC 2
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "WinRel"
# PROP BASE Intermediate_Dir "WinRel"
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "WinRel"
# PROP Intermediate_Dir "WinRel"
OUTDIR=.\WinRel
INTDIR=.\WinRel

ALL : "$(OUTDIR)\generic.exe"

CLEAN : 
	-@erase "$(INTDIR)\CDateBundle.obj"
	-@erase "$(INTDIR)\CFileUtils.obj"
	-@erase "$(INTDIR)\CIconUtils.obj"
	-@erase "$(INTDIR)\CLinkFileUtils.obj"
	-@erase "$(INTDIR)\CMemory.obj"
	-@erase "$(INTDIR)\CRegUtils.obj"
	-@erase "$(INTDIR)\CStrA.obj"
	-@erase "$(INTDIR)\CStringVector.obj"
	-@erase "$(INTDIR)\CStrW.obj"
	-@erase "$(INTDIR)\CVector.obj"
	-@erase "$(INTDIR)\Debugger.obj"
	-@erase "$(INTDIR)\Generic.obj"
	-@erase "$(INTDIR)\Generic.res"
	-@erase "$(INTDIR)\SAliases.obj"
	-@erase "$(INTDIR)\SAppFinder.obj"
	-@erase "$(INTDIR)\SAppInfo.obj"
	-@erase "$(INTDIR)\SFileInfo.obj"
	-@erase "$(INTDIR)\SFileIterate.obj"
	-@erase "$(INTDIR)\SIconInfo.obj"
	-@erase "$(INTDIR)\SMonitors.obj"
	-@erase "$(INTDIR)\SVersionInfo.obj"
	-@erase "$(INTDIR)\SVolumes.obj"
	-@erase "$(INTDIR)\SWebBrowser.obj"
	-@erase "$(INTDIR)\utils.obj"
	-@erase "$(INTDIR)\W95trace.obj"
	-@erase "$(INTDIR)\XToolkit.obj"
	-@erase "$(OUTDIR)\generic.exe"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
# ADD BASE CPP /nologo /MD /W3 /GX /O2 /D "NDEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /FR /YX /c
# ADD CPP /nologo /W3 /GX /O2 /I "..\files\common" /I "..\files\win" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /YX /c
# SUBTRACT CPP /Fr
CPP_PROJ=/nologo /ML /W3 /GX /O2 /I "..\files\common" /I "..\files\win" /D\
 "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /Fp"$(INTDIR)/Generic.pch" /YX\
 /Fo"$(INTDIR)/" /c 
CPP_OBJS=.\WinRel/
CPP_SBRS=.\.

.c{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.c{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

MTL=mktyplib.exe
# ADD BASE MTL /nologo /D "NDEBUG" /win32
# ADD MTL /nologo /D "NDEBUG" /win32
MTL_PROJ=/nologo /D "NDEBUG" /win32 
RSC=rc.exe
# ADD BASE RSC /l 0x409 /d "NDEBUG"
# ADD RSC /l 0x409 /d "NDEBUG"
RSC_PROJ=/l 0x409 /fo"$(INTDIR)/Generic.res" /d "NDEBUG" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# SUBTRACT BASE BSC32 /Iu
# ADD BSC32 /nologo
# SUBTRACT BSC32 /Iu
BSC32_FLAGS=/nologo /o"$(OUTDIR)/Generic.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib mfc30.lib mfco30.lib mfcd30.lib /nologo /subsystem:windows /machine:I386
# ADD LINK32 version.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /machine:I386
LINK32_FLAGS=version.lib kernel32.lib user32.lib gdi32.lib winspool.lib\
 comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib\
 odbc32.lib odbccp32.lib /nologo /subsystem:windows /incremental:no\
 /pdb:"$(OUTDIR)/Generic.pdb" /machine:I386 /out:"$(OUTDIR)/Generic.exe" 
LINK32_OBJS= \
	"$(INTDIR)\CDateBundle.obj" \
	"$(INTDIR)\CFileUtils.obj" \
	"$(INTDIR)\CIconUtils.obj" \
	"$(INTDIR)\CLinkFileUtils.obj" \
	"$(INTDIR)\CMemory.obj" \
	"$(INTDIR)\CRegUtils.obj" \
	"$(INTDIR)\CStrA.obj" \
	"$(INTDIR)\CStringVector.obj" \
	"$(INTDIR)\CStrW.obj" \
	"$(INTDIR)\CVector.obj" \
	"$(INTDIR)\Debugger.obj" \
	"$(INTDIR)\Generic.obj" \
	"$(INTDIR)\Generic.res" \
	"$(INTDIR)\SAliases.obj" \
	"$(INTDIR)\SAppFinder.obj" \
	"$(INTDIR)\SAppInfo.obj" \
	"$(INTDIR)\SFileInfo.obj" \
	"$(INTDIR)\SFileIterate.obj" \
	"$(INTDIR)\SIconInfo.obj" \
	"$(INTDIR)\SMonitors.obj" \
	"$(INTDIR)\SVersionInfo.obj" \
	"$(INTDIR)\SVolumes.obj" \
	"$(INTDIR)\SWebBrowser.obj" \
	"$(INTDIR)\utils.obj" \
	"$(INTDIR)\W95trace.obj" \
	"$(INTDIR)\XToolkit.obj" \
	"..\..\..\lib\Comctl32.lib" \
	"E:\psdk\lib\WinMM.Lib"

"$(OUTDIR)\generic.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

# PROP BASE Use_MFC 2
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "WinDebug"
# PROP BASE Intermediate_Dir "WinDebug"
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "WinDebug"
# PROP Intermediate_Dir "WinDebug"
OUTDIR=.\WinDebug
INTDIR=.\WinDebug

ALL : "$(OUTDIR)\generic.exe" "$(OUTDIR)\generic.bsc"

CLEAN : 
	-@erase "$(INTDIR)\CDateBundle.obj"
	-@erase "$(INTDIR)\CDateBundle.sbr"
	-@erase "$(INTDIR)\CFileUtils.obj"
	-@erase "$(INTDIR)\CFileUtils.sbr"
	-@erase "$(INTDIR)\CIconUtils.obj"
	-@erase "$(INTDIR)\CIconUtils.sbr"
	-@erase "$(INTDIR)\CLinkFileUtils.obj"
	-@erase "$(INTDIR)\CLinkFileUtils.sbr"
	-@erase "$(INTDIR)\CMemory.obj"
	-@erase "$(INTDIR)\CMemory.sbr"
	-@erase "$(INTDIR)\CRegUtils.obj"
	-@erase "$(INTDIR)\CRegUtils.sbr"
	-@erase "$(INTDIR)\CStrA.obj"
	-@erase "$(INTDIR)\CStrA.sbr"
	-@erase "$(INTDIR)\CStringVector.obj"
	-@erase "$(INTDIR)\CStringVector.sbr"
	-@erase "$(INTDIR)\CStrW.obj"
	-@erase "$(INTDIR)\CStrW.sbr"
	-@erase "$(INTDIR)\CVector.obj"
	-@erase "$(INTDIR)\CVector.sbr"
	-@erase "$(INTDIR)\Debugger.obj"
	-@erase "$(INTDIR)\Debugger.sbr"
	-@erase "$(INTDIR)\Generic.obj"
	-@erase "$(INTDIR)\Generic.res"
	-@erase "$(INTDIR)\Generic.sbr"
	-@erase "$(INTDIR)\SAliases.obj"
	-@erase "$(INTDIR)\SAliases.sbr"
	-@erase "$(INTDIR)\SAppFinder.obj"
	-@erase "$(INTDIR)\SAppFinder.sbr"
	-@erase "$(INTDIR)\SAppInfo.obj"
	-@erase "$(INTDIR)\SAppInfo.sbr"
	-@erase "$(INTDIR)\SFileInfo.obj"
	-@erase "$(INTDIR)\SFileInfo.sbr"
	-@erase "$(INTDIR)\SFileIterate.obj"
	-@erase "$(INTDIR)\SFileIterate.sbr"
	-@erase "$(INTDIR)\SIconInfo.obj"
	-@erase "$(INTDIR)\SIconInfo.sbr"
	-@erase "$(INTDIR)\SMonitors.obj"
	-@erase "$(INTDIR)\SMonitors.sbr"
	-@erase "$(INTDIR)\SVersionInfo.obj"
	-@erase "$(INTDIR)\SVersionInfo.sbr"
	-@erase "$(INTDIR)\SVolumes.obj"
	-@erase "$(INTDIR)\SVolumes.sbr"
	-@erase "$(INTDIR)\SWebBrowser.obj"
	-@erase "$(INTDIR)\SWebBrowser.sbr"
	-@erase "$(INTDIR)\utils.obj"
	-@erase "$(INTDIR)\utils.sbr"
	-@erase "$(INTDIR)\vc40.idb"
	-@erase "$(INTDIR)\vc40.pdb"
	-@erase "$(INTDIR)\W95trace.obj"
	-@erase "$(INTDIR)\W95trace.sbr"
	-@erase "$(INTDIR)\XToolkit.obj"
	-@erase "$(INTDIR)\XToolkit.sbr"
	-@erase "$(OUTDIR)\generic.bsc"
	-@erase "$(OUTDIR)\generic.exe"
	-@erase "$(OUTDIR)\Generic.ilk"
	-@erase "$(OUTDIR)\Generic.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
# ADD BASE CPP /nologo /MD /W3 /GX /Zi /Od /D "_DEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /FR /YX /c
# ADD CPP /nologo /W3 /Gm /GX /Zi /Od /I "..\files\common" /I "..\files\win" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /FR /YX /c
CPP_PROJ=/nologo /MLd /W3 /Gm /GX /Zi /Od /I "..\files\common" /I\
 "..\files\win" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_MBCS" /FR"$(INTDIR)/"\
 /Fp"$(INTDIR)/Generic.pch" /YX /Fo"$(INTDIR)/" /Fd"$(INTDIR)/" /c 
CPP_OBJS=.\WinDebug/
CPP_SBRS=.\WinDebug/

.c{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.c{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

MTL=mktyplib.exe
# ADD BASE MTL /nologo /D "_DEBUG" /win32
# ADD MTL /nologo /D "_DEBUG" /win32
MTL_PROJ=/nologo /D "_DEBUG" /win32 
RSC=rc.exe
# ADD BASE RSC /l 0x409 /d "_DEBUG"
# ADD RSC /l 0x409 /d "_DEBUG"
RSC_PROJ=/l 0x409 /fo"$(INTDIR)/Generic.res" /d "_DEBUG" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# SUBTRACT BASE BSC32 /Iu
# ADD BSC32 /nologo
# SUBTRACT BSC32 /Iu
BSC32_FLAGS=/nologo /o"$(OUTDIR)/Generic.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\CDateBundle.sbr" \
	"$(INTDIR)\CFileUtils.sbr" \
	"$(INTDIR)\CIconUtils.sbr" \
	"$(INTDIR)\CLinkFileUtils.sbr" \
	"$(INTDIR)\CMemory.sbr" \
	"$(INTDIR)\CRegUtils.sbr" \
	"$(INTDIR)\CStrA.sbr" \
	"$(INTDIR)\CStringVector.sbr" \
	"$(INTDIR)\CStrW.sbr" \
	"$(INTDIR)\CVector.sbr" \
	"$(INTDIR)\Debugger.sbr" \
	"$(INTDIR)\Generic.sbr" \
	"$(INTDIR)\SAliases.sbr" \
	"$(INTDIR)\SAppFinder.sbr" \
	"$(INTDIR)\SAppInfo.sbr" \
	"$(INTDIR)\SFileInfo.sbr" \
	"$(INTDIR)\SFileIterate.sbr" \
	"$(INTDIR)\SIconInfo.sbr" \
	"$(INTDIR)\SMonitors.sbr" \
	"$(INTDIR)\SVersionInfo.sbr" \
	"$(INTDIR)\SVolumes.sbr" \
	"$(INTDIR)\SWebBrowser.sbr" \
	"$(INTDIR)\utils.sbr" \
	"$(INTDIR)\W95trace.sbr" \
	"$(INTDIR)\XToolkit.sbr"

"$(OUTDIR)\generic.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
# ADD BASE LINK32 user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib mfc30d.lib mfco30d.lib mfcd30d.lib /nologo /subsystem:windows /debug /machine:I386
# ADD LINK32 version.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /debug /machine:I386
LINK32_FLAGS=version.lib kernel32.lib user32.lib gdi32.lib winspool.lib\
 comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib\
 odbc32.lib odbccp32.lib /nologo /subsystem:windows /incremental:yes\
 /pdb:"$(OUTDIR)/Generic.pdb" /debug /machine:I386 /out:"$(OUTDIR)/Generic.exe" 
LINK32_OBJS= \
	"$(INTDIR)\CDateBundle.obj" \
	"$(INTDIR)\CFileUtils.obj" \
	"$(INTDIR)\CIconUtils.obj" \
	"$(INTDIR)\CLinkFileUtils.obj" \
	"$(INTDIR)\CMemory.obj" \
	"$(INTDIR)\CRegUtils.obj" \
	"$(INTDIR)\CStrA.obj" \
	"$(INTDIR)\CStringVector.obj" \
	"$(INTDIR)\CStrW.obj" \
	"$(INTDIR)\CVector.obj" \
	"$(INTDIR)\Debugger.obj" \
	"$(INTDIR)\Generic.obj" \
	"$(INTDIR)\Generic.res" \
	"$(INTDIR)\SAliases.obj" \
	"$(INTDIR)\SAppFinder.obj" \
	"$(INTDIR)\SAppInfo.obj" \
	"$(INTDIR)\SFileInfo.obj" \
	"$(INTDIR)\SFileIterate.obj" \
	"$(INTDIR)\SIconInfo.obj" \
	"$(INTDIR)\SMonitors.obj" \
	"$(INTDIR)\SVersionInfo.obj" \
	"$(INTDIR)\SVolumes.obj" \
	"$(INTDIR)\SWebBrowser.obj" \
	"$(INTDIR)\utils.obj" \
	"$(INTDIR)\W95trace.obj" \
	"$(INTDIR)\XToolkit.obj" \
	"..\..\..\lib\Comctl32.lib" \
	"E:\psdk\lib\WinMM.Lib"

"$(OUTDIR)\generic.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

# PROP BASE Use_MFC 2
# PROP BASE Use_Debug_Libraries 1
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "WinDebug"
# PROP Intermediate_Dir "WinDebug"
OUTDIR=.\WinDebug
INTDIR=.\WinDebug

ALL :                    $(OUTDIR)/generic.exe $(OUTDIR)/generic.bsc

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

MTL=MkTypLib.exe
# ADD BASE MTL /nologo /D "_DEBUG" /win32
# ADD MTL /nologo /D "_DEBUG" /win32
MTL_PROJ=/nologo /D "_DEBUG" /win32  
CPP=cl.exe
# ADD BASE CPP /nologo /MD /Gt0 /QMOb2000 /W3 /GX /Zi /YX /Od /D "_DEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /FR /c
# ADD CPP /nologo /Gt0 /QMOb2000 /W3 /GX /Zi /YX /Od /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "WIN32" /FR /c
CPP_PROJ=/nologo /Gt0 /QMOb2000 /W3 /GX /Zi /YX /Od /D "_DEBUG" /D "_WINDOWS"\
 /D "_MBCS" /D "WIN32" /FR$(INTDIR)/ /Fp$(OUTDIR)/"generic.pch" /Fo$(INTDIR)/\
 /Fd$(OUTDIR)/"generic.pdb" /c 
CPP_OBJS=.\WinDebug/

.c{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

RSC=rc.exe
# ADD BASE RSC /l 0x409 /d "_DEBUG"
# ADD RSC /l 0x409 /d "_DEBUG"
RSC_PROJ=/l 0x409 /fo$(INTDIR)/"generic.res" /d "_DEBUG" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# SUBTRACT BASE BSC32 /Iu
# ADD BSC32 /nologo
# SUBTRACT BSC32 /Iu
BSC32_FLAGS=/nologo /o$(OUTDIR)/"generic.bsc" 
BSC32_SBRS= \
	$(INTDIR)/generic.sbr

$(OUTDIR)/generic.bsc : $(OUTDIR)  $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
# ADD BASE LINK32 user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib odbc32.lib mfc30d.lib mfco30d.lib mfcd30d.lib oleaut32.lib uuid.lib /NOLOGO /SUBSYSTEM:windows /DEBUG /MACHINE:MIPS
# ADD LINK32 version.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib /NOLOGO /SUBSYSTEM:windows /DEBUG /MACHINE:MIPS
LINK32_FLAGS=version.lib kernel32.lib user32.lib gdi32.lib winspool.lib\
 comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib /NOLOGO\
 /SUBSYSTEM:windows /PDB:$(OUTDIR)/"generic.pdb" /DEBUG /MACHINE:MIPS\
 /OUT:$(OUTDIR)/"generic.exe" 
DEF_FILE=
LINK32_OBJS= \
	$(INTDIR)/generic.res \
	$(INTDIR)/generic.obj

$(OUTDIR)/generic.exe : $(OUTDIR)  $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

# PROP BASE Use_MFC 2
# PROP BASE Use_Debug_Libraries 0
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "WinRel"
# PROP Intermediate_Dir "WinRel"
OUTDIR=.\WinRel
INTDIR=.\WinRel

ALL :                    $(OUTDIR)/generic.exe $(OUTDIR)/generic.bsc

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

MTL=MkTypLib.exe
# ADD BASE MTL /nologo /D "NDEBUG" /win32
# ADD MTL /nologo /D "NDEBUG" /win32
MTL_PROJ=/nologo /D "NDEBUG" /win32 
CPP=cl.exe
# ADD BASE CPP /nologo /MD /Gt0 /QMOb2000 /W3 /GX /YX /O2 /D "NDEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /FR /c
# ADD CPP /nologo /Gt0 /QMOb2000 /W3 /GX /YX /O2 /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "WIN32" /FR /c
CPP_PROJ=/nologo /Gt0 /QMOb2000 /W3 /GX /YX /O2 /D "NDEBUG" /D "_WINDOWS" /D\
 "_MBCS" /D "WIN32" /FR$(INTDIR)/ /Fp$(OUTDIR)/"generic.pch" /Fo$(INTDIR)/ /c 
CPP_OBJS=.\WinRel/

.c{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

RSC=rc.exe
# ADD BASE RSC /l 0x409 /d "NDEBUG"
# ADD RSC /l 0x409 /d "NDEBUG"
RSC_PROJ=/l 0x409 /fo$(INTDIR)/"generic.res" /d "NDEBUG" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# SUBTRACT BASE BSC32 /Iu
# ADD BSC32 /nologo
# SUBTRACT BSC32 /Iu
BSC32_FLAGS=/nologo /o$(OUTDIR)/"generic.bsc" 
BSC32_SBRS= \
	$(INTDIR)/generic.sbr

$(OUTDIR)/generic.bsc : $(OUTDIR)  $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
# ADD BASE LINK32 user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib odbc32.lib mfc30.lib mfco30.lib mfcd30.lib oleaut32.lib uuid.lib /NOLOGO /SUBSYSTEM:windows /MACHINE:MIPS
# ADD LINK32 version.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib /NOLOGO /SUBSYSTEM:windows /MACHINE:MIPS
LINK32_FLAGS=version.lib kernel32.lib user32.lib gdi32.lib winspool.lib\
 comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib /NOLOGO\
 /SUBSYSTEM:windows /PDB:$(OUTDIR)/"generic.pdb" /MACHINE:MIPS\
 /OUT:$(OUTDIR)/"generic.exe" 
DEF_FILE=
LINK32_OBJS= \
	$(INTDIR)/generic.res \
	$(INTDIR)/generic.obj

$(OUTDIR)/generic.exe : $(OUTDIR)  $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ENDIF 

################################################################################
# Begin Target

# Name "Generic - Win32 (80x86) Release"
# Name "Generic - Win32 (80x86) Debug"
# Name "Generic - Win32 (MIPS) Debug"
# Name "Generic - Win32 (MIPS) Release"

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\common\CDateBundle.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CDATE=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CDateBundle.obj" : $(SOURCE) $(DEP_CPP_CDATE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CDATE=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CDateBundle.obj" : $(SOURCE) $(DEP_CPP_CDATE) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CDateBundle.sbr" : $(SOURCE) $(DEP_CPP_CDATE) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\common\CMemory.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CMEMO=\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\errcodes.h"\
	

"$(INTDIR)\CMemory.obj" : $(SOURCE) $(DEP_CPP_CMEMO) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CMEMO=\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\errcodes.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CMemory.obj" : $(SOURCE) $(DEP_CPP_CMEMO) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CMemory.sbr" : $(SOURCE) $(DEP_CPP_CMEMO) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\common\CStrA.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CSTRA=\
	"..\files\common\AppData.h"\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CStrA.obj" : $(SOURCE) $(DEP_CPP_CSTRA) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CSTRA=\
	"..\files\common\AppData.h"\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CStrA.obj" : $(SOURCE) $(DEP_CPP_CSTRA) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CStrA.sbr" : $(SOURCE) $(DEP_CPP_CSTRA) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\common\CStringVector.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CSTRI=\
	"..\files\common\AppData.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CStringVector.obj" : $(SOURCE) $(DEP_CPP_CSTRI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CSTRI=\
	"..\files\common\AppData.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CStringVector.obj" : $(SOURCE) $(DEP_CPP_CSTRI) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CStringVector.sbr" : $(SOURCE) $(DEP_CPP_CSTRI) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\common\CStrW.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CSTRW=\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CStrW.obj" : $(SOURCE) $(DEP_CPP_CSTRW) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CSTRW=\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CStrW.obj" : $(SOURCE) $(DEP_CPP_CSTRW) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CStrW.sbr" : $(SOURCE) $(DEP_CPP_CSTRW) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\common\CVector.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CVECT=\
	"..\files\common\AppData.h"\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CVector.obj" : $(SOURCE) $(DEP_CPP_CVECT) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CVECT=\
	"..\files\common\AppData.h"\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CVector.obj" : $(SOURCE) $(DEP_CPP_CVECT) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CVector.sbr" : $(SOURCE) $(DEP_CPP_CVECT) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\common\Debugger.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_DEBUG=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\unicodeOn95.h"\
	"..\files\win\w95trace.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\Debugger.obj" : $(SOURCE) $(DEP_CPP_DEBUG) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_DEBUG=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\unicodeOn95.h"\
	"..\files\win\w95trace.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\Debugger.obj" : $(SOURCE) $(DEP_CPP_DEBUG) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\Debugger.sbr" : $(SOURCE) $(DEP_CPP_DEBUG) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\Generic.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_GENER=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\SAppFinder.h"\
	"..\files\win\SFileInfo.h"\
	"..\files\win\w95trace.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\Generic.obj" : $(SOURCE) $(DEP_CPP_GENER) "$(INTDIR)"


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_GENER=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\SAppFinder.h"\
	"..\files\win\SFileInfo.h"\
	"..\files\win\w95trace.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\Generic.obj" : $(SOURCE) $(DEP_CPP_GENER) "$(INTDIR)"

"$(INTDIR)\Generic.sbr" : $(SOURCE) $(DEP_CPP_GENER) "$(INTDIR)"


!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\W95trace.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_W95TR=\
	"..\files\win\w95trace.h"\
	

"$(INTDIR)\W95trace.obj" : $(SOURCE) $(DEP_CPP_W95TR) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_W95TR=\
	"..\files\win\w95trace.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\W95trace.obj" : $(SOURCE) $(DEP_CPP_W95TR) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\W95trace.sbr" : $(SOURCE) $(DEP_CPP_W95TR) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\XToolkit.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_XTOOL=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\unicodeOn95.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	
NODEP_CPP_XTOOL=\
	"..\files\win\Debugger.h"\
	

"$(INTDIR)\XToolkit.obj" : $(SOURCE) $(DEP_CPP_XTOOL) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_XTOOL=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\unicodeOn95.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	
NODEP_CPP_XTOOL=\
	"..\files\win\Debugger.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\XToolkit.obj" : $(SOURCE) $(DEP_CPP_XTOOL) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\XToolkit.sbr" : $(SOURCE) $(DEP_CPP_XTOOL) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\Generic.rc

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_RSC_GENERI=\
	".\cursor1.cur"\
	".\Generic.ico"\
	

"$(INTDIR)\Generic.res" : $(SOURCE) $(DEP_RSC_GENERI) "$(INTDIR)"
   $(RSC) $(RSC_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_RSC_GENERI=\
	".\cursor1.cur"\
	".\Generic.ico"\
	

"$(INTDIR)\Generic.res" : $(SOURCE) $(DEP_RSC_GENERI) "$(INTDIR)"
   $(RSC) $(RSC_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\CFileUtils.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CFILE=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CFileUtils.obj" : $(SOURCE) $(DEP_CPP_CFILE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CFILE=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CFileUtils.obj" : $(SOURCE) $(DEP_CPP_CFILE) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CFileUtils.sbr" : $(SOURCE) $(DEP_CPP_CFILE) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\CIconUtils.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CICON=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CIconUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CIconUtils.obj" : $(SOURCE) $(DEP_CPP_CICON) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CICON=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CIconUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CIconUtils.obj" : $(SOURCE) $(DEP_CPP_CICON) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CIconUtils.sbr" : $(SOURCE) $(DEP_CPP_CICON) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\CLinkFileUtils.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CLINK=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CLinkFileUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CLinkFileUtils.obj" : $(SOURCE) $(DEP_CPP_CLINK) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CLINK=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CLinkFileUtils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CLinkFileUtils.obj" : $(SOURCE) $(DEP_CPP_CLINK) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CLinkFileUtils.sbr" : $(SOURCE) $(DEP_CPP_CLINK) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\CRegUtils.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_CREGU=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CRegUtils.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\CRegUtils.obj" : $(SOURCE) $(DEP_CPP_CREGU) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_CREGU=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CRegUtils.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\CRegUtils.obj" : $(SOURCE) $(DEP_CPP_CREGU) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CRegUtils.sbr" : $(SOURCE) $(DEP_CPP_CREGU) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SAliases.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SALIA=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CLinkFileUtils.h"\
	"..\files\win\SAliases.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SAliases.obj" : $(SOURCE) $(DEP_CPP_SALIA) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SALIA=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CLinkFileUtils.h"\
	"..\files\win\SAliases.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SAliases.obj" : $(SOURCE) $(DEP_CPP_SALIA) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SAliases.sbr" : $(SOURCE) $(DEP_CPP_SALIA) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SAppInfo.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SAPPI=\
	"..\files\common\AppData.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\CRegUtils.h"\
	"..\files\win\SAppInfo.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SAppInfo.obj" : $(SOURCE) $(DEP_CPP_SAPPI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SAPPI=\
	"..\files\common\AppData.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\CRegUtils.h"\
	"..\files\win\SAppInfo.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SAppInfo.obj" : $(SOURCE) $(DEP_CPP_SAPPI) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SAppInfo.sbr" : $(SOURCE) $(DEP_CPP_SAPPI) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SFileInfo.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SFILE=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\SFileInfo.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SFileInfo.obj" : $(SOURCE) $(DEP_CPP_SFILE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SFILE=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\SFileInfo.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SFileInfo.obj" : $(SOURCE) $(DEP_CPP_SFILE) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SFileInfo.sbr" : $(SOURCE) $(DEP_CPP_SFILE) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SFileIterate.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SFILEI=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\SFileIterate.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SFileIterate.obj" : $(SOURCE) $(DEP_CPP_SFILEI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SFILEI=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\SFileIterate.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SFileIterate.obj" : $(SOURCE) $(DEP_CPP_SFILEI) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SFileIterate.sbr" : $(SOURCE) $(DEP_CPP_SFILEI) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SIconInfo.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SICON=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CIconUtils.h"\
	"..\files\win\SIconInfo.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SIconInfo.obj" : $(SOURCE) $(DEP_CPP_SICON) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SICON=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\CIconUtils.h"\
	"..\files\win\SIconInfo.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SIconInfo.obj" : $(SOURCE) $(DEP_CPP_SICON) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SIconInfo.sbr" : $(SOURCE) $(DEP_CPP_SICON) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SMonitors.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SMONI=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\SMonitors.h"\
	"E:\psdk\include\multimon.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SMonitors.obj" : $(SOURCE) $(DEP_CPP_SMONI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SMONI=\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\SMonitors.h"\
	"E:\psdk\include\multimon.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SMonitors.obj" : $(SOURCE) $(DEP_CPP_SMONI) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SMonitors.sbr" : $(SOURCE) $(DEP_CPP_SMONI) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SVersionInfo.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SVERS=\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\SVersionInfo.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SVersionInfo.obj" : $(SOURCE) $(DEP_CPP_SVERS) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SVERS=\
	"..\files\common\CMemory.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\win\SVersionInfo.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SVersionInfo.obj" : $(SOURCE) $(DEP_CPP_SVERS) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SVersionInfo.sbr" : $(SOURCE) $(DEP_CPP_SVERS) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SVolumes.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SVOLU=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\SVolumes.h"\
	"..\files\win\utils.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SVolumes.obj" : $(SOURCE) $(DEP_CPP_SVOLU) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SVOLU=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\SVolumes.h"\
	"..\files\win\utils.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SVolumes.obj" : $(SOURCE) $(DEP_CPP_SVOLU) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SVolumes.sbr" : $(SOURCE) $(DEP_CPP_SVOLU) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SWebBrowser.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SWEBB=\
	"..\files\common\AppData.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CRegUtils.h"\
	"..\files\win\SAppFinder.h"\
	"..\files\win\SWebBrowser.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\SWebBrowser.obj" : $(SOURCE) $(DEP_CPP_SWEBB) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SWEBB=\
	"..\files\common\AppData.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\Debugger.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CRegUtils.h"\
	"..\files\win\SAppFinder.h"\
	"..\files\win\SWebBrowser.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SWebBrowser.obj" : $(SOURCE) $(DEP_CPP_SWEBB) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SWebBrowser.sbr" : $(SOURCE) $(DEP_CPP_SWEBB) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\utils.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_UTILS=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\SVolumes.h"\
	"..\files\win\utils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

"$(INTDIR)\utils.obj" : $(SOURCE) $(DEP_CPP_UTILS) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_UTILS=\
	"..\files\common\AppData.h"\
	"..\files\common\CDateBundle.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\SVolumes.h"\
	"..\files\win\utils.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\utils.obj" : $(SOURCE) $(DEP_CPP_UTILS) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\utils.sbr" : $(SOURCE) $(DEP_CPP_UTILS) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\lib\Comctl32.lib

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\projects\Config\files\win\SAppFinder.cpp

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

DEP_CPP_SAPPF=\
	"..\files\common\AppData.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\CRegUtils.h"\
	"..\files\win\SAppFinder.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	
NODEP_CPP_SAPPF=\
	"..\files\win\Debugger.h"\
	

"$(INTDIR)\SAppFinder.obj" : $(SOURCE) $(DEP_CPP_SAPPF) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

DEP_CPP_SAPPF=\
	"..\files\common\AppData.h"\
	"..\files\common\comdefs.h"\
	"..\files\common\CStrA.h"\
	"..\files\common\CString.h"\
	"..\files\common\CStringVector.h"\
	"..\files\common\CStrW.h"\
	"..\files\common\CVector.h"\
	"..\files\common\errcodes.h"\
	"..\files\common\jmacros.h"\
	"..\files\common\jni_macros.h"\
	"..\files\common\JNIUtils.h"\
	"..\files\common\jri_macros.h"\
	"..\files\common\JRIUtils.h"\
	"..\files\common\rni_macros.h"\
	"..\files\common\RNIUtils.h"\
	"..\files\win\CFileUtils.h"\
	"..\files\win\CRegUtils.h"\
	"..\files\win\SAppFinder.h"\
	"..\files\win\XToolkit.h"\
	"F:\jdk1.1.4\include\config.h"\
	"F:\jdk1.1.4\include\interpreter.h"\
	"F:\jdk1.1.4\include\java_lang_String.h"\
	"F:\jdk1.1.4\include\javaString.h"\
	"F:\jdk1.1.4\include\jcov.h"\
	"F:\jdk1.1.4\include\oobj.h"\
	"F:\jdk1.1.4\include\signature.h"\
	"F:\jdk1.1.4\include\typecodes.h"\
	"F:\jdk1.1.4\include\typedefs.h"\
	{$(INCLUDE)}"\debug.h"\
	{$(INCLUDE)}"\jni.h"\
	{$(INCLUDE)}"\jni_md.h"\
	{$(INCLUDE)}"\native.h"\
	{$(INCLUDE)}"\oobj_md.h"\
	{$(INCLUDE)}"\typedefs_md.h"\
	
NODEP_CPP_SAPPF=\
	"..\files\win\Debugger.h"\
	

BuildCmds= \
	$(CPP) $(CPP_PROJ) $(SOURCE) \
	

"$(INTDIR)\SAppFinder.obj" : $(SOURCE) $(DEP_CPP_SAPPF) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\SAppFinder.sbr" : $(SOURCE) $(DEP_CPP_SAPPF) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=E:\psdk\lib\WinMM.Lib

!IF  "$(CFG)" == "Generic - Win32 (80x86) Release"

!ELSEIF  "$(CFG)" == "Generic - Win32 (80x86) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Debug"

!ELSEIF  "$(CFG)" == "Generic - Win32 (MIPS) Release"

!ENDIF 

# End Source File
# End Target
# End Project
################################################################################

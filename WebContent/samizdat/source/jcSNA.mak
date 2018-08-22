# Microsoft Developer Studio Generated NMAKE File, Format Version 4.20
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

!IF "$(CFG)" == ""
CFG=jcSNA - Win32 Debug
!MESSAGE No configuration specified.  Defaulting to jcSNA - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "jcSNA - Win32 Release" && "$(CFG)" != "jcSNA - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE on this makefile
!MESSAGE by defining the macro CFG on the command line.  For example:
!MESSAGE 
!MESSAGE NMAKE /f "jcSNA.mak" CFG="jcSNA - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "jcSNA - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "jcSNA - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
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
# PROP Target_Last_Scanned "jcSNA - Win32 Debug"
MTL=mktyplib.exe
CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "jcSNA - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "SNA"
# PROP Intermediate_Dir "SNA"
# PROP Target_Dir ""
OUTDIR=.\SNA
INTDIR=.\SNA

ALL : "$(OUTDIR)\jcnfigSN.dll"

CLEAN : 
	-@erase "$(INTDIR)\AppUtilsMSVM.obj"
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
	-@erase "$(INTDIR)\CWinProcess95.obj"
	-@erase "$(INTDIR)\Debugger.obj"
	-@erase "$(INTDIR)\entry.obj"
	-@erase "$(INTDIR)\JNIUtils.obj"
	-@erase "$(INTDIR)\SAliases.obj"
	-@erase "$(INTDIR)\SAppFinder.obj"
	-@erase "$(INTDIR)\SAppInfo.obj"
	-@erase "$(INTDIR)\Script1.res"
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
	-@erase "$(OUTDIR)\jcnfigSN.dll"
	-@erase "$(OUTDIR)\jcnfigSN.exp"
	-@erase "$(OUTDIR)\jcnfigSN.lib"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /MT /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /YX /c
# ADD CPP /nologo /MT /W3 /GX /O2 /I "files\common" /I "files\win" /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "DO_SEH" /D "DO_JNI" /YX /c
CPP_PROJ=/nologo /MT /W3 /GX /O2 /I "files\common" /I "files\win" /D "NDEBUG"\
 /D "WIN32" /D "_WINDOWS" /D "DO_SEH" /D "DO_JNI" /Fp"$(INTDIR)/jcSNA.pch" /YX\
 /Fo"$(INTDIR)/" /c 
CPP_OBJS=.\SNA/
CPP_SBRS=.\.
# ADD BASE MTL /nologo /D "NDEBUG" /win32
# ADD MTL /nologo /D "NDEBUG" /win32
MTL_PROJ=/nologo /D "NDEBUG" /win32 
# ADD BASE RSC /l 0x409 /d "NDEBUG"
# ADD RSC /l 0x409 /d "NDEBUG"
RSC_PROJ=/l 0x409 /fo"$(INTDIR)/Script1.res" /d "NDEBUG" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
BSC32_FLAGS=/nologo /o"$(OUTDIR)/jcSNA.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /dll /machine:I386
# ADD LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /dll /machine:I386 /out:"SNA/jcnfigSN.dll"
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib\
 advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib\
 odbccp32.lib /nologo /subsystem:windows /dll /incremental:no\
 /pdb:"$(OUTDIR)/jcnfigSN.pdb" /machine:I386 /out:"$(OUTDIR)/jcnfigSN.dll"\
 /implib:"$(OUTDIR)/jcnfigSN.lib" 
LINK32_OBJS= \
	"$(INTDIR)\AppUtilsMSVM.obj" \
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
	"$(INTDIR)\CWinProcess95.obj" \
	"$(INTDIR)\Debugger.obj" \
	"$(INTDIR)\entry.obj" \
	"$(INTDIR)\JNIUtils.obj" \
	"$(INTDIR)\SAliases.obj" \
	"$(INTDIR)\SAppFinder.obj" \
	"$(INTDIR)\SAppInfo.obj" \
	"$(INTDIR)\Script1.res" \
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
	"$(PSDK)\lib\Comctl32.lib" \
	"$(PSDK)\lib\Version.lib"

"$(OUTDIR)\jcnfigSN.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "jcSNA - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Target_Dir ""
OUTDIR=.\Debug
INTDIR=.\Debug

ALL : "$(OUTDIR)\jcnfigSN.dll"

CLEAN : 
	-@erase "$(INTDIR)\AppUtilsMSVM.obj"
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
	-@erase "$(INTDIR)\CWinProcess95.obj"
	-@erase "$(INTDIR)\Debugger.obj"
	-@erase "$(INTDIR)\entry.obj"
	-@erase "$(INTDIR)\JNIUtils.obj"
	-@erase "$(INTDIR)\SAliases.obj"
	-@erase "$(INTDIR)\SAppFinder.obj"
	-@erase "$(INTDIR)\SAppInfo.obj"
	-@erase "$(INTDIR)\Script1.res"
	-@erase "$(INTDIR)\SFileInfo.obj"
	-@erase "$(INTDIR)\SFileIterate.obj"
	-@erase "$(INTDIR)\SIconInfo.obj"
	-@erase "$(INTDIR)\SMonitors.obj"
	-@erase "$(INTDIR)\SVersionInfo.obj"
	-@erase "$(INTDIR)\SVolumes.obj"
	-@erase "$(INTDIR)\SWebBrowser.obj"
	-@erase "$(INTDIR)\utils.obj"
	-@erase "$(INTDIR)\vc40.idb"
	-@erase "$(INTDIR)\vc40.pdb"
	-@erase "$(INTDIR)\W95trace.obj"
	-@erase "$(INTDIR)\XToolkit.obj"
	-@erase "$(OUTDIR)\jcnfigSN.dll"
	-@erase "$(OUTDIR)\jcnfigSN.exp"
	-@erase "$(OUTDIR)\jcnfigSN.ilk"
	-@erase "$(OUTDIR)\jcnfigSN.lib"
	-@erase "$(OUTDIR)\jcnfigSN.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /MTd /W3 /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /YX /c
# ADD CPP /nologo /MTd /W3 /Gm /GX /Zi /Od /I "files\common" /I "files\win" /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "DO_SEH" /D "DO_JNI" /YX /c
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /Zi /Od /I "files\common" /I "files\win" /D\
 "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "DO_SEH" /D "DO_JNI"\
 /Fp"$(INTDIR)/jcSNA.pch" /YX /Fo"$(INTDIR)/" /Fd"$(INTDIR)/" /c 
CPP_OBJS=.\Debug/
CPP_SBRS=.\.
# ADD BASE MTL /nologo /D "_DEBUG" /win32
# ADD MTL /nologo /D "_DEBUG" /win32
MTL_PROJ=/nologo /D "_DEBUG" /win32 
# ADD BASE RSC /l 0x409 /d "_DEBUG"
# ADD RSC /l 0x409 /d "_DEBUG"
RSC_PROJ=/l 0x409 /fo"$(INTDIR)/Script1.res" /d "_DEBUG" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
BSC32_FLAGS=/nologo /o"$(OUTDIR)/jcSNA.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /dll /debug /machine:I386
# ADD LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /dll /debug /machine:I386 /out:"Debug/jcnfigSN.dll"
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib\
 advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib\
 odbccp32.lib /nologo /subsystem:windows /dll /incremental:yes\
 /pdb:"$(OUTDIR)/jcnfigSN.pdb" /debug /machine:I386\
 /out:"$(OUTDIR)/jcnfigSN.dll" /implib:"$(OUTDIR)/jcnfigSN.lib" 
LINK32_OBJS= \
	"$(INTDIR)\AppUtilsMSVM.obj" \
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
	"$(INTDIR)\CWinProcess95.obj" \
	"$(INTDIR)\Debugger.obj" \
	"$(INTDIR)\entry.obj" \
	"$(INTDIR)\JNIUtils.obj" \
	"$(INTDIR)\SAliases.obj" \
	"$(INTDIR)\SAppFinder.obj" \
	"$(INTDIR)\SAppInfo.obj" \
	"$(INTDIR)\Script1.res" \
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
	"$(PSDK)\lib\Comctl32.lib" \
	"$(PSDK)\lib\Version.lib"

"$(OUTDIR)\jcnfigSN.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ENDIF 

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

################################################################################
# Begin Target

# Name "jcSNA - Win32 Release"
# Name "jcSNA - Win32 Debug"

!IF  "$(CFG)" == "jcSNA - Win32 Release"

!ELSEIF  "$(CFG)" == "jcSNA - Win32 Debug"

!ENDIF 

################################################################################
# Begin Source File

SOURCE=\Msdev\lib\Version.lib

!IF  "$(CFG)" == "jcSNA - Win32 Release"

!ELSEIF  "$(CFG)" == "jcSNA - Win32 Debug"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\lib\Comctl32.lib

!IF  "$(CFG)" == "jcSNA - Win32 Release"

!ELSEIF  "$(CFG)" == "jcSNA - Win32 Debug"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\JNIUtils.cpp
DEP_CPP_JNIUT=\
	".\files\common\AppData.h"\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	
NODEP_CPP_JNIUT=\
	".\files\common\CUtils.h"\
	".\files\common\SFiles.h"\
	

"$(INTDIR)\JNIUtils.obj" : $(SOURCE) $(DEP_CPP_JNIUT) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CMemory.cpp
DEP_CPP_CMEMO=\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\errcodes.h"\
	

"$(INTDIR)\CMemory.obj" : $(SOURCE) $(DEP_CPP_CMEMO) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CStrA.cpp
DEP_CPP_CSTRA=\
	".\files\common\AppData.h"\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	

"$(INTDIR)\CStrA.obj" : $(SOURCE) $(DEP_CPP_CSTRA) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CStringVector.cpp
DEP_CPP_CSTRI=\
	".\files\common\AppData.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStringVector.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	

"$(INTDIR)\CStringVector.obj" : $(SOURCE) $(DEP_CPP_CSTRI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CStrW.cpp
DEP_CPP_CSTRW=\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	

"$(INTDIR)\CStrW.obj" : $(SOURCE) $(DEP_CPP_CSTRW) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CVector.cpp
DEP_CPP_CVECT=\
	".\files\common\AppData.h"\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	

"$(INTDIR)\CVector.obj" : $(SOURCE) $(DEP_CPP_CVECT) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\Debugger.cpp
DEP_CPP_DEBUG=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\unicodeOn95.h"\
	".\files\win\w95trace.h"\
	

"$(INTDIR)\Debugger.obj" : $(SOURCE) $(DEP_CPP_DEBUG) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CDateBundle.cpp
DEP_CPP_CDATE=\
	".\files\common\AppData.h"\
	".\files\common\CDateBundle.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	

"$(INTDIR)\CDateBundle.obj" : $(SOURCE) $(DEP_CPP_CDATE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CFileUtils.cpp
DEP_CPP_CFILE=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\CFileUtils.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\CFileUtils.obj" : $(SOURCE) $(DEP_CPP_CFILE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CIconUtils.cpp
DEP_CPP_CICON=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\CIconUtils.h"\
	

"$(INTDIR)\CIconUtils.obj" : $(SOURCE) $(DEP_CPP_CICON) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CLinkFileUtils.cpp
DEP_CPP_CLINK=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\CLinkFileUtils.h"\
	

"$(INTDIR)\CLinkFileUtils.obj" : $(SOURCE) $(DEP_CPP_CLINK) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CRegUtils.cpp
DEP_CPP_CREGU=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	".\files\win\CRegUtils.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\CRegUtils.obj" : $(SOURCE) $(DEP_CPP_CREGU) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\entry.cpp
DEP_CPP_ENTRY=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	

"$(INTDIR)\entry.obj" : $(SOURCE) $(DEP_CPP_ENTRY) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SAliases.cpp
DEP_CPP_SALIA=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	".\files\win\CLinkFileUtils.h"\
	".\files\win\SAliases.h"\
	

"$(INTDIR)\SAliases.obj" : $(SOURCE) $(DEP_CPP_SALIA) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SAppInfo.cpp
DEP_CPP_SAPPI=\
	".\files\common\AppData.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStringVector.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	".\files\win\CFileUtils.h"\
	".\files\win\CRegUtils.h"\
	".\files\win\SAppInfo.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SAppInfo.obj" : $(SOURCE) $(DEP_CPP_SAPPI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SFileInfo.cpp
DEP_CPP_SFILE=\
	".\files\common\AppData.h"\
	".\files\common\CDateBundle.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	".\files\win\CFileUtils.h"\
	".\files\win\SFileInfo.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SFileInfo.obj" : $(SOURCE) $(DEP_CPP_SFILE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SFileIterate.cpp
DEP_CPP_SFILEI=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	".\files\win\SFileIterate.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SFileIterate.obj" : $(SOURCE) $(DEP_CPP_SFILEI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SIconInfo.cpp
DEP_CPP_SICON=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\CIconUtils.h"\
	".\files\win\SIconInfo.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SIconInfo.obj" : $(SOURCE) $(DEP_CPP_SICON) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SMonitors.cpp
DEP_CPP_SMONI=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\SMonitors.h"\
	"$(PSDK)\include\multimon.h"\
	

"$(INTDIR)\SMonitors.obj" : $(SOURCE) $(DEP_CPP_SMONI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SVersionInfo.cpp
DEP_CPP_SVERS=\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\SVersionInfo.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SVersionInfo.obj" : $(SOURCE) $(DEP_CPP_SVERS) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SVolumes.cpp
DEP_CPP_SVOLU=\
	".\files\common\AppData.h"\
	".\files\common\CDateBundle.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStringVector.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	".\files\win\CFileUtils.h"\
	".\files\win\SVolumes.h"\
	".\files\win\utils.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SVolumes.obj" : $(SOURCE) $(DEP_CPP_SVOLU) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SWebBrowser.cpp
DEP_CPP_SWEBB=\
	".\files\common\AppData.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStringVector.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	".\files\win\CRegUtils.h"\
	".\files\win\SAppFinder.h"\
	".\files\win\SWebBrowser.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SWebBrowser.obj" : $(SOURCE) $(DEP_CPP_SWEBB) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\utils.cpp
DEP_CPP_UTILS=\
	".\files\common\AppData.h"\
	".\files\common\CDateBundle.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStringVector.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	".\files\win\SVolumes.h"\
	".\files\win\utils.h"\
	

"$(INTDIR)\utils.obj" : $(SOURCE) $(DEP_CPP_UTILS) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\W95trace.cpp
DEP_CPP_W95TR=\
	".\files\win\w95trace.h"\
	

"$(INTDIR)\W95trace.obj" : $(SOURCE) $(DEP_CPP_W95TR) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\XToolkit.cpp
DEP_CPP_XTOOL=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\unicodeOn95.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\XToolkit.obj" : $(SOURCE) $(DEP_CPP_XTOOL) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\Script1.rc
DEP_RSC_SCRIP=\
	".\files\win\icon1.ico"\
	

!IF  "$(CFG)" == "jcSNA - Win32 Release"


"$(INTDIR)\Script1.res" : $(SOURCE) $(DEP_RSC_SCRIP) "$(INTDIR)"
   $(RSC) /l 0x409 /fo"$(INTDIR)/Script1.res" /i "files\win" /d "NDEBUG"\
 $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNA - Win32 Debug"


"$(INTDIR)\Script1.res" : $(SOURCE) $(DEP_RSC_SCRIP) "$(INTDIR)"
   $(RSC) /l 0x409 /fo"$(INTDIR)/Script1.res" /i "files\win" /d "_DEBUG"\
 $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SAppFinder.cpp
DEP_CPP_SAPPF=\
	".\files\common\AppData.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStringVector.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	".\files\win\CFileUtils.h"\
	".\files\win\CRegUtils.h"\
	".\files\win\SAppFinder.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SAppFinder.obj" : $(SOURCE) $(DEP_CPP_SAPPF) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\AppUtilsMSVM.cpp
DEP_CPP_APPUT=\
	".\files\common\AppData.h"\
	".\files\common\CDateBundle.h"\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStringVector.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	".\files\common\stub_macros.h"\
	".\files\win\AppUtilsMSVM.h"\
	".\files\win\CIconUtils.h"\
	".\files\win\CWinProcess.h"\
	".\files\win\CWinProcess95.h"\
	".\files\win\CWinProcessNT.h"\
	".\files\win\JDK10_AppUtilsMSVM.h"\
	".\files\win\JNI_AppUtilsMSVM.h"\
	".\files\win\RNI1_AppUtilsMSVM.h"\
	".\files\win\RNI2_AppUtilsMSVM.h"\
	".\files\win\SAliases.h"\
	".\files\win\SAppFinder.h"\
	".\files\win\SAppInfo.h"\
	".\files\win\sehstubs.cpp"\
	".\files\win\sehstubs.h"\
	".\files\win\SFileInfo.h"\
	".\files\win\SFileIterate.h"\
	".\files\win\SIconInfo.h"\
	".\files\win\SMonitors.h"\
	".\files\win\SVersionInfo.h"\
	".\files\win\SVolumes.h"\
	".\files\win\SWebBrowser.h"\
	

"$(INTDIR)\AppUtilsMSVM.obj" : $(SOURCE) $(DEP_CPP_APPUT) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CWinProcess95.cpp
DEP_CPP_CWINP=\
	".\files\common\AppData.h"\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStringVector.h"\
	".\files\common\CStrW.h"\
	".\files\common\CVector.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\common\jmacros.h"\
	".\files\common\jni_macros.h"\
	".\files\common\JNIUtils.h"\
	".\files\common\jri_macros.h"\
	".\files\common\JRIUtils.h"\
	".\files\common\rni_macros.h"\
	".\files\common\RNIUtils.h"\
	".\files\win\CWinProcess95.h"\
	".\files\win\SAppInfo.h"\
	

"$(INTDIR)\CWinProcess95.obj" : $(SOURCE) $(DEP_CPP_CWINP) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
# End Target
# End Project
################################################################################

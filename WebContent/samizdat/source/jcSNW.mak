# Microsoft Developer Studio Generated NMAKE File, Format Version 4.20
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

!IF "$(CFG)" == ""
CFG=jcSNW - Win32 Debug
!MESSAGE No configuration specified.  Defaulting to jcSNW - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "jcSNW - Win32 Release" && "$(CFG)" != "jcSNW - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE on this makefile
!MESSAGE by defining the macro CFG on the command line.  For example:
!MESSAGE 
!MESSAGE NMAKE /f "jcSNW.mak" CFG="jcSNW - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "jcSNW - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "jcSNW - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
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
# PROP Target_Last_Scanned "jcSNW - Win32 Debug"
MTL=mktyplib.exe
CPP=cl.exe
RSC=rc.exe

!IF  "$(CFG)" == "jcSNW - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "SNW"
# PROP Intermediate_Dir "SNW"
# PROP Target_Dir ""
OUTDIR=.\SNW
INTDIR=.\SNW

ALL : "$(OUTDIR)\jcnfigSW.dll"

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
	-@erase "$(INTDIR)\CWinProcessNT.obj"
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
	-@erase "$(INTDIR)\XToolkit.obj"
	-@erase "$(OUTDIR)\jcnfigSW.dll"
	-@erase "$(OUTDIR)\jcnfigSW.exp"
	-@erase "$(OUTDIR)\jcnfigSW.lib"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /MT /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /YX /c
# ADD CPP /nologo /MT /W3 /GX /O2 /I "files\common" /I "files\win" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "DO_JNI" /D "UNICODE" /D "_UNICODE" /D "DO_SEH" /YX /c
CPP_PROJ=/nologo /MT /W3 /GX /O2 /I "files\common" /I "files\win" /D "WIN32" /D\
 "NDEBUG" /D "_WINDOWS" /D "DO_JNI" /D "UNICODE" /D "_UNICODE" /D "DO_SEH"\
 /Fp"$(INTDIR)/jcSNW.pch" /YX /Fo"$(INTDIR)/" /c 
CPP_OBJS=.\SNW/
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
BSC32_FLAGS=/nologo /o"$(OUTDIR)/jcSNW.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /dll /machine:I386
# ADD LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /dll /machine:I386 /out:"SNW/jcnfigSW.dll"
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib\
 advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib\
 odbccp32.lib /nologo /subsystem:windows /dll /incremental:no\
 /pdb:"$(OUTDIR)/jcnfigSW.pdb" /machine:I386 /out:"$(OUTDIR)/jcnfigSW.dll"\
 /implib:"$(OUTDIR)/jcnfigSW.lib" 
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
	"$(INTDIR)\CWinProcessNT.obj" \
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
	"$(INTDIR)\XToolkit.obj" \
	"$(PSDK)\lib\Comctl32.lib" \
	"$(PSDK)\lib\Version.lib"

"$(OUTDIR)\jcnfigSW.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

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

ALL : "$(OUTDIR)\jcSNW.dll"

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
	-@erase "$(INTDIR)\CWinProcessNT.obj"
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
	-@erase "$(INTDIR)\XToolkit.obj"
	-@erase "$(OUTDIR)\jcSNW.dll"
	-@erase "$(OUTDIR)\jcSNW.exp"
	-@erase "$(OUTDIR)\jcSNW.ilk"
	-@erase "$(OUTDIR)\jcSNW.lib"
	-@erase "$(OUTDIR)\jcSNW.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /MTd /W3 /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /YX /c
# ADD CPP /nologo /MTd /W3 /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /YX /c
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS"\
 /Fp"$(INTDIR)/jcSNW.pch" /YX /Fo"$(INTDIR)/" /Fd"$(INTDIR)/" /c 
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
BSC32_FLAGS=/nologo /o"$(OUTDIR)/jcSNW.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /dll /debug /machine:I386
# ADD LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:windows /dll /debug /machine:I386
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib\
 advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib\
 odbccp32.lib /nologo /subsystem:windows /dll /incremental:yes\
 /pdb:"$(OUTDIR)/jcSNW.pdb" /debug /machine:I386 /out:"$(OUTDIR)/jcSNW.dll"\
 /implib:"$(OUTDIR)/jcSNW.lib" 
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
	"$(INTDIR)\CWinProcessNT.obj" \
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
	"$(INTDIR)\XToolkit.obj" \
	"$(PSDK)\lib\Comctl32.lib" \
	"$(PSDK)\lib\Version.lib"

"$(OUTDIR)\jcSNW.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

# Name "jcSNW - Win32 Release"
# Name "jcSNW - Win32 Debug"

!IF  "$(CFG)" == "jcSNW - Win32 Release"

!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

!ENDIF 

################################################################################
# Begin Source File

SOURCE=\Msdev\lib\Comctl32.lib

!IF  "$(CFG)" == "jcSNW - Win32 Release"

!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=\Msdev\lib\Version.lib

!IF  "$(CFG)" == "jcSNW - Win32 Release"

!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CDateBundle.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

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


!ENDIF 

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

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

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


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CStringVector.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

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


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CStrW.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

DEP_CPP_CSTRW=\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	

"$(INTDIR)\CStrW.obj" : $(SOURCE) $(DEP_CPP_CSTRW) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_CSTRW=\
	".\files\common\CMemory.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	

"$(INTDIR)\CStrW.obj" : $(SOURCE) $(DEP_CPP_CSTRW) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\CVector.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

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


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\Debugger.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

DEP_CPP_DEBUG=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\unicodeOn95.h"\
	".\files\win\w95trace.h"\
	
NODEP_CPP_DEBUG=\
	".\files\common\Debug.h"\
	

"$(INTDIR)\Debugger.obj" : $(SOURCE) $(DEP_CPP_DEBUG) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_DEBUG=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	
NODEP_CPP_DEBUG=\
	".\files\common\unicodeOn95.h"\
	".\files\common\w95trace.h"\
	

"$(INTDIR)\Debugger.obj" : $(SOURCE) $(DEP_CPP_DEBUG) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\common\JNIUtils.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

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


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\AppUtilsMSVM.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

DEP_CPP_APPUT=\
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
	".\files\common\stub_macros.h"\
	".\files\win\AppUtilsMSVM.h"\
	".\files\win\CIconUtils.h"\
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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_APPUT=\
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
	
NODEP_CPP_APPUT=\
	".\files\win\AppData.h"\
	".\files\win\CDateBundle.h"\
	".\files\win\CMemory.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\CStringVector.h"\
	".\files\win\CVector.h"\
	".\files\win\Debugger.h"\
	".\files\win\jmacros.h"\
	".\files\win\stub_macros.h"\
	

"$(INTDIR)\AppUtilsMSVM.obj" : $(SOURCE) $(DEP_CPP_APPUT) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CFileUtils.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_CFILE=\
	".\files\win\CFileUtils.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_CFILE=\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\CFileUtils.obj" : $(SOURCE) $(DEP_CPP_CFILE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CIconUtils.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_CICON=\
	".\files\win\CIconUtils.h"\
	
NODEP_CPP_CICON=\
	".\files\win\comdefs.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\CIconUtils.obj" : $(SOURCE) $(DEP_CPP_CICON) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CLinkFileUtils.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_CLINK=\
	".\files\win\CLinkFileUtils.h"\
	
NODEP_CPP_CLINK=\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\CLinkFileUtils.obj" : $(SOURCE) $(DEP_CPP_CLINK) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CRegUtils.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_CREGU=\
	".\files\win\CRegUtils.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_CREGU=\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	

"$(INTDIR)\CRegUtils.obj" : $(SOURCE) $(DEP_CPP_CREGU) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\entry.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

DEP_CPP_ENTRY=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	

"$(INTDIR)\entry.obj" : $(SOURCE) $(DEP_CPP_ENTRY) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

NODEP_CPP_ENTRY=\
	".\files\win\comdefs.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\entry.obj" : $(SOURCE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SAliases.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SALIA=\
	".\files\win\CLinkFileUtils.h"\
	".\files\win\SAliases.h"\
	
NODEP_CPP_SALIA=\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	

"$(INTDIR)\SAliases.obj" : $(SOURCE) $(DEP_CPP_SALIA) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SAppInfo.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SAPPI=\
	".\files\win\CFileUtils.h"\
	".\files\win\CRegUtils.h"\
	".\files\win\SAppInfo.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_SAPPI=\
	".\files\win\AppData.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\CStringVector.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\SAppInfo.obj" : $(SOURCE) $(DEP_CPP_SAPPI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SFileInfo.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SFILE=\
	".\files\win\CFileUtils.h"\
	".\files\win\SFileInfo.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_SFILE=\
	".\files\win\CDateBundle.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\SFileInfo.obj" : $(SOURCE) $(DEP_CPP_SFILE) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SFileIterate.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SFILEI=\
	".\files\win\SFileIterate.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_SFILEI=\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	

"$(INTDIR)\SFileIterate.obj" : $(SOURCE) $(DEP_CPP_SFILEI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SIconInfo.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SICON=\
	".\files\win\CIconUtils.h"\
	".\files\win\SIconInfo.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_SICON=\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\SIconInfo.obj" : $(SOURCE) $(DEP_CPP_SICON) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SMonitors.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

DEP_CPP_SMONI=\
	"$(PSDK)\include\multimon.h"\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\Debugger.h"\
	".\files\common\errcodes.h"\
	".\files\win\SMonitors.h"\
	

"$(INTDIR)\SMonitors.obj" : $(SOURCE) $(DEP_CPP_SMONI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SMONI=\
	"$(PSDK)\include\multimon.h"\
	".\files\win\SMonitors.h"\
	
NODEP_CPP_SMONI=\
	".\files\win\comdefs.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\SMonitors.obj" : $(SOURCE) $(DEP_CPP_SMONI) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SVersionInfo.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SVERS=\
	".\files\win\SVersionInfo.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_SVERS=\
	".\files\win\CMemory.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\SVersionInfo.obj" : $(SOURCE) $(DEP_CPP_SVERS) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SVolumes.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

DEP_CPP_SVOLU=\
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
	".\files\win\CFileUtils.h"\
	".\files\win\SVolumes.h"\
	".\files\win\utils.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\SVolumes.obj" : $(SOURCE) $(DEP_CPP_SVOLU) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SVOLU=\
	".\files\win\CFileUtils.h"\
	".\files\win\SVolumes.h"\
	".\files\win\utils.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_SVOLU=\
	".\files\win\CDateBundle.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\CStringVector.h"\
	

"$(INTDIR)\SVolumes.obj" : $(SOURCE) $(DEP_CPP_SVOLU) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SWebBrowser.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SWEBB=\
	".\files\win\CRegUtils.h"\
	".\files\win\SAppFinder.h"\
	".\files\win\SWebBrowser.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_SWEBB=\
	".\files\win\AppData.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\CStringVector.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\SWebBrowser.obj" : $(SOURCE) $(DEP_CPP_SWEBB) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\utils.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_UTILS=\
	".\files\win\SVolumes.h"\
	".\files\win\utils.h"\
	
NODEP_CPP_UTILS=\
	".\files\win\CDateBundle.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\CStringVector.h"\
	

"$(INTDIR)\utils.obj" : $(SOURCE) $(DEP_CPP_UTILS) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\XToolkit.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

DEP_CPP_XTOOL=\
	".\files\common\comdefs.h"\
	".\files\common\CStrA.h"\
	".\files\common\CString.h"\
	".\files\common\CStrW.h"\
	".\files\common\errcodes.h"\
	".\files\win\unicodeOn95.h"\
	".\files\win\XToolkit.h"\
	

"$(INTDIR)\XToolkit.obj" : $(SOURCE) $(DEP_CPP_XTOOL) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_XTOOL=\
	".\files\win\unicodeOn95.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_XTOOL=\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	

"$(INTDIR)\XToolkit.obj" : $(SOURCE) $(DEP_CPP_XTOOL) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\Script1.rc
DEP_RSC_SCRIP=\
	".\files\win\icon1.ico"\
	

!IF  "$(CFG)" == "jcSNW - Win32 Release"


"$(INTDIR)\Script1.res" : $(SOURCE) $(DEP_RSC_SCRIP) "$(INTDIR)"
   $(RSC) /l 0x409 /fo"$(INTDIR)/Script1.res" /i "files\win" /d "NDEBUG"\
 $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"


"$(INTDIR)\Script1.res" : $(SOURCE) $(DEP_RSC_SCRIP) "$(INTDIR)"
   $(RSC) /l 0x409 /fo"$(INTDIR)/Script1.res" /i "files\win" /d "_DEBUG"\
 $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\SAppFinder.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

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


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_SAPPF=\
	".\files\win\CFileUtils.h"\
	".\files\win\CRegUtils.h"\
	".\files\win\SAppFinder.h"\
	".\files\win\XToolkit.h"\
	
NODEP_CPP_SAPPF=\
	".\files\win\AppData.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\CStringVector.h"\
	".\files\win\Debugger.h"\
	

"$(INTDIR)\SAppFinder.obj" : $(SOURCE) $(DEP_CPP_SAPPF) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\files\win\CWinProcessNT.cpp

!IF  "$(CFG)" == "jcSNW - Win32 Release"

DEP_CPP_CWINP=\
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
	".\files\win\CWinProcessNT.h"\
	".\files\win\SAppInfo.h"\
	

"$(INTDIR)\CWinProcessNT.obj" : $(SOURCE) $(DEP_CPP_CWINP) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ELSEIF  "$(CFG)" == "jcSNW - Win32 Debug"

DEP_CPP_CWINP=\
	".\files\win\CWinProcessNT.h"\
	".\files\win\SAppInfo.h"\
	
NODEP_CPP_CWINP=\
	".\files\win\AppData.h"\
	".\files\win\comdefs.h"\
	".\files\win\CString.h"\
	".\files\win\CStringVector.h"\
	".\files\win\CVector.h"\
	".\files\win\Debugger.h"\
	".\files\win\jmacros.h"\
	

"$(INTDIR)\CWinProcessNT.obj" : $(SOURCE) $(DEP_CPP_CWINP) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


!ENDIF 

# End Source File
# End Target
# End Project
################################################################################

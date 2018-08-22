/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CWINPROCESSNT_H
#define INC_CWINPROCESSNT_H

#include "comdefs.h"
#include <tlhelp32.h>
#include "CString.h"
#include "CVector.h"
#include "jmacros.h"

#if defined( DO_JNI ) || defined( DO_JRI ) || defined( DO_RNI1 ) || defined( DO_RNI2 )
	#if defined(_WIN32)
		#include <native.h>
	#endif
#endif

/*------------------------------------------------------------------------
CLASS
	CWinProcessNT

	Represents a process on Windows NT.

DESCRIPTION
	Represents a process on Windows NT.

------------------------------------------------------------------------*/

class CWinProcessNT {
	public:

		///////////////////////
		//
		//  Constructor
		//
		//	[in]	dwProcessID	the process ID, as retrieved from EnumProcesses
		//	[in]	hProc		the process handle, as retrieved from OpenProcess
		//
	CWinProcessNT( DWORD dwProcessID, HANDLE hProc );

		///////////////////////
		//
		//  Destructor
		//
	virtual	~CWinProcessNT( void );

		///////////////////////
		//
		//  Fills the CVector with all the running processes.
		//
		//  [in]	processes		On exit, will contain CWinProcessNT objects representing each running process.
		//
	static ErrCode getAllProcessInfo( CVector &processes );

		///////////////////////
		//
		//  Fills the passed in pointers with information on this process.
		//
		//
	virtual void getData( DWORD *cntUsageArrayP, DWORD *th32ProcessIDArray,
					DWORD *th32DefaultHeapIDArrayP, DWORD *th32ModuleIDArrayP,
					DWORD *cntThreadsArrayP, DWORD *th32ParentProcessIDArrayP,
					DWORD *pcPriClassBaseArrayP, DWORD *dwFlagsArrayP,
					DWORD *dwThreadIDArrayP, HWND *hWndArrayP );

		///////////////////////
		//
		//  Returns the full path of this process.
		//
		//
	virtual CStr *getFullPath();

protected:

private:

	enum {
		MAX_PROCESS_IDS = 1024,
		MAX_MODULES = 1
	};

	static void initFuncs();

	typedef BOOL (WINAPI *ENUMPROCESSES)( DWORD * lpidProcess, DWORD cb, DWORD * cbNeeded );
	typedef BOOL (WINAPI *ENUMPROCESSMODULES)( HANDLE hProcess, HMODULE * lphModule, DWORD cb, LPDWORD lpcbNeeded );
	typedef DWORD (WINAPI *GETMODULEFILENAMEEX)( HANDLE hProcess, HMODULE hModule, LPTSTR lpstrFileName, DWORD nSize );

	static ENUMPROCESSES		pEnumProcesses;
	static ENUMPROCESSMODULES	pEnumProcessModules;
	static GETMODULEFILENAMEEX	pGetModuleFileNameEx;

	TCHAR	mainModuleFullPath[ MAX_PATH ];
	DWORD	dwProcessID;
};

#endif


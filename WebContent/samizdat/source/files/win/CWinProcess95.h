/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CWINPROCESS95_H
#define INC_CWINPROCESS95_H

#include "comdefs.h"
#include <tlhelp32.h>
#include "CVector.h"
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	CWinProcess95

	Represents a process on Windows 95/98.

DESCRIPTION
	Represents a process on Windows 95/98.

------------------------------------------------------------------------*/

class CWinProcess95 {
	public:

		///////////////////////
		//
		//  Constructor
		//
		//	[in]	entry		contains info on the process, as retrieved from Process32First
		//
	CWinProcess95( const PROCESSENTRY32 *entry );

		///////////////////////
		//
		//  Destructor
		//
	virtual	~CWinProcess95( void );

		///////////////////////
		//
		//  Fills the CVector with all the running processes.
		//
		//  [in]	processes		On exit, will contain CWinProcessNT objects representing each running process.
		//
	static ErrCode getAllProcessInfo( CVector &vec );

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

	static ErrCode initFuncs();

	typedef BOOL (WINAPI *MODULEWALK)( HANDLE hSnapshot, LPMODULEENTRY32 lpme ); 
	typedef BOOL (WINAPI *THREADWALK)( HANDLE hSnapshot, LPTHREADENTRY32 lpte ); 
	typedef BOOL (WINAPI *PROCESSWALK)( HANDLE hSnapshot, LPPROCESSENTRY32 lppe ); 
	typedef HANDLE (WINAPI *CREATESNAPSHOT)( DWORD dwFlags, DWORD th32ProcessID ); 

	static CREATESNAPSHOT pCreateToolhelp32Snapshot; 
	static PROCESSWALK pProcess32First; 
	static PROCESSWALK pProcess32Next; 

	PROCESSENTRY32		processEntry;
};

#endif


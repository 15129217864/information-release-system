/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CWinProcess95.h"
#include "CMemory.h"
#include "CString.h"
#include "Debugger.h"
#include "SAppInfo.h"

CWinProcess95::CREATESNAPSHOT CWinProcess95::pCreateToolhelp32Snapshot = NULL; 
CWinProcess95::PROCESSWALK CWinProcess95::pProcess32First = NULL; 
CWinProcess95::PROCESSWALK CWinProcess95::pProcess32Next  = NULL; 

CWinProcess95::CWinProcess95( const PROCESSENTRY32 *entry )
{
	memcpy( (void*) &processEntry, (const void*) entry, sizeof(PROCESSENTRY32) );
}

CWinProcess95::~CWinProcess95()
{
}

ErrCode CWinProcess95::getAllProcessInfo( CVector &processes )
{
	PROCESSENTRY32		processEntry;
	HANDLE				hSnapshot;
	ErrCode				theErr;
	BOOL				bSuccess;

	theErr = kErrNoErr;
	hSnapshot = NULL;

	if ( pCreateToolhelp32Snapshot == NULL )
		theErr = initFuncs();

	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "enumerateProcesses, can't init" ) );
		goto bail;
	}

	hSnapshot = pCreateToolhelp32Snapshot( TH32CS_SNAPPROCESS, 0 );
	if ( hSnapshot == INVALID_HANDLE_VALUE ) {
		theErr = kErrEnumerateProcessesSnapshot;
		Debugger::debug( __LINE__, _TXL( "enumerateProcesses, can't CreateToolhelp32Snapshot" ) );
		goto bail;
	}

	processEntry.dwSize = sizeof(processEntry);
	bSuccess = pProcess32First( hSnapshot, &processEntry );
	if ( !bSuccess ) {
		theErr = kErrEnumerateProcessesFirst;
		Debugger::debug( __LINE__, _TXL( "enumerateProcesses, can't Process32First" ) );
		goto bail;
	}

	while ( bSuccess ) {
		processes.append( new CWinProcess95( &processEntry ) );
		processEntry.dwSize = sizeof(processEntry);
		bSuccess = pProcess32Next( hSnapshot, &processEntry );
	}

bail:

	if ( hSnapshot != NULL && hSnapshot != INVALID_HANDLE_VALUE )
		CloseHandle( hSnapshot );

	return theErr;
}

ErrCode CWinProcess95::initFuncs() 
{ 
	BOOL   bRet  = FALSE; 
	HANDLE hKernel = NULL; 

	hKernel = GetModuleHandle( "KERNEL32.DLL" ); 

	if ( hKernel == NULL )
		return kErrEnumerateProcessesInit;

	pCreateToolhelp32Snapshot = (CREATESNAPSHOT) GetProcAddress( (HINSTANCE) hKernel, "CreateToolhelp32Snapshot" ); 
	pProcess32First = (PROCESSWALK) GetProcAddress( (HINSTANCE) hKernel, "Process32First" ); 
	pProcess32Next = (PROCESSWALK) GetProcAddress( (HINSTANCE) hKernel, "Process32Next" ); 

	if ( pCreateToolhelp32Snapshot == NULL || pProcess32First == NULL || pProcess32Next == NULL )
		return kErrEnumerateProcessesInit;
	else
		return kErrNoErr;
} 

void CWinProcess95::getData( DWORD *cntUsageArrayP, DWORD *th32ProcessIDArray,
								DWORD *th32DefaultHeapIDArrayP, DWORD *th32ModuleIDArrayP,
								DWORD *cntThreadsArrayP, DWORD *th32ParentProcessIDArrayP,
								DWORD *pcPriClassBaseArrayP, DWORD *dwFlagsArrayP,
								DWORD *dwThreadIDArrayP, HWND *hWndArrayP )
{
	HWND			hWnd;
	DWORD			dwThreadID;

	SAppInfo::getThreadIDAndHWNDFromProcessID( processEntry.th32ProcessID, &dwThreadID, &hWnd );

	*cntUsageArrayP = processEntry.cntUsage;
	*th32ProcessIDArray = processEntry.th32ProcessID;
	*th32DefaultHeapIDArrayP = processEntry.th32DefaultHeapID;
	*th32ModuleIDArrayP = processEntry.th32ModuleID;
	*cntThreadsArrayP = processEntry.cntThreads;
	*th32ParentProcessIDArrayP = processEntry.th32ParentProcessID;
	*pcPriClassBaseArrayP = processEntry.pcPriClassBase;
	*dwFlagsArrayP = processEntry.dwFlags;
	*dwThreadIDArrayP = dwThreadID;
	*hWndArrayP = hWnd;
}

CStr *CWinProcess95::getFullPath()
{
	return new CStr( processEntry.szExeFile );
}


/*
typedef struct tagPROCESSENTRY32 { 
    DWORD dwSize;               // size, in bytes, of structure 
    DWORD cntUsage;             // see below 
    DWORD th32ProcessID;        // specified process 
    DWORD th32DefaultHeapID;    // see below 
    DWORD th32ModuleID;         // see below 
    DWORD cntThreads;           // see below 
    DWORD th32ParentProcessID;  // see below 
    LONG  pcPriClassBase;       // see below 
    DWORD dwFlags;              // reserved; do not use 
    char szExeFile[MAX_PATH];   // see below 
} PROCESSENTRY32; 
th32ProcessID
Identifier of the Win32 process.

szExeFile
Path and filename of the executable file for the process. 

pcPriClassBase
Base priority of any threads created by this process.

cntUsage
Number of references to the process

cntThreads
Number of execution threads started by the process.

th32ParentProcessID
Identifier of the Win32 process that created the process being examined.
The contents of this member can be used by other Win32 functions and macros.


*/


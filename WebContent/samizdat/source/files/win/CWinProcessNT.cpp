/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CWinProcessNT.h"
#include "Debugger.h"
#include "SAppInfo.h"
 
CWinProcessNT::ENUMPROCESSES		CWinProcessNT::pEnumProcesses = NULL;
CWinProcessNT::ENUMPROCESSMODULES	CWinProcessNT::pEnumProcessModules = NULL;
CWinProcessNT::GETMODULEFILENAMEEX	CWinProcessNT::pGetModuleFileNameEx = NULL;

CWinProcessNT::CWinProcessNT( DWORD procID, HANDLE hProcess )
{
	HMODULE				modules[ MAX_MODULES ];
	DWORD				numModules;
	long				result;
	BOOL				bResult;

	if ( pEnumProcesses == NULL )
		initFuncs();

	dwProcessID = procID;

	bResult = pEnumProcessModules( hProcess, modules, sizeof(modules), &numModules );
	if ( !bResult || numModules < 1 ) {
		CloseHandle( hProcess );
		throw _TXL( "CWinProcessNT: can't pEnumProcessModules" );
	}

	result = pGetModuleFileNameEx( hProcess, modules[ 0 ], mainModuleFullPath, sizeof(mainModuleFullPath)/sizeof(TCHAR) );

	if ( result == 0 ) {
		CloseHandle( hProcess );
		throw _TXL( "CWinProcessNT: can't pGetModuleFileNameEx" );
	}
}

CWinProcessNT::~CWinProcessNT()
{
}

void CWinProcessNT::initFuncs() 
{
	HINSTANCE		hInstance;

	hInstance = (HINSTANCE) LoadLibrary( _TXL("PSAPI.DLL") );

    pEnumProcesses = (ENUMPROCESSES) GetProcAddress( hInstance, "EnumProcesses" );
    pEnumProcessModules = (ENUMPROCESSMODULES) GetProcAddress( hInstance, "EnumProcessModules" );

#if defined(UNICODE)
    pGetModuleFileNameEx = (GETMODULEFILENAMEEX) GetProcAddress( hInstance, "GetModuleFileNameExW" );
#else
    pGetModuleFileNameEx = (GETMODULEFILENAMEEX) GetProcAddress( hInstance, "GetModuleFileNameExA" );
#endif

	if ( pEnumProcesses == NULL || pGetModuleFileNameEx == NULL || pEnumProcessModules == NULL )
		throw _TXL( "CWinProcessNT: can't initFuncs" );
}

ErrCode CWinProcessNT::getAllProcessInfo( CVector &processes )
{
	BOOL				bSuccess;
	DWORD				numProcesses, processID, processIDs[ MAX_PROCESS_IDS ];
	long				i;
	HANDLE				hProcess;

	if ( pEnumProcesses == NULL )
		initFuncs();

	bSuccess = pEnumProcesses( processIDs, sizeof(processIDs), &numProcesses );
	if ( !bSuccess )
		return kErrEnumerateProcessesSnapshot;

	numProcesses /= sizeof(DWORD);

	for ( i = 0; i < numProcesses; i++ ) {
		processID = processIDs[ i ];

		hProcess = OpenProcess( PROCESS_QUERY_INFORMATION | PROCESS_TERMINATE | PROCESS_VM_READ,
								FALSE, processID );
		//hProcess = OpenProcess( PROCESS_ALL_ACCESS, FALSE, processID );

		if ( hProcess != NULL ) {
			try {
				processes.append( new CWinProcessNT( processID, hProcess ) );
			}
			catch ( LPCTSTR e ) {
				Debugger::debug( __LINE__, _TXL( "got exception" ), e );
			}

			CloseHandle( hProcess );
		}
	}

	return kErrNoErr;
}

void CWinProcessNT::getData( DWORD *cntUsageArrayP, DWORD *th32ProcessIDArray,
								DWORD *th32DefaultHeapIDArrayP, DWORD *th32ModuleIDArrayP,
								DWORD *cntThreadsArrayP, DWORD *th32ParentProcessIDArrayP,
								DWORD *pcPriClassBaseArrayP, DWORD *dwFlagsArrayP,
								DWORD *dwThreadIDArrayP, HWND *hWndArrayP )
{
	HWND			hWnd;
	DWORD			dwThreadID;

	SAppInfo::getThreadIDAndHWNDFromProcessID( dwProcessID, &dwThreadID, &hWnd );

	*cntUsageArrayP = (DWORD) -1;
	*th32ProcessIDArray = dwProcessID;
	*th32DefaultHeapIDArrayP = (DWORD) -1;
	*th32ModuleIDArrayP = (DWORD) -1;
	*cntThreadsArrayP = (DWORD) 0;
	*th32ParentProcessIDArrayP = (DWORD) -1;
	*pcPriClassBaseArrayP = (DWORD) -1;
	*dwFlagsArrayP = (DWORD) 0;
	*dwThreadIDArrayP = dwThreadID;
	*hWndArrayP = hWnd;
}

CStr *CWinProcessNT::getFullPath()
{
	return new CStr( mainModuleFullPath );
}



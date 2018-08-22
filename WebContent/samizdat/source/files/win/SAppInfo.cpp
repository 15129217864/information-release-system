/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SAppInfo.h"
#include "XToolkit.h"
#include "CFileUtils.h"
#include "CRegUtils.h"
#include "Debugger.h"

ErrCode SAppInfo::iNativeLaunchApp( const CStr *commandLine, AppDataType *appDataP )
{
	long				theErr;

	appDataP->pi.hProcess = NULL;
	appDataP->pi.hThread = NULL;
	appDataP->pecntUsage = (DWORD) -1;
	appDataP->peth32DefaultHeapID = (DWORD) -1;
	appDataP->peth32ModuleID = (DWORD) -1;
	appDataP->pecntThreads = (DWORD) -1;
	appDataP->peth32ParentProcessID = (DWORD) -1;
	appDataP->pepcPriClassBase = -1;
	appDataP->pedwFlags = 0;

	if ( !XToolkit::XCreateProcess( commandLine, STARTF_USESHOWWINDOW, SW_SHOW, &appDataP->pi ) ) {
		Debugger::debug( __LINE__, _TXL( "createProcess" ), commandLine, NULL );
		theErr = kErrCreateProcess;
	}
	else {
		scanForMainHWND( appDataP );
		
		if ( appDataP->pi.hProcess != NULL )
			CloseHandle( appDataP->pi.hProcess );

		if ( appDataP->pi.hThread != NULL )
			CloseHandle( appDataP->pi.hThread );

		appDataP->pi.hProcess = NULL;
		appDataP->pi.hThread = NULL;

		theErr = kErrNoErr;
	}

	return theErr;
}

ErrCode SAppInfo::iNativeMoveApp( AppDataType *appData, eMoveApp selector, long flags )
{
	long		theErr;

	theErr = scanForMainHWND( appData );
	if ( theErr != kErrNoErr )
		goto bail;

	theErr = moveHWND( (HWND) appData->hwnd, selector );

bail:

	return theErr;
}

ErrCode SAppInfo::moveHWND( HWND hWnd, long selector )
{
	long			theErr;

	switch ( selector ) {
		case kMoveAppToFront:
			if ( !SetForegroundWindow( hWnd ) )
				theErr = kErrSetForegroundWindow;
			else
				theErr = kErrNoErr;
		break;

		case kMoveAppToBack:
			if ( !SetWindowPos( hWnd, HWND_BOTTOM, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE ) )
				theErr = kErrSetWindowPos;
			else
				theErr = kErrNoErr;
		break;

		case kMoveAppMinimize:
			if ( !CloseWindow( hWnd ) )
				theErr = kErrCloseWindow;
			else
				theErr = kErrNoErr;
		break;

		case kMoveAppMaximize:
			if ( !ShowWindow( hWnd, SW_SHOWMAXIMIZED ) )
				theErr = kErrShowWindow;
			else
				theErr = kErrNoErr;
		break;

		default:
			theErr = kErrParamErr;
		break;
	}

	return theErr;
}

ErrCode SAppInfo::iNativeQuitApp( AppDataType *appData )
{
	long		theErr;

	theErr = scanForMainHWND( appData );
	if ( theErr != kErrNoErr )
		goto bail;

	PostThreadMessage( appData->pi.dwThreadId, WM_QUIT, (WPARAM) NULL, (LPARAM) NULL );

	theErr = kErrNoErr;

bail:

	return theErr;
}

ErrCode SAppInfo::iNativeVerifyNativeAppData( AppDataType *appData )
{
	return scanForMainHWND( appData );
}

long SAppInfo::moveAppFlagsToSWFlags( eMoveApp flags )
{
	if ( flags == kMoveAppToFront )
		return SW_SHOWNORMAL;
	else if ( flags == kMoveAppToBack )
		return SW_SHOWNOACTIVATE;
	else if ( flags == kMoveAppMinimize )
		return SW_SHOWMINIMIZED;
	else if ( flags == kMoveAppMaximize )
		return SW_SHOWMAXIMIZED;

	return SW_SHOWNORMAL;
}

HWND SAppInfo::getTopWindow( HWND start )
{
	HWND		last;
	
	last = 0;
	while ( start != 0 ) {
		last = start;
		start = GetParent( start );
	}
	
	return last;
}

BOOL CALLBACK SAppInfo::MyEnumThreadWndProc( HWND hwnd, LPARAM lParam )
{
	*( (HWND*) lParam ) = hwnd;

	return FALSE;
}

ErrCode SAppInfo::scanForMainHWND( AppDataType *appData )
{
	DWORD		fred;

	fred = 0;

	EnumThreadWindows( appData->pi.dwThreadId, (WNDENUMPROC) MyEnumThreadWndProc, (LPARAM) &fred );

	if ( fred == 0 )
		Debugger::debug( __LINE__, _TXL( "scanForMainHWND" ), NULL, NULL, fred, 0 );

	appData->hwnd = (HWND) fred;

	if ( fred == 0 )
		return kErrEnumThreadWindows;
	else
		return kErrNoErr;
}

BOOL CALLBACK SAppInfo::MyEnumWindowsProc( HWND hwnd, LPARAM lParam )
{
	MyEnumWindowsProcParam		*lpData;
	DWORD						dwProcessID, dwThreadID;

	lpData = (MyEnumWindowsProcParam*) lParam;

	dwThreadID = GetWindowThreadProcessId( hwnd, &dwProcessID );

	//Debugger::debug( __LINE__, _TXL( "        enum" ), NULL, NULL, dwProcessID, (DWORD) hwnd );

	if ( dwProcessID == lpData->dwInputProcessID ) {
		lpData->dwThreadID = dwThreadID;
		lpData->hwnd = hwnd;

		return FALSE;
	}

	return TRUE;
}

BOOL SAppInfo::getThreadIDAndHWNDFromProcessID( DWORD dwProcessId, DWORD *lpdwThreadId, HWND *lphwndMainWindow )
{
	MyEnumWindowsProcParam		data;
	BOOL						bResult;

	//Debugger::debug( __LINE__, _TXL( "getThreadIDAndHWNDFromProcessID" ), NULL, NULL, dwProcessId, 0 );

	data.dwThreadID = (DWORD) 0;
	data.hwnd = (HWND) 0;
	data.dwInputProcessID = dwProcessId;

	bResult = EnumWindows( (WNDENUMPROC) MyEnumWindowsProc, (LPARAM) &data );

/*
	if ( bResult )
		Debugger::debug( __LINE__, _TXL( "    end, bResult TRUE" ), NULL, NULL, data.dwThreadID, (DWORD) data.hwnd );
	else
		Debugger::debug( __LINE__, _TXL( "    end, bResult FALSE" ), NULL, NULL, data.dwThreadID, (DWORD) data.hwnd );
*/

	*lpdwThreadId = data.dwThreadID;
	*lphwndMainWindow = data.hwnd;

	return bResult;
}


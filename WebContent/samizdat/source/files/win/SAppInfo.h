/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SAppInfo_H
#define	INC_SAppInfo_H

#include "comdefs.h"
#include "CString.h"
#include "CStringVector.h"
#include "AppData.h"

/*------------------------------------------------------------------------
CLASS
	SAppInfo

	Routines for working with applications.

DESCRIPTION
	Routines for working with applications.

------------------------------------------------------------------------*/

class SAppInfo
{
public:
		///////////////////////
		//
		//  Selector value used with iNativeMoveApp()
		//
	typedef enum tageMoveApp {
		kMoveAppToFront = 1,
		kMoveAppToBack = 2,
		kMoveAppMinimize = 3,
		kMoveAppMaximize = 4
	} eMoveApp;
	
		///////////////////////
		//
		//  Launches an app with zero or more documents.
		//
		//  [in]	commandLine			The command line, including any arguments.
		//  [out]	appDataP			On exit, contains information from CreateProcess
		//								no arguments.
		//
	static	ErrCode iNativeLaunchApp( const CStr *commandLine, AppDataType *appDataP );
	
		///////////////////////
		//
		//  Quits an application
		//
		//  [in]	appData			Information on the app.
		//
	static	ErrCode iNativeQuitApp( AppDataType *appData );

		///////////////////////
		//
		//  Moves an application
		//
		//  [in]	appData			Information on the app.
		//  [in]	selector		One of the eMoveApp constants
		//  [in]	flags			Various flags
		//
	static	ErrCode iNativeMoveApp( AppDataType *appData, eMoveApp selector, long flags );

		///////////////////////
		//
		//  Verifies that an application is still running
		//
		//  [in]	appData			Information on the app.
		//
	static	ErrCode iNativeVerifyNativeAppData( AppDataType *appData ); 
	
		///////////////////////
		//
		//  Uses a processID to get the primary HWND and primary thread of the process.
		//	May not be 100% reliable; the process might not have a window,
		//	the thread might not be the main thread, etc. etc.
		//
		//  [in]	dwProcessId			The processID.
		//  [out]	lpdwThreadId		The primary threadID, or -1 if an error occurs.
		//  [out]	lphwndMainWindow	The primary HWND, or -1 if an error occurs.
		//
	static	BOOL getThreadIDAndHWNDFromProcessID( DWORD dwProcessId, DWORD *lpdwThreadId, HWND *lphwndMainWindow );

	static	long moveAppFlagsToSWFlags( eMoveApp flags );

protected:
	static	ErrCode			scanForMainHWND( AppDataType *appData );
	static	BOOL CALLBACK	MyEnumThreadWndProc( HWND hwnd, LPARAM lParam );
	static	BOOL CALLBACK	MyEnumWindowsProc( HWND hwnd, LPARAM lParam );
	static	ErrCode			moveHWND( HWND hWnd, long selector );
	static	HWND			getTopWindow( HWND start );

private:
	typedef struct tagMyEnumWindowsProcParam {
		DWORD		dwThreadID, dwInputProcessID;
		HWND		hwnd;
	} MyEnumWindowsProcParam;

};

#endif

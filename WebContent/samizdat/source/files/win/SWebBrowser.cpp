/****************************************************
	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT
	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement
****************************************************/

#include "SWebBrowser.h"
#include "SAppFinder.h"
#include "CRegUtils.h"
#include "XToolkit.h"
#include "Debugger.h"

#define	FREEHSZ(a,b) { if ( (b) != NULL ) DdeFreeStringHandle( (a), (b) ); } 

const	CStr	*SWebBrowser::gcsHTMExtension = new CStr( _TXL( ".htm" ) );


								//	the three DDE servers we search for
const	CStr	*SWebBrowser::gcsNotscape = new CStr( _TXL( "NETSCAPE" ) );
const	CStr	*SWebBrowser::gcsNotscape2 = new CStr( _TXL( "NSShell" ) );
const	CStr	*SWebBrowser::gcsExploder = new CStr( _TXL( "IEXPLORE" ) );

								//	the DDE messages we send
const	CStr	*SWebBrowser::gcsDDEOpenURLCommand = new CStr( _TXL( "WWW_OpenURL" ) );
const	CStr	*SWebBrowser::gcsDDEActivateCommand = new CStr( _TXL( "WWW_Activate" ) );

								//	parameters for the DDE messages
const	CStr	*SWebBrowser::gcsURLCommandPrefix = new CStr( _TXL( "\"" ) );
const	CStr	*SWebBrowser::gcsURLCommandSuffix = new CStr( _TXL( "\",,0xFFFFFFFF,0x00000000,,,," ) );
const	CStr	*SWebBrowser::gcsActivateParms = new CStr( _TXL( "0xFFFFFFFF,0x00000000" ) );

/*
First, try to launch the URL using DDE.If that doesn't work, launch using CreateProcess or ShellExecute.
As a last attempt, launch using WinExec.
*/

ErrCode SWebBrowser::iNativeLaunchURL( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags )
{
	CStringVector		htmlAppStringVector( 1 );
	CStr				*csHTMLApp = NULL;
	long				theErr, attemptLevel;
	
	attemptLevel = 0;
	
	if ( csURL == NULL || csTempDir == NULL ) {
		theErr = kErrParamErr;
		goto bail;
	}
	
	//	find out which app is registered for .html files
	
	theErr = SAppFinder::iNativeFindAppsByExtension( gcsHTMExtension, csTempDir, 1, 0, &htmlAppStringVector );
	if ( theErr == kErrNoErr && htmlAppStringVector.getNumStrings() > 0 ) {
		csHTMLApp = htmlAppStringVector.getString( 0 );
		attemptLevel = 1;
	}
	else {
		csHTMLApp = NULL;
		attemptLevel = 2;
	}
	
	theErr = launchDDE( csURL, csTempDir, flags, csHTMLApp );
	if ( theErr == kErrNoErr ) {
		attemptLevel = 3;
		goto bail;
	}
	
	theErr = launchCP( csURL, csTempDir, flags, csHTMLApp );
	if ( theErr == kErrNoErr ) {
		attemptLevel = 4;
		goto bail;
	}
	
	theErr = launchSE( csURL, csTempDir, flags, csHTMLApp );
	if ( theErr == kErrNoErr ) {
		attemptLevel = 5;
		goto bail;
	}
	
	theErr = launchWE( csURL, csTempDir, flags, csHTMLApp );
	if ( theErr != kErrNoErr ) {
		attemptLevel = 6;
		goto bail;
	}

//moreDDE:

//	SleepEx( 100, TRUE );
	
//	launchDDE( csURL, csTempDir, flags, csHTMLApp );

bail:
	
	Debugger::debug( __LINE__, _TXL( "launchURL" ), csURL, NULL, theErr, attemptLevel );
	
	return theErr;

}

/*
Launch using CreateProcess
*/

ErrCode SWebBrowser::launchCP( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags, const CStr *csHTMLApp )
{
	CStr					*csCommand = NULL;
	PROCESS_INFORMATION		processInfo;
	ErrCode					theErr;
	
	if ( csHTMLApp == NULL )
		return kErrParamErr;
	
	csCommand = new CStr( csHTMLApp->getCharCapacity() + csURL->getCharCapacity() + 50 );
	csCommand->setBuf( _TXL( "\"" ) );
	csCommand->concat( csHTMLApp );
	csCommand->concat( _TXL( "\" \"" ) );
	csCommand->concat( csURL );
	csCommand->concat( _TXL( "\"" ) );
	
	if ( XToolkit::XCreateProcess( csCommand, STARTF_USESHOWWINDOW,
		(WORD) launchFlagsToSWFlags( flags ), &processInfo ) )
		theErr = kErrNoErr;
	else
		theErr = kErrCreateProcess;
	
	delete csCommand;

	return theErr;
}

/*
Launch using DDE
*/

ErrCode SWebBrowser::launchDDE( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags, const CStr *csHTMLApp )
{
	UINT				nResult;
	long				theErr;
	DWORD				dwDDE;
	HWND				targetHWND;
	CStr				*csTempHTMLApp, *csCommand = NULL;
	const CStr			*csServiceName1, *csServiceName2, *csServiceName3;
	
	dwDDE = 0;
	
	//	construct the DDE open URL command
	csCommand = new CStr( gcsURLCommandPrefix->getCharCapacity() +
		csURL->getCharCapacity() +
		gcsURLCommandSuffix->getCharCapacity() + 50 );
	
	csCommand->setBuf( gcsURLCommandPrefix );
	csCommand->concat( csURL );
	csCommand->concat( gcsURLCommandSuffix );
	
	//	we look for 3 DDE servers, and we assume the default browser is IE...
	csServiceName1 = gcsExploder;
	csServiceName2 = gcsNotscape;
	csServiceName3 = gcsNotscape2;
	
	//	however, if Netscrape is registered to handle shortcuts,
	//	rearrange csServiceName1, etc. so we look for it first
	if ( csHTMLApp != NULL ) {
		csTempHTMLApp = new CStr( csHTMLApp );
		csTempHTMLApp->toUpper();
		if ( csTempHTMLApp->contains( gcsNotscape ) ) {
			csServiceName1 = gcsNotscape;
			csServiceName2 = gcsNotscape2;
			csServiceName3 = gcsExploder;
		}
	}
	
	nResult = DdeInitialize( &dwDDE, NULL, APPCMD_CLIENTONLY | CBF_SKIP_ALLNOTIFICATIONS, 0 );
	if ( nResult != DMLERR_NO_ERROR ) {
		Debugger::debug( __LINE__, _TXL( "launchD, init" ), NULL, NULL, nResult, 0 );
		theErr = kErrDDEInitialize;
		goto bail;
	}
	
	//	look for each of the three servers in order
	theErr = ddeHelper( dwDDE, csCommand, csServiceName1, &targetHWND );
	if ( theErr != kErrNoErr )
		theErr = ddeHelper( dwDDE, csCommand, csServiceName2, &targetHWND );
	if ( theErr != kErrNoErr )
		theErr = ddeHelper( dwDDE, csCommand, csServiceName3, &targetHWND );
	
	DdeUninitialize( dwDDE );

bail:
	
	if ( csCommand != NULL )
		delete csCommand;
	
	//	Debugger::debug( __LINE__, _TXL( "launchD" ), NULL, NULL, theErr, 0 );
	
	return theErr;
}

/*
Launch using ShellExecute
*/

ErrCode SWebBrowser::launchSE( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags, const CStr *csHTMLApp )
{
	HINSTANCE		hInstance;
	long			theErr;
	
	//Debugger::debug( __LINE__, _TXL( "launchSE1" ), csURL, csTempDir, SAppInfo::moveAppFlagsToSWFlags( flags ), 0 );
	
	hInstance = XToolkit::XShellExecute( 0, NULL, csURL, NULL, NULL, launchFlagsToSWFlags( flags ) );
	
	//	Debugger::debug( __LINE__, _TXL( "launchS" ), NULL, NULL, (long) hInstance, 0 );
	
	if ( hInstance <= (HINSTANCE) 32 )
		theErr = kErrShellExecute;
	else
		theErr = kErrNoErr;
	
	return theErr;
}

/*
Launch using WinExec
*/

ErrCode SWebBrowser::launchWE( const CStr *csURL, const CStr *csTempDir, eLaunchURLFlags flags, const CStr *csHTMLApp )
{
	CStr		csKey( CStr::kMaxPath );
	long		theErr, newIndex;
	
	theErr = kErrNoErr;
	
	if ( CRegUtils::getRegKey( HKEY_CLASSES_ROOT, gcsHTMExtension, &csKey ) != ERROR_SUCCESS ) {
		theErr = kErrWinExec;
		goto bail;
	}
	
	csKey.concat( _TXL( "\\shell\\open\\command" ) );
	
	if ( CRegUtils::getRegKey( HKEY_CLASSES_ROOT, &csKey, &csKey ) != ERROR_SUCCESS ) {
		theErr = kErrWinExec;
		goto bail;
	}
	
	newIndex = csKey.replaceFrom( 0, _TXL("%1"), csURL );
	if ( newIndex < 0 ) {
		csKey.concat( _TXL( " " ) );
		csKey.concat( csURL );
	}
	
	if ( XToolkit::XWinExec( &csKey, SW_SHOWNORMAL ) > 31 )
		theErr = kErrWinExec;

bail:
	
	//	Debugger::debug( __LINE__, _TXL( "launchW" ), NULL, NULL, theErr, 0 );
	
	return theErr;
}

/*
Send an activate and open url command to the server indicated by 'csServiceName'
'csCommand' contains the parameters for the open url command
*/

ErrCode SWebBrowser::ddeHelper( DWORD dwDDE, const CStr *csCommand, const CStr *csServiceName, HWND *pTargetHWND )
{
	HSZ			hszServName = NULL, hszActivateTopic = NULL, hszOpenURLTopic = NULL;
	long		theErr;
	
	theErr = kErrNoErr;
	*pTargetHWND = 0;
	hszServName = XToolkit::XDdeCreateStringHandle( dwDDE, csServiceName, kDDECodePage );
	if ( hszServName == NULL ) {
			
		theErr = kErrDdeCreateStringHandle;
		Debugger::debug( __LINE__, _TXL( "ddH, hserv=NULL" ), csServiceName, NULL );
		goto bail;
	}
	
	hszActivateTopic = XToolkit::XDdeCreateStringHandle( dwDDE, gcsDDEActivateCommand, kDDECodePage );
	if ( hszActivateTopic == NULL ) {
		theErr = kErrDdeCreateStringHandle;
		Debugger::debug( __LINE__, _TXL( "ddH, htop=NULL for ActCmd" ) );
		goto bail;
	}
	
	hszOpenURLTopic = XToolkit::XDdeCreateStringHandle( dwDDE, gcsDDEOpenURLCommand, kDDECodePage );
	if ( hszOpenURLTopic == NULL ) {
		theErr = kErrDdeCreateStringHandle;
		Debugger::debug( __LINE__, _TXL( "ddH, htop=NULL for OURL" ) );
		goto bail;
	}
	
	theErr = ddeConnect( dwDDE, hszServName, hszActivateTopic, gcsActivateParms );
	if ( theErr != kErrNoErr ) {
		//		Debugger::debug( __LINE__, _TXL( "ddH, act" ), csServiceName, NULL, theErr, 0 );
		goto bail;
	}
	
	theErr = ddeConnect( dwDDE, hszServName, hszOpenURLTopic, csCommand );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, _TXL( "ddH, url" ), csServiceName, NULL, theErr, 0 );

 bail:
	
	FREEHSZ( dwDDE, hszActivateTopic );
	FREEHSZ( dwDDE, hszOpenURLTopic );
	FREEHSZ( dwDDE, hszServName );
	
	return theErr;
}

/*
Send a DDE command.
*/

ErrCode SWebBrowser::ddeConnect( DWORD dwDDE, HSZ hszServName, HSZ hszTopic, const CStr *csCommand )
{
	HCONV		hConv = NULL;
	HSZ			hszItem = NULL;
	long		theErr = kErrNoErr;
	
	hConv = DdeConnect( dwDDE, hszServName, hszTopic, NULL );
	if ( hConv == NULL ) {
		theErr = kErrDdeConnect;
		goto bail;
	}
	
	hszItem = XToolkit::XDdeCreateStringHandle( dwDDE, csCommand, kDDECodePage );
	if ( hszItem == NULL ) {
		theErr = kErrDdeCreateStringHandle;
		Debugger::debug( __LINE__, _TXL( "ddC, htem=NULL" ), csCommand, NULL );
		goto bail;
	}
	
	//	Debugger::debug( __LINE__, _TXL( "ddC..." ) );
	
	DdeClientTransaction( NULL, 0, hConv, hszItem, CF_TEXT, XTYP_REQUEST, TIMEOUT_ASYNC, NULL );//20000, NULL );
	
	//	Debugger::debug( __LINE__, _TXL( "    done" ), csCommand, NULL, DdeGetLastError( dwDDE ), 0 );

bail:
	
	FREEHSZ( dwDDE, hszItem );
	
	if ( hConv != NULL )
		DdeDisconnect( hConv );
	
	return theErr;

}

long SWebBrowser::launchFlagsToSWFlags( eLaunchURLFlags flags )
{
	if ( flags == kLaunchToFront )
		return SW_SHOWNORMAL;
	else if ( flags == kLaunchToBack )
		return SW_SHOWNOACTIVATE;
	else if ( flags == kLaunchMinimize )
		return SW_SHOWMINIMIZED;
	else if ( flags == kLaunchMaximize )
		return SW_SHOWMAXIMIZED;
	return SW_SHOWNORMAL;
}

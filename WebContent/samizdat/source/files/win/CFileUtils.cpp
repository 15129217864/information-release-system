/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CFileUtils.h"
#include "XToolkit.h"
#include <shlobj.h>
#include "Debugger.h"

#if defined(UNICODE)
	#ifndef SHGetPathFromIDListW
		#define	SHGetPathFromIDList	SHGetPathFromIDListW
		WINSHELLAPI BOOL WINAPI SHGetPathFromIDListW(LPCITEMIDLIST pidl, LPWSTR pszPath);
	#endif
#endif

BOOL CFileUtils::exists( const CStr *csFullPath )
{
	CStr		csTempFullPath( csFullPath ), csUnused( CStr::kMaxPath );
	HANDLE		hFind;
	DWORD		dwAttrs;
	UINT		uiOldErrorMode;

	uiOldErrorMode = SetErrorMode( SEM_NOOPENFILEERRORBOX | SEM_FAILCRITICALERRORS );

	hFind = XToolkit::XFindFirstFile( &csTempFullPath, &dwAttrs, &csUnused );

	SetErrorMode( uiOldErrorMode );

	if ( hFind == INVALID_HANDLE_VALUE )
		return FALSE;

	FindClose( hFind );

	return TRUE;
}

BOOL CFileUtils::createTempFile( const CStr *csTempPath, const CStr *csExt, CStr *csOutFilePath )
{
	CStr			csNumber( 32 );
	long			i, x1, x2;
	HANDLE			hTempFile;
	BOOL			bRet;

	x1 = GetTickCount();
	bRet = FALSE;

	csOutFilePath->ensureCharCapacity( csTempPath->getCharCapacity() + csExt->getCharCapacity() + 50 );

	for ( i = 0; i < 1000; i++ ) {
		x1 = x1 * 1103515245 + 12345;
		x2 = x1 & 9999999;

		csNumber.formatInt( _TXL( "A%7.7ld" ), x2 );
		csOutFilePath->setBuf( csTempPath );
		csOutFilePath->concat( &csNumber );
		csOutFilePath->concat( csExt );

		hTempFile = XToolkit::XCreateFile( csOutFilePath, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ,
								NULL, CREATE_NEW, FILE_ATTRIBUTE_NORMAL, 0 );
		if ( hTempFile != INVALID_HANDLE_VALUE ) {
			CloseHandle( hTempFile );
			bRet = TRUE;
			break;
		}
	}

	return bRet;
}

ErrCode CFileUtils::deleteFile( const CStr *csFullPath )
{
	if ( XToolkit::XDeleteFile( csFullPath ) )
		return kErrNoErr;
	
	return GetLastError();
}

BOOL CFileUtils::isMountedDrive( const CStr *driveName )
{
	CStr				csTest( driveName );

	csTest.concat( _TXL( "*.*" ) );
	
	return exists( &csTest );
}

/*
0								Nonexecutable file or an error condition.

LOWORD = NE or PE				Windows application
HIWORD = 3.0, 3.5, or 4.0

LOWORD = MZ						MS-DOS .EXE, .COM or .BAT file
HIWORD = 0

LOWORD = PE						Win32 console application
HIWORD = 0
*/

BOOL CFileUtils::isExeFile( const CStr *csFullPath )
{
	DWORD		dwType;

	dwType = XToolkit::XSHGetFileInfoExeType( csFullPath );
	
	return ( dwType != 0 );
}

ErrCode CFileUtils::makeLongFileName( const CStr *csShortFileName, CStr *longFileName )
{
	LPITEMIDLIST		pidl;
	LPSHELLFOLDER		pDesktopFolder;
	ULONG				chEaten, dwAttr;
	HRESULT				hr;
	IMalloc				*mem;
	BOOL				bRet;

	hr = SHGetDesktopFolder( &pDesktopFolder );
	if ( FAILED( hr ) ) {
		Debugger::debug( __LINE__, _TXL( "can't get desktop" ) );
		return kErrSHGetDesktopFolder;
	}

#ifndef UNICODE
	CStrW		*csTempShortNameW;
	csTempShortNameW = new CStrW( csShortFileName->getBuf() );
	hr = pDesktopFolder->ParseDisplayName( NULL, NULL, csTempShortNameW->getBuf(), &chEaten, &pidl, &dwAttr );
	delete csTempShortNameW;
#else
	hr = pDesktopFolder->ParseDisplayName( NULL, NULL, csShortFileName->getBuf(), &chEaten, &pidl, &dwAttr );
#endif

	if ( FAILED( hr ) ) {
		Debugger::debug( __LINE__, _TXL( "can't parse" ) );
		return kErrParseDisplayName;
	}

	longFileName->ensureCharCapacity( CStr::kMaxPath );
	bRet = XToolkit::XSHGetPathFromIDList( pidl, longFileName );
	if ( !bRet ) {
		Debugger::debug( __LINE__, _TXL( "can't SHGetPathFromIDList" ) );
		return kErrSHGetPathFromIDList;
	}

	hr = SHGetMalloc( &mem );
	if ( SUCCEEDED( hr ) ) {
		mem->Free( pidl );
		mem->Release();
	}

	pDesktopFolder->Release();
	
	return kErrNoErr;
}

ErrCode CFileUtils::getFileTime( const CStr *fullPath, FILETIME *crDate, FILETIME *acDate, FILETIME *mdDate )
{
	CStr			csTempFullPath( fullPath ), csUnused( CStr::kMaxPath );
	HANDLE			hFind;

	hFind = XToolkit::XFindFirstFileGetDates( &csTempFullPath, crDate, acDate, mdDate );
	if ( hFind == INVALID_HANDLE_VALUE ) {
		Debugger::debug( __LINE__, _TXL( "cfu.gft" ), &csTempFullPath, NULL );
		return GetLastError();
	}

	FindClose( hFind );

	return kErrNoErr;
}

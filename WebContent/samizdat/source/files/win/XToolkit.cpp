/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "XToolkit.h"
#include "unicodeOn95.h"
#include "Debugger.h"

//	some WinAPI routines which take a character buffer length return the length which
//	the buffer must be if the length is too short. If this happens, and the suggested
//	length is shorter than this value, we up the buffer size, and try again

enum {
	kMaxLegitStringRetryLength = 2000
};

BOOL XToolkit::XCreateProcess( const CStr *csCommandLine, DWORD dwFlags, WORD wShowWindow, LPPROCESS_INFORMATION lpPI )
{
	STARTUPINFO		startUpInfo;

	if ( csCommandLine == NULL )
		return kErrParamErr;

	startUpInfo.cb = sizeof(STARTUPINFO);
	startUpInfo.lpReserved = NULL;
	startUpInfo.lpDesktop = NULL;
	startUpInfo.lpTitle = NULL;
	startUpInfo.dwFlags = dwFlags;
	startUpInfo.wShowWindow = wShowWindow;
	startUpInfo.cbReserved2 = 0;
	startUpInfo.lpReserved2 = NULL;
	
	return CreateProcess( NULL, csCommandLine->getBuf(), NULL, NULL, TRUE, 0, NULL, NULL, &startUpInfo, lpPI );
}

ErrCode XToolkit::XGetLogicalDriveStrings( CStr *toStr )
{
	DWORD		dwRes;
	UINT		uiOldErrorMode;

	if ( toStr == NULL )
		return kErrParamErr;

	uiOldErrorMode = SetErrorMode( SEM_NOOPENFILEERRORBOX | SEM_FAILCRITICALERRORS );

	dwRes = GetLogicalDriveStrings( toStr->getCharCapacity(), toStr->getBuf() );

	SetErrorMode( uiOldErrorMode );

	if ( dwRes == 0 )
		return GetLastError();

	if ( dwRes > kMaxLegitStringRetryLength )
		return kErrShortString;

	if ( dwRes <= (DWORD) toStr->getCharCapacity() )
		return kErrNoErr;

		//	the toStr buffer was too small, try again, but only if the requested length seems legit

	toStr->ensureCharCapacity( dwRes + 4 );

	uiOldErrorMode = SetErrorMode( SEM_NOOPENFILEERRORBOX | SEM_FAILCRITICALERRORS );

	dwRes = GetLogicalDriveStrings( toStr->getCharCapacity(), toStr->getBuf() );

	SetErrorMode( uiOldErrorMode );

	if ( dwRes == 0 )
		return GetLastError();

	if ( dwRes > (DWORD) toStr->getCharCapacity() )
		return kErrShortString;

	return kErrNoErr;
}

BOOL XToolkit::XGetShortPathName( const CStr *fullPath, CStr *shortPath )
{
	CStr		tempFullPath( fullPath );
	DWORD		dwRes;

	if ( fullPath == NULL || shortPath == NULL )
		return FALSE;

	dwRes = GetShortPathName( tempFullPath.getBuf(), shortPath->getBuf(), shortPath->getCharCapacity() );

	if ( dwRes == 0 )
		return FALSE;

	if ( dwRes <= (DWORD) shortPath->getCharCapacity() )
		return TRUE;

		//	the shortPath buffer was too small, try again, but only if the requested length seems legit

	if ( dwRes > kMaxLegitStringRetryLength )
		return FALSE;

	shortPath->ensureCharCapacity( dwRes + 4 );

	dwRes = GetShortPathName( tempFullPath.getBuf(), shortPath->getBuf(), shortPath->getCharCapacity() );

	if ( dwRes == 0 || dwRes > (DWORD) shortPath->getCharCapacity() )
		return FALSE;

	return TRUE;
}

#if defined(USING_UNICODE_ON_W95_FOR_TESTING_ONLY)

	/**
	Some WinAPI routines, such as SHGetPathFromIDList(), aren't available in wide versions on Win95.
	So, if USING_UNICODE_ON_W95_FOR_TESTING_ONLY is defined, we have to do some conversions to/from
	CStrW's to work with the A versions of some WinAPI routines.
	*/

	BOOL XToolkit::XSHGetPathFromIDList( LPCITEMIDLIST pidl, CStr *csLongFileName )
	{
		CStrA			csTemp( CStr::kMaxPath );
		CStrW			*dn;
		BOOL			bRet;

		bRet = SHGetPathFromIDListA( pidl, csTemp.getBuf() );
		
		dn = new CStrW( csTemp.getBuf() );
		csLongFileName->setBuf( dn->getBuf() );
		delete dn;

		return bRet;
	}
	
	HIMAGELIST XToolkit::XSHGetFileInfoIcon( const CStr *csName, DWORD dwFileAttr, DWORD dwFlags, long *iconID )
	{
		HIMAGELIST		hList;
		SHFILEINFOA		shfi;
		CStrA			csTemp( csName->getBuf() );

		shfi.szDisplayName[ 0 ] = 0;
		hList = (HIMAGELIST) SHGetFileInfoA( csTemp.getBuf(), 0, &shfi, sizeof(SHFILEINFOA), dwFlags );
		if ( hList == NULL )
			return NULL;
	
		*iconID = shfi.iIcon;
	
		return hList;
	}

	DWORD XToolkit::XGetDriveDisplayName( const CStr *csDriveName, DWORD dwFileAttributes, CStr *csDisplayName )
	{
		SHFILEINFOA		shfi;
		DWORD			dwRet;
		CStrA			csTemp( csDriveName->getBuf() );
		CStrW			*dn;

		shfi.szDisplayName[ 0 ] = 0;
		dwRet = SHGetFileInfoA( csTemp.getBuf(), 0, &shfi, sizeof(SHFILEINFOA), dwFileAttributes );
		if ( !( (BOOL) dwRet ) )
			return dwRet;
		
		dn = new CStrW( &shfi.szDisplayName[ 0 ] );
		csDisplayName->setBuf( dn->getBuf() );
		delete dn;

		return dwRet;
	}

	
	DWORD XToolkit::XSHGetFileInfoExeType( const CStr *fileName )
	{
		SHFILEINFOA		shfi;
		CStrA			csTemp( fileName->getBuf() );

		return SHGetFileInfoA( csTemp.getBuf(), 0, &shfi, sizeof(SHFILEINFO), SHGFI_EXETYPE );
	}

	HINSTANCE XToolkit::XFindExecutable( const CStr *csDocFile, const CStr *csDirectory, CStr *csExeFullPath )
	{
		CStrA			*csDocA, *csDirA, *csExeA;
		CStrW			*ww;
		HINSTANCE		retH;

		csDocA = ( csDocFile == NULL ? NULL : new CStrA( csDocFile->getBuf() ) );
		csDirA = ( csDirectory == NULL ? NULL : new CStrA( csDirectory->getBuf() ) );
		csExeA = new CStrA( CStr::kMaxPath );

		retH = FindExecutableA( ( csDocA == NULL ? NULL : csDocA->getBuf() ),
									( csDirA == NULL ? NULL : csDirA->getBuf() ),
									csExeA->getBuf() );

		ww = new CStrW( csExeA->getBuf() );
		csExeFullPath->setBuf( ww );

		return retH;
	}

#else

	BOOL XToolkit::XSHGetPathFromIDList( LPCITEMIDLIST pidl, CStr *csLongFileName )
	{
		if ( csLongFileName == NULL )
			return FALSE;

		csLongFileName->ensureCharCapacity( CStr::kMaxPath );
		
		return SHGetPathFromIDList( pidl, csLongFileName->getBuf() );
	}
	
	HIMAGELIST XToolkit::XSHGetFileInfoIcon( const CStr *csName, DWORD dwFileAttr, DWORD dwFlags, long *iconID )
	{
		HIMAGELIST		hList;
		SHFILEINFO		shfi;
		
		if ( csName == NULL )
			return NULL;

		shfi.szDisplayName[ 0 ] = 0;
		hList = (HIMAGELIST) SHGetFileInfo( csName->getBuf(), 0, &shfi, sizeof(SHFILEINFO), dwFlags );
		if ( hList == NULL )
			return NULL;
	
		*iconID = shfi.iIcon;
	
		return hList;
	}

	DWORD XToolkit::XGetDriveDisplayName( const CStr *csDriveName, DWORD dwFileAttributes, CStr *csDisplayName )
	{
		SHFILEINFO		shfi;
		DWORD			dwRet;
		
		if ( csDriveName == NULL || csDisplayName == NULL )
			return NULL;

		shfi.szDisplayName[ 0 ] = 0;
		dwRet = SHGetFileInfo( csDriveName->getBuf(), 0, &shfi, sizeof(SHFILEINFO), dwFileAttributes );
		if ( !( (BOOL) dwRet ) )
			return dwRet;
		
		csDisplayName->setBuf( &shfi.szDisplayName[ 0 ] );
		
		return dwRet;
	}
	
	DWORD XToolkit::XSHGetFileInfoExeType( const CStr *fileName )
	{
		SHFILEINFO		shfi;

		if ( fileName == NULL )
			return 0;

		return SHGetFileInfo( fileName->getBuf(), 0, &shfi, sizeof(SHFILEINFO), SHGFI_EXETYPE );
	}

	HINSTANCE XToolkit::XFindExecutable( const CStr *csDocFile, const CStr *csDirectory, CStr *csExeFullPath )
	{
		if ( csExeFullPath == NULL )
			return NULL;

		csExeFullPath->ensureCharCapacity( CStr::kMaxPath );
		
		return FindExecutable( ( csDocFile == NULL ? NULL : csDocFile->getBuf() ),
								( csDirectory == NULL ? NULL : csDirectory->getBuf() ),
								csExeFullPath->getBuf() );	
	}

#endif

BOOL XToolkit::XGetVolumeInformation(
const CStr *csDriveName,
CStr *csOutVolName,
LPDWORD lpVolumeSerialNumber,
LPDWORD lpMaximumComponentLength,
LPDWORD lpFileSystemFlags,
CStr *csFileSystemName )
{
	return GetVolumeInformation( ( csDriveName == NULL ? NULL : csDriveName->getBuf() ),
									( csOutVolName == NULL ? NULL : csOutVolName->getBuf() ),
									( csOutVolName == NULL ? 0 : csOutVolName->getCharCapacity() ),
									lpVolumeSerialNumber,
									lpMaximumComponentLength,
									lpFileSystemFlags,
									( csFileSystemName == NULL ? NULL : csFileSystemName->getBuf() ),
									( csFileSystemName == NULL ? 0 : csFileSystemName->getCharCapacity() ) );
}

DWORD XToolkit::XGetFileAttributes( const CStr *csFullPath )
{
	if ( csFullPath == NULL )
		return kErrParamErr;

	return GetFileAttributes( csFullPath->getBuf() );
}

HINSTANCE XToolkit::XShellExecute( HWND hwnd, const CStr *csOp, const CStr *csFile, const CStr *csParams, const CStr *csDir, INT nShowCmd )
{
	return ShellExecute( hwnd,
							( csOp == NULL ? NULL : csOp->getBuf() ), 
							( csFile == NULL ? NULL : csFile->getBuf() ), 
							( csParams == NULL ? NULL : csParams->getBuf() ),
							( csDir == NULL ? NULL : csDir->getBuf() ),
							nShowCmd );
}

UINT XToolkit::XWinExec( const CStr *csCmdLine, UINT uCmdShow )
{
	if ( csCmdLine == NULL )
		return kErrParamErr;

#if defined(UNICODE)

	CStrA		*csCmdA;
	UINT		retVal;

	csCmdA = new CStrA( csCmdLine->getBuf() );

	retVal = WinExec( csCmdA->getBuf(), uCmdShow );

	delete csCmdA;

	return retVal;

#else

	return WinExec( csCmdLine->getBuf(), uCmdShow );

#endif
}

HSZ XToolkit::XDdeCreateStringHandle( DWORD idInst, const CStr *csStr, int iCodePage )
{
	if ( csStr == NULL )
		return NULL;

	return DdeCreateStringHandle( idInst, csStr->getBuf(), iCodePage );
}

HANDLE XToolkit::XFindFirstFile( const CStr *csSearchString, DWORD *dwAttrs, CStr *csFileName )
{
	WIN32_FIND_DATA		findData;
	HANDLE				hRet;

	if ( csSearchString == NULL || csFileName == NULL )
		return INVALID_HANDLE_VALUE;

	hRet = FindFirstFile( csSearchString->getBuf(), &findData );
	if ( hRet == INVALID_HANDLE_VALUE )
		return hRet;

	*dwAttrs = (long) findData.dwFileAttributes;
	csFileName->setBuf( &( findData.cFileName[ 0 ] ) );

	return hRet;
}

HANDLE XToolkit::XFindFirstFileGetDates( const CStr *csSearchString,
											FILETIME *crDate, FILETIME *acDate, FILETIME *mdDate )
{
	WIN32_FIND_DATA		findData;
	HANDLE				hRet;

	if ( csSearchString == NULL )
		return INVALID_HANDLE_VALUE;

	hRet = FindFirstFile( csSearchString->getBuf(), &findData );
	if ( hRet == INVALID_HANDLE_VALUE )
		return hRet;

    *crDate = findData.ftCreationTime;
    *acDate = findData.ftLastAccessTime; 
    *mdDate = findData.ftLastWriteTime; 

	return hRet;
}

BOOL XToolkit::XFindNextFile( HANDLE findH, DWORD *dwAttrs, CStr *csFileName )
{
	WIN32_FIND_DATA		findData;
	BOOL				bRet;

	if ( csFileName == NULL )
		return FALSE;

	bRet = FindNextFile( (HANDLE) findH, &findData );
	if ( !bRet )
		return bRet;
	
	*dwAttrs = (long) findData.dwFileAttributes;
	csFileName->setBuf( &( findData.cFileName[ 0 ] ) );

	return bRet;
}

BOOL XToolkit::XDeleteFile( const CStr *csFullPath )
{
	if ( csFullPath == NULL )
		return FALSE;

	return DeleteFile( csFullPath->getBuf() );
}

HANDLE XToolkit::XCreateFile( const CStr *csFileName, DWORD dwDesiredAccess, DWORD dwShareMode,
								LPSECURITY_ATTRIBUTES lpSecurityAttributes, DWORD dwCreationDistribution,
								DWORD dwFlagsAndAttributes, HANDLE hTemplateFile )
{
	if ( csFileName == NULL )
		return NULL;

	return CreateFile( csFileName->getBuf(), dwDesiredAccess, dwShareMode,
								lpSecurityAttributes, dwCreationDistribution,
								dwFlagsAndAttributes, hTemplateFile );
}


ErrCode XToolkit::XRegQueryValueEx( HKEY hKey, const CStr *csSubKey, CStr *csValue )
{
	DWORD		strLen, type;
	long		retVal;
	
	if ( csValue == NULL )
		return kErrParamErr;

	strLen = csValue->getByteCapacity();

	retVal = RegQueryValueEx( hKey,
							( csSubKey == NULL ? NULL : csSubKey->getBuf() ),
							NULL,
							&type,
							(LPBYTE) csValue->getBuf(),
							&strLen );

	if ( retVal != ERROR_MORE_DATA ) {
		return retVal;
	}

	if ( strLen < 0 || strLen > kMaxLegitStringRetryLength ) {
		return kErrShortString;
	}

	csValue->ensureCharCapacity( 2 * strLen + 20 );
	strLen = csValue->getByteCapacity();

	return RegQueryValueEx( hKey,
							( csSubKey == NULL ? NULL : csSubKey->getBuf() ),
							NULL,
							&type,
							(LPBYTE) csValue->getBuf(),
							&strLen );
}

ErrCode XToolkit::XRegQueryValue( HKEY hKey, const CStr *csSubKey, CStr *csValue )
{
	long		strLen, retVal;
	
	if ( csValue == NULL )
		return kErrParamErr;

	strLen = csValue->getByteCapacity();

	retVal = RegQueryValue( hKey,
							( csSubKey == NULL ? NULL : csSubKey->getBuf() ),
							csValue->getBuf(),
							&strLen );

	if ( retVal != ERROR_MORE_DATA )
		return retVal;

	if ( strLen < 0 || strLen > kMaxLegitStringRetryLength )
		return kErrShortString;

	csValue->ensureCharCapacity( 2 * strLen + 20 );
	strLen = csValue->getByteCapacity();

	return RegQueryValue( hKey,
							( csSubKey == NULL ? NULL : csSubKey->getBuf() ),
							csValue->getBuf(),
							&strLen );
}

ErrCode XToolkit::XRegOpenKeyEx( HKEY hKey, const CStr *csSubKey, DWORD ulOptions, REGSAM samDesired, PHKEY phkResult )
{
	return RegOpenKeyEx( hKey,
							( csSubKey == NULL ? NULL : csSubKey->getBuf() ),
							ulOptions,
							samDesired,
							phkResult );
}

ErrCode XToolkit::XRegEnumKey( HKEY hKey, DWORD dwIndex, CStr *csName )
{
	if ( csName == NULL )
		return kErrParamErr;

	csName->ensureCharCapacity( CStr::kMaxPath );

	return RegEnumKey( hKey, dwIndex, csName->getBuf(), csName->getCharCapacity() );
}
  
DWORD XToolkit::XGetDriveType( const CStr *csDriveName )
{
	if ( csDriveName == NULL )
		return 0;

	return GetDriveType( csDriveName->getBuf() );
}

BOOL XToolkit::XGetDiskFreeSpace( const CStr *csDriveName, DWORD *sectorsPerClst, DWORD *bytesPerSector,
									DWORD *numFreeClst, DWORD *totNumClst )
{
	if ( csDriveName == NULL )
		return FALSE;

	return GetDiskFreeSpace( csDriveName->getBuf(), sectorsPerClst, bytesPerSector, numFreeClst, totNumClst );
}

DWORD XToolkit::XGetFileVersionInfoSize( const CStr *csFileName, LPDWORD lpdwHandle )
{
	CStr		csTempFileName( csFileName );

	return GetFileVersionInfoSize( csTempFileName.getBuf(), lpdwHandle );
}

BOOL XToolkit::XGetFileVersionInfo( const CStr *csFileName, DWORD dwHandle, DWORD dwLen, LPVOID lpData )
{
	CStr		csTempFileName( csFileName );

	return GetFileVersionInfo( csTempFileName.getBuf(), dwHandle, dwLen, lpData );
}

BOOL XToolkit::XVerQueryValue( const LPVOID pBlock, const CStr *csSubBlock, LPVOID *lplpBuffer, PUINT puLen )
{
	CStr		csTempSubBlock( csSubBlock );

	return VerQueryValue( pBlock, csTempSubBlock.getBuf(), lplpBuffer, puLen );
}


		//	these would normally be enclosed in _TXL() blocks,
		//	but GetProcAddress doesn't come in a W version
#if defined(UNICODE)
	#define		kGetDiskFreeSpaceExName			"GetDiskFreeSpaceExW"
#else
	#define		kGetDiskFreeSpaceExName			"GetDiskFreeSpaceExA"
#endif

#define			kDLLWithGetDiskFreeSpaceEx		_TXL( "kernel32.dll" )

typedef BOOL (WINAPI *P_GDFSE)(LPCTSTR, PULARGE_INTEGER, PULARGE_INTEGER, PULARGE_INTEGER);

BOOL XToolkit::XGetDiskFreeSpace2( const CStr *csDriveName, unsigned __int64 *totalCapP,
									unsigned __int64 *totalFreeP, unsigned __int64 *userCapP,
									unsigned __int64 *userFreeP )
{
	DWORD			dwSectPerClust, dwBytesPerSect, dwFreeClusters, dwTotalClusters;
	P_GDFSE			pGetDiskFreeSpaceEx = NULL;
	BOOL			bRet;

	pGetDiskFreeSpaceEx = (P_GDFSE) GetProcAddress( GetModuleHandle( kDLLWithGetDiskFreeSpaceEx ), kGetDiskFreeSpaceExName );

	if ( pGetDiskFreeSpaceEx != NULL ) {
		bRet = pGetDiskFreeSpaceEx( csDriveName->getBuf(),
										(PULARGE_INTEGER) userFreeP,
										(PULARGE_INTEGER) totalCapP,
										(PULARGE_INTEGER) totalFreeP );

		*userCapP = *totalCapP;
	}
	else {
		bRet = GetDiskFreeSpace( csDriveName->getBuf(), &dwSectPerClust, &dwBytesPerSect,
														&dwFreeClusters, &dwTotalClusters );

		*totalCapP = (unsigned __int64) dwTotalClusters * (unsigned __int64) dwSectPerClust *
						(unsigned __int64) dwBytesPerSect;

		*totalFreeP = (unsigned __int64) dwFreeClusters * (unsigned __int64) dwSectPerClust *
						(unsigned __int64) dwBytesPerSect;

		*userCapP = *totalCapP;
		*userFreeP = *totalFreeP;
	}

	return bRet;
}

		//	these would normally be enclosed in _TXL() blocks,
		//	but GetProcAddress doesn't come in a W version
#if defined(UNICODE)
	#define		kGetFileAttributesExName			"GetFileAttributesExW"
#else
	#define		kGetFileAttributesExName			"GetFileAttributesExA"
#endif

#define			kDLLWithGetFileAttributesEx		_TXL( "kernel32.dll" )

typedef BOOL (WINAPI *P_GFAE)( LPCTSTR, GET_FILEEX_INFO_LEVELS, LPVOID );

ErrCode XToolkit::XGetFileAttributes2( const CStr *csFullPath, DWORD *attributes )
{
	P_GFAE						pGetFileAttributesEx = NULL;
	WIN32_FILE_ATTRIBUTE_DATA	attributesData;
	BOOL						bResult;

	if ( csFullPath == NULL )
		return kErrParamErr;

	pGetFileAttributesEx = (P_GFAE) GetProcAddress( GetModuleHandle( kDLLWithGetFileAttributesEx ), kGetFileAttributesExName );

	if ( pGetFileAttributesEx != NULL ) {
		bResult = pGetFileAttributesEx( csFullPath->getBuf(), GetFileExInfoStandard, &attributesData );

		*attributes = attributesData.dwFileAttributes;

		if ( !bResult )
			return GetLastError();
	}
	else {
		*attributes = GetFileAttributes( csFullPath->getBuf() );

		if ( *attributes == -1 )
			return GetLastError();
	}

	return kErrNoErr;
}

BOOL XToolkit::isGetFileAttributesExAvailable()
{
	return ( GetProcAddress( GetModuleHandle( kDLLWithGetFileAttributesEx ), kGetFileAttributesExName ) != NULL );
}

ErrCode XToolkit::XSetFileAttributes( const CStr *csFullPath, DWORD dwAttributes )
{
	if ( SetFileAttributes( csFullPath->getBuf(), dwAttributes ) )
		return kErrNoErr;

	return GetLastError();
}


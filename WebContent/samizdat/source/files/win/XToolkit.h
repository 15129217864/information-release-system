/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_XTOOLKIT_H
#define INC_XTOOLKIT_H

#include "comdefs.h"
#include <commctrl.h>
#include <shlobj.h>
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	XToolkit

	Wrappers for some API routines.

DESCRIPTION
	Wraps some API routines, mainly to make them easier to use with CStr's.
	Most of these simply call the associated WinAPI routine, however some
	parameter validation is also performed.

------------------------------------------------------------------------*/

class XToolkit {
public:

		///////////////////////
		//
		//  Calls GetDiskFreeSpaceEx() if it's available
		//	GetDiskFreeSpace() if not
		//
		//  [out]	totalCapP		will contain the total capacity of the volume
		//  [out]	totalFreeP		will contain the total free space of the volume
		//  [out]	userCapP		will contain the total capacity of the volume which is available to the current user
		//  [out]	userFreeP		will contain the total free space of the volume which is available to the current user
		//
	static	BOOL		XGetDiskFreeSpace2( const CStr *csDriveName, unsigned __int64 *totalCapP,
											unsigned __int64 *totalFreeP, unsigned __int64 *userCapP,
											unsigned __int64 *userFreeP );

		///////////////////////
		//
		//  Wraps GetDriveDisplayName()
		//
	static	DWORD		XGetDriveDisplayName( const CStr *csDriveName, DWORD dwFileAttributes, CStr *csDisplayName );

		///////////////////////
		//
		//  Wraps GetVolumeInformation()
		//
	static	BOOL		XGetVolumeInformation( const CStr *csDriveName,
												CStr *csOutVolName,
												LPDWORD lpVolumeSerialNumber,
												LPDWORD lpMaximumComponentLength,
												LPDWORD lpFileSystemFlags,
												CStr *csFileSystemName );

		///////////////////////
		//
		//  Wraps SHGetFileInfo() relating to getting the icon of a file.
		//
	static	HIMAGELIST	XSHGetFileInfoIcon( const CStr *csName, DWORD dwFileAttr, DWORD dwFlags, long *iconID );

		///////////////////////
		//
		//  Wraps GetShortPathName()
		//
	static	BOOL		XGetShortPathName( const CStr *fullPath, CStr *shortPath );

		///////////////////////
		//
		//  Wraps GetLogicalDriveStrings()
		//
	static	ErrCode		XGetLogicalDriveStrings( CStr *toStr );

		///////////////////////
		//
		//  Wraps CreateProcess()
		//
	static	BOOL		XCreateProcess( const CStr *commandLine, DWORD dwFlags, WORD wShowWindow,
										LPPROCESS_INFORMATION lpPI );

		///////////////////////
		//
		//  Wraps FindExecutable()
		//
	static	HINSTANCE	XFindExecutable( const CStr *csDocFile, const CStr *csDirectory, CStr *csExeFullPath );

		///////////////////////
		//
		//  Wraps GetDiskFreeSpace()
		//
	static	BOOL		XGetDiskFreeSpace( const CStr *csDriveName, DWORD *sectorsPerClst, DWORD *bytesPerSector,
											DWORD *numFreeClst, DWORD *totNumClst );

		///////////////////////
		//
		//  Wraps GetDriveType()
		//
	static	DWORD		XGetDriveType( const CStr *csDriveName );

		///////////////////////
		//
		//  Uses FindFirstFile() to get the dates for a file.
		//
	static	HANDLE		XFindFirstFileGetDates( const CStr *csSearchString, FILETIME *crDate, FILETIME *acDate, FILETIME *mdDate );

		///////////////////////
		//
		//  Wraps FindFirstFile()
		//
	static	HANDLE		XFindFirstFile( const CStr *csSearchString, DWORD *dwAttrs, CStr *csFileName );

		///////////////////////
		//
		//  Wraps FindNextFile()
		//
	static	BOOL		XFindNextFile( HANDLE findH, DWORD *dwAttrs, CStr *csFileName );

		///////////////////////
		//
		//  Wraps GetFileAttributes()
		//
	static	DWORD		XGetFileAttributes( const CStr *csFullPath );

		///////////////////////
		//
		//  Calls GetFileAttributesEx() if it's available, GetFileAttributes if not
		//
		//  [out]	attributes		contains the attributes if the return value is zero
		//
	static	ErrCode		XGetFileAttributes2( const CStr *csFullPath, DWORD *attributes );

		///////////////////////
		//
		//  Indicates whether GetFileAttributesEx() is available
		//
		//
	static	BOOL		isGetFileAttributesExAvailable();

		///////////////////////
		//
		//  Wraps SetFileAttributes()
		//
	static	ErrCode		XSetFileAttributes( const CStr *csFullPath, DWORD dwAttributes );

		///////////////////////
		//
		//  Wraps ShellExecute()
		//
	static	HINSTANCE	XShellExecute( HWND hwnd, const CStr *csOp, const CStr *csFile,
										const CStr *csParams, const CStr *csDir, INT nShowCmd );

		///////////////////////
		//
		//  Wraps WinExec()
		//
	static	UINT		XWinExec( const CStr *csCmdLine, UINT uCmdShow );

		///////////////////////
		//
		//  Wraps DdeCreateStringHandle()
		//
	static	HSZ			XDdeCreateStringHandle( DWORD idInst, const CStr *csStr, int iCodePage );

		///////////////////////
		//
		//  Wraps DeleteFile()
		//
	static	BOOL		XDeleteFile( const CStr *csFullPath );

		///////////////////////
		//
		//  Wraps CreateFile()
		//
	static	HANDLE		XCreateFile( const CStr *csFileName, DWORD dwDesiredAccess, DWORD dwShareMode,
								LPSECURITY_ATTRIBUTES lpSecurityAttributes, DWORD dwCreationDistribution,
								DWORD dwFlagsAndAttributes, HANDLE hTemplateFile );

		///////////////////////
		//
		//  Wraps RegQueryValue()
		//
	static	ErrCode		XRegQueryValue( HKEY hKey, const CStr *csSubKey, CStr *csValue );

		///////////////////////
		//
		//  Wraps RegQueryValueEx()
		//
	static	ErrCode		XRegQueryValueEx( HKEY hKey, const CStr *csSubKey, CStr *csValue );

		///////////////////////
		//
		//  Wraps RegOpenKeyEx()
		//
	static	ErrCode		XRegOpenKeyEx( HKEY hKey, const CStr *csSubKey, DWORD ulOptions,
										REGSAM samDesired, PHKEY phkResult );

		///////////////////////
		//
		//  Wraps RegEnumKey()
		//
	static	ErrCode		XRegEnumKey( HKEY hKey, DWORD dwIndex, CStr *csName );

		///////////////////////
		//
		//  Wraps GetFileVersionInfoSize()
		//
	static	DWORD		XGetFileVersionInfoSize( const CStr *csFileName, LPDWORD lpdwHandle );

		///////////////////////
		//
		//  Wraps GetFileVersionInfo()
		//
	static	BOOL		XGetFileVersionInfo( const CStr *csFileName, DWORD dwHandle, DWORD dwLen, LPVOID lpData );

		///////////////////////
		//
		//  Wraps VerQueryValue()
		//
	static	BOOL		XVerQueryValue( const LPVOID pBlock, const CStr *csSubBlock, LPVOID *lplpBuffer, PUINT puLen );

		///////////////////////
		//
		//  Wraps SHGetFileInfo() relating to getting the type of an executable
		//
	static	DWORD		XSHGetFileInfoExeType( const CStr *fileName );

		///////////////////////
		//
		//  Wraps SHGetPathFromIDList()
		//
	static	BOOL		XSHGetPathFromIDList( LPCITEMIDLIST pidl, CStr *csLongFileName );
};

#endif


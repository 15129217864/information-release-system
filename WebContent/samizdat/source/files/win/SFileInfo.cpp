/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SFileInfo.h"
#include "CFileUtils.h"
#include "XToolkit.h"
#include "Debugger.h"

ErrCode SFileInfo::iGetShortPathName( const CStr *inFileName, CStr *outFileName )
{
	BOOL			bResult;

	bResult = (BOOL) XToolkit::XGetShortPathName( inFileName, outFileName );

	return bResult ? kErrNoErr : kErrGetShortPathName;
}

ErrCode SFileInfo::iGetFileDates( const CStr *fullPath, CDateBundle *dateBundle )
{
	FILETIME		modDate, crDate, acDate;
	long			theErr;

	theErr = CFileUtils::getFileTime( fullPath, &crDate, &acDate, &modDate );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "iGetFileDates" ), fullPath, NULL, theErr, 0 );
		goto bail;
	}

	theErr = dateBundle->setDate( CDateBundle::kModificationDate, &modDate );
	theErr = dateBundle->setDate( CDateBundle::kCreationDate, &crDate );
	theErr = dateBundle->setDate( CDateBundle::kAccessDate, &acDate );

bail:

	return theErr;
}

ErrCode SFileInfo::iGetFileAttributes( const CStr *fullPath, unsigned long *val )
{
	DWORD		dwAttrs, retAttrs;
	long		theErr;
	
	retAttrs = 0;
	theErr = kErrNoErr;

	theErr = XToolkit::XGetFileAttributes2( fullPath, &dwAttrs );
	if ( dwAttrs == 0xFFFFFFFF ) {
		theErr = kErrGetFileAttributes;
		goto bail;
	}

	if ( ( dwAttrs & FILE_ATTRIBUTE_DIRECTORY ) != 0 )
		retAttrs |= kFileAttributes_FILE_DIR;

	if ( ( dwAttrs & FILE_ATTRIBUTE_HIDDEN ) != 0 )
		retAttrs |= kFileAttributes_FILE_HIDDEN;

	if ( ( dwAttrs & FILE_ATTRIBUTE_ARCHIVE ) != 0 )
		retAttrs |= kFileAttributes_FILE_ARCHIVE;

	if ( ( dwAttrs & FILE_ATTRIBUTE_COMPRESSED ) != 0 )
		retAttrs |= kFileAttributes_FILE_COMPRESSED;

	if ( ( dwAttrs & FILE_ATTRIBUTE_ENCRYPTED ) != 0 )
		retAttrs |= kFileAttributes_FILE_ENCRYPTED;

	if ( ( dwAttrs & FILE_ATTRIBUTE_OFFLINE ) != 0 )
		retAttrs |= kFileAttributes_FILE_OFFLINE;

	if ( ( dwAttrs & FILE_ATTRIBUTE_READONLY ) != 0 )
		retAttrs |= kFileAttributes_FILE_READONLY;

	if ( ( dwAttrs & FILE_ATTRIBUTE_REPARSE_POINT ) != 0 )
		retAttrs |= kFileAttributes_FILE_REPARSEPOINT;

	if ( ( dwAttrs & FILE_ATTRIBUTE_SPARSE_FILE ) != 0 )
		retAttrs |= kFileAttributes_FILE_SPARSE;

	if ( ( dwAttrs & FILE_ATTRIBUTE_SYSTEM ) != 0 )
		retAttrs |= kFileAttributes_FILE_SYSTEM;

	if ( ( dwAttrs & FILE_ATTRIBUTE_TEMPORARY ) != 0 ) 
		retAttrs |= kFileAttributes_FILE_TEMP;

	if ( ( dwAttrs & FILE_ATTRIBUTE_DEVICE ) != 0 ) 
		retAttrs |= kFileAttributes_FILE_DEVICE;

	if ( ( dwAttrs & FILE_ATTRIBUTE_NOT_CONTENT_INDEXED ) != 0 ) 
		retAttrs |= kFileAttributes_FILE_NOT_CONTENT_INDEXED;


		//	if this is a normal file, none of the bits can be set,
		//	*except for* kFileAttributes_FILE_EXECUTABLE, which can be set if this is an app
	if ( ( dwAttrs & FILE_ATTRIBUTE_NORMAL ) != 0 )
		retAttrs = 0;


	if ( ( dwAttrs & FILE_ATTRIBUTE_DIRECTORY ) == 0 ) {
		if ( CFileUtils::isExeFile( fullPath ) )
			retAttrs |= kFileAttributes_FILE_EXECUTABLE;
	}

bail:

	*val = retAttrs;

	return theErr;
}

ErrCode SFileInfo::iGetFileAttributesReadMask( const CStr *fullPath, unsigned long *val )
{
	if ( XToolkit::isGetFileAttributesExAvailable() )
		*val = kFileAttributesExReadMask;
	else
		*val = kFileAttributesStandardReadMask;

	Debugger::debug( __LINE__, _TXL( "iGetFileAttributesReadMask" ), fullPath, NULL, (long) val, 0 );

	return kErrNoErr;
}

ErrCode SFileInfo::iSetFileAttributes( const CStr *fullPath, unsigned long flagMask, unsigned long newFlags )
{
	DWORD		dwAttrs, andValue, orValue;
	long		theErr;
	
	theErr = kErrNoErr;
	andValue = 0xFFFFFFFF;
	orValue = 0;

	theErr = XToolkit::XGetFileAttributes2( fullPath, &dwAttrs );
	if ( dwAttrs == 0xFFFFFFFF ) {
		theErr = kErrGetFileAttributes;
		goto bail;
	}
	if ( theErr != kErrNoErr )
		goto bail;

	if ( ( flagMask & kFileAttributes_FILE_HIDDEN ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_HIDDEN;
		if ( ( newFlags & kFileAttributes_FILE_HIDDEN ) != 0 )
			orValue |= FILE_ATTRIBUTE_HIDDEN;
	}

	if ( ( flagMask & kFileAttributes_FILE_ARCHIVE ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_ARCHIVE;
		if ( ( newFlags & kFileAttributes_FILE_ARCHIVE ) != 0 )
			orValue |= FILE_ATTRIBUTE_ARCHIVE;
	}

	if ( ( flagMask & kFileAttributes_FILE_COMPRESSED ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_COMPRESSED;
		if ( ( newFlags & kFileAttributes_FILE_COMPRESSED ) != 0 )
			orValue |= FILE_ATTRIBUTE_COMPRESSED;
	}

	if ( ( flagMask & kFileAttributes_FILE_ENCRYPTED ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_ENCRYPTED;
		if ( ( newFlags & kFileAttributes_FILE_ENCRYPTED ) != 0 )
			orValue |= FILE_ATTRIBUTE_ENCRYPTED;
	}

	if ( ( flagMask & kFileAttributes_FILE_OFFLINE ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_OFFLINE;
		if ( ( newFlags & kFileAttributes_FILE_OFFLINE ) != 0 )
			orValue |= FILE_ATTRIBUTE_OFFLINE;
	}

	if ( ( flagMask & kFileAttributes_FILE_READONLY ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_READONLY;
		if ( ( newFlags & kFileAttributes_FILE_READONLY ) != 0 )
			orValue |= FILE_ATTRIBUTE_READONLY;
	}

	if ( ( flagMask & kFileAttributes_FILE_REPARSEPOINT ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_REPARSE_POINT;
		if ( ( newFlags & kFileAttributes_FILE_REPARSEPOINT ) != 0 )
			orValue |= FILE_ATTRIBUTE_REPARSE_POINT;
	}

	if ( ( flagMask & kFileAttributes_FILE_SPARSE ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_SPARSE_FILE;
		if ( ( newFlags & kFileAttributes_FILE_SPARSE ) != 0 )
			orValue |= FILE_ATTRIBUTE_SPARSE_FILE;
	}

	if ( ( flagMask & kFileAttributes_FILE_SYSTEM ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_SYSTEM;
		if ( ( newFlags & kFileAttributes_FILE_SYSTEM ) != 0 )
			orValue |= FILE_ATTRIBUTE_SYSTEM;
	}

	if ( ( flagMask & kFileAttributes_FILE_TEMP ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_TEMPORARY;
		if ( ( newFlags & kFileAttributes_FILE_TEMP ) != 0 )
			orValue |= FILE_ATTRIBUTE_TEMPORARY;
	}

	if ( ( flagMask & kFileAttributes_FILE_DEVICE ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_DEVICE;
		if ( ( newFlags & kFileAttributes_FILE_DEVICE ) != 0 )
			orValue |= FILE_ATTRIBUTE_DEVICE;
	}

	if ( ( flagMask & kFileAttributes_FILE_NOT_CONTENT_INDEXED ) != 0 ) {
		andValue &= ~FILE_ATTRIBUTE_NOT_CONTENT_INDEXED;
		if ( ( newFlags & kFileAttributes_FILE_NOT_CONTENT_INDEXED ) != 0 )
			orValue |= FILE_ATTRIBUTE_NOT_CONTENT_INDEXED;
	}

	dwAttrs &= andValue;
	dwAttrs |= orValue;

	theErr = XToolkit::XSetFileAttributes( fullPath, dwAttrs );

bail:

	return theErr;
}

ErrCode SFileInfo::iGetFileAttributesWriteMask( const CStr *fullPath, unsigned long *val )
{
	*val = kFileAttributesStandardWriteMask;

	return kErrNoErr;
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

ErrCode SFileInfo::iGetExecutableType( const CStr *fullPath, unsigned long *val )
{
	DWORD			dwType, retVal;
	long			theErr;
	char			aa, bb, cc, dd;

	theErr = kErrNoErr;
	retVal = kExecutableTypeUnknown;

	if ( !CFileUtils::exists( fullPath ) ) {
		theErr = kErrFileNotFound;
		goto bail;
	}

	dwType = XToolkit::XSHGetFileInfoExeType( fullPath );
	if ( dwType == 0 ) {
		theErr = kErrGetBinaryType;
		goto bail;
	}

	aa = HIBYTE( HIWORD( dwType ) );
	bb = LOBYTE( HIWORD( dwType ) );
	cc = HIBYTE( LOWORD( dwType ) );
	dd = LOBYTE( LOWORD( dwType ) );

	if ( dd == 'M' && cc == 'Z' ) {
		retVal = kExecutableTypeDOSExe;
	}
	else if ( ( dd == 'P' && cc == 'E' ) || ( dd == 'N' && cc == 'E' ) ) {
		retVal = kExecutableTypeWin32Exe;
	}
	else
		retVal = kExecutableTypeUnknown;

bail:

	*val = retVal;

	return theErr;
}


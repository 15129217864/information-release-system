/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SVolumes.h"
#include "XToolkit.h"
#include "CFileUtils.h"
#include "utils.h"
#include "Debugger.h"

ErrCode SVolumes::iSetVolumeLabel( const CStr *driveName, const CStr *newLabel )
{
	return kErrUnimplementedErr;
}

ErrCode SVolumes::iGetVolumeDates( const CStr *path, CDateBundle *dateBundle )
{
	return kErrUnimplementedErr;
}

ErrCode SVolumes::iGetDriveDisplayName( const CStr *csDriveName, CStr *csDisplayName )
{
	BOOL			bResult;

	bResult = (BOOL) XToolkit::XGetDriveDisplayName( csDriveName, SHGFI_DISPLAYNAME, csDisplayName );

	return bResult ? kErrNoErr : kErrGetDriveDisplayName;
}

ErrCode SVolumes::iGetVolumeInformation( const CStr *csDriveName, CStr *csOutVolName,
										CStr *csFileSystemName, unsigned long *theIntsP )
{
	BOOL			bRet;

	csOutVolName->ensureCharCapacity( CStr::kMaxPath );
	csFileSystemName->ensureCharCapacity( CStr::kMaxPath );

	bRet = XToolkit::XGetVolumeInformation( csDriveName, csOutVolName,
									&theIntsP[ 0 ], &theIntsP[ 1 ], &theIntsP[ 2 ],
									csFileSystemName );

	return bRet ? kErrNoErr : kErrGetVolumeInformation;
}

ErrCode SVolumes::iGetVolumeCapInfo( const CStr *driveName, unsigned __int64 *capP )
{
	unsigned __int64	totalCap, totalFree, userCap, userFree;
	BOOL				bRet;

	bRet = XToolkit::XGetDiskFreeSpace2( driveName, &totalCap, &totalFree, &userCap, &userFree );
	if ( !bRet )
		return kErrGetVolumeCapInfo;

	capP[ kVolumeCapInfoCapacityOffset ] = totalCap;
	capP[ kVolumeCapInfoFreeSpaceOffset ] = totalFree;
	capP[ kVolumeCapInfoCapacityToUserOffset ] = userCap;
	capP[ kVolumeCapInfoFreeSpaceToUserOffset ] = userFree;

	return kErrNoErr;
}

ErrCode SVolumes::iGetVolumeFlags( const CStr *driveName, long *flagsP )
{
	DWORD			driveType, dwVolFlags, dwMaxCompLen;
	long			theErr, retFlags;
	BOOL			bRet;
	
	retFlags = 0;
	theErr = kErrNoErr;
	
	driveType = XToolkit::XGetDriveType( driveName );
	if ( driveType == 0 || driveType == 1 ) {
		theErr = kErrGetDriveType;
		goto bail;
	}

	switch ( driveType ) {
		case DRIVE_REMOVABLE:
			retFlags = kVolumeFlagRemovable;
		break;
		
		case DRIVE_FIXED:
			retFlags = kVolumeFlagFixed;
		break;
		
		case DRIVE_REMOTE:
			retFlags = kVolumeFlagRemote;
		break;
		
		case DRIVE_CDROM:
			retFlags = kVolumeFlagCDROM;
		break;
		
		case DRIVE_RAMDISK:
			retFlags = kVolumeFlagRAM;
		break;
	}

	bRet = XToolkit::XGetVolumeInformation( driveName, NULL, NULL, &dwMaxCompLen, &dwVolFlags, NULL );
	if ( !bRet ) {
		theErr = kErrGetVolumeInformation;
		goto bail;
	}
	
	if ( ( dwVolFlags & FS_CASE_IS_PRESERVED ) != 0 )
		retFlags |= kVolumeFlagCaseIsPreserved;

	if ( ( dwVolFlags & FS_CASE_SENSITIVE ) != 0 )
		retFlags |= kVolumeFlagCaseSensitive;

	if ( ( dwVolFlags & FS_UNICODE_STORED_ON_DISK ) != 0 )
		retFlags |= kVolumeFlagUnicodeSupported;

	if ( ( dwVolFlags & FS_FILE_COMPRESSION ) != 0 )
		retFlags |= kVolumeFlagFilesCompressed;

	if ( ( dwVolFlags & FS_VOL_IS_COMPRESSED ) != 0 )
		retFlags |= kVolumeFlagVolumeCompressed;

bail:

	*flagsP = retFlags;

	return theErr;
}

ErrCode SVolumes::iGetVolumeReadFlagsMask( const CStr *driveName, long *maskP )
{
	*maskP = kVolumeFlagSupportedFlags;

	return kErrNoErr;
}

ErrCode SVolumes::iGetVolumes( long maxToReturn, CStringVector *stringVec )
{
	CStr		tempStr( CStr::kMaxPath ), *csDrive;
	long		theErr, i;

	theErr = XToolkit::XGetLogicalDriveStrings( &tempStr );
	if ( theErr != kErrNoErr )
		goto bail;

		//	put the drive strings into stringVec
	stringVec->appendPackedString( &tempStr );
	
		//	remove unmounted drives
	for ( i = stringVec->getNumStrings() - 1; i >= 0; i-- ) {
		csDrive = stringVec->getString( i );
		if ( csDrive != NULL ) {
			if ( !CFileUtils::isMountedDrive( csDrive ) ) {
				stringVec->removeString( csDrive );
				delete csDrive;
			}
		}
	}

bail:

	return theErr;
}


/*
	BOOL	bRet;
	DWORD	lpV, lpM, lpF;

	bRet = GetVolumeInformation(
		"C:\\",	// address of root directory of the file system 
		NULL,	// address of name of the volume 
		0,	// length of lpVolumeNameBuffer 
		&lpV,	// address of volume serial number 
		&lpM,	// address of system's maximum filename length
		&lpF,	// address of file system flags 
		NULL,
		0
	);

	if ( bRet )
		Debugger::debug( lpV, "VOLINFO" );
	else
		Debugger::debug( GetLastError(), "VOLINFO ERROR" );

	bRet = GetVolumeSerialNumber( "C:\\", &lpV );

	if ( bRet )
		Debugger::debug( lpV, "	VOLINFO2" );
	else
		Debugger::debug( GetLastError(), "	VOLINFO2 ERROR" );
*/


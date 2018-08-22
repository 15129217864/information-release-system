/***************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SFlags.h"
#include "CUtils.h"
#include "SFiles.h"
#include "MoreFiles.h"
#include "MoreFilesExtras.h"
#include "FullPath.h"
#include "Debugger.h"

#if defined(__osx__)
	#include <sys/param.h>
	OSStatus getCDROMAndEjectableStatus( char *drivePath, Boolean *isCDROM, Boolean *isEjectable );
	OSStatus vRefNumToBSDName( short vRefNum, char *buf );
#endif

ErrCode SFlags::getDiskFileFlags( const CFSpec *theSpec, unsigned long *flags )
{
	ErrCode					theErr, ignoredErr;
	long					creator, type, finderFlags, attribs;
	BOOL					bIsShared;

	*flags = 0;

	theErr = SFiles::getFinderInfo( theSpec, &creator, &type, &finderFlags, &attribs );
	if ( theErr != kErrNoErr )
		return theErr;

	ignoredErr = isShared( theSpec, finderFlags, attribs, type, &bIsShared );

	if ( ignoredErr == kErrNoErr && bIsShared )
		*flags |= kFileAttributes_FILE_SHARED;

	if ( ( finderFlags & fHasBundle ) != 0 )
		*flags |= kFileAttributes_FILE_HAS_BNDL;
	
	if ( ( finderFlags & kHasBeenInited ) != 0 )
		*flags |= kFileAttributes_FILE_BEEN_INITED;
	
	if ( ( finderFlags & 0x0080 ) != 0 )
		*flags |= kFileAttributes_FILE_NO_INITS;
	
	if ( ( finderFlags & kHasCustomIcon ) != 0 )
		*flags |= kFileAttributes_FILE_CUSTOM_ICON;
	
	if ( ( finderFlags & kIsStationery ) != 0 )
		*flags |= kFileAttributes_FILE_STATIONERY;
	
	if ( ( finderFlags & kNameLocked ) != 0 )
		*flags |= kFileAttributes_FILE_NAME_LOCKED;
	
	if ( ( finderFlags & kIsInvisible ) != 0 )
		*flags |= kFileAttributes_FILE_HIDDEN;
	
	if ( ( attribs & ioDirMask ) != 0 )
		*flags |= kFileAttributes_FILE_DIR;

	if ( ( attribs & 1 ) != 0 )
		*flags |= kFileAttributes_FILE_READONLY;

	if ( type == 'APPL' || type == 'APPE' )
		*flags |= kFileAttributes_FILE_EXECUTABLE;

	return theErr;
}

ErrCode SFlags::setDiskFileFlags( const CFSpec *theSpec, unsigned long flagMask, unsigned long newFlags )
{
	FSSpec				tempSpec;
	ErrCode				theErr;
	unsigned short		andValue, orValue;

	andValue = 0xFFFF;
	orValue = 0;

	if ( ( flagMask & kFileAttributes_FILE_HIDDEN ) != 0 ) {
		andValue &= ~kIsInvisible;
		if ( ( newFlags & kFileAttributes_FILE_HIDDEN ) != 0 )
			orValue |= kIsInvisible;
	}

	if ( ( flagMask & kFileAttributes_FILE_HAS_BNDL ) != 0 ) {
		andValue &= ~kHasBundle;
		if ( ( newFlags & kFileAttributes_FILE_HAS_BNDL ) != 0 )
			orValue |= kHasBundle;
	}

	if ( ( flagMask & kFileAttributes_FILE_BEEN_INITED ) != 0 ) {
		andValue &= ~kHasBeenInited;
		if ( ( newFlags & kFileAttributes_FILE_BEEN_INITED ) != 0 )
			orValue |= kHasBeenInited;
	}

	if ( ( flagMask & kFileAttributes_FILE_STATIONERY ) != 0 ) {
		andValue &= ~kIsStationery;
		if ( ( newFlags & kFileAttributes_FILE_STATIONERY ) != 0 )
			orValue |= kIsStationery;
	}

	if ( ( flagMask & kFileAttributes_FILE_NAME_LOCKED ) != 0 ) {
		andValue &= ~kNameLocked;
		if ( ( newFlags & kFileAttributes_FILE_NAME_LOCKED ) != 0 )
			orValue |= kNameLocked;
	}

	if ( ( flagMask & kFileAttributes_FILE_CUSTOM_ICON ) != 0 ) {
		andValue &= ~kHasCustomIcon;
		if ( ( newFlags & kFileAttributes_FILE_CUSTOM_ICON ) != 0 )
			orValue |= kHasCustomIcon;
	}

	theErr = SFiles::setFinderFlags( theSpec, andValue, orValue );
	if ( theErr != kErrNoErr )
		return theErr;

	theSpec->putInto( &tempSpec );

	if ( ( flagMask & kFileAttributes_FILE_READONLY ) != 0 ) {
		if ( ( newFlags & kFileAttributes_FILE_READONLY ) != 0 )
			theErr = FSpSetFLock( &tempSpec );
		else
			theErr = FSpRstFLock( &tempSpec );
	}

	return theErr;
}

ErrCode SFlags::getDiskFileReadFlagsMask( const CFSpec *theSpec, unsigned long *masks )
{
	*masks = kStdDiskFileReadMask;

	return kErrNoErr;

	UNUSED( theSpec );
}

ErrCode SFlags::getDiskFileWriteFlagsMask( const CFSpec *theSpec, unsigned long *masks )
{
	*masks = kStdDiskFileWriteMask;

	return kErrNoErr;

	UNUSED( theSpec );
}

ErrCode SFlags::getDiskVolumeFlags( long vRef, unsigned long *flags )
{
	HParamBlockRec			pb;
	ErrCode					theErr;

	*flags = 0;

	pb.volumeParam.ioNamePtr = NULL;
	pb.volumeParam.ioVRefNum = vRef;
	pb.volumeParam.ioVolIndex = 0;

	theErr = PBHGetVInfoSync( &pb );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "SFlags:gdvf", NULL, NULL, theErr, vRef );
		return theErr;
	}

#if defined(macintosh)

	BOOL					bResult;

//	kMaskRemovable
//	kMaskFixed
	theErr = IsEjectable( pb.volumeParam.ioVDrvInfo, &bResult );
	if ( theErr == kErrNoErr ) {
		if ( bResult )
			*flags |= kMaskRemovable;
		else
			*flags |= kMaskFixed;
	}
	else
		Debugger::debug( __LINE__, "SFlags:IsEjectable", NULL, NULL, theErr, vRef );


//	kMaskCDROM
	theErr = checkForCD( vRef, &bResult );
	if ( theErr == kErrNoErr ) {
		if ( bResult )
			*flags |= kMaskCDROM;
	}
	else
		Debugger::debug( __LINE__, "SFlags:checkForCD", NULL, NULL, theErr, vRef );

#elif defined(__osx__)

    char		buf[ MAXPATHLEN ];
	Boolean		isCDROM, isEjectable;

	theErr = vRefNumToBSDName( vRef, buf );
	if ( theErr == kErrNoErr ) {
		theErr = getCDROMAndEjectableStatus( buf, &isCDROM, &isEjectable );
		if ( theErr == kErrNoErr ) {

			if ( isEjectable )
				*flags |= kMaskRemovable;
			else
				*flags |= kMaskFixed;

			if ( isCDROM )
				*flags |= kMaskCDROM;
		}
		else
			Debugger::debug( __LINE__, "SFlags:getCDROMAndEjectableStatus", NULL, NULL, theErr, vRef );
	}
	else
		Debugger::debug( __LINE__, "SFlags:vRefNumToBSDName", NULL, NULL, theErr, vRef );

#endif

//	kMaskRemote
	if ( !Is_local_volume( vRef ) )
		*flags |= kMaskRemote;


//	kMaskSystem
	if ( isSystemDisk( vRef ) )
		*flags |= kMaskSystem;


	return kErrNoErr;
}

ErrCode SFlags::getDiskVolumeReadFlagsMask( long vRef, unsigned long *masks )
{
	*masks = kStdDiskVolumeReadFlagsMask;
	
	return kErrNoErr;

	UNUSED( vRef );
}

ErrCode SFlags::setDiskVolumeFlags( long vRef, unsigned long flagMask, unsigned long newFlags )
{
	return paramErr;

	UNUSED( vRef );
	UNUSED( flagMask );
	UNUSED( newFlags );
}

ErrCode SFlags::getDiskVolumeWriteFlagsMask( long vRef, unsigned long *masks )
{
	*masks = kStdDiskVolumeWriteFlagsMask;

	return kErrNoErr;

	UNUSED( vRef );
}

/*
If it's a directory, returns whether the attributes indicate if it's shared
If it's an .exe, returns whether the finderFlags indicate it's a shared app
If it's a regular file, returns what its container says
*/

ErrCode SFlags::isShared( const CFSpec *theSpec, long finderFlags, long attribs, long type, BOOL *bIsShared )
{
	CFSpec			*dirSpec;
	ErrCode			theErr;
	Str255			contName;
	long			contParID, dirCreator, dirType, dirFinderFlags, dirAttribs;

	*bIsShared = FALSE;

	if ( ( attribs & ioDirMask ) != 0 ) {
		if ( ( attribs & 4 ) != 0 )
			*bIsShared = TRUE;
		
		return kErrNoErr;
	}
	else {
		if ( type == 'APPL' || type == 'APPE' ) {
			if ( ( finderFlags & kIsShared ) != 0 ) {
				*bIsShared = TRUE;
				return kErrNoErr;
			}
		}
		
		theErr = SFiles::getContainer( theSpec, &contParID, contName );
		if ( theErr != kErrNoErr )
			return theErr;		

		dirSpec = new CFSpec( theSpec->getVRef(), contParID, contName );

		theErr = SFiles::getFinderInfo( dirSpec, &dirCreator, &dirType, &dirFinderFlags, &dirAttribs );
		if ( theErr != kErrNoErr )
			return theErr;		

		if ( ( attribs & 4 ) != 0 )
			*bIsShared = TRUE;
		
		return kErrNoErr;
	}
}

#if defined(macintosh)

ErrCode SFlags::checkForCD( long vRef, BOOL *bIsCD )
{
	DriverGestaltInfo	response;
	DrvQElPtr			driveQElementPtr;
	char*				drvrNamePtr;
	ErrCode				theErr;
	short				notused;

	*bIsCD = FALSE;

	theErr = FindDrive( NULL, vRef, &driveQElementPtr );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "SFlags:checkForCD.FindDrive", NULL, NULL, theErr, vRef );
		return theErr;
	}

	drvrNamePtr = DrvrRefToName( driveQElementPtr->dQRefNum );

	theErr = OpenDriver( (ConstStr255Param) drvrNamePtr, &notused );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "SFlags:checkForCD.open", NULL, NULL, theErr, vRef );
		return theErr;
	}

	theErr = DoDriverGestalt( driveQElementPtr->dQRefNum, driveQElementPtr->dQDrive, kdgDeviceType, &response );
	if ( theErr == kErrNoErr ) {
		if ( response.devt.deviceType == kdgCDType )
			*bIsCD = TRUE;
		else
			*bIsCD = FALSE;
	}
	else {
		Debugger::debug( __LINE__, "SFlags:checkForCD.gestalt", NULL, NULL, theErr, vRef );
		if ( checkCDDriverName( (StringPtr) drvrNamePtr ) )
			*bIsCD = TRUE;
		else
			*bIsCD = FALSE;
	}

	return theErr;
}

BOOL SFlags::checkCDDriverName( StringPtr p )
{
	if ( p[ 0 ] < 8 )
		return FALSE;
	if ( p[ 3 ] != 'p' && p[ 3 ] != 'P' )
		return FALSE;
	if ( p[ 4 ] != 'p' && p[ 4 ] != 'P' )
		return FALSE;
	if ( p[ 5 ] != 'l' && p[ 5 ] != 'L' )
		return FALSE;
	if ( p[ 6 ] != 'e' && p[ 6 ] != 'E' )
		return FALSE;
	if ( p[ 7 ] != 'c' && p[ 7 ] != 'C' )
		return FALSE;
	if ( p[ 8 ] != 'd' && p[ 8 ] != 'D' )
		return FALSE;
	
	return TRUE;
}

char *SFlags::DrvrRefToName(short refNum)
{
	AuxDCEHandle*		UTable  = (AuxDCEHandle*) LMGetUTableBase();
	DCtlPtr				dctl;
	Ptr					p;	
	
	if(!refNum) return ((char*) "\p<none>");

	dctl = (DCtlPtr) *UTable[~refNum];
	p 	 =  dctl->dCtlDriver;
	if ( dctl->dCtlFlags  & 0x0040 )
		p = (Ptr) *p;

	return  ( p?(char*) (p+18):(char*)"\p-Purged-");
}

ErrCode SFlags::DoDriverGestalt		(short 				driverRefnum,
							 short 				driveNumber,
							OSType 				gestaltSelector,
							DriverGestaltInfo	*response )
{
	DriverGestaltParam		pb;
	ErrCode					status;
		
	memset( &pb, 0, sizeof(DriverGestaltParam) );

	pb.ioVRefNum 	= driveNumber;
	pb.ioCRefNum	= driverRefnum;
	pb.csCode 		= kDriverGestaltCode;
	pb.driverGestaltSelector = gestaltSelector;
	
	status = PBStatusSync((ParmBlkPtr) &pb);
	
	if (status == kErrNoErr)
		response->i  = pb.driverGestaltResponse;
	return (status);
}

/*
** modified from http://developer.apple.com/technotes/fl/fl_530.html **
*/

ErrCode SFlags::IsEjectable( short driveNumber, BOOL *bResult )
{
	DrvQElPtr	d;
	QHdrPtr	 queueHeader;
	Ptr		 p;

	*bResult = FALSE;
	queueHeader = GetDrvQHdr();
	d = (DrvQElPtr)queueHeader->qHead;

	while ( d != NULL )	/* find the appropriate drive # */
	{
		if (d->dQDrive == driveNumber)	/* is this the drive we want? */
		{
			p = (Ptr)d;
			p -= 3; /* to get to the byte with eject info */

			if ((*p) & 8)
				*bResult = FALSE;			/* non ejectable disk in drive */
			else
				*bResult = TRUE;
			
			return kErrNoErr;
		}
		d = (DrvQElPtr)d->qLink;
	}

	return kErrParamErr;	/* you specified an invalid drive number */
}

#elif defined(__osx__)

/*
char *SFlags::DrvrRefToName(short refNum)
{
	return ((char*) "\p<none>");
}

ErrCode SFlags::IsEjectable( short driveNumber, BOOL *bResult )
{
	return kErrParamErr;
}
#elif defined(__osx__)

ErrCode SFlags::checkForCD( long vRef, BOOL *bIsCD )
{
	return kErrParamErr;
}

*/

#endif

BOOL SFlags::isSystemDisk( long vRef )
{
	ErrCode		theErr;
	long		foundDirID;
	short		foundVRef;
	
	theErr = FindFolder( kOnSystemDisk, kSystemFolderType, FALSE, &foundVRef, &foundDirID );
	if ( theErr == kErrNoErr && foundVRef == vRef )
		return TRUE;
	else
		return FALSE;
}

//	from FindIcon
BOOL SFlags::Is_local_volume( short vRefNum )
{
	GetVolParmsInfoBuffer	vol_parms;
	ErrCode					err;
	HIOParam				opb;

	opb.ioBuffer = (Ptr)&vol_parms;
	opb.ioReqCount = sizeof(vol_parms);
	opb.ioVRefNum = vRefNum;
	opb.ioNamePtr = NULL;
	err = PBHGetVolParmsSync( (HParmBlkPtr) &opb );
	
	return (err != kErrNoErr) || (vol_parms.vMServerAdr == 0);
}

ErrCode SFlags::getVolumeName( long vRef, StringPtr volumeName )
{
	HParamBlockRec			block;
	ErrCode					theErr;

	block.volumeParam.ioNamePtr = volumeName;
	block.volumeParam.ioVRefNum = vRef;
	block.volumeParam.ioVolIndex = 0;

	theErr = PBHGetVInfoSync( &block );

	return theErr;
}


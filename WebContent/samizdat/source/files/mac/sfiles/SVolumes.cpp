/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include <string.h>
#include <translation.h>
#include <Finder.h>
#include "SVolumes.h"
#include "SFiles.h"
#include "MoreFiles.h"
#include "MoreFilesExtras.h"
#include "FullPath.h"
#include "CUtils.h"
#include "stdlib.h"
#include "CMemory.h"
#include "CFilePath.h"
#include "Debugger.h"

#if defined(__osx__)
	#include <Math64.h>
#endif

ErrCode SVolumes::getAllVRefNums( long *vRefP, long maxNum, long *numReturned )
{
	HParamBlockRec		pb;
	ErrCode				theErr;
	long				i, numWritten;

	numWritten = 0;
	CUtils::zeroset( &pb, sizeof(HParamBlockRec) );

	for ( i = 1; i < maxNum; i++ ) {
		pb.volumeParam.ioVolIndex = i;
		theErr = PBHGetVInfoSync( &pb );
		if ( theErr != kErrNoErr )
			break;

		if ( pb.volumeParam.ioVDrvInfo > 0 ) {
			*vRefP = pb.volumeParam.ioVRefNum;
			++vRefP;
			++numWritten;
		}
	}
	
	*numReturned = numWritten;
	
	return kErrNoErr;
}

ErrCode SVolumes::setVolumeColorCoding( long vRef, long newCoding )
{
	CInfoPBRec			pb;
	FSSpec				theSpec;
	ErrCode					theErr;
	unsigned short		newVal;
	
	newVal = (unsigned short) newCoding;
	newVal &= 0x7;
	newVal <<= 1;

	theErr = FSMakeFSSpec( vRef, 0, NULL, &theSpec );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "svcc.fsmfss" ), NULL, NULL, theErr, vRef );
		return theErr;
	}

	CUtils::zeroset( &pb, sizeof(CInfoPBRec) );

	pb.hFileInfo.ioVRefNum = theSpec.vRefNum;
	pb.hFileInfo.ioDirID = theSpec.parID;
	pb.hFileInfo.ioNamePtr = &( theSpec.name[ 0 ] );

	theErr = PBGetCatInfoSync( &pb );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "svcc.gci" ), NULL, NULL, theErr, vRef );
		return theErr;
	}

	pb.dirInfo.ioDrUsrWds.frFlags &= kColor;
	pb.dirInfo.ioDrUsrWds.frFlags |= newVal;

	pb.hFileInfo.ioDirID = theSpec.parID;
	
	theErr = PBSetCatInfoSync( &pb );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, _TXL( "svcc.gci" ), NULL, NULL, theErr, vRef );

	return theErr;
}

ErrCode SVolumes::getVolumeFinderInfo( long vRef, long *creator, long *type, long *flags, long *attribs )
{
	CFSpec		*theSpec;
	ErrCode			theErr;

	theSpec = new CFSpec( vRef, 0, NULL );

	theErr = theSpec->verifySpec();

	if ( theErr != kErrNoErr )
		return theErr;

	return SFiles::getFinderInfo( theSpec, creator, type, flags, attribs );
}

ErrCode SVolumes::setVolumeDate( long vRef, CDateBundle *dateBundle )
{
	ErrCode						theErr;
	HParamBlockRec			block;

	CUtils::zeroset( &block, sizeof(HParamBlockRec) );

	block.volumeParam.ioNamePtr = NULL;
	block.volumeParam.ioVRefNum = vRef;
	block.volumeParam.ioVolIndex = 0;

	theErr = PBHGetVInfoSync( &block );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "svd.gvis", NULL, NULL, theErr, vRef );
		goto bail;
	}

	dateBundle->getDate( CDateBundle::kModificationDate, &block.volumeParam.ioVLsMod );
	dateBundle->getDate( CDateBundle::kCreationDate, &block.volumeParam.ioVCrDate );
	dateBundle->getDate( CDateBundle::kBackupDate, &block.volumeParam.ioVBkUp );

	block.volumeParam.ioNamePtr = NULL;
	block.volumeParam.ioVRefNum = vRef;
	block.volumeParam.ioVolIndex = 0;

	theErr = PBSetVInfoSync( &block );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "svd.svis", NULL, NULL, theErr, vRef );

bail:

	return theErr;
}

ErrCode SVolumes::verifyVolume( long vRef )
{
	FSSpec		theSpec;
	ErrCode			theErr;

	theErr = FSMakeFSSpec( vRef, 0, NULL, &theSpec );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, _TXL( "verifyVolume" ), NULL, NULL, theErr, vRef );

	return theErr;
}

ErrCode SVolumes::getVolumeDate( long vRef, CDateBundle *dateBundle )
{
	ErrCode						theErr;
	HParamBlockRec			block;

	CUtils::zeroset( &block, sizeof(HParamBlockRec) );

	block.volumeParam.ioNamePtr = NULL;
	block.volumeParam.ioVRefNum = vRef;
	block.volumeParam.ioVolIndex = 0;

	theErr = PBHGetVInfoSync( &block );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "gvd.gvis", NULL, NULL, theErr, vRef );

	dateBundle->setDate( CDateBundle::kModificationDate, block.volumeParam.ioVLsMod );
	dateBundle->setDate( CDateBundle::kCreationDate, block.volumeParam.ioVCrDate );
	dateBundle->setDate( CDateBundle::kBackupDate, block.volumeParam.ioVBkUp );

	return theErr;
}

ErrCode SVolumes::renameVolume( long vRef, const CStr *csNewName )
{
	HParamBlockRec		block;
	CFilePath			cfNewName( csNewName );
	Str255				tempNewName;
	ErrCode					theErr;

	cfNewName.getPStr( tempNewName, sizeof(tempNewName) );

	CUtils::zeroset( &block, sizeof(HParamBlockRec) );

	block.ioParam.ioVRefNum = vRef;
	block.ioParam.ioMisc = (Ptr) tempNewName;

	theErr = PBHRenameSync( &block );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "rnV", NULL, NULL, theErr, 0 );

	return theErr;
}

#if defined(macintosh)

ErrCode SVolumes::getVolumeCapacity( long vRef, UnsignedWide *capacity )
{
	UnsignedWide		freeBytes;
	ErrCode					theErr;
	short					outVRef;

	theErr = XGetVInfo( vRef, NULL, &outVRef, &freeBytes, capacity );
	
	return theErr;
}

ErrCode SVolumes::getVolumeFreeSpace( long vRef, UnsignedWide *space )
{
	UnsignedWide		totalBytes;
	ErrCode					theErr;
	short					outVRef;

	theErr = XGetVInfo( vRef, NULL, &outVRef, space, &totalBytes );
	
	return theErr;
}

#elif defined(__osx__)

ErrCode SVolumes::getVolumeCapacity( long vRef, UnsignedWide *capacity )
{
	UInt64					tempCapacity, freeBytes;
	ErrCode					theErr;
	short					outVRef;

	theErr = XGetVInfo( vRef, NULL, &outVRef, &freeBytes, &tempCapacity );

	*capacity = UInt64ToUnsignedWide( tempCapacity );

	return theErr;
}

ErrCode SVolumes::getVolumeFreeSpace( long vRef, UnsignedWide *space )
{
	UInt64					tempSpace, totalBytes;
	ErrCode					theErr;
	short					outVRef;

	theErr = XGetVInfo( vRef, NULL, &outVRef, &tempSpace, &totalBytes );
	
	*space = UInt64ToUnsignedWide( tempSpace );

	return theErr;
}

#endif

ErrCode SVolumes::getVolumeName( long vRef, StringPtr volumeName )
{
	HParamBlockRec			block;
	ErrCode						theErr;

	block.volumeParam.ioNamePtr = volumeName;
	block.volumeParam.ioVRefNum = vRef;
	block.volumeParam.ioVolIndex = 0;

	theErr = PBHGetVInfoSync( &block );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "getVolumeName", NULL, NULL, theErr, 0 );

	return theErr;
}

ErrCode SVolumes::getAllVolumes( long *numReturned, long maxToReturn, long *refsP )
{
	ErrCode			theErr;

	*numReturned = 0;
	theErr = moreFilesOnLine( refsP, maxToReturn, numReturned );
	if ( theErr == kErrNoErr )
		return theErr;

	Debugger::debug( __LINE__, "SV.gav.mfol", NULL, NULL, theErr, 0 );

	*numReturned = 0;
	theErr = getAllVRefNums( refsP, maxToReturn, numReturned );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "SV.gav.gavr", NULL, NULL, theErr, 0 );
	
	return theErr;
}

ErrCode SVolumes::moreFilesOnLine( long *refsP, long maxToReturn, long *numReturned )
{
	FSSpec		*specP;
	ErrCode			theErr;
	long			i;
	short			actVolCount, volIndex;

	specP = (FSSpec*) CMemory::mmalloc( maxToReturn * sizeof(FSSpec), _TXL( "moreFilesOnLine" ) );

	volIndex = 1;
	theErr = OnLine( specP, maxToReturn, &actVolCount, &volIndex );
	if ( theErr == nsvErr )
		theErr = kErrNoErr;
	if ( theErr != kErrNoErr )
		goto bail;

	for ( i = 0; i < actVolCount; i++ )
		refsP[ i ] = specP[ i ].vRefNum;

	*numReturned = actVolCount;

bail:

	CMemory::mfree( specP );

	return theErr;
}


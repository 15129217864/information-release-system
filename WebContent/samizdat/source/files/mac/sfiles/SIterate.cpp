/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SIterate.h"
#include "CUtils.h"
#include "MoreFiles.h"
#include "MoreFilesExtras.h"
#include "SVolumes.h"
#include "SFiles.h"
#include "Debugger.h"

ErrCode SIterate::iterateContents( const CFSpec *theSpec, long *dirIDP, long *numRet,
						unsigned char *bufferP, long numEntries, long flags )
{
	ErrCode			theErr;

	theErr = getDirID( theSpec, dirIDP );
	if ( theErr != kErrNoErr ) {
		theSpec->dumpInfo( theErr, _TXL( "iterateContents.getDirID" ) );
		return theErr;
	}

	theErr = iterateDirectory( theSpec->getVRef(), *dirIDP, numRet, bufferP, numEntries, flags );

	return theErr;
}

ErrCode SIterate::iterateVolumeContents( long vRef, long *dirIDP, long *numRet, unsigned char *bufferP,
								long numEntries, long flags )
{
	ErrCode			theErr;

	theErr = SVolumes::verifyVolume( vRef );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "ivc.vv", NULL, NULL, theErr, vRef );
		return theErr;
	}

	*dirIDP = fsRtDirID;

	theErr = iterateDirectory( vRef, fsRtDirID, numRet, bufferP, numEntries, flags );

	return theErr;
}

ErrCode SIterate::iterateDirectory( long vRef, long dirID, long *numRet,
								unsigned char *bufferP, long numEntries, long flags )
{
	CInfoPBRec			pb;
	Str255				fileName;
	ErrCode					theErr;
	long					numWritten;
	short					index;
	unsigned short		finderFlags;
	Boolean				bIgnoreFiles, bIgnoreAliases, bIgnoreHidden, bIgnoreFolders, bIgnoreNameLocked;
	Byte					objType;

	numWritten = 0;
	bIgnoreHidden = ( ( flags & kIgnoreHidden ) != 0 );
	bIgnoreFolders = ( ( flags & kIgnoreFolders ) != 0 );
	bIgnoreFiles = ( ( flags & kIgnoreFiles ) != 0 );
	bIgnoreAliases = ( ( flags & kIgnoreAliases ) != 0 );
	bIgnoreNameLocked = ( ( flags & kIgnoreNameLocked ) != 0 );

	CUtils::zeroset( &pb, sizeof(CInfoPBRec) );	
	pb.hFileInfo.ioNamePtr = fileName;
	pb.hFileInfo.ioVRefNum = vRef;

	for ( index = 1; numWritten < numEntries; index++ ) {
		pb.hFileInfo.ioDirID = dirID;
		pb.hFileInfo.ioFDirIndex = index;

		theErr = PBGetCatInfoSync( &pb );
		if ( theErr != kErrNoErr )
			break;

		if ( ( pb.hFileInfo.ioFlAttrib & ioDirMask ) == 0 ) {
			finderFlags = pb.hFileInfo.ioFlFndrInfo.fdFlags;
			if ( ( finderFlags & kIsAlias ) != 0 ) {
				if ( bIgnoreAliases )
					continue;
				objType = kIterateIsAlias;
			}
			else {
				if ( bIgnoreFiles )
					continue;
				objType = kIterateIsFile;
			}
		}
		else {
			if ( bIgnoreFolders )
				continue;
			finderFlags = pb.dirInfo.ioDrUsrWds.frFlags;
			objType = kIterateIsDir;
		}

		if ( ( finderFlags & kIsInvisible ) != 0 ) {
			if ( bIgnoreHidden )
				continue;
		}

		if ( ( finderFlags & kNameLocked ) != 0 ) {
			if ( bIgnoreNameLocked )
				continue;
		}

		CUtils::pStrncpy( bufferP, fileName, kIterateNameSize - 1 );
		bufferP[ kIterateTypeOffset ] = objType;
		bufferP += kIterateEntrySize;
		++numWritten;
	}
	
	*numRet = numWritten;

	if ( numWritten < 1 )
		Debugger::debug( __LINE__, _TXL( "iterDir, no items" ), NULL, NULL, theErr, vRef, dirID );

	return kErrNoErr;
}

ErrCode SIterate::getDirID( const CFSpec *theSpec, long *dirIDP )
{
	CFSpec			tempSpec( theSpec );
	CInfoPBRec		pb;
	ErrCode				theErr;

	CUtils::zeroset( &pb, sizeof(CInfoPBRec) );
	pb.hFileInfo.ioNamePtr = tempSpec.getName();
	pb.hFileInfo.ioVRefNum = tempSpec.getVRef();
	pb.hFileInfo.ioDirID = tempSpec.getParID();

	theErr = PBGetCatInfoSync( &pb );
	if ( theErr != kErrNoErr ) {
		tempSpec.dumpInfo( theErr, _TXL( "getDirID" ) );
		return theErr;
	}

	if ( ( pb.hFileInfo.ioFlAttrib & ioDirMask ) == 0 )
		return dirNFErr;

	*dirIDP = pb.dirInfo.ioDrDirID;

	return theErr;
}


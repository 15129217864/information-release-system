/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SFiles.h"
#include "CUtils.h"
#include <translation.h>
#include <string.h>
#include <Finder.h>
#include "MoreFiles.h"
#include "MoreFilesExtras.h"
#include "FullPath.h"
#include "CFilePath.h"
#include "Debugger.h"

#if defined(__osx__)
	#include "CFilePathOSX.h"
#endif

/*
	convert "/Macintosh%20HD/Photoshop%AC" to mac spec, setting 'theSpec'
*/


ErrCode SFiles::fullPathNameToSpec( const CStr *csFullPath, CFSpec *theSpec )
{
#if defined(__osx__)
	CFilePathOSX		cfPath( csFullPath );
#else
	CFilePath			cfPath( csFullPath );
#endif
	ErrCode				theErr;

	theErr = FSpLocationFromFullPath( cfPath.getLength(), cfPath.getBuf(), theSpec->getSpecP() );

	return theErr;
}

/*
	turn "BlosÄsom:Photoshopª" into "Blos%ACsom/Photoshop%C2"
	- java must prepend a '/'
	- the file must exist
*/

ErrCode SFiles::specToFullPathName( const CFSpec *theSpec, short *len, Handle *hFullPath )
{
	CFSpec		tempSpec( theSpec );
	Handle		h1, h2;
	ErrCode			theErr;
	long			i;
	short			outLen1, outLen2;
	char			*s;

	*len = 0;
	*hFullPath = NULL;

	theErr = tempSpec.verifySpec();
	if ( theErr != kErrNoErr )
		return theErr;

	theErr = FSpGetFullPath( tempSpec.getSpecP(), &outLen1, &h1 );
	if ( theErr != kErrNoErr )
		return theErr;

	theErr = CUtils::enQP( outLen1, h1, &outLen2, &h2 );
	DisposeHandle( h1 );
	if ( theErr != kErrNoErr )
		return theErr;

	HLock( h2 );
	s = *h2;
	for ( i = 0; i < outLen2; i++, s++ )
		if ( *s == ':' )
			*s = '/';
	HUnlock( h2 );

	*len = outLen2;
	*hFullPath = h2;

	return theErr;
}

ErrCode SFiles::getContainer( const CFSpec *theSpec, long *contParID, StringPtr contName )
{
	CInfoPBRec		pb;
	ErrCode				theErr;

	CUtils::zeroset( &pb, sizeof(CInfoPBRec) );
	pb.hFileInfo.ioNamePtr = contName;
	pb.hFileInfo.ioVRefNum = theSpec->getVRef();
	pb.hFileInfo.ioDirID = theSpec->getParID();
	pb.hFileInfo.ioFDirIndex = -1;

	theErr = PBGetCatInfoSync( &pb );
	if ( theErr != kErrNoErr ) {
		theSpec->dumpInfo( theErr, _TXL( "getContainer" ) );
		return theErr;
	}

	*contParID = pb.dirInfo.ioDrParID;

	return theErr;
}

ErrCode SFiles::setColorCoding( const CFSpec *theSpec, long newCoding )
{
	ErrCode					theErr;
	unsigned short		newVal;
	
	newVal = (unsigned short) newCoding;
	newVal &= 0x7;
	newVal <<= 1;

	theErr = setFinderFlags( theSpec, ~kColor, newVal );
	
	return theErr;
}

ErrCode SFiles::getFinderInfo( const CFSpec *theSpec,
										long *creator, long *type, long *flags, long *attribs )
{
	CInfoPBRec		pb;
	CFSpec			tempSpec( theSpec );
	ErrCode				theErr;

	CUtils::zeroset( &pb, sizeof(CInfoPBRec) );
	pb.hFileInfo.ioNamePtr = tempSpec.getName();
	pb.hFileInfo.ioVRefNum = tempSpec.getVRef();
	pb.hFileInfo.ioDirID = tempSpec.getParID();

	theErr = PBGetCatInfoSync( &pb );
	if ( theErr != kErrNoErr ) {
		tempSpec.dumpInfo( theErr, _TXL( "getFinderInfo" ) );
		return theErr;
	}

	*attribs = pb.hFileInfo.ioFlAttrib;

	if ( ( pb.hFileInfo.ioFlAttrib & ioDirMask ) == 0 ) {
		*flags = pb.hFileInfo.ioFlFndrInfo.fdFlags;
		*creator = pb.hFileInfo.ioFlFndrInfo.fdCreator;
		*type = pb.hFileInfo.ioFlFndrInfo.fdType;
	}
	else {
		*flags = pb.dirInfo.ioDrUsrWds.frFlags;
		*creator = 0;
		*type = 0;
	}

	return theErr;
}

//		directories: not changed
//		files: only change creator or type if non-zero

ErrCode SFiles::setCreatorAndType( const CFSpec *theSpec, long creator, long type, long flags, long attributes )
{
	CFSpec		tempSpec( theSpec );
	ErrCode			theErr;

	theErr = FSpChangeCreatorType( tempSpec.getSpecP(), creator, type );
	if ( theErr != kErrNoErr )
		theSpec->dumpInfo( theErr, _TXL( "setCreatorAndType.cct" ) );

	return theErr;

	UNUSED( flags );
	UNUSED( attributes );
}

ErrCode SFiles::setFileDate( const CFSpec *theSpec, CDateBundle *dateBundle )
{
	CFSpec			tempSpec( theSpec );
	CInfoPBRec		pb;
	ErrCode				theErr;

	pb.hFileInfo.ioNamePtr = tempSpec.getName();
	pb.hFileInfo.ioVRefNum = tempSpec.getVRef();
	pb.hFileInfo.ioDirID = tempSpec.getParID();
	pb.hFileInfo.ioFDirIndex = 0;

	theErr = PBGetCatInfoSync( &pb );
	if ( theErr != kErrNoErr ) {
		theSpec->dumpInfo( theErr, _TXL( "setFileDate1" ) );
		return theErr;
	}

	if ( ( pb.hFileInfo.ioFlAttrib & ioDirMask ) == 0 ) {
		dateBundle->getDate( CDateBundle::kModificationDate, &pb.hFileInfo.ioFlMdDat );
		dateBundle->getDate( CDateBundle::kCreationDate, &pb.hFileInfo.ioFlCrDat );
		dateBundle->getDate( CDateBundle::kBackupDate, &pb.hFileInfo.ioFlBkDat );
			
		pb.hFileInfo.ioDirID = tempSpec.getParID();
	}
	else {
		dateBundle->getDate( CDateBundle::kModificationDate, &pb.dirInfo.ioDrMdDat );
		dateBundle->getDate( CDateBundle::kCreationDate, &pb.dirInfo.ioDrCrDat );
		dateBundle->getDate( CDateBundle::kBackupDate, &pb.dirInfo.ioDrBkDat );
			
		pb.dirInfo.ioDrDirID = tempSpec.getParID();
	}

	theErr = PBSetCatInfoSync( &pb );
	if ( theErr != kErrNoErr )
		theSpec->dumpInfo( theErr, _TXL( "setFileDate2" ) );

	return theErr;
}

ErrCode SFiles::verifyFile( const CFSpec *theSpec )
{
	CFSpec		tempSpec( theSpec );

	return tempSpec.verifySpec();
}

ErrCode SFiles::getFileDate( const CFSpec *theSpec, CDateBundle *dateBundle )
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
		tempSpec.dumpInfo( theErr, _TXL( "getFileDate" ) );
		return theErr;
	}

	if ( ( pb.hFileInfo.ioFlAttrib & ioDirMask ) == 0 ) {
		theErr = dateBundle->setDate( CDateBundle::kModificationDate, pb.hFileInfo.ioFlMdDat );
		theErr = dateBundle->setDate( CDateBundle::kCreationDate, pb.hFileInfo.ioFlCrDat );
		theErr = dateBundle->setDate( CDateBundle::kBackupDate, pb.hFileInfo.ioFlBkDat );
	}
	else {
		theErr = dateBundle->setDate( CDateBundle::kModificationDate, pb.dirInfo.ioDrMdDat );
		theErr = dateBundle->setDate( CDateBundle::kCreationDate, pb.dirInfo.ioDrCrDat );
		theErr = dateBundle->setDate( CDateBundle::kBackupDate, pb.dirInfo.ioDrBkDat );
	}

	return theErr;
}

ErrCode SFiles::setFinderFlags( const CFSpec *theSpec,
										unsigned short firstAnd, unsigned short thenOr )
{
	CFSpec				tempSpec( theSpec );
	CInfoPBRec			pb;
	ErrCode					theErr;

	pb.hFileInfo.ioNamePtr = tempSpec.getName();
	if ( theSpec->isNameNull() )
		pb.hFileInfo.ioFDirIndex = -1;
	else
		pb.hFileInfo.ioFDirIndex = 0;

	pb.hFileInfo.ioVRefNum = tempSpec.getVRef();
	pb.hFileInfo.ioDirID = tempSpec.getParID();

	theErr = PBGetCatInfoSync( &pb );
	if ( theErr != kErrNoErr ) {
		theSpec->dumpInfo( theErr, _TXL( "setFinderFlags1" ) );
		return theErr;
	}

	if ( ( pb.hFileInfo.ioFlAttrib & ioDirMask ) == 0 ) {
		pb.hFileInfo.ioFlFndrInfo.fdFlags &= firstAnd;
		pb.hFileInfo.ioFlFndrInfo.fdFlags |= thenOr;
	}
	else {
		pb.dirInfo.ioDrUsrWds.frFlags &= firstAnd;
		pb.dirInfo.ioDrUsrWds.frFlags |= thenOr;
	}

	if ( theSpec->isNameNull() )
		pb.hFileInfo.ioDirID = pb.hFileInfo.ioFlParID;
	else
		pb.hFileInfo.ioDirID = theSpec->getParID();
	
	theErr = PBSetCatInfoSync( &pb );
	if ( theErr != kErrNoErr )
		theSpec->dumpInfo( theErr, _TXL( "setFinderFlags2" ) );

	return theErr;
}

ErrCode SFiles::getForkSizes( const CFSpec *theSpec, UnsignedWide *dataLen, UnsignedWide *rsrcLen )
{
	CFSpec		tempSpec( theSpec );
	ErrCode			theErr;
	long			data, rsrc;

	theErr = FSpGetFileSize( tempSpec.getSpecP(), &data, &rsrc );
	
	dataLen->hi = 0;
	dataLen->lo = data;
	rsrcLen->hi = 0;
	rsrcLen->lo = rsrc;
	
	return theErr;
}

ErrCode SFiles::renameFile( const CFSpec *theSpec, const CStr *csNewName,
									StringPtr outName, long outNameLen )
{
	CFilePath		cfNewName( csNewName );
	CFSpec			tempSpec( theSpec );
	ErrCode				theErr;

	theErr = tempSpec.verifySpec();
	if ( theErr != kErrNoErr )
		return theErr;
	
	cfNewName.getPStr( outName, outNameLen );

	theErr = FSpRename( tempSpec.getSpecP(), outName );
	
	return theErr;
}

ErrCode SFiles::updateContainer( const CFSpec *containedSpec )
{
	CFSpec			tempSpec( containedSpec );
	ErrCode				theErr;
	long				parParID;

	theErr = GetParentID( tempSpec.getVRef(), tempSpec.getParID(), tempSpec.getName(), &parParID );
	if ( theErr != kErrNoErr ) {
		tempSpec.dumpInfo( theErr, _TXL( "updateContainer" ) );
		return theErr;
	}

	if ( parParID != fsRtParID )
		theErr = BumpDate( tempSpec.getVRef(), parParID, NULL );

	return theErr;
}


ErrCode SFiles::getRawResourceFork( long flags, CFSpec *theSpec, char *buffer, long bufferLen )
{
	ErrCode		theErr;
	long		forkLen;
	short		refNum;

	theErr = FSpOpenRFAware( theSpec->getSpecP(), dmRd, &refNum );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "grrf.1" ), NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = GetEOF( refNum, &forkLen );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "grrf.2" ), NULL, NULL, theErr, 0 );
		FSClose( refNum );
		goto bail;
	}
	
	if ( bufferLen != forkLen ) {
		FSClose( refNum );
		theErr = -1;
		Debugger::debug( __LINE__, _TXL( "grrf.3" ), NULL, NULL, theErr, 0 );
		goto bail;
	}
	
	theErr = FSRead( refNum, &bufferLen, buffer );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "grrf.4" ), NULL, NULL, theErr, 0 );
	}
	
	FSClose( refNum );

bail:

	return theErr;

	UNUSED( flags );
}

ErrCode SFiles::setRawResourceFork( long flags, CFSpec *theSpec, char *buffer, long bufferLen )
{
	ErrCode		theErr;
	short		refNum;

	theErr = FSpOpenRFAware( theSpec->getSpecP(), dmWrDenyRdWr, &refNum );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "srrf.1" ), NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = SetEOF( refNum, 0 );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "srrf.2" ), NULL, NULL, theErr, 0 );
		FSClose( refNum );
		goto bail;
	}

	theErr = FSWrite( refNum, &bufferLen, buffer );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "srrf.3" ), NULL, NULL, theErr, 0 );
	}

	FSClose( refNum );

bail:

	return theErr;

	UNUSED( flags );
}

ErrCode SFiles::setForkLength( long flags, long whichFork, CFSpec *theSpec, UnsignedWide newLen )
{
	ErrCode		theErr;
	short		refNum;

	if ( whichFork != 1 )
		return kErrParamErr;

	theErr = FSpOpenRFAware( theSpec->getSpecP(), dmWrDenyRdWr, &refNum );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "sfl.1" ), NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = SetEOF( refNum, newLen.lo );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "sfl.2" ), NULL, NULL, theErr, 0 );
	}

	FSClose( refNum );

bail:

	return theErr;

	UNUSED( flags );
}


/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include <translation.h>
#include <Finder.h>
#include "SAliases.h"
#include "SFiles.h"
#include "MoreFiles.h"
#include "MoreFilesExtras.h"
#include "FullPath.h"
#include "CUtils.h"
#include "Debugger.h"

#if defined(__osx__)
	#include <LaunchServices.h>
#endif

ErrCode SAliases::resolveAnAlias( const CFSpec *inSpec, CFSpec *outSpec,
									tageAliasInteraction flags )
{
	CFSpec		tempInSpec( inSpec );
	ErrCode			theErr;
	BOOL			bTargetIsFolder, bWasAliased, bMountRemoteVols;

	theErr = tempInSpec.verifySpec();
	if ( theErr != kErrNoErr )
		goto bail;

	if ( flags == kResolveAliasNoUI )
		bMountRemoteVols = FALSE;
	else
		bMountRemoteVols = TRUE;

	theErr = resolveAliasFileMountOption( tempInSpec.getSpecP(), TRUE,
														&bTargetIsFolder, &bWasAliased, bMountRemoteVols );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "ran.rafmo", NULL, NULL, theErr, 0 );
		goto bail;
	}

	outSpec->setFrom( &tempInSpec );

bail:
	
	return theErr;
}

ErrCode SAliases::getFileCategory( const CFSpec *theSpec, eFileCategory *category )
{
	CInfoPBRec		pb;
	CFSpec			tempSpec( theSpec );
	ErrCode				theErr;

	*category = kCategoryUnknown;

#if defined(__osx__)
	theErr = getFileCategoryOSX( theSpec, category );
	if ( theErr == noErr )
		return noErr;
#endif

	theErr = tempSpec.verifySpec();
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "  didn't verify", NULL, NULL, theErr, 0 );
		return theErr;
	}

	if ( tempSpec.getParID() == fsRtParID ) {
		*category = kCategoryVolume;
		return kErrNoErr;
	}

	CUtils::zeroset( &pb, sizeof(CInfoPBRec) );
	pb.hFileInfo.ioNamePtr = tempSpec.getName();
	pb.hFileInfo.ioVRefNum = tempSpec.getVRef();
	pb.hFileInfo.ioDirID = tempSpec.getParID();

	theErr = PBGetCatInfoSync( &pb );
	if ( theErr != kErrNoErr ) {
		tempSpec.dumpInfo( theErr, _TXL( "getFileCategory" ) );
		return theErr;
	}

	if ( ( pb.hFileInfo.ioFlAttrib & ioDirMask ) != 0 ) {
		*category = kCategoryDirectory;
		return kErrNoErr;
	}

	if ( ( pb.hFileInfo.ioFlFndrInfo.fdFlags & kIsAlias ) != 0 ) {
		*category = kCategoryAlias;
	}
	else {
		*category = kCategoryFile;
	}

	return kErrNoErr;
}

	//	ALIAS FILE NO LONGER MUST EXIST
ErrCode SAliases::createAlias( const CFSpec *targetSpec, const CStr *csNewAliasPath,
										long creator, long flags )
{
	CFSpec		tempTargetSpec( targetSpec ), *newAliasSpec = NULL;
	long			aliasType, aliasCreator, gfiCreator, gfiType, gfiFlags, gfiAtr;
	ErrCode			theErr;

	theErr = tempTargetSpec.verifySpec();
	if ( theErr != kErrNoErr ) {
		tempTargetSpec.dumpInfo( theErr, _TXL( "createAlias.verifySpec " ) );
		goto bail;
	}

	theErr = SFiles::getFinderInfo( &tempTargetSpec, &gfiCreator, &gfiType, &gfiFlags, &gfiAtr );
	if ( theErr != kErrNoErr ) {
		tempTargetSpec.dumpInfo( theErr, _TXL( "createAlias.getFinderInfo " ) );
		goto bail;
	}

	if ( ( gfiAtr & ioDirMask ) == 0 ) {		//	alias to file
		if ( gfiType == 'APPL' )
			aliasType = kApplicationAliasType;
		else
			aliasType = gfiType;
		aliasCreator = gfiCreator;
	}
	else {		//	alias to folder
		aliasCreator = 'MACS';
		aliasType = kContainerFolderAliasType;
	}

	newAliasSpec = new CFSpec( 0, 0, NULL );

	theErr = SFiles::fullPathNameToSpec( csNewAliasPath, newAliasSpec );
	if ( theErr == fnfErr )
		theErr = kErrNoErr;
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "ca.fpts", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = makeAliasFile( &tempTargetSpec, newAliasSpec, aliasCreator, aliasType );

bail:

	if ( newAliasSpec != NULL )
		delete newAliasSpec;

	return theErr;

	UNUSED( flags );
	UNUSED( creator );
}

	//	ALIAS FILE NO LONGER MUST EXIST
ErrCode SAliases::createVolumeAlias( long targetVRef, const CStr *csNewAliasPath,
									long creator, long flags )
{
	CFSpec		*newAliasSpec, *targetSpec;
	ErrCode			theErr;

	targetSpec = new CFSpec( targetVRef, 0, NULL );
	theErr = targetSpec->verifySpec();
	if ( theErr != kErrNoErr ) {
		targetSpec->dumpInfo( theErr, _TXL( "cva.fsm" ) );
		goto bail;
	}

	newAliasSpec = new CFSpec( 0, 0, NULL );

	theErr = SFiles::fullPathNameToSpec( csNewAliasPath, newAliasSpec );
	if ( theErr == fnfErr )
		theErr = kErrNoErr;
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "cva.fpts", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = makeAliasFile( targetSpec, newAliasSpec, 'MACS', kContainerHardDiskAliasType );

bail:

	if ( targetSpec != NULL )
		delete targetSpec;

	if ( newAliasSpec != NULL )
		delete newAliasSpec;

	return theErr;

	UNUSED( flags );
	UNUSED( creator );
}

ErrCode SAliases::makeAliasFile( const CFSpec *targetSpec, const CFSpec *newAliasSpec,
											long creator, long type )
{
	HFileParam		pb;
	AliasHandle		aliasH;
	CFSpec			tempNewAliasSpec( newAliasSpec ), tempTargetSpec( targetSpec );
	ErrCode				theErr;
	Handle			existingAliasH;
	short				refNum;
	Boolean			bCreated;

	bCreated = false;

	theErr = FSpDelete( tempNewAliasSpec.getSpecP() );

	FSpCreateResFile( tempNewAliasSpec.getSpecP(), creator, type, smSystemScript );
	theErr = ResError();
	if ( theErr != kErrNoErr ) {
		tempNewAliasSpec.dumpInfo( theErr, _TXL( "maf.hcrf" ) );
		goto bail;
	}

	bCreated = true;

	CUtils::zeroset( &pb, sizeof(HFileParam) );
	pb.ioNamePtr = tempNewAliasSpec.getName();
	pb.ioVRefNum = tempNewAliasSpec.getVRef();
	pb.ioDirID = tempNewAliasSpec.getParID();
	theErr = PBHGetFInfoSync( (HParmBlkPtr) &pb );
	if ( theErr != kErrNoErr ) {
		tempNewAliasSpec.dumpInfo( theErr, _TXL( "maf.gfis" ) );
		goto bail;
	}

	pb.ioFlFndrInfo.fdCreator = creator;
	pb.ioFlFndrInfo.fdType = type;
	pb.ioFlFndrInfo.fdFlags |= 0x8000;
	pb.ioDirID = tempNewAliasSpec.getParID();
	pb.ioFVersNum = 0;
	theErr = PBHSetFInfoSync( (HParmBlkPtr) &pb );

	theErr = NewAlias( NULL, tempTargetSpec.getSpecP(), &aliasH );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "maf.na", NULL, NULL, theErr, 0 );
		goto bail;
	}
	if ( aliasH == NULL ) {
		Debugger::debug( __LINE__, "maf.na==NULL" );
		theErr = -1;
		goto bail;
	}

	refNum = FSpOpenResFile( tempNewAliasSpec.getSpecP(), fsRdWrPerm );
	if ( refNum > 0 ) {
		existingAliasH = Get1Resource( 'alis', 0 );
		if ( existingAliasH != NULL ) {
			RemoveResource( existingAliasH );
			DisposeHandle( existingAliasH );
		}

		AddResource( (Handle) aliasH, 'alis', 0, (ConstStr255Param) "\0" );
		WriteResource( (Handle) aliasH );
		CloseResFile( refNum );
	}
	else {
		tempNewAliasSpec.dumpInfo( ResError(), _TXL( "maf.orf" ) );
		if ( aliasH != NULL )
			DisposeHandle( (Handle) aliasH );
	}

bail:

	if ( theErr != kErrNoErr && bCreated )
		PBHDeleteSync( (HParmBlkPtr) &pb );

	return theErr;
}

//		the isAliasFile and resolveAliasFileMountOption routines are derived from Apple sample code

#if defined(macintosh)
ErrCode SAliases::isAliasFile( const FSSpec *fileFSSpec,
										Boolean *aliasFileFlag, Boolean *folderFlag )
{
	CInfoPBRec		pb;
	Str255			tempName;
	ErrCode				retCode;

	*aliasFileFlag = *folderFlag = false;
	
	CUtils::zeroset( &pb, sizeof(CInfoPBRec) );
	CUtils::pStrcpy( tempName, &( fileFSSpec->name[ 0 ] ) );

	pb.hFileInfo.ioNamePtr = tempName;
	pb.hFileInfo.ioVRefNum = fileFSSpec->vRefNum;
	pb.hFileInfo.ioDirID = fileFSSpec->parID;

	retCode = PBGetCatInfoSync( &pb );

	if ( retCode == kErrNoErr ) {
		if ( ( pb.hFileInfo.ioFlAttrib & ioDirMask ) != 0 )
			*folderFlag = true;
		else if ( ( pb.hFileInfo.ioFlFndrInfo.fdFlags & 0x8000) != 0 )
			*aliasFileFlag = true;
	}
	
	return retCode;
}

ErrCode SAliases::resolveAliasFileMountOption( FSSpec *fileFSSpec,
										 Boolean resolveAliasChains,
										 Boolean *targetIsFolder,
										 Boolean *wasAliased,
										 Boolean mountRemoteVols)
{
#define MAXCHAINS 10

	short myResRefNum;
	Handle alisHandle;
	FSSpec initFSSpec;
	Boolean updateFlag, foundFlag, wasAliasedTemp, specChangedFlag;
	short chainCount;
	ErrCode retCode;

	if ( fileFSSpec == nil || targetIsFolder == nil || wasAliased == nil )
		return paramErr;

	initFSSpec = *fileFSSpec;
	chainCount = MAXCHAINS;
	myResRefNum = -1;

	*targetIsFolder = foundFlag = specChangedFlag = false;
	
	do {
		chainCount--;

		retCode = isAliasFile( fileFSSpec, wasAliased, targetIsFolder );
		if ( retCode != kErrNoErr || !(*wasAliased) )
			break;

		myResRefNum = FSpOpenResFile( fileFSSpec, fsCurPerm );
		retCode = ResError();
		if ( myResRefNum == -1 ) {
			break;
		}
				

		alisHandle = Get1IndResource( rAliasType, 1 );
		retCode = ResError();
		if ( alisHandle == nil )
			break;
		
		LoadResource( alisHandle );
		retCode = ResError();
		if ( retCode != kErrNoErr )
			break;

		retCode = FollowFinderAlias( fileFSSpec, (AliasHandle) alisHandle, 
												mountRemoteVols, fileFSSpec, &updateFlag);
		
		if ( retCode == kErrNoErr ) {
			if (updateFlag) {
				ChangedResource( alisHandle );
				WriteResource( alisHandle );
			}
			
			specChangedFlag = true;
			
			retCode = isAliasFile( fileFSSpec, &wasAliasedTemp, targetIsFolder );
			if ( retCode == kErrNoErr ) 
				foundFlag = !( wasAliasedTemp && resolveAliasChains );
		}
		
		CloseResFile( myResRefNum );
		myResRefNum = -1;
		
	} while ( retCode == kErrNoErr && chainCount > 0 && !foundFlag );
	
	if (chainCount == 0 && !foundFlag)
		retCode = fnfErr;

	if ( myResRefNum != -1 )
		CloseResFile(myResRefNum);
	if ( retCode != kErrNoErr && specChangedFlag )
		*fileFSSpec = initFSSpec;
		
	return retCode;
}

#elif defined(__osx__)
ErrCode SAliases::resolveAliasFileMountOption( FSSpec *fileFSSpec,
										 Boolean resolveAliasChains,
										 Boolean *targetIsFolder,
										 Boolean *wasAliased,
										 Boolean mountRemoteVols )
{
	return ResolveAliasFileWithMountFlags( fileFSSpec, resolveAliasChains, targetIsFolder, wasAliased,
											mountRemoteVols ? kResolveAliasFileNoUI : 0 );
}
#endif

#if defined(__osx__)

ErrCode SAliases::getFileCategoryOSX( const CFSpec *theSpec, eFileCategory *category )
{
	FSRef					fsRef;
	LSItemInfoRecord		itemInfo;
	FSSpec					tempSpec;
	ErrCode					theErr;

	if ( LSFindApplicationForInfo == (void *) kUnresolvedCFragSymbolAddress ) {
		Debugger::debug( __LINE__, "getFileCategoryOSX: LS not available" );
		return paramErr;
	}

	theSpec->putInto( &tempSpec );

	theErr = FSpMakeFSRef( &tempSpec, &fsRef );
	if ( theErr != noErr ) {
		CStr	tempStr( tempSpec.name );
		Debugger::debug( __LINE__, "getFileCategoryOSX: FSpMakeFSRef err", &tempStr, NULL, theErr, 0 );
		return theErr;
	}

	theErr = LSCopyItemInfoForRef( &fsRef, kLSRequestAppTypeFlags, &itemInfo );
	if ( theErr != noErr ) {
		Debugger::debug( __LINE__, "getFileCategoryOSX: LS err", NULL, NULL, theErr, 0 );
		return theErr;
	}

	*category = (eFileCategory) ( kCategoryOSXExtendedInfoBit | (eFileCategory) itemInfo.flags );

	return theErr;
}

#endif

/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CUtils.h"
#include "SIConfig.h"
#include "SAppFinder.h"
#include "Debugger.h"

#if defined(macintosh)

ErrCode SIConfig::iCStart( OSType creator, OurDataH ourDataH )
{
	ICError			theErr;
	Handle			tempMappings;
	ICInstance		tempInstance;
	ICAttr			attr;

	tempMappings = NULL;
	tempInstance = NULL;
	( *ourDataH )->mappings = NULL;
	( *ourDataH )->instance = NULL;

	theErr = ICStart( &tempInstance, creator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCStart.ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICFindConfigFile( tempInstance, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCStart.FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}

	tempMappings = NewHandleSysClear( 0 );
	if ( tempMappings == NULL ) {
		Debugger::debug( __LINE__, "iCStart, tempM==NULL" );
		theErr = memFullErr;
		goto bail;
	}

	theErr = ICFindPrefHandle( tempInstance, kICMapping, &attr, tempMappings );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "iCStart.ICGetPrefHandle", NULL, NULL, theErr, 0 );
	
bail:

	if ( theErr != kErrNoErr ) {
		if ( tempInstance != NULL )
			ICStop( tempInstance );
		if ( tempMappings != NULL )
			DisposeHandle( tempMappings );
	}
	else {
		( *ourDataH )->instance = tempInstance;
		( *ourDataH )->mappings = tempMappings;
	}

	return theErr;
}

void SIConfig::iCStop( OurDataH ourDataH )
{
	if ( ourDataH == NULL )
		return;

	if ( (*ourDataH)->instance != NULL ) {		
		ICStop( (*ourDataH)->instance );
		(*ourDataH)->instance = NULL;
	}
	
	if ( (*ourDataH)->mappings != NULL ) {
		DisposeHandle( (*ourDataH)->mappings );
		(*ourDataH)->mappings = NULL;
	}

	DisposeHandle( (Handle) ourDataH );
}

ErrCode SIConfig::iCGetSeed( OurDataH ourDataH, long *seedP )
{
	ErrCode			theErr;

	if ( ourDataH == NULL || (*ourDataH)->instance == NULL ) {
		Debugger::debug( __LINE__, "iCGetSeed", NULL, NULL, (long) ourDataH,
						(long) (*ourDataH)->instance, (long) (*ourDataH)->mappings );
		return paramErr;
	}
	
	theErr = ICGetSeed( (*ourDataH)->instance, seedP );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "iCGetSeed.ICGetSeed", NULL, NULL, theErr, 0 );
	
	return theErr;
}

ErrCode SIConfig::iCGetIndMapEntry( OurDataH ourDataH, long whichRecord, ICMapEntry *mapEntryP )
{
	ErrCode			theErr;
	long			junk;

	if ( ourDataH == NULL || (*ourDataH)->instance == NULL || (*ourDataH)->mappings == NULL ) {
		Debugger::debug( __LINE__, "iCGetIndMapEntry", NULL, NULL, (long) ourDataH,
						(long) (*ourDataH)->instance, (long) (*ourDataH)->mappings );
		return paramErr;
	}

	theErr = ICGetIndMapEntry( (*ourDataH)->instance, (*ourDataH)->mappings,
										whichRecord, &junk, mapEntryP );
	
	return theErr;
}

ErrCode SIConfig::iCCountMapEntries( OurDataH ourDataH, long *countP )
{
	ErrCode			theErr;

	*countP = 0;

	if ( ourDataH == NULL || (*ourDataH)->instance == NULL || (*ourDataH)->mappings == NULL ) {
		Debugger::debug( __LINE__, "iCCountMapEntries", NULL, NULL, (long) ourDataH,
						(long) (*ourDataH)->instance, (long) (*ourDataH)->mappings );
		return paramErr;
	}

	theErr = ICCountMapEntries( (*ourDataH)->instance, (*ourDataH)->mappings, countP );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "iCCountMapEntries.ICCountMapEntries", NULL, NULL, theErr, 0 );
	
	return theErr;
}

ErrCode SIConfig::iCLaunchURL( long appCreator, const CStr *csURL )
{
	ICError			theErr;
	ICInstance		instanceH;
	long				urlLen, selStart, selEnd;

	instanceH = NULL;

	theErr = ICStart( &instanceH, appCreator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCLaunchURL: ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICFindConfigFile( instanceH, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCLaunchURL: FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}

	urlLen = csURL->getLength();
	selStart = 0;
	selEnd = urlLen;

	theErr = ICLaunchURL( instanceH, "\p", csURL->getBuf(), urlLen, &selStart, &selEnd );
	Debugger::debug( __LINE__, "iCLaunchURL.ICLaunchURL", csURL, NULL, theErr, urlLen );

bail:

	if ( instanceH != NULL )
		ICStop( instanceH );

	return theErr;
}

ErrCode SIConfig::iCFindMatchesFInfo( long appCreator, long targetCreator, long targetType,
										long direction, long *numReturned,
										long maxToReturn, StringPtr retData )
{
	ICError			theErr;
	ICInstance		instanceH;
	ICAttr			attr;
	ICMapEntry		theEntry;
	Handle			tempMappings;
	long			numEntries, junk, numWritten, whichRecord;

	instanceH = NULL;
	tempMappings = NULL;
	*numReturned = 0;

	theErr = ICStart( &instanceH, appCreator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo: ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICFindConfigFile( instanceH, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo: FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}

	tempMappings = NewHandleSysClear( 0 );
	if ( tempMappings == NULL ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo, tempM==NULL" );
		theErr = memFullErr;
		goto bail;
	}

	theErr = ICFindPrefHandle( instanceH, kICMapping, &attr, tempMappings );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo: ICGetPrefHandle", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICCountMapEntries( instanceH, tempMappings, &numEntries );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo.ICCountMapEntries", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( whichRecord = 1, numWritten = 0; whichRecord <= numEntries && numWritten < maxToReturn; whichRecord++ ) {
		theErr = ICGetIndMapEntry( instanceH, tempMappings, whichRecord, &junk, &theEntry );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "iCFindMatchesFInfo.ICGetIndMapEntry", NULL, NULL, theErr, whichRecord );
			goto bail;
		}

		if ( ( theEntry.flags & ICmap_not_outgoing_mask ) != 0 )
			continue;

		if ( theEntry.file_creator == targetCreator && theEntry.file_type == targetType ) {
			CUtils::pStrncpy( retData, &theEntry.extension[ 0 ], kMaxExtensionLength - 1 );
			retData += kMaxExtensionLength;
			++numWritten;
		}
	}

	*numReturned = numWritten;

bail:

	if ( instanceH != NULL )
		ICStop( instanceH );

	if ( tempMappings != NULL )
		DisposeHandle( tempMappings );
	
	return theErr;

	UNUSED( direction );
}

ErrCode SIConfig::iCFindMatchesExt( long appCreator, const CStr *csExtension,
						long direction, long *numReturned,
						long maxToReturn, long *cValsP, long *tValsP )
{
	ICError			theErr;
	ICInstance		instanceH;
	ICAttr			attr;
	ICMapEntry		theEntry;
	Handle			tempMappings;
	char				tempEntryExtension[ 256 ];
	long			numEntries, junk, numWritten, whichRecord;

	instanceH = NULL;
	tempMappings = NULL;
	*numReturned = 0;

	theErr = ICStart( &instanceH, appCreator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt: ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICFindConfigFile( instanceH, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt: FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}

	tempMappings = NewHandleSysClear( 0 );
	if ( tempMappings == NULL ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt, tempM==NULL" );
		theErr = memFullErr;
		goto bail;
	}

	theErr = ICFindPrefHandle( instanceH, kICMapping, &attr, tempMappings );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt: ICGetPrefHandle", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICCountMapEntries( instanceH, tempMappings, &numEntries );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt.ICCountMapEntries", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( whichRecord = 1, numWritten = 0; whichRecord <= numEntries && numWritten < maxToReturn; whichRecord++ ) {
		theErr = ICGetIndMapEntry( instanceH, tempMappings, whichRecord, &junk, &theEntry );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "iCFindMatchesExt.ICGetIndMapEntry", NULL, NULL, theErr, whichRecord );
			goto bail;
		}

		if ( ( theEntry.flags & ICmap_not_incoming_mask ) != 0 )
			continue;

		CUtils::pStrToLowerCString( &tempEntryExtension[ 0 ], theEntry.extension );

		if ( csExtension->isEqual( &tempEntryExtension[ 0 ] ) ) {
			*cValsP++ = theEntry.file_creator;
			*tValsP++ = theEntry.file_type;
			++numWritten;
		}
	}

	*numReturned = numWritten;

bail:

	if ( instanceH != NULL )
		ICStop( instanceH );

	if ( tempMappings != NULL )
		DisposeHandle( tempMappings );
	
	return theErr;

	UNUSED( direction );
}

ErrCode SIConfig::iCFindAppByName( long appCreator, const CStr *csName, long *creatorP,
									long *vRefP, long *parIDP, StringPtr namesP,
									long maxToReturn, long *numReturned )
{
	ICError			theErr;
	ICInstance		instanceH;
	ICAttr			attr;
	ICMapEntry		theEntry;
	CStr			lowerMatchName( csName );
	Handle			tempMappings;
	long			numEntries, junk, numWritten, whichRecord, vRef, parID, i, *origCreatorP;
	char			entryName[ 256 ];
	Str255			fileName;
	Boolean			bFound;

	instanceH = NULL;
	tempMappings = NULL;
	*numReturned = 0;
	origCreatorP = creatorP;
	lowerMatchName.toLower();

	theErr = ICStart( &instanceH, appCreator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindAppByName: ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICFindConfigFile( instanceH, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindAppByName: FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}

	tempMappings = NewHandleSysClear( 0 );
	if ( tempMappings == NULL ) {
		Debugger::debug( __LINE__, "iCFindAppByName, tempM==NULL" );
		theErr = memFullErr;
		goto bail;
	}

	theErr = ICFindPrefHandle( instanceH, kICMapping, &attr, tempMappings );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindAppByName: ICGetPrefHandle", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICCountMapEntries( instanceH, tempMappings, &numEntries );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindAppByName.ICCountMapEntries", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( whichRecord = 1, numWritten = 0;
	whichRecord <= numEntries && numWritten < maxToReturn; whichRecord++ ) {
		theErr = ICGetIndMapEntry( instanceH, tempMappings, whichRecord, &junk, &theEntry );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "iCFindAppByName.ICGetIndMapEntry", NULL, NULL, theErr, whichRecord );
			goto bail;
		}

		CUtils::pStrToLowerCString( entryName, theEntry.creator_app_name );
		if ( lowerMatchName.isSubstringOf( entryName ) ) {
			for ( i = 0, bFound = false; i < numWritten; i++ ) {
				if ( origCreatorP[ i ] == theEntry.file_creator )
					bFound = true;
			}

			if ( !bFound ) {
				theErr = SAppFinder::findAPPLSingle( theEntry.file_creator, &vRef, &parID, &fileName[ 0 ], false );
				if ( theErr == kErrNoErr ) {
					*creatorP++ = theEntry.file_creator;
					*vRefP++ = vRef;
					*parIDP++ = parID;
					CUtils::pStrncpy( namesP, &fileName[ 0 ], kFindAppByNameMaxNameLen - 1 );
					namesP += kFindAppByNameMaxNameLen;
					++numWritten;
				}
			}
		}
	}

	*numReturned = numWritten;

bail:

	if ( instanceH != NULL )
		ICStop( instanceH );

	if ( tempMappings != NULL )
		DisposeHandle( tempMappings );
	
	return theErr;
}

#elif defined(__osx__)

ErrCode SIConfig::iCStart( OSType creator, OurDataH ourDataH )
{
	OSStatus			theErr;
	Handle			tempMappings;
	ICInstance		tempInstance;
	ICAttr			attr;

	tempMappings = NULL;
	tempInstance = NULL;
	( *ourDataH )->mappings = NULL;
	( *ourDataH )->instance = NULL;

	theErr = ICStart( &tempInstance, creator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCStart.ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

	tempMappings = NewHandleClear( 0 );
	if ( tempMappings == NULL ) {
		Debugger::debug( __LINE__, "iCStart, tempM==NULL" );
		theErr = memFullErr;
		goto bail;
	}

	theErr = ICFindPrefHandle( tempInstance, kICMapping, &attr, tempMappings );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "iCStart.ICGetPrefHandle", NULL, NULL, theErr, 0 );
	
bail:

	if ( theErr != kErrNoErr ) {
		if ( tempInstance != NULL )
			ICStop( tempInstance );
		if ( tempMappings != NULL )
			DisposeHandle( tempMappings );
	}
	else {
		( *ourDataH )->instance = tempInstance;
		( *ourDataH )->mappings = tempMappings;
	}

	return theErr;
}

void SIConfig::iCStop( OurDataH ourDataH )
{
	if ( ourDataH == NULL )
		return;

	if ( (*ourDataH)->instance != NULL ) {		
		ICStop( (*ourDataH)->instance );
		(*ourDataH)->instance = NULL;
	}
	
	if ( (*ourDataH)->mappings != NULL ) {
		DisposeHandle( (*ourDataH)->mappings );
		(*ourDataH)->mappings = NULL;
	}

	DisposeHandle( (Handle) ourDataH );
}

ErrCode SIConfig::iCGetSeed( OurDataH ourDataH, long *seedP )
{
	ErrCode			theErr;

	if ( ourDataH == NULL || (*ourDataH)->instance == NULL ) {
		Debugger::debug( __LINE__, "iCGetSeed", NULL, NULL, (long) ourDataH,
						(long) (*ourDataH)->instance, (long) (*ourDataH)->mappings );
		return paramErr;
	}
	
	theErr = ICGetSeed( (*ourDataH)->instance, seedP );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "iCGetSeed.ICGetSeed", NULL, NULL, theErr, 0 );
	
	return theErr;
}

ErrCode SIConfig::iCGetIndMapEntry( OurDataH ourDataH, long whichRecord, ICMapEntry *mapEntryP )
{
	ErrCode			theErr;
	long			junk;

	if ( ourDataH == NULL || (*ourDataH)->instance == NULL || (*ourDataH)->mappings == NULL ) {
		Debugger::debug( __LINE__, "iCGetIndMapEntry", NULL, NULL, (long) ourDataH,
						(long) (*ourDataH)->instance, (long) (*ourDataH)->mappings );
		return paramErr;
	}

	theErr = ICGetIndMapEntry( (*ourDataH)->instance, (*ourDataH)->mappings,
										whichRecord, &junk, mapEntryP );
	
	return theErr;
}

ErrCode SIConfig::iCCountMapEntries( OurDataH ourDataH, long *countP )
{
	ErrCode			theErr;

	*countP = 0;

	if ( ourDataH == NULL || (*ourDataH)->instance == NULL || (*ourDataH)->mappings == NULL ) {
		Debugger::debug( __LINE__, "iCCountMapEntries", NULL, NULL, (long) ourDataH,
						(long) (*ourDataH)->instance, (long) (*ourDataH)->mappings );
		return paramErr;
	}

	theErr = ICCountMapEntries( (*ourDataH)->instance, (*ourDataH)->mappings, countP );
	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "iCCountMapEntries.ICCountMapEntries", NULL, NULL, theErr, 0 );
	
	return theErr;
}

ErrCode SIConfig::iCLaunchURL( long appCreator, const CStr *csURL )
{
	OSStatus			theErr;
	ICInstance		instanceH;
	long				urlLen, selStart, selEnd;

	instanceH = NULL;

	theErr = ICStart( &instanceH, appCreator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCLaunchURL: ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

/*
	theErr = ICFindConfigFile( instanceH, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCLaunchURL: FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}
*/

	urlLen = csURL->getLength();
	selStart = 0;
	selEnd = urlLen;

	theErr = ICLaunchURL( instanceH, "\p", csURL->getBuf(), urlLen, &selStart, &selEnd );
	Debugger::debug( __LINE__, "iCLaunchURL.ICLaunchURL", csURL, NULL, theErr, urlLen );

bail:

	if ( instanceH != NULL )
		ICStop( instanceH );

	return theErr;
}

ErrCode SIConfig::iCFindMatchesFInfo( long appCreator, long targetCreator, long targetType,
										long direction, long *numReturned,
										long maxToReturn, StringPtr retData )
{
	OSStatus			theErr;
	ICInstance		instanceH;
	ICAttr			attr;
	ICMapEntry		theEntry;
	Handle			tempMappings;
	long			numEntries, junk, numWritten, whichRecord;

	instanceH = NULL;
	tempMappings = NULL;
	*numReturned = 0;

	theErr = ICStart( &instanceH, appCreator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo: ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

/*
	theErr = ICFindConfigFile( instanceH, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo: FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}
*/

	tempMappings = NewHandleClear( 0 );
	if ( tempMappings == NULL ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo, tempM==NULL" );
		theErr = memFullErr;
		goto bail;
	}

	theErr = ICFindPrefHandle( instanceH, kICMapping, &attr, tempMappings );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo: ICGetPrefHandle", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICCountMapEntries( instanceH, tempMappings, &numEntries );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesFInfo.ICCountMapEntries", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( whichRecord = 1, numWritten = 0; whichRecord <= numEntries && numWritten < maxToReturn; whichRecord++ ) {
		theErr = ICGetIndMapEntry( instanceH, tempMappings, whichRecord, &junk, &theEntry );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "iCFindMatchesFInfo.ICGetIndMapEntry", NULL, NULL, theErr, whichRecord );
			goto bail;
		}

		if ( ( theEntry.flags & kICMapNotOutgoingMask ) != 0 )
			continue;

		if ( theEntry.fileCreator == (OSType) targetCreator && theEntry.fileType == (OSType) targetType ) {
			CUtils::pStrncpy( retData, &theEntry.extension[ 0 ], kMaxExtensionLength - 1 );
			retData += kMaxExtensionLength;
			++numWritten;
		}
	}

	*numReturned = numWritten;

bail:

	if ( instanceH != NULL )
		ICStop( instanceH );

	if ( tempMappings != NULL )
		DisposeHandle( tempMappings );
	
	return theErr;

	UNUSED( direction );
}

void putHex( char val, char *msg )
{
	char		temp;

	temp = 0x0F & ( val >> 4 );
	if ( temp <= 9 )
		temp += '0';
	else
		temp += 'A';
	*msg++ = temp;

	temp = 0x0F & val;
	if ( temp <= 9 )
		temp += '0';
	else
		temp += 'A';

	*msg++ = temp;
	
	*msg++ = ' ';
}

void doLine( char *ss, char *msg )
{
	int			i;

	for ( i = 0; i < 8; i++, msg += 3 )
		putHex( ss[ i ], msg );

	for ( i = 0; i < 4; i++, msg++ )
		*msg = ' ';

	for ( i = 0; i < 8; i++, msg++ )
		*msg = ss[ i ] >= 32 ? ss[ i ] : '.';
	
	*msg = 0;
}

ErrCode SIConfig::iCFindMatchesExt( long appCreator, const CStr *csExtension,
						long direction, long *numReturned,
						long maxToReturn, long *cValsP, long *tValsP )
{
	OSStatus			theErr;
	ICInstance		instanceH;
	ICAttr			attr;
	ICMapEntry		theEntry;
	Handle			tempMappings;
	char				tempEntryExtension[ 256 ];
	long			numEntries, junk, numWritten, whichRecord;

	instanceH = NULL;
	tempMappings = NULL;
	*numReturned = 0;

	theErr = ICStart( &instanceH, appCreator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt: ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

/*
	theErr = ICFindConfigFile( instanceH, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt: FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}
*/

	tempMappings = NewHandleClear( 0 );
	if ( tempMappings == NULL ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt, tempM==NULL" );
		theErr = memFullErr;
		goto bail;
	}

	theErr = ICFindPrefHandle( instanceH, kICMapping, &attr, tempMappings );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt: ICGetPrefHandle", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICCountMapEntries( instanceH, tempMappings, &numEntries );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindMatchesExt.ICCountMapEntries", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( whichRecord = 1, numWritten = 0; whichRecord <= numEntries && numWritten < maxToReturn; whichRecord++ ) {
		theErr = ICGetIndMapEntry( instanceH, tempMappings, whichRecord, &junk, &theEntry );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "iCFindMatchesExt.ICGetIndMapEntry", NULL, NULL, theErr, whichRecord );
			goto bail;
		}

		if ( ( theEntry.flags & kICMapNotIncomingMask ) != 0 )
			continue;

		CUtils::pStrToLowerCString( &tempEntryExtension[ 0 ], theEntry.extension );

		if ( csExtension->isEqual( &tempEntryExtension[ 0 ] ) ) {
			*cValsP++ = theEntry.fileCreator;
			*tValsP++ = theEntry.fileType;
			++numWritten;
		}
	}

	*numReturned = numWritten;

bail:

	if ( instanceH != NULL )
		ICStop( instanceH );

	if ( tempMappings != NULL )
		DisposeHandle( tempMappings );
	
	return theErr;

	UNUSED( direction );
}

ErrCode SIConfig::iCFindAppByName( long appCreator, const CStr *csName, long *creatorP,
									long *vRefP, long *parIDP, StringPtr namesP,
									long maxToReturn, long *numReturned )
{
	OSStatus			theErr;
	ICInstance		instanceH;
	ICAttr			attr;
	ICMapEntry		theEntry;
	CStr			lowerMatchName( csName );
	Handle			tempMappings;
	long			numEntries, junk, numWritten, whichRecord, vRef, parID, i, *origCreatorP;
	char			entryName[ 256 ];
	Str255			fileName;
	Boolean			bFound;

	instanceH = NULL;
	tempMappings = NULL;
	*numReturned = 0;
	origCreatorP = creatorP;
	lowerMatchName.toLower();

	theErr = ICStart( &instanceH, appCreator );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindAppByName: ICStart", NULL, NULL, theErr, 0 );
		goto bail;
	}

/*
	theErr = ICFindConfigFile( instanceH, 0, nil );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindAppByName: FindConfigFile", NULL, NULL, theErr, 0 );
		goto bail;
	}
*/

	tempMappings = NewHandleClear( 0 );
	if ( tempMappings == NULL ) {
		Debugger::debug( __LINE__, "iCFindAppByName, tempM==NULL" );
		theErr = memFullErr;
		goto bail;
	}

	theErr = ICFindPrefHandle( instanceH, kICMapping, &attr, tempMappings );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindAppByName: ICGetPrefHandle", NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = ICCountMapEntries( instanceH, tempMappings, &numEntries );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "iCFindAppByName.ICCountMapEntries", NULL, NULL, theErr, 0 );
		goto bail;
	}

	for ( whichRecord = 1, numWritten = 0;
	whichRecord <= numEntries && numWritten < maxToReturn; whichRecord++ ) {
		theErr = ICGetIndMapEntry( instanceH, tempMappings, whichRecord, &junk, &theEntry );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, "iCFindAppByName.ICGetIndMapEntry", NULL, NULL, theErr, whichRecord );
			goto bail;
		}

		CUtils::pStrToLowerCString( entryName, theEntry.creatorAppName );
		if ( lowerMatchName.isSubstringOf( entryName ) ) {
			for ( i = 0, bFound = false; i < numWritten; i++ ) {
				if ( (OSType) origCreatorP[ i ] == theEntry.fileCreator )
					bFound = true;
			}

			if ( !bFound ) {
				theErr = SAppFinder::findAPPLSingle( theEntry.fileCreator, &vRef, &parID, &fileName[ 0 ], false );
				if ( theErr == kErrNoErr ) {
					*creatorP++ = theEntry.fileCreator;
					*vRefP++ = vRef;
					*parIDP++ = parID;
					CUtils::pStrncpy( namesP, &fileName[ 0 ], kFindAppByNameMaxNameLen - 1 );
					namesP += kFindAppByNameMaxNameLen;
					++numWritten;
				}
			}
		}
	}

	*numReturned = numWritten;

bail:

	if ( instanceH != NULL )
		ICStop( instanceH );

	if ( tempMappings != NULL )
		DisposeHandle( tempMappings );
	
	return theErr;
}

#endif



/*
	Debugger::debug( __LINE__, "iCFindMatchesExt NUM ENTRIES, handle size", NULL, NULL, numEntries, GetHandleSize(tempMappings) );
	Debugger::debug( __LINE__, "iCFindMatchesExt NUM ENTRIES, *handle", NULL, NULL, 0, (long) *tempMappings );

	char	msg[ 100 ];
	char	*ss;
	ss = *tempMappings;
	Debugger::debug( 0, "---- START HANDLE ----" );
	for ( int i = 0; i < 64; i++ ) {
		doLine( ss, msg );
		Debugger::debug( 0, msg );
		ss += 8;
	}
	Debugger::debug( 0, "---- END HANDLE ----" );

*/

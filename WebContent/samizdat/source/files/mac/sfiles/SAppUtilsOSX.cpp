/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SAppUtilsOSX.h"
#include "Debugger.h"

#if defined(__osx__)
	#include <LaunchServices.h>
#endif

ErrCode SAppUtilsOSX::getPOSIXPath( CFSpec *spec, CStr *outPath )
{
	CFURLRef		cfurlRef;
	CFStringRef		csfOutPath;
	FSRef			fsRef;
	ErrCode			theErr = kErrNoErr;
	BOOL			bResult;

	if ( spec == NULL || outPath == NULL )
		return kErrParamErr;

	outPath->ensureCharCapacity( CStr::kMaxPath );

	theErr = FSpMakeFSRef( spec->getSpecP(), &fsRef );
	if ( theErr != noErr ) {
		spec->dumpInfo( theErr, "getPOSIXPath: FSpMakeFSRef err" );
		goto bail;
	}

	cfurlRef = CFURLCreateFromFSRef( kCFAllocatorDefault, &fsRef );
	if ( cfurlRef == NULL ) {
		theErr = paramErr;
		goto bail;
	}

	csfOutPath = CFURLCopyFileSystemPath( cfurlRef, kCFURLPOSIXPathStyle );
	if ( csfOutPath == NULL ) {
		theErr = paramErr;
		goto bail;
	}

	bResult = CFStringGetCString( csfOutPath, outPath->getBuf(), outPath->getCharCapacity() - 1, kCFStringEncodingUTF8 );
	if ( !bResult )
		theErr = paramErr;

bail:

	return theErr;
}

ErrCode SAppUtilsOSX::getProcessBundleLocation( ProcessSerialNumber *psn, FSSpec *spec )
{
	FSRef			fsRef;
	ErrCode			theErr;
	
	theErr = GetProcessBundleLocation( psn, &fsRef );
	if ( theErr != noErr )
		goto bail;
	
	theErr = FSGetCatalogInfo( &fsRef, kFSCatInfoNone, NULL, NULL, spec, NULL );

bail:

	return theErr;
}

ErrCode SAppUtilsOSX::findApplicationForInfo( OSType inCreator, const CStr *inBundleID, const CStr *inName, FSSpec *outSpec )
{
	CFStringRef			cfsBundleID = NULL, cfsName = NULL;
	FSRef				fsRef;
	ErrCode				theErr;

	if ( inBundleID != NULL )
		cfsBundleID = CFStringCreateWithCString( NULL, inBundleID->getBuf(), kCFStringEncodingUTF8 );

	if ( inName != NULL )
		cfsName = CFStringCreateWithCString( NULL, inName->getBuf(), kCFStringEncodingUTF8 );

	theErr = LSFindApplicationForInfo( inCreator, cfsBundleID, cfsName, &fsRef, NULL );
	if ( theErr != noErr )
		goto bail;
	
	theErr = FSGetCatalogInfo( &fsRef, kFSCatInfoNone, NULL, NULL, outSpec, NULL );

bail:

	if ( cfsBundleID != NULL )
		CFRelease( cfsBundleID );

	if ( cfsName != NULL )
		CFRelease( cfsName );

	return theErr;
}

ErrCode SAppUtilsOSX::getApplicationForInfo( OSType inType, OSType inCreator, const CStr *inExtension, long roleMask, FSSpec *outSpec )
{
	CFStringRef			cfsExtension = NULL;
	FSRef				fsRef;
	ErrCode				theErr;

	if ( inExtension != NULL )
		cfsExtension = CFStringCreateWithCString( NULL, inExtension->getBuf(), kCFStringEncodingUTF8 );

	theErr = LSGetApplicationForInfo( inType, inCreator, cfsExtension, (LSRolesMask) roleMask, &fsRef, NULL );
	if ( theErr != noErr )
		goto bail;
	
	theErr = FSGetCatalogInfo( &fsRef, kFSCatInfoNone, NULL, NULL, outSpec, NULL );

bail:

	if ( cfsExtension != NULL )
		CFRelease( cfsExtension );

	return theErr;
}



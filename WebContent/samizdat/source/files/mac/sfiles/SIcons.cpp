/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SIcons.h"
#include "MoreFiles.h"
#include "MoreFilesExtras.h"
#include "FullPath.h"

#if defined(macintosh)
#include "find_icon.h"
#include "DTGetIconSuite.h"
#endif

#include "CUtils.h"
#include "Debugger.h"

#if defined(__osx__)
	#include <Icons.h>
#endif

ErrCode SIcons::getVolumeIconSuite( long vRef, long selector, Handle *hSuite )
{
	FSSpec		theSpec;
	ErrCode			theErr;
	SInt16			label;

	*hSuite = NULL;

	theErr = FSMakeFSSpec( vRef, 0, NULL, &theSpec );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, "getVolumeIconSuite.fsm", NULL, NULL, theErr, vRef );
		return theErr;
	}

#if defined(macintosh)
	theErr = Find_icon( &theSpec, NULL, kSelectorAllAvailableData, hSuite );
#elif defined(__osx__)
	theErr = GetIconRefFromFile( &theSpec, (IconRef*) hSuite, &label );
#endif

	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "getVolumeIconSuite.fi", NULL, NULL, theErr, vRef );
	
	return theErr;

	UNUSED( selector );
}

ErrCode SIcons::getFileIconSuite( const CFSpec *theSpec, long selector, Handle *hSuite )
{
	CFSpec		tempSpec( theSpec );
	ErrCode			theErr;
	SInt16			label;

	*hSuite = NULL;

	theErr = tempSpec.verifySpec();
	if ( theErr != kErrNoErr ) {
		tempSpec.dumpInfo( theErr, _TXL( "getFileIconSuite.fsm" ) );
		return theErr;
	}

#if defined(macintosh)
	theErr = Find_icon( tempSpec.getSpecP(), NULL, kSelectorAllAvailableData, hSuite );
#elif defined(__osx__)
	theErr = GetIconRefFromFile( tempSpec.getSpecP(), (IconRef*) hSuite, &label );
#endif

	if ( theErr != kErrNoErr )
		tempSpec.dumpInfo( theErr, _TXL( "getFileIconSuite.fi" ) );

	return theErr;

	UNUSED( selector );
}

ErrCode SIcons::getFTACIconSuite( long vRef, long creator, long type, long selector, Handle *hSuite )
{
	ErrCode			theErr;

	*hSuite = NULL;

#if defined(macintosh)
	theErr = DTGetIconSuite( vRef, kSelectorAllAvailableData, creator, type, hSuite );
#elif defined(__osx__)
	theErr = GetIconRef( vRef, creator, type, (IconRef*) hSuite );
#endif

	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "getFTACIconSuite.dt", NULL, NULL, theErr, creator, type );

	return theErr;

	UNUSED( selector );
}

ErrCode SIcons::disposeAnIconSuite( Handle hSuite, long flags )
{
	ErrCode			theErr;

	if ( hSuite == NULL )
		return paramErr;

#if defined(macintosh)
	theErr = DisposeIconSuite( hSuite, (Boolean) flags );
#elif defined(__osx__)
	ReleaseIconRef( (IconRef) hSuite );
#endif

	if ( theErr != kErrNoErr )	
		Debugger::debug( __LINE__, "disposing of icon suite", NULL, NULL, (long) hSuite, theErr );
	
	return theErr;
}

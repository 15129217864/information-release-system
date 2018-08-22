/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SIConfig_H
#define INC_SIConfig_H

#if defined(macintosh)
	#include "ICAPI.h"
#elif defined(__osx__)
	#include <InternetConfig.h>
#endif

#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	SIConfig

	An interface to Internet Config

DESCRIPTION
	An interface to Internet Config

------------------------------------------------------------------------*/

class SIConfig
{
public:

	typedef struct {
		ICInstance		instance;
		Handle			mappings;
	} OurData, *OurDataP, **OurDataH;


		///////////////////////
		//
		//	Starts an Internet Config session. 'ourDataH' must have been created, and will be
		//	resized and initialized for use in the other routines of this class which take
		//	a OurDataH argument
		//
		//	[in]	appCreator	the creator code of the app calling this routine
		//	[out]	ourDataH	on return, will be initialized for use with the other routines
		//
	static	ErrCode iCStart( OSType creator, OurDataH ourDataH );

		///////////////////////
		//
		//	Shuts down an IC session.
		//
		//	[in]	ourDataH	must have been previously initialized by calling iCStart
		//
	static	void iCStop( OurDataH ourDataH );

		///////////////////////
		//
		//	Similar to the IC routine.
		//
		//	[in]	ourDataH	must have been previously initialized by calling iCStart
		//	[out]	seedP		on return, will contain the seed value
		//
	static	ErrCode iCGetSeed( OurDataH ourDataH, long *seedP );

		///////////////////////
		//
		//	Similar to the IC routine.
		//
		//	[in]	ourDataH	must have been previously initialized by calling iCStart
		//	[in]	whichRecord	which record of the IC mapping database to retrieve
		//	[out]	mapEntryP	the database record will be placed in this buffer
		//
	static	ErrCode iCGetIndMapEntry( OurDataH ourDataH, long whichRecord, ICMapEntry *mapEntryP );

		///////////////////////
		//
		//	Similar to the IC routine.
		//
		//	[in]	ourDataH	must have been previously initialized by calling iCStart
		//	[out]	countP		on return, will contain the number of records in the file
		//						mapping database
		//
	static	ErrCode iCCountMapEntries( OurDataH ourDataH, long *countP );

		///////////////////////
		//
		//	Similar to the IC routine.
		//
		//	[in]	appCreator	the creator code of the app calling this routine
		//	[in]	csURL		the fully-qualified URL to launch
		//
	static	ErrCode iCLaunchURL( long appCreator, const CStr *csURL );

		///////////////////////
		//
		//	Similar to the IC routine.
		//
		//	[in]	appCreator		the creator code of the app calling this routine
		//	[in]	targetCreator	the creator code being searched for	
		//	[in]	targetType		the type code being searched for
		//	[in]	direction		unused
		//	[out]	numReturned		on return, will contain the number of extensions returned
		//	[in]	maxToReturn		the maximum number of extensions to return
		//	[out]	retData			on return, this buffer will contain each extension corresponding
		//							to targetCreator and targetType
		//							each extension will consume kMaxExtensionLength bytes, and this
		//							array must have at least (maxToReturn * kMaxExtensionLength)
		//							elements
		//
	static	ErrCode iCFindMatchesFInfo( long appCreator, long targetCreator, long targetType,
										long direction, long *numReturned, long maxToReturn,
										StringPtr retData );

		///////////////////////
		//
		//	Similar to the IC routine.
		//
		//	[in]	appCreator		the creator code of the app calling this routine
		//	[in]	csName			the app name to search for
		//	[out]	creatorP		the creator codes of found apps will be placed in this array
		//							must have at least maxToReturn elements
		//	[out]	vRefP			the vRef's of found apps will be placed in this array
		//							must have at least maxToReturn elements
		//	[out]	parIDP			the parID's of found apps will be placed in this array
		//							must have at least maxToReturn elements
		//	[out]	namesP			the names of found apps will be placed in this array as
		//							Pascal strings
		//							must have at least (maxToReturn * kFindAppByNameMaxNameLen)
		//							elements
		//	[in]	maxToReturn		the maximum number of apps to return
		//	[out]	numReturned		on return, will contain the number of apps returned
		//
	static	ErrCode iCFindAppByName( long appCreator, const CStr *csName, long *creatorP,
										long *vRefP, long *parIDP, StringPtr namesP,
										long maxToReturn, long *numReturned);


		///////////////////////
		//
		//	Similar to the IC routine, finds the type/creator codes corresponding to
		//	a file extension.
		//
		//	[in]	appCreator		the creator code of the app calling this routine
		//	[in]	csExtension		the extension to search for
		//	[in]	direction		ignored
		//	[out]	numReturned		on return, will contain the number of apps returned
		//	[in]	maxToReturn		the maximum number of apps to return
		//	[out]	cValsP			on return, the creator values will be placed in this array
		//							must have at least maxToReturn elements
		//	[out]	tValsP			on return, the type values will be placed in this array
		//							must have at least maxToReturn elements
		//
	static	ErrCode iCFindMatchesExt( long appCreator, const CStr *csExtension,
										long direction, long *numReturned,
										long maxToReturn, long *cValsP, long *tValsP );

	enum {
		kMaxExtensionLength = 32,
		kFindAppByNameMaxNameLen = 64
	};

};

#endif


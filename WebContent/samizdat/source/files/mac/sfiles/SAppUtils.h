/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SAppUtils_H
#define INC_SAppUtils_H

#include "comdefs.h"
#include <translation.h>
#include "CFSpec.h"

/*------------------------------------------------------------------------
CLASS
	SAppUtils

	Routines for working with applications.

DESCRIPTION
	Routines for working with applications.

------------------------------------------------------------------------*/

class SAppUtils
{
public:

		///////////////////////
		//
		//	See openWithDocs()
		//
	typedef enum tageOpenWithDocsEventCode {
		kLaunchWithDocOpenDoc = 1,
		kLaunchWithDocPrintDoc = 2
	} eOpenWithDocsEventCode;

		///////////////////////
		//
		//	See sendAppDocs()
		//
	typedef enum tageSendAppDocsEventCode {
		kSendAppDocsOpenDoc = 3,
		kSendAppDocsPrintDoc = 4
	} eSendAppDocsEventCode;

		///////////////////////
		//
		//	See openWithDocs() and sendAppDocs()
		//
	typedef enum tageLaunchWithDocFlags {
		kDontSwitchLayer = 1
	} eLaunchWithDocFlags;

		///////////////////////
		//
		//	Send a series of documents to a running app.
		//
		//	[in]	eventCode		one of the eSendAppDocsEventCode values
		//	[in]	appPSN			the PSN of the app
		//	[in]	filesToLaunch	an array of FSSpec's containing the documents to send to the app
		//	[in]	numSpecs		the number of FSSpec's in filesToLaunch
		//	[in]	flags			ignored
		//
	static	ErrCode sendAppDocs( eSendAppDocsEventCode eventCode, ProcessSerialNumber *appPSN,
									FSSpec *filesToLaunch, long numSpecs,
									eLaunchWithDocFlags flags );

		///////////////////////
		//
		//	Open an app with a series of documents.
		//
		//	[in]	eventCode		one of the eOpenWithDocsEventCode values
		//	[in]	appSpec			the CFSpec of the app
		//	[in]	filesToLaunch	an array of FSSpec's containing the documents to send to the app
		//	[in]	numSpecs		the number of FSSpec's in filesToLaunch
		//	[out]	retPSN			on return, the PSN of the new process
		//	[in]	flags			0 or a combination of one of the eLaunchWithDocFlags values
		//
	static	ErrCode openWithDocs( eOpenWithDocsEventCode eventCode, CFSpec *appSpec,
									FSSpec *filesToLaunch, long numSpecs,
									ProcessSerialNumber *retPSN, eLaunchWithDocFlags flags );

		///////////////////////
		//
		//	Launch an app without docs.
		//
		//	[in]	appSpec			the CFSpec of the app
		//	[out]	retPSN			on return, the PSN of the new process
		//	[in]	flags			0 or a combination of one of the eLaunchWithDocFlags values
		//
	static	ErrCode launchApp( CFSpec *appSpec, ProcessSerialNumber *retPSN, long flags );

		///////////////////////
		//
		//	Get a list of the file types an app says it can open.
		//
		//	[in]	vRef			the volume containing the add identified by creator
		//	[in]	creator			the creator code of the app
		//	[out]	typesBlockP		an array of FileType's
		//	[in]	maxToReturn		the number of FileType elements in typesBlockP
		//	[out]	numReturned		on returned, the number of FileType's placed in typesBlockP
		//
	static	ErrCode getOpenableFileTypes( long vRef, long creator, FileType *typesBlockP, long maxToReturn,
											long *numReturned );

		///////////////////////
		//
		//	Send an event to an app, running or not.
		//
		//	[in]	theEvent		the event to send
		//	[in]	creator			the creator code of the app
		//
	static	ErrCode sendEvent( AppleEvent *theEvent, OSType creator );

		///////////////////////
		//
		//	Launch the app specified by 'appSpec' with an AppleEvent.
		//
		//	[in]	appSpec			the CFSpec of the app
		//	[in]	theEvent		the AppleEvent to launch the app with
		//
	static	ErrCode launchFSSpec( CFSpec *appSpec, AppleEvent *theEvent );
};

#endif


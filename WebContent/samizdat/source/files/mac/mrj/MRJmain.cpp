/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include <CodeFragments.h>
#include <Gestalt.h>
#include <string.h>
#include <NativeLibSupport.h>
#include <jri.h>

#include "comdefs.h"
#include "Debugger.h"

#include "IConfig.h"
#include "AppUtils.h"
#include "AppFinder.h"
#include "ResFile.h"

#include "vtbls.h"

#include "SIconDrawer.h"

short		gFileRefNum = -1;
Boolean		bFinderIsScriptable;

enum {
	kMaxBrowserCreators = 10
};

OSType					browserCreators[ kMaxBrowserCreators ];
long					numBrowserCreators;

static ErrCode			theInitResult = paramErr;

static char				*mayim = "Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
static char				*mayim2 = "Copyright (c) 1997-2002 Samizdat Productions.  All Rights Reserved.";
static char				*mayim3 = "Copyright (c) 1997-2002 Samizdat Productions.   All Rights Reserved.";
static char				*mayimP;

ErrCode readBrowserPrefs( OSType *typeP, long max, long *numWritten );
Boolean finderIsRunning( void );
void setFinderIsScriptable( void );

#ifdef __cplusplus
	extern "C" {
#endif
	pascal OSErr __initialize( const CFragInitBlock *ibp );
	pascal OSErr _setter_cfm_init( const CFragInitBlock *ibp );

	pascal void __terminate(); 
	pascal void _setter_cfm_term(); 
#ifdef __cplusplus
	}
#endif

pascal OSErr _setter_cfm_init( const CFragInitBlock *ibp )
{
	ErrCode		theErr;
	long		i;

	theErr = __initialize( ibp );
	if ( theErr != noErr ) {
		Debugger::debug( theErr, "<- that's the error" );
		return theErr;
	}

	gFileRefNum = -1;
	numBrowserCreators = 0;

	if ( ibp->fragLocator.where == kDataForkCFragLocator ) {
		gFileRefNum = FSpOpenResFile( ibp->fragLocator.u.onDisk.fileSpec, fsRdPerm );
		if ( gFileRefNum < 0 ) {
			theErr = ResError();
			if ( theErr == kErrNoErr )
				theErr = -1;
			Debugger::debug( __LINE__, "can't open resF", NULL, NULL, theErr, 0 );
		}
	}

	if ( theErr == kErrNoErr )	
		theErr = readBrowserPrefs( &browserCreators[ 0 ], kMaxBrowserCreators, &numBrowserCreators );
	
	if ( theErr != kErrNoErr || numBrowserCreators == 0 ) {
		browserCreators[ 0 ] = 'MSIE';
		browserCreators[ 1 ] = 'MOS!';
		numBrowserCreators = 2;
	}

	Debugger::debug( __LINE__, "creators", NULL, NULL, numBrowserCreators, 0 );
	for ( i = 0; i < numBrowserCreators; i++ )
		Debugger::debug( __LINE__, "  ", NULL, NULL, browserCreators[ i ], 0 );

	SIconDrawer::initializeIconDrawer();

	return kErrNoErr;
}

pascal void _setter_cfm_term()
{
	if ( gFileRefNum > -1 )
		CloseResFile( gFileRefNum );

	SIconDrawer::disposeIconDrawer();

	__terminate();
}

ErrCode readBrowserPrefs( OSType *typeP, long max, long *numWritten )
{
	long		i, fromLen;
	short		saveResFile;
	OSType		*fromP;
	ErrCode		theErr;
	Handle		h;
	
	*numWritten = 0;
	theErr = kErrNoErr;
	h = NULL;

	saveResFile = CurResFile();
	UseResFile( gFileRefNum );

	h = Get1Resource( 'brws', 5000 );
	if ( h == NULL ) {
		Debugger::debug( __LINE__, "rbp: can't find <brws,5000> resource" );
		theErr = -1;
		goto bail;
	}
	
	DetachResource( h );
	HLock( h );
	if ( *h == NULL ) {
		Debugger::debug( __LINE__, "rbp: bad brws resource *==null" );
		theErr = -1;
		goto bail;
	}

	fromLen = GetHandleSize( h ) / sizeof(long);
	if ( fromLen > max )
		fromLen = max;
	if ( fromLen < 1 ) {
		Debugger::debug( __LINE__, "rbp: bad brws resource", NULL, NULL, fromLen, 0 );
		theErr = -1;
		goto bail;
	}

	Debugger::debug( __LINE__, "copying browser types", NULL, NULL, fromLen, 0 );

	for ( i = 0, fromP = (OSType*) *h; i < fromLen; i++ )
		*typeP++ = *fromP++;

	*numWritten = fromLen;

bail:

	if ( h != NULL )
		DisposeHandle( h );

	UseResFile( saveResFile );

	return theErr;
}

Boolean finderIsRunning (void)
{
	ErrCode				err;
	ProcessInfoRec		pInfo;
	ProcessSerialNumber	psn;
	Boolean				foundIt;

	foundIt = false;
	psn.highLongOfPSN = 0; psn.lowLongOfPSN = kNoProcess;

	while ((foundIt == false) && (GetNextProcess(&psn) == kErrNoErr)) {
		pInfo.processName 		= NULL;
		pInfo.processAppSpec 	= NULL;
		pInfo.processInfoLength	= sizeof(ProcessInfoRec);
	
		err = GetProcessInformation(&psn, &pInfo);
	
		if ((err == kErrNoErr) 
			&& (pInfo.processSignature == 'MACS') 
			&& (pInfo.processType == 'FNDR'))
			
			foundIt = true;
	}
	
	return foundIt;
}

void setFinderIsScriptable( void )
{
	long 		response;
	ErrCode		theErr;

	bFinderIsScriptable = false;
	
	theErr = Gestalt( gestaltFinderAttr, &response );
	if ( theErr != kErrNoErr )
		return;

	if ( ( response & ( 1 << gestaltOSLCompliantFinder ) ) && ( finderIsRunning() ) )
		bFinderIsScriptable = true;
}

#pragma export on

OSStatus InitJavaLibrary(JRIRuntimeInstance* runtimeInstance, CFragConnectionID javaLibFragment)
{
	NativeFuncs		*funcList;
	JRIEnv			*env = nil;
	JRIClassID		classID;

	mayimP = mayim;
	mayimP = mayim2;
	mayimP = mayim3;
	
	setFinderIsScriptable();
	if ( bFinderIsScriptable )
		Debugger::debug( __LINE__, "finder is scriptable" );
	
	theInitResult = InitNativeLibSupport( runtimeInstance, javaLibFragment );
	if ( theInitResult != kErrNoErr ) {
		Debugger::debug( __LINE__, "InitNativeLibSupport", NULL, NULL, theInitResult, 0 );
		goto bail;
	}

	env = JRI_NewEnv( runtimeInstance, nil );
	if ( env == nil ) {
		Debugger::debug( __LINE__, "env == nil" );
		theInitResult = -1;
		goto bail;
	}

	funcList = &allFuncList[ 0 ];

	while ( funcList->className != nil ) {
		classID = JRI_FindClass( env, funcList->className );

		if ( classID == nil ) {
			Debugger::debug( __LINE__, "can't find class", funcList->className );
			theInitResult = fnfErr;
			break;
		}
		else
			JRI_RegisterNatives( env, classID, funcList->methodName, funcList->methodImpl );
		
		++funcList;
	}

bail:
	if ( env != nil )
		JRI_DisposeEnv( runtimeInstance, env );

	return theInitResult;	
}

#pragma export off


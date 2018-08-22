/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SProcesses_H
#define INC_SProcesses_H

#include "CFSpec.h"

#if defined(__osx__)
	#include <Processes.h>
#endif

/*------------------------------------------------------------------------
CLASS
	SProcesses

	Routines for working with processes.

DESCRIPTION
	Routines for working with processes.

------------------------------------------------------------------------*/

class SProcesses
{
public:

		///////////////////////
		//
		//	See moveProcess().
		//
	typedef enum tageMoveProcess {
		kMoveProcessToFront = 1,
		kMoveProcessToBack = 2,
		kMoveProcessMinimize = 3,
		kMoveProcessMaximize = 4
	} eMoveProcess;

		///////////////////////
		//
		//	Get a list of the running processes.
		//
	static	ErrCode getProcesses( long maxToReturn, long flags, long *numReturned,
								long *vRefsP, long *parIDsP, StringPtr namesP,
								long *PSNLoP, long *PSNHiP, long *proFlagsP );

		///////////////////////
		//
		//	Bring finder to the front.
		//
	static	ErrCode setFinderAsFrontProcess( void );

		///////////////////////
		//
		//	Bring a process to the front.
		//
	static	ErrCode escortProcessToTheFront( ProcessSerialNumber *psnP );

		///////////////////////
		//
		//	Search for a running process, and return its PSN and FSSpec in 'processP' and 'specP'
		//	if found.
		//
	static	ErrCode findProcess( OSType creator, OSType typ, ProcessSerialNumber *processP,
								CFSpec *specP );

		///////////////////////
		//
		//	Hide a process.
		//
	static	ErrCode hideProcess( ProcessSerialNumber *psnP );

		///////////////////////
		//
		//	Hide the front process.
		//
	static	ErrCode hideFrontProcess( void );

		///////////////////////
		//
		//	Move a process.
		//
	static	ErrCode moveProcess( ProcessSerialNumber *psn, eMoveProcess selector, long flags );

		///////////////////////
		//
		//	Quit a process.
		//
	static	ErrCode quitProcess( ProcessSerialNumber *thePSN, long flags );

		///////////////////////
		//
		//	Verify that a process is still running.
		//
	static	ErrCode verifyPSN( ProcessSerialNumber *thePSN );

	enum {
		kGetProcessMaxNameLen = 64
	};

protected:
};

#endif


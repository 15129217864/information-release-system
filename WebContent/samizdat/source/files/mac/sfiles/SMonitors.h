/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SMonitors_H
#define INC_SMonitors_H

#include "comdefs.h"

#if defined(__osx__)
	#include <Displays.h>
#endif

/*------------------------------------------------------------------------
CLASS
	SMonitors

	Routines for working with video monitors.

DESCRIPTION
	Routines for working with video monitors.

------------------------------------------------------------------------*/

class SMonitors
{
public:

		///////////////////////
		//
		//	Get information on the user's main monitor into 'monitorInfoP'.
		//	eMonitorIntArray defines the offset of each piece of data.
		//	For instance, the depth of the monitor will be stored
		//	at monitorInfoP[ kOffsDepth ]
		//
		//  [out]	monitorInfoP		Points to a memory buffer which will receive the information
		//								This must be large enough for at lesst kMonitorInfoNumInts longs.
		//
	static	ErrCode getMainMonitorInfo( long *monitorInfoP );

		///////////////////////
		//
		//	Get information on each of the user's monitors into 'monitorInfoP'.
		//	'numDone' will contain the number of monitors for which
		//	information is returned.
		//	Information on each monitor will be stored at succeeding
		//	offsets, in a similar manner as with the 'getMainMonitorInfo()'
		//	routine.
		//
		//  [out]	monitorInfoP		Points to a memory buffer which will receive the information
		//								This must be large enough for at lesst (maxToReturn * kMonitorInfoNumInts)
		//								longs.
		//  [in]	maxToReturn			The maximum number to return; indicates the size of the buffer.
		//  [out]	numReturnedP		On exit, the number of monitors returned.
		//
	static	ErrCode getAllMonitorInfo( long *monitorInfoP, long maxToReturn, long *numDone );

		///////////////////////
		//
		//	The offsets at which monitor information is stored.
		//
	enum {
		kOffsBoundsTop = 0,
		kOffsBoundsLeft = 1,
		kOffsBoumdsBottom = 2,
		kOffsBoundsRight = 3,
		kOffsWorkareaTop = 4,
		kOffsWorkareaLeft = 5,
		kOffsWorkareaBottom = 6,
		kOffsWorkareaRight = 7,
		kOffsDepth = 8,
		kOffsIsMainMonitor = 9,
		kOffsRefNum = 10,

		kMonitorInfoNumInts = 20
	} eMonitorIntArray;

protected:
	static	void stuffGDHandle( GDHandle gdH, long *dataP );
};

#endif


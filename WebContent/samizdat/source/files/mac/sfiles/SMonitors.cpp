/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SMonitors.h"
#include "CUtils.h"

#if defined(__osx__)
	#include <Menus.h>
#endif

ErrCode SMonitors::getMainMonitorInfo( long *dataP )
{
	GDHandle		mainGD;
	
	mainGD = GetMainDevice();
	if ( mainGD == NULL )
		return -1;
	
	stuffGDHandle( mainGD, dataP );

	return kErrNoErr;
}

ErrCode SMonitors::getAllMonitorInfo( long *dataP, long maxToReturn, long *numDone )
{
	GDHandle		theGD;
	long			numWritten;
	
	theGD = GetDeviceList();
	numWritten = 0;

	while ( theGD != NULL && numWritten < maxToReturn ) {

		if ( TestDeviceAttribute( theGD, screenDevice ) &&
				TestDeviceAttribute( theGD, screenActive ) ) {
			stuffGDHandle( theGD, dataP );
			dataP += kMonitorInfoNumInts;
			++numWritten;
		}

		theGD = GetNextDevice( theGD );
	}

	*numDone = numWritten;
	
	return kErrNoErr;
}

void SMonitors::stuffGDHandle( GDHandle gdH, long *dataP )
{
	PixMapHandle		pixmapH;
	short				menuBarHeight;
	
#if defined(__osx__)
	menuBarHeight = GetMBarHeight();
#else
	menuBarHeight = LMGetMBarHeight();
#endif

	dataP[ kOffsBoundsTop ] = (**gdH).gdRect.top;
	dataP[ kOffsBoundsLeft ] = (**gdH).gdRect.left;
	dataP[ kOffsBoumdsBottom ] = (**gdH).gdRect.bottom;
	dataP[ kOffsBoundsRight ] = (**gdH).gdRect.right;

	dataP[ kOffsWorkareaTop ] = (**gdH).gdRect.top;
	dataP[ kOffsWorkareaLeft ] = (**gdH).gdRect.left;
	dataP[ kOffsWorkareaBottom ] = (**gdH).gdRect.bottom;
	dataP[ kOffsWorkareaRight ] = (**gdH).gdRect.right;

	pixmapH = (**gdH).gdPMap;
	dataP[ kOffsDepth ] = (**pixmapH).pixelSize;

	if ( TestDeviceAttribute( gdH, mainScreen ) ) {
		dataP[ kOffsIsMainMonitor ] = 1;
		dataP[ kOffsWorkareaTop ] += menuBarHeight;
	}
	else {
		dataP[ kOffsIsMainMonitor ] = 0;
	}

	dataP[ kOffsRefNum ] = (**gdH).gdRefNum;
}


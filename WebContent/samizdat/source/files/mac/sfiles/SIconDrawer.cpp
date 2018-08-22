/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SIconDrawer.h"
#include "CUtils.h"
#include "Debugger.h"

#if defined(__osx__)
	#include <Icons.h>
#endif

static GWorldX		bigGW = { NULL, { 0, 0, 0, 0 }, NULL, NULL, NULL, NULL, 0 },
					smallGW = { NULL, { 0, 0, 0, 0 }, NULL, NULL, NULL, NULL, 0 };

ErrCode SIconDrawer::initializeIconDrawer( void )
{
	makeMiniBackground( &bigGW, 32, 32, 32 );
	makeMiniBackground( &smallGW, 16, 16, 32 );

	if ( bigGW.theGWorld == NULL || smallGW.theGWorld == NULL ) {
		Debugger::debug( __LINE__, "initIconD, null gws" );
		return memFullErr;
	}
	else
		return kErrNoErr;
}

void SIconDrawer::disposeIconDrawer( void )
{
	disposeMiniBackground( &bigGW );
	disposeMiniBackground( &smallGW );
}

ErrCode SIconDrawer::plotIcon( eWhichIcon which, long width, long height,
								Handle hSuite, long xform, long align, unsigned long *data )
{
	GWorldXPtr		gxP;
	ErrCode			theErr = kErrNoErr;
	long			i, xx, yy, addToLine, fromIndex, toIndex;
	unsigned long	*sourceP;

	if ( bigGW.theGWorld == NULL || smallGW.theGWorld == NULL )
		initializeIconDrawer();

	if ( bigGW.theGWorld == NULL || smallGW.theGWorld == NULL )
		return memFullErr;

	if ( hSuite == NULL || data == NULL )
		return paramErr;

	if ( which == kPlotLargeIcon ) {
		if ( width != kPlotLargeIconWidth || height != kPlotLargeIconHeight )
			return paramErr;
	}
	else if ( which == kPlotSmallIcon ) {
		if ( width != kPlotSmallIconWidth || height != kPlotSmallIconHeight )
			return paramErr;
	}
	else
		return paramErr;

	if ( which == kPlotLargeIcon )
		gxP = &bigGW;
	else
		gxP = &smallGW;

	if ( gxP->theGWorld == NULL )
		return memFullErr;

	setAsPortMiniBackground( gxP );

	EraseRect( &( gxP->theRect ) );

#if defined(macintosh)
	theErr = PlotIconSuite( &( gxP->theRect ), align, xform, hSuite );
#elif defined(__osx__)
	theErr = PlotIconRef ( &( gxP->theRect ), (IconAlignmentType) align, (IconTransformType) xform,
							kIconServicesNormalUsageFlag, (IconRef) hSuite );
#endif

	if ( theErr != kErrNoErr )
		Debugger::debug( __LINE__, "plotIcon.PIS", NULL, NULL, theErr, 0 );
	
	restorePortMiniBackground( gxP );

	addToLine = gxP->theRowBytes / 4 - width;
	fromIndex = 0;
	toIndex = 0;

	sourceP = (unsigned long*) gxP->theBytes;
	for ( yy = 0, i = 0; yy < height; yy++ ) {
		for ( xx = 0; xx < width; xx++, fromIndex++, toIndex++ ) {
			if ( ( sourceP[ fromIndex ] & 0x00FFFFFF ) == 0x00FFFFFF )
				data[ toIndex ] = 0;
			else
				data[ toIndex ] = 0xFF000000 | sourceP[ fromIndex ];
		}

		fromIndex += addToLine;
	}

	return theErr;
}


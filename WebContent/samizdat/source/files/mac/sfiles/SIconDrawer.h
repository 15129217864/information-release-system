/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SIconDrawer_H
#define INC_SIconDrawer_h

#include "comdefs.h"
#include "MiniBackground.h"

/*------------------------------------------------------------------------
CLASS
	SIconDrawer

	Copies icons to a pixel array.

DESCRIPTION
	Copies icons to a pixel array.

------------------------------------------------------------------------*/

class SIconDrawer
{
public:

		///////////////////////
		//
		//	Plot the small or large icon.
		//
	typedef enum tageWhichIcon {
		kPlotLargeIcon = 1,
		kPlotSmallIcon = 2
	} eWhichIcon;

		///////////////////////
		//
		//	Must be called before any use of the 'plotIcon' method.
		//
	static	ErrCode initializeIconDrawer( void );

		///////////////////////
		//
		//	Must be called when done using the 'plotIcon' method.
		//
	static	void disposeIconDrawer( void );

		///////////////////////
		//
		//	Puts the pixel data of an icon into 'data'.
		//
	static	ErrCode plotIcon( eWhichIcon which, long width, long height,
							Handle hSuite, long xform, long align, unsigned long *data );

		///////////////////////
		//
		//	Dimensions for small and large icons.
		//
	enum {
		kPlotLargeIconWidth = 32,
		kPlotLargeIconHeight = 32,
		kPlotSmallIconWidth = 16,
		kPlotSmallIconHeight = 16
	} eIconDims;
};

#endif


/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_ICONBUNDLEMRJ_H
#define INC_ICONBUNDLEMRJ_H

#include <NativeLibSupport.h>
#include <jri.h>

long _jriGetIcon( JRIEnv* env, jref configObj, long creator, long fileType, long whichIcon,
					long width, long height, jintArray dataArray );

#endif

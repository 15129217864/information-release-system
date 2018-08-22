/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "Debug.h"

#define	FI_DEBUGGING	1

#define	DEBUG( msg )	Debug( msg )
#define	ASSERT(cond)	do { if (!(cond)) {Debug("assert: " # cond);}} while(0)

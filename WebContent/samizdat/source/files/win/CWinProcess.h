/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CWINPROCESS_H
#define INC_CWINPROCESS_H

#if defined(UNICODE)
	#define CWinProcess		CWinProcessNT
	#include "CWinProcessNT.h"
#else
	#define CWinProcess		CWinProcess95
	#include "CWinProcess95.h"
#endif

#endif

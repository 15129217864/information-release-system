/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SWebBrowser_H
#define	INC_SWebBrowser_H

#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	SWebBrowser

	Used to launch URLs.

DESCRIPTION
	Used to launch URLs, independent of Internet Config.

------------------------------------------------------------------------*/

class SWebBrowser
{
public:

		///////////////////////
		//
		//	Launch a URL without using Internet Config.
		//
	static	ErrCode launchURLDirect( const CStr *csURL, OSType creator, long flags );
};

#endif

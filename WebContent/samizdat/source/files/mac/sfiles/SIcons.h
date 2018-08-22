/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SIcons_H
#define	INC_SIcons_H

#include "CFSpec.h"

/*------------------------------------------------------------------------
CLASS
	SIcons

	Returns the icons for files, volumes, and file types.

DESCRIPTION
	Returns the icons for files, volumes, and file types.

------------------------------------------------------------------------*/

class SIcons
{
public:

		///////////////////////
		//
		//	Returns the icon Handle for a given volume.
		//
	static	ErrCode getVolumeIconSuite( long vRef, long selector, Handle *hSuite );

		///////////////////////
		//
		//	Returns the icon Handle for a given file.
		//
	static	ErrCode getFileIconSuite( const CFSpec *theSpec, long selector, Handle *hSuite );

		///////////////////////
		//
		//	Returns the icon Handle for a given file type and creator.
		//
	static	ErrCode getFTACIconSuite( long vRef, long creator, long type, long selector, Handle *hSuite );

		///////////////////////
		//
		//	Disposes of an icon Handle.
		//
	static	ErrCode disposeAnIconSuite( Handle hSuite, long flags );
};

#endif

/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SResFile_H
#define INC_SResFile_H

#include "CFSpec.h"

/*------------------------------------------------------------------------
CLASS
	SResFile

	Routines for working with resource forks.

DESCRIPTION
	Routines for working with resource forks.

------------------------------------------------------------------------*/

class SResFile
{
public:

		///////////////////////
		//
		//	Open a resource fork.
		//
	static	short openExistingResFile( const CFSpec *theSpec );

		///////////////////////
		//
		//	Close a resource fork.
		//
	static	void closeResFile( long fileFD );

		///////////////////////
		//
		//	Get the size of a resource.
		//
	static	ErrCode getResourceSize( long fileFD, long resName, long resID, long *resSizeP );

		///////////////////////
		//
		//	Get a resource into a buffer.
		//
	static	ErrCode getResource( long fileFD, long resName, long resID, long bufLen, char *bufP );
};

#endif

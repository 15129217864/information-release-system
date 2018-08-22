/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SIterate_H
#define INC_SIterate_H

#include "CFSpec.h"

/*------------------------------------------------------------------------
CLASS
	SIterate

	Iterates the contents of volumes and folders.

DESCRIPTION
	Iterates the contents of volumes and folders.

------------------------------------------------------------------------*/

class SIterate
{
public:

		///////////////////////
		//
		//	Return a list of the contents of a folder or volume.
		//
	static	ErrCode iterateContents( const CFSpec *theSpec, long *dirID, long *numRet,
							unsigned char *bufferP, long numEntries, long flags );

		///////////////////////
		//
		//	Return a list of the contents of a volume.
		//
	static	ErrCode iterateVolumeContents( long vRef, long *dirID, long *numRet, unsigned char *bufferP,
									long numEntries, long flags );


		///////////////////////
		//
		//	Return a list of the contents of a folder.
		//
	static	ErrCode iterateDirectory( long vRef, long dirID, long *numRet,
									unsigned char *bufferP, long numEntries, long flags );

	enum {
		kIterateEntrySize = 68,
		kIgnoreHidden = 0x01,
		kIgnoreFolders = 0x02,
		kIgnoreFiles = 0x04,
		kIgnoreAliases = 0x08,
		kIgnoreNameLocked = 0x10,

		kIterateNameSize = 64,
		kIterateTypeOffset = 64,
		kIterateIsFile = 0,
		kIterateIsDir = 1,
		kIterateIsAlias = 2
	};

protected:

	static	ErrCode getDirID( const CFSpec *theSpec, long *dirIDP );
};

#endif


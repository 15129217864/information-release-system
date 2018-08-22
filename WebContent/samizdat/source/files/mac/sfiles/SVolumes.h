/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SVolumes_H
#define	INC_SVolumes_H

#include "CString.h"
#include "CDateBundle.h"

/*------------------------------------------------------------------------
CLASS
	SVolumes

	Routines for working with volumes.

DESCRIPTION
	Routines for working with volumes.

------------------------------------------------------------------------*/

class SVolumes
{
public:

		///////////////////////
		//
		//	Set the color coding of a volume
		//
	static	ErrCode setVolumeColorCoding( long vRef, long newCoding );

		///////////////////////
		//
		//	Call SFiles::getFinderInfo for a volume.
		//
	static	ErrCode getVolumeFinderInfo( long vRef, long *creator, long *type, long *flags, long *attribs );

		///////////////////////
		//
		//	Set the date of a volume.
		//
	static	ErrCode setVolumeDate( long vRef, CDateBundle *dateBundle );

		///////////////////////
		//
		//	Verify that a volume is still mounted.
		//
	static	ErrCode verifyVolume( long vRef );

		///////////////////////
		//
		//	Get the date of a volume.
		//
	static	ErrCode getVolumeDate( long vRef, CDateBundle *dateBundle );

		///////////////////////
		//
		//	Rename a volume
		//
	static	ErrCode renameVolume( long vRef, const CStr *csNewName );

		///////////////////////
		//
		//	Get the maximum capacity of a volume.
		//
	static	ErrCode getVolumeCapacity( long vRef, UnsignedWide *capacity );

		///////////////////////
		//
		//	Get the free space of a volume.
		//
	static	ErrCode getVolumeFreeSpace( long vRef, UnsignedWide *space );

		///////////////////////
		//
		//	Get the name of a volume.
		//
	static	ErrCode getVolumeName( long vRef, StringPtr volumeName );

		///////////////////////
		//
		//	Get a list of the mounted volumes.
		//
	static	ErrCode getAllVolumes( long *numReturned, long maxToReturn, long *refsP );

		///////////////////////
		//
		//	Wraps the MoreFiles OnLine routine.
		//
	static	ErrCode moreFilesOnLine( long *refsP, long maxToReturn, long *numReturned );

		///////////////////////
		//
		//	Get a list of the mounted volumes.
		//
	static	ErrCode getAllVRefNums( long *vRefP, long maxNum, long *numReturned );
};

#endif

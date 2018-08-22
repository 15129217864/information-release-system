/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SFiles_H
#define	INC_SFiles_H

#include "comdefs.h"
#include "CFSpec.h"
#include "CString.h"
#include "CDateBundle.h"

/*------------------------------------------------------------------------
CLASS
	SFiles

	Routines for working with files.

DESCRIPTION
	Routines for working with files.

------------------------------------------------------------------------*/

class SFiles
{
public:

		///////////////////////
		//
		//	Convert an MRJ full path to a CFSpec.
		//	An MRJ path is like: "/Macintosh%20HD/Photoshop%AC"
		//
	static	ErrCode fullPathNameToSpec( const CStr *csFullPath, CFSpec *theSpecP );

		///////////////////////
		//
		//	Convert a CFSpec to an MRJ full path. See the preceding routine.
		//
	static	ErrCode specToFullPathName( const CFSpec *theSpec, short *len, Handle *hFullPath );

		///////////////////////
		//
		//	Get the parID and name of the CFSpec's parent.
		//
	static	ErrCode getContainer( const CFSpec *theSpec, long *contParID, StringPtr contName );

		///////////////////////
		//
		//	Set a file's color coding.
		//
	static	ErrCode setColorCoding( const CFSpec *theSpec, long newCoding );

		///////////////////////
		//
		//	Get a file's creator, type, flags, and attributes.
		//
	static	ErrCode getFinderInfo( const CFSpec *theSpec, long *creator, long *type, long *flags, long *attribs );

		///////////////////////
		//
		//	Set a file's creator and type.
		//
	static	ErrCode setCreatorAndType( const CFSpec *theSpec, long creator, long type, long flags, long attributes );

		///////////////////////
		//
		//	Set the dates of a file from the given CDateBundle.
		//
	static	ErrCode setFileDate( const CFSpec *theSpec, CDateBundle *dateBundle );

		///////////////////////
		//
		//	Verify a file exists.
		//
	static	ErrCode verifyFile( const CFSpec *theSpec );

		///////////////////////
		//
		//	Get the creation, modification, and backup dates of a file into the
		//	given CDateBundle.
		//
	static	ErrCode getFileDate( const CFSpec *theSpec, CDateBundle *dateBundle );

		///////////////////////
		//
		//	Set the finder flags of a file. The current flags will be AND'ed with 'firstAnd',
		//	and the OR'ed with 'thenOr'
		//
	static	ErrCode setFinderFlags( const CFSpec *theSpec, unsigned short firstAnd, unsigned short thenOr );

		///////////////////////
		//
		//	Get the sizes of a files res and data forks.
		//
	static	ErrCode getForkSizes( const CFSpec *theSpec, UnsignedWide *dataLen, UnsignedWide *rsrcLen );

		///////////////////////
		//
		//	Rename a file.
		//
	static	ErrCode renameFile( const CFSpec *theSpec, const CStr *csNewName,
								StringPtr outName, long outNameLen );

		///////////////////////
		//
		//	Set the modification date of the folder containing a file to the current time.
		//
	static	ErrCode updateContainer( const CFSpec *containedSpec );

		///////////////////////
		//
		//	Read the raw resource fork into the given buffer.
		//
	static	ErrCode getRawResourceFork( long flags, CFSpec *theSpec, char *buffer, long bufferLen );

		///////////////////////
		//
		//	Overwrite the resource fork from the given buffer.
		//
	static	ErrCode setRawResourceFork( long flags, CFSpec *theSpec, char *buffer, long bufferLen );

		///////////////////////
		//
		//	Set the length of the data or resource fork.
		//
	static	ErrCode setForkLength( long flags, long whichFork, CFSpec *theSpec, UnsignedWide newLen );
};

#endif


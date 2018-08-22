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
#define INC_SVolumes_H

#include "comdefs.h"
#include "CString.h"
#include "CStringVector.h"
#include "CDateBundle.h"

/*------------------------------------------------------------------------
CLASS
	SVolumes

	Routines for working with volumes

DESCRIPTION
	Routines for working with volumes

------------------------------------------------------------------------*/

class SVolumes {
public:
	
		///////////////////////
		//
		//  Set the label of the given volume
		//
		//  [in]	csDriveName		The	name of the volume.
		//  [in]	newLabel		The	new label.
		//
	static	ErrCode iSetVolumeLabel( const CStr *csDriveName, const CStr *newLabel );

		///////////////////////
		//
		//  Calls the WinAPI routine GetVolumeInformation()
		//	This routine returns two strings and three DWORDs, the strings are
		//	placed into 'csOutVolName' and 'csFileSystemName', and the DWORDs are
		//	placed into the 'theIntsP' array. See the enum eGVI for the length
		//	and offsets of the values in this buffer.
		//
		//  [in]	csDriveName			The	name of the volume.
		//  [out]	csOutVolName		The	label of the volume
		//  [out]	csFileSystemName	The	name of the file system on the volume
		//  [out]	theIntsP			Longs with info about the volume
		//
	static	ErrCode iGetVolumeInformation( const CStr *csDriveName, CStr *csOutVolName,
										CStr *csFileSystemName, unsigned long *theIntsP );

		///////////////////////
		//
		//  Returns the free space and capacity of a volume
		//
		//  [in]	csDriveName		The	name of the volume.
		//  [out]	cap				The	free space and capacity of the volume will
		//							be placed at the offsets in cap given by the eVolumeCapInfo constants
		//
	static	ErrCode iGetVolumeCapInfo( const CStr *csDriveName, unsigned __int64 *cap );

		///////////////////////
		//
		//  Retrieves the three dates associated with a volume, and puts them in the
		//	given CDateBundle
		//
		//  [in]	csDriveName		The	name of the volume.
		//  [out]	dateBundle		On exit, contains the three dates for the volume.
		//
	static	ErrCode iGetVolumeDates( const CStr *csDriveName, CDateBundle *dateBundle );

		///////////////////////
		//
		//  Gets flags on a volume
		//
		//  [in]	csDriveName		The	name of the volume.
		//  [out]	flagsP			The flags; see the 'kVolumeFlag...' constants
		//
	static	ErrCode iGetVolumeFlags( const CStr *csDriveName, long *flagsP );

		///////////////////////
		//
		//  Same
		//
		//  [in]	csDriveName		The	name of the volume.
		//  [out]	maskP			Returns which bits from	iGetVolumeFlags are significant.
		//
	static	ErrCode iGetVolumeReadFlagsMask( const CStr *csDriveName, long *maskP );

		///////////////////////
		//
		//  Returns the display name of the volume.
		//
		//  [in]	csDriveName		The	name of the volume.
		//  [out]	displayName		The display name.
		//
	static	ErrCode iGetDriveDisplayName( const CStr *csDriveName, CStr *displayName );

		///////////////////////
		//
		//  Sets a string vector with a list of the mounted volumes.
		//
		//  [in]	maxToReturn			The	maximum number of volumes to return.
		//  [out]	retStringTable		Each mounted volume will be appended to this vector
		//
	static	ErrCode iGetVolumes( long maxToReturn, CStringVector *retStringTable );

		///////////////////////
		//
		//  The length of the buffer passed to iGetVolumeCapInfo, and the offset
		//	in this buffer of the system-wide capacity, system-wide free space,
		//	current-user-specific capacity, and current-user-specific free space
		//
	enum {
		kVolumeCapInfoLen = 4,
		kVolumeCapInfoCapacityOffset = 0,
		kVolumeCapInfoFreeSpaceOffset = 1,
		kVolumeCapInfoCapacityToUserOffset = 2,
		kVolumeCapInfoFreeSpaceToUserOffset = 3
	} eVolumeCapInfo;

		///////////////////////
		//
		//  The length of the long buffers passed to iGetVolumeInformation,
		//	and the offsets in this buffer of the values returned from this routine.
		//
	enum {
		kGVIIntArrayLen = 3,
		kGVIIntSerialNumberOffset = 0,
		kGVIIntMaxComponentLengthOffset = 1,
		kGVIIntFileSystemFlagsOffset = 2
	} eGVI;
	
		///////////////////////
		//
		//  Flags returned from iGetVolumeFlags
		//
	enum {
		kVolumeFlagCaseIsPreserved = 0x01,
		kVolumeFlagCaseSensitive = 0x02,
		kVolumeFlagUnicodeSupported = 0x04,
		kVolumeFlagFilesCompressed = 0x08,
		kVolumeFlagVolumeCompressed = 0x10,
		kVolumeFlagRemovable = 0x20,
		kVolumeFlagFixed = 0x40,
		kVolumeFlagRemote = 0x80,
		kVolumeFlagCDROM = 0x100,
		kVolumeFlagRAM = 0x200,
		kVolumeFlagSystem = 0x400,
		kVolumeFlagSupportedFlags = (
					kVolumeFlagCaseIsPreserved | kVolumeFlagCaseSensitive | kVolumeFlagUnicodeSupported |
					kVolumeFlagFilesCompressed | kVolumeFlagVolumeCompressed | kVolumeFlagRemovable |
					kVolumeFlagFixed | kVolumeFlagRemote | kVolumeFlagCDROM | kVolumeFlagRAM | kVolumeFlagSystem )
	} eDiskVolumeFlags;
	
};

#endif


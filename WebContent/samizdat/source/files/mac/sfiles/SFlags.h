/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SFlags_H
#define INC_SFlags_H

#include "CFSpec.h"
#include <Devices.h>

#if defined(macintosh)
	#include <DriverGestalt.h>
#endif

/*------------------------------------------------------------------------
CLASS
	SFlags

	Returns flags for disk objects.

DESCRIPTION
	Returns flags for disk objects.

------------------------------------------------------------------------*/

class SFlags
{
public:

		///////////////////////
		//
		//	Get flags associated with a volume.
		//
	static	ErrCode getDiskVolumeFlags( long vRef, unsigned long *flags );

		///////////////////////
		//
		//	Set flags associated with a volume.
		//
	static	ErrCode setDiskVolumeFlags( long vRef, unsigned long flagMask, unsigned long newFlags );

		///////////////////////
		//
		//	Get the mask indicating which bits of 'getDiskVolumeFlags' are significant.
		//
	static	ErrCode getDiskVolumeReadFlagsMask( long vRef, unsigned long *masks );

		///////////////////////
		//
		//	Get the mask indicating which bits of 'setDiskVolumeFlags' are significant.
		//
	static	ErrCode getDiskVolumeWriteFlagsMask( long vRef, unsigned long *masks );

		///////////////////////
		//
		//	Get flags associated with a file or folder.
		//
	static	ErrCode getDiskFileFlags( const CFSpec *theSpec, unsigned long *flags );

		///////////////////////
		//
		//	Set flags associated with a file or folder.
		//
	static	ErrCode setDiskFileFlags( const CFSpec *theSpec, unsigned long flagMask, unsigned long newFlags );

		///////////////////////
		//
		//	Get the mask indicating which bits of 'getDiskFileFlags' are significant.
		//
	static	ErrCode getDiskFileReadFlagsMask( const CFSpec *theSpec, unsigned long *masks );

		///////////////////////
		//
		//	Get the mask indicating which bits of 'setDiskFileFlags' are significant.
		//
	static	ErrCode getDiskFileWriteFlagsMask( const CFSpec *theSpec, unsigned long *masks );

		///////////////////////
		//
		//	Indicates whether the given file/dir is "shared".
		//	For .exe's, "shared" means the kIsShared finder flag is set
		//	For regular files, the status of the containing folder is checked.
		//	For folders, whether the 3rd bit of ioDirAttrib is set
		//
	static	ErrCode	isShared( const CFSpec *theSpec, long finderFlags, long attribs, long type, BOOL *bIsShared );

		///////////////////////
		//
		//	Flags for DiskVolume's.
		//
	enum {
		kMaskCaseIsPreserved = 0x01,
		kMaskCaseSensitive = 0x02,
		kMaskUnicodeSupported = 0x04,
		kMaskFilesCompressed = 0x08,
		kMaskVolumeCompressed = 0x10,
		kMaskRemovable = 0x20,
		kMaskFixed = 0x40,
		kMaskRemote = 0x80,
		kMaskCDROM = 0x100,
		kMaskRAM = 0x200,
		kMaskSystem = 0x400,
		kStdDiskVolumeReadFlagsMask = ( kMaskCaseIsPreserved | kMaskCaseSensitive |
										kMaskRemovable | kMaskFixed |
										kMaskRemote | kMaskCDROM |
										kMaskSystem ),
		kStdDiskVolumeWriteFlagsMask = 0
	} eDiskVolumeFlags;

		///////////////////////
		//
		//	Flags for DiskFile's (files or folders).
		//
	enum {
		kFileAttributes_FILE_EXECUTABLE = 0x01,
		kFileAttributes_FILE_DIR = 0x02,
		kFileAttributes_FILE_HIDDEN = 0x04,
		kFileAttributes_FILE_STATIONERY = 0x08,
		kFileAttributes_FILE_HAS_BNDL = 0x10,
		kFileAttributes_FILE_BEEN_INITED = 0x20,
		kFileAttributes_FILE_NO_INITS = 0x40,
		kFileAttributes_FILE_SHARED = 0x80,
		kFileAttributes_FILE_NAME_LOCKED = 0x100,
		kFileAttributes_FILE_CUSTOM_ICON = 0x200,
		kFileAttributes_FILE_SYSTEM = 0x400,
		kFileAttributes_FILE_ARCHIVE = 0x800,
		kFileAttributes_FILE_DEVICE = 0x1000,
		kFileAttributes_FILE_TEMP = 0x2000,
		kFileAttributes_FILE_SPARSE = 0x4000,
		kFileAttributes_FILE_REPARSEPOINT = 0x8000,
		kFileAttributes_FILE_COMPRESSED = 0x10000,
		kFileAttributes_FILE_OFFLINE = 0x20000,
		kFileAttributes_FILE_NOT_CONTENT_INDEXED = 0x40000,
		kFileAttributes_FILE_ENCRYPTED = 0x80000,
		kFileAttributes_FILE_READONLY = 0x100000,

		kStdDiskFileReadMask = ( kFileAttributes_FILE_EXECUTABLE | kFileAttributes_FILE_DIR |
									kFileAttributes_FILE_HIDDEN | kFileAttributes_FILE_STATIONERY |
									kFileAttributes_FILE_HAS_BNDL | kFileAttributes_FILE_BEEN_INITED |
									kFileAttributes_FILE_NO_INITS | kFileAttributes_FILE_SHARED |
									kFileAttributes_FILE_NAME_LOCKED | kFileAttributes_FILE_CUSTOM_ICON |
									kFileAttributes_FILE_READONLY ),

		kStdDiskFileWriteMask = ( kFileAttributes_FILE_HIDDEN | kFileAttributes_FILE_STATIONERY |
									kFileAttributes_FILE_HAS_BNDL | kFileAttributes_FILE_BEEN_INITED |
									kFileAttributes_FILE_NAME_LOCKED | kFileAttributes_FILE_CUSTOM_ICON |
									kFileAttributes_FILE_READONLY )
	} eDiskFileFlags;


private:

#if defined(macintosh)

	typedef struct DriverGestaltSyncResponse DriverGestaltSyncResponse;
	
	struct DriverGestaltVersResponse {
		NumVersion driverVersion;
	};
	typedef struct DriverGestaltVersResponse DriverGestaltVersResponse;
	
	union DriverGestaltInfo {
		DriverGestaltSyncResponse		sync;
		DriverGestaltBootResponse		boot;
		DriverGestaltDevTResponse		devt;
		DriverGestaltIntfResponse		intf;
		DriverGestaltVersResponse		vers;
		DriverGestaltEjectResponse		ejec;
		DriverGestaltPowerResponse		powr;
		DriverGestaltFlushResponse		flus;
		UInt32							i;
	};
	typedef union DriverGestaltInfo DriverGestaltInfo;

	static	ErrCode		DoDriverGestalt( short driverRefnum, short driveNumber,
											OSType gestaltSelector, DriverGestaltInfo *response );

	static	char*		DrvrRefToName(short refNum);
	static	BOOL		checkCDDriverName( StringPtr p );
	static	long		IsEjectable( short driveNumber, BOOL *bResult );
	static	ErrCode		checkForCD( long vRef, BOOL *bIsCD );

#endif

	static	long		getVolumeName( long vRef, StringPtr volumeName );
	static	BOOL		Is_local_volume( short vRef );
	static	BOOL		isSystemDisk( long vRef );
};

#endif


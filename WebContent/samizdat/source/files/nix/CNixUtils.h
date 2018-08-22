/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CNixUtils_H
#define INC_CNixUtils_H

#include "CString.h"
#include "CStringVector.h"
#include "CDateBundle.h"

/*------------------------------------------------------------------------
CLASS
	CNixUtils

	Contains static routines which implement the Linux native code.

DESCRIPTION

	These routines wrap the following Linux routines:
	<UL>
	<LI>getmntent
	<LI>statfs
	<LI>stat/lstat
	<LI>readlink
	</UL>

------------------------------------------------------------------------*/

class CNixUtils {
public:

		///////////////////////
		//
		//	The array passed to iStatFS must have at least kStatFSRetArrayLen elements,
		//	and the return values will be placed at the indicated offsets in the array.
		//
	typedef enum tageStatFS {
		kStatFSOffs_type = 0,
		kStatFSOffs_bsize = 1,
		kStatFSOffs_blocks = 2,
		kStatFSOffs_bfree = 3,
		kStatFSOffs_bavail = 4,
		kStatFSOffs_files = 5,
		kStatFSOffs_ffree = 6,
		kStatFSOffs_fsid0 = 7,
		kStatFSOffs_fsid1 = 8,
		kStatFSRetArrayLen = ( kStatFSOffs_fsid1 + 1 )
	} eStatFS;

		///////////////////////
		//
		//	The array passed to iStat must have at least kStatRetArrayLen elements,
		//	and the return values will be placed at the indicated offsets in the array.
		//
	typedef enum tageStat {
		kStatOffs_dev = 0,
		kStatOffs_ino = 1,
		kStatOffs_mode = 2,
		kStatOffs_nlink = 3,
		kStatOffs_uid = 4,
		kStatOffs_gid = 5,
		kStatOffs_rdev = 6,
		kStatOffs_size = 7,
		kStatOffs_blksize = 8,
		kStatOffs_blocks = 9,
		kStatRetArrayLen = ( kStatOffs_blocks + 1 )
	} eStat;

		///////////////////////
		//
		//	Calls getmntent
		//	[in]	csMntFileName	full path of the file containing a list of mounted file systems
		//	[out]	retQuads		on exit, 4 strings about each file system will be stored in this
		//							vector, corresponding to the following fields of the 'mntent' struct:
		//							mnt_fsname, mnt_dir, mnt_type, and mnt_opts
		//	[in]	maxToReturn		the maximum number to return
		//	[out]	numReturned		the number of file systems on which information is returned
		//							the number of strings in 'retQuads' will be 4 times this number
		//
	static	long iGetMntEnt( const CStr *csMntFileName, CStringVector *retQuads, long maxToReturn, long *numReturned );

		///////////////////////
		//
		//	Calls statfs to return information on the file system containing the given file
		//	[in]	csFilePath		the full path of the file
		//	[out]	retArray		on exit, stats on the file system will be placed at offsets defined by eStatFS
		//
	static	long iStatFS( const CStr *csFilePath, long *retArray );

		///////////////////////
		//
		//	Calls stat to get information on the given file
		//	[in]	selector		if this is zero, calls stat(); otherwise, calls lstat()
		//	[in]	csFilePath		the full path of the file
		//	[out]	retArray		on exit, stats on the file will be placed at offsets defined by eStat
		//	[out]	dateBundle		the dates of the file will be stored in this object
		//
	static	long iStat( long selector, CStr *csFilePath, long *retArray, CDateBundle *dateBundle );

		///////////////////////
		//
		//	Calls readlink
		//	[in]	csLinkName		the full path (which may be a symbolic link) to resolve
		//	[out]	csResolvedName	on exit, contains the full path of the resolved file
		//
	static	long iReadLink( const CStr *csLinkName, CStr *csResolvedName );
};

#endif

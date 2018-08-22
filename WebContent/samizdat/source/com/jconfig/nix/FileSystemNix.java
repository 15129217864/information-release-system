/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.nix;

import java.io.PrintStream;
import java.io.File;
import com.jconfig.*;

/**
Represents a file system.

This is a preliminary interface, which will be modified and expanded later.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FileSystemNix implements FileSystem {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private String		fsname, dir, type, opts;

	FileSystemNix( String fn, String dr, String ty, String op ) {
		fsname = fn;
		dir = dr;
		type = ty;
		opts = op;
	}

/**
Indicates whether this FileSystem and 'fs' are the same.
*/

	public boolean isMatch( FileSystem fs ) {
		FileSystemNix		other;

		if ( fs == null )
			return false;

		if ( !( fs instanceof FileSystemNix ) )
			return false;

		other = (FileSystemNix) fs;

		if ( fsname == null || other.fsname == null )
			return false;

		return fsname.equalsIgnoreCase( other.fsname );
	}

/**
Returns a DiskObject representing the point in the directory hierarchy where this file system is mounted.
May return null if that information is not available.
*/

	public DiskObject getMountPoint() {
		File			fl;

		fl = new File( dir );
		if ( !fl.exists() || !fl.isDirectory() )
			return null;

		return DOCreatorNix.createDiskObject( fl );
	}

/**
Returns the name of this object, as it would be displayed to the user. Note that the display
name may be different from the name of this object. If this object no longer exists,
returns null.
*/

	public String getDisplayName() {
		return fsname;
	}

/**
Returns a set of binary flags associated with this object. Use the 'getGetFlagsMask' method
to find out which bits of the returned value are significant.
*/

	public int getFlags() {
		return 0;
	}

/**
Returns a mask which indicates which bits returned by 'getFlags' are significant. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'getFlags' contains
actual information, otherwise, this information is not available.
*/

	public int getGetFlagsMask() {
		return 0;
	}

/**
Retrieves the the maximum number of bytes of data this file system can hold, as well as the number of bytes currently
available on this file system.
@param cap on return from this method, this array will contain the capacity and free space. It must have at least
'FS_GETCAP_LENGTH' elements. The capacity of the file system will be stored at offset 'FS_GETCAP_CAP_OFFSET', and the
free space will be stored at offset 'FS_GETCAP_FREESPACE_OFFSET'.
*/
	
	public int getCapacity( long cap[] ) {
		long		bAvail, bTotal, blockSize;
		int			retVals[], theErr;

		retVals = new int[ AppUtilsNix.kStatFSRetArrayLen ];
		theErr = AppUtilsNix.statFS( dir, retVals );
		if ( theErr != ErrCodes.ERROR_NONE )
			return theErr;

		bAvail = retVals[ AppUtilsNix.kStatFSOffs_bavail ];
//		bFree = retVals[ AppUtilsNix.kStatFSOffs_bfree ];
//		bTotal = bFree + retVals[ AppUtilsNix.kStatFSOffs_bavail ];
		bTotal = retVals[ AppUtilsNix.kStatFSOffs_blocks ];
		blockSize = retVals[ AppUtilsNix.kStatFSOffs_bsize ];

		cap[ FS_GETCAP_CAP_OFFSET ] = blockSize * bTotal;
		cap[ FS_GETCAP_FREESPACE_OFFSET ] = blockSize * bAvail;

		return theErr;
	}

/**
Returns the maximum length each element of a path name can have.
*/
	
	public int getMaxFileNameLength() {
		return 256;
	}

	String getDir() {
		return dir;
	}

	public long getReferenceNumber() {
		int			retVals[], theErr;

		retVals = new int[ AppUtilsNix.kStatFSRetArrayLen ];
		theErr = AppUtilsNix.statFS( dir, retVals );
		if ( theErr != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getReferenceNumber=" + theErr );

		return JUtils.intsToLong( retVals[ AppUtilsNix.kStatFSOffs_fsid1 ], retVals[ AppUtilsNix.kStatFSOffs_fsid0 ] );
	}

	public String toString() {
		DiskObject		dobj;
		String			mnt;
		long			cap[];

		dobj = getMountPoint();
		if ( dobj == null )
			mnt = "<unk>";
		else
			mnt = dobj.getDisplayName();

		cap = new long[ FS_GETCAP_LENGTH ];
		getCapacity( cap );
	
		return "< " + fsname + ", mount point=" + mnt + ", type=" + type + ", opts=" + opts + ", " +
				cap[ FS_GETCAP_FREESPACE_OFFSET ] + " of " +
				cap[ FS_GETCAP_CAP_OFFSET ] + " free, refNum=" + getReferenceNumber() + " >";
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "FileSystem: " + toString() );
	}
}

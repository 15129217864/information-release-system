/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig;

import java.io.PrintStream;

/**
Represents a file system.

This is a preliminary interface, which will be modified and expanded later.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface FileSystem extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Mask used to indicate that case is preserved on this file system.
*/

	public static final int		VOL_CASE_PRESERVED = 0x01;

/**
Mask used to indicate that file names used on this file system are case sensitive.
*/

	public static final int		VOL_CASE_SENSITIVE = 0x02;

/**
Mask used to indicate that this file system supports file names stored with Unicode.
*/

	public static final int		VOL_UNICODE = 0x04;

/**
Mask used to indicate that files saved to this file system will be compressed.
*/

	public static final int		VOL_FILES_COMPRESSED = 0x08;

/**
Mask used to indicate that this file system supports volume-level compression.
*/

	public static final int		VOL_VOL_COMPRESSED = 0x10;

/**
Mask used to indicate that this file system is removable.
*/

	public static final int		VOL_REMOVABLE = 0x20;

/**
Mask used to indicate that this file system is fixed.
*/

	public static final int		VOL_FIXED = 0x40;

/**
Mask used to indicate that this is a remote file system.
*/

	public static final int		VOL_REMOTE = 0x80;

/**
Mask used to indicate that this is a CD-ROM.
*/

	public static final int		VOL_CDROM = 0x100;

/**
Mask used to indicate that this is a RAM disk.
*/

	public static final int		VOL_RAM = 0x200;

/**
Mask used to indicate that this is system disk.
*/

	public static final int		VOL_SYSTEM = 0x400;

/**
The minimum length of the 'cap' argument to the 'getCapacity()' method.
*/

	public static final int		FS_GETCAP_LENGTH = 2;

/**
The minimum length of the 'cap' argument to the 'getCapacity()' method.
*/

	public static final int		FS_GETCAP_CAP_OFFSET = 0;

/**
The minimum length of the 'cap' argument to the 'getCapacity()' method.
*/

	public static final int		FS_GETCAP_FREESPACE_OFFSET = 1;

/**
Indicates whether this FileSystem and 'fs' are the same.
*/

	boolean isMatch( FileSystem fs );

/**
Returns a DiskObject representing the point in the directory hierarchy where this file system is mounted.
May return null if that information is not available.
*/

	DiskObject getMountPoint();

/**
Returns the name of this object, as it would be displayed to the user. Note that the display
name may be different from the name of this object. If this object no longer exists,
returns null.
*/

	String getDisplayName();

/**
Returns a set of binary flags associated with this object. Use the 'getGetFlagsMask' method
to find out which bits of the returned value are significant.
*/

	int getFlags();

/**
Returns a mask which indicates which bits returned by 'getFlags' are significant. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'getFlags' contains
actual information, otherwise, this information is not available.
*/

	int getGetFlagsMask();

/**
Retrieves the the maximum number of bytes of data this file system can hold, as well as the number of bytes currently
available on this file system.
@param cap on return from this method, this array will contain the capacity and free space. It must have at least
'FS_GETCAP_LENGTH' elements. The capacity of the file system will be stored at offset 'FS_GETCAP_CAP_OFFSET', and the
free space will be stored at offset 'FS_GETCAP_FREESPACE_OFFSET'.
*/
	
	int getCapacity( long cap[] );

/**
Returns the maximum length each element of a path name can have.
*/
	
	int getMaxFileNameLength();

/**
Returns the platform-specific number used to uniquely identify this file system.
On 'Nix, this is the f_fsid field of the statfs structure returned from fstatfs
*/

	long getReferenceNumber();
}

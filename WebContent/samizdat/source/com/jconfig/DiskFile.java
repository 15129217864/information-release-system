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
Represents a file.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface DiskFile extends DiskObject {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
See the file types table in overview.html
*/

	public static final int		FILE_EXECUTABLE = 0x01;

/**
See the file types table in overview.html
*/

	public static final int		FILE_DIR = 0x02;

/**
See the file types table in overview.html
*/

	public static final int		FILE_HIDDEN = 0x04;

/**
See the file types table in overview.html
*/

	public static final int		FILE_STATIONERY = 0x08;

/**
See the file types table in overview.html
*/

	public static final int		FILE_HAS_BNDL = 0x10;

/**
See the file types table in overview.html
*/

	public static final int		FILE_BEEN_INITED = 0x20;

/**
See the file types table in overview.html
*/

	public static final int		FILE_NO_INITS = 0x40;

/**
See the file types table in overview.html
*/

	public static final int		FILE_SHARED = 0x80;

/**
See the file types table in overview.html
*/

	public static final int		FILE_NAME_LOCKED = 0x100;

/**
See the file types table in overview.html
*/

	public static final int		FILE_CUSTOM_ICON = 0x200;

/**
See the file types table in overview.html
*/

	public static final int		FILE_SYSTEM = 0x400;

/**
See the file types table in overview.html
*/

	public static final int		FILE_ARCHIVE = 0x800;

/**
See the file types table in overview.html
*/

	public static final int		FILE_DEVICE = 0x1000;

/**
See the file types table in overview.html
*/

	public static final int		FILE_TEMP = 0x2000;

/**
See the file types table in overview.html
*/

	public static final int		FILE_SPARSE = 0x4000;

/**
See the file types table in overview.html
*/

	public static final int		FILE_REPARSEPOINT = 0x8000;

/**
See the file types table in overview.html
*/

	public static final int		FILE_COMPRESSED = 0x10000;

/**
See the file types table in overview.html
*/

	public static final int		FILE_OFFLINE = 0x20000;

/**
See the file types table in overview.html
*/

	public static final int		FILE_NOT_CONTENT_INDEXED = 0x40000;

/**
See the file types table in overview.html
*/

	public static final int		FILE_ENCRYPTED = 0x80000;

/**
See the file types table in overview.html.
*/

	public static final int		FILE_READONLY = 0x100000;

/**
Returns the length of this file. Note that on Mac, this includes both forks. If this object is a
directory, or if an error occurs, 0 is returned.
*/
 
	long getFileSize();

/**
Returns the size of this file's resource fork, if any. On Windows, zero is returned.
*/

	long getResourceForkSize();

/**
Returns the DiskVolume object which contains this file or directory.
*/
	
	DiskVolume getVolume();

/**
Returns the VersionInfo object which is associated with this file. Returns null if
no version information is available.
*/

	VersionInfo getVersion();
	
/**
Retrieves platform-specific data for this file. On Mac, the vRef is at offset 0, and the
parID is at offset 1.
*/

	int[] getPlatformData();

/**
Retrieves the FinderInfo associated with this file. Returns null or throws an exception if an error occurs.
@return a value containing the creator and file type of this file.
*/

	FinderInfo getFinderInfo();

/**
Sets the FinderInfo of this file. The FinderInfo is the creator and file
type of this file. This only works for files, not directories. Returns zero if no error occured;
non-zero otherwise.
*/
	
	int setFinderInfo( FinderInfo newFI );

/**
Returns the the short version of the file's name, if applicable
*/

	String getShortName();

/**
Convenience method which converts a given set of DiskFile flags into a string representation.
*/

	String diskFileFlagsToString( int f );

/**
Returns the ResourceFork object for this file. Only valid on Mac; on other platforms, returns null.
*/

	ResourceFork getResourceFork();
}



/*
Mask used to indicate that this file is an executable.
Mask used to indicate that this is a directory.
Mask used to indicate that this file/directory is hidden.
Mask used to indicate that this file is a stationery form.
Mask used to indicate that the name of this file/directory cannot be changed.
Mask used to indicate that this file/directory has a custom icon.
*/

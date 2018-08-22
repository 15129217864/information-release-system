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
Represents a disk volume.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface DiskVolume extends DiskObject {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Mask used to indicate that case is preserved on this volume.
*/

	public static final int		VOL_CASE_PRESERVED = 0x01;

/**
Mask used to indicate that file names used on this volume are case sensitive.
*/

	public static final int		VOL_CASE_SENSITIVE = 0x02;

/**
Mask used to indicate that this volume supports file names stored with Unicode.
*/

	public static final int		VOL_UNICODE = 0x04;

/**
Mask used to indicate that files saved to this volume will be compressed.
*/

	public static final int		VOL_FILES_COMPRESSED = 0x08;

/**
Mask used to indicate that this volume supports volume-level compression.
*/

	public static final int		VOL_VOL_COMPRESSED = 0x10;

/**
Mask used to indicate that this volume is removable.
*/

	public static final int		VOL_REMOVABLE = 0x20;

/**
Mask used to indicate that this volume is fixed.
*/

	public static final int		VOL_FIXED = 0x40;

/**
Mask used to indicate that this is a remote volume.
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
Returns the prefix used to access this volume. E.g., 'c://' or '/Macintosh%20HD/'.
*/

	String getPrefix();

/**
Returns the maximum length each element of a path name can have.
*/
	
	int getMaxFileNameLength();

/**
Returns the platform-specific number used to uniquely identify this volume. On Mac, this is
a volume reference number. On Windows, this is a volume serial number.
*/

	long getReferenceNumber();

/**
Returns the maximum number of bytes of data this volume can hold.
*/
	
	long getMaxCapacity();

/**
Returns the number of bytes currently available on this volume.
*/
	
	long getFreeSpace();
}

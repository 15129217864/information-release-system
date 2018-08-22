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

import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.io.FileNotFoundException;

/**
The interfaces DiskVolume, DiskFile, and DiskAlias extend this interface, which contains
common methods for disk-related objects.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface DiskObject extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
See the 'type' argument of the 'createObject() method.
*/

	public static final int		DO_CREATEFILE = 0;

/**
See the 'type' argument of the 'createObject() method.
*/

	public static final int		DO_CREATEDIR = 1;

/**
Returns the name of this object. If this object no longer exists or if an error occurs,
returns null.
*/

	String getName();

/**
Sets the name of this object. Returns 0 if the name was set successfully, non-zero otherwise.
The name must be in quoted-printable form.
*/

	int setName( String newName );

/**
Returns the name of this object, as it would be displayed to the user. Note that the display
name may be different from the name of this object. If this object no longer exists,
returns null.
*/

	String getDisplayName();
	
/**
Returns a DateBundle containing zero or more of the dates associated with this object:
the creation date, modification date, backup date, and access date. Use this instead of the
following methods. Any or all of the dates in the DateBundle may be null if this object
no longer exists, of if it is not possible to determine this information. This method can
return null in those cases as well.
*/

	DateBundle getDateBundle();

/**
Sets the set of dates associated with this file.
@param newDates if one or more of the dates in this bundle are null, they will not be changed.
*/

	void setDateBundle( DateBundle newDates );

/**
Returns the icon bundle associated with this object. Returns null if this object does not have
any associated icons, or if this object no longer exists.
*/

	IconBundle getIconBundle();

/**
Returns the Mac-style color coding label for this object. Returns a number between 0 and 7;
0 signifies that there is no color coding.
*/

	int getColorCoding();

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
Sets the binary flags associated with this object. Use the 'getSetFlagsMask' method
to find out which bits can be set.

@param whichFlags a mask which indicates which bits in 'flags' will be set or reset

For instance, if whichFlags is 0011, and flags is 0101, and the rightmost digit
in the preceding numbers is bit #0, then, only bits #0 and #1 will be changed;
bit #0 (indicated by the rightmost digit of whichFlags being 1) will be changed to 1 (the rightmost value in flags),
and bit #1 will be changed to 0. All the other flags will not be changed, because only bits #0 and bits #1 are
set in the mask.

@param flags contains the new values of the bits indicated by the whichFlags mask

@exception OSException if an OS error occurs
*/

	void setFlags( int whichFlags, int flags );

/**
Returns a mask which indicates which bits can be set by 'setFlags'.
For instance, if bit 0 of the return value of this method is set,
bit 0 of the argument to 'setFlags' can be set.

@exception OSException if an OS error occurs
*/

	int getSetFlagsMask();
	
/**
Returns a copy of the File object associated with this object.
*/

	File getFile();

/**
Returns true if this object still exists, false otherwise.
*/

	boolean exists();

/**
Enumerates the contents of this object. If this object does not contain other objects, returns
-1. Other error codes may be returned. Returns 0 if no error occurs.
@param filter a DiskFilter object, to which each of the contained items will be presented. The
DiskFilter returns a boolean; true means to continue, false means to stop.
@param flags indicates what type of objects should be ignored. If zero, all objects will be
presented to the filter. If you want to exclude various types of objects, OR together the
appropriate constants from the DiskFilter interface: kIgnoreHidden, kIgnoreFolders,
kIgnoreFiles, kIgnoreAliases, kIgnoreNameLocked.
@param maxToIterate the maximum number of files to present to the DiskFilter
*/

	int iterate( DiskFilter filter, int flags, int maxToIterate );

/**
Returns the DiskObject which contains this object. If there is no such object ( i.e., this is
a volume ), returns null.
*/

	DiskObject getContainer() throws FileNotFoundException, DiskFileException;

/**
Returns the FileSystem which contains this object. If that information cannot be determined, returns null.
*/

	FileSystem getFileSystem();

/**
Sets the modification date of the container of this object to the current time. Use this method,
for instance, to inform the Finder that you've made changes to an object.
*/

	int updateContainer();

/**
Used to create a file, folder or other object which will be contained by this DiskObject.
Returns the new DiskObject, or null if the object could not be created.
@param name the name of the new object
@param type either 'DO_CREATEFILE' or 'DO_CREATEDIR'
@param flags reserved; set to 0
The following logic is used to create the object:
<PRE>
if type == DO_CREATEDIR
	if an object with that name already exists
		if it's a folder
			return a DiskObject representing that folder
		else
			return null
	else
		create a new folder, and return a DiskObject representing the new folder
else if type == DO_CREATEFILE
	if an object with that name already exists
		if it's a file
			return a DiskObject representing that file
		else
			return null
	else
		create a new, empty file, and return a DiskObject representing the new file
</PRE>
*/
	DiskObject createObject( String name, int type, int flags );
}







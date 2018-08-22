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

import java.io.IOException;

/**
Represents the resource fork of a file.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface ResourceFork extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
See openResources()
*/

	public static final int RESFORK_OPENEXISTING = 1;

/**
See openResources()
*/

	public static final int RESFORK_READONLY = 2;

/**
Returns the DiskFile object with which this resource fork is associated.
*/

	DiskFile getDiskFile();

/**
Returns the raw resource fork of this file. Only valid on Mac.
*/

	byte[] getRawResourceFork() throws ResourceForkException;

/**
Sets the raw resource fork of this file.

<P>
WARNING: this will overwrite any previous contents of this file's resource fork

<P>
If an error occurs in the middle of this routine, the resource fork may be deleted,
and the new resource data may not be able to be written.

<P>
The data must be in the expected resource format, otherwise Mac errors or crashes may occur
when the file is used.

<P>
USE WITH CAUTION!

@param data contains the raw resource fork. Must have length >= 1 
*/

	void setRawResourceFork( byte data[] ) throws ResourceForkException;

/**
Deletes the resource fork.
*/
	void deleteResourceFork() throws ResourceForkException;

/**
Returns the size of this resource fork.
*/

	long getResourceForkSize() throws ResourceForkException;

/**
This method must be called before calling the getResource() method. See that method for more
information.
@param mode currently, must be RESFORK_OPENEXISTING
@param perms currently, must be RESFORK_READONLY
*/

	void openResources( int mode, int perms ) throws ResourceForkException, IOException;

/**
Returns a resource. openResources() must have been called before calling this routine,
and closeResources() must be called when you are finished getting resources:

<PRE>
	openResources(...)
	getResource(...)
	...
	getResource(...)
	closeResources()
</PRE>

You will need to decode the format of the resource.

@param resName the name of the resource, for instance 'VERS' Use the JUtils.asciiToInt()
or similar method to compute this value.
@param resID the ID of the resource
*/

	byte[] getResource( int resName, int resID ) throws ResourceForkException;

/**
This method must be called after you are finished getting resources.
See the getResource() method
*/

	void closeResources() throws ResourceForkException;
}


/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.win;

import com.jconfig.*;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;

/**
A singleton used to create DiskObject's.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DOCreatorMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Flags for the createNewDiskObject() method.
*/

	static final int				kCreateDiskObjectFile = 0;
	static final int				kCreateDiskObjectFolder = 1;

/**
Create a file or directory in the given directory
@param newName the name of the new file
@param par the directory in which the file will be created.
@param flags ignored; set to 0
@param whichType indicates whether file or folder, as follows:

<PRE>
if whichType => folder
	if an object with that name already exists
		if it's a folder
			return a DiskObject representing that folder
		else
			return null
	else
		create a new folder, and return a DiskObject representing the new folder
else if whichType => file
	if an object with that name already exists
		if it's a file
			return a DiskObject representing that file
		else
			return null
	else
		create a new, empty file, and return a DiskObject representing the new file
</PRE>
*/

	static DiskObject createNewDiskObject( String par, String newName, int whichType, int flags ) {
		PrintStream		ps;
		File			newFile, parFile;

		try {
			parFile = new File( par );
			if ( !parFile.exists() || !parFile.isDirectory() ) {
				return null;
			}

			newFile = new File( parFile, newName );

			if ( whichType == kCreateDiskObjectFolder ) {
				if ( newFile.exists() ) {

					if ( newFile.isDirectory() ) {
						return new DiskFileMSVM( newFile );
					}
					else {
						return null;
					}
				}

				newFile.mkdir();

				return new DiskFileMSVM( newFile );
			}
			else if ( whichType == kCreateDiskObjectFile ) {
				if ( newFile.exists() ) {

					if ( newFile.isDirectory() ) {
						return null;
					}
					else {
						return createDiskObject( newFile );
					}
				}

					//	write empty file
				ps = new PrintStream( new FileOutputStream( newFile ) );
				ps.close();

				return createDiskObject( newFile );
			}
			else
				return null;
		}
		catch ( Exception e ) {
			return null;
		}
	}

/**
Create a DiskObject from a java.io.File object. If the file doesn't exist, or if an error occurs, null is returned
@param fl the preexisting File, which might be a volume, directory, file, or alias.
*/

	static DiskObject createDiskObject( File fl ) {
		String			driveName;

		try {
			if ( AppUtilsMSVM.isLinkFile( fl ) ) {
				return new DiskAliasMSVM( fl );
			}
			else if ( AppUtilsMSVM.isDrivePath( fl ) ) {
				driveName = AppUtilsMSVM.pathToDriveName( fl );
				if ( driveName == null )
					return null;
				return new DiskVolumeMSVM( driveName );
			}
			else {
				return new DiskFileMSVM( fl );
			}
		}

		catch ( Exception e ) {
			return null;
		}
	}

	private DOCreatorMSVM() {}
}


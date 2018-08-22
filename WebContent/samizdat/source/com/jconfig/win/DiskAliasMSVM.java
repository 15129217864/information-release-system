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
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
Represents a .lnk file.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskAliasMSVM extends DiskFileMSVM implements DiskAlias {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	DiskAliasMSVM( File fl ) throws FileNotFoundException, DiskFileException {
		super( fl );
	}

/**
Calls native code to resolve this alias, and indicate what type of object it points to, if any.
1,2,1 CHANGE: No user interaction is allowed, however, the .lnk file may be updated.
*/

	public int getAliasType() {
		File			testFile;
		String			retPath[];
		int				theErr, cat[];

		if ( !exists() )
			return ALIAS_NOLONGER;

		retPath = new String[ 1 ];

		theErr = AppUtilsMSVM.resolveLinkFile( getFilePath(), retPath, AppUtilsMSVM.kResolveLinkFileNoUI );
		if ( theErr != ErrCodes.ERROR_NONE )
			return ALIAS_NOLONGER;

		try {
			testFile = new File( retPath[ 0 ] );
			if ( !testFile.exists() )
				return ALIAS_NOLONGER;
			
			if ( AppUtilsMSVM.isDrivePath( testFile ) )
				return ALIAS_VOL;
			else if ( testFile.isDirectory() )
				return ALIAS_DIR;
			else 
				return ALIAS_FILE;
		}
		catch ( Exception e ) {
			return ALIAS_NOLONGER;
		}
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		int			flags;

		flags = getFlags();

		ps.println( indent + "DiskAliasMSVM " + hashCode() +": name=" + getName() );
		ps.println( indent + "  get flags=" + diskFileFlagsToString( flags ) + " " + Integer.toHexString( flags ) );
		ps.println( indent + "  " + getDateString() );
		ps.println( indent + "  file=" + getFile().getPath() );
		ps.println( indent + "  file size=" + getFileSize() );

		try {
			ps.println( indent + "  contained by " + getContainer() );
		}
		catch ( Exception e ) {
			ps.println( indent + "  can't get container: " + e );
		}
	}
}


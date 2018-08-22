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

import com.jconfig.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskAliasNix extends DiskFileNix implements DiskAlias {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	DiskAliasNix( File fl ) throws FileNotFoundException, DiskFileException {
		super( fl );
	}

	public int getAliasType() {
		File			testFile;
		String			retPath[];
		int				theErr, cat[];

		if ( !exists() )
			return ALIAS_NOLONGER;

		retPath = new String[ 1 ];

		theErr = AppUtilsNix.resolveLinkFile( getFilePath(), retPath, 0 );
		if ( theErr != ErrCodes.ERROR_NONE )
			return ALIAS_NOLONGER;

		try {
			testFile = new File( retPath[ 0 ] );
			if ( !testFile.exists() )
				return ALIAS_NOLONGER;
			
			if ( AppUtilsNix.isDrivePath( testFile ) )
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
}


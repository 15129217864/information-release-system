package com.tolstoy.testjc;

import java.io.*;
import com.jconfig.*;

/**
This file illustrate how to create a Windows link file ( i.e., a shortcut )

Feel free to modify this file as you wish.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class MakeLink {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private static final int		MY_SIGNATURE = JUtils.asciiToInt( "fred" );

	static PrintStream			outStream = null;

	public static void main( String args[] ) {
		File				curDir;
		
		try {
			curDir = new File( System.getProperty( "user.dir" ) );

			outStream = new PrintStream( new FileOutputStream( new File( curDir, "results.txt" ) ) );
			Trace.setOut( outStream );
			Trace.setDestination( Trace.TRACE_FILE );

			FileRegistry.initialize( curDir, MY_SIGNATURE );
			if ( !FileRegistry.isInited() ) {
				System.out.println( "Please check your configuration." );
				return;
			}

			tryMakeLink( "c:\\windows\\desktop\\target.txt", "c:\\windows\\desktop\\shortcut.lnk" );
		}
		catch ( Exception e ) {
			System.out.println( "problems: " + e );
			e.printStackTrace( System.out );
		}

		outStream.println( "" );
		outStream.println( "*** Ciao." );
		outStream.close();
	}

	private static int tryMakeLink( String targetPath, String linkPath ) {
		DiskObject		targetDiskObject;
		File			targetFile, linkFile;
		int				theErr;

		try {
			targetFile = new File( targetPath );
			targetDiskObject = FileRegistry.createDiskObject( targetFile, 0 );

			linkFile = new File( linkPath );
			
				//	the link file must exist; it will be overwritten
			( new FileOutputStream( linkFile ) ).close();

			theErr = FileRegistry.createAlias( targetDiskObject, linkFile, 0, 0 );
		}
		catch ( Exception e ) {
			outStream.println( "can't create link file, e=" + e );
			return -1;
		}

		if ( theErr != ErrCodes.ERROR_NONE )
			outStream.println( "can't create link file, theErr=" + theErr );
		
		return theErr;
	}
}


/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.mac;

import com.jconfig.*;

import java.io.File;

/**
Calls the testLink() methods of the four Mac-specific classes which use native code. These methods
call each of their class' native methods with bogus values. This should result in each native
method returning -50 or another error code. However, if some native methods couldn't be linked,
exceptions will be listed.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class TestLinkMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	public static void main( String args[] ) {
		File	curDir;
		int		err;

		try {
			curDir = new File( System.getProperty( "user.dir" ) );

			FileRegistry.initialize( curDir, JUtils.asciiToInt( "fred" ) );
			if ( !FileRegistry.isInited() ) {
				Trace.println( "Please check your configuration." );
				return;
			}
		}
 		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
		catch ( Error er ) {
			Trace.println( "problems: " + er );
			er.printStackTrace( Trace.getOut() );
		}

		try {
			Trace.println( "************** testing AppUtilsMRJ ******************" );
			AppUtilsMRJ.testLink();
			Trace.println( "" );
		}
 		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
		catch ( Error er ) {
			Trace.println( "problems: " + er );
			er.printStackTrace( Trace.getOut() );
		}

		try {
			Trace.println( "************** testing AppFinderMRJ ******************" );
			AppFinderMRJ.testLink();
			Trace.println( "" );
		}
 		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
		catch ( Error er ) {
			Trace.println( "problems: " + er );
			er.printStackTrace( Trace.getOut() );
		}

		try {
			Trace.println( "************** testing IConfigMRJ ******************" );
			IConfigMRJ.testLink();
			Trace.println( "" );
		}
 		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
		catch ( Error er ) {
			Trace.println( "problems: " + er );
			er.printStackTrace( Trace.getOut() );
		}

		try {
			Trace.println( "************** testing ResFileMRJ ******************" );
			ResFileMRJ.testLink();
			Trace.println( "" );
		}
 		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
		catch ( Error er ) {
			Trace.println( "problems: " + er );
			er.printStackTrace( Trace.getOut() );
		}
	}
}




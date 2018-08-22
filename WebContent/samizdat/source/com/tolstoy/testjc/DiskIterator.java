package com.tolstoy.testjc;

import com.jconfig.*;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

/**
@author JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class DiskIterator {
		//	initialize JConfig, and then create the iterator
	public static void main( String args[] ) {
		File				curDir;

		try {
			curDir = new File( System.getProperty( "user.dir" ) );

			Trace.setOut( new PrintStream( new FileOutputStream( new File( "results.txt" ) ) ) );
			Trace.setDestination( Trace.TRACE_FILE );

			FileRegistry.initialize( curDir, JUtils.asciiToInt( "fred" ) );
			if ( !FileRegistry.isInited() ) {
				Trace.println( "Please check your configuration." );
				return;
			}

			DiskIterator iterator = new DiskIterator();
		}
		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}
	}

	public DiskIterator() {
		DiskVolume			mountedVolumes[];
		GenDiskFilter		df;
		DiskObject			savedItems[];
		int					i, theErr;

		mountedVolumes = FileRegistry.getVolumes();
		if ( mountedVolumes == null ) {
			Trace.println( "can't get mounted volumes" );
			return;
		}

		System.out.println( "there are " + mountedVolumes.length + " volumes" );

		for ( i = 0; i < mountedVolumes.length; i++ ) {
			mountedVolumes[ i ].dumpInfo( Trace.getOut(), "" );
		}

		df = new GenDiskFilter( 10 );

		theErr = mountedVolumes[ 0 ].iterate( df, DiskFilter.IGNORE_NAME_LOCKED | DiskFilter.IGNORE_HIDDEN, 10 );

		savedItems = df.getArray();

		if ( savedItems == null || theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "iterated on " + mountedVolumes[ 0 ] + ", got err=0x" + Integer.toHexString( theErr ) );
			return;
		}

		for ( i = 0; i < savedItems.length; i++ ) {
			savedItems[ i ].dumpInfo( Trace.getOut(), "" );
			if ( savedItems[ i ] instanceof DiskAlias )
				doAlias( (DiskAlias) savedItems[ i ] );
		}
	}

	void doAlias( DiskAlias da ) {
	}
}


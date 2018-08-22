/*
	TestResFork.java tests the new ResourceFork interface.
	This only works on Mac.

	This sample prompts for a file, and copies the selected file into a new file
	named 'JConfigSampleFile'.

	**Both** forks of the file are copied; "standard" Java can only do the data fork.


	The compiled version of this file is included in JConfig.zip.

	Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

package com.tolstoy.testjc;

import com.jconfig.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.io.PrintStream;
import java.util.Random;

/**
This class is used to test this package on Mac.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class TestResFork {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	public static final int		MY_SIGNATURE = JUtils.asciiToInt( "fred" );

	public static void main( String args[] ) {
		FinderInfo			finfo;
		FileExtension		txtExtension;
		AppFile				simpleAppFile = null;
		AppProcess			simpleAppProcess = null;
		File				curDir;
		
		try {
			curDir = new File( System.getProperty( "user.dir" ) );

				//	output all trace messages to System.out
			Trace.setDestination( Trace.TRACE_SYSOUT );

			FileRegistry.initialize( curDir, MY_SIGNATURE );
			if ( !FileRegistry.isInited() ) {
				Trace.println( "Please check your configuration." );
				return;
			}

			tryResourceForkDemo();
		}
		catch ( Exception e ) {
			Trace.println( "problems: " + e );
			e.printStackTrace( Trace.getOut() );
		}

		Trace.println( "" );
		Trace.println( "*** Ciao." );
	}

	private static void tryResourceForkDemo() {
		File			selectedFile, newFile;
		String			fileName;

		fileName = JUtils.getFileNameFromUser();
		if ( fileName == null ) {
			Trace.println( "-- cancelled --" );
			return;
		}

		try {
			selectedFile = new File( fileName );
			newFile = new File( selectedFile.getParent(), "JConfigSampleFile" );
			if ( newFile.exists() )
				newFile.delete();

			tryCopyDataFork( selectedFile, newFile );
			tryCopyResourceFork( selectedFile, newFile );
		}
		catch ( Exception e ) {
			Trace.println( "-- file problems --" + e );
		}
	}

	private static void tryCopyResourceFork( File srcFile, File dstFile )
	throws ResourceForkException, DiskFileException, FileNotFoundException {
		DiskFile			srcDiskObj, dstDiskObj;
		ResourceFork		srcResFork, dstResFork;
		FinderInfo			newFI;
		byte				rawData[];
		int					theErr;

			//	create DiskFile objects for both files, and get their resource forks

		srcDiskObj = (DiskFile) FileRegistry.createDiskObject( srcFile, FileRegistry.ALIAS_UI );
		dstDiskObj = (DiskFile) FileRegistry.createDiskObject( dstFile, FileRegistry.ALIAS_UI );

		srcResFork = srcDiskObj.getResourceFork();
		dstResFork = dstDiskObj.getResourceFork();


			//	read the raw resource fork from the source file, and set that data as the raw resource
			//	fork of the destination file

		rawData = srcResFork.getRawResourceFork();

		dstResFork.setRawResourceFork( rawData );


			//	set the Finder Info of the destination file to that of the source file

		theErr = dstDiskObj.setFinderInfo( srcDiskObj.getFinderInfo() );
	}

		//	this is not optimal of course

	private static void tryCopyDataFork( File srcFile, File dstFile )
	throws FileNotFoundException, IOException {
		FileInputStream			fis;
		FileOutputStream		fos;
		byte					buffer[];
		long					dataForkLen;

		dataForkLen = srcFile.length();
		buffer = new byte[ (int) dataForkLen ];
		fis = new FileInputStream( srcFile );
		fis.read( buffer );
		fis.close();

		fos = new FileOutputStream( dstFile );
		fos.write( buffer );
		fos.close();
	}
}



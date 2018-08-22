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

/**
The master FileRegistryFactoryI, which is called by FileRegistry to create an object implementing the
FileRegistryI interface. All calls to FileRegistry will be delegated to the object returned from this
class.

<BR>
This class implements the behavior described at the start of 'FileRegistryFactoryI': it reads the
'jcfactrz.txt' file, and creates each class listed in this file.

<BR>
Each class listed in 'jcfactrz.txt' should implement the FileRegistryFactoryI interface.
This class will create an instance of each class listed in the 'jcfactrz.txt' file and
call that object's createFileRegistry() method. If that method returns an object, the object is
returned to FileRegistry. Otherwise, it tries the next FileRegistryFactoryI class listed in this file.

<BR>
If all FileRegistryFactoryI objects return null, a FileRegistryPlain object is created, if possible,
and returned to the FileRegistry.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class FileRegistryFactory implements FileRegistryFactoryI {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final String		kFactoryFileName = "jcfactrz.txt",
									defaultFactoryClasses[] = {
																"com.jconfig.win.FileRegistryFactoryWin",
																"com.jconfig.mac.FileRegistryFactoryMac",
																"com.jconfig.nix.FileRegistryFactoryNix"
																};
	private FileRegistryI			obj = null;
	private boolean					bBeenInited = false;

	public FileRegistryFactory() {
	}

	public FileRegistryI createFileRegistry( File curDir, int creator ) {
		if ( !bBeenInited )
			initialize( curDir, creator );
		
		return obj;
	}

/**
First, figure out which platform we're running on. Try to create a platform specific object implementing
FileRegistryI (such as FileRegistryMSVM or FileRegistryMRJ).
If none of the platform-specific objects can be created, try to create a FileRegistryPlain object.
*/

	private void initialize( File curDir, int creator ) {
		bBeenInited = true;

		printPlatformInfo();

		tryCreatePlatformObject( curDir, creator );

		if ( obj == null )
			tryCreatePlainObject( curDir, creator );

		if ( obj == null )
			Trace.println( "Please check your configuration" );
	}


/**
Open the 'jcfactrz.txt' file, and convert this file into an array of Strings.
Then, for each of the Strings, which should be the name of a class, create an instance of that class,
which should implement the FileRegistryFactoryI interface.
Each object is called, and given a chance to create a FileRegistryI object.
If it does create such an object, we're done.
The instance variable 'obj' is set by this method.

<P>
For instance, with 'jcfactrz.txt' having its default values, this method will create a 'FileRegistryFactoryWin'
object, and then call its 'createFileRegistry()' method.
If this method return null, we keep searching.
Otherwise, it will return a 'FileRegistryMSVM' object, and we're done.
*/
	private void tryCreatePlatformObject( File curDir, int creator ) {
		FileRegistryFactoryI		factory;
		FileRegistryI				tempObj;
		Class						factoryClass;
		File						factoryFile;
		String						classNames[];
		int							i;

		classNames = null;
		factoryFile = null;

		try {
			factoryFile = new File( curDir, kFactoryFileName );

			if ( factoryFile.exists() )
				classNames = JUtils.fileToStringArray( factoryFile );
		}
		catch ( Exception e ) {
			Trace.println( "FRF.tcpo, e=" + e );
		}

		if ( classNames == null ) {
			classNames = defaultFactoryClasses;
			if ( curDir == null )
				Trace.println( "WARNING: can't read jcfactrz.txt, curDir is null; using defaults" );
			else
				Trace.println( "WARNING: can't read jcfactrz.txt in " + curDir.getPath() + "; using defaults" );
		}

		for ( i = 0; i < classNames.length; i++ ) {
			try {
				Trace.println( "FRF.tcpo, " + i + " of " + classNames.length + ", trying to create " + classNames[ i ] );
				factoryClass = Class.forName( classNames[ i ] );
				factory = (FileRegistryFactoryI) factoryClass.newInstance();
				tempObj = factory.createFileRegistry( curDir, creator );
				if ( tempObj == null )
					Trace.println( "  didn't work" );
				else {
					Trace.println( "  that worked" );
					obj = tempObj;
					return;
				}
			}
			catch ( Exception e ) {
				Trace.println( "FRF.tcpo2, e=" + e );
			}
		}

		Trace.println( "FRF.tcpo, nothing found" );
	}

/**
Create a FileRegistryPlain object.
*/

	private void tryCreatePlainObject( File curDir, int creator ) {
		try {
			Trace.println( "trying to create plain object" );
			obj = new FileRegistryPlain( curDir, creator );
			Trace.println( "    worked" );
		}
		catch ( Exception e ) {
			Trace.println( "can't create plain object:" + e );
			obj = null;
		}
		catch ( Error er ) {
			Trace.println( "can't create plain object:" + er );
			obj = null;
		}
	}

	private void printPlatformInfo() {
		String			osName, osArch, osVersion, vendor, APIVersion, interpreterVersion;
		VersionNumber	versNum;

		try {
			osName = System.getProperty( "os.name" ).toLowerCase();
			osArch = System.getProperty( "os.arch" ).toLowerCase();
			osVersion = System.getProperty( "os.version" ).toLowerCase();
			vendor = System.getProperty( "java.vendor" ).toLowerCase();
			APIVersion = System.getProperty( "java.class.version" ).toLowerCase();
			interpreterVersion = System.getProperty( "java.version" ).toLowerCase();
			versNum = new VersionNumber( interpreterVersion );

			Trace.println( "osName=" + osName );
			Trace.println( "osArch=" + osArch );
			Trace.println( "osVersion=" + osVersion );
			Trace.println( "vendor=" + vendor );
			Trace.println( "APIVersion=" + APIVersion );
			Trace.println( "interpreterVersion=" + interpreterVersion );
			Trace.println( "versNum=" + versNum );
		}
		catch ( Exception e ) {
			Trace.println( "can't get platform info" + e );
		}
		catch ( Error er ) {
			Trace.println( "can't get platform info" + er );
		}
	}
}


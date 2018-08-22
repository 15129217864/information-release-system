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

import java.io.File;
import com.jconfig.*;

/**
A singleton called by the FileRegistry to create the object to which the FileRegistry delegates all calls.
For this class to be called, its full name (com.jconfig.mac.FileRegistryFactoryMac) must be
listed in the file 'jcfactrz.txt'

<P>
See the 'FileRegistryI.java' class for more information on the dynamic loading features of JConfig.

<P>
If we're running on a PowerMac, tries to create a FileRegistryMRJ object.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class FileRegistryFactoryMac implements FileRegistryFactoryI {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private FileRegistryI		obj = null;
	private boolean				bBeenInited = false;
 	private boolean				bIsPlatformMRJJRI = false,
								bIsPlatformCW113 = false;

	public FileRegistryFactoryMac() {
	}

/**
Returns 'obj', initializing it if necessary.
*/

	public FileRegistryI createFileRegistry( File curDir, int creator ) {
		if ( !bBeenInited )
			initialize( curDir, creator );
		
		return obj;
	}

/**
First, figure out which platform we're running on. Then, try to create a FileRegistryMRJ object.
*/

	private void initialize( File curDir, int creator ) {
		bBeenInited = true;
		
		tryCreatePlatformSpecific( curDir, creator );
	}

/**
Create a FileRegistryMRJ object. Depending on the specific platform/VM, load
the correct native code library.
*/

	private void tryCreatePlatformSpecific( File curDir, int creator ) {
		PlatformInfoMRJ			platformInfo;

		platformInfo = PlatformInfoMRJ.getInstance();

		try {
			if ( platformInfo.isPlatformMRJJRI() )
				obj = new FileRegistryMRJ( platformInfo, FileRegistryMRJ.kMRJJRI, curDir, creator );
			else if ( platformInfo.isPlatformCW113() )
				obj = new FileRegistryMRJ( platformInfo, FileRegistryMRJ.kCW113, curDir, creator );
			else if ( platformInfo.isPlatformMRJOSX() )
				obj = new FileRegistryMRJ( platformInfo, FileRegistryMRJ.kMRJOSX, curDir, creator );
			else
				obj = null;
		}
		catch ( Exception e ) {
			Trace.println( "can't create FileRegistry for " + platformInfo.getPlatformString() + ":" + e );
			obj = null;
		}
		catch ( Error er ) {
			Trace.println( "can't create FileRegistry for " + platformInfo.getPlatformString() + ":" + er );
			obj = null;
		}
	}
}



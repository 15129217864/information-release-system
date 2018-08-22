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

import java.io.File;
import com.jconfig.*;

/**
A singleton called by the FileRegistry to create the object to which the FileRegistry delegates all calls.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class FileRegistryFactoryNix implements FileRegistryFactoryI {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private FileRegistryI		obj = null;
	private PlatformInfoNix		platformInfo = null;
	private boolean				bBeenInited = false;

	public FileRegistryFactoryNix() {
	}

	public FileRegistryI createFileRegistry( File curDir, int creator ) {
		if ( !bBeenInited )
			initialize( curDir, creator );
		
		return obj;
	}

/**
First, figure out which platform we're running on. Then, try to create a FileRegistryNix object.
*/

	private void initialize( File curDir, int creator ) {
		bBeenInited = true;
		
		platformInfo = new PlatformInfoNix();
		tryCreatePlatformSpecific( curDir, creator );
	}

/**
Create a FileRegistryNix object. Depending on the specific platform/VM, load
the correct native code library.
*/

	private void tryCreatePlatformSpecific( File curDir, int creator ) {
		try {
			if ( platformInfo.isPlatformLinuxX86() )
				obj = new FileRegistryNix( platformInfo, FileRegistryNix.kLinuxX86Library, curDir, creator );
			else if ( platformInfo.isPlatformNix() )
				obj = new FileRegistryNix( platformInfo, FileRegistryNix.kNoLibrary, curDir, creator );
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



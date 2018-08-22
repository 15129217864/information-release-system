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

import java.io.File;
import com.jconfig.*;

/**
When the FileRegistry.initialize() method is called. the FileRegistry calls the FileRegistryFactory to create a
platform-specific instance of FileRegistryI to which the FileRegistry will delegate all calls.

<P>
The FileRegistryFactory will read 'jcfactrz.txt', and, if this class is listed in that file, it will create an
instance of this class, and call its createFileRegistry() method.

<P>
If we're running on Windows on a supported VM, the createFileRegistry() method will return an FileRegistryMSVM
object to which the FileRegistry will delegate all calls.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class FileRegistryFactoryWin implements FileRegistryFactoryI {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private FileRegistryI		obj = null;
	private PlatformInfoMSVM	platformInfo = null;
	private boolean				bBeenInited = false;

	public FileRegistryFactoryWin() {
	}

	public FileRegistryI createFileRegistry( File curDir, int creator ) {
		if ( !bBeenInited )
			initialize( curDir, creator );
		
		return obj;
	}

/**
First, figure out which platform we're running on. Then, try to create a FileRegistryMSVM object.
*/

	private void initialize( File curDir, int creator ) {
		bBeenInited = true;
		
		platformInfo = new PlatformInfoMSVM();
		tryCreatePlatformSpecific( curDir, creator );
	}

/**
Create a FileRegistryMSVM object. Depending on the specific platform/VM, load
the correct native code library.
*/

	private void tryCreatePlatformSpecific( File curDir, int creator ) {
		try {
			if ( platformInfo.isPlatformMSVM15() )
				obj = new FileRegistryMSVM( platformInfo, FileRegistryMSVM.kMSVM1, curDir, creator );
			else if ( platformInfo.isPlatformMSVM20() )
				obj = new FileRegistryMSVM( platformInfo, FileRegistryMSVM.kMSVM2, curDir, creator );
			else if ( platformInfo.isPlatformSun11() )
				obj = new FileRegistryMSVM( platformInfo, FileRegistryMSVM.kSun11, curDir, creator );
			else if ( platformInfo.isPlatformMSVM15W() )
				obj = new FileRegistryMSVM( platformInfo, FileRegistryMSVM.kMSVM1W, curDir, creator );
			else if ( platformInfo.isPlatformMSVM20W() )
				obj = new FileRegistryMSVM( platformInfo, FileRegistryMSVM.kMSVM2W, curDir, creator );
			else if ( platformInfo.isPlatformSun11W() )
				obj = new FileRegistryMSVM( platformInfo, FileRegistryMSVM.kSun11W, curDir, creator );
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



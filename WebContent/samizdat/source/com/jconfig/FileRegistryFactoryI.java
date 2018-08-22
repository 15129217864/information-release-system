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
Represents an object used to create objects implementing the FileRegistryI interface.

<P>
On initialization, the FileRegistry singleton reads the file 'jcfactrz.txt', which contains a list of
classes implementing this interface. For example:

<P>
com.jconfig.win.FileRegistryFactoryWin
<BR>
com.jconfig.mac.FileRegistryFactoryMac
<BR>
com.jconfig.nix.FileRegistryFactoryNix

<P>
Each class listed in this file is created and given a chance to create a FileRegistryI object.

<BR>
If the object returns a FileRegistryI object, all calls to the FileRegistry will be delegated
to that object.

<BR>
If the object can't create an object, (for instance if it's running on an unsupported platform),
it should return null. 

<BR>
If all of the FileRegistryFactoryI objects specified in 'jcfactrz.txt' return null, FileRegistry
creates a FileRegistryPlain object, and delegates all calls to it. 

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface FileRegistryFactoryI {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	FileRegistryI createFileRegistry( File curDir, int creator );
}

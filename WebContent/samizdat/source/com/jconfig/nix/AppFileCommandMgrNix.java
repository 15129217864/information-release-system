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
import java.io.PrintStream;
import java.util.Date;
import java.util.Vector;

/**
Used by AppFileNix to manage its commands. Currently, most methods do nothing, or return null.

This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppFileCommandMgrNix {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private String				filePath;
	private int					numCommands;
	private boolean				bAlreadyRequestedFileTypes;
	private AppCommandWatcher	acWatcher;
	private AppFileNix			owner;

	AppFileCommandMgrNix( String path, String name, AppFileNix af, AppCommandWatcher watch ) {
		filePath = path;
		owner = af;
		acWatcher = watch;
		bAlreadyRequestedFileTypes = false;
		numCommands = 0;
		createCommands();
		createFileTypes();
	}
	
	private AppCommand createDefaultCommand() {
		return null;
	}

	private void createCommands() {
	}

	private void createFileTypes() {
	}

	FileType[] getFileTypes( int maxToReturn ) {
		return null;
	}

	AppProcess performCommand( AppCommand command, int flags ) {
		return null;
	}

	AppCommand getCommand( String commandName ) {
		return null;
	}
	
	AppCommand[] getAllCommands() {
		return null;
	}
 
	public void dumpInfo( PrintStream ps, String indent ) {
		int			i;

		ps.println( indent + "  no commands" );
	}
}




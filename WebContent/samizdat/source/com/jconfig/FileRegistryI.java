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

import java.io.FileNotFoundException;


/**
Interface for objects which implement the methods of the FileRegistry. See that class for
information on each of these methods. Objects implementing this interface may be created
by the FileRegistryFactory for use by the FileRegistry.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface FileRegistryI {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public static final int		IN_ONLY = 0x10;
	public static final int		OUT_ONLY = 0x20;
	public static final int		INANDOUT_ONLY = 0x30;

	public static final int		APP_MOVE_TOFRONT = AppProcess.APP_MOVE_TOFRONT;
	public static final int		APP_MOVE_TOBACK = AppProcess.APP_MOVE_TOBACK;
	public static final int		APP_MOVE_MINIMIZE = AppProcess.APP_MOVE_MINIMIZE;
	public static final int		APP_MOVE_MAXIMIZE = AppProcess.APP_MOVE_MAXIMIZE;
	
	public static final int		ALIAS_NO_UI = 1;
	public static final int		ALIAS_UI = 2;



	public static final int		GETAPPS_SEARCH1 = 1;

	public static final int		GETAPPS_SEARCH2 = 2;

	public static final int		GETAPPS_SEARCH3 = 3;


	PlatformInfoI getPlatformInfo();

	FileExtension[] findExtensions( FinderInfo finfo, int maxToReturn );

	FinderInfo[] findFinderInfo( FileExtension ext, int maxToReturn );

	AppFile[] getApps( String appName, int maxToReturn, int flags );

	AppFile[] getApps( FinderInfo finfo, int maxToReturn, int flags );

	AppFile[] getApps( FileExtension ext, int maxToReturn, int flags );

	int iterate( ConfigEntryVisitor fdv );

	int launchURL( String url, int flags, String preferredBrowsers[] );

	DiskVolume[] getVolumes();

	AppFile createAppFile( File fl )
		throws FileNotFoundException, DiskFileException;

	DiskObject createDiskObject( File fl, int flags )
		throws FileNotFoundException, DiskFileException;

	int createAlias( DiskObject target, File newAlias, int creator, int flags )
		throws FileNotFoundException, DiskFileException;

	DiskObject resolveAlias( DiskAlias da, int flags )
		throws FileNotFoundException, DiskFileException;

	FileType getFileType( File fl )
		throws FileNotFoundException, DiskFileException;

	int getDirection();

	void setDirection( int dir );

	Monitor[] getMonitors();

	Monitor getMainMonitor();

	AppProcess[] getProcesses( int maxToReturn, int flags );

 	FileSystem[] getFileSystems( int maxToReturn, int flags );

	void setFileUtils( FileUtilsI fi );
	FileUtilsI getFileUtils();
}


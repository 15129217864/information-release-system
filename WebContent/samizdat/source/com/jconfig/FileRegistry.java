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
This singleton returns the following information:
<BR>
	- the FileExtension's corresponding to a given FinderInfo
<BR>
	- the FinderInfo's corresponding to a given FileExtension
<BR>
	- applications whose name contain a given string
<BR>
	- applications associated with a given FileExtension
<BR>
	- applications associated with a given FinderInfo
<BR>
You can also use this object to:
<BR>
   - launch a URL or file in a Web browser
<BR>
   - create and resolve aliases
<BR>
   - create AppFile's and DiskObject's
<BR>

   - enumerate the currently mounted volumes

<BR>

   - enumerate the user's active video monitors

<BR>
   - iterate over each record used to build the mappings database which maps
FileExtension's to FinderInfo's and both of those to applications.
<BR>

Note: Before using any method of this class, you must call the initialize method, as illustrated
in the sample applications.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class FileRegistry {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private static FileRegistryI		delegate = null;

/**
See the getDirection() method.
*/

	public static final int		IN_ONLY = FileRegistryI.IN_ONLY;

/**
See the getDirection() method.
*/

	public static final int		OUT_ONLY = FileRegistryI.OUT_ONLY;

/**
See the getDirection() method.
*/

	public static final int		INANDOUT_ONLY = FileRegistryI.INANDOUT_ONLY;

/**
See the launchURL method.
*/

	public static final int APP_MOVE_TOFRONT = FileRegistryI.APP_MOVE_TOFRONT;

/**
See the launchURL method.
*/

	public static final int APP_MOVE_TOBACK = FileRegistryI.APP_MOVE_TOBACK;

/**
See the launchURL method.
*/

	public static final int APP_MOVE_MINIMIZE = FileRegistryI.APP_MOVE_MINIMIZE;

/**
See the launchURL method.
*/

	public static final int APP_MOVE_MAXIMIZE = FileRegistryI.APP_MOVE_MAXIMIZE;

/**
See the createDiskObject and resolveAlias methods.
*/

	public static final int		ALIAS_NO_UI = FileRegistryI.ALIAS_NO_UI;

/**
See the createDiskObject and resolveAlias methods.
*/

	public static final int		ALIAS_UI = FileRegistryI.ALIAS_UI;

/**
See the getApps() methods.
*/

	public static final int		GETAPPS_SEARCH1 = FileRegistryI.GETAPPS_SEARCH1;

/**
See the getApps() methods.
*/

	public static final int		GETAPPS_SEARCH2 = FileRegistryI.GETAPPS_SEARCH2;

/**
See the getApps() methods.
*/

	public static final int		GETAPPS_SEARCH3 = FileRegistryI.GETAPPS_SEARCH3;

/**
See the getProcesses() methods.
*/

	public static final int		GETPROCESSES_IGNORE_SYSTEM = 1;

/**
See the getProcesses() methods.
*/

	public static final int		GETPROCESSES_IGNORE_HIDDEN = 2;

	private FileRegistry() {
	}

/**
This method must be called before using any other method of this class.

@param curDir this must be the directory which contains the files 'jconfig.cfg' and 'jcfactrz.txt', both
of which are supplied with this distribution.

<BR>
<B>
IMPORTANT:
</B>
<BR>
If 'curDir' does not contain both 'jconfig.cfg' and 'jcfactrz.txt',
JConfig will not be properly initialized, and only a limited set
of functionality will be provided. In this case, JConfig will output a message saying that FileRegistryPlain
has been loaded. If this occurs, check that the 'curDir' directory contains both these files.

This directory must be writable; temporary files might be created in this directory.

@param creator the creator value of the application using this package.
*/

	public static void initialize( File curDir, int creator ) {
		FileRegistryFactory		factory;

		System.out.println( "JConfig EVALUATION VERSION 2.2.0" );
		System.out.println( "Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved." );
		System.out.println( "Visit us at www.tolstoy.com" );
		System.out.println( "" );

		factory = new FileRegistryFactory();

		try {
			delegate = factory.createFileRegistry( curDir, creator );
		}
		catch ( Exception e ) {
			Trace.println( "FileRegistry.initialize, can't make delegate " + e );
			delegate = null;
		}
		catch ( Error ee ) {
			Trace.println( "FileRegistry.initialize, can't make delegate " + ee );
			delegate = null;
		}
	}

/**
Sets the object to which all other calls are delegated.
*/

	public static void setDelegate( FileRegistryI fri ) {
		delegate = fri;
	}

/**
Returns the object to which all other calls are delegated.
*/

	public static FileRegistryI getDelegate() {
		return delegate;
	}

/**
Returns whether this class has been initialized. If initialization using the previous method
failed, false is returned.
*/

	public static boolean isInited() {
		if ( delegate == null )
			return false;
		else
			return true;
	}

/**
Indicates which platform we're running on.
@return a PlatformInfoI object containing information on the current platform/VM
*/

	public static PlatformInfoI getPlatformInfo() {
		if ( delegate == null )
			return null;
		else
			return delegate.getPlatformInfo();
	}

/**
Return an array containing FileExtension objects which correspond to the given FinderInfo
object. The 'maxToReturn' argument is used as a hint only; the actual array size may be greater
or less than this. If no extensions were found, null is returned.
*/

	public static FileExtension[] findExtensions( FinderInfo finfo, int maxToReturn ) {
		if ( delegate == null )
			return null;
		else
			return delegate.findExtensions( finfo, maxToReturn );
	}
	
/**
Return an array containing FinderInfo objects which correspond to the given FileExtension
object. The 'maxToReturn' argument is used as a hint only; the actual array size may be greater
or less than this. If no FinderInfo objects were found, null is returned.
*/

	public static FinderInfo[] findFinderInfo( FileExtension ext, int maxToReturn ) {
		if ( delegate == null )
			return null;
		else
			return delegate.findFinderInfo( ext, maxToReturn );
	}

/**
Returns an array of applications whose name contains the string 'appName'. Case is ignored;
whether the argument matches a whole word or not is ignored. If no applications are found,
null is returned.

@param appName the string to search for.
@param maxToReturn indicates the maximum number of AppFiles to return. NOTE: this is used as
a hint only; the actual array size may be greater or less than this.
@param flags the lower two bits of this int indicate the level of searching which should be
performed. 0 indicates only standard searching; the values 'GETAPPS_SEARCH1',
'GETAPPS_SEARCH2', and 'GETAPPS_SEARCH3' indicate increasing levels of searching
should be performed. The remaining bits of this int are reserved, and should be set to zero.
*/

	public static AppFile[] getApps( String appName, int maxToReturn, int flags ) {
		if ( delegate == null )
			return null;
		else
			return delegate.getApps( appName, maxToReturn, flags );
	}
	
/**
Returns an array of applications which are associated with the given FinderInfo object.

See the preceding method for details on the arguments.
*/

	public static AppFile[] getApps( FinderInfo finfo, int maxToReturn, int flags ) {
		if ( delegate == null )
			return null;
		else
			return delegate.getApps( finfo, maxToReturn, flags );
	}
	
/**
Returns an array of applications which are associated with the given FileExtension object.
See the preceding method for details on the arguments.

*/

	public static AppFile[] getApps( FileExtension ext, int maxToReturn, int flags ) {
		if ( delegate == null )
			return null;
		else
			return delegate.getApps( ext, maxToReturn, flags );
	}

/**
For each entry in the mappings database, the 'visit()' method of the 'cev' argument is called.
Returns 0 if no error occured.
*/

	public static int iterate( ConfigEntryVisitor cev ) {
		if ( delegate == null )
			return -1;
		else
			return delegate.iterate( cev );
	}

/**
Launch the indicated URL. See the Internet Config documentation for more information.
Returns 0 if no error occured. 'url' must be a fully qualified URL in quoted-printable form.
@param flags one of the following values: 0, APP_MOVE_TOFRONT, APP_MOVE_TOBACK, APP_MOVE_MINIMIZE, or APP_MOVE_MAXIMIZE

@param preferredBrowsers a list of the browsers which should be tried to use to open the

URL, in order of preference.

<BR>

On Mac, each String should be exactly four characters long, and represents a creator code, i.e.,

{ "MSIE", "MOS!" }. This argument will be ignored if Internet Config is installed and properly

configured, because Internet Config is given first chance to launch the URL in the user-specified

browser.

<BR>

On Windows, each String represents the name of a DDE server which will be searched for, i.e.,

{ "IEXPLORER", "NETSCAPE", "NSShell" }. This argument will be ignored if one of the indicated servers is not

running ( i.e., with the previous example, if both IE and Netscape aren't running, this argument

will be ignored. )

<BR>

This argument may be null.
*/

	public static int launchURL( String url, int flags, String preferredBrowsers[] ) {
		if ( delegate == null )
			return -1;
		else
			return delegate.launchURL( url, flags, preferredBrowsers );
	}

/**
Returns an array containing the current disk volumes. Returns null if an error occurs.
*/

	public static DiskVolume[] getVolumes() {
		if ( delegate == null )
			return null;
		else
			return delegate.getVolumes();
	}

/**
Returns an AppFile created from a disk file. The file must exist, and be an application.
@param fl the file from which to create the object.
*/

	public static AppFile createAppFile( File fl ) throws FileNotFoundException, DiskFileException {
		if ( delegate == null )
			return null;
		else
			return delegate.createAppFile( fl );
	}

/**
Returns a DiskObject created from a java.io.File object. The returned DiskObject may
represent a file, a directory, a drive, or an alias.
@param fl the file from which to create the object.
@param flags if this is 0, aliases will not be resolved. Otherwise, set this to ALIAS_UI
if interaction with the user is permissible, or to ALIAS_NO_UI if interaction with the
user is not permissible.
*/

	public static DiskObject createDiskObject( File fl, int flags )
	throws FileNotFoundException, DiskFileException {
		if ( delegate == null )
			return null;
		else
			return delegate.createDiskObject( fl, flags );
	}

/**
Creates an alias. Returns zero if no error occured, non-zero otherwise. Note that both files
must already exist; the newAlias argument will be overwritten with the new alias.
@param target the target to which the alias will point
@param newAlias the new alias
@param creator reserved; set to zero
@param flags reserved; set to zero
*/

	public static int createAlias( DiskObject target, File newAlias, int creator, int flags )
	throws FileNotFoundException, DiskFileException {
		if ( delegate == null )
			return -1;
		else
			return delegate.createAlias( target, newAlias, creator, flags );
	}

/**
Returns a DiskObject created from an alias. The returned DiskObject may be represent a file,
a directory, or a drive.
@param da the alias from which to create the object.
@param flags must be either ALIAS_UI if interaction with the user is permissible,
or ALIAS_NO_UI if interaction with the user is not permissible.
*/

	public static DiskObject resolveAlias( DiskAlias da, int flags )
	throws FileNotFoundException, DiskFileException {
		if ( delegate == null )
			return null;
		else
			return delegate.resolveAlias( da, flags );
	}

/**
Returns a FileType object representing the file type of a disk file. The file must exist.
@param fl the file
*/

	public static FileType getFileType( File fl )
	throws FileNotFoundException, DiskFileException {
		if ( delegate == null )
			return null;
		else
			return delegate.getFileType( fl );
	}

/**
Returns the direction flags. This is one of four values: 0, IN_ONLY, OUT_ONLY, and INANDOUT_ONLY; these correspond
to the check boxes in 'inbound only' and 'outbound only' in the dialog used to edit the Internet Config file
mapping database. The default value is 0: no flags are set.
*/

	public static int getDirection() {
		if ( delegate == null )
			return 0;
		else
			return delegate.getDirection();
	}

/**
Sets the direction flags. See the getDirection() method.
*/

	public static void setDirection( int dir ) {
		if ( delegate != null )
			delegate.setDirection( dir );
	}


/**
Returns a list of all the video monitors which are currently active.
*/

	public static Monitor[] getMonitors() {
		Monitor			retArray[] = null;

		if ( delegate != null )
			retArray = delegate.getMonitors();

		if ( retArray == null ) {
			retArray = new Monitor[ 1 ];
			retArray[ 0 ] = new MonitorPlain();
		}

		return retArray;
	}

/**
Returns the main video monitor.
*/

	public static Monitor getMainMonitor() {
		Monitor		retMon = null;

		if ( delegate != null )
			retMon = delegate.getMainMonitor();

		if ( retMon == null )		
			retMon = new MonitorPlain();

		return retMon;
	}

/**
Returns an array of all the currently running processes, whether created using JConfig
or not.
Returns null if an error occurs.
@param maxToReturn the maximum number of processes to return. This is a hint only.
@param flags either 0, or GETPROCESSES_IGNORE_SYSTEM	and/or GETPROCESSES_IGNORE_HIDDEN
*/

	public static AppProcess[] getProcesses( int maxToReturn, int flags ) {
		if ( delegate == null )
			return null;
		else
			return delegate.getProcesses( maxToReturn, flags );
	}

/**
Returns an array of all the currently mounted FileSystems. If this information cannot be determined,
returns null.
@param maxToReturn the maximum number of file system to return. The actual number returned may be
more than this amount.
@param flags reserved; set to zero
*/

	public static FileSystem[] getFileSystems( int maxToReturn, int flags ) {
		if ( delegate == null )
			return null;
		else
			return delegate.getFileSystems( maxToReturn, flags );
	}
}


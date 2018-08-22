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
import java.util.Date;
import java.io.File;

/**
This is a preliminary interface derived from AppUtilsMSVM.

<P>
It is implemented by AppUtilsNixLinux (for Linux) and AppUtilsNixPlain (for all other Unix systems)

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

interface AppUtilsNixI {
	final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	final int		kUnused = -1;

/**
The stat() and lstat() methods are passed
an array of java ints, into which the native code puts the modification,
creation and access dates. Each date takes 6 ints: year, month, day,
hour, minute, and second. These define the total length of the jint array,
and the offset of each date.
*/

	final int		kDateBundleArrayLen = 18;
	final int		DATE_MOD_OFFSET = 0;
	final int		DATE_CRE_OFFSET = 6;
	final int		DATE_ACC_OFFSET = 12;

/**
The statFS() method is passed
an array of ints, into which the native code information on a file system.
These constants define the total length of the array, and the offset of each item.
*/

	final int		kStatFSOffs_type = 0;								//	nStatFS
	final int		kStatFSOffs_bsize = 1;
	final int		kStatFSOffs_blocks = 2;
	final int		kStatFSOffs_bfree = 3;
	final int		kStatFSOffs_bavail = 4;
	final int		kStatFSOffs_files = 5;
	final int		kStatFSOffs_ffree = 6;
	final int		kStatFSOffs_fsid0 = 7;
	final int		kStatFSOffs_fsid1 = 8;
	final int		kStatFSRetArrayLen = ( kStatFSOffs_fsid1 + 1 );

/**
The getMntEnt() method is passed
an array of Strings, into which the native code information on a file system.
These constants define the total length of the array, and the offset of each item.
*/

	final int		kGetMntEntOffs_fsname = 0;
	final int		kGetMntEntOffs_dir = 1;
	final int		kGetMntEntOffs_type = 2;
	final int		kGetMntEntOffs_opts = 3;
	final int		kGetMntEntRetArrayLen = ( kGetMntEntOffs_opts + 1 );

/**
The stat() method is passed
an array of ints, into which the native code information on a file.
These constants define the total length of the array, and the offset of each item.
*/

	final int		kStatOffs_dev = 0;
	final int		kStatOffs_ino = 1;
	final int		kStatOffs_mode = 2;
	final int		kStatOffs_nlink = 3;
	final int		kStatOffs_uid = 4;
	final int		kStatOffs_gid = 5;
	final int		kStatOffs_rdev = 6;
	final int		kStatOffs_size = 7;
	final int		kStatOffs_blksize = 8;
	final int		kStatOffs_blocks = 9;
	final int		kStatRetArrayLen = ( kStatOffs_blocks + 1 );

/**
Used in retrieving monitor information; see	the 'getAllMonitorInfo' method.
*/

	final int		kMonitorInfoNumInts = 20;

/**
See the 'resolveLinkFile' method.
*/

	final int		kResolveLinkFileNoUI = 1;

/**
See the 'resolveLinkFile' method.
*/

	final int		kResolveLinkFileUI = 2;

	final int		kStandardVolumeAttrsMask = 0;

	final int		kStandardFileAttrsMask = ( DiskFile.FILE_DIR | DiskFile.FILE_HIDDEN );

/**
Calls the Unix getmntent() routine to get a list of mounted file systems.

@param retQuads on return, 4 strings about each file system will be stored in this
		array, corresponding to the following fields of the 'mntent' struct: mnt_fsname, mnt_dir, mnt_type, and mnt_opts

@param maxToReturn the maximum number to return. the length of retQuads should be 4 times this value

@param numReturned the number of file systems on which information is returned.
		The number of strings in 'retQuads' will be 4 times this number
*/

	int getMntEnt( String retQuads[], int maxToReturn, int numReturned[] );

/**
Calls the Unix statfs() routine to return information on the file system containing the given file
@param fileName the full path of the file
@param retInts on exit, stats on the file system will be placed at offsets defined by the kStatFSOffs_XXX constants.
This array must have at least kStatFSRetArrayLen elements.
*/

	int statFS( String fileName, int retInts[] );

/**
Calls the Unix stat() routine to return information on the given file
@param fileName the full path of the file
@param retArray on exit, stats on the file will be placed at offsets defined by the kStatOffs_XXX constants.
This array must have at least kStatRetArrayLen elements.
@param datesArray on exit, stats on the file system will be placed at offsets defined by the kXXXDateOffset constants.
This array must have at least kDateBundleArrayLen elements.
*/

	int stat( String fileName, int retArray[], int datesArray[] );

/**
Calls the Unix lstat() routine to return information on the given file
@param fileName the full path of the file
@param retArray on exit, stats on the file will be placed at offsets defined by the kStatOffs_XXX constants.
This array must have at least kStatRetArrayLen elements.
@param datesArray on exit, stats on the file system will be placed at offsets defined by the kXXXDateOffset constants.
This array must have at least kDateBundleArrayLen elements.
*/

	int lstat( String fileName, int retArray[], int datesArray[] );

/**
Calls the Unix readlink() routine to resolve a symbolic link.
@param linkFilePath the full path (which may be a symbolic link) to resolve
@param retPath on return, contains the full path of the resolved file
*/

	int readLink( String linkFilePath, String retPath[] );

/**
Gets the icon for a file.

<BR><B>WANC</B><BR>

@param fullPath full path of the file
@param bIsDir is this a directory?
@param whichIcon either IconBundle.ICON_LARGE or IconBundle.ICON_SMALL
@param w the width of the icon
@param h the height of the icon
@param xform any transformation to be applied to the icon
@param align any alignment to be applied to the icon
@param pData an array of ints which will hold the Java format ARGB data for the icon
This must have at least w * h elements
*/

	int getFileIcon( String fullPath, boolean bIsDir, int whichIcon, int w, int h, int xform, int align, int pData[] );

/**
Gets the icon for files ending in the given extension.

<BR><B>WANC</B><BR>

@param ext the extension
@param whichIcon either IconBundle.ICON_LARGE or IconBundle.ICON_SMALL
@param w the width of the icon
@param h the height of the icon
@param xform any transformation to be applied to the icon
@param align any alignment to tbe applied to the icon
@param pData an array of ints which will hold the Java format ARGB data for the icon
This must have at least w * h elements
*/

	int getExtIcon( String ext, int whichIcon, int w, int h, int xform, int align, int pData[] );

/**
Gets the icon for a volume

<BR><B>WANC</B><BR>

@param driveName the voluem
@param whichIcon either IconBundle.ICON_LARGE or IconBundle.ICON_SMALL
@param w the width of the icon
@param h the height of the icon
@param xform any transformation to be applied to the icon
@param align any alignment to tbe applied to the icon
@param pData an array of ints which will hold the Java format ARGB data for the icon
This must have at least w * h elements
*/

	int getVolumeIcon( String driveName, int whichIcon, int w, int h, int xform, int align, int pData[] );

/**
Create an alias to a volume

<BR><B>WANC</B><BR>

@param driveName the target
@param newAliasPath the full path to the new alias
@flags ignored; set to 0
*/

	int createVolumeAlias( String driveName, String newAliasPath, int flags );

/**
Create an alias to a file

<BR><B>WANC</B><BR>

@param targetPath full path of the target
@param newAliasPath the full path to the new alias
@flags ignored; set to 0
*/

	int createFileAlias( String targetPath, String newAliasPath, int flags );

/**
Is 'fl' a symbolic link?
*/

	boolean isLinkFile( File fl );

/**
Is 'fl' a volume?
*/

	boolean isDrivePath( File fl );

/**
Returns the volume which contains 'fl', or null if that information can't be obtained.
*/

	String pathToDriveName( File fl );

/**
Is 'drivePath' a volume path, i.e., '/'?
*/

	boolean isDriveString( String drivePath );

/**
Retrieve a list of the mounted volumes.

<BR><B>WANC</B><BR>

@param maxToReturn the maximum number of volumes to return
@param numReturned on return, the first element will contain the number of volumes in 'driveNames'
@param driveNames on return, contains an array of Strings representing each drive. Must have
at least 'maxToReturn' elements.
*/

	int getVolumes( int maxToReturn, int numReturned[], String driveNames[] );

/**
Get the volume label
@param driveName the drive name
@param label the drive label will be put at label[ 0 ]
*/

	int getVolumeLabel( String driveName, String label[] );

/**
Get the maximum file name length of a volume.
@param driveName the drive name
@param nameLen the name length will be placed at nameLen[ 0 ]
*/

	int getVolumeMaxFileNameLength( String driveName, int nameLen[] );

/**
Get a platform-dependent number for a volume, such as a reference number.
@param driveName the drive name
@param nameLen the reference number will be placed at refNum[ 0 ]
*/

	int getVolumeReferenceNumber( String driveName, int refNum[] );

/**
Sets the volume label of a drive

<BR><B>WANC</B><BR>

@param driveName the drive name
@param newLabel the new label
*/

	int setVolumeLabel( String driveName, String newLabel );

/**
Gets the display name of a drive. This is the name that would be displayed to the user.

<BR><B>WANC</B><BR>

@param driveName the drive name
@param displayName the display name will be placed at displayName[ 0 ]
*/

	int getDriveDisplayName( String driveName, String displayName[] );

/**
Gets the flags for a volume. See DiskVolume for a description of the flags

<BR><B>WANC</B><BR>

@param driveName the drive name
@param flags the flags will be placed at flags[ 0 ]
*/

	int getVolumeFlags( String driveName, int flags[] );

/**
Gets the read flags for a volume. See DiskVolume for a description of this.

<BR><B>WANC</B><BR>

@param driveName the drive name
@param flags the flags will be placed at flags[ 0 ]
*/

	int getVolumeReadFlagsMask( String driveName, int flags[] );

/**
Resolves a symbolic link.

<BR><B>WANC</B><BR>

@param linkFilePath the full path of the .lnk file
@param retPath the resolved full path will be placed at retPath[ 0 ]
@param flags either kResolveLinkFileNoUI or kResolveLinkFileUI
*/

	int resolveLinkFile( String linkFilePath, String retPath[], int flags );

/**
Gets information on all the user's monitors. 
Info on each monitor will be placed into the 'monitorInfo' int array. Each monitor consumes 'kMonitorInfoNumInts'
elements of this array. The format of each monitor is as described in the 'getMainMonitorInfo' method.

<BR><B>WANC</B><BR>

@param montitorInfo must have at least 'maxToReturn' * kMonitorInfoNumInts elements.
@param maxToReturn the maximum number of monitors to return info on.
@param numReturned the number of monitors returned will be placed at numReturned[ 0 ]
*/

	int getAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] );

/**
Stores information on the user's main monitor into the 'monitorInfo' array.
This array must have at least 'kMonitorInfoNumInts' elements.
The information on the monitor is stored at the 'kOffs???' offsets defined in 'MonitorMSVM.java':

<BR><B>WANC</B><BR>
*/

	int getMainMonitorInfo( int monitorInfo[] );

/**
Gets the type of the given executable.

<BR><B>WANC</B><BR>

@param fullPath the full path of the executable
@param val the type will be placed at val[ 0 ], and will be one of the k???Exe values defined in 'AppFile.java':
'AF_W32', etc.
*/

	int getExecutableType( String fullPath, int val[] );

/**
Gets the attributes of the given file.

<BR><B>WANC</B><BR>

@param fullPath the full path of the file
@param val the attributes will be placed at val[ 0 ], and will be one of the attributes defined in 'DiskFile.java':
'FILE_EXECUTABLE', 'FILE_DIR', etc.
*/

	int getFileAttributes( String fullPath, int val[]  );

/**
Indicates which bits of the value returned from getFileAttributes are valid.
See that method for more information.

<BR><B>WANC</B><BR>

@param fullPath the full path of the file
@param val the mask will be placed at val[ 0 ]
*/

	int getFileAttributesMask( String fullPath, int val[]  );

/**
Launches the given app with a set of arguments.
*/

	int launchApp( String appPath, String verb, String regKey, String commandLine, int retData[], int numArgs, String args[] );

/**
Launches the given URL.
@param url the fully qualified URL
@param flags currently ignored
@param preferredBrowsers currently ignored
*/

	int launchURL( String url, int flags, String preferredBrowsers[] );

/**
Returns the full paths of the apps which match the given name.

@param appName the string to search for.
@param maxToReturn indicates the maximum number of AppFiles to return. NOTE: this is used as
a hint only; the actual array size may be greater or less than this.
@param flags the lower two bits of this int indicate the level of searching which should be
performed. 0 indicates only standard searching; 1, 2, and 3 indicate increasingly full searching
The remaining bits of this int are reserved, and should be set to zero.
*/

	String[] findAppsByName( String appName, int maxToReturn, int flags );

/**
Returns the full paths of the apps which are used to launch files with the given extension
@param ext the extension being searched for: ".txt"
@param maxToReturn the maximum number to return. This is a hint only.
@param flags reserved; set to 0
*/

	String[] findAppsByExtension( String ext, int maxToReturn, int flags );

/**
Indicates if the given process is still running.

<BR><B>WANC</B><BR>
*/

	int verifyNativeAppData( int appData[] );

/**
Quits the given process.

<BR><B>WANC</B><BR>

@param appData identifies the process
@param flags ignored; set to 0
*/

	int quitApp( int appData[], int flags );

/**
Moves the given process.

<BR><B>WANC</B><BR>

@param appData identifies the process
@param selector one of the 'APP_MOVE_TOFRONT', etc. constants defined in AppProcess.java
@param flags ignored; set to 0
*/

	int moveApp( int appData[], int selector, int flags );
}





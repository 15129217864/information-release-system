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

import com.jconfig.ErrCodes;
import java.io.File;

/**
This is the lowest Java layer, and contains the native methods as static private members.
Many of the methods of this file are simply wrappers around the private native methods.
Almost all of these methods return an error code; 0 means success, other values indicate some
form of error.

<P>
The 'initialize' method should be called before calling any other methods of this class.

<P>
The term 'WANC' in the documentation means that that method is just a Wrapper Around Native Code.

<P>
Future directions: make sure this is the lowest layer, by moving methods which have knowledge of higher
layers (e.g., createNewDiskObject) into other files.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppUtilsMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Atrribute returned by findFirstFile/findNextFile.
*/

	static final int		kFindFirstAttrHidden = 0x00000002;

/**
Atrribute returned by findFirstFile/findNextFile.
*/

	static final int		kFindFirstAttrSystem = 0x00000004;

/**
The getFileDate() and getVolumeDate() methods are passed
an array of java ints, into which the native code puts the modification,
creation and access dates. Each date takes 6 ints: year, month, day,
hour, minute, and second. This defines the total length of the jint array.
*/

	static final int		kDateBundleArrayLen = 18;

/**
The offset of the modification date in the array passed to getFileDate() and getVolumeDate().
*/

	static final int		DATE_MOD_OFFSET = 0;

/**
The offset of the creation date in the array passed to getFileDate() and getVolumeDate().
*/

	static final int		DATE_CRE_OFFSET = 6;

/**
The offset of the access date in the array passed to getFileDate() and getVolumeDate().
*/

	static final int		DATE_ACC_OFFSET = 12;

/**
Used in retrieving monitor information; see	the 'getAllMonitorInfo' method.
*/

	static final int		kMonitorInfoNumInts = 20;

/**
See the 'getVolumeCapInfo' method.
*/

	static final int		kVolumeCapInfoLen = 4;

/**
See the 'getVolumeCapInfo' method.
*/

	static final int		kVolumeCapInfoCapacityOffset = 0;

/**
See the 'getVolumeCapInfo' method.
*/

	static final int		kVolumeCapInfoFreeSpaceOffset = 1;

/**
See the 'getVolumeCapInfo' method.
*/

	static final int		kVolumeCapInfoCapacityToUserOffset = 2;

/**
See the 'getVolumeCapInfo' method.
*/

	static final int		kVolumeCapInfoFreeSpaceToUserOffset = 3;

/**
See the 'resolveLinkFile' method.
*/

	static final int		kResolveLinkFileNoUI = 1;

/**
See the 'resolveLinkFile' method.
*/

	static final int		kResolveLinkFileUI = 2;

/**
See the 'launchURL' method.
*/

	private static final String		kStandardBrowsers[] = { "IEXPLORE", "NETSCAPE" };

/**
Used with calls to nativeGetVolumeInformation().
*/

	private static final int		kGVIStringArrayLen = 2;

/**
Used with calls to nativeGetVolumeInformation().
*/

	private static final int		kGVIIntArrayLen = 3;

/**
Used with calls to nativeGetVolumeInformation().
*/

	private static final int		kGVIStringLabelOffset = 0;

/**
Used with calls to nativeGetVolumeInformation().
*/

	private static final int		kGVIStringFileSystemNameOffset = 1;

/**
Used with calls to nativeGetVolumeInformation().
*/

	private static final int		kGVIIntSerialNumberOffset = 0;

/**
Used with calls to nativeGetVolumeInformation().
*/

	private static final int		kGVIIntMaxComponentLengthOffset = 1;

/**
Used with calls to nativeGetVolumeInformation().
*/

	private static final int		kGVIIntFileSystemFlagsOffset = 2;

/**
Used for some unused arguments.
*/

	private static final int		kUnused = -1;

/**
The extension of shortcut files.
*/
	
	private static final String		kLinkExtension = ".lnk";

/**
Holds the full path of a directory used to store temporary files.
*/

	private static String		tempDir = null;

	private AppUtilsMSVM() {
	}

/**
Must be called before calling other methods of this class
@param temp a writable directory in which temporary files will be created.
*/

	static void initialize( File temp ) {
		tempDir = adjustBadMSVM1Path( temp.getPath() );
		if ( !tempDir.endsWith( "\\" ) )
			tempDir = tempDir + "\\";
	}

/**
Works around a problem with the IE3.0 VM
*/

	static String adjustBadMSVM1ParentPath( String ss ) {
		char		cc1, cc2;

		if ( ss.length() == 2 ) {
			cc1 = ( ss.toLowerCase() ).charAt( 0 );
			cc2 = ss.charAt( 1 );
			if ( cc2 == ':' && cc1 >= 'a' && cc1 <= 'z' )
				return ss + '\\';
		}

		return ss;
	}

/**
Works around a problem with the IE3.0 VM
*/

	static String adjustBadMSVM1Path( String ss ) {
		String				retStr;
		StringBuffer		sbIn, sbOut;
		int					len, i, outPos;
		char				cc;

		len = ss.length();
		if ( len < 2 )
			return ss;

		sbIn = new StringBuffer( ss );
		sbOut = new StringBuffer( ss );
		i = 0;
		outPos = 0;
		while ( i < len ) {
			cc = sbIn.charAt( i );
			sbOut.setCharAt( outPos, cc );
			if ( cc == '\\' && ( i < len - 1 ) && sbIn.charAt( i + 1 ) == '\\' )
				i += 2;
			else
				i++;
			outPos++;
		}

		sbOut.setLength( outPos );

		retStr = new String( sbOut );

		return retStr;
	}

/**
Is 'fl' a shortcut?
*/
	static boolean isLinkFile( File fl ) {
		boolean			bRet;

		try {
			bRet = ( ( fl.getPath() ).toLowerCase() ).endsWith( kLinkExtension );
		}
		catch ( Exception e ) {
			bRet = false;
		}

		return bRet;
	}

/**
Is 'fl' a volume?
*/

	static boolean isDrivePath( File fl ) {
		String			drivePath;

		drivePath = fl.getPath();

		if ( drivePath.length() != 3 )
			return false;

		return isDriveString( drivePath );
	}

/**
Returns the volume which contains 'fl', or null if that information can't be obtained.
*/

	static String pathToDriveName( File fl ) {
		String			drivePath;

		drivePath = fl.getPath();

		if ( drivePath.length() < 3 )
			return null;

		drivePath = drivePath.substring( 0, 3 );
		if ( !isDriveString( drivePath ) )
			return null;

		return drivePath;
	}

/**
Is 'drivePath' a volume path, i.e., 'c:\'?
*/

	private static boolean isDriveString( String drivePath ) {
		char			c0, c1, c2;

		c0 = ( drivePath.toLowerCase() ).charAt( 0 );
		c1 = drivePath.charAt( 1 );
		c2 = drivePath.charAt( 2 );

		if ( ( c0 >= 'a' && c0 <= 'z' ) && c1 == ':' && c2 == '\\' )
			return true;

		return false;
	}

/**
Gets the icon for a file.

<BR><B>WANC</B><BR>

@param fullPath full path of the file
@param whichIcon either IconBundle.ICON_LARGE or IconBundle.ICON_SMALL
@param w the width of the icon
@param h the height of the icon
@param xform any transformation to be applied to the icon
@param align any alignment to be applied to the icon
@param pData an array of ints which will hold the Java format ARGB data for the icon
This must have at least w * h elements
*/

	static int getFileIcon( String fullPath, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		return nativeGetFileIcon( fullPath, whichIcon, w, h, xform, align, pData );
	}

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

	static int getExtIcon( String ext, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		return nativeGetExtIcon( ext, whichIcon, w, h, xform, align, pData );
	}

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

	static int getVolumeIcon( String driveName, int whichIcon, int w, int h, int xform, int align, int pData[] ) {
		return nativeGetVolumeIcon( driveName, whichIcon, w, h, xform, align, pData );
	}

/**
Create an alias to a volume

<BR><B>WANC</B><BR>

@param driveName the target
@param newAliasPath the full path to the new alias
@flags ignored; set to 0
*/

	static int createVolumeAlias( String driveName, String newAliasPath, int flags ) {
		return nativeCreateVolumeAlias( driveName, newAliasPath, flags );
	}

/**
Create an alias to a file

<BR><B>WANC</B><BR>

@param targetPath full path of the target
@param newAliasPath the full path to the new alias
@flags ignored; set to 0
*/

	static int createFileAlias( String targetPath, String newAliasPath, int flags ) {
		return nativeCreateFileAlias( targetPath, newAliasPath, flags );
	}

/**
Retrieves version information for a file.

<BR><B>WANC</B><BR>

@param fileName the full path of the file
@return 0 if the file has no version information, otherwise a handle which is passed
to 'vipVerQueryValue' and 'vipGetFileVersionInfoEnd'
*/

	static int vipGetFileVersionInfoStart( String fileName ) {
		return nativeVIPGetFileVersionInfoStart( fileName );
	}


/**
Call this after calling 'vipGetFileVersionInfoStart' to get specific version information.

<BR><B>WANC</B><BR>

@param versionH the value returned from 'vipGetFileVersionInfoStart'
@param key the name of the version information you wish to retrieve, e.g. "CompanyName"
@param value the value of that key will be returned at value[ 0 ]
*/

	static int vipVerQueryValue( int versionH, String key, String value[] ) {
		return nativeVIPVerQueryValue( versionH, key, value );
	}

/**
Frees the handle returned from 'vipGetFileVersionInfoStart'.

<BR><B>WANC</B><BR>

*/

	static int vipGetFileVersionInfoEnd( int versionH ) {
		return nativeVIPGetFileVersionInfoEnd( versionH );
	}

/**
Retrieve a list of the mounted volumes.

<BR><B>WANC</B><BR>

@param maxToReturn the maximum number of volumes to return
@param numReturned on return, the first element will contain the number of volumes in 'driveNames'
@param driveNames on return, contains an array of Strings representing each drive. Must have
at least 'maxToReturn' elements.
*/

	static int getVolumes( int maxToReturn, int numReturned[], String driveNames[] ) {
		return nativeGetVolumes( maxToReturn, numReturned, driveNames );
	}

/**
Uses WinAPI 'GetVolumeInformation()' to get the volume label
@param driveName the drive name
@param label the drive label will be put at label[ 0 ]
*/

	static int getVolumeLabel( String driveName, String label[] ) {
		String			gviStrings[];
		int				gviInts[], theErr;

		gviStrings = new String[ kGVIStringArrayLen ];
		gviInts = new int[ kGVIIntArrayLen ];

		theErr = nativeGetVolumeInformation( driveName, gviStrings, gviInts );

		label[ 0 ] = gviStrings[ kGVIStringLabelOffset ];

		return theErr;
	}

/**
Uses WinAPI 'GetVolumeInformation()' to get the maximum file name length of a volume.
@param driveName the drive name
@param nameLen the name length will be placed at nameLen[ 0 ]
*/

	static int getVolumeMaxFileNameLength( String driveName, int nameLen[] ) {
		String			gviStrings[];
		int				gviInts[], theErr;

		gviStrings = new String[ kGVIStringArrayLen ];
		gviInts = new int[ kGVIIntArrayLen ];

		theErr = nativeGetVolumeInformation( driveName, gviStrings, gviInts );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return gviInts[ kGVIIntMaxComponentLengthOffset ];
	}

/**
Uses WinAPI 'GetVolumeInformation()' to get the reference number of a volume.
@param driveName the drive name
@param nameLen the reference number will be placed at refNum[ 0 ]
*/

	static int getVolumeReferenceNumber( String driveName, int refNum[] ) {
		String			gviStrings[];
		int				gviInts[], theErr;

		gviStrings = new String[ kGVIStringArrayLen ];
		gviInts = new int[ kGVIIntArrayLen ];

		theErr = nativeGetVolumeInformation( driveName, gviStrings, gviInts );

		if ( theErr == ErrCodes.ERROR_NONE )
			refNum[ 0 ] = gviInts[ kGVIIntSerialNumberOffset ];
		else
			refNum[ 0 ] = 0;

		return theErr;
	}

/**
Gets the free space and max capacity of the given drive.

<BR><B>WANC</B><BR>

@param driveName the drive name.
@param capInfo must have at least kVolumeCapInfoLen elements.
The max capacity will be placed at offset kVolumeCapInfoCapacityOffset,
and the free space will be placed at offset kVolumeCapInfoFreeSpaceOffset.
*/

	static int getVolumeCapInfo( String driveName, long capInfo[] ) {
		return nativeGetVolumeCapInfo( driveName, capInfo );
	}

/**
Sets the volume label of a drive

<BR><B>WANC</B><BR>

@param driveName the drive name
@param newLabel the new label
*/

	static int setVolumeLabel( String driveName, String newLabel ) {
		return nativeSetVolumeLabel( driveName, newLabel );
	}

/**
Gets the display name of a drive. This is the name that would be displayed to the user.

<BR><B>WANC</B><BR>

@param driveName the drive name
@param displayName the display name will be placed at displayName[ 0 ]
*/

	static int getDriveDisplayName( String driveName, String displayName[] ) {
		return nativeGetDriveDisplayName( driveName, displayName );
	}

/**
Gets the flags for a volume. See DiskVolume for a description of the flags

<BR><B>WANC</B><BR>

@param driveName the drive name
@param flags the flags will be placed at flags[ 0 ]
*/

	static int getVolumeFlags( String driveName, int flags[] ) {
		return nativeGetVolumeFlags( driveName, flags );
	}

/**
Gets the read flags for a volume. See DiskVolume for a description of this.

<BR><B>WANC</B><BR>

@param driveName the drive name
@param flags the flags will be placed at flags[ 0 ]
*/

	static int getVolumeReadFlagsMask( String driveName, int flags[] ) {
		return nativeGetVolumeReadFlagsMask( driveName, flags );
	}

/**
Resolves a shortcut (.lnk file.)

<BR><B>WANC</B><BR>

@param linkFilePath the full path of the .lnk file
@param retPath the resolved full path will be placed at retPath[ 0 ]
@param flags either kResolveLinkFileNoUI or kResolveLinkFileUI
*/

	static int resolveLinkFile( String linkFilePath, String retPath[], int flags ) {
		return nativeResolveLinkFile( linkFilePath, retPath, flags );
	}

/**
Gets information on all the user's monitors. 
Info on each monitor will be placed into the 'monitorInfo' int array. Each monitor consumes 'kMonitorInfoNumInts'
elements of this array. The format of each monitor is as described in the 'getMainMonitorInfo' method.

<BR><B>WANC</B><BR>

@param montitorInfo must have at least 'maxToReturn' * kMonitorInfoNumInts elements.
@param maxToReturn the maximum number of monitors to return info on.
@param numReturned the number of monitors returned will be placed at numReturned[ 0 ]
*/

	static int getAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] ) {
		return nativeGetAllMonitorInfo( monitorInfo, maxToReturn, numReturned );
	}

/**
Stores information on the user's main monitor into the 'monitorInfo' array.
This array must have at least 'kMonitorInfoNumInts' elements.
The information on the monitor is stored at the 'kOffs???' offsets defined in 'MonitorMSVM.java':

<BR><B>WANC</B><BR>
*/

	static int getMainMonitorInfo( int monitorInfo[] ) {
		return nativeGetMainMonitorInfo( monitorInfo );
	}

/**
Gets the type of the given executable.

<BR><B>WANC</B><BR>

@param fullPath the full path of the executable
@param val the type will be placed at val[ 0 ], and will be one of the k???Exe values defined in 'AppFile.java':
'AF_W32', etc.
*/

	static int getExecutableType( String fullPath, int val[] ) {
		return nativeGetExecutableType( fullPath, val );
	}


/**
Gets the attributes of the given file.

<BR><B>WANC</B><BR>

@param fullPath the full path of the file
@param val the attributes will be placed at val[ 0 ], and will be one of the attributes defined in 'DiskFile.java':
'FILE_EXECUTABLE', 'FILE_DIR', etc.
*/

	static int getFileAttributes( String fullPath, int val[]  ) {
		return nativeGetFileAttributes( fullPath, val );
	}

/**
Indicates which bits of the value returned from getFileAttributes are valid.
See that method for more information.

<BR><B>WANC</B><BR>

@param fullPath the full path of the file
@param val the mask will be placed at val[ 0 ]
*/

	static int getFileAttributesMask( String fullPath, int val[]  ) {
		return nativeGetFileAttributesMask( fullPath, val );
	}

/**
Set the indicated flags.

<BR><B>WANC</B><BR>

@param fullPath the full path of the file
@param whichFlags the mask indicating which bits in newValues are significant
@param newValues contains the bits to be set/reset.
*/

	static int setFileAttributes( String thePath, int whichFlags, int newValues ) {
		return nativeSetFileAttributes( thePath, whichFlags, newValues );
	}

/**
Gets the mask which indicates which bits in the argument to 'setFileAttributes' can be set.
Returns an error code, the mask is returned in the first element of val.

<BR><B>WANC</B><BR>

@param fullPath the full path of the file
*/

	static int getFileAttributesWriteMask( String thePath, int val[] ) {
		return nativeGetFileAttributesWriteMask( thePath, val );
	}

/**
Launches the given app with a set of arguments.

<P>
The Java/native implementation of this method has changed as of 1.2.1.
Previously, thus was a WANC: i.e., the arguments to this method were passed through to native code, which handled
putting the arguments into the template. Now, this is handled in Java.

<P>
Now, only the appPath and retData arguments are used, and the rest are ignored.

@param appPath the command line which will be passed to CreateProcess. Any arguments must be added to this
command line Java-side.
@param retData on return, contains the AppData for the process. See AppProcessMSVM.java for a description of AppData.
@param allotherarguments ignored; set to 0 or null
*/

	static int launchApp( String appPath, String verb, String regKey, String commandLine,
							int retData[], int numArgs, String args[] ) {
		return nativeLaunchApp( appPath, verb, regKey, commandLine, retData, numArgs, args );
	}

/**
Launches the given URL.
@param url the fully qualified URL
@param flags currently ignored
@param preferredBrowsers currently ignored
*/

	static int launchURL( String url, int flags, String preferredBrowsers[] ) {
		if ( preferredBrowsers != null )
			return nativeLaunchURL( url, tempDir, flags, preferredBrowsers );
		else
			return nativeLaunchURL( url, tempDir, flags, kStandardBrowsers );
	}

/**
Returns the full paths of the apps which match the given name.

@param appName the string to search for.
@param maxToReturn indicates the maximum number of AppFiles to return. NOTE: this is used as
a hint only; the actual array size may be greater or less than this.
@param flags the lower two bits of this int indicate the level of searching which should be
performed. 0 indicates only standard searching; 1, 2, and 3 indicate increasingly full searching
The remaining bits of this int are reserved, and should be set to zero.
*/

	static String[] findAppsByName( String appName, int maxToReturn, int flags ) {
		String		paths[], retArray[];
		int			i, theErr, numReturned, numRetArray[];

		paths = new String[ maxToReturn ];
		numRetArray = new int[ 1 ];

		theErr = nativeFindAppsByName( appName, maxToReturn, flags, numRetArray, paths );

		numReturned = numRetArray[ 0 ];
		if ( theErr != ErrCodes.ERROR_NONE || numReturned < 1 )
			return null;

		retArray = new String[ numReturned ];
		for ( i = 0; i < numReturned; i++ )	{
			retArray[ i ] = paths[ i ];
		}

		return retArray;
	}	

/**
Returns the full paths of the apps which are used to launch files with the given extension
@param ext the extension being searched for: ".txt"
@param maxToReturn the maximum number to return. This is a hint only.
@param flags reserved; set to 0
*/

	static String[] findAppsByExtension( String ext, int maxToReturn, int flags ) {
		String		paths[], retArray[];
		int			i, theErr, numReturned, numRetArray[];

		paths = new String[ maxToReturn ];
		numRetArray = new int[ 1 ];

		theErr = nativeFindAppsByExtension( ext, tempDir, maxToReturn, flags, numRetArray, paths );
		numReturned = numRetArray[ 0 ];
		if ( theErr != ErrCodes.ERROR_NONE || numReturned == 0 )
			return null;

		retArray = new String[ numReturned ];
		for ( i = 0; i < numReturned; i++ )
			retArray[ i ] = paths[ i ];

		return retArray;

	}	

/**
Indicates if the process with the given AppData is still running.
See AppProcessMSVM.java for a description of AppData.

<BR><B>WANC</B><BR>
*/

	static int verifyNativeAppData( int appData[] ) {
		return nativeVerifyNativeAppData( appData );
	}

/**
Quits the given process.

<BR><B>WANC</B><BR>

@param appData the AppData
See AppProcessMSVM.java for a description of AppData.
@param flags ignored; set to 0
*/

	static int quitApp( int appData[], int flags ) {
		return nativeQuitApp( appData, flags );
	}

/**
Moves the given process.

<BR><B>WANC</B><BR>

@param appData the AppData
See AppProcessMSVM.java for a description of AppData.
@param selector one of the 'APP_MOVE_TOFRONT', etc. constants defined in AppProcess.java
@param flags ignored; set to 0
*/

	static int moveApp( int appData[], int selector, int flags ) {
		return nativeMoveApp( appData, selector, flags );
	}
 
/**
Wraps WinAPI FindFirstFile.

<BR><B>WANC</B><BR>

@param searchStr the full path to be searched
@param hFind on return, hFind[ 0 ] will contain the search handle to be used with findNextFile and findClose
You must call findClose when you are finished searching
@param attrs on return, attrs[ 0 ] will contain attributes for the file. See the kFindFirstAttr??? values above.
@param fileName on return fileName[ 0 ] will contain the first matching file name
*/

	static int findFirstFile( String searchStr, int hFind[], int attrs[], String fileName[] ) {
		return nativeFindFirstFile( searchStr, hFind, attrs, fileName );
	}

/**
Wraps WinAPI FindNextFile.

<BR><B>WANC</B><BR>

@param findHandle the search handle obtained from a previous call to findFirstFile.
@param attrs on return, attrs[ 0 ] will contain attributes for the file. See the kFindFirstAttr??? values above.
@param fileName on return fileName[ 0 ] will contain the next matching file name
*/

	static int findNextFile( int findHandle, int attrs[], String fileName[] ) {
		return nativeFindNextFile( findHandle, attrs, fileName );
	}

/**
Wraps WinAPI FindClose.

<BR><B>WANC</B><BR>

@param findHandle the search handle obtained from a previous call to findFirstFile.
You must call this method when you are finished searching
*/

	static int findClose( int findHandle ) {
		return nativeFindClose( findHandle );
	}

/**
See the CommandLineUtilsMSVM.findVerbs() method.

<BR><B>WANC</B><BR>
*/

	static int findVerbs( String fullPaths[], String fileName, int maxToReturn, int numReturned[], String triples[] ) {
		return nativeFindVerbs( fullPaths, fileName, maxToReturn, numReturned, triples );
	}

/**
Gets the creation/modification/access dates of a volume.
@param driveName
@param datesArray on return, the dates will be placed in this array at the offsets defined by the
kXXXDateOffset constants. This array must have at least kDateBundleArrayLen elements.

<BR><B>WANC</B><BR>
*/

	static int getVolumeDate( String driveName, int datesArray[] ) {
		return nativeGetVolumeDate( kUnused, driveName, datesArray );
	}

/**
Gets the creation/modification/access dates of a file/folder.
@param path the full path of the file/folder
@param datesArray on return, the dates will be placed in this array at the offsets defined by the
kXXXDateOffset constants. This array must have at least kDateBundleArrayLen elements.

<BR><B>WANC</B><BR>
*/

	static int getFileDate( String path, int datesArray[] ) {
		return nativeGetFileDate( kUnused, path, datesArray );
	}

/**
<BR><B>WANC</B><BR>
*/

	static int getAllProcessInfo( int cntUsageArray[], int th32ProcessIDArray[],
									int th32DefaultHeapIDArray[], int th32ModuleIDArray[],
									int cntThreadsArray[], int th32ParentProcessIDArray[],
									int pcPriClassBaseArray[], int dwFlagsArray[],
									int dwThreadIDArray[], int hWndArray[],
									String fullPathArray[], int maxToReturn, int numReturned[] ) {
		return nativeGetAllProcessInfo( cntUsageArray, th32ProcessIDArray,
									th32DefaultHeapIDArray, th32ModuleIDArray,
									cntThreadsArray, th32ParentProcessIDArray,
									pcPriClassBaseArray, dwFlagsArray,
									dwThreadIDArray, hWndArray,
									fullPathArray, maxToReturn, numReturned );
	}

/**
<BR><B>WANC</B><BR>
*/

	static int getShortPathName( String inFileName, String outFileName[] ) {
		return nativeGetShortPathName( inFileName, outFileName );
	}

	private static native int nativeLaunchApp( String appPath, String verb, String regKey, String commandLine, int retData[], int numArgs, String args[] );

	private static native int nativeGetVolumeDate( int unused, String driveName, int datesArray[] );
	private static native int nativeGetFileDate( int unused, String path, int datesArray[] );

	private static native int nativeVerifyNativeAppData( int appData[] );
	private static native int nativeQuitApp( int appData[], int flags );
	private static native int nativeMoveApp( int appData[], int selector, int flags );

	private static native int nativeFindAppsByName( String appName, int maxToReturn, int flags, int numReturned[], String paths[] );
	private static native int nativeFindAppsByExtension( String appName, String tempDir, int maxToReturn, int flags, int numReturned[], String paths[] );
	private static native int nativeFindVerbs( String fullPaths[], String fileName, int maxToReturn, int numReturned[], String triples[] );
	private static native int nativeLaunchURL( String url, String tempDir, int flags, String preferredBrowsers[] );

	private static native int nativeResolveLinkFile( String linkFilePath, String retPath[], int flags );

	private static native int nativeGetExecutableType( String fullPath, int val[] );

	private static native int nativeGetFileAttributes( String fullPath, int val[]  );
	private static native int nativeGetFileAttributesMask( String fullPath, int val[]  );
	private static native int nativeSetFileAttributes( String thePath, int whichFlags, int newValues );
	private static native int nativeGetFileAttributesWriteMask( String thePath, int val[] );

	private static native int nativeGetAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] );

	private static native int nativeGetMainMonitorInfo( int monitorInfo[] );

	private static native int nativeSetVolumeLabel( String driveName, String newLabel );

	private static native int nativeGetDriveDisplayName( String driveName, String displayName[] );

	private static native int nativeGetVolumeFlags( String driveName, int flags[] );

	private static native int nativeGetVolumeReadFlagsMask( String driveName, int flags[] );

	private static native int nativeGetVolumeCapInfo( String driveName, long capInfo[] );

	private static native int nativeGetVolumeInformation( String driveName, String infoStr[], int infoInt[] );

	private static native int nativeGetVolumes( int maxToReturn, int numReturned[], String driveNames[] );

	private static native int nativeCreateVolumeAlias( String driveName, String newAliasPath, int flags );

	private static native int nativeCreateFileAlias( String targetPath, String newAliasPath, int flags );

	private static native int nativeVIPGetFileVersionInfoStart( String fileName );

	private static native int nativeVIPVerQueryValue( int versionH, String key, String value[] );

	private static native int nativeVIPGetFileVersionInfoEnd( int versionH );

	private static native int nativeGetFileIcon( String fullPath, int whichIcon, int w, int h,

															int xform, int align, int pData[] );

	private static native int nativeGetExtIcon( String ext, int whichIcon, int w, int h,

															int xform, int align, int pData[] );

	private static native int nativeGetVolumeIcon( String driveName, int whichIcon, int w, int h,

															int xform, int align, int pData[] );

	private static native int nativeFindFirstFile( String searchStr, int hFind[], int attrs[], String fileName[] );
	private static native int nativeFindNextFile( int findHandle, int attrs[], String fileName[] );
	private static native int nativeFindClose( int findHandle );

	private static native int nativeGetAllProcessInfo( int cntUsageArray[], int th32ProcessIDArray[],
									int th32DefaultHeapIDArray[], int th32ModuleIDArray[],
									int cntThreadsArray[], int th32ParentProcessIDArray[],
									int pcPriClassBaseArray[], int dwFlagsArray[],
									int dwThreadIDArray[], int hWndArray[],
									String fullPathArray[], int maxToReturn, int numReturned[] );
	private static native int nativeGetShortPathName( String inFileName, String outFileName[] );
}


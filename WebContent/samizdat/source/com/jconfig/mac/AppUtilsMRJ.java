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

import com.jconfig.FinderInfo;	//	this should be removed
import com.jconfig.ErrCodes;
import com.jconfig.Trace;
import com.jconfig.JUtils;
import java.io.PrintStream;

/**
Contains static methods which call native methods. Contains most of the Mac native code.
Many of the methods of this file are simply wrappers around the private native methods.
Almost all of these methods return an error code; 0 means success, other values indicate some
form of error.

<P>
The term 'WANC' in the documentation means that that method is just a Wrapper Around Native Code.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppUtilsMRJ {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	static final int		FI_ARRAYLEN = FinderInfo.FI_ARRAYLEN;
	static final int		FI_OFFS_CRE = FinderInfo.FI_OFFS_CRE;
	static final int		FI_OFFS_TYPE = FinderInfo.FI_OFFS_TYPE;
	static final int		FI_OFFS_FLAGS = FinderInfo.FI_OFFS_FLAGS;
	static final int		FI_OFFS_ATTRIBUTES = FinderInfo.FI_OFFS_ATTRIBUTES;

	static final int		kLaunchWithDocOpenDoc = 1;
	static final int		kLaunchWithDocPrintDoc = 2;
	static final int		kSendAppDocsOpenDoc = 3;
	static final int		kSendAppDocsPrintDoc = 4;

	static final int		kCategoryVolume = 0;
	static final int		kCategoryDirectory = 1;
	static final int		kCategoryFile = 2;
	static final int		kCategoryAlias = 3;
	static final int		kCategoryUnknown = 4;

	static final int		kCategoryOSXExtendedInfoBit = 0x10000000;
	static final int		kCategoryOSXPlainFileBit = 0x00000001;
	static final int		kCategoryOSXPackageBit = 0x00000002;
	static final int		kCategoryOSXApplicationBit = 0x00000004;
	static final int		kCategoryOSXContainerBit = 0x00000008;
	static final int		kCategoryOSXAliasFileBit = 0x00000010;
	static final int		kCategoryOSXSymlinkBit = 0x00000020;
	static final int		kCategoryOSXInvisibleBit = 0x00000040;
	static final int		kCategoryOSXNativeAppBit = 0x00000080;
	static final int		kCategoryOSXClassicAppBit = 0x00000100;
	static final int		kCategoryOSXAppPrefersNativeBit = 0x00000200;
	static final int		kCategoryOSXAppPrefersClassicBit = 0x00000400;
	static final int		kCategoryOSXAppIsScriptableBit = 0x00000800;
	static final int		kCategoryOSXVolumeBit = 0x00001000;
	static final int		kCategoryOSXExtensionIsHiddenBit = 0x00100000;



/**
The length of a Pascal string; for many methods which accept a writable byte array as an argument,
the array must have at least this many elements.
*/

	static final int		kPNameLen = 256;

/**
The length of an array used to hold a ProcessSerialNumber
*/

	static final int		kPSNLen = 2;

/**
The offset of the low int of a ProcessSerialNumber
*/

	static final int		kPSNLoOffset = 0;

/**
The offset of the high int of a ProcessSerialNumber
*/

	static final int		kPSNHiOffset = 1;

/**
The length of an array used to hold a vRef and parID
*/

	static final int		kRefPairLen = 2;

/**
The offset of the vRef in an array used to hold a vRef and parID
*/

	static final int		kVRefOffset = 0;

/**
The offset of the parID in an array used to hold a vRef and parID
*/

	static final int		kParIDOffset = 1;

/**
The length of an array used to hold the length of a file's data and resource forks.
*/

	static final int		kForkSizesLen = 2;

/**
The offset of the data fork size in an array used to hold the length of a file's data and resource forks.
*/

	static final int		kForkSizesDataOffset = 0;

/**
The offset of the resource fork size in an array used to hold the length of a file's data and resource forks.
*/

	static final int		kForkSizesRsrcOffset = 1;

	static final int		kFinderFlagsColorMask = 0x0E;
	
/**
See iterateContents()
*/

	static final int		kIterateEntrySize = 68;
	
/**
See iterateContents()
*/

	static final int		kIterateMaxEntries = 1000;
	
/**
See iterateContents()
*/

	static final int		kIterateBufferSize = ( kIterateMaxEntries * kIterateEntrySize );
	
/**
See iterateContents()
*/

	static final int		kIterateNameSize = 64;
	
/**
See iterateContents()
*/

	static final int		kIterateTypeOffset = 64;
	
/**
See iterateContents()
*/

	static final int		kIterateFlagsOffset = 65;
	
/**
See iterateContents()
*/

	static final int		kIterateIsFile = 0;
	
/**
See iterateContents()
*/

	static final int		kIterateIsDir = 1;
	
/**
See iterateContents()
*/

	static final int		kIterateIsAlias = 2;
	
/**
See iterateContents()
*/

	static final int		kIterateIsHiddenMask = 0x01;

/**
Used in retrieving monitor information; see	the 'getAllMonitorInfo' method.
*/

	static final int		kMonitorInfoNumInts = 20;

	static final int		kGetProcessesNameLen = 64;

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
The offset of the backup date in the array passed to getFileDate() and getVolumeDate().
*/

	static final int		DATE_BKUP_OFFSET = 12;

/**
See the 'whichFork' argument to setForkLength().
*/

	static final int		kSetForkLengthDATA = 0;
	static final int		kSetForkLengthRSRC = 1;

	private static final int		kUnused = -1;
	
	private static IToolboxLock		lockObject = null;

	private AppUtilsMRJ() {
	}

	static void setLockObject( IToolboxLock lock ) {
		lockObject = lock;
	}

	static IToolboxLock getLockObject() {
		return lockObject;
	}

	static void dumpCategory( java.io.PrintStream ps, int category ) {
		ps.println( "Category: " + category + " (0x" + Integer.toHexString( category ) + ")" );

		if ( ( category & kCategoryOSXExtendedInfoBit ) != 0 ) {
			if ( ( category & kCategoryOSXExtendedInfoBit ) != 0 ) ps.println( "    ExtendedInfo" );
			if ( ( category & kCategoryOSXPlainFileBit ) != 0 ) ps.println( "    PlainFile" );
			if ( ( category & kCategoryOSXPackageBit ) != 0 ) ps.println( "    Package" );
			if ( ( category & kCategoryOSXApplicationBit ) != 0 ) ps.println( "    Application" );
			if ( ( category & kCategoryOSXContainerBit ) != 0 ) ps.println( "    Container" );
			if ( ( category & kCategoryOSXAliasFileBit ) != 0 ) ps.println( "    AliasFile" );
			if ( ( category & kCategoryOSXSymlinkBit ) != 0 ) ps.println( "    Symlink" );
			if ( ( category & kCategoryOSXInvisibleBit ) != 0 ) ps.println( "    Invisible" );
			if ( ( category & kCategoryOSXNativeAppBit ) != 0 ) ps.println( "    NativeApp" );
			if ( ( category & kCategoryOSXClassicAppBit ) != 0 ) ps.println( "    ClassicApp" );
			if ( ( category & kCategoryOSXAppPrefersNativeBit ) != 0 ) ps.println( "    AppPrefersNative" );
			if ( ( category & kCategoryOSXAppPrefersClassicBit ) != 0 ) ps.println( "    AppPrefersClassic" );
			if ( ( category & kCategoryOSXAppIsScriptableBit ) != 0 ) ps.println( "    AppIsScriptable" );
			if ( ( category & kCategoryOSXVolumeBit ) != 0 ) ps.println( "    Volume" );
			if ( ( category & kCategoryOSXExtensionIsHiddenBit ) != 0 ) ps.println( "    ExtensionIsHidden" );
		}
		else {
			if ( category == kCategoryVolume ) ps.println( "    Volume" );
			else if ( category == kCategoryDirectory ) ps.println( "    Directory" );
			else if ( category == kCategoryFile ) ps.println( "    File" );
			else if ( category == kCategoryAlias ) ps.println( "    Alias" );
			else if ( category == kCategoryUnknown ) ps.println( "    Unknown" );
			else ps.println( "    out of range" );
		}
	}

/**
Returns a full path name created from an FSSpec.
The name will be in MRJ format: quoted-printable, and with '/' as the separator.

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
*/

	static String createFullPathName( int vRef, int parID, byte pName[] ) {
		int			theErr;
		String		retArray[];
		
		retArray = new String[ 1 ];

		theErr = wrap_nCreateFullPathName( vRef, parID, pName, retArray );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		return retArray[ 0 ];
	}

/**
Gets a list of the running processes.
Information on each process is placed into a series of arrays.

<BR><B>WANC</B><BR>

@param maxToReturn the lengths of the 'vRefs', 'parIDs', 'psnLo', 'psnHi', and 'proFlags' arrays.

@param flags 0 or a combination of the FileRegistry.kGetProcesses??? flags

@param numRet the number of processes returned will be placed at numRet[ 0 ]

@param vRefs the vRefNum of the file of each process will be placed at each element of this array.
Must have at least 'maxToReturn' elements.

@param parIDs the parID of the file of each process will be placed at each element of this array.
Must have at least 'maxToReturn' elements.

@param pNames the name of the file of each process will be placed into this array; each name will
be a Pascal string, and will occupy 'kGetProcessesNameLen' elements.
Must have at least 'maxToReturn' * 'kGetProcessesNameLen' elements.

@param psnLo the low int of the process' PSN will be placed at each element of this array.
Must have at least 'maxToReturn' elements.

@param psnHi the high int of the process' PSN will be placed at each element of this array.
Must have at least 'maxToReturn' elements.

@param proFlags flags on the process will be placed at each element of this array.
Not currently used.
Must have at least 'maxToReturn' elements.
*/

	static int getProcesses( int maxToReturn, int flags, int numRet[], int vRefs[], int parIDs[],
								byte pNames[], int psnLo[], int psnHi[], int proFlags[] ) {
		return wrap_nGetProcesses( maxToReturn, flags, numRet, vRefs, parIDs, pNames, psnLo, psnHi, proFlags );
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
		return wrap_nGetAllMonitorInfo( monitorInfo, maxToReturn, numReturned );
	}

/**
Stores information on the user's main monitor into the 'monitorInfo' array.
This array must have at least 'kMonitorInfoNumInts' elements.
The information on the monitor is stored at the 'kOffs???' offsets defined in 'MonitorMRJ.java':

<BR><B>WANC</B><BR>
*/

	static int getMainMonitorInfo( int monitorInfo[] ) {
		return wrap_nGetMainMonitorInfo( monitorInfo );
	}

/**
Launches an app without any arguments.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the app's file
@param parID the parID of the app's file
@param pName the name of the app's file, as a Pascal string
@param retPSN on return, the process' PSN will be stored in this array, the low int of the PSN at
'kPSNLoOffset', and the high int at 'kPSNHiOffset'
@param flags 0, or AppFile.AF_NO_LAYER_SWITCH
*/

	static int launchApp( int vRef, int parID, byte pName[], int retPSN[], int flags ) {
		return wrap_nLaunchApp( vRef, parID, pName, retPSN, flags );
	}

/**
Launches an app with one or more documents.

<BR><B>WANC</B><BR>

@param whichCommand either kLaunchWithDocOpenDoc or kLaunchWithDocPrintDoc
@param vRef the vRefNum of the app's file
@param parID the parID of the app's file
@param pName the name of the app's file, as a Pascal string
@param filePaths the full paths of the files to open the app with
@param retPSN on return, the process' PSN will be stored in this array, the low int of the PSN at
'kPSNLoOffset', and the high int at 'kPSNHiOffset'
@param flags 0, or AppFile.AF_NO_LAYER_SWITCH
*/

	static int launchWithDoc( int whichCommand, int vRef, int parID, byte pName[],
												String filePaths[], int retPSN[], int flags ) {
		return wrap_nLaunchWithDoc( whichCommand, vRef, parID, pName, filePaths, retPSN, flags );
	}

/**
Get the folder which contains a file.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param pContParID the parID of the container will be placed at pContParID[ 0 ]
@param pContName the name of the container will be placed into this array as a Pascal string.
Must have kPNameLen or more elements.
*/

	static int getContainer( int vRef, int parID, byte pName[], int pContParID[], byte pContName[] ) {
		return wrap_nGetContainer( vRef, parID, pName, pContParID, pContName );
	}

/**
Get the names and other information on the objects inside a folder.
The native code puts information on each file or folder into a byte array.

Each entry in this array is 'kIterateEntrySize' bytes long; the name of the object occupies the first
'kIterateNameSize' bytes.

The type of the object is stored at offset 'kIterateTypeOffset', and will
have the value 'kIterateIsFile', 'kIterateIsDir', or 'kIterateIsAlias'.

Flags on the object are stored at offset 'kIterateFlagsOffset'. This is not currently used.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the folder
@param parID the parID of the folder
@param pName the name of the folder, as a Pascal string
@param dirIDArray the dirID of the folder will be placed at dirIDArray[ 0 ]
@param numRet the number of objects returned will be placed at numRet[ 0 ]
@param buffer will hold information on each object, in the format given above.
@param numEntries the number of entries which 'buffer' can hold
@param flags 0, or one or more of the FileRegistry.kIgnore??? flags
*/

	static int iterateContents( int vRef, int parID, byte pName[], int dirIDArray[],
													int numRet[], byte buffer[], int numEntries, int flags ) {
		return wrap_nIterateContents( vRef, parID, pName, dirIDArray,
													numRet, buffer, numEntries, flags );
	}

/**
Get the names and other information on the objects inside the root level of a volume.
The native code puts information on each file or folder into a byte array in the same format as
described for the 'iterateContents' method.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param dirIDArray the dirID of the volume will be placed at dirIDArray[ 0 ]
@param numRet the number of objects returned will be placed at numRet[ 0 ]
@param buffer will hold information on each object, in the format described for the 'iterateContents' method.
@param numEntries the number of entries which 'buffer' can hold
@param flags 0, or one or more of the FileRegistry.kIgnore??? flags
*/

	static int iterateVolumeContents( int vRef, int dirIDArray[], int numRet[],
															byte buffer[], int numEntries, int flags ) {
		return wrap_nIVC( vRef, dirIDArray, numRet, buffer, numEntries, flags );
	}

/**
Creates an alias to a volume

<BR><B>WANC</B><BR>

@param targetVRef the vRefNum of the target volume
@param newAlias the full path of the new alias
@param creator ignored; set to 0
@param flags 
*/

	static int createVolumeAlias( int targetVRef, String newAlias, int creator, int flags ) {
		return wrap_nCreateVolumeAlias( targetVRef, newAlias, creator, flags );
	}

/**
Gets the FinderInfo for a volume.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param on return, contains the finder info
*/

	static int getVolumeFinderInfo( int vRef, int finfo[] ) {
		return wrap_nGetVolumeFinderInfo( vRef, finfo );
	}

/**
Sets the color coding of a file.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param newCoding the new color coding
*/

	static int setColorCoding( int vRef, int parID, byte pName[], int newCoding ) {
		return wrap_nSetColorCoding( vRef, parID, pName, newCoding );
	}

/**
Sets the color coding of a volume.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param newCoding the new color coding
*/

	static int setVolumeColorCoding( int vRef, int newCoding ) {
		return wrap_nSetVolumeColorCoding( vRef, newCoding );
	}

/**
Gets the type of a file.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param cat the type of the file will be placed at cat[ 0 ], and will be one of the 'kCategory???' values
*/

	static int getFileCategory( int vRef, int parID, byte pName[], int cat[] ) {
		return wrap_nGetFileCategory( vRef, parID, pName, cat );
	}

/**
Resolves an alias file.

<BR><B>WANC</B><BR>

@param inVRef the vRefNum of the alias
@param inParID the parID of the alias
@param pInName the name of the alias, as a Pascal string
@param outVRefAndParID the vRef of the resolved alias will be placed at vRefAndParID[ 0 ]
the parID of the resolved alias will be placed at vRefAndParID[ 1 ]
@param pOutName the name of the resolved alias will be placed in this array as a Pascal string.
This array must have at least kPNameLen elements.
@param flags either FileRegistry.ALIAS_UI or FileRegistry.ALIAS_NO_UI
*/

	static int resolveAlias( int inVRef, int inParID, byte pInName[],
								int outVRefAndParID[], byte pOutName[], int flags ) {
		return wrap_nResolveAlias( inVRef, inParID, pInName, outVRefAndParID, pOutName, flags );
	}

/**
Creates an alias to a file or folder.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param newAlias the full path of the new alias
@param creator
@param flags
*/

	static int createAlias( int vRef, int parID, byte pName[],
								String newAlias, int creator, int flags ) {
		return wrap_nCreateAlias( vRef, parID, pName, newAlias, creator, flags );
	}

/**
Returns the capacity of a volume

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param cap the capacity will be placed at cap[ 0 ]
*/

	static int getVolumeCapacity( int vRef, long cap[] ) {
		return wrap_nGetVolumeCapacity( vRef, cap );
	}

/**
Returns the free space of a volume

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param cap the free space will be placed at cap[ 0 ]
*/

	static int getVolumeFreeSpace( int vRef, long space[] ) {
		return wrap_nGetVolumeFreeSpace( vRef, space );
	}

/**
Gets the name of a volume

<BR><B>WANC</B><BR>

@param vRef the vRefNum
@param pName the name will be placed in this array as a Pascal string
This array must have at least kPNameLen elements.
*/
	static int getVolumeName( int vRef, byte pName[] ) {
		return wrap_nGetVolumeName( vRef, pName );
	}

/**
Renames a volume.

<BR><B>WANC</B><BR>

@param vRef the vRefNum
@param newName the new name of the volume
*/

	static int renameVolume( int vRef, String newName ) {
		return wrap_nRenameVolume( vRef, newName );
	}

/**
Renames a file or folder.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param pOutName returns the new name of the file, as a Pascal string
Must have at least kPNameLen elements.
@param newName the new name of the file
*/

	static int renameFile( int vRef, int parID, byte pName[], byte pOutName[], String newName ) {
		return wrap_nRenameFile( vRef, parID, pName, pOutName, newName );
	}

/**
Gets the sizes of a file's data and resource forks.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param sizes must have at least kForkSizesLen elements
the data fork size will be placed at sizes[ kForkSizesDataOffset ], and
the resource fork size will be placed at sizes[ kForkSizesRsrcOffset ]
*/

	static int getForkSizes( int vRef, int parID, byte pName[], long sizes[] ) {
		return wrap_nGetForkSizes( vRef, parID, pName, sizes );
	}

/**
Updates the folder which contains a file.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
*/

	static int updateContainer( int vRef, int parID, byte pName[] ) {
		return wrap_nUpdateContainer( vRef, parID, pName );
	}

/**
Gets the flags for a volume. These flags are defined in DiskVolume.java.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param flags the flags will be placed at flags[ 0 ]
*/

	static int getDiskVolumeFlags( int vRef, int flags[] ) {
		return wrap_nGetDiskVolumeFlags( vRef, flags );
	}

/**
Sets the flags for a volume. These flags are defined in DiskVolume.java.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param flagsMask indicates which flags in newFlags are significant
@param newFlags which flags to set or reset
*/

	static int setDiskVolumeFlags( int vRef, int flagMask, int newFlags ) {
		return wrap_nSetDiskVolumeFlags( vRef, flagMask, newFlags );
	}

/**
Indicates which flags returned by 'getDiskVolumeFlags' are significant.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param masks the mask will be placed at masks[ 0 ]
*/

	static int getDiskVolumeReadFlagsMask( int vRef, int masks[] ) {
		return wrap_nGetDVReadFlagsMask( vRef, masks );
	}

/**
Indicates which flags can be set for the volume.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
@param masks the mask will be placed at masks[ 0 ]
*/

	static int getDiskVolumeWriteFlagsMask( int vRef, int masks[] ) {
		return wrap_nGetDVWriteFlagsMask( vRef, masks );
	}

/**
Gets the vRefNums of the currently mounted volumes.

<BR><B>WANC</B><BR>

@param maxToReturn the maximum number of volumes to return
@param numRet the number returned will be placed at numRet[ 0 ]
@param vRefs the vRefNum of each volume will be placed in this array.
Must have at least 'maxToReturn' elements.
*/

	static int getVolumes( int maxToReturn, int numRet[], int vRefs[] ) {
		return wrap_nGetVolumes( maxToReturn, numRet, vRefs );
	}

/**
Gets the icon suite for a file type.

<BR><B>WANC</B><BR>

@param vRef	the volume to search for that type
@param creator the file's creator
@param type the file's type
@param selector	unused; set to 0
@param pHSuite the suite's handle will be placed at pHSuite[ 0 ]
*/

	static int getFTACIconSuite( int vRef, int creator, int type,
													int selector, int pHSuite[] ) {
		return wrap_nGetFTACIconSuite( vRef, creator, type, selector, pHSuite );
	}

/**
Gets the icon suite for a volume.

<BR><B>WANC</B><BR>

@param vRef	the vRefNum of the volume 
@param selector	unused; set to 0
@param pHSuite the suite's handle will be placed at pHSuite[ 0 ]
*/

	static int getVolumeIconSuite( int vRef, int selector, int pHSuite[] ) {
		return wrap_nGetVolumeIconSuite( vRef, selector, pHSuite );
	}

/**
Gets the icon suite for a file.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param selector	unused; set to 0
@param pHSuite the suite's handle will be placed at pHSuite[ 0 ]
*/

	static int getFileIconSuite( int vRef, int parID, byte pName[],
													int selector, int pHSuite[] ) {
		return wrap_nGetFileIconSuite( vRef, parID, pName, selector, pHSuite );
	}

/**
Draws an icon of an icon suite.

<BR><B>WANC</B><BR>

@param which either IconBundle.ICON_LARGE or IconBundle.ICON_SMALL
@param width the width of the icon
@param height the height of the icon
@param hSuite the icon suite
@param xform any transformation to be applied to the icon
@param align any alignment to tbe applied to the icon
@param pData an array of ints which will hold the Java format ARGB data for the icon
This must have at least width * height elements
*/

	static int plotIcon( int which, int width, int height,
											int hSuite, int xform, int align, int pData[] ) {
		return wrap_nPlotIcon( which, width, height, hSuite, xform, align, pData );
	}

/**
Disposes an icon suite.

<BR><B>WANC</B><BR>

@param hSuite the icon suite
@param flags passed to Mac API DisposeIconSuite()
*/

	static int disposeIconSuite( int hSuite, int flags ) {
		return wrap_nDisposeIconSuite( hSuite, flags );
	}

/**
Gets the flags for a file. These flags are defined in DiskFile.java.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param flags the flags will be placed at flags[ 0 ]
*/

	static int getDiskFileFlags( int vRef, int parID, byte pName[], int flags[] ) {
		return wrap_nGetDiskFileFlags( vRef, parID, pName, flags );
	}

/**
Sets the flags for a file. These flags are defined in DiskFile.java.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param flagsMask indicates which flags in newFlags are significant
@param newFlags which flags to set or reset
*/

	static int setDiskFileFlags( int vRef, int parID, byte pName[], int flagMask, int newFlags ) {
		return wrap_nSetDiskFileFlags( vRef, parID, pName, flagMask, newFlags );
	}


/**
Indicates which flags returned by 'getDiskVolumeFlags' are significant.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param masks the mask will be placed at masks[ 0 ]
*/

	static int getDiskFileReadFlagsMask( int vRef, int parID, byte pName[], int masks[] ) {
		return wrap_nGetDFReadFlagsMask( vRef, parID, pName, masks );
	}

/**
Indicates which flags can be set for the file.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param masks the mask will be placed at masks[ 0 ]
*/

	static int getDiskFileWriteFlagsMask( int vRef, int parID, byte pName[], int masks[] ) {
		return wrap_nGetDFWriteFlagsMask( vRef, parID, pName, masks );
	}

/**
Verifies whether a file exists.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
*/

	static int verifyFile( int vRef, int parID, byte pName[] ) {
		return wrap_nVerifyFile( vRef, parID, pName );
	}

/**
Verifies whether a volume is still mounted.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the volume
*/

	static int verifyVolume( int vRef ) {
		return wrap_nVerifyVolume( vRef );
	}

/**
Converts a full path name to a FSSpec.

<BR><B>WANC</B><BR>

@param fullPath the full path, in MRJ format: quoted-printable, and with '/' as the separator.
@param vRefAndParID the vRef of the file will be placed at vRefAndParID[ 0 ]
the parID of the file will be placed at vRefAndParID[ 1 ]
@param pName the name of the file will be placed in this array, in Pascal format
This array must have at least kPNameLen elements.
*/

	static int fullPathToSpec( String fullPath, int vRefAndParID[], byte pName[] ) {
		return wrap_nFullPathToSpec( fullPath, vRefAndParID, pName );
	}

/**
Gets the creator, type, flags, and attributes of a file.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param finfo information on the file will be placed in this array.
This must have at least 4 elements, which will be placed as follows:
creator at finfo[ 0 ], type at finfo[ 1 ], flags at finfo[ 2 ], attribs at finfo[ 3 ]
*/

	static int getFinderInfo( int vRef, int parID, byte pName[], int finfo[] ) {
		return wrap_nGetFinderInfo( vRef, parID, pName, finfo );
	}

/**
Sets the FinderInfo of a file.

<BR><B>WANC</B><BR>

@param vRef the vRefNum of the file
@param parID the parID of the file
@param pName the name of the file, as a Pascal string
@param creatorAndType the new creator should be at creatorAndType[ 0 ], and the new type at creatorAndType[ 1 ]
*/

	static int setCreatorAndType( int vRef, int parID, byte pName[], int creatorAndType[] ) {
		return wrap_nSetCreatorAndType( vRef, parID, pName, creatorAndType );
	}

/**
Sends an AppleEvent to a process.

<BR><B>WANC</B><BR>

@param whichCommand either 'kSendAppDocsOpenDoc' or 'kSendAppDocsPrintDoc'
@param appPSN the PSN of the process. The low int of the PSN should be at 'kPSNLoOffset',
and the high int at 'kPSNHiOffset'
@param filePaths full paths of the documents to be sent to the app
@param flags unused; set to 0
*/

	static int sendAppDocs( int whichCommand, int appPSN[], String filePaths[], int flags ) {
		return wrap_nSendAppDocs( whichCommand, appPSN, filePaths, flags );
	}

/**
Quits a process.

<BR><B>WANC</B><BR>

@param appPSN the PSN of the process. The low int of the PSN should be at 'kPSNLoOffset',
and the high int at 'kPSNHiOffset'
@param flags unused; set to 0
*/

	static int quitApp( int appPSN[], int flags ) {
		return wrap_nQuitApp( appPSN, flags );
	}

/**
Moves a process.

<BR><B>WANC</B><BR>

@param appPSN the PSN of the process. The low int of the PSN should be at 'kPSNLoOffset',
and the high int at 'kPSNHiOffset'
@param selector one of the values defined in FileRegistry: 'APP_MOVE_TOFRONT', etc.
@param flags unused; set to 0
*/

	static int moveApp( int appPSN[], int selector, int flags ) {
		return wrap_nMoveApp( appPSN, selector, flags );
	}

/**
Verifies that a process is still running.

<BR><B>WANC</B><BR>

@param appPSN the PSN of the process. The low int of the PSN should be at 'kPSNLoOffset',
and the high int at 'kPSNHiOffset'
*/

	static int verifyPSN( int appPSN[] ) {
		return wrap_nVerifyPSN( appPSN );
	}

/**
Launches the given URL.
@param url the fully qualified URL
@param flags currently ignored
@param preferredBrowsers currently ignored
*/

	static int launchURL( String url, int flags, String preferredBrowsers[] ) {
		return wrap_nLaunchURLDirect( url, flags, preferredBrowsers );
	}

/**
Get the dates of a file into an array of ints

<BR><B>WANC</B><BR>

@param datesArray the three dates for the file are placed in this array, in the order:
modification, creation, backup
*/

	static int getFileDate( int vRef, int parID, byte pName[], int datesArray[] ) {
		return wrap_nGetFileDate( kUnused, vRef, parID, pName, datesArray );
	}

/**
Set the dates of a file from an array of ints

<BR><B>WANC</B><BR>

@param datesArray the three dates for the file should be placed in this array, in the order:
modification, creation, backup
*/

	static int setFileDate( int vRef, int parID, byte pName[], int datesArray[] ) {
		return wrap_nSetFileDate( kUnused, vRef, parID, pName, datesArray );
	}

/**
Get the dates of a volume into an array of ints

<BR><B>WANC</B><BR>

@param datesArray the three dates for the volume are placed in this array, in the order:
modification, creation, backup
*/

	static int getVolumeDate( int vRef, int datesArray[] ) {
		return wrap_nGetVolumeDate( kUnused, vRef, datesArray );
	}

/**
Set the dates of a volume from an array of ints

<BR><B>WANC</B><BR>

@param datesArray the three dates for the volume should be placed in this array, in the order:
modification, creation, backup
*/

	static int setVolumeDate( int vRef, int datesArray[] ) {
		return wrap_nSetVolumeDate( kUnused, vRef, datesArray );
	}

/**
<BR><B>WANC</B><BR>
*/

	static int getOpenableFileTypes( int vRef, int creator, int numReturned[], int fileTypes[] ) {
		return wrap_nGetOpenableFileTypes( vRef, creator, numReturned, fileTypes );
	}



/**
<BR><B>WANC</B><BR>
*/

	static int getRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] ) {
		return wrap_nGetRawResourceFork( flags, vRef, parID, pName, data );
	}

/**
<BR><B>WANC</B><BR>
*/

	static int setRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] ) {
		return wrap_nSetRawResourceFork( flags, vRef, parID, pName, data );
	}

/**
<BR><B>WANC</B><BR>
*/

	static int setForkLength( int flags, int whichFork, int vRef, int parID, byte pName[],
								long newLen ) {
		return wrap_nSetForkLength( flags, whichFork, vRef, parID, pName, newLen );
	}







	private static int wrap_nGetRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetRawResourceFork( flags, vRef, parID, pName, data );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetRawResourceFork( flags, vRef, parID, pName, data );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetForkLength( int flags, int whichFork, int vRef, int parID, byte pName[], long newLen ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetForkLength( flags, whichFork, vRef, parID, pName, newLen );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSendAppDocs( int whichCommand, int appPSN[], String filePaths[], int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSendAppDocs( whichCommand, appPSN, filePaths, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nQuitApp( int appPSN[], int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nQuitApp( appPSN, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nLaunchApp( int vRef, int parID, byte pName[], int retPSN[], int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nLaunchApp( vRef, parID, pName, retPSN, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nLaunchWithDoc( int whichCommand, int vRef, int parID, byte pName[], String filePaths[], int retPSN[], int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nLaunchWithDoc( whichCommand, vRef, parID, pName, filePaths, retPSN, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nCreateFullPathName( int vRef, int parID, byte pName[], String retArray[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nCreateFullPathName( vRef, parID, pName, retArray );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetOpenableFileTypes( int vRef, int creator, int numReturned[], int fileTypes[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetOpenableFileTypes( vRef, creator, numReturned, fileTypes );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetFileDate( int unused, int vRef, int parID, byte pName[], int datesArray[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetFileDate( unused, vRef, parID, pName, datesArray );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetFileDate( int unused, int vRef, int parID, byte pName[], int datesArray[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetFileDate( unused, vRef, parID, pName, datesArray );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetVolumeDate( int unused, int vRef, int datesArray[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetVolumeDate( unused, vRef, datesArray );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetVolumeDate( int unused, int vRef, int datesArray[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetVolumeDate( unused, vRef, datesArray );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nVerifyPSN( int appPSN[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nVerifyPSN( appPSN );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nMoveApp( int appPSN[], int selector, int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nMoveApp( appPSN, selector, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nLaunchURLDirect( String url, int flags, String preferredBrowsers[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nLaunchURLDirect( url, flags, preferredBrowsers );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nFullPathToSpec( String fullPath, int vRefAndParID[], byte pName[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nFullPathToSpec( fullPath, vRefAndParID, pName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetFinderInfo( int vRef, int parID, byte pName[], int finfo[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetFinderInfo( vRef, parID, pName, finfo );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetCreatorAndType( int vRef, int parID, byte pName[], int creatorAndType[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetCreatorAndType( vRef, parID, pName, creatorAndType );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nVerifyFile( int vRef, int parID, byte pName[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nVerifyFile( vRef, parID, pName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nVerifyVolume( int vRef ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nVerifyVolume( vRef );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetDiskFileFlags( int vRef, int parID, byte pName[], int flags[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetDiskFileFlags( vRef, parID, pName, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetDiskFileFlags( int vRef, int parID, byte pName[], int flagMask, int newFlags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetDiskFileFlags( vRef, parID, pName, flagMask, newFlags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetDFWriteFlagsMask( int vRef, int parID, byte pName[], int masks[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetDFWriteFlagsMask( vRef, parID, pName, masks );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetDFReadFlagsMask( int vRef, int parID, byte pName[], int masks[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetDFReadFlagsMask( vRef, parID, pName, masks );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetDiskVolumeFlags( int vRef, int flags[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetDiskVolumeFlags( vRef, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetDiskVolumeFlags( int vRef, int flagMask, int newFlags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetDiskVolumeFlags( vRef, flagMask, newFlags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetDVReadFlagsMask( int vRef, int masks[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetDVReadFlagsMask( vRef, masks );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetDVWriteFlagsMask( int vRef, int masks[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetDVWriteFlagsMask( vRef, masks );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetVolumes( int maxToReturn, int numRet[], int vRefs[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetVolumes( maxToReturn, numRet, vRefs );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nUpdateContainer( int vRef, int parID, byte pName[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nUpdateContainer( vRef, parID, pName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetFileCategory( int vRef, int parID, byte pName[], int cat[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetFileCategory( vRef, parID, pName, cat );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetForkSizes( int vRef, int parID, byte pName[], long len[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetForkSizes( vRef, parID, pName, len );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nRenameFile( int vRef, int parID, byte pName[], byte pOutName[], String newName ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nRenameFile( vRef, parID, pName, pOutName, newName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nRenameVolume( int vRef, String newName ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nRenameVolume( vRef, newName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetVolumeCapacity( int vRef, long cap[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetVolumeCapacity( vRef, cap );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetVolumeFreeSpace( int vRef, long space[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetVolumeFreeSpace( vRef, space );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nCreateAlias( int vRef, int parID, byte pName[], String newAlias, int creator, int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nCreateAlias( vRef, parID, pName, newAlias, creator, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetVolumeName( int vRef, byte pName[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetVolumeName( vRef, pName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nResolveAlias( int inVRef, int inParID, byte pInName[], int outVRefAndParID[], byte pOutName[], int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nResolveAlias( inVRef, inParID, pInName, outVRefAndParID, pOutName, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetColorCoding( int vRef, int parID, byte pName[], int newCoding ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetColorCoding( vRef, parID, pName, newCoding );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nSetVolumeColorCoding( int vRef, int newCoding ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nSetVolumeColorCoding( vRef, newCoding );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetVolumeFinderInfo( int vRef, int finfo[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetVolumeFinderInfo( vRef, finfo );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetVolumeIconSuite( int vRef, int selector, int pHSuite[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetVolumeIconSuite( vRef, selector, pHSuite );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetFileIconSuite( int vRef, int parID, byte pName[], int selector, int pHSuite[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetFileIconSuite( vRef, parID, pName, selector, pHSuite );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetFTACIconSuite( int vRef, int creator, int type, int selector, int pHSuite[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetFTACIconSuite( vRef, creator, type, selector, pHSuite );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nPlotIcon( int which, int width, int height, int hSuite, int xform, int align, int pData[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nPlotIcon( which, width, height, hSuite, xform, align, pData );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nCreateVolumeAlias( int targetVRef, String newAlias, int creator, int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nCreateVolumeAlias( targetVRef, newAlias, creator, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nIterateContents( int vRef, int parID, byte pName[], int dirIDArray[], int numRet[], byte buffer[], int numEntries, int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nIterateContents( vRef, parID, pName, dirIDArray, numRet, buffer, numEntries, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nIVC( int vRef, int dirIDArray[], int numRet[], byte buffer[], int numEntries, int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nIVC( vRef, dirIDArray, numRet, buffer, numEntries, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetContainer( int vRef, int parID, byte pName[], int pContParID[], byte pContName[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetContainer( vRef, parID, pName, pContParID, pContName );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nDisposeIconSuite( int hSuite, int flags ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nDisposeIconSuite( hSuite, flags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetAllMonitorInfo( monitorInfo, maxToReturn, numReturned );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetMainMonitorInfo( int monitorInfo[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetMainMonitorInfo( monitorInfo );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}

	private static int wrap_nGetProcesses( int maxToReturn, int flags, int numRet[], int vRefs[], int parIDs[], byte pNames[], int psnLo[], int psnHi[], int proFlags[] ) {
		try {
			if ( lockObject != null )
				lockObject.lock();

			return nGetProcesses( maxToReturn, flags, numRet, vRefs, parIDs, pNames, psnLo, psnHi, proFlags );
		}
		finally {
			if ( lockObject != null )
				lockObject.unlock();
		}
	}








	private static native int nGetRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] );
	private static native int nSetRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] );
	private static native int nSetForkLength( int flags, int whichFork, int vRef, int parID, byte pName[], long newLen );


	private static native int nSendAppDocs( int whichCommand, int appPSN[], String filePaths[], int flags );
	private static native int nQuitApp( int appPSN[], int flags );
	private static native int nLaunchApp( int vRef, int parID, byte pName[], int retPSN[], int flags );
	private static native int nLaunchWithDoc( int whichCommand, int vRef, int parID, byte pName[], String filePaths[], int retPSN[], int flags );
	private static native int nCreateFullPathName( int vRef, int parID, byte pName[], String retArray[] );
	private static native int nGetOpenableFileTypes( int vRef, int creator, int numReturned[], int fileTypes[] );


/**
Called by getFileDate
@param unused no longer used, set to 0
all three dates for the file are placed in the 'datesArray' argument, in the order:
modification, creation, backup
*/

	private static native int nGetFileDate( int unused, int vRef, int parID, byte pName[], int datesArray[] );

/**
Called by xetFileDate
@param unused no longer used, set to 0
all three dates for the file should be placed in the 'datesArray' argument, in the order:
modification, creation, backup
*/

	private static native int nSetFileDate( int unused, int vRef, int parID, byte pName[], int datesArray[] );

/**
Called by getVolumeDate
@param unused no longer used, set to 0
all three dates for the volume are placed in the 'datesArray' argument, in the order:
modification, creation, backup
*/

	private static native int nGetVolumeDate( int unused, int vRef, int datesArray[] );

/**
Called by setVolumeDate
@param unused no longer used, set to 0
all three dates for the volume should be placed in the 'datesArray' argument, in the order:
modification, creation, backup
*/

	private static native int nSetVolumeDate( int unused, int vRef, int datesArray[] );




	private static native int nVerifyPSN( int appPSN[] );
	private static native int nMoveApp( int appPSN[], int selector, int flags );
	private static native int nLaunchURLDirect( String url, int flags, String preferredBrowsers[] );

	private static native int nFullPathToSpec( String fullPath, int vRefAndParID[], byte pName[] );

	private static native int nGetFinderInfo( int vRef, int parID, byte pName[], int finfo[] );
	private static native int n
	( int vRef, int parID, byte pName[], int creatorAndType[] );
	
	private static native int nVerifyFile( int vRef, int parID, byte pName[] );
	private static native int nVerifyVolume( int vRef );

	private static native int nGetDiskFileFlags( int vRef, int parID, byte pName[], int flags[] );
	private static native int nSetDiskFileFlags( int vRef, int parID, byte pName[], int flagMask, int newFlags );
	private static native int nGetDFWriteFlagsMask( int vRef, int parID, byte pName[], int masks[] );
	private static native int nGetDFReadFlagsMask( int vRef, int parID, byte pName[], int masks[] );

	private static native int nGetDiskVolumeFlags( int vRef, int flags[] );
	private static native int nSetDiskVolumeFlags( int vRef, int flagMask, int newFlags );
	private static native int nGetDVReadFlagsMask( int vRef, int masks[] );
	private static native int nGetDVWriteFlagsMask( int vRef, int masks[] );

	private static native int nGetVolumes( int maxToReturn, int numRet[], int vRefs[] );

	private static native int nUpdateContainer( int vRef, int parID, byte pName[] );

	private static native int nGetFileCategory( int vRef, int parID, byte pName[], int cat[] );

	private static native int nGetForkSizes( int vRef, int parID, byte pName[], long len[] );

	private static native int nRenameFile( int vRef, int parID, byte pName[],
														byte pOutName[], String newName );

	private static native int nRenameVolume( int vRef, String newName );

	private static native int nGetVolumeCapacity( int vRef, long cap[] );

	private static native int nGetVolumeFreeSpace( int vRef, long space[] );

	private static native int nCreateAlias( int vRef, int parID, byte pName[], String newAlias, int creator, int flags );

	private static native int nGetVolumeName( int vRef, byte pName[] );

	private static native int nResolveAlias( int inVRef, int inParID, byte pInName[],
												int outVRefAndParID[], byte pOutName[], int flags );

	private static native int nSetColorCoding( int vRef, int parID, byte pName[], int newCoding );

	private static native int nSetVolumeColorCoding( int vRef, int newCoding );

	private static native int nGetVolumeFinderInfo( int vRef, int finfo[] );

	private static native int nGetVolumeIconSuite( int vRef, int selector, int pHSuite[] );

	private static native int nGetFileIconSuite( int vRef, int parID, byte pName[], int selector, int pHSuite[] );

	private static native int nGetFTACIconSuite( int vRef, int creator, int type, int selector, int pHSuite[] );

	private static native int nPlotIcon( int which, int width, int height,
															int hSuite, int xform, int align, int pData[] );

	private static native int nCreateVolumeAlias( int targetVRef, String newAlias, int creator, int flags );

	private static native int nIterateContents( int vRef, int parID, byte pName[],
																		int dirIDArray[], int numRet[],
																		byte buffer[], int numEntries, int flags );

	private static native int nIVC( int vRef, int dirIDArray[], int numRet[],
																		byte buffer[], int numEntries, int flags );

	private static native int nGetContainer( int vRef, int parID, byte pName[],
																	int pContParID[], byte pContName[] );

	private static native int nDisposeIconSuite( int hSuite, int flags );


	private static native int nGetAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] );

	private static native int nGetMainMonitorInfo( int monitorInfo[] );



	private static native int nGetProcesses( int maxToReturn, int flags, int numRet[], int vRefs[], int parIDs[],

								byte pNames[], int psnLo[], int psnHi[], int proFlags[] );


	private static native int nSetCreatorAndType( int vRef, int parID, byte pName[], int creatorAndType[] );



/**
Calls each of the native methods with invalid arguments. Used to test if there are link problems.
*/

	static void testLink() {

		int			err;



		try {

			err=wrap_nSendAppDocs( 0, null, null, 0 );

			Trace.println( "wrap_nSendAppDocs error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nQuitApp( null, 0 );

			Trace.println( "wrap_nQuitApp error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nLaunchApp( 0, 0, null, null, 0 );

			Trace.println( "wrap_nLaunchApp error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nLaunchWithDoc( 0, 0, 0, null, null, null, 0 );

			Trace.println( "wrap_nLaunchWithDoc error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nCreateFullPathName( 0, 0, null, null );

			Trace.println( "wrap_nCreateFullPathName error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nGetOpenableFileTypes( 0, 0, null, null );

			Trace.println( "wrap_nGetOpenableFileTypes error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}







		try {

			err=wrap_nGetFileDate( 0, 0, 0, null, null );

			Trace.println( "wrap_nGetFileDate error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nSetFileDate( 0, 0, 0, null, null );

			Trace.println( "wrap_nSetFileDate error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nGetVolumeDate( 0, 0, null );

			Trace.println( "wrap_nGetVolumeDate error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nSetVolumeDate( 0, 0, null );

			Trace.println( "wrap_nSetVolumeDate error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}











		try {

			err=wrap_nVerifyPSN( null );

			Trace.println( "wrap_nVerifyPSN error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nMoveApp( null, 0, 0 );

			Trace.println( "wrap_nMoveApp error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nLaunchURLDirect( null, 0, null );

			Trace.println( "wrap_nLaunchURLDirect error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nFullPathToSpec( null, null, null );

			Trace.println( "wrap_nFullPathToSpec error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetFinderInfo( 0, 0, null, null );

			Trace.println( "wrap_nGetFinderInfo error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nSetCreatorAndType( 0, 0, null, null );

			Trace.println( "wrap_nSetCreatorAndType error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



				

		try {

			err=wrap_nVerifyFile( 0, 0, null );

			Trace.println( "wrap_nVerifyFile error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nVerifyVolume( 0 );

			Trace.println( "wrap_nVerifyVolume error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetDiskFileFlags( 0, 0, null, null );

			Trace.println( "wrap_nGetDiskFileFlags error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nSetDiskFileFlags( 0, 0, null, 0, 0 );

			Trace.println( "wrap_nSetDiskFileFlags error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetDiskVolumeFlags( 0, null );

			Trace.println( "wrap_nGetDiskVolumeFlags error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nSetDiskVolumeFlags( 0, 0, 0 );

			Trace.println( "wrap_nSetDiskVolumeFlags error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetVolumes( 0, null, null );

			Trace.println( "wrap_nGetVolumes error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nUpdateContainer( 0, 0, null );

			Trace.println( "wrap_nUpdateContainer error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetFileCategory( 0, 0, null, null );

			Trace.println( "wrap_nGetFileCategory error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetForkSizes( 0, 0, null, null );

			Trace.println( "wrap_nGetForkSizes error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nRenameFile( 0, 0, null, null, null );

			Trace.println( "wrap_nRenameFile error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nRenameVolume( 0, null );

			Trace.println( "wrap_nRenameVolume error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetVolumeCapacity( 0, null );

			Trace.println( "wrap_nGetVolumeCapacity error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetVolumeFreeSpace( 0, null );

			Trace.println( "wrap_nGetVolumeFreeSpace error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nCreateAlias( 0, 0, null, null, 0, 0 );

			Trace.println( "wrap_nCreateAlias error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetVolumeName( 0, null );

			Trace.println( "wrap_nGetVolumeName error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nResolveAlias( 0, 0, null, null, null, 0 );

			Trace.println( "wrap_nResolveAlias error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nSetColorCoding( 0, 0, null, 0 );

			Trace.println( "wrap_nSetColorCoding error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nSetVolumeColorCoding( 0, 0 );

			Trace.println( "wrap_nSetVolumeColorCoding error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetVolumeFinderInfo( 0, null );

			Trace.println( "wrap_nGetVolumeFinderInfo error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetVolumeIconSuite( 0, 0, null );

			Trace.println( "wrap_nGetVolumeIconSuite error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetFileIconSuite( 0, 0, null, 0, null );

			Trace.println( "wrap_nGetFileIconSuite error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetFTACIconSuite( 0, 0, 0, 0, null );

			Trace.println( "wrap_nGetFTACIconSuite error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nPlotIcon( 0, 0, 0, 0, 0, 0, null );

			Trace.println( "wrap_nPlotIcon error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nCreateVolumeAlias( 0, null, 0, 0 );

			Trace.println( "wrap_nCreateVolumeAlias error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nIterateContents( 0, 0, null, null, null, null, 0, 0 );

			Trace.println( "wrap_nIterateContents error value " + err );

  		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nGetContainer( 0, 0, null, null, null );

			Trace.println( "wrap_nGetContainer error value " + err );

  		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}





		try {

			err=wrap_nDisposeIconSuite( 0, 0 );

			Trace.println( "wrap_nDisposeIconSuite error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nGetDVReadFlagsMask( 0, null );

			Trace.println( "wrap_nGetDVReadFlagsMask error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nGetDVWriteFlagsMask( 0, null );

			Trace.println( "wrap_nGetDVWriteFlagsMask error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nGetDFReadFlagsMask( 0, 0, null, null );

			Trace.println( "wrap_nGetDFReadFlagsMask error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nGetDFWriteFlagsMask( 0, 0, null, null );

			Trace.println( "wrap_nGetDFWriteFlagsMask error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nIVC( 0, null, null, null, 0, 0 );

			Trace.println( "wrap_nIVC error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nGetAllMonitorInfo( null, 0, null );

			Trace.println( "wrap_nGetAllMonitorInfo error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}



		try {

			err=wrap_nGetMainMonitorInfo( null );

			Trace.println( "wrap_nGetMainMonitorInfo error value " + err );

		}

		catch ( Exception e ) {

			Trace.println( "problems: " + e );

			e.printStackTrace( Trace.getOut() );

		}

		catch ( Error er ) {

			Trace.println( "problems: " + er );

			er.printStackTrace( Trace.getOut() );

		}

	}

}



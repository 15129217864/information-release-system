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


import com.jconfig.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.io.FileNotFoundException;

/**
Represents a DiskVolume on Windows.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskVolumeMSVM implements DiskVolume {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";


	private String			driveName;

/**
's' is the drive name, i.e., 'c:\'
*/

	DiskVolumeMSVM( String s ) {
		driveName = s;
	}

/**
Returns the file system containing this object.
Calls FSCreatorMSVM.getVolumeFileSystem().
*/

	public FileSystem getFileSystem() {
		return FSCreatorMSVM.getVolumeFileSystem( driveName, 0 );
	}

/**
Returns the volume label.
*/

	public String getName() {
		String		retLabel[];
		int			theErr;

		retLabel = new String[ 1 ];

		theErr = AppUtilsMSVM.getVolumeLabel( driveName, retLabel );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		return retLabel[ 0 ];
	}

/**
Sets the volume label.
*/

	public int setName( String newLabel ) {
		return AppUtilsMSVM.setVolumeLabel( driveName, newLabel );
	}

/**
Returns the name as it would be displayed to the user.
*/

	public String getDisplayName() {
		String		displayName[];
		int			theErr;

		displayName = new String[ 1 ];

		theErr = AppUtilsMSVM.getDriveDisplayName( driveName, displayName );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		return displayName[ 0 ];
	}

/**
Returns the drive name, e.g., "c:\"
*/

	public String getPrefix() {
		return driveName;
	}

/**
Returns the drive name, e.g., "c:\"
*/

	String getDriveName() {
		return driveName;
	}

/**
Checks whether this volume is still mounted
*/

	public boolean exists() {
		File		tempFile;

		try {
			tempFile = new File( getPrefix() );
			return tempFile.exists();
		}
		catch ( Exception e ) {
			return false;
		}
	}

/**
Returns a set of binary flags associated with this object.
These flags are defined in DiskVolume.java.
Use the 'getGetFlagsMask' method to find out which bits of the returned value are significant.
*/

	public int getFlags() {
		int			theErr, flags[];

		flags = new int[ 1 ];

		theErr = AppUtilsMSVM.getVolumeFlags( driveName, flags );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return flags[ 0 ];
	}

/**
Returns a mask which indicates which bits returned by 'getFlags' are significant. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'getFlags' contains
actual information, otherwise, this information is not available.
*/

	public int getGetFlagsMask() {
		int			theErr, mask[];

		mask = new int[ 1 ];

		theErr = AppUtilsMSVM.getVolumeReadFlagsMask( driveName, mask );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return mask[ 0 ];
	}

/**
Set the indicated flags.
@param whichFlags the mask indicating which bits in newValues are significant
@param newValues contains the bits to be set/reset.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public void setFlags( int whichFlags, int newValues ) {
		throw new UnimplementedException( "not yet implemented" );
	}

/**
Returns a mask which indicates which bits in the argument to 'setFlags' can be set. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'setFlags' can be set.
@return currently, always returns 0
*/

	public int getSetFlagsMask() {
		return 0;
	}

/**
Returns a DateBundle containing the creation, modification, and backup dates of this volume.
*/

	public DateBundle getDateBundle() {
		return DateUtilsMSVM.getVolumeDateBundle( driveName );
	}

/**
Sets the set of dates associated with this volume.
*/

	public void setDateBundle( DateBundle newDates ) {
		DateUtilsMSVM.setVolumeDateBundle( driveName, newDates );
	}

	public int getColorCoding() {
		return 0;
	}

	public File getFile() {
		try {
			return new File( getPrefix() );
		}
		catch ( Exception e ) {
			return null;
		}
	}

/**
Returns the maximum length of a file name component.
*/

	public int getMaxFileNameLength() {
		int			theErr, nameLen[];

		nameLen = new int[ 1 ];

		theErr = AppUtilsMSVM.getVolumeMaxFileNameLength( driveName, nameLen );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return nameLen[ 0 ];
	}

/**
Returns the volume serial number
*/

	public long getReferenceNumber() {
		int			theErr, refNum[];

		refNum = new int[ 1 ];

		theErr = AppUtilsMSVM.getVolumeReferenceNumber( driveName, refNum );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return refNum[ 0 ];
	}

/**
Returns the max bytes this volume can hold, or 0 if an error occurs.
*/

	public long getMaxCapacity() {
		long		capInfo[];
		int			theErr;

		capInfo = new long[ AppUtilsMSVM.kVolumeCapInfoLen ];

		theErr = AppUtilsMSVM.getVolumeCapInfo( driveName, capInfo );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return capInfo[ AppUtilsMSVM.kVolumeCapInfoCapacityOffset ];
	}

/**
Returns the free space on this volume, or 0 if an error occurs.
*/

	public long getFreeSpace() {
		long		capInfo[];
		int			theErr;

		capInfo = new long[ AppUtilsMSVM.kVolumeCapInfoLen ];

		theErr = AppUtilsMSVM.getVolumeCapInfo( driveName, capInfo );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return capInfo[ AppUtilsMSVM.kVolumeCapInfoFreeSpaceOffset ];
	}

/**
Not yet implemented.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int updateContainer() {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
Returns an IconBundle for this volume.
*/

	public IconBundle getIconBundle() {
		return new IconBundleVolumeMSVM( driveName );
	}

/**
Calls the DiskFilter's visit() method with each file or folder at the top level of this volume.
*/

	public int iterate( DiskFilter filter, int flags, int maxToIterate ) {
		return FileIteratorMSVM.runDiskFilter( driveName, filter, flags, maxToIterate );
	}

/**
Always returns null.
*/

	public DiskObject getContainer() throws FileNotFoundException, DiskFileException {
		return null;
	}

/**
Used to create a file, folder or other object which will be contained by this DiskObject.
Returns the new DiskObject, or null if the object could not be created.
@param name the name of the new object
@param type either 'DO_CREATEFILE' or 'DO_CREATEDIR'
@param flags reserved; set to 0
*/

	public DiskObject createObject( String name, int type, int flags ) {
		int			whichType;

		if ( type == DO_CREATEDIR )
			whichType = DOCreatorMSVM.kCreateDiskObjectFolder;
		else if ( type == DO_CREATEFILE )
			whichType = DOCreatorMSVM.kCreateDiskObjectFile;
		else
			return null;

		return DOCreatorMSVM.createNewDiskObject( driveName, name, whichType, flags );
	}

/**
Convenience method which converts a given set of DiskVolume flags into a string representation.
*/

	private String diskVolumeFlagsToString( int f ) {
		String		ret = "";

		if ( ( f & VOL_CASE_PRESERVED ) != 0 )
			ret = ret + "<case preserved> ";

		if ( ( f & VOL_CASE_SENSITIVE ) != 0 )
			ret = ret + "<case sensitive> ";

		if ( ( f & VOL_UNICODE ) != 0 )
			ret = ret + "<Unicode> ";

		if ( ( f & VOL_FILES_COMPRESSED ) != 0 )
			ret = ret + "<files compressed> ";

		if ( ( f & VOL_VOL_COMPRESSED ) != 0 )
			ret = ret + "<volume compressed> ";

		if ( ( f & VOL_REMOVABLE ) != 0 )
			ret = ret + "<removable> ";

		if ( ( f & VOL_FIXED ) != 0 )
			ret = ret + "<fixed> ";

		if ( ( f & VOL_REMOTE ) != 0 )
			ret = ret + "<remote> ";

		if ( ( f & VOL_CDROM ) != 0 )
			ret = ret + "<CDROM> ";

		if ( ( f & VOL_RAM ) != 0 )
			ret = ret + "<RAM> ";

		if ( ( f & VOL_SYSTEM ) != 0 )
			ret = ret + "<system> ";

		return ret;
	}

	protected String getDateString() {
		DateBundle		dateBundle;

		try {
			dateBundle = getDateBundle();
			if ( dateBundle == null )
				return "getDateBundle returned null";
			else
				return dateBundle.toString();
		}
		catch ( Exception e ) {
			return "getDateBundle returned " + e;
		}
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		File			tempFile;
		String			flString;
		int				flags;

		flags = getFlags();
		tempFile = getFile();
		if ( tempFile == null )
			flString = "jFile is null";
		else
			flString = tempFile.getPath();

		ps.println( indent + "DiskVolumeMSVM " + hashCode() + ", name=" + getName() );
		ps.println( indent + "  get flags=" + diskVolumeFlagsToString( flags ) + " " + Integer.toHexString( flags ) );
		ps.println( indent + "  " + getDateString() );
		ps.println( indent + "  max capacity=" + getMaxCapacity() + ", free space=" + getFreeSpace() );
		ps.println( indent + "  file=" + flString );
	}
}





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
import java.io.FileNotFoundException;

/**
This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskVolumeNix implements DiskVolume {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private String					driveName;

	DiskVolumeNix( String s ) {
		driveName = "/";
	}

	public FileSystem getFileSystem() {
 		return FSCreatorNix.getVolumeFileSystem( driveName, 0 );
	}

	public String getName() {
		return driveName;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setName( String newLabel ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
//		return AppUtilsNix.setVolumeLabel( driveName, newLabel );
	}

	public String getDisplayName() {
		return driveName;
	}

	public String getPrefix() {
		return driveName;
	}

	String getDriveName() {
		return driveName;
	}

	public boolean exists() {
		return true;
	}

	public int getFlags() {
		int			theErr, flags[];

		flags = new int[ 1 ];

		theErr = AppUtilsNix.getVolumeFlags( driveName, flags );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return flags[ 0 ];
	}

	public int getGetFlagsMask() {
		int			theErr, mask[];

		mask = new int[ 1 ];

		theErr = AppUtilsNix.getVolumeReadFlagsMask( driveName, mask );
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

	public DateBundle getDateBundle() {
		return DateUtilsNix.getVolumeDateBundle( driveName );
	}

/**
Sets the set of dates associated with this file.
@param newDates if one or more of the dates in this bundle are null, they will not be changed.
*/

	public void setDateBundle( DateBundle newDates ) {
		DateUtilsNix.setVolumeDateBundle( driveName, newDates );
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

	public int getMaxFileNameLength() {
		int			theErr, nameLen[];

		nameLen = new int[ 1 ];

		theErr = AppUtilsNix.getVolumeMaxFileNameLength( driveName, nameLen );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return nameLen[ 0 ];
	}

	public long getReferenceNumber() {
		FileSystem			fileSystem;

 		fileSystem = FSCreatorNix.getVolumeFileSystem( driveName, 0 );
		if ( fileSystem == null )
			return 0;

		return fileSystem.getReferenceNumber();
	}

	public long getMaxCapacity() {
		long		capInfo[];
		int			theErr;

		capInfo = new long[ FSCreatorNix.FS_TOTAL_CAP_INFO_LEN ];

		theErr = FSCreatorNix.getFileSystemTotalCapInfo( capInfo );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return capInfo[ FSCreatorNix.FS_TOTAL_CAP_OFFSET ];
	}

	public long getFreeSpace() {
		long		capInfo[];
		int			theErr;

		capInfo = new long[ FSCreatorNix.FS_TOTAL_CAP_INFO_LEN ];

		theErr = FSCreatorNix.getFileSystemTotalCapInfo( capInfo );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		return capInfo[ FSCreatorNix.FS_TOTAL_FREE_SPACE_OFFSET ];
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int updateContainer() {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public IconBundle getIconBundle() {
		return new IconBundleVolumeNix( driveName );
	}

	public int iterate( DiskFilter filter, int flags, int maxToIterate ) {
		return FileIteratorNix.runDiskFilter( driveName, filter, flags, maxToIterate );
	}

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
			whichType = DOCreatorNix.kCreateDiskObjectFolder;
		else if ( type == DO_CREATEFILE )
			whichType = DOCreatorNix.kCreateDiskObjectFile;
		else
			return null;

		return DOCreatorNix.createNewDiskObject( driveName, name, whichType, flags );
	}

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
		File		tempFile;
		String		flString;
		int			flags;

		flags = getFlags();
		tempFile = getFile();
		if ( tempFile == null )
			flString = "jFile is null";
		else
			flString = tempFile.getPath();

		ps.println( indent + "DiskVolumeNix " + hashCode() + ", name=" + getName() );
		ps.println( indent + "  get flags=" + diskVolumeFlagsToString( flags ) + " " + Integer.toHexString( flags ) );
		ps.println( indent + "  " + getDateString() );
		ps.println( indent + "  max capacity=" + getMaxCapacity() + ", free space=" + getFreeSpace() );
		ps.println( indent + "  file=" + flString );
	}
}



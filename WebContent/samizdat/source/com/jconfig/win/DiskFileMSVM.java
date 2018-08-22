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
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
Represents a disk file.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskFileMSVM implements DiskFile {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private File					theFile;
	private String					thePath;
	private DiskVolume				theVolume;
	private VersionInfoMSVM			versionInfo;

	private boolean					bIsFolder, bAlreadyGotVersionInfo;


/**
Construct from a java.io.File object. The file must exist.
*/

	DiskFileMSVM( File fl ) throws FileNotFoundException, DiskFileException {
		if ( !fl.exists() )
			throw new FileNotFoundException( "file must exist " + fl.getPath() );

		thePath = AppUtilsMSVM.adjustBadMSVM1Path( fl.getPath() );
		theFile = new File( thePath );
		bIsFolder = theFile.isDirectory();
		theVolume = new DiskVolumeMSVM( AppUtilsMSVM.pathToDriveName( theFile ) );
		bAlreadyGotVersionInfo = false;
		versionInfo = null;
	}

/**
Returns the file system containing this object.
Calls FSCreatorMSVM.getFileFileSystem().
*/

	public FileSystem getFileSystem() {
		return FSCreatorMSVM.getFileFileSystem( thePath, 0 );
	}

/**
Checks whether this file or folder exists.
*/

	public boolean exists() {
		return theFile.exists();
	}

/**
Returns the name. If the file or folder does not exist, returns null.
*/

	public String getName() {
		if ( !theFile.exists() )
			return null;

		return theFile.getName();
	}

/**
Renames this file.
*/

	public int setName( String newName ) {
		File		toFile;
		String		par;

		try {
			if ( !theFile.exists() )
				return -1;

			par = AppUtilsMSVM.adjustBadMSVM1ParentPath( theFile.getParent() );

			toFile = new File( par, newName );

			theFile.renameTo( toFile );

			return 0;
		}
		catch ( Exception e ) {
			return -1;
		}
	}

/**
Returns the name as it would be displayed to the user.
*/

	public String getDisplayName() {
		return getName();
	}

/**
Returns the the short version of the file's name, if applicable
*/

	public String getShortName() {
		String			retName[] = new String[ 1 ];
		int				err;

		err = AppUtilsMSVM.getShortPathName( getFile().getAbsolutePath(), retName );
		if ( err != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getShortName=" + err );

		return retName[ 0 ];
	}

/**
Returns a set of binary flags associated with this object.
These flags are defined in DiskFile.java.
Use the 'getGetFlagsMask' method to find out which bits of the returned value are significant.
@exception OSException if an OS error occurs
*/

	public int getFlags() {
		int		err, val[];

		val = new int[ 1 ];

		err = AppUtilsMSVM.getFileAttributes( thePath, val );
		if ( err != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getFlags=" + err );

		return val[ 0 ];
	}

/**
Returns a mask which indicates which bits returned by 'getFlags' are significant. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'getFlags' contains
actual information, otherwise, this information is not available.
@exception OSException if an OS error occurs
*/

	public int getGetFlagsMask() {
		int		err, val[];

		val = new int[ 1 ];

		err = AppUtilsMSVM.getFileAttributesMask( thePath, val );

		if ( err != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getGetFlagsMask=" + err );

		return val[ 0 ];
	}

/**
Set the indicated flags.
@param whichFlags the mask indicating which bits in newValues are significant
@param newValues contains the bits to be set/reset.
@exception OSException if an OS error occurs
*/

	public void setFlags( int whichFlags, int newValues ) {
		int		err;

		err = AppUtilsMSVM.setFileAttributes( thePath, whichFlags, newValues );
		if ( err != ErrCodes.ERROR_NONE )
			throw new OSException( "can't setFlags=" + err );
	}

/**
Returns a mask which indicates which bits in the argument to 'setFlags' can be set. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'setFlags' can be set.
@exception OSException if an OS error occurs
*/

	public int getSetFlagsMask() {
		int		err, val[];

		val = new int[ 1 ];

		err = AppUtilsMSVM.getFileAttributesWriteMask( thePath, val );

		if ( err != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getSetFlagsMask=" + err );

		return val[ 0 ];
	}

/**
Returns a DateBundle containing the creation, modification, and backup dates of this file or folder.
*/

	public DateBundle getDateBundle() {
		return DateUtilsMSVM.getFileDateBundle( thePath );
	}

/**
Sets the set of dates associated with this file.
*/

	public void setDateBundle( DateBundle newDates ) {
		DateUtilsMSVM.setFileDateBundle( thePath, newDates );
	}

/**
Mac-specific. Always returns 0.
*/

	public int getColorCoding() {
		return 0;
	}

/**
Returns a copy of the java.io.File object used to create this object.
*/

	public File getFile() {
		try {
			return new File( thePath );

		}
		catch ( Exception e ) {
			return null;
		}
	}

/**
Calls the java.io.File.length() method.
*/

	public long getFileSize() {
		if ( !theFile.exists() )
			return 0;

		return theFile.length();
	}

/**
Mac-specific. Always returns 0.
*/

	public long getResourceForkSize() {
		return 0;
	}

/**
Returns the DiskVolumeMSVM containing this object.
*/

	public DiskVolume getVolume() {
		return theVolume;
	}

/**
Returns the version information for this file, if any. The version info is lazily created, and is assumed not
to change throughout the life of this object.
*/

	public VersionInfo getVersion() {
		if ( bIsFolder )
			return null;

		if ( !bAlreadyGotVersionInfo ) {
			bAlreadyGotVersionInfo = true;
			makeVersionInfo();
		}

		return versionInfo;
	}


/**
Create a VersionInfoMSVM object for this file.
*/

	private void makeVersionInfo() {

		try {

			versionInfo = new VersionInfoMSVM( thePath );

		}

		catch ( Exception e ) {

			versionInfo = null;

		}

	}


/**
Always returns null.
*/

	public int[] getPlatformData() {
		return null;
	}

/**
Mac-specific.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public FinderInfo getFinderInfo() {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
Mac-specific.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setFinderInfo( FinderInfo newFI ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int updateContainer() {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
Returns an IconBundle for this file or folder.
*/

	public IconBundle getIconBundle() {
		return new IconBundleFileMSVM( thePath );
	}

/**
Calls through to a convenience method in AppUtilsMSVM.
*/

	public int iterate( DiskFilter filter, int flags, int maxToIterate ) {
		if ( !bIsFolder )
			throw new IllegalArgumentException( "not a folder" );	//	return ErrCodes.ERROR_PARAM;

		return FileIteratorMSVM.runDiskFilter( thePath, filter, flags, maxToIterate );
	}

/**
Uses the java.io.File.getParent() method to create a DiskObject.
*/

	public DiskObject getContainer() throws FileNotFoundException, DiskFileException {
		DiskObject		dobj;
		File			fl;
		String			contString, driveName;

		try {
			contString = AppUtilsMSVM.adjustBadMSVM1ParentPath( theFile.getParent() );

			fl = new File( contString );
			if ( !fl.exists() )
				return null;

			dobj = DOCreatorMSVM.createDiskObject( fl );
		}
		catch ( Exception e ) {
			dobj = null;
		}

		return dobj;
	}

/**
Returns the full path of this file.
*/

	String getFilePath() {
		return thePath;
	}

/**
Convenience method which converts a given set of DiskFile flags into a string representation.
*/

	public String diskFileFlagsToString( int f ) {
		String		ret = "";

		if ( ( f & FILE_EXECUTABLE ) != 0 )
			ret = ret + "<exe> ";
		if ( ( f & FILE_DIR ) != 0 )
			ret = ret + "<dir> ";
		if ( ( f & FILE_HIDDEN ) != 0 )
			ret = ret + "<hidden> ";
		if ( ( f & FILE_STATIONERY ) != 0 )
			ret = ret + "<stationery> ";
		if ( ( f & FILE_NAME_LOCKED ) != 0 )
			ret = ret + "<name locked> ";
		if ( ( f & FILE_CUSTOM_ICON ) != 0 )
			ret = ret + "<custom icon> ";
		if ( ( f & FILE_HAS_BNDL ) != 0 )
			ret = ret + "<has BNDL> ";
		if ( ( f & FILE_BEEN_INITED ) != 0 )
			ret = ret + "<inited> ";
		if ( ( f & FILE_NO_INITS ) != 0 )
			ret = ret + "<no inits> ";
		if ( ( f & FILE_SHARED ) != 0 )
			ret = ret + "<shared> ";
		if ( ( f & FILE_READONLY ) != 0 )
			ret = ret + "<readonly> ";
		if ( ( f & FILE_SYSTEM ) != 0 )
			ret = ret + "<system> ";
		if ( ( f & FILE_ARCHIVE ) != 0 )
			ret = ret + "<archive> ";
		if ( ( f & FILE_DEVICE ) != 0 )
			ret = ret + "<device> ";
		if ( ( f & FILE_TEMP ) != 0 )
			ret = ret + "<temp> ";
		if ( ( f & FILE_SPARSE ) != 0 )
			ret = ret + "<sparse> ";
		if ( ( f & FILE_REPARSEPOINT ) != 0 )
			ret = ret + "<reparse> ";
		if ( ( f & FILE_COMPRESSED ) != 0 )
			ret = ret + "<cmprssd> ";
		if ( ( f & FILE_OFFLINE ) != 0 )
			ret = ret + "<offline> ";
		if ( ( f & FILE_NOT_CONTENT_INDEXED ) != 0 )
			ret = ret + "<not idxd> ";
		if ( ( f & FILE_ENCRYPTED ) != 0 )
			ret = ret + "<encrypted> ";

		return ret;
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

		if ( !bIsFolder )
			return null;

		if ( type == DO_CREATEDIR )
			whichType = DOCreatorMSVM.kCreateDiskObjectFolder;
		else if ( type == DO_CREATEFILE )
			whichType = DOCreatorMSVM.kCreateDiskObjectFile;
		else
			return null;

		return DOCreatorMSVM.createNewDiskObject( thePath, name, whichType, flags );
	}

/**
Not implemented on Windows, always returns null.
*/

	public ResourceFork getResourceFork() {
		return null;
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
		VersionInfo		vers;
		int				flags;

		flags = getFlags();
		vers = getVersion();

		ps.println( indent + "DiskFileMSVM " + hashCode() +": name=" + getName() );
		ps.println( indent + "  get flags=" + diskFileFlagsToString( flags ) + " " + Integer.toHexString( flags ) );
		ps.println( indent + "  " + getDateString() );
		ps.println( indent + "  file=" + getFile().getAbsolutePath() );
		ps.println( indent + "  file size=" + getFileSize() );

		if ( vers == null )
			ps.println( indent + "  version information not available" );
		else
			vers.dumpInfo( ps, indent + "  " );

		try {
			ps.println( indent + "  contained by " + getContainer() );
		}
		catch ( Exception e ) {
			ps.println( indent + "  can't get container: " + e );
		}
	}
}





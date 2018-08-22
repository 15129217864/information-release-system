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
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskFileNix implements DiskFile {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private File					theFile;
	private String					thePath;
	private DiskVolume				theVolume;
	private VersionInfoNix			versionInfo;

	private boolean					bIsFolder, bAlreadyGotVersionInfo;


	DiskFileNix( File fl ) throws FileNotFoundException, DiskFileException {
		if ( !fl.exists() )
			throw new FileNotFoundException( "file must exist " + fl.getPath() );

		thePath = fl.getPath();
		theFile = new File( thePath );
		bIsFolder = theFile.isDirectory();
		theVolume = new DiskVolumeNix( AppUtilsNix.pathToDriveName( theFile ) );
		bAlreadyGotVersionInfo = false;
		versionInfo = null;
	}

	public FileSystem getFileSystem() {
 		return FSCreatorNix.getFileFileSystem( thePath, 0 );
	}

	public boolean exists() {
		return theFile.exists();
	}

	public String getName() {
		if ( !theFile.exists() )
			return null;

		return theFile.getName();
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setName( String newName ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public String getDisplayName() {
		return getName();
	}

/**
Returns the the short version of the file's name, if applicable
*/

	public String getShortName() {
		return getName();
	}

	public int getFlags() {
		int		err, val[];

		val = new int[ 1 ];

		err = AppUtilsNix.getFileAttributes( thePath, val );
		if ( err != ErrCodes.ERROR_NONE )
			return 0;

		return val[ 0 ];
	}

	public int getGetFlagsMask() {
		int		err, val[];

		val = new int[ 1 ];

		err = AppUtilsNix.getFileAttributesMask( thePath, val );
		if ( err != ErrCodes.ERROR_NONE )
			return 0;

		return val[ 0 ];
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
		return DateUtilsNix.getFileDateBundle( thePath );
	}

/**
Sets the set of dates associated with this file.
*/

	public void setDateBundle( DateBundle newDates ) {
		DateUtilsNix.setFileDateBundle( thePath, newDates );
	}

	public int getColorCoding() {
		return 0;
	}

	public File getFile() {
		try {
			return new File( thePath );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	public long getFileSize() {
		if ( !theFile.exists() )
			return 0;

		return theFile.length();
	}

	public long getResourceForkSize() {
		return 0;
	}

	public DiskVolume getVolume() {
		return theVolume;
	}

	public VersionInfo getVersion() {
		if ( bIsFolder )
			return null;

		if ( !bAlreadyGotVersionInfo ) {
			bAlreadyGotVersionInfo = true;
			makeVersionInfo();
		}

		return versionInfo;
	}

	private void makeVersionInfo() {
	}

	public int[] getPlatformData() {
		return null;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public FinderInfo getFinderInfo() {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
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

	public IconBundle getIconBundle() {
		return new IconBundleFileNix( thePath, bIsFolder );
	}

	public int iterate( DiskFilter filter, int flags, int maxToIterate ) {
		if ( !bIsFolder )
			throw new IllegalArgumentException( "not a folder" );	//	return ErrCodes.ERROR_PARAM;

		return FileIteratorNix.runDiskFilter( thePath, filter, flags, maxToIterate );
	}

	public DiskObject getContainer() throws FileNotFoundException, DiskFileException {
		DiskObject		dobj;
		File			fl;

		try {
			fl = new File( theFile.getParent() );
			if ( !fl.exists() )
				return null;

			dobj = DOCreatorNix.createDiskObject( fl );
		}
		catch ( Exception e ) {
			dobj = null;
		}

		return dobj;
	}

	String getFilePath() {
		return thePath;
	}

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
			whichType = DOCreatorNix.kCreateDiskObjectFolder;
		else if ( type == DO_CREATEFILE )
			whichType = DOCreatorNix.kCreateDiskObjectFile;
		else
			return null;

		return DOCreatorNix.createNewDiskObject( thePath, name, whichType, flags );
	}

/**
Not implemented on Nix, always returns null.
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

		ps.println( indent + "DiskFileNix " + hashCode() +": name=" + getName() );
		ps.println( indent + "  get flags=" + diskFileFlagsToString( flags ) + " " + Integer.toHexString( flags ) );
		ps.println( indent + "  " + getDateString() );
		ps.println( indent + "  file=" + getFile().getPath() );
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


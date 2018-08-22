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

import com.jconfig.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

/**
Implements the DiskFile interface on the Mac. See that interface for more information.
Most of the methods call methods in AppUtilsMRJ to obtain system information using native code.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskFileMRJ implements DiskFile, IMacDiskObject {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
The volume containing this file or folder.
*/

	private DiskVolume			theVolume;

/**
Version info on the file. Only created when needed.
*/

	private VersionInfoMRJ		versionInfo;

/**
The vRefNum of this file's FSSpec.
*/

	private int					vRef;

/**
The parID of this file's FSSpec.
*/

	private int					parID;

/**
The name of this file's FSSpec.
*/

	private byte				pName[];

/**
Indicates whether this is a folder.
*/

	private boolean				bIsFolder;

/**
Used to lazily-create and cache the version info.
*/

	private boolean				bAlreadyGotVersionInfo;

/**
Create using fields similar to those in an FSSpec.
Does not check whether the file or folder exists.
The vRef must be valid, however.
@param vRef the vRefNum of the file or folder
@param parID the parID of the file or folder
@param pName the name of the file or folder, as a Pascal string
@param bIsFolder set this to indicate whether this object is a file or folder
*/

	DiskFileMRJ( int vRef, int parID, byte pName[], boolean bIsFolder )
	throws FileNotFoundException, DiskFileException {
		int			theErr;

		this.vRef = vRef;
		this.parID = parID;
		this.bIsFolder = bIsFolder;

		this.pName = new byte[ pName.length ];
		System.arraycopy( pName, 0, this.pName, 0, pName.length );
	
		theVolume = DOFactoryMRJ.createDiskVolume( vRef );

		bAlreadyGotVersionInfo = false;
		versionInfo = null;
	}

/**
Checks whether this file or folder exists.
*/

	public boolean exists() {
		int		theErr;
		
		theErr = AppUtilsMRJ.verifyFile( vRef, parID, pName );
		if ( theErr == ErrCodes.ERROR_NONE )
			return true;
		else
			return false;
	}

/**
Returns the name in quoted-printable form. If the file does not exist, returns null.
*/

	public String getName() {
		if ( !exists() )
			return null;
			
		return JUtils.pascalBytesToQPString( pName );
	}

/**
Returns the the short version of the file's name, if applicable
*/

	public String getShortName() {
		return getName();
	}

/**
Returns the file system containing this object.
Calls FSCreatorMRJ.getFileFileSystem().
*/

	public FileSystem getFileSystem() {
		return FSCreatorMRJ.getFileFileSystem( vRef, parID, pName );
	}

/**
Renames this object.
*/

	public int setName( String newName ) {
		int			theErr;
		byte			pOutName[];

		if ( newName == null || newName.length() < 1 )
			throw new IllegalArgumentException( "invalid file name " + newName );
		
		pOutName = new byte[ AppUtilsMRJ.kPNameLen ];

		theErr = AppUtilsMRJ.renameFile( vRef, parID, pName, pOutName, newName );
		if ( theErr != ErrCodes.ERROR_NONE )
			return theErr;

		pName = pOutName;

		return ErrCodes.ERROR_NONE;		
	}

/**
Returns the name of this object, as it would be displayed to the user.
*/

	public String getDisplayName() {
		return JUtils.deQuoteDePrint( getName() );
	}

/**
Returns a set of binary flags associated with this object.
These flags are defined in DiskFile.java.
Use the 'getGetFlagsMask' method to find out which bits of the returned value are significant.
*/

	public int getFlags() {
		int		theErr, flags[];

		flags = new int[ 1 ];

		theErr = AppUtilsMRJ.getDiskFileFlags( vRef, parID, pName, flags );

		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;
		else
			return flags[ 0 ];
	}

/**
Set the indicated flags.

<P>
For more information on these flags, see:
<PRE>http://developer.apple.com/technotes/tb/tb_09.html
http://developer.apple.com/techpubs/mac/Toolbox/Toolbox-455.html#MARKER-9-356</PRE>
@param whichFlags the mask indicating which bits in newValues are significant
@param newValues contains the bits to be set/reset.
@exception IllegalArgumentException if whichFlags is 0
*/

	public void setFlags( int whichFlags, int newValues ) {
		int		theErr, newFlags;

		if ( whichFlags == 0 )
			throw new IllegalArgumentException( "whichFlags==0" );	//	return ErrCodes.ERROR_PARAM;

		theErr = AppUtilsMRJ.setDiskFileFlags( vRef, parID, pName, whichFlags, newValues );

		if ( theErr != ErrCodes.ERROR_NONE )
			throw new OSException( "can't setFlags=" + theErr );
	}

/**
Returns a mask which indicates which bits returned by 'getFlags' are significant. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'getFlags' contains
actual information, otherwise, this information is not available.
*/

	public int getGetFlagsMask() {
		int		theErr, masks[];

		masks = new int[ 1 ];

		theErr = AppUtilsMRJ.getDiskFileReadFlagsMask( vRef, parID, pName, masks );

		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;
		else
			return masks[ 0 ];
	}

/**
Returns a mask which indicates which bits in the argument to 'setFlags' can be set. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'setFlags' can be set.
*/

	public int getSetFlagsMask() {
		int		theErr, masks[];

		masks = new int[ 1 ];

		theErr = AppUtilsMRJ.getDiskFileWriteFlagsMask( vRef, parID, pName, masks );

		if ( theErr != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getSetFlagsMask=" + theErr );

		return masks[ 0 ];
	}

/**
Returns a DateBundle containing the creation, modification, and backup dates of this file or folder.
*/

	public DateBundle getDateBundle() {
		return DateUtilsMRJ.getFileDateBundle( vRef, parID, pName );
	}

/**
Sets the set of dates associated with this file.
*/

	public void setDateBundle( DateBundle newDates ) {
		DateUtilsMRJ.setFileDateBundle( vRef, parID, pName, newDates );
	}

/**
Returns an IconBundle for this file or folder.
*/

	public IconBundle getIconBundle() {
		return IconBundleFactoryMRJ.createFromFile( vRef, parID, pName );
	}

/**
Not yet implemented.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setIconBundle( IconBundle bndl ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
Returns the color coding of this object.
*/

	public int getColorCoding() {
		FinderInfo		currentFI;
		int				finderFlags;

		currentFI = getFinderInfo();

		finderFlags = currentFI.getFlags();

		return ( ( finderFlags & AppUtilsMRJ.kFinderFlagsColorMask ) >> 1 );
	}

/**
Sets the color coding of this object.
*/

	public int setColorCoding( int newCoding ) {
		int		theErr, newFlags;

		if ( newCoding < 0 || newCoding > 7 )
			throw new IllegalArgumentException( "bad color coding " + newCoding );

		theErr = AppUtilsMRJ.setColorCoding( vRef, parID, pName, newCoding );
		
		return theErr;
	}

/**
Determines the full path name of this object, and returns a java.io.File object created from that path.
Returns null if an error occurs.
*/

	public File getFile() {
		String			pathName;

		pathName = AppUtilsMRJ.createFullPathName( vRef, parID, pName );
		if ( pathName == null )
			return null;
		
		try {
			return new File( "/" + pathName );
		}
		catch ( Exception e ) {
			return null;
		}
	}

/**
Returns the total length of this file.
*/

	public long getFileSize() {
		long			sizes[];
		int			theErr;
		
		sizes = new long[ AppUtilsMRJ.kForkSizesLen ];
		
		theErr = AppUtilsMRJ.getForkSizes( vRef, parID, pName, sizes );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;
		else
			return sizes[ AppUtilsMRJ.kForkSizesDataOffset ] + sizes[ AppUtilsMRJ.kForkSizesRsrcOffset ];
	}

/**
Returns the length of this file's resource fork, or 0 if an error occurs.
*/

	public long getResourceForkSize() {
		long			sizes[];
		int			theErr;
		
		sizes = new long[ AppUtilsMRJ.kForkSizesLen ];
		
		theErr = AppUtilsMRJ.getForkSizes( vRef, parID, pName, sizes );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;
		else
			return sizes[ AppUtilsMRJ.kForkSizesRsrcOffset ];
	}

/**
Returns the DiskVolume containing this object.
*/

	public DiskVolume getVolume() {
		return theVolume;
	}

/**
The file's version info, if any, is assumed not to change and only created when needed and then cached.
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
Return a two int array containing the vRef of the file in the first int and the parID in the second.
*/

	public int[] getPlatformData() {
		int		retVal[];
		
		retVal = new int[ AppUtilsMRJ.kRefPairLen ];
		
		retVal[ 0 ] = vRef;
		retVal[ 1 ] = parID;

		return retVal;
	}

/**
Returns the FinderInfo for this object.
*/

	public FinderInfo getFinderInfo() {
		int			theErr, tempInfo[];

		tempInfo = new int[ FinderInfo.FI_ARRAYLEN ];

		theErr = AppUtilsMRJ.getFinderInfo( vRef, parID, pName, tempInfo );

		//	TO DO: if ( theErr != 0 )...

		return new FinderInfo( tempInfo );
	}

/**
Sets the FinderInfo (creator and type) for this object.
*/

	public int setFinderInfo( FinderInfo newFI ) {
		int		theErr, tempArray[];

		if ( newFI == null )
			throw new IllegalArgumentException( "bad argument" );

		tempArray = new int[ FinderInfo.FI_ARRAYLEN ];
		newFI.toArray( tempArray );

		theErr = AppUtilsMRJ.setCreatorAndType( vRef, parID, pName, tempArray );

		return theErr;
	}

/**
Returns the vRefNum.
*/

	public int getVRef() {
		return vRef;
	}
	
/**
Returns the parID.
*/

	public int getParID() {
		return parID;
	}
	
/**
Returns a copy of the name as a Pascal string.
*/

	public byte[] getPName() {
		byte			retPName[];
		
		retPName = new byte[ pName.length ];
		System.arraycopy( pName, 0, retPName, 0, pName.length );

		return retPName;
	}

/**
Updates the folder containing this object.
*/

	public int updateContainer() {
		return AppUtilsMRJ.updateContainer( vRef, parID, pName );
	}

/**
Open the resource fork, and try to read the <'VERS',1> resource.
*/

	private void makeVersionInfo() {
		ResFileMRJ		resFile;
		int				theErr;
		byte				versResource[];

		versionInfo = null;

		try {
			resFile = new ResFileMRJ( vRef, parID, pName, ResFileMRJ.RESFORK_OPENEXISTING,
												ResFileMRJ.RESFORK_READONLY );
			theErr = resFile.open();
			if ( theErr != ErrCodes.ERROR_NONE )
				return;
	
			versResource = resFile.getResource( ResFileMRJ.kResTypevers, 1 );
			if ( versResource == null ) {
				resFile.close();
				return;
			}

			versionInfo = new VersionInfoMRJ( versResource, versResource.length );
			resFile.close();
		}
		catch ( Exception e ) {
			versionInfo = null;
		}
	}

/**
Call the DiskFilter's visit() method for each file/folder inside this folder.
Calls FileIteratorMRJ.iterateFolder()
@exception IllegalArgumentException if this is not a folder.
*/

	public int iterate( DiskFilter filter, int flags, int maxToIterate ) {
		if ( !bIsFolder )
			throw new IllegalArgumentException( "not a folder" );	//	return ErrCodes.ERROR_PARAM;

		return FileIteratorMRJ.iterateFolder( vRef, parID, pName, filter, flags, maxToIterate );
	}

/**
If this object is at the volume's root level, return the volume containing this object.
Otherwise, return a DiskObject representing the folder containing this object.
*/

	public DiskObject getContainer() throws FileNotFoundException, DiskFileException{
		int			theErr, pContParID[];
		byte			pContName[];

		if ( parID == 2 )
			return DOFactoryMRJ.createDiskVolume( vRef );

		pContParID = new int[ 1 ];
		pContName = new byte[ AppUtilsMRJ.kPNameLen ];
		
		theErr = AppUtilsMRJ.getContainer( vRef, parID, pName, pContParID, pContName );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;
		
		return DOFactoryMRJ.createDiskObject( vRef, pContParID[ 0 ], pContName );
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
Not yet implemented.
*/

	public DiskObject createObject( String name, int type, int flags ) {
		return null;
	}

/**
Returns the ResourceForkMRJ object for this file.
*/

	public ResourceFork getResourceFork() {
		return new ResourceForkMRJ( this );
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
		FinderInfo		currentFI;
		VersionInfo		vers;
		int				flags;

		currentFI = getFinderInfo();
		flags = getFlags();
		vers = getVersion();

		ps.println( indent + "DiskFileMRJ " + hashCode() +": vRef=" + vRef + ", parID=" + parID + ", name=" + getName() );
		ps.println( indent + "  get flags=" + diskFileFlagsToString( flags ) + " " + Integer.toHexString( flags ) );
		ps.println( indent + "  color coding=" + getColorCoding() );
		ps.println( indent + "  " + getDateString() );
		ps.println( indent + "  file=" + getFile().getPath() );
		ps.println( indent + "  file size=" + getFileSize() + ", res fork size=" + getResourceForkSize() );
		ps.println( indent + "  creator=" + JUtils.intToAscii( currentFI.getCreator() ) +
								", type=" + JUtils.intToAscii( currentFI.getFileType() ) );

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


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
import java.io.PrintStream;
import java.util.Date;
import java.io.FileNotFoundException;

/**
Represents a Mac disk drive.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskVolumeMRJ implements DiskVolume, IMacDiskObject {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private int			vRef;
	private boolean	bExists;

	DiskVolumeMRJ( int ref ) {
		vRef = ref;
	}

/**
Returns the file system containing this object.
Calls FSCreatorMRJ.getVolumeFileSystem().
*/

	public FileSystem getFileSystem() {
		return FSCreatorMRJ.getVolumeFileSystem( vRef );
	}

/**
Returns the name of this volume in quoted-printable format.
*/

	public String getName() {
		int			theErr;
		byte			pName[];
		
		pName = new byte[ AppUtilsMRJ.kPNameLen ];
		
		theErr = AppUtilsMRJ.getVolumeName( vRef, pName );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;
		
		return JUtils.pascalBytesToQPString( pName );
	}

/**
Returns the vRefNum.
*/

	public int getVRef() {
		return vRef;
	}
	
/**
Returns the parID, in this case the number 1 (fsRtParID).
*/

	public int getParID() {
		return 1;
	}
	
/**
Returns a copy of the name as a Pascal string.
*/

	public byte[] getPName() {
		byte			retPName[];
		int				theErr;
		
		retPName = new byte[ AppUtilsMRJ.kPNameLen ];
		
		theErr = AppUtilsMRJ.getVolumeName( vRef, retPName );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;
		
		return retPName;
	}

/**
Renames this volume.
*/

	public int setName( String newName ) {
		int			theErr;

		if ( newName == null || newName.length() < 1 )
			throw new IllegalArgumentException( "invalid file name " + newName );

		theErr = AppUtilsMRJ.renameVolume( vRef, newName );

		return theErr;		
	}

/**
Returns the name as it would be displayed to the user.
*/

	public String getDisplayName() {
		return JUtils.deQuoteDePrint( getName() );
	}

/**
Checks whether this volume is still mounted
*/

	public boolean exists() {
		int		theErr;
		
		theErr = AppUtilsMRJ.verifyVolume( vRef );
		if ( theErr == ErrCodes.ERROR_NONE )
			bExists = true;
		else
			bExists = false;

		return bExists;
	}

/**
Returns a set of binary flags associated with this object.
These flags are defined in DiskVolume.java.
Use the 'getGetFlagsMask' method to find out which bits of the returned value are significant.
*/

	public int getFlags() {
		int		theErr, flags[];

		flags = new int[ 1 ];

		theErr = AppUtilsMRJ.getDiskVolumeFlags( vRef, flags );

		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;
		else
			return flags[ 0 ];
	}

/**
Set the indicated flags.
@param whichFlags the mask indicating which bits in newValues are significant
@param newValues contains the bits to be set/reset.
@exception IllegalArgumentException if whichFlags is 0
@exception OSException if an OS error occurs
*/

	public void setFlags( int whichFlags, int newValues ) {
		int		theErr;

		if ( whichFlags == 0 )
			throw new IllegalArgumentException( "whichFlags==0" );	//	return ErrCodes.ERROR_PARAM;

		theErr = AppUtilsMRJ.setDiskVolumeFlags( vRef, whichFlags, newValues );

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

		theErr = AppUtilsMRJ.getDiskVolumeReadFlagsMask( vRef, masks );

		if ( theErr != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getGetFlagsMask=" + theErr );

		return masks[ 0 ];
	}

/**
Returns a mask which indicates which bits in the argument to 'setFlags' can be set. For
instance, if bit 0 of the return value of this method is set, bit 0 of 'setFlags' can be set.
*/

	public int getSetFlagsMask() {
		int		theErr, masks[];

		masks = new int[ 1 ];

		theErr = AppUtilsMRJ.getDiskVolumeWriteFlagsMask( vRef, masks );

		if ( theErr != ErrCodes.ERROR_NONE )
			throw new OSException( "can't getSetFlagsMask=" + theErr );

		return masks[ 0 ];
	}

/**
Returns a DateBundle containing the creation, modification, and backup dates of this volume.
*/

	public DateBundle getDateBundle() {
		return DateUtilsMRJ.getVolumeDateBundle( vRef );
	}

/**
Sets the set of dates associated with this volume.
*/

	public void setDateBundle( DateBundle newDates ) {
		DateUtilsMRJ.setVolumeDateBundle( vRef, newDates );
	}

/**
Returns an IconBundle for this volume.
*/

	public IconBundle getIconBundle() {
		return IconBundleFactoryMRJ.createFromVolume( vRef );
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
		int		theErr, finfo[], finderFlags;

		finfo = new int[ AppUtilsMRJ.FI_ARRAYLEN ];

		theErr = AppUtilsMRJ.getVolumeFinderInfo( vRef, finfo );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;

		finderFlags = finfo[ AppUtilsMRJ.FI_OFFS_FLAGS ];

		return ( ( finderFlags & AppUtilsMRJ.kFinderFlagsColorMask ) >> 1 );
	}

/**
Sets the color coding of this object.
*/

	public int setColorCoding( int newCoding ) {
		int		theErr;

		if ( newCoding < 0 || newCoding > 7 )
			throw new IllegalArgumentException( "bad color coding " + newCoding );

		theErr = AppUtilsMRJ.setVolumeColorCoding( vRef, newCoding );
		
		return theErr;
	}

/**
Returns a java.io.File object created from the name of this volume.
Returns null if an error occurs.
*/

	public File getFile() {
		String			prefix;
		
		prefix = getPrefix();
		if ( prefix == null )
			return null;

		try {
			return new File( prefix );
		}
		catch ( Exception e ) {
			return null;
		}
	}

/**
Returns the prefix, which can be used in constructing full path names: "/Blossom/"
*/

	public String getPrefix() {
		String		nm;
		
		nm = getName();
		if ( nm == null )
			return null;
		else
			return "/" + nm + "/";
	}

/**
Currently, always returns 31.
*/

	public int getMaxFileNameLength() {
		return 31;
	}

/**
Returns the vRefNum.
*/

	public long getReferenceNumber() {
		return vRef;
	}

/**
Returns the max bytes this volume can hold, or 0 if an error occurs.
*/

	public long getMaxCapacity() {
		long			cap[];
		int			theErr;

		cap = new long[ 1 ];

		theErr = AppUtilsMRJ.getVolumeCapacity( vRef, cap );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;
		else
			return cap[ 0 ];
	}

/**
Returns the free space on this volume, or 0 if an error occurs.
*/

	public long getFreeSpace() {
		long			space[];
		int			theErr;

		space = new long[ 1 ];

		theErr = AppUtilsMRJ.getVolumeFreeSpace( vRef, space );
		if ( theErr != ErrCodes.ERROR_NONE )
			return 0;
		else
			return space[ 0 ];
	}

/**
Not yet implemented.
*/

	public int updateContainer() {
		throw new UnimplementedException( "updateContainer not implemented" );
		//return ErrCodes.ERROR_PARAM;
	}

/**
Calls the DiskFilter's visit() method with each file or folder at the top level of this volume.
Calls FileIteratorMRJ.iterateVolume().
*/

	public int iterate( DiskFilter filter, int flags, int maxToIterate ) {
		return FileIteratorMRJ.iterateVolume( vRef, filter, flags, maxToIterate );
	}

/**
Always returns null.
*/

	public DiskObject getContainer() throws FileNotFoundException, DiskFileException {
		return null;
	}

/**
Not yet implemented; returns null.
*/

	public DiskObject createObject( String name, int type, int flags ) {
		return null;
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
		File		tempFile;
		String		flString;
		int			flags;

		flags = getFlags();
		tempFile = getFile();
		if ( tempFile == null )
			flString = "jFile is null";
		else
			flString = tempFile.getPath();

		ps.println( indent + "DiskVolumeMac " + hashCode() +": vRef=" + vRef + ", name=" + getName() );
		ps.println( indent + "  get flags=" + diskVolumeFlagsToString( flags ) + " " + Integer.toHexString( flags ) );
		ps.println( indent + "  " + getDateString() );
		ps.println( indent + "  max capacity=" + getMaxCapacity() + ", free space=" + getFreeSpace() );
		ps.println( indent + "  file=" + flString );
	}
}






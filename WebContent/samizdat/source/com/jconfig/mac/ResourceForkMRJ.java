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
import java.io.PrintStream;
import java.io.IOException;

/**
Implements the DiskFile interface on the Mac. See that interface for more information.
Most of the methods call methods in AppUtilsMRJ to obtain system information using native code.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class ResourceForkMRJ implements ResourceFork {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private DiskFile			diskFile;
	private IMacDiskObject		macDiskObject;	//diskFile casted to an IMacDiskObject
	private ResFileMRJ			resFileMRJ;

	public ResourceForkMRJ( DiskFile df ) {
		diskFile = df;
		macDiskObject = (IMacDiskObject) df;
	}

/**
Returns the DiskFile object with which this resource fork is associated.
*/

	public DiskFile getDiskFile() {
		return diskFile;
	}

/**
Returns the raw resource fork of this file. Only valid on Mac.
The resource fork length must be less than Integer.MAX_VALUE.
If there is no resource fork, or if an error occurs, returns null.
*/

	public byte[] getRawResourceFork() throws ResourceForkException {
		long				resForkSize;
		int					theErr;
		byte				retData[];
		
		resForkSize = getResourceForkSize();

		if ( resForkSize <= 0 || resForkSize >= (long) Integer.MAX_VALUE )
			throw new ResourceForkException( "resForkSize out of range=" + resForkSize );

		retData = new byte[ (int) resForkSize ];

		theErr = AppUtilsMRJ.getRawResourceFork( 0, macDiskObject.getVRef(), macDiskObject.getParID(),
													macDiskObject.getPName(), retData );
		if ( theErr == ErrCodes.ERROR_NONE )
			return retData;
		
		retData = null;
		throw new ResourceForkException( "Can't read res fork, err=" + theErr );
	}

/**
Sets the raw resource fork of this file.
@param data contains the raw resource fork. Must have length >= 1 
*/

	public void setRawResourceFork( byte data[] ) throws ResourceForkException {
		int					theErr;

		theErr = AppUtilsMRJ.setRawResourceFork( 0, macDiskObject.getVRef(), macDiskObject.getParID(),
													macDiskObject.getPName(), data );

		if ( theErr != ErrCodes.ERROR_NONE )
			throw new ResourceForkException( "Can't set res fork, data.length=" +
												( data == null ? "null" : ( "" + data.length ) ) +
												", err=" + theErr );
	}

/**
Deletes the resource fork.
*/
	public void deleteResourceFork() throws ResourceForkException {
		int					theErr;

		theErr = AppUtilsMRJ.setForkLength( 0, AppUtilsMRJ.kSetForkLengthRSRC, macDiskObject.getVRef(),
											macDiskObject.getParID(), macDiskObject.getPName(), 0L );

		if ( theErr != ErrCodes.ERROR_NONE )
			throw new ResourceForkException( "Can't delete res fork, err=" + theErr );
	}

/**
Returns the size of this resource fork.
*/

	public long getResourceForkSize() throws ResourceForkException {
		long				sizes[];
		int					theErr;
		
		sizes = new long[ AppUtilsMRJ.kForkSizesLen ];

		theErr = AppUtilsMRJ.getForkSizes( macDiskObject.getVRef(), macDiskObject.getParID(),
											macDiskObject.getPName(), sizes );

		if ( theErr != ErrCodes.ERROR_NONE )
			throw new ResourceForkException( "Can't get res fork size, err=" + theErr );
		else
			return sizes[ AppUtilsMRJ.kForkSizesRsrcOffset ];
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "ResourceForkMRJ " + hashCode() +
								": vRef=" + macDiskObject.getVRef() +
								", parID=" + macDiskObject.getParID() +
								", name=" + diskFile.getName() );
		
		try {
			ps.println( indent + "  res fork size=" + getResourceForkSize() );
		}
		catch ( ResourceForkException e ) {
			ps.println( indent + "  can't get res fork size, e=" + e );
		}
	}

	public void openResources( int mode, int perms ) throws ResourceForkException, IOException {
		int			theErr;

		if ( mode != RESFORK_OPENEXISTING || perms != RESFORK_READONLY )
			throw new IllegalArgumentException( "bad mode=" + mode + " or perms=" + perms );

		if ( resFileMRJ == null ) {
			resFileMRJ = new ResFileMRJ( macDiskObject.getVRef(), macDiskObject.getParID(),
											macDiskObject.getPName(),
											ResFileMRJ.RESFORK_OPENEXISTING, ResFileMRJ.RESFORK_READONLY );
			theErr = resFileMRJ.open();
			if ( theErr != ErrCodes.ERROR_NONE ) {
				resFileMRJ = null;
				throw new ResourceForkException( "can't open res fork, err=" + theErr );
			}
		}
	}

	public byte[] getResource( int resName, int resID ) throws ResourceForkException {
		if ( resFileMRJ == null )
			throw new IllegalArgumentException( "must call openResources() first" );

		return resFileMRJ.getResource( resName, resID );
	}

	public void closeResources() throws ResourceForkException {
		if ( resFileMRJ == null )
			throw new IllegalArgumentException( "must call openResources() first" );

		resFileMRJ.close();

		resFileMRJ = null;
	}
}


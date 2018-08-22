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

/**
Represents a Mac alias (similar to a Windows shortcut)

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DiskAliasMRJ extends DiskFileMRJ implements DiskAlias {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final int		kAliasFileTestBits = AppUtilsMRJ.kCategoryOSXAliasFileBit |
															AppUtilsMRJ.kCategoryOSXSymlinkBit;

	private static final int		kPlainFileTestBits = AppUtilsMRJ.kCategoryOSXPlainFileBit |
															AppUtilsMRJ.kCategoryOSXPackageBit |
															AppUtilsMRJ.kCategoryOSXApplicationBit |
															AppUtilsMRJ.kCategoryOSXNativeAppBit |
															AppUtilsMRJ.kCategoryOSXClassicAppBit;

/**
Create using the FSSpec of the alias.
@param vRef the vRefNum of the alias
@param parID the parID of the alias
@param pName the name of the alias, as a Pascal string
*/

	DiskAliasMRJ( int vRef, int parID, byte pName[] )
	throws FileNotFoundException, DiskFileException {
		super( vRef, parID, pName, false );
	}

/**
Returns one of the constants defined in DiskAlias.java: ALIAS_VOL, etc.
Returns ALIAS_NOLONGER if this object no longer exists, or if an error occurs.
The alias will be resolved without user interaction, and the alias will not be modified.
*/

	public int getAliasType() {
		int			theErr, pOutVRefAndParID[], cat[];
		byte			pOutName[];

		if ( !exists() )
			return ALIAS_NOLONGER;

		pOutVRefAndParID = new int[ AppUtilsMRJ.kRefPairLen ];
		pOutName = new byte[ AppUtilsMRJ.kPNameLen ];

		theErr = AppUtilsMRJ.resolveAlias( getVRef(), getParID(), getPName(),
														pOutVRefAndParID, pOutName, 1 );
		if ( theErr != ErrCodes.ERROR_NONE )
			return ALIAS_NOLONGER;

		cat = new int[ 1 ];

		theErr = AppUtilsMRJ.getFileCategory( pOutVRefAndParID[ AppUtilsMRJ.kVRefOffset ],
															pOutVRefAndParID[ AppUtilsMRJ.kParIDOffset ],
															pOutName, cat );
		if ( theErr != ErrCodes.ERROR_NONE )
			return ALIAS_NOLONGER;

		if ( ( cat[ 0 ] & AppUtilsMRJ.kCategoryOSXExtendedInfoBit ) != 0 ) {
			if ( ( cat[ 0 ] & AppUtilsMRJ.kCategoryOSXVolumeBit ) != 0 )
				return ALIAS_VOL;
			else if ( ( cat[ 0 ] & kPlainFileTestBits ) != 0 )
				return ALIAS_FILE;
			else if ( ( cat[ 0 ] & kAliasFileTestBits ) != 0 )
				return ALIAS_OTHER;
			else if ( ( cat[ 0 ] & AppUtilsMRJ.kCategoryOSXContainerBit ) != 0 )
				return ALIAS_DIR;
			else
				return ALIAS_OTHER;
		}
		else {
			switch ( cat[ 0 ] ) {
				case AppUtilsMRJ.kCategoryVolume:
					return ALIAS_VOL;

				case AppUtilsMRJ.kCategoryDirectory:
					return ALIAS_DIR;

				case AppUtilsMRJ.kCategoryFile:
					return ALIAS_FILE;

				default:
					return ALIAS_OTHER;
			}
		}
	}

	public int setFinderInfo( FinderInfo newFI ) {
		FinderInfo		currentFI;
		int				theErr;

		currentFI = getFinderInfo();
		currentFI.setCreator( newFI.getCreator() );
		
		theErr = super.setFinderInfo( currentFI );

		return theErr;
	}

/**
Always returns -1.
*/

	public int iterate( DiskFilter filter, int flags, int maxToIterate ) {
		return -1;
	}
	
	public void dumpInfo( PrintStream ps, String indent ) {
		FinderInfo		currentFI;
		String			ts;
		int				type;

		currentFI = getFinderInfo();
		type = getAliasType();
		if ( type == ALIAS_VOL ) ts = "to volume";
		else if ( type == ALIAS_DIR ) ts = "to dir";
		else if ( type == ALIAS_FILE ) ts = "to file";
		else if ( type == ALIAS_OTHER ) ts = "to other";
		else ts = "no longer alias";

		ps.println( indent + "DiskAliasMRJ: vRef=" + getVRef() + ", parID=" + getParID() + ", name=" + getName() );
		ps.println( indent + "  alias type=" + ts );
		ps.println( indent + "  get flags=" + diskFileFlagsToString( getFlags() ) );
		ps.println( indent + "  color coding=" + getColorCoding() );
		ps.println( indent + "  " + getDateString() );
		ps.println( indent + "  file=" + getFile().getPath() );
		ps.println( indent + "  creator=" + JUtils.intToAscii( currentFI.getCreator() ) +
								", type=" + JUtils.intToAscii( currentFI.getFileType() ) );

		try {
			ps.println( indent + "  contained by " + getContainer() );
		}
		catch ( Exception e ) {
			ps.println( indent + "  can't get container: " + e );
		}
	}
}


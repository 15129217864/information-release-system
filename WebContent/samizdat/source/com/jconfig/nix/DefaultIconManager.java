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

/**
This is a preliminary class which manages the disk object icons on Unix.

<P>
These icons are stored in a seris of 8-bit uncompressed Windows .bmp files: 'filel.bmp', etc.
This class is created with the directory containing these files; it read each file, and converts
it into an array of ints, stored in Java's ARGB format.

<P>
The 'getIcon()' method copies these ARGB values into the given int array for the indicated
icon.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class DefaultIconManager {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	static final int	kLargeFile = 0;
	static final int	kLargeDir = 1;
	static final int	kLargeVolume = 2;
	static final int	kSmallFile = 3;
	static final int	kSmallDir = 4;
	static final int	kSmallVolume = 5;

	private static final int		kLargeW = 32;
	private static final int		kLargeH = 32;
	private static final int		kSmallW = 16;
	private static final int		kSmallH = 16;

/**
File containing the large file icon.
*/

	private static final String		kLargeFileName = "filel.bmp";

/**
File containing the large folder icon.
*/

	private static final String		kLargeFolderName = "folderl.bmp";

/**
File containing the large volume icon.
*/

	private static final String		kLargeVolumeName = "volumel.bmp";

/**
File containing the small file icon.
*/

	private static final String		kSmallFileName = "files.bmp";

/**
File containing the small folder icon.
*/

	private static final String		kSmallFolderName = "folders.bmp";

/**
File containing the small volume icon.
*/

	private static final String		kSmallVolumeName = "volumes.bmp";

	private		int					pixLargeFile[], pixLargeFolder[], pixLargeVolume[],
									pixSmallFile[], pixSmallFolder[], pixSmallVolume[];

/**
Construct using a File object which indicates the directory containing the BMP files.
*/

	DefaultIconManager( File dir ) {
		pixLargeFile = new int[ kLargeW * kLargeH ];
		pixLargeFolder = new int[ kLargeW * kLargeH ];
		pixLargeVolume = new int[ kLargeW * kLargeH ];

		pixSmallFile = new int[ kSmallW * kSmallH ];
		pixSmallFolder = new int[ kSmallW * kSmallH ];
		pixSmallVolume = new int[ kSmallW * kSmallH ];

		init( dir, kLargeFileName, kLargeW, kLargeH, pixLargeFile );
		init( dir, kLargeFolderName, kLargeW, kLargeH, pixLargeFolder );
		init( dir, kLargeVolumeName, kLargeW, kLargeH, pixLargeVolume );
		init( dir, kSmallFileName, kSmallW, kSmallH, pixSmallFile );
		init( dir, kSmallFolderName, kSmallW, kSmallH, pixSmallFolder );
		init( dir, kSmallVolumeName, kSmallW, kSmallH, pixSmallVolume );
	}

	boolean init( File dir, String fileName, int w, int h, int pData[] ) {
		File		tempFile;

		try {
			tempFile = new File( dir, fileName );
			return BMPReader.readBMPFile( tempFile, w, h, pData );
		}
		catch ( Exception e ) {
			return false;
		}
	}

	int getIcon( int type, int pData[] ) {
		switch ( type ) {
			case kLargeFile:
				System.arraycopy( pixLargeFile, 0, pData, 0, kLargeW * kLargeH );
			break;
			case kLargeDir:
				System.arraycopy( pixLargeFolder, 0, pData, 0, kLargeW * kLargeH );
			break;
			case kLargeVolume:
				System.arraycopy( pixLargeVolume, 0, pData, 0, kLargeW * kLargeH );
			break;
			case kSmallFile:
				System.arraycopy( pixSmallFile, 0, pData, 0, kSmallW * kSmallH );
			break;
			case kSmallDir:
				System.arraycopy( pixSmallFolder, 0, pData, 0, kSmallW * kSmallH );
			break;
			case kSmallVolume:
				System.arraycopy( pixSmallVolume, 0, pData, 0, kSmallW * kSmallH );
			break;
			default:
				throw new IllegalArgumentException( "bad type=" + type );	//	return ErrCodes.ERROR_PARAM;
		}

		return ErrCodes.ERROR_NONE;
	}
}


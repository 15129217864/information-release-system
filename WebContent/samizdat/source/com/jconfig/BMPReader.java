/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig;

import java.io.*;

/**
This class contains a static method used to convert an 8-bit, uncompressed Windows .bmp file
into Java's ARGB format.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class BMPReader {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final int BI_RGB = 0;

	private static final int kOffsType = 0;
	private static final int kOffsFileSize = 2;
	private static final int kOffsBitmapOffset = 10;
	private static final int kOffsBIHSize = 14;
	private static final int kOffsWidth = 18;
	private static final int kOffsHeight = 22;
	private static final int kOffsBitCount = 28;
	private static final int kOffsCompression = 30;
	private static final int kOffsSizeImage = 34;
	private static final int kOffsColorUsed = 46;
	private static final int kOffsColorImp = 50;
	private static final int kOffsEndHeader = 54;

	private BMPReader() {
	}

/**
Puts an 8-bit, uncompressed Windows .bmp file into Java's ARGB format.
Returns true if the file was read OK, false otherwise.
@param bmpFile the .bmp file
@param destWidth the width of the bitmap. You must know this in advance.
@param destHeight the height of the bitmap. You must know this in advance.
@param destPixels the pixels will be placed in this array.
It must have at least destWidth * destHeight elements.
The top left pixel will be placed at destPixels[ 0 ], and so on.
*/

	public static final boolean readBMPFile( File bmpFile, int destWidth, int destHeight, int destPixels[] ) {
		int		fileSize, bitmapOffset, bitmapInfoHeaderSize, width, height,
				bitCount, compressionType, imageSize, colorsUsed,
				colorsImportant,
				colorTable[],
				offs, i, numToRead;
		byte	buf[];
		FileInputStream		fis;

		try {
			numToRead = (int) bmpFile.length();

			buf = new byte[ numToRead ];
			fis = new FileInputStream( bmpFile );
			fis.read( buf );
			fis.close();

			if ( buf[ kOffsType ] != 'B' || buf[ kOffsType + 1 ] != 'M' )
				return false;

			fileSize = JUtils.bytesToIntSwap( buf, kOffsFileSize );
			bitmapOffset = JUtils.bytesToIntSwap( buf, kOffsBitmapOffset );

			bitmapInfoHeaderSize = JUtils.bytesToIntSwap( buf, kOffsBIHSize );
			width = JUtils.bytesToIntSwap( buf, kOffsWidth );
			height = JUtils.bytesToIntSwap( buf, kOffsHeight );
			bitCount = JUtils.bytesToShortSwap( buf, kOffsBitCount );
			compressionType = JUtils.bytesToIntSwap( buf, kOffsCompression );
			imageSize = JUtils.bytesToIntSwap( buf, kOffsSizeImage );
			colorsUsed = JUtils.bytesToIntSwap( buf, kOffsColorUsed );
			colorsImportant = JUtils.bytesToIntSwap( buf, kOffsColorImp );

			if ( compressionType != BI_RGB || bitCount != 8 || width != destWidth || height != destHeight )
				return false;

			if ( colorsUsed == 0 )
				colorsUsed = 1 << bitCount;

			colorTable = new int[colorsUsed];

				// Read the bitmap's color table
			for ( i = 0, offs = kOffsEndHeader; i < colorsUsed; i++ ) {
				int		anInt;

				anInt = JUtils.bytesToIntSwap( buf, offs ) & 0x00ffffff;
				offs += 4;
				if ( anInt == 0x00ffffff )
					colorTable[ i ] = 0;
				else
					colorTable[ i ] = anInt | 0xff000000;
			}				

			translatePixels( width, height, colorTable, destPixels, buf, offs );

			return true;
		}
		catch ( Exception e ) {
		}

		return false;
	}

/**
Translate 8-bit palette indexes into ARGB ints
*/

	private static final void translatePixels( int width, int height, int colorTable[], int pixels[], byte in[], int offs )
	{
		int		h, w, pos, index, roundedWidth;

		roundedWidth = ( width + 3 ) & 0xFFFFFFFC;
		for ( h = height - 1; h >= 0; h-- ) {
			pos = h * width;
			for ( w = 0; w < width; w++ )
				pixels[ pos++ ] = colorTable[ 0xFF & in[ offs++ ] ];
			offs += roundedWidth - width;
		}
	}
}



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
import java.util.*;
import java.net.*;
import java.awt.*;

/**
Contains various utility routines.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class JUtils {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final char		hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', 
																	'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private JUtils() {
	}

/**
Reads a text file, and returns an array containing each line of the file. Stops at the end of the file,
or if a blank line is found.
*/

	public static String[] fileToStringArray( File fl ) {
		DataInputStream		dis;
		Vector				lines;
		String				tempStr, retArray[];
		int					i;

		lines = new Vector( 10, 10 );

		try {
			dis = new DataInputStream( new FileInputStream( fl ) );

			for ( i = 0; i < 1000; i++ ) {		
				tempStr = dis.readLine();
				if ( tempStr == null || tempStr == "" )
					break;
				lines.addElement( tempStr );
			}

			dis.close();
		}
		catch ( Exception e ) {
			return null;
		}
		
		if ( lines.size() < 1 )
			return null;
		
		retArray = new String[ lines.size() ];
		lines.copyInto( retArray );
		
		return retArray;
	}

/**
Display a FileDialog and return a full path name. Returns null if the user chooses 'Cancel'.
*/

	public static String getFileNameFromUser() {
		FileDialog		fd;
		String			dir;
		
		fd = new FileDialog( new Frame(), "Choose a File...", FileDialog.LOAD );
		fd.setDirectory("");
		fd.setFile("");
		fd.show();

		dir = fd.getDirectory();
		if ( dir == null )
			return null;

		if ( fd.getFile() == null )
			return null;

		File f = new File( dir, fd.getFile() );
		return f.getAbsolutePath();

		//if ( !dir.endsWith( File.separator ) )
		//	return dir + File.separator + fd.getFile();
		//else
		//	return dir + fd.getFile();
	}

/**
Convert a RawDate into a java.util.Date.  If the year is less than 1901, it will be set to 1970.
If rd is null, returns null.
*/

	public static final Date rawDateToJDate( RawDate rd ) {
		int			year;

		if ( rd == null )
			return null;

		year = rd.getYear();
		if ( year < 1901 )
			year = 1970;

		return new java.util.Date( year - 1900, rd.getMonth() - 1, rd.getDay(), rd.getHour(), rd.getMinute(), rd.getSecond() );
	}

/**
Convert two bytes of a byte array into a short. Big-endian.
@param offset the offset at which to start
*/

	public static final short bytesToShort( byte buf[], int offset ) {
		short		s1, s2;

		s1 = (short) ( 0xFF & buf[ offset ] );
		s2 = (short) ( 0xFF & buf[ offset + 1 ] );
		
		return (short) ( ( s1 << 8 ) | s2 );
	}
		
/**
Convert two bytes of a byte array into a short. Little-endian.
@param offset the offset at which to start
*/

	public static final short bytesToShortSwap( byte buf[], int offs ) {
		short		byte0, byte1, shorto;

		byte0 = (short) ( (short) buf[ offs ] & (short) 0xFF );
		byte1 = (short) ( (short) buf[ offs + 1 ] & (short) 0xFF );
		byte1 <<= 8;

		return (short) ( (short) byte0 | (short) byte1 );
	}

/**
Convert four bytes of a byte array into an integer. Big-endian.
@param offset the offset at which to start
*/

	public static final int bytesToInt( byte buf[], int offset ) {
		int		i1, i2, i3, i4;

		i1 = 0xFF & buf[ offset ];
		i2 = 0xFF & buf[ offset + 1 ];
		i3 = 0xFF & buf[ offset + 2 ];
		i4 = 0xFF & buf[ offset + 3 ];
		
		return ( ( i1 << 24 ) | ( i2 << 16 ) | ( i3 << 8 ) | i4 );
	}

/**
Convert four bytes of a byte array into an integer. Little-endian.
@param offset the offset at which to start
*/

	public static final int bytesToIntSwap( byte buf[], int offs ) {
		int		byte0, byte1, byte2, byte3;

		byte0 = (int) ( buf[ offs ] & 0xFF );
		byte1 = (int) ( buf[ offs + 1 ] & 0xFF );
		byte2 = (int) ( buf[ offs + 2 ] & 0xFF );
		byte3 = (int) ( buf[ offs + 3 ] & 0xFF );
		byte1 <<= 8;
		byte2 <<= 16;
		byte3 <<= 24;

		return byte0 | byte1 | byte2 | byte3;
	}

	public static final long intsToLong( int hi, int lo ) {
		long			h, l;

		h = hi;
		l = lo;

		return ( h << 32 ) | ( l & 0xFFFFFFFF );
	}

/**
Converts an ASCII string into an integer. The string must contain exactly 4 characters,
e.g, "TEXT".
*/

	public static final int asciiToInt( String s ) {
		int		i0, i1, i2, i3;

		if ( s == null || s.length() != 4 )
			return 0;

		i0 = (int) ( ( (int) s.charAt(3) ) & 0x000000FF );
		i1 = (int) ( ( (int) s.charAt(2) ) & 0x000000FF );
		i2 = (int) ( ( (int) s.charAt(1) ) & 0x000000FF );
		i3 = (int) ( ( (int) s.charAt(0) ) & 0x000000FF );

		i1 <<= 8;
		i2 <<= 16;
		i3 <<= 24;

		return i0 | i1 | i2 | i3;
	}

/**
Convert an integer into a four character string. If any characters would be zero,
they are replaced with spaces.
*/

	public static final String intToAscii( int n ) {
		byte		temp[] = new byte[ 4 ];
		int			i;

		temp[ 3 ] = (byte) ( n & 0x000000FF );
		temp[ 2 ] = (byte) ( ( n >> 8 ) & 0x000000FF );
		temp[ 1 ] = (byte) ( ( n >> 16 ) & 0x000000FF );
		temp[ 0 ] = (byte) ( ( n >> 24 ) & 0x000000FF );
		
		for ( i = 0; i < 4; i++ )
			if ( temp[ i ] == 0 )
				temp[ i ] = 32;

		return new String( temp, 0 );
	}

/**
Convert a java string into a Pascal string, and put it at the indicated position of a byte
array.
*/
	
	public static final void stringToPascalBytes( String s, byte[] record, int offset ) {
		int			len;

		if ( record == null || record.length < 1 || offset >= record.length )
			return;
		
		record[ 0 ] = 0;

		len = s.length();

		if ( len + offset >= record.length )
			len = record.length - offset - 1;

		if ( len <= 0 )
			return;
		
		s.getBytes( 0, len, record, 1 );
		record[ 0 ] = (byte) len;
	}

/**
Read a pascal-style string from a byte array.
*/
	
	public static final String pascalBytesToString( byte[] record, int offset ) {
		int			len;

		try {
			len = record[ offset ] & 0xFF;
			
			if ( len + offset >= record.length )
				len = record.length - offset - 1;

			return new String( record, 0, offset + 1, len );
		}
		catch ( Exception e ) {
			return "";
		}
	}

/**
Convert a Pascal string in a byte array into a quoted-printable String.
*/

	public static final String pascalBytesToQPString( byte[] record ) {
		StringBuffer		sb;
		int					len, i;
		char					c;

		try {
			len = record[ 0 ] & 0xFF;
			
			if ( len >= record.length )
				len = record.length - 1;

			sb = new StringBuffer( len );
			
			for ( i = 1; i <= len; i++ ) {
				c = (char) ( record[ i ] & 0xFF );

				if ( c <= ' ' || c > 0x7F || c == '%' || c == '/' ) {
					sb.append( '%' );
					sb.append( hexDigits[ c >> 4 ] );
					sb.append( hexDigits[ c & 0x0F ] );
				}
				else
					sb.append( c );
			}

			return sb.toString();
		}
		catch ( Exception e ) {
			return "";
		}
	}

/**
Convert a java-style file name into a Win-style file name.
*/

	public static final String javaPathToWinPath( String javaPath ) {
		return javaPath;
	}

/**
Convert a quoted-printable String into a regular String.
*/

	public static String deQuoteDePrint( String srcStr ) {
		StringBuffer		destBuf;
		int					srcI, destI, srcLen, num, num1, num2;
		char				cc, cc1, cc2;

		srcI = destI = 0;
		destBuf = new StringBuffer( srcStr );
		srcLen = srcStr.length();
		
		while ( srcI < srcLen ) {
			cc = srcStr.charAt( srcI );
			if ( cc == '%' ) {
				if ( srcI + 2 < srcLen ) {
					cc1 = srcStr.charAt( srcI + 1 );
					cc2 = srcStr.charAt( srcI + 2 );
		
					num1 = getHexValue( cc1 );
					num2 = getHexValue( cc2 );
					if ( num1 < 0 || num2 < 0 ) {
						destBuf.setCharAt( destI, cc );
						++destI;
						++srcI;
					}
					else {
						num = 16 * num1 + num2;
						destBuf.setCharAt( destI, (char) num );
						++destI;
						srcI += 3;
					}
				}
				else {
					destBuf.setCharAt( destI, cc );
					++destI;
					++srcI;
				}
			}
			else {
				destBuf.setCharAt( destI, cc );
				++destI;
				++srcI;
			}
		}
		
			destBuf.setLength( destI );
			return new String( destBuf );
	}

	static final int getHexValue( char cc ) {
		if ( cc >= 'a' && cc <= 'f' )
			return 10 + cc - 'a';
		else if ( cc >= 'A' && cc <= 'F' )
			return 10 + cc - 'A';
		else if ( cc >= '0' && cc <= '9' )
			return cc - '0';
		else
			return -1;
	}

	static public final String enQP( byte in[] ) {
		StringBuffer		out;
		int					len, i, tempInt;
		char				cc;

		if ( in == null )
			return null;

		len = in.length;
		if ( len < 1 )
			return "";

		out = new StringBuffer( len );

		for ( i = 0; i < len; i++ ) {
			cc = (char) in[ i ];

			if ( cc <= ' ' || cc > 0x7F || cc == '%' ) {
				tempInt = (int) in[ i ];
				out.append( '%' );
				out.append( getHexChar( ( tempInt >> 4 ) & 0x0F ) );
				out.append( getHexChar( tempInt & 0x0F ) );
			}
			else {
				out.append( cc );
			}
		}

		return out.toString();
	}

	static public final String enQP( String in ) {
		StringBuffer		out;
		int					len, i, tempInt;
		char				cc;

		if ( in == null )
			return null;

		len = in.length();
		if ( len < 1 )
			return "";

		out = new StringBuffer( len );

		for ( i = 0; i < len; i++ ) {
			cc = in.charAt( i );

			if ( cc <= ' ' || cc > 0x7F || cc == '%' ) {
				tempInt = (int) cc;
				out.append( '%' );
				out.append( getHexChar( ( tempInt >> 4 ) & 0x0F ) );
				out.append( getHexChar( tempInt & 0x0F ) );
			}
			else {
				out.append( cc );
			}
		}

		return out.toString();
	}

	static private final char getHexChar( int which ) {
		if ( which < 0 || which > 15 )
			return '0';

		return hexDigits[ which ];
	}
}



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
import java.util.Vector;

/**
A singleton used to deal with command lines.

<P>
findVerbs() gets the VATs of an application.

<P>
createCommandLine() creates a command line from a command line template
and a series of arguments.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class CommandLineUtilsMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
See the 'findVerbs' method.
*/

	private static final int		kMaxReturns = 50;

/**
Used by 'splitCommandLine'
*/

	private static final String noQ[] = { "%1", "%2", "%3", "%4", "%5", "%6", "%7", "%8", "%9" };

/**
Used by 'splitCommandLine'
*/

	private static final String withQ[] = { "\"%1\"", "\"%2\"", "\"%3\"", "\"%4\"", "\"%5\"",
												"%6\"", "\"%7\"", "\"%8\"", "\"%9\"" };
/**
Find the VATs of an array of files.
See 'RegCommandMSVM.java' for a description of the VAT format.
@param fullPaths the full paths of the application "c:\windows\notepad.exe"
@return each object in the return Vector is an array of RegCommandMSVM objects,
in the same order as the input file names*/

	static Vector findVerbs( String fullPaths[] ) {
		Vector				retVal;
		String				qs[];
		int					i, numFullPaths, theErr, numRetArray[];

		numFullPaths = fullPaths.length;

		qs = new String[ 4 * numFullPaths * kMaxReturns ];

		numRetArray = new int[ numFullPaths ];

		theErr = AppUtilsMSVM.findVerbs( fullPaths, "unused", kMaxReturns, numRetArray, qs );
		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		retVal = new Vector( numFullPaths );

		for ( i = 0; i < numFullPaths; i++ )
			retVal.addElement( makeRegCommandArray( qs, 4 * i * kMaxReturns, numRetArray[ i ] ) );

		return retVal;
	}

/**
Find the VATs of a given file.
See 'RegCommandMSVM.java' for a description of the VAT format.
@param fullPath the full path of the application "c:\windows\notepad.exe"
@param fileName the name of the app "notepad.exe"
*/

	static RegCommandMSVM[] findVerbs( String fullPath, String fileName ) {
		String				qs[], fullPathArray[];
		int					i, vatCount, theErr, numRetArray[];

		qs = new String[ 4 * kMaxReturns ];

		fullPathArray = new String[ 1 ];
		numRetArray = new int[ 1 ];

		fullPathArray[ 0 ] = fullPath;

		theErr = AppUtilsMSVM.findVerbs( fullPathArray, fileName, kMaxReturns, numRetArray, qs );

		if ( theErr != ErrCodes.ERROR_NONE )
			return null;

		return makeRegCommandArray( qs, 0, numRetArray[ 0 ] );
	}

	private static RegCommandMSVM[] makeRegCommandArray( String qs[], int startIndex, int numVats ) {
		RegCommandMSVM		retArray[];
		int					i, vatCount;

		if ( numVats < 1 )
			return null;

		retArray = new RegCommandMSVM[ numVats ];

		for ( i = startIndex, vatCount = 0; vatCount < numVats; vatCount++, i += 4 )
			retArray[ vatCount ] = new RegCommandMSVM( qs[ i ], qs[ i + 1 ], qs[ i + 2 ], qs[ i + 3 ] );

		return retArray;
	}

/**
Create a command line from a Registry-style command line template and a Vector of arguments
Each argument placeholder ("%1", etc.) will be replaced with one of the arguments
Unused placeholders will be removed
Excess arguments will be added after the last placeholder, or at the end of the command line
Arguments which contains spaces will be surrounded with quotes.

@param template the command line template, e.g., "c:\windows\notepad.exe %1 /p %2"
@param argVector contains the list of arguments, may have zero or more elements
*/

	static String createCommandLine( String template, Vector argVector ) {
		Vector			splits;

		splits = new Vector( 10, 10 );

		splitCommandLine( template, 0, splits );

		return meldCommandLine( splits, argVector );
	}

/**
Splits a command line into one or more segments. Each split occurs at the location of an argument
placeholder: %1 or "%1", %2 or "%2", etc. Each segment is appended to 'vec'

<P>
After calling this method, call 'meldCommandLine' with a Vector containing the arguments, which
will be melded into the command line.

<PRE>
For instance, "c:\windows\notepad.exe %1 -d %2" will be split into three strings:
	"c:\windows\notepad.exe "
	" -d "
	""
</PRE>
*/

	private static void splitCommandLine( String s, int argNum, Vector vec ) {
		int			pos;

		pos = s.indexOf( withQ[ argNum ] );
		if ( pos >= 0 ) {
			vec.addElement( s.substring( 0, pos ) );
			splitCommandLine( s.substring( pos + withQ[ argNum ].length(), s.length() ), argNum + 1, vec );
			return;
		}

		pos = s.indexOf( noQ[ argNum ] );
		if ( pos >= 0 ) {
			vec.addElement( s.substring( 0, pos ) );
			splitCommandLine( s.substring( pos + noQ[ argNum ].length(), s.length() ), argNum + 1, vec );
			return;
		}

		vec.addElement( s );
	}
	
/**
'splits' contains the vector of command line segments from 'splitCommandLine'
fold these together with the command line arguments in 'args'
*/

	private static String meldCommandLine( Vector splits, Vector args ) {
		String		retStr;

		if ( splits == null || args == null || splits.size() < 1 )
			return "";

		retStr = popOneSplit( splits, "" );

		while ( true ) {
			if ( splits.size() < 2 ) {
				retStr = popAllArgs( args, retStr );
				retStr = popAllSplits( splits, retStr );

				return retStr;
			}

			if ( args.size() < 1 ) {
				retStr = popAllSplits( splits, retStr );

				return retStr;
			}

			retStr = popOneArg( args, retStr );
			retStr = popOneSplit( splits, retStr );
		}
	}

	private static String popOneSplit( Vector splits, String s ) {
		String		temp;

		if ( splits == null || splits.size() < 1 )
			return s;

		temp = (String) splits.elementAt( 0 );
		splits.removeElementAt( 0 );

		return s + temp;
	}

	private static String popAllSplits( Vector splits, String s ) {
		if ( splits == null || splits.size() < 1 )
			return s;

		while ( splits.size() > 0 )
			s = popOneSplit( splits, s );

		return s;
	}

	private static String popOneArg( Vector args, String s ) {
		String		temp;

		if ( args == null || args.size() < 1 )
			return s;

		temp = (String) args.elementAt( 0 );
		args.removeElementAt( 0 );

		return s + " " + addQuotesToLongFileNames( temp );
	}

	private static String popAllArgs( Vector args, String s ) {
		if ( args == null || args.size() < 1 )
			return s;

		while ( args.size() > 0 )
			s = popOneArg( args, s );

		return s;
	}

	private static String addQuotesToLongFileNames( String s ) {
		if ( s == null || s.length() < 1 )
			return "";

		if ( s.indexOf( " " ) < 0 )
			return s;
		else
			return "\"" + s + "\"";
	}

	private CommandLineUtilsMSVM() {}
}



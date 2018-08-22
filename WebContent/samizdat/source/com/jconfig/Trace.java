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
Used to write debugging messages.

<P>
This class maintains a PrintStream object; the println() method writes a line of text to this stream, and the getOut()
method returns the PrintStream object.

<P>
Initially, the PrintStream object is null, meaning that all output will be discarded. If the PrintStream object is
null, the 'getOut' method will return a PrintStream which discards all input.

<P>
Using the setDestination() method, you can indicate that output should be:

<UL>
<LI>sent to System.out
<LI>sent to a PrintStream you supply using the setOut() method
<LI>discarded
</UL>

<P>
For example, if you want to direct all calls to Trace.println() to System.out, use:

<PRE>
	Trace.setDestination( Trace.TRACE_SYSOUT );
</PRE>

If you want to direct all calls to Trace.println() to a file, use:

<PRE>
	PrintStream		myStream;

	myStream = new PrintStream( new FileOutputStream( new File( curDir, "myfile.txt" ) ) );

	Trace.setOut( outStream );
	Trace.setDestination( Trace.TRACE_FILE );
</PRE>

<P>
The setDestination() method takes precedence over the setOut() method; that is, if you call setOut() with a PrintStream,
followed by setDestination( Trace.TRACE_NULL ), all output will be discarded. However, the PrintStream you passed to setOut()
will be saved, so if you subsequently call setDestination( Trace.TRACE_FILE ), output will be sent to the PrintStream.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class Trace {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
See the setDestination() method
*/

	public static final int		TRACE_SYSOUT = 1;

/**
See the setDestination() method
*/

	public static final int		TRACE_FILE = 2;

/**
See the setDestination() method
*/

	public static final int		TRACE_NULL = 3;

	private static PrintStream		out = null;
	private static PrintStream		nullOutput = new PrintStream( new NullOutputStream() );
	private static int				destination = TRACE_NULL;

	public static void setDestination( int d ) {
		if ( !( d == TRACE_SYSOUT || d == TRACE_FILE || d == TRACE_NULL ) )
			d = TRACE_NULL;

		destination = d;
	}

/**
Print the given string to the current PrintStream.
*/

	public static void println( String s ) {
		switch ( destination ) {
			case TRACE_SYSOUT:
				System.out.println( s );
			break;

			case TRACE_FILE:
				if ( out != null )
					out.println( s );
			break;

			default:
			break;
		}
	}

/**
Sets the PrintStream to which output of the 'println' method will be sent.
@param o the new PrintStream object. If this is null, any data sent to the 'println' method will be discarded,
and the 'getOut' method will return a PrintStream object which discards all input.
*/

	public static void setOut( PrintStream o ) {
		out = o;
	}

/**
Returns the current PrintStream object.
*/

	public static PrintStream getOut() {
		switch ( destination ) {
			case TRACE_SYSOUT:
				return System.out;

			case TRACE_FILE:
				if ( out != null )
					return out;
				else
					return nullOutput;

			default:
				return nullOutput;
		}
	}

	private Trace() {}
}


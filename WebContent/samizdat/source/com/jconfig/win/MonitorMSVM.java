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
import java.awt.Rectangle;
import java.awt.Dimension;
import java.io.PrintStream;

/**
Represents a video monitor on Windows. See the Monitor interface for a description of each method.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class MonitorMSVM implements Monitor {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private Rectangle		boundsRect, workareaRect;
	private int				depth, refNum;
	private boolean			bIsMainMonitor;

/**
Offset of the top of the global bounds of the monitor.
*/

	private static final int		kOffsBoundsTop = 0;

/**
Offset of the left of the global bounds of the monitor.
*/

	private static final int		kOffsBoundsLeft = 1;

/**
Offset of the bottom of the global bounds of the monitor.
*/

	private static final int		kOffsBoundsBottom = 2;

/**
Offset of the right of the global bounds of the monitor.
*/

	private static final int		kOffsBoundsRight = 3;

/**
Offset of the top of the global work area of the monitor.
*/

	private static final int		kOffsWorkareaTop = 4;

/**
Offset of the left of the global work area of the monitor.
*/

	private static final int		kOffsWorkareaLeft = 5;

/**
Offset of the bottom of the global work area of the monitor.
*/

	private static final int		kOffsWorkareaBottom = 6;

/**
Offset of the right of the global work area of the monitor.
*/

	private static final int		kOffsWorkareaRight = 7;

/**
Offset of the depth of the monitor.
*/

	private static final int		kOffsDepth = 8;

/**
Offset of a boolean indicating whether this is the main monitor.
*/

	private static final int		kOffsIsMainMonitor = 9;

/**
Offset of the reference number of the monitor.
*/

	private static final int		kOffsRefNum = 10;

/**
Create from any array of ints containing information on the monitor. See the 'kOffsXXX' constants for
the significance of each int in the array.
*/

	MonitorMSVM( int data[], int dataOffset, int dataLen ) {
		int			top, left, bot, right;

		top = data[ dataOffset + kOffsBoundsTop ];
		left = data[ dataOffset + kOffsBoundsLeft ];
		bot = data[ dataOffset + kOffsBoundsBottom ];
		right = data[ dataOffset + kOffsBoundsRight ];
		boundsRect = new Rectangle( left, top, right - left, bot - top );

		top = data[ dataOffset + kOffsWorkareaTop ];
		left = data[ dataOffset + kOffsWorkareaLeft ];
		bot = data[ dataOffset + kOffsWorkareaBottom ];
		right = data[ dataOffset + kOffsWorkareaRight ];
		workareaRect = new Rectangle( left, top, right - left, bot - top );

		depth = data[ dataOffset + kOffsDepth ];
		refNum = data[ dataOffset + kOffsRefNum ];

		if ( data[ dataOffset + kOffsIsMainMonitor ] != 0 )
			bIsMainMonitor = true;
		else
			bIsMainMonitor = false;
	}

	public Rectangle getBounds() {
		return new Rectangle( boundsRect.x, boundsRect.y,
								boundsRect.width, boundsRect.height );
	}

	public Rectangle getWorkarea() {
		return new Rectangle( workareaRect.x, workareaRect.y,
								workareaRect.width, workareaRect.height );
	}

	public int getDepth() {
		return depth;
	}

	public boolean isMainMonitor() {
		return bIsMainMonitor;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setDepth( int newDepth, int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setResolution( Dimension requestedRes, Dimension newRes, int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "MonitorWin " + Integer.toHexString( hashCode() ) +":" );
		ps.println( indent + "  bounds=" + getBounds() );
		ps.println( indent + "  workarea=" + getWorkarea() );
		ps.println( indent + "  depth=" + getDepth() + ", " +
								( isMainMonitor() ? "is the main monitor" : "not the main monitor" ) );
	}
}

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

import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.PrintStream;

/**
Represents a monitor in cases where FileRegistryPlain is being used. Since this can only rely on
standard Java, it can't return too much information.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class MonitorPlain implements Monitor {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public MonitorPlain() {
	}

/**
Uses Toolkit.getDefaultToolkit().getScreenSize() to return this information.
*/

	public Rectangle getBounds() {
		Dimension		dims;

		dims = Toolkit.getDefaultToolkit().getScreenSize();

		return new Rectangle( 0, 0, dims.width, dims.height );
	}

/**
Returns the same value as getBounds().
*/

	public Rectangle getWorkarea() {
		return getBounds();
	}

/**
Unimplemented.
@return always returns 0
*/

	public int getDepth() {
		return 0;
	}

/**
Always returns true.
*/

	public boolean isMainMonitor() {
		return true;
	}

/**
Unimplemented.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setDepth( int newDepth, int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

/**
Unimplemented.
@exception UnimplementedException this method always throws an UnimplementedException
*/

	public int setResolution( Dimension requestedRes, Dimension newRes, int flags ) {
		throw new UnimplementedException( "not yet implemented" );		//	return ErrCodes.ERROR_UNIMPLEMENTED;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "MonitorPlain " + Integer.toHexString( hashCode() ) +":" );
		ps.println( indent + "  bounds=" + getBounds() );
		ps.println( indent + "  workarea=" + getWorkarea() );
		ps.println( indent + "  depth=" + getDepth() + ", " +
								( isMainMonitor() ? "is the main monitor" : "not the main monitor" ) );
	}
}


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

/**
Represents a video monitor. The 'getMonitors' and 'getMainMonitor' methods of the
'FileRegistry' singleton return objects of this class.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface Monitor extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
See the 'setDepth' and 'setResolution' methods.
*/

	public static final int TEST_ONLY_MASK=0x01;

/**
Returns the global coordinates of the monitor. On a Mac, the upper left corner of the
main monitor ( the one with the menu bar ) is at <0,0>.
*/

	public Rectangle getBounds();

/**
Returns the 'work area' of the monitor, in global coordinates. This is the bounds,
minus any menu/task bars. For instance, if the main monitor of a Mac is 640x480,
and the menu bar is 20 pixels high, this will return a Rectangle where x=0, y=20,
width=640, and height=460.
*/

	public Rectangle getWorkarea();

/**
Returns the bit depth of the monitor. If the value returned is zero or less, an
error occured.
*/

	public int getDepth();

/**
Returns whether this is the main monitor.
*/

	public boolean isMainMonitor();

/**
Tries to set the indicated depth. If 'TEST_ONLY_MASK' is set in 'flags', whether
this depth can be set is tested, but the depth is not changed. Returns an error
code, where zero means no error occured.
*/

	public int setDepth( int newDepth, int flags );

/**
Tries to set the indicated resolution. If 'TEST_ONLY_MASK' is set in 'flags',
whether this resolution can be set is tested, but the resolution is not changed.
On exit, 'newRes' contains the closest resolution possible. Returns an error code,
where zero means no error occured.
*/

	public int setResolution( Dimension requestedRes, Dimension newRes, int flags );
}



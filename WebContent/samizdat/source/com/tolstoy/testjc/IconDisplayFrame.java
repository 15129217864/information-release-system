package com.tolstoy.testjc;

import java.awt.*;

/**
A frame which exits the app when the user closes the window.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IconDisplayFrame extends Frame {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	public IconDisplayFrame( String ttl ) {
		super( ttl );
	}

	public boolean handleEvent( Event ev ) {
		if ( ev.id == Event.WINDOW_DESTROY ) {
			System.exit( 0 );
			return true;
		}
		else
			return false;
	}
}



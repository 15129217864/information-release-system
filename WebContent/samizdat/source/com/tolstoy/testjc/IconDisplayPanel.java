package com.tolstoy.testjc;

import java.awt.*;
import java.awt.image.*;

/**
Used to display a panel containing an icon.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IconDisplayPanel extends Panel {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	MemoryImageSource	mis;
	int					bits[];
	int					width, height;

	public IconDisplayPanel( int bits[], int wd, int ht ) {
		this.bits = bits;
		width = wd;
		height = ht;
	}

	public Dimension preferredSize() {
		return new Dimension( 100, 100 );
	}

	public void paint( Graphics g ) {
		Image			image;

		mis = new MemoryImageSource( width, height, bits, 0, width );

		image = createImage( mis );
		
		g.setColor( Color.red );
		g.fillRect( 0, 0, 100, 100 );
		g.drawImage( image, 0, 0, null );
	}
}
		

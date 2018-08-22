package com.tolstoy.testjc;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class BrowserTestPanel extends Panel {
	int			which, level;

	public BrowserTestPanel( int i ) {
		which = i;
		level = 0;
	}

	public void setLevel( int l ) {
		level = l;
	}

	public Dimension preferredSize() {
		return new Dimension( 500, 60 );
	}

	public void paint( Graphics g ) {
		Dimension		dims;

		dims = size();
		g.setColor( Color.lightGray );
		g.fillRect( 0, 0, dims.width, dims.height );
		g.draw3DRect( 0, 0, dims.width - 1, dims.height - 1, false );

		g.setColor( Color.red );
		g.drawString( "Level=" + level + ", which=" + which, 20, 20 );
	}
}


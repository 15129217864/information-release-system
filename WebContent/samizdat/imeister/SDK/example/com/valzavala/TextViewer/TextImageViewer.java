package com.valzavala.TextViewer;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import com.jconfig.*;
import com.tolstoy.imagemeister.*;

/**
Copyright (c) 1998 Samizdat Productions. All Rights Reserved.
Feel free to modify this file as you wish.
*/

public class TextImageViewer extends Panel implements ImageViewerI, WindowOwner {
	private ImageViewerOwner	owner;
	private FileSpecifier		theSpec;
	private OwnedFrame			theFrame;
	private File				theFile;
	private String				theString;

	public TextImageViewer( OwnedFrame oldFrame, ImageViewerOwner onr,
							FileSpecifier spec, Rectangle rect ) {
		DiskObject			diskObj;

		owner = onr;
		theSpec = spec;

		theFile = theSpec.getFile();
		if ( theFile == null ) {
			diskObj = theSpec.getDiskObject();
			if ( diskObj != null )
				theFile = diskObj.getFile();
		}

		if ( theFile == null )
			throw new IllegalArgumentException( "can't get file from " + spec );

		if ( !theFile.exists() || theFile.isDirectory() )
			throw new IllegalArgumentException( "the file doesn't exist or is a directory " + theFile.getPath() );

		createTheString();

		if ( oldFrame == null ) {
			theFrame = new OwnedFrame( this );
			theFrame.reshape( rect.x, rect.y, rect.width, rect.height );
		}
		else {
			theFrame = oldFrame;
			theFrame.setWindowOwner( this );
			theFrame.removeAll();
		}
		
		theFrame.setLayout( new BorderLayout() );
		theFrame.add( "Center", this );
		theFrame.setTitle( theFile.getName() );
	}

	public FileSpecifier getFileSpecifier() {
		return theSpec;
	}

	public OwnedFrame getFrame() {
		return theFrame;
	}

	public void setVisible( boolean bState ) {
		if ( bState )
			theFrame.show();
		else
			theFrame.hide();
	}

	public void paint( Graphics g ) {
		Dimension		dd;
		
		dd = size();
		g.setColor( Color.red );
		g.fillRect( 0, 0, dd.width, dd.height );
		g.setColor( Color.black );
		g.drawString( theString, 20, 30 );
	}

	public void closeWindow( Window w ) {
		if ( w == theFrame )
			owner.closeImageViewer( this );
	}

	private void createTheString() {
		DataInputStream			dis;

		try {
			dis = new DataInputStream( new FileInputStream( theFile ) );
			theString = dis.readLine();
			dis.close();
		}
		catch ( Exception e ) {
		}

		if ( theString == null )
			theString = ( "can't read " + theFile.getPath() );
		else if ( theString.equals( "" ) )
			theString = "blank first line";
	}
}

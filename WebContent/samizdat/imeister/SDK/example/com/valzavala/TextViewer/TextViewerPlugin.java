package com.valzavala.TextViewer;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import com.jconfig.*;
import com.tolstoy.imagemeister.*;

/**
Copyright (c) 1998 Samizdat Productions. All Rights Reserved.
Feel free to modify this file as you wish.
*/

public class TextViewerPlugin implements PluginI {
	private static final int				k4ccTEXT = JUtils.asciiToInt( "TEXT" );
	private static final FileExtension		kTextExtension = new FileExtension( ".txt" );

	public boolean canCreateImageViewer( FileSpecifier spec ) {
		FileExtension		ext;
		FinderInfo			finfo;

		ext = spec.getFileExtension();
		if ( ext != null && ext.isMatch( kTextExtension ) )
			return true;

		finfo = spec.getFinderInfo();
		if ( finfo != null && finfo.getFileType() == k4ccTEXT )
			return true;

		return false;
	}

	public ImageViewerI createImageViewer( OwnedFrame frame, ImageViewerOwner onr,
											FileSpecifier spec, Rectangle rect ) {
		Rectangle		rr;

		rr = new Rectangle( rect.x, rect.y, rect.width, rect.height );

		if ( frame == null )
			WindowManager.adjustWindowPlacement( rr );

		try {
			return new TextImageViewer( frame, onr, spec, rr );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	public boolean canCreateInfoViewer( FileSpecifier spec ) {
		return false;
	}
	
	public InfoViewerI createInfoViewer( OwnedFrame frame, InfoViewerOwner onr,
											FileSpecifier spec, Rectangle rect ) {
		return null;
	}
	
	public FinderInfo[] getFinderInfo() {
		FinderInfo		retArray[];
		
		retArray = new FinderInfo[ 1 ];
		retArray[ 0 ] = new FinderInfo( JUtils.asciiToInt( "????" ), k4ccTEXT );
		
		return retArray;
	}

	public FileExtension[] getFileExtension() {
		FileExtension		retArray[];
		
		retArray = new FileExtension[ 1 ];
		retArray[ 0 ] = kTextExtension;
		
		return retArray;
	}

	public MIMEType[] getMIMEType() {
		return null;
	}

	public VersionInfo getVersionInfo() {
		return null;
	}

	public String getName( int which ) {
		switch ( which ) {
			case kDisplayName:
				return "Text Files";

			case kMacStyleName:
				return "Text Files";

			case kWinStyleName:
				return "*.txt ( Text Files )";

			default:
				return null;
		}
	}
	
	public PluginI getParent() {
		return null;
	}

	public PluginI[] getChildren() {
		return null;
	}
	
	public IconPanel getIconPanel() {
		return null;
	}
}



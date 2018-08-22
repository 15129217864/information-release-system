package com.tolstoy.testjc;

import com.jconfig.*;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
Used by the DiskBrowser class. Displays information about a file, alias, directory, or volume.
The disk object being displayed is set and retrieved by the BrowserFrame which contains this
panel.


Feel free to modify this file as you wish.



@author JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.

*/

public class BrowserPanel extends Panel {
	DiskObject				diskObject;
	int						smIconData[], smIconW, smIconH,
								lgIconData[], lgIconW, lgIconH,
								which;

	public BrowserPanel( int i ) {
		diskObject = null;
		smIconData = null;
		smIconW = smIconH = 0;
		lgIconData = null;
		lgIconW = lgIconH = 0;
		which = i;
	}

/*
Depending on which type of disk object we hold, display its information. Note that since DiskAlias
extends DiskFile, we check for it first.
*/

	void showDiskInformation( Graphics g ) {
		showLargeIcon( g, 10, 10 );
		showSmallIcon( g, 50, 10 );
		
		if ( diskObject instanceof DiskAlias )
			showDiskAliasStrings( g, 100, 10 );
		else if ( diskObject instanceof DiskFile )
			showDiskFileStrings( g, 100, 10 );
		else if ( diskObject instanceof DiskVolume )
			showDiskVolumeStrings( g, 100, 10 );
		else
			Trace.println( "diskObject of unknown class: " + diskObject );
	}

/*
Get the icon bundle for the disk object, and display the large icon.
*/

	void showLargeIcon( Graphics g, int x, int y ) {
		MemoryImageSource		mis;
		Image						image;
		IconBundle				ib;
		int						theErr;

		ib = diskObject.getIconBundle();
		if ( ib == null ) {
			Trace.println( "can't get icon bundle from " + diskObject.getName() );
			return;
		}

		if ( lgIconData == null ||
		lgIconW != ib.getIconWidth( IconBundle.ICON_LARGE ) ||
		lgIconH != ib.getIconHeight( IconBundle.ICON_LARGE ) ) {
			lgIconW = ib.getIconWidth( IconBundle.ICON_LARGE );
			lgIconH = ib.getIconHeight( IconBundle.ICON_LARGE );
			lgIconData = new int[ lgIconW * lgIconH ];
		}

		theErr = ib.getIcon( IconBundle.ICON_LARGE, IconBundle.ICON_CHANGE_NONE, IconBundle.ICON_ALIGN_NONE, lgIconData );

		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "got err=" + theErr + " from icon bundle for " + diskObject.getName() );
			return;
		}

		mis = new MemoryImageSource( lgIconW, lgIconH, lgIconData, 0, lgIconW );

		image = createImage( mis );

		g.drawImage( image, x, y, null );
	}

/*
Similar to the preceding method.
*/

	void showSmallIcon( Graphics g, int x, int y ) {
		MemoryImageSource		mis;
		Image						image;
		IconBundle				ib;
		int						theErr;

		ib = diskObject.getIconBundle();
		if ( ib == null ) {
			Trace.println( "can't get icon bundle from " + diskObject.getName() );
			return;
		}

		if ( smIconData == null ||
		smIconW != ib.getIconWidth( IconBundle.ICON_SMALL ) ||
		smIconH != ib.getIconHeight( IconBundle.ICON_SMALL ) ) {
			smIconW = ib.getIconWidth( IconBundle.ICON_SMALL );
			smIconH = ib.getIconHeight( IconBundle.ICON_SMALL );
			smIconData = new int[ smIconW * smIconH ];
		}

		theErr = ib.getIcon( IconBundle.ICON_SMALL, IconBundle.ICON_CHANGE_NONE, IconBundle.ICON_ALIGN_NONE, smIconData );

		if ( theErr != ErrCodes.ERROR_NONE ) {
			Trace.println( "got err=" + theErr + " from icon bundle for " + diskObject.getName() );
			return;
		}

		mis = new MemoryImageSource( smIconW, smIconH, smIconData, 0, smIconW );

		image = createImage( mis );

		g.drawImage( image, x, y, null );
	}

/*
Show information for a disk file.
*/

	void showDiskFileStrings( Graphics g, int x, int y ) {
		VersionInfo			versionInfo;
		DiskFile			df;
		FileSystem			fileSystem;
		File				jFile;
		String				s1, s2, s3, s4, s5, s7, fileSystemName, finderInfoString, dateString;
		int					theErr, finfo[], flags;
		
		df = (DiskFile) diskObject;
		fileSystem = df.getFileSystem();
		if ( fileSystem == null )
			fileSystemName = "<unk>";
		else
			fileSystemName = fileSystem.getDisplayName();

		dateString = getDateString( df );
		finderInfoString = getFinderInfoString( df );

		flags = df.getFlags();
		versionInfo = df.getVersion();
		jFile = df.getFile();

		if ( ( flags & DiskFile.FILE_DIR ) != 0 )
			s1 = "FOLDER ";
		else
			s1 = "FILE ";

		s1 += "@" + Integer.toHexString( df.hashCode() ) + ": " +  df.getName() + ", on " + fileSystemName;

		s2 = "flags=" + df.diskFileFlagsToString( flags ) + " ( 0x" + Integer.toHexString( flags ) + " )";
	
		if ( versionInfo == null )
			s2 += ", no version info";
		else
			s2 += ", version string: " + versionInfo.getVersionString();

		if ( jFile == null )
			s3 = "java.io.File is null";
		else {
			s3 = "java.io.File=" + jFile.getPath();
			if ( !jFile.exists() )
				s3 += "** java.io.File.exists() == false **";
		}

		s4 = finderInfoString +
				", color coding=" + df.getColorCoding() +
				", file size=" + df.getFileSize() +
				", res fork size=" + df.getResourceForkSize() +
				", shortname=" + df.getShortName();
		
		s5 = "dates=" + dateString;

		g.setColor( Color.red );
		g.drawString( s1, x, y );
		g.drawString( s2, x, y + 15 );
		g.drawString( s3, x, y + 30 );
		g.drawString( s4, x, y + 45 );
		g.drawString( s5, x, y + 60 );
	}

/*
Show information for an alias.
*/

	void showDiskAliasStrings( Graphics g, int x, int y ) {
		DiskObject		resolvedObject;
		DiskAlias		da;
		String			s1, s2;
		int				type;

		da = (DiskAlias) diskObject;

		s1 = "ALIAS @" + Integer.toHexString( da.hashCode() ) + ": " +  da.getName();

		type = da.getAliasType();
		if ( type == DiskAlias.ALIAS_VOL ) s2 = "to volume";
		else if ( type == DiskAlias.ALIAS_DIR ) s2 = "to folder";
		else if ( type == DiskAlias.ALIAS_FILE ) s2 = "to file";
		else if ( type == DiskAlias.ALIAS_OTHER ) s2 = "to unknown";
		else if ( type == DiskAlias.ALIAS_NOLONGER ) s2 = "no longer alias";
		else s2 = "unknown type value";

		try {
			resolvedObject = FileRegistry.resolveAlias( da, FileRegistry.ALIAS_NO_UI );
			if ( resolvedObject == null )
				s2 += ", resolveAlias returned null";
			else
				s2 += ": to " + resolvedObject.getName();
		}
		catch ( Exception e ) {
			s2 += " got exception resolving: " + e;
		}

		g.setColor( Color.red );
		g.drawString( s1, x, y );
		g.drawString( s2, x, y + 15 );
	}

/*
Show information for a volume.
*/

	void showDiskVolumeStrings( Graphics g, int x, int y ) {
		DiskVolume			dv;
		File				jFile;
		String				s1, s2, s3, s4;
		
		dv = (DiskVolume) diskObject;
		
		jFile = dv.getFile();

		s1 = "VOLUME @" + Integer.toHexString( dv.hashCode() ) + ": " +  dv.getName();

		s2 = "prefix=" + dv.getPrefix() +
				", color coding=" + dv.getColorCoding() +
				", ref num=" + dv.getReferenceNumber();
	
		if ( jFile == null )
			s3 = "java.io.File is null";
		else
			s3 = "java.io.File=" + jFile.getPath();

		if ( !jFile.exists() )
			s3 += "** java.io.File.exists() == false **"; 

		s4 = "max capacity=" + dv.getMaxCapacity() +
				", free space=" + dv.getFreeSpace() + ", flags=" + diskVolumeFlagsToString( dv.getFlags() );

		g.setColor( Color.red );
		g.drawString( s1, x, y );
		g.drawString( s2, x, y + 15 );
		g.drawString( s3, x, y + 30 );
		g.drawString( s4, x, y + 45 );
	}

	String diskVolumeFlagsToString( int f ) {
		String		ret = "";

		if ( ( f & DiskVolume.VOL_CASE_PRESERVED ) != 0 )
			ret = ret + "<CsPrsrv> ";
		if ( ( f & DiskVolume.VOL_CASE_SENSITIVE ) != 0 )
			ret = ret + "<CsSens> ";
		if ( ( f & DiskVolume.VOL_UNICODE ) != 0 )
			ret = ret + "<Uni> ";
		if ( ( f & DiskVolume.VOL_FILES_COMPRESSED ) != 0 )
			ret = ret + "<FComp> ";
		if ( ( f & DiskVolume.VOL_VOL_COMPRESSED ) != 0 )
			ret = ret + "<VComp> ";
		if ( ( f & DiskVolume.VOL_REMOVABLE ) != 0 )
			ret = ret + "<Rmv> ";
		if ( ( f & DiskVolume.VOL_FIXED ) != 0 )
			ret = ret + "<Fxd> ";
		if ( ( f & DiskVolume.VOL_REMOTE ) != 0 )
			ret = ret + "<Rmt> ";
		if ( ( f & DiskVolume.VOL_CDROM ) != 0 )
			ret = ret + "<CD> ";
		if ( ( f & DiskVolume.VOL_RAM ) != 0 )
			ret = ret + "<RAM> ";
		if ( ( f & DiskVolume.VOL_SYSTEM ) != 0 )
			ret = ret + "<Sys> ";

		return ret;
	}

	private String getFinderInfoString( DiskFile df ) {
		try {
			return df.getFinderInfo().toString();
		}
		catch ( UnimplementedException ue ) {
			return "FinderInfo not implemented";
		}
		catch ( Exception e ) {
			return e.toString();
		}
	}

	private String getDateString( DiskFile df ) {
		try {
			return df.getDateBundle().toString();
		}
		catch ( UnimplementedException ue ) {
			return "getDateBundle not implemented";
		}
		catch ( Exception e ) {
			return e.toString();
		}
	}

	public DiskObject getDiskObject() {
		return diskObject;
	}
	
	public void setDiskObject( DiskObject dob ) {
		diskObject = dob;
	}

	public Dimension preferredSize() {
		return new Dimension( 500, 60 );
	}

	public void paint( Graphics g ) {
		Dimension		dims;

		dims = size();
		if ( diskObject == null ) {
			g.setColor( Color.darkGray );
			g.fillRect( 0, 0, dims.width, dims.height );
		}
		else {
			g.setColor( Color.lightGray );
			g.fillRect( 0, 0, dims.width, dims.height );
			g.draw3DRect( 0, 0, dims.width - 1, dims.height - 1, false );

			showDiskInformation( g );
		}
	}
}


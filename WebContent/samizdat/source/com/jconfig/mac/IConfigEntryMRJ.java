/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.mac;

import com.jconfig.*;

import java.io.PrintStream;

/**
Reads a ConfigEntry from a buffer filled by the Internet Config ICGetIndMapEntry() routine.

<P>
See the IC documentation for more information on that routine.

<P>
Note that the IConfigEntryBinary class reads a ConfigEntry from a packed buffer,
while this class read a ConfigEntry from an unpacked buffer provided by InternetConfig.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IConfigEntryMRJ implements ConfigEntry {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	FinderInfo		fInfo;
	MIMEType		mimeType;
	FileExtension	fileExtension;
	String			creatorAppName, postAppName, entryName;
	int				flags;
	
	public static final int		kRecordLength = 1302;

/**
Construct from an unpacked buffer. See the IC documentation for the offsets used.
*/

	IConfigEntryMRJ( byte[] record ) {
		int			type, creator;
		String		ext, mime, entry;

		if ( record.length < kRecordLength )
			return;
			
		type = JUtils.bytesToInt( record, 6 );
		creator = JUtils.bytesToInt( record, 10 );
		flags = JUtils.bytesToInt( record, 18 );
		
		ext = JUtils.pascalBytesToString( record, 22 );
		creatorAppName = JUtils.pascalBytesToString( record, 278 );
		postAppName = JUtils.pascalBytesToString( record, 534 );
		mime = JUtils.pascalBytesToString( record, 790 );
		entryName = JUtils.pascalBytesToString( record, 1046 );
		
		fInfo = new FinderInfo( creator, type );
		
		if ( mime != null && mime.length() > 0 )
			mimeType = new MIMEType( mime );

		if ( ext != null && ext.length() > 0 )
			fileExtension = new FileExtension( ext );
	}
	
	public FinderInfo getFinderInfo() {
		return fInfo;
	}
	
	public MIMEType getMIMEType() {
		return mimeType;
	}
	
	public FileExtension getFileExtension() {
		return fileExtension;
	}
	
	public String getAppName() {
		return creatorAppName;
	}
	
	public String getEntryName() {
		return entryName;
	}

	public int getFlags() {
		return flags;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "IConfigEntryMRJ:" );
		ps.println( indent + "  creatorAppName=" + creatorAppName + ", postAppName=" + postAppName );

		if ( fInfo == null )
			ps.println( indent + "  no FinderInfo" );
		else
			ps.println( indent + "  " + fInfo );

		if ( mimeType == null )
			ps.println( indent + "  no MIMEType" );
		else
			ps.println( indent + "  " + mimeType );

		if ( fileExtension == null )
			ps.println( indent + "  no FileExtension" );
		else
			ps.println( indent + "  " + fileExtension );

		ps.println( indent + "  flags=" + Integer.toHexString( flags ) );
	}
}


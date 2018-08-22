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

import java.io.PrintStream;
import java.util.StringTokenizer;

/**
Represents an IC file mapping table entry. Reads the entry from an array of bytes, and returns the various
fields of the entry.

<P>
These entries are used to map between Mac creator/file types, Windows extensions, and MIME types. See the
Internet Config documentation for more information.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class IConfigEntryBinary implements ConfigEntry {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private static final int		kShortSize = 2;
	private static final int		kIntSize = 4;

	private FinderInfo		fInfo;
	private MIMEType		mimeType;
	private FileExtension	fileExtension;
	private String			creatorAppName, postAppName, entryName;
	private int				flags, entryLen, fixedLen, version, postCreator;
	
/**
See the Internet Config documentation for the format of an entry.
*/

	IConfigEntryBinary( byte buf[], int offset ) {
		createFromBuffer( buf, offset );
	}

	private void createFromBuffer( byte buf[], int offset ) {
		int			strLen, tempFileType, tempFileCreator;
		String		tempMIME, tempExt;

		entryLen = JUtils.bytesToShort( buf, offset );
		offset += kShortSize;

		fixedLen = JUtils.bytesToShort( buf, offset );
		offset += kShortSize;

		version = JUtils.bytesToShort( buf, offset );
		offset += kShortSize;

		tempFileType = JUtils.bytesToInt( buf, offset );
		offset += kIntSize;

		tempFileCreator = JUtils.bytesToInt( buf, offset );
		offset += kIntSize;

		postCreator = JUtils.bytesToInt( buf, offset );
		offset += kIntSize;

		flags = JUtils.bytesToInt( buf, offset );
		offset += kIntSize;

		strLen = (int) buf[ offset ] + 1;
		tempExt = JUtils.pascalBytesToString( buf, offset );
		offset += strLen;

		strLen = (int) buf[ offset ] + 1;
		creatorAppName = JUtils.pascalBytesToString( buf, offset );
		offset += strLen;

		strLen = (int) buf[ offset ] + 1;
		postAppName = JUtils.pascalBytesToString( buf, offset );
		offset += strLen;

		strLen = (int) buf[ offset ] + 1;
		tempMIME = JUtils.pascalBytesToString( buf, offset );
		offset += strLen;

		strLen = (int) buf[ offset ] + 1;
		entryName = JUtils.pascalBytesToString( buf, offset );

		fInfo = new FinderInfo( tempFileCreator, tempFileType );
		fileExtension = new FileExtension( tempExt );
		mimeType = new MIMEType( tempMIME );
	}

/**
Return the length of the entry.
*/
	
	public int getEntryLength() {
		return entryLen;
	}

/**
Return the FinderInfo of the entry.
*/
	
	public FinderInfo getFinderInfo() {
		return fInfo;
	}
	
/**
Return the MIMEType of the entry.
*/
	
	public MIMEType getMIMEType() {
		return mimeType;
	}
	
/**
Return the FileExtension of the entry.
*/
	
	public FileExtension getFileExtension() {
		return fileExtension;
	}
	
/**
Return the application name of the entry.
*/
	
	public String getAppName() {
		return creatorAppName;
	}
	
/**
Return the name of the entry.
*/
	
	public String getEntryName() {
		return entryName;
	}

/**
Return the flags of the entry.
*/
	
	public int getFlags() {
		return flags;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "IConfigEntryBin: creatorAppName=" + creatorAppName + ", postAppName=" + postAppName );

		if ( fInfo == null )
			ps.println( indent + "no FinderInfo" );
		else
			ps.println( indent + fInfo );

		if ( mimeType == null )
			ps.println( indent + "no MIMEType" );
		else
			ps.println( indent + mimeType );

		if ( fileExtension == null )
			ps.println( indent + "no FileExtension" );
		else
			ps.println( indent + fileExtension );

		ps.println( indent + "flags=0x" + Integer.toHexString( flags ) );
	}
}


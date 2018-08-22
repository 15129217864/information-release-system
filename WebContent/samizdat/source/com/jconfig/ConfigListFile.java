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

import java.io.*;
import java.util.Vector;

/**
Used to obtain information from 'jconfig.cfg', which contains the contents of the Internet Config
file mapping table. This is used in four situations: by FileRegistryPlain, on Windows, on Unix,
and on Mac if Internet Config is not installed.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class ConfigListFile implements ConfigList {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private byte		buffer[];
	private int			bufferLen;

/**
Reads the contents of the given file into an internal buffer.
*/

	public ConfigListFile( File configDir, String fileName, int creator ) {
		String				dirName;

		if ( !tryCreateFromFile( configDir, fileName ) ) {
			dirName = ( configDir == null ? "<null>" : configDir.getPath() );
			Trace.println( "WARNING: can't read from " + fileName + " in " + dirName + ", using defaults" );
			buffer = ConfigListDefault.getBuf();
			bufferLen = buffer.length;
		}
	}

	private boolean tryCreateFromFile( File configDir, String fileName ) {
		File				fl;
		DataInputStream		dis;

		try {
			fl = new File( configDir, fileName );
			if ( !fl.exists() )
				return false;

			bufferLen = (int) fl.length();
			if ( bufferLen <= 0 )
				return false;

			buffer = new byte[ bufferLen ];
			dis = new DataInputStream( new FileInputStream( fl ) );
			dis.readFully( buffer );
			dis.close();
		}
		catch ( Exception e ) {
			return false;
		}

		return true;
	}

/**
For each entry of the buffer, creates a IConfigEntryBinary object, and calls the fdv's visit() method with this
object.
*/

	public int iterate( ConfigEntryVisitor fdv ) {
		IConfigEntryBinary		entry;
		int						offset;

		offset = 4;
		
		while ( offset < bufferLen ) {
			entry = new IConfigEntryBinary( buffer, offset );
			fdv.visit( entry );
			offset += entry.getEntryLength();
		}

		return 0;
	}

/**
Map a FinderInfo object to zero or more FileExtension objects. May return null if none could be found.
*/

	public FileExtension[] findMatches( FinderInfo fInfo, int maxToReturn, int direction ) {
		CEVFinderInfoFinder		cev;

		if ( direction == 0 )
			cev = new CEVFinderInfoFinder( fInfo, maxToReturn );
		else
			cev = new CEVFinderInfoFinderDir( fInfo, maxToReturn, direction );

		iterate( cev );

		return cev.getExtensions();
	}

/**
Map a FileExtension object to zero or more FinderInfo objects. May return null if none could be found.
*/

	public FinderInfo[] findMatches( FileExtension ext, int maxToReturn, int direction ) {
		CEVExtensionFinder		cev;

		if ( direction == 0 )
			cev = new CEVExtensionFinder( ext, maxToReturn );
		else
			cev = new CEVExtensionFinderDir( ext, maxToReturn, direction );

		iterate( cev );

		return cev.getFinderInfos();
	}
}

class CEVFinderInfoFinder implements ConfigEntryVisitor {
	FileExtension		exactMatches[], partialMatches[];
	int					testCreator, testFileType, maxToReturn,
						numExactWritten, numPartialWritten;

	CEVFinderInfoFinder( FinderInfo fi, int max ) {
		testCreator = fi.getCreator();
		testFileType = fi.getFileType();
		maxToReturn = max;
		numExactWritten = 0;
		numPartialWritten = 0;
		exactMatches = new FileExtension[ maxToReturn ];
		partialMatches = new FileExtension[ maxToReturn ];
	}

	public void visit( ConfigEntry fd ) {
		FinderInfo		curFInfo;
		int				flags;

		if ( numExactWritten >= maxToReturn && numPartialWritten >= maxToReturn )
			return;

		curFInfo = fd.getFinderInfo();
		flags = fd.getFlags();

		if ( curFInfo.getFileType() == testFileType ) {
			if ( curFInfo.getCreator() == testCreator ) {
				if ( numExactWritten < maxToReturn ) {
					exactMatches[ numExactWritten++ ] = fd.getFileExtension();
				}
			}
			else {
				if ( numPartialWritten < maxToReturn ) {
					partialMatches[ numPartialWritten++ ] = fd.getFileExtension();
				}
			}
		}
	}

	FileExtension[] getExtensions() {
		FileExtension		retArray[];
		int					i, j, numWritten;

		numWritten = numExactWritten + numPartialWritten;

		if ( numWritten < 1 )
			return null;

		if ( numWritten > maxToReturn )
			numWritten = maxToReturn;

		retArray = new FileExtension[ numWritten ];

		for ( i = 0; i < numExactWritten && i < numWritten; i++ )
			retArray[ i ] = exactMatches[ i ];

		for ( j = 0; j < numPartialWritten && i < numWritten; j++, i++ )
			retArray[ i ] = partialMatches[ j ];

		return retArray;
	}
}

class CEVFinderInfoFinderDir extends CEVFinderInfoFinder {
	int					direction;

	CEVFinderInfoFinderDir( FinderInfo fi, int max, int dir ) {
		super( fi, max );
		direction = dir;
	}

	public void visit( ConfigEntry fd ) {
		FinderInfo		curFInfo;

		if ( numExactWritten >= maxToReturn && numPartialWritten >= maxToReturn )
			return;

		if ( ( fd.getFlags() & direction ) != 0 )
			return;

		curFInfo = fd.getFinderInfo();

		if ( curFInfo.getFileType() == testFileType ) {
			if ( curFInfo.getCreator() == testCreator ) {
				if ( numExactWritten < maxToReturn ) {
					exactMatches[ numExactWritten++ ] = fd.getFileExtension();
				}
			}
			else {
				if ( numPartialWritten < maxToReturn ) {
					partialMatches[ numPartialWritten++ ] = fd.getFileExtension();
				}
			}
		}
	}
}

class CEVExtensionFinder implements ConfigEntryVisitor {
	FileExtension		ext;
	FinderInfo			tempArray[];
	int					maxToReturn, numWritten, count;

	CEVExtensionFinder( FileExtension fe, int max ) {
		ext = fe;
		maxToReturn = max;
		count = 0;
		numWritten = 0;
		tempArray = new FinderInfo[ maxToReturn ];
	}

	public void visit( ConfigEntry fd ) {
		if ( numWritten < maxToReturn && fd.getFileExtension().isMatch( ext ) )
			tempArray[ numWritten++ ] = fd.getFinderInfo();
	}
	
	FinderInfo[] getFinderInfos() {
		FinderInfo		retArray[];
		int				i;

		if ( numWritten < 1 )
			return null;

		retArray = new FinderInfo[ numWritten ];

		for ( i = 0; i < numWritten; i++ )
			retArray[ i ] = tempArray[ i ];

		return retArray;
	}
}

class CEVExtensionFinderDir extends CEVExtensionFinder {
	int					direction;

	CEVExtensionFinderDir( FileExtension fe, int max, int dir ) {
		super( fe, max );
		direction = dir;
	}

	public void visit( ConfigEntry fd ) {
		if ( numWritten >= maxToReturn )
			return;

		if ( ( fd.getFlags() & direction ) != 0 )
			return;

		if ( fd.getFileExtension().isMatch( ext ) )
			tempArray[ numWritten++ ] = fd.getFinderInfo();
	}
}







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

/**
Represents a cross-platform file type. Instances are created from either a FileExtension or
FinderInfo object, and arrays of corresponding FinderInfo or FileExtension objects can be
obtained.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class FileType {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private int						createdFrom, maxExtensionsRequested,
									maxFinderInfosRequested;
	private FileExtension			theExt, savedExtensions[];
	private FinderInfo				theFinderInfo, savedFileInfos[];

/**
See the getProvenance() method.
*/

	public static final int			CREATED_FROM_EXT = 1;

/**
See the getProvenance() method.
*/

	public static final int			CREATED_FROM_FTAC = 2;

/**
See the getProvenance() method.
*/

	public static final int			CREATED_FROM_MIME = 3;

/**
Initialize from a FileExtension object.
*/

	public FileType( FileExtension ext ) {
		theExt = ext;
		theFinderInfo = null;
		createdFrom = CREATED_FROM_EXT;
		maxExtensionsRequested = 0;
		maxFinderInfosRequested = 0;
		savedExtensions = null;
		savedFileInfos = null;
	}

/**
Initialize from a FileType object.
*/

	public FileType( FinderInfo finfo ) {
		theFinderInfo = finfo;
		theExt = null;
		createdFrom = CREATED_FROM_FTAC;
		maxExtensionsRequested = 0;
		maxFinderInfosRequested = 0;
		savedExtensions = null;
		savedFileInfos = null;
	}

/**
Returns an array of FileExtension's corresponding to this FileType.
<BR>
If this object was created from an extension, an array with length 1 is returned containing
the extension, and the 'maxToReturn' argument is ignored.
<BR>
Otherwise, an array is returned containing FinderInfo objects which
correspond to the extension used to initialize this object. The 
'maxToReturn' argument is used a hint; the actual array size may be larger
or smaller than this. This method may return null if no matches could be found.
*/

	public FileExtension[] getExtensions( int maxToReturn ) {
		FileExtension		retVal[];

		if ( maxToReturn < 1 )
			throw new IllegalArgumentException( "maxToReturn < 1" );
	
		if ( createdFrom == CREATED_FROM_EXT ) {
			retVal = new FileExtension[ 1 ];
			retVal[ 0 ] = theExt;
			
			return retVal;
		}
		
		if ( maxExtensionsRequested <= maxToReturn ) {
			maxExtensionsRequested = maxToReturn;
			savedExtensions = FileRegistry.findExtensions( theFinderInfo, maxToReturn );
		}

		return savedExtensions;
	}
	
/**
Returns an array of FinderInfo's corresponding to this FileType.
<BR>
If this object was created from a FinderInfo object, an array with length 1 is returned
containing that object, and the 'maxToReturn' argument is ignored.
<BR>
Otherwise, an array is returned containing FileExtension objects which
correspond to the FinderInfo object used to initialize this object. The 
'maxToReturn' argument is used a hint; the actual array size may be larger
or smaller than this. This method may return null if no matches could be found.
*/

	public FinderInfo[] getFinderInfos( int maxToReturn ) {
		FinderInfo			retVal[];

		if ( maxToReturn < 1 )
			throw new IllegalArgumentException( "maxToReturn < 1" );
	
		if ( createdFrom == CREATED_FROM_FTAC ) {
			retVal = new FinderInfo[ 1 ];
			retVal[ 0 ] = theFinderInfo;
			
			return retVal;
		}
		
		if ( maxFinderInfosRequested <= maxToReturn ) {
			maxFinderInfosRequested = maxToReturn;
			savedFileInfos = FileRegistry.findFinderInfo( theExt, maxToReturn );
		}

		return savedFileInfos;
	}
	
/**
Flush any cached data. This method can be used after, for instance, a new application has
been installed on the user's system.
*/

	public void flushCachedData() {
		theExt = null;
		theFinderInfo = null;
		maxExtensionsRequested = 0;
		maxFinderInfosRequested = 0;
	}

/**
Returns how this object was created, one of the following three values: CREATED_FROM_EXT,
CREATED_FROM_FTAC, or CREATED_FROM_MIME.
*/

	public int getProvenance() {
		return createdFrom;
	}

	public String toString() {
		if ( createdFrom == CREATED_FROM_FTAC )
			return "FileType " + hashCode() + " from " + theFinderInfo;
		else if ( createdFrom == CREATED_FROM_EXT )
			return "FileType " + hashCode() + " from " + theExt;
		else
			return "FileType " + hashCode() + " from MIME";
	}
}


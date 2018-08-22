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
A generic implementation of the DiskFilter interface. When the 'visit()' method is called, it saves the 
DiskObject passed to this method in an array. This array can be retrieved with the 'getArray()' method.

See the DiskBrowser application for an example of how to use this.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class GenDiskFilter implements DiskFilter {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	DiskObject			savedItems[];
	int					count, numItems;

/**
This object will store up to 'n' DiskObjects.
*/

	public GenDiskFilter( int n ) {
		numItems = n;
		savedItems = new DiskObject[ n ];
		count = 0;
	}

/**
Return the array of saved DiskObjects. May return null if no objects were stored.
*/

	public DiskObject[] getArray() {
		DiskObject			retArray[];
		int					i;

		if ( count < 1 )
			return null;
			
		retArray = new DiskObject[ count ];
		for ( i = 0; i < count; i++ )
			retArray[ i ] = savedItems[ i ];

		return retArray;
	}

	public boolean visit( DiskObject diskObj ) {
		if ( count >= numItems )
			return false;

		savedItems[ count ] = diskObj;
		++count;

		return true;
	}
}



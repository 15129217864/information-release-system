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
Used to contain zero or more RawDate objects.
You can get and set the modification, creation, backup, and access dates.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class DateBundle {
	public static final int		DATE_MOD = 0;
	public static final int		DATE_CRE = 1;
	public static final int		DATE_BKUP = 2;
	public static final int		DATE_ACC = 3;

	public static final int		DB_ARRAY_LEN = 18;

/**
The offset of the modification date in the array returned from toArray and in the array passed to createFromArray.
*/

	public static final int		DB_MOD_OFFSET = 0;

/**
The offset of the creation date in the array returned from toArray and in the array passed to createFromArray.
*/

	public static final int		DB_CRE_OFFSET = 6;

/**
The offset of the backup date in the array returned from toArray and in the array passed to createFromArray.
*/

	public static final int		DB_BKUP_OFFSET = 12;

	private RawDate		modDate = null, creationDate = null, backupDate = null, accessDate = null;

/**
Construct with all the dates set to null.
*/

	public DateBundle() {
		modDate = null;
		creationDate = null;
		backupDate = null;
		accessDate = null;
	}

/**
Construct from a series of RawDate objects. Any of these can be null.
*/

	public DateBundle( RawDate mdDate, RawDate crDate, RawDate bkDate, RawDate acDate ) {
		modDate = mdDate;
		creationDate = crDate;
		backupDate = bkDate;
		accessDate = acDate;
	}

/**
Return the indicated date. 'which' must be one of the constants defined above.
This method returns null if 'which' is out of bounds, or if the give date is null.
*/

	public RawDate getRawDate( int which ) {
		if ( !checkDateSelector( which ) )
			return null;

		if ( which == DATE_MOD )
			return modDate;
		else if ( which == DATE_CRE )
			return creationDate;
		else if ( which == DATE_BKUP )
			return backupDate;
		else
			return accessDate;
	}

/**
Set the indicated date. 'which' must be one of the constants defined above.
Returns true if the date was set, false otherwise.
*/

	public boolean setDate( int which, RawDate rd ) {
		if ( !checkDateSelector( which ) )
			return false;

		if ( which == DATE_MOD )
			modDate = rd;
		else if ( which == DATE_CRE )
			creationDate = rd;
		else if ( which == DATE_BKUP )
			backupDate = rd;
		else
			accessDate = rd;

		return true;
	}

/**
Puts the int fields of each date of db into offsets in the datesArray
*/

	public int[] toArray() {
		RawDate			tempDate;
		int				retArray[], i;

		retArray = new int[ DB_ARRAY_LEN ];
		for ( i = 0; i < DB_ARRAY_LEN; i++ )
			retArray[ i ] = 0;

		tempDate = getRawDate( DATE_CRE );
		if ( tempDate != null )
			tempDate.putIntoArray( retArray, DB_CRE_OFFSET );

		tempDate = getRawDate( DATE_MOD );
		if ( tempDate != null )
			tempDate.putIntoArray( retArray, DB_MOD_OFFSET );

		tempDate = getRawDate( DATE_BKUP );
		if ( tempDate != null )
			tempDate.putIntoArray( retArray, DB_BKUP_OFFSET );

		return retArray;
	}

/**
Returns a DateBundle constructed from an int array containing the creation, modification,
and backup dates. Each date takes up 6 ints: year, month, etc., and is stored at
the kXXXDateOffset.
*/

	public static DateBundle createFromArray( int datesArray[] ) {
		RawDate			c, m, b;

		c = makeRawDate( datesArray, DB_CRE_OFFSET );
		m = makeRawDate( datesArray, DB_MOD_OFFSET );
		b = makeRawDate( datesArray, DB_BKUP_OFFSET );

		return new DateBundle( m, c, b, null );
	}

/**
Constructs a RawDate from an int array containing the components of the date: year, month,
day, hour, minute, and second, in that order.
@param ary contains the components of the date
@param offset the date components will be read starting at this offset
*/

	private static RawDate makeRawDate( int ary[], int offset ) {
		if ( ary[ offset ] == 0 )
			return null;

		try {
			return new RawDate( ary[ offset + 0 ], ary[ offset + 1 ], ary[ offset + 2 ],
								ary[ offset + 3 ], ary[ offset + 4 ], ary[ offset + 5 ] );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	private boolean checkDateSelector( int which ) {
		if ( which == DATE_MOD ||
				which == DATE_CRE ||
				which == DATE_BKUP ||
				which == DATE_ACC )
			return true;

		return false;
	}

	public String toString() {
		return "<cr=" + creationDate + ", mod=" + modDate + ", bk=" + backupDate + ", ac=" + accessDate + " >";
	}
}



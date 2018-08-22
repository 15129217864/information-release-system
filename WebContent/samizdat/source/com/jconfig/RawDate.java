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

import java.util.Date;

/**
Stores a date in raw format:
<UL>
<LI>year - the actual year, >= 1. 1970 means 1970
<LI>month - the month, 1 to 12
<LI>day - the day, 1 to 31 or less
<LI>hour - 0 to 23
<LI>minute - 0 to 59
<LI>second - 0 to 59
</UL>

This class is immutable.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public class RawDate {
	private int		year, month, day, hour, minute, second;

/**
Construct from a year, month, day, hour, minute, and second.
If ( year < 1 || year > 1000000 ), it will be set to 1970.
If any of the other values are out of range, they will be set to be within the values given above.
*/

	public RawDate( int yr, int mo, int dy, int hh, int mm, int ss ) {
		year = yr;
		month = mo;
		day = dy;
		hour = hh;
		minute = mm;
		second = ss;

		if ( year < 1 || year > 1000000 )
			year = 1970;

		month = pinValue( month, 1, 12 );
		day = pinValue( day, 1, 31 );
		hour = pinValue( hour, 0, 23 );
		minute = pinValue( minute, 0, 59 );
		second = pinValue( second, 0, 59 );
	}

/**
Places the fields of this date into the six elements of ary, beginning at 'offset'. The fields are in this order:
year, month, day, hour, minute, second.	They have the significance indicated above.
*/

	public void putIntoArray( int ary[], int offset ) {
		ary[ 0 ] = year;		ary[ 1 ] = month;		ary[ 2 ] = day;
		ary[ 3 ] = hour;		ary[ 4 ] = minute;		ary[ 5 ] = second;
	}

/**
Return the year. This will have the meaning given above.
*/

	public int getYear() { return year; }

/**
Return the month. This will have the meaning given above.
*/

	public int getMonth() { return month; }

/**
Return the day. This will have the meaning given above.
*/

	public int getDay() { return day; }

/**
Return the hour. This will have the meaning given above.
*/

	public int getHour() { return hour; }

/**
Return the minute. This will have the meaning given above.
*/

	public int getMinute() { return minute; }

/**
Return the second. This will have the meaning given above.
*/

	public int getSecond() { return second; }

	private int pinValue( int val, int min, int max ) {
		if ( val < min )
			return min;
		if ( val > max )
			return max;
		return val;
	}

	public String toString() {
		return "<" + month + "/" + day + "/" + year + " at " + hour + ":" + minute + ":" + second + ">";
	}
}


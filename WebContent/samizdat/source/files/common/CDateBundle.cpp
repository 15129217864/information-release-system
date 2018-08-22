/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CDateBundle.h"

/*
The three dates are stored in unpacked format (year, month, etc.) in
an array of longs named 'datesArray'.

For instance, the creation date is stored at offset 'kCreationDateOffset':

	datesArray[ kCreationDateOffset + 0 ] is the creation date's year
	datesArray[ kCreationDateOffset + 1 ] is the creation date's month
	etc...

The methods that set a date from native format (e.g. FILETIME) convert
the native representation of a date into year, month, etc., and then
store the year, month, etc at appropriate locations in the array of longs.

Then, when one of the 'setDatesArray' methods is called, it copies 'datesArray'
into the given Java jint array.

Back in Javaland, the dates will be retrieved from this Java jint array,
and used to create RawDate objects.
*/


CDateBundle::CDateBundle()
{
	long		i;

	for ( i = 0; i < kDatesArrayLen; i++ )
		datesArray[ i ] = 0;
}

CDateBundle::~CDateBundle()
{
}

#if defined( DO_JNI )

ErrCode CDateBundle::setDatesArray( JNIEnv *pEnv, JINTARRAY pDatesArray )
{
	if ( pDatesArray == NULL || GETINTARRAYLEN( pDatesArray ) < kDatesArrayLen )
		return kErrParamErr;

#if defined( __linux__ )
	SETINTARRAYREGION( pDatesArray, 0, kDatesArrayLen, (int*) &datesArray[ 0 ] );
#else
	SETINTARRAYREGION( pDatesArray, 0, kDatesArrayLen, &datesArray[ 0 ] );
#endif

	return kErrNoErr;
}

#elif defined( DO_RNI1 ) || defined( DO_RNI2 )

ErrCode CDateBundle::setDatesArray( JINTARRAY pDatesArray )
{
	if ( pDatesArray == NULL || GETINTARRAYLEN( pDatesArray ) < kDatesArrayLen )
		return kErrParamErr;

	SETINTARRAYREGION( pDatesArray, 0, kDatesArrayLen, &datesArray[ 0 ] );

	return kErrNoErr;
}

#elif defined(DO_JRI)

ErrCode CDateBundle::setDatesArray( JRIEnv *pEnv, JINTARRAY pDatesArray )
{
	if ( pDatesArray == NULL || GETINTARRAYLEN( pDatesArray ) < kDatesArrayLen )
		return kErrParamErr;

	SETINTARRAYREGION( pDatesArray, 0, kDatesArrayLen, &datesArray[ 0 ] );

	return kErrNoErr;
}

#endif

#if defined(macintosh) || defined(__osx__)

#if defined( DO_JNI )

CDateBundle::CDateBundle( JNIEnv *pEnv, JINTARRAY pDatesArray )
{
	if ( pDatesArray == NULL || GETINTARRAYLEN( pDatesArray ) < kDatesArrayLen )
		throw _TXL( "bad array in CDB" );

	GETINTARRAYREGION( pDatesArray, 0, kDatesArrayLen, &datesArray[ 0 ] );
}

#elif defined(DO_JRI)

CDateBundle::CDateBundle( JRIEnv *pEnv, JINTARRAY pDatesArray )
{
	if ( pDatesArray == NULL || GETINTARRAYLEN( pDatesArray ) < kDatesArrayLen )
		throw _TXL( "bad array in CDB" );

	GETINTARRAYREGION( pDatesArray, 0, kDatesArrayLen, &datesArray[ 0 ] );
}

#endif

#endif

long CDateBundle::getDatesArrayOffset( eDateBundleSelector which )
{
	if ( which == kModificationDate )
		return kModDateOffset;
	else if ( which == kCreationDate )
		return kCreationDateOffset;
	else if ( which == kAccessDate )
		return kAccessDateOffset;
	else
		return -1;
}

ErrCode CDateBundle::setDate( eDateBundleSelector which, long yr, long mo, long dy, long hh, long mm, long ss )
{
	long			offset;

	offset = getDatesArrayOffset( which );
	if ( offset < 0 )
		return kErrParamErr;

	datesArray[ offset + 0 ] = yr;
	datesArray[ offset + 1 ] = mo;
	datesArray[ offset + 2 ] = dy;

	datesArray[ offset + 3 ] = hh;
	datesArray[ offset + 4 ] = mm;
	datesArray[ offset + 5 ] = ss;

	return kErrNoErr;
}

#if defined(_WIN32)

ErrCode CDateBundle::setDate( eDateBundleSelector which, FILETIME *lpFileTime )
{
	FILETIME		localFileTime;
	SYSTEMTIME		systemTime;
	long			offset;

	offset = getDatesArrayOffset( which );
	if ( offset < 0 )
		return kErrParamErr;

	if ( !FileTimeToLocalFileTime( lpFileTime, &localFileTime ) )
		return GetLastError();

	if ( !FileTimeToSystemTime( &localFileTime, &systemTime ) )
		return GetLastError();

	datesArray[ offset + 0 ] = systemTime.wYear;
	datesArray[ offset + 1 ] = systemTime.wMonth;
	datesArray[ offset + 2 ] = systemTime.wDay;

	datesArray[ offset + 3 ] = systemTime.wHour;
	datesArray[ offset + 4 ] = systemTime.wMinute;
	datesArray[ offset + 5 ] = systemTime.wSecond;

	return kErrNoErr;
}

#elif defined(__linux__)

ErrCode CDateBundle::setDate( eDateBundleSelector which, time_t *lpFileTime )
{
	struct tm		*tmP;
	long			offset;

	offset = getDatesArrayOffset( which );
	if ( offset < 0 )
		return kErrParamErr;

	tmP = localtime( lpFileTime );
	if ( tmP == NULL )
		return -1;

	datesArray[ offset + 0 ] = tmP->tm_year + 1900;
	datesArray[ offset + 1 ] = tmP->tm_mon + 1;
	datesArray[ offset + 2 ] = tmP->tm_mday;

	datesArray[ offset + 3 ] = tmP->tm_hour;
	datesArray[ offset + 4 ] = tmP->tm_min;
	datesArray[ offset + 5 ] = tmP->tm_sec;

	return kErrNoErr;
}

#elif defined(macintosh) || defined(__osx__)

ErrCode CDateBundle::getDate( eDateBundleSelector which, unsigned long *macDateP )
{
	DateTimeRec		dateTimeRec;
	long			offset;

	offset = getDatesArrayOffset( which );
	if ( offset < 0 )
		return kErrParamErr;

	if ( datesArray[ offset ] == 0 )
		return kErrParamErr;

	dateTimeRec.year =		datesArray[ offset + 0 ];
	dateTimeRec.month =		datesArray[ offset + 1 ];
	dateTimeRec.day =		datesArray[ offset + 2 ];
	dateTimeRec.hour =		datesArray[ offset + 3 ];
	dateTimeRec.minute =	datesArray[ offset + 4 ];
	dateTimeRec.second =	datesArray[ offset + 5 ];

	DateToSeconds( &dateTimeRec, macDateP );

	return kErrNoErr;
}

ErrCode CDateBundle::setDate( eDateBundleSelector which, unsigned long macDate )
{
	DateTimeRec		dateTimeRec;
	long			offset;

	offset = getDatesArrayOffset( which );
	if ( offset < 0 )
		return kErrParamErr;

	SecondsToDate( macDate, &dateTimeRec );

	datesArray[ offset + 0 ] = dateTimeRec.year;
	datesArray[ offset + 1 ] = dateTimeRec.month;
	datesArray[ offset + 2 ] = dateTimeRec.day;

	datesArray[ offset + 3 ] = dateTimeRec.hour;
	datesArray[ offset + 4 ] = dateTimeRec.minute;
	datesArray[ offset + 5 ] = dateTimeRec.second;

	return kErrNoErr;
}

#endif



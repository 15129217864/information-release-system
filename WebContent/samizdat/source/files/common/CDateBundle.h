/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CDateBundle_H
#define INC_CDateBundle_H

#include "comdefs.h"
#include "jmacros.h"

#if defined(__linux__)
	#include <time.h>
#endif


/*------------------------------------------------------------------------
CLASS
	CDateBundle

	Contains the three dates associated with a file or volume: creation, modification, and access/backup.

DESCRIPTION

	Contains the three dates associated with a file or volume: creation, modification, and access/backup.
	The access date (windows/linux) is interchangeable with the backup date (Mac).
	Dates are passed between Java and native code in an array of 18 Java ints which contain the
	fields for each of the three dates.	The eDateBundleArray enum defines each of these offsets.


MACROS

<PRE>

DECLAREDATEBUNDLE(a)
	Allocates space for a CDateBundle*, and sets it to null

DELETEDATEBUNDLE(a)
	If 'a' isn't null, calls 'delete' on it.

MAKEDATEBUNDLE(a,b)
	Constructs a CDateBundle from a JINTARRAY
	'a' is a CDateBundle*
	'b' is the JINTARRAY from which all the dates will be retrieved

SLAMDATEBUNDLE(a,b)
	Copies all the dates of the CDateBundle into a JINTARRAY
	'a' is a CDateBundle*
	'b' is the JINTARRAY to which the dates will be written

</PRE>

------------------------------------------------------------------------*/

class CDateBundle {
public:

		///////////////////////
		//
		//  The selector values used with the getDate()/setDate() methods
		//
	typedef enum tageDateBundleSelector {
		kModificationDate = 0,
		kCreationDate = 1,
		kAccessDate = 2,
		kBackupDate = kAccessDate
	} eDateBundleSelector;

		///////////////////////
		//
		//  The minimum length of the jint array set by the	setDatesArray() method,
		//	and the offsets in this array of each of the dates.
		//
	enum {
		kDatesArrayLen = 18,
		kModDateOffset = 0,
		kCreationDateOffset = 6,
		kAccessDateOffset = 12,
		kBackupDateOffset = kAccessDateOffset
	} eDateBundleArray;

		///////////////////////
		//
		//  Construct with null dates.
		//
	CDateBundle();

		///////////////////////
		//
		//  Destructor
		//
	virtual	~CDateBundle();

#if defined(macintosh) || defined(__osx__)

	#if defined(DO_JNI)

			///////////////////////
			//
			//  Construct from an array of jints.
			//	Only available in Mac JNI builds.
			//
		CDateBundle( JNIEnv *pEnv, JINTARRAY pDatesArray );

	#elif defined(DO_JRI)

			///////////////////////
			//
			//  Construct from an array of jints.
			//	Only available in Mac JRI builds.
			//
		CDateBundle( JRIEnv *pEnv, JINTARRAY pDatesArray );

	#endif

#endif

#if defined( DO_JNI )

		///////////////////////
		//
		//  Put the dates into the given array of Java ints.
		//	Only available in JNI builds.
		//
	virtual ErrCode	setDatesArray( JNIEnv *pEnv, JINTARRAY pDatesArray );

#elif ( defined( DO_RNI1 ) || defined( DO_RNI2 ) )

		///////////////////////
		//
		//  Put the dates into the given array of Java ints.
		//	Only available in RNI builds.
		//
	virtual ErrCode	setDatesArray( JINTARRAY pDatesArray );

#elif defined(DO_JRI)

		///////////////////////
		//
		//  Put the dates into the given array of Java ints.
		//	Only available in JRI builds.
		//
	virtual ErrCode	setDatesArray( JRIEnv *pEnv, JINTARRAY pDatesArray );

#endif

		///////////////////////
		//
		//  Set the indicated date. Values are in absolute, 1-based form ( i.e., 1970, 1==Jan, etc. )
		//
	virtual	ErrCode	setDate( eDateBundleSelector which, long yr, long mo, long dy, long hh, long mm, long ss );

#if defined(WIN32)

		///////////////////////
		//
		//  Set the indicated date from a FILETIME structure. Only on Windows.
		//
	virtual	ErrCode	setDate( eDateBundleSelector which, FILETIME *fileTime );

#elif defined(__linux__)

		///////////////////////
		//
		//  Set the indicated date from a time_t structure. Only on Linux
		//
	virtual	ErrCode	setDate( eDateBundleSelector which, time_t *fileTime );

#elif defined(macintosh) || defined(__osx__)

		///////////////////////
		//
		//  Put the indicated date into macDate. If the indicated date is not valid,
		//	or if 'which' is not valid, just returns an error code.
		//	Only on Mac.
		//
	virtual	ErrCode	getDate( eDateBundleSelector which, unsigned long *macDate );

		///////////////////////
		//
		//  Set the indicated date from a Mac date. If 'which' is not valid, just
		//	returns an error code.
		//	Only on Mac.
		//
	virtual	ErrCode	setDate( eDateBundleSelector which, unsigned long macDate );

#endif

private:

	long			getDatesArrayOffset( eDateBundleSelector which );

	long		datesArray[ kDatesArrayLen ];
};


#define	DECLAREDATEBUNDLE(a)		CDateBundle	*a = NULL
#define	DELETEDATEBUNDLE(a)			{ if ( a != NULL ) delete a; }

#if defined( DO_JNI ) || defined(DO_JRI)

    #define	MAKEDATEBUNDLE(a,b)		(a) = new CDateBundle( pEnv, b )
    #define	SLAMDATEBUNDLE(a,b)		(a)->setDatesArray( pEnv, b )

#elif defined( DO_RNI1 ) || defined( DO_RNI2 )

    #define	MAKEDATEBUNDLE(a,b)		(a) = new CDateBundle( b )
    #define	SLAMDATEBUNDLE(a,b)		(a)->setDatesArray( b )

#endif


#endif


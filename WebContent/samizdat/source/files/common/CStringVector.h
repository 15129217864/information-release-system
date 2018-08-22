/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CStringVector_H
#define INC_CStringVector_H

#if defined(__linux__)
	#include <jni.h>
#endif

#include "comdefs.h"
#include "CString.h"
#include "CVector.h"
#include "jmacros.h"


/*------------------------------------------------------------------------
CLASS
	CStringVector

	Holds a vector of CStr's.

DESCRIPTION
	Holds a vector of CStr's.
	Can be created from an array of Java Strings, and the slamInto() method puts the strings in the vector into
	an array of Java Strings.
	A set of macros are provided to make it easier to work with this class.

MACROS

<PRE>

DECLARESTRINGVECTOR(a)
	Allocates space for a CStringVector*, and sets it to null

DELETESTRINGVECTOR(a)
	If 'a' isn't null, calls 'delete' on it.

MAKESTRINGVECTOR(a,b,c)
	Constructs a CStringVector from an array of Java strings (a JOBJECTARRAY)
	'a' is the number of strings to take from the array
	'b' is the JOBJECTARRAY from which the strings will be retrieved
	'c' is a CStringVector*

SLAMSTRINGVECTOR(a,b)
	Creates Java strings from the strings in the array, and puts each into a JOBJECTARRAY
	'a' is a CStringVector*
	'b' is the JOBJECTARRAY to which the strings will be written

</PRE>

------------------------------------------------------------------------*/

class CStringVector {
public:

		///////////////////////
		//
		//  Construct, starting with the given number of elements.
		//
	CStringVector( long max );

#if defined( DO_JNI )

		///////////////////////
		//
		//  Construct using an array of JNI Strings. Only the first 'numStrs' will be used.
		//	Only available in JNI builds.
		//
	CStringVector( JNIEnv *pEnv, long numStrs, JOBJECTARRAY pStrs );

		///////////////////////
		//
		//  Construct using an array of JNI Strings.
		//	Only available in JNI builds.
		//
	CStringVector( JNIEnv *pEnv, JOBJECTARRAY pStrs );

		///////////////////////
		//
		//  Create JNI Strings from the CStr's in this object, and put them
		//	in the given JNI String array.
		//	Only available in JNI builds.
		//
	virtual	void	slamInto( JNIEnv *pEnv, JOBJECTARRAY pStrs );

#elif defined( DO_RNI1 ) || defined( DO_RNI2 )

		///////////////////////
		//
		//  Construct using an array of RNI Strings. Only the first 'numStrs' will be used.
		//	Only available in RNI builds.
		//
	CStringVector( long numStrs, JOBJECTARRAY pStrs );

		///////////////////////
		//
		//  Construct using an array of RNI Strings.
		//	Only available in RNI builds.
		//
	CStringVector( JOBJECTARRAY pStrs );

		///////////////////////
		//
		//  Create RNI Strings from the CStr's in this object, and put them
		//	in the given RNI String array.
		//	Only available in RNI builds.
		//
	virtual	void	slamInto( JOBJECTARRAY pStrs );

#endif

		///////////////////////
		//
		//  Destructor
		//
	virtual	~CStringVector();


		///////////////////////
		//
		//  Return the number of strings in this array.
		//
	virtual	long	getNumStrings();

		///////////////////////
		//
		//  Append a series of strings from a packed string.
		//	See the CStrA getPackedString() method for the definition of a packed string.
		//
	virtual	void	appendPackedString( const CStr *ps );

		///////////////////////
		//
		//  Returns the i'th string.
		//	Returns NULL if there's no such index.
		//
	virtual	CStr	*getString( long i );

		///////////////////////
		//
		//  Set the i'th string.
		//
	virtual	void	setString( CStr *cs, long i );

		///////////////////////
		//
		//  Append the given string.
		//
	virtual	void	appendString( CStr *cs );

		///////////////////////
		//
		//  Remove the given string.
		//
	virtual	void	removeString( CStr *cs );

		///////////////////////
		//
		//  Returns whether this object contains a string matching 'cs'. Uses CStr::isEqual().
		//
	virtual	BOOL contains( const CStr *cs );

		///////////////////////
		//
		//  Returns whether this object contains a string matching 'cs'. Uses CStr::isEqualIgnoreCase().
		//
	virtual	BOOL containsIgnoreCase( const CStr *cs );

		///////////////////////
		//
		//  Write info on the strings to Debugger::debug.
		//
	virtual	void	dumpInfo();

private:

#if defined( DO_JNI )

	void initFrom( JNIEnv *pEnv, long numArgs, JOBJECTARRAY pArgs );

#elif defined( DO_RNI1 ) || defined( DO_RNI2 )

	void initFrom( long numArgs, JOBJECTARRAY pArgs );

#endif

protected:
	CVector			*vec;
};


#if defined( DO_JNI )

    #define	DECLARESTRINGVECTOR(a)		CStringVector	*a = NULL
    #define	DELETESTRINGVECTOR(a)		{ if ( a != NULL ) delete a; }
    #define	SLAMSTRINGVECTOR(a,b)		(a)->slamInto( pEnv, b )
	#define	MAKESTRINGVECTOR(a,b,c)	{										\
		if ( a < 0 || b == NULL ) { theErr = kErrParamErr; goto bail; }		\
		try {																\
			c = new CStringVector( pEnv, a, b );							\
		}																	\
		catch ( LPCTSTR e ) {												\
			Debugger::debug( __LINE__, _TXL( "got exception" ), e );		\
			theErr = kErrCantCreateStringVector;							\
			goto bail;														\
		}																	\
	}

#elif defined( DO_RNI1 ) || defined( DO_RNI2 )

    #define	DECLARESTRINGVECTOR(a)		CStringVector	*a = NULL
    #define	DELETESTRINGVECTOR(a)		{ if ( a != NULL ) delete a; }
    #define	SLAMSTRINGVECTOR(a,b)		(a)->slamInto( b )
	#define	MAKESTRINGVECTOR(a,b,c)	{										\
		if ( a < 0 || b == NULL ) { theErr = kErrParamErr; goto bail; }		\
		try {																\
			c = new CStringVector( a, b );									\
		}																	\
		catch ( LPCTSTR e ) {												\
			Debugger::debug( __LINE__, _TXL( "got exception" ), e );		\
			theErr = kErrCantCreateStringVector;							\
			goto bail;														\
		}																	\
	}

#endif


#endif


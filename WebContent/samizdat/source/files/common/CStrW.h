/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CStrW_H
#define INC_CStrW_H

#include "comdefs.h"

#if defined( DO_JNI ) || defined( DO_JRI ) || defined( DO_RNI1 ) || defined( DO_RNI2 )
	#include <native.h>
#endif

/*------------------------------------------------------------------------
CLASS
	CStrW

	Represents a Unicode string.

DESCRIPTION
	Represents a Unicode string.
	This class can be created from a Java string, and a Java string can be created from objects of this class.
	A set of macros are used to make it easier to work with these objects.
	See the 'string.html' file for more information.

MACROS
	These macros are defined in CString.h, and are defined using CStr, not CStrW
<PRE>

DECLARESTR(a)
	Allocates space for a CStr*, and sets it to null

MAKESTR(a,b)
	Constructs a CStr from a Java String. If 'a' is null, or if an exception
	occurs, sets 'theErr' and jumps to 'bail'
	'a' is the Java string ( JSTRING )
	'b' is the CStr*

DELETESTR(a)
	If 'a' isn't null, calls 'delete' on it.

</PRE>

------------------------------------------------------------------------*/

class CStrW {
public:

#if defined( DO_JNI )

		/////////////////////////////
		//
		//  Construct from an RNI string. Only available in RNI builds.
		//
	CStrW( JNIEnv *pEnv, jstring pStr );
#endif

#if defined( DO_RNI1 ) || defined( DO_RNI2 )

		/////////////////////////////
		//
		//  Construct from a JNI String. Only available in JNI builds.
		//
	CStrW( Hjava_lang_String *ps );
#endif

		/////////////////////////////
		//
		//  Construct with an internal buffer able to hold 'len' characters
		//
	CStrW( long len );

		/////////////////////////////
		//
		//  Construct from a wchar_t string
		//
	CStrW( const wchar_t *s );

		/////////////////////////////
		//
		//  Construct from a C string, converting with MultiByteToWideChar().
		//
	CStrW( const char *s );

		/////////////////////////////
		//
		//  Construct from another CStrW.
		//
	CStrW( const CStrW *cs );

		/////////////////////////////
		//
		//  Destructor.
		//
	virtual	~CStrW();
	
#if defined( DO_RNI1 ) || defined( DO_RNI2 )

		/////////////////////////////
		//
		//  Convert to an RNI String. Only available in RNI builds.
		//
	virtual	Hjava_lang_String	*getJString();
#endif

#if defined( DO_JNI )

		/////////////////////////////
		//
		//  Convert to a JNI String.  Only available in JNI builds.
		//
	virtual	jstring	getJString( JNIEnv *pEnv );
#endif

		///////////////////////
		//
		//  Returns a pointer to the character buffer.
		//
	virtual	wchar_t	*getBuf() const;

		///////////////////////
		//
		//  Returns the length of the string.
		//
	virtual	long	getLength() const;

		///////////////////////
		//
		//  Converts the string to upper case.
		//
	virtual	void	toUpper();

		///////////////////////
		//
		//  Converts the string to lower case.
		//
	virtual	void	toLower();

		///////////////////////
		//
		//  Copies the given string into this object, expanding the internal buffer as necessary.
		//
		//  [in]	s		The string to copy into this object.
		//
	virtual	void	setBuf( const wchar_t *s );

		///////////////////////
		//
		//  Copies the given string into this object, expanding the internal buffer as necessary.
		//
		//  [in]	cs		The string to copy into this object.
		//
	virtual	void	setBuf( const CStrW *cs );

		///////////////////////
		//
		//  Ensures that this object's internal buffer can contain the given number of characters.
		//
		//  [in]	cap			The new number of characters.
		//
	virtual	void	ensureCharCapacity( long cap );

		///////////////////////
		//
		//  Returns the number of characters this object's buffer can hold.
		//
	virtual	long	getCharCapacity() const;

		///////////////////////
		//
		//  Returns the number of bytes this object's buffer can hold.
		//
	virtual	long	getByteCapacity() const;

		///////////////////////
		//
		//  Concatenates the given string, expanding the internal buffer as necessary.
		//
		//  [in]	s		The string to concat.
		//
	virtual	void	concat( const wchar_t *s );

		///////////////////////
		//
		//  Concatenates the given string, expanding the internal buffer as necessary.
		//
		//  [in]	cs		The string to concat.
		//
	virtual	void	concat( const CStrW *cs );

		///////////////////////
		//
		//  Returns TRUE if this string contains 'cs', FALSE otherwise. Case significant.
		//
		//  [in]	cs		The string to be searched for.
		//
	virtual	BOOL	contains( const CStrW *cs ) const;

		///////////////////////
		//
		//  Returns TRUE if this string contains 's', FALSE otherwise. Case significant.
		//
		//  [in]	s		The string to be searched for.
		//
	virtual	BOOL	contains( const wchar_t *s ) const;

		///////////////////////
		//
		//  Returns TRUE if this string starts with the given string, FALSE otherwise.
		//
		//  [in]	cs		The string to search for.
		//
	virtual	BOOL	startsWith( const CStrW *cs ) const;

		///////////////////////
		//
		//  Returns TRUE if this string starts with the given string, FALSE otherwise.
		//
		//  [in]	s		The string to search for.
		//
	virtual	BOOL	startsWith( const wchar_t *s ) const;

		///////////////////////
		//
		//  Returns TRUE if this string equals the given string, FALSE otherwise.
		//
		//  [in]	cs		The string to compare.
		//
	virtual	BOOL	isEqual( const CStrW *cs ) const;

		///////////////////////
		//
		//  Returns TRUE if this string equals the given string, FALSE otherwise.
		//
		//  [in]	s		The string to compare.
		//
	virtual	BOOL	isEqual( const wchar_t *s ) const;

		///////////////////////
		//
		//  Returns TRUE if this string equals the given string, FALSE otherwise. Not case sensitive.
		//
		//  [in]	cs		The string to compare.
		//
	virtual	BOOL	isEqualIgnoreCase( const CStrW *cs ) const;

		///////////////////////
		//
		//  Returns TRUE if this string equals the given string, FALSE otherwise. Not case sensitive.
		//
		//  [in]	s		The string to compare.
		//
	virtual	BOOL	isEqualIgnoreCase( const wchar_t *s ) const;

		///////////////////////
		//
		//  Searches for a substring, and, if found, replaces with another string.
		//
		//  [in]	startIndex		The index in this string at which to start searching.
		//  [in]	searchString	The string to be searched for.
		//  [in]	csReplace		If the string is found, it will be replaced with this string.
		//
	virtual	long	replaceFrom( long startIndex, const wchar_t *searchString, const CStrW *csReplace );

		///////////////////////
		//
		//  If this string contains the full path of an .exe file, places it in csDest. Returns TRUE
		//	if it succeeds, FALSE otherwise.
		//
		//  [out]	csDest			Destination of the exe file name.
		//
	virtual	BOOL	extractExeFileName( CStrW *csDest );

		///////////////////////
		//
		//  Uses sprintf to write an integer into this string. The string must be at least 10 characters
		//	long.
		//
		//  [in]	lpszFormat		The printf-style format of the integer.
		//  [in]	num				The integer.
		//
	virtual	void	formatInt( const wchar_t *lpszFormat, long num );

		///////////////////////
		//
		//  Returns the number of strings in the given packed string.
		//	See getPackedString for a description of the format of packed strings.
		//
		//  [in]	ps		The packed string.
		//
	static	long	countPackedStrings( const CStrW *ps );

		///////////////////////
		//
		//  Returns a CStrW containing the i'th string in the given packed string.
		//	Returns NULL if that string could not be found.
		//	A "packed" string consists of a series of null-terminated c strings, followed by a null character.
		//	If <null> represents the null character, a packed string containing "a", "b", and "c" looks like:
		//	a<null>b<null>c<null><null>
		//	The packed string methods are provided for use with such WinAPI routines as GetLogicalDriveStrings()
		//
		//  [in]	ps		The packed string.
		//	[in]	i		Which string to return; 0-based.
		//
	static	CStrW	*getPackedString( const CStrW *ps, long i );

	enum {
		kMaxPath = ( MAX_PATH + 256 )
	};

protected:
	virtual	void	init( const wchar_t *strP, long strLen, LPCTSTR lpException );

	wchar_t			*buf;
	long			bufLen;

};

#endif




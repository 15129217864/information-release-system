/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CStrA_H
#define INC_CStrA_H

#include "comdefs.h"

#if defined( DO_JNI ) || defined( DO_JRI ) || defined( DO_RNI1 ) || defined( DO_RNI2 )
	#if defined(_WIN32)
		#include <native.h>
	#elif defined(__linux__)
		#include <jni.h>
	#elif defined(macintosh)
		#if defined(DO_JNI)
			#include <jni.h>
		#elif defined(DO_JRI)
			#include <jri.h>
		#endif
		#include <wchar_t.h>
	#elif defined(__osx__)
		#if defined(DO_JNI)
			#include <jni.h>
		#elif defined(DO_JRI)
			#include <jri.h>
		#endif
		#include <sys/param.h>
	#endif
#endif

/*------------------------------------------------------------------------
CLASS
	CStrA

	Represents an ASCII/MBCS/UTF string.

DESCRIPTION
	Represents an ASCII/MBCS/UTF string.
	This class can be created from a Java string, and a Java string can be created from objects of this class.
	A set of macros are used to make it easier to work with these objects.
	See the 'string.html' file for more information.

MACROS
	These macros are defined in CString.h, and are defined using CStr, not CStrA
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

class CStrA {
public:

#if defined(DO_JNI)

		/////////////////////////////
		//
		//  Construct from an JNI string. Only available in JNI builds.
		//
	CStrA( JNIEnv *pEnv, jstring pStr );

#elif defined(DO_JRI)

		/////////////////////////////
		//
		//  Construct from an JRI string. Only available in JRI builds.
		//
	CStrA( JRIEnv *pEnv, JRIStringID pStr );

#elif defined( DO_RNI1 ) || defined( DO_RNI2 )

		/////////////////////////////
		//
		//  Construct from an RNI string. Only available in RNI builds.
		//
	CStrA( Hjava_lang_String *ps );

#endif

		/////////////////////////////
		//
		//  Construct with an internal buffer able to hold 'len' characters ( buffer may be > 'len' bytes ).
		//
	CStrA( long len );

		/////////////////////////////
		//
		//  Construct from a C string
		//
	CStrA( const char *s );


#if defined(macintosh) || defined(__osx__)

		/////////////////////////////
		//
		//  Construct from a Pascal string
		//
	CStrA( const StringPtr s );

#endif

#if defined(_WIN32)

		/////////////////////////////
		//
		//  Construct from a Unicode string, converting with WideCharToMultiByte().
		//	Only available in win builds.
		//
	CStrA( const wchar_t *s );

#endif

		/////////////////////////////
		//
		//  Construct from another CStrA.
		//
	CStrA( const CStrA *cs );

		/////////////////////////////
		//
		//  Destructor.
		//
	virtual	~CStrA();
	
#if defined(DO_JNI)

		/////////////////////////////
		//
		//  Convert to an JNI String. Only available in JNI builds.
		//
	virtual	jstring	getJString( JNIEnv *pEnv );

#elif defined(DO_JRI)

		/////////////////////////////
		//
		//  Convert to an JRI String. Only available in JRI builds.
		//
	virtual	JRIStringID	getJString( JRIEnv *pEnv );

#elif defined( DO_RNI1 ) || defined( DO_RNI2 )

		/////////////////////////////
		//
		//  Convert to an RNI String. Only available in RNI builds.
		//
	virtual	Hjava_lang_String *getJString();

#endif

		///////////////////////
		//
		//  Returns a pointer to the character buffer.
		//
	virtual	char	*getBuf() const;

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
		//  [in]	s		The null-terminated string to copy into this object.
		//
	virtual	void	setBuf( const char *s );

		///////////////////////
		//
		//  Copies the given CStrA into this object, expanding the internal buffer as necessary.
		//
		//  [in]	cs		The buffer of this object will be copied into this object.
		//
	virtual	void	setBuf( const CStrA *cs );

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
	virtual	void	concat( const char *s );

		///////////////////////
		//
		//  Concatenates the given string, expanding the internal buffer as necessary.
		//
		//  [in]	s		The string to concat.
		//
	virtual	void	concat( const CStrA *cs );

		///////////////////////
		//
		//  Shortens this string.
		//
		//  [in]	wh		The new end location of this string
		//
	virtual	void	truncateAt( long wh );

		///////////////////////
		//
		//  Returns TRUE if this string contains 'cs', FALSE otherwise. Case significant.
		//
		//  [in]	cs		The string to be searched for.
		//
	virtual	BOOL	contains( const CStrA *cs ) const;

		///////////////////////
		//
		//  Returns TRUE if this string contains 's', FALSE otherwise. Case significant.
		//
		//  [in]	s		The string to be searched for.
		//
	virtual	BOOL	contains( const char *s ) const;

		///////////////////////
		//
		//  Returns TRUE if this string starts with the given string, FALSE otherwise.
		//
		//  [in]	cs		The string to search for.
		//
	virtual	BOOL	startsWith( const CStrA *cs ) const;

		///////////////////////
		//
		//  Returns TRUE if this string starts with the given string, FALSE otherwise.
		//
		//  [in]	s		The string to search for.
		//
	virtual	BOOL	startsWith( const char *s ) const;

		///////////////////////
		//
		//  Returns TRUE if this string equals the given string, FALSE otherwise. Case sensitive.
		//
		//  [in]	cs		The string to compare.
		//
	virtual	BOOL	isEqual( const CStrA *cs ) const;

		///////////////////////
		//
		//  Returns TRUE if this string equals the given string, FALSE otherwise. Case sensitive.
		//
		//  [in]	s		The string to compare.
		//
	virtual	BOOL	isEqual( const char *s ) const;

		///////////////////////
		//
		//  Returns TRUE if this string equals the given string, FALSE otherwise. Not case sensitive.
		//
		//  [in]	cs		The string to compare.
		//
	virtual	BOOL	isEqualIgnoreCase( const CStrA *cs ) const;

		///////////////////////
		//
		//  Returns TRUE if this string equals the given string, FALSE otherwise. Not case sensitive.
		//
		//  [in]	s		The string to compare.
		//
	virtual	BOOL	isEqualIgnoreCase( const char *s ) const;

		///////////////////////
		//
		//  Copies this string into the given character buffer, which is assumed to be long enough to
		//	hold this string.
		//
		//  [out]	s		Where to copy this string..
		//
	virtual	void	copyInto( char *s );

		///////////////////////
		//
		//  Searches for a substring, and, if found, replaces with another string. Returns -1
		//	if the string couldn't be found, a positive index otherwise. The index can be used with
		//	subsequent calls.
		//
		//  [in]	startIndex		The index in this string at which to start searching.
		//  [in]	searchString	The string to be searched for.
		//  [in]	csReplace		If the string is found, it will be replaced with this string.
		//
	virtual	long	replaceFrom( long startIndex, const char *searchString, const CStrA *csReplace );

		///////////////////////
		//
		//  Returns TRUE if this is a substring of the given string, FALSE otherwise.
		//
	virtual	BOOL	isSubstringOf( const char *s ) const;

		///////////////////////
		//
		//  If this string contains the full path of an .exe file, places it in csDest. Returns TRUE
		//	if it succeeds, FALSE otherwise.
		//
		//  [out]	csDest			Destination of the exe file name.
		//
	virtual	BOOL	extractExeFileName( CStrA *csDest );

		///////////////////////
		//
		//  Uses sprintf to write an integer into this string. The string must be at least 10 characters
		//	long.
		//
		//  [in]	lpszFormat		The printf-style format of the integer.
		//  [in]	num				The integer.
		//
	virtual	void	formatInt( const char *lpszFormat, long num );

		///////////////////////
		//
		//  Returns the number of strings in the given packed string.
		//	See getPackedString for a description of the format of packed strings.
		//
		//  [in]	ps		The packed string.
		//
	static	long	countPackedStrings( const CStrA *ps );

		///////////////////////
		//
		//  Returns a CStrA containing the i'th string in the given packed string.
		//	Returns NULL if that string could not be found.
		//	A "packed" string consists of a series of null-terminated c strings, followed by a null character.
		//	If <null> represents the null character, a packed string containing "a", "b", and "c" looks like:
		//	a<null>b<null>c<null><null>
		//	The packed string methods are provided for use with such WinAPI routines as GetLogicalDriveStrings()
		//
		//  [in]	ps		The packed string.
		//	[in]	i		Which string to return; 0-based.
		//
	static	CStrA	*getPackedString( const CStrA *ps, long i );

#if defined(MAX_PATH)
	enum {
		kMaxPath = ( MAX_PATH + 128 )
	};
#elif defined(MAXPATHLEN)
	enum {
		kMaxPath = ( MAXPATHLEN + 128 )
	};
#else
	enum {
		kMaxPath = 512
	};
#endif

private:
	void		init( const char *s, LPCTSTR lpException );
	void		init( const char *s, long strLen, LPCTSTR lpException );

#if defined(_WIN32)
	void		init( const wchar_t *wideP, long wideNumChars, LPCTSTR lpException );
	void		convertToWide( wchar_t **lplpWide, long *wideLen, LPCTSTR lpException );
#endif

	char			*buf;
	long			bufLen;
};


#endif


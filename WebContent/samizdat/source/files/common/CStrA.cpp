/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CStrA.h"
#include "CMemory.h"
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include "jmacros.h"

#define		NUMBYTESA(a)		( sizeof(char) * (a) )
#define		NUMBYTESW(a)		( sizeof(wchar_t) * (a) )

#if defined(_WIN32)
	#if defined(__MWERKS__)
		#define		STRSTR(a,b)		strstr( (a), (b) )
		#define		STRCAT(a,b)		lstrcatA( (a), (b) )
		#define		BYTELEN(a)		lstrlenA( a )
		#define		CHARLEN(a)		lstrlenA( a )
		#define		STRCPY(a,b)		lstrcpyA( (a), (b) )
		#define		STRCMP(a,b)		strcmp( (a), (b) )
		#define		STRICMP(a,b)	stricmp( (a), (b) )
		#define		STRLOWER(a)		CharLowerBuffA( (a), CHARLEN( (a) ) )
		#define		STRUPPER(a)		CharUpperBuffA( (a), CHARLEN( (a) ) )
		#define		SPRINTF(a,b,c)	wsprintfA( (a), (b), (c) )
	#else
		#include <mbstring.h>
		#define		STRSTR(a,b)		_mbsstr( (unsigned char*) (a), (unsigned char*) (b) )
		#define		STRCAT(a,b)		_mbscat( (unsigned char*) (a), (unsigned char*) (b) )
		#define		BYTELEN(a)		lstrlenA( (a) )
		#define		CHARLEN(a)		_mbslen( (unsigned char*) (a) )
		#define		STRCPY(a,b)		_mbscpy( (unsigned char*) (a), (unsigned char*) (b) )
		#define		STRICMP(a,b)	_mbsicmp( (unsigned char*) (a), (unsigned char*) (b) )
		#define		STRCMP(a,b)		_mbscmp( (unsigned char*) (a), (unsigned char*) (b) )
		#define		STRLOWER(a)		_mbslwr( (unsigned char*) (a) )
		#define		STRUPPER(a)		_mbsupr( (unsigned char*) (a) )
		#define		SPRINTF(a,b,c)	wsprintfA( (a), (b), (c) )
	#endif

#elif defined(macintosh) || defined(__osx__)

	//	_stricmp() was taken verbatim from metrowerks/win32/extras.c
	
	int _stricmp(const char *s1, const char *s2);
	int _stricmp(const char *s1, const char *s2)
	{
	    char c1, c2;
	    while (1)
	    {
	    	c1 = tolower(*s1++);
	    	c2 = tolower(*s2++);
	        if (c1 < c2) return -1;
	        if (c1 > c2) return 1;
	        if (c1 == 0) return 0;
	    }
	}
	
	#define		STRSTR(a,b)		strstr( (a), (b) )
	#define		STRCAT(a,b)		strcat( (a), (b) )
	#define		BYTELEN(a)		strlen( a )
	#define		CHARLEN(a)		strlen( a )
	#define		STRCPY(a,b)		strcpy( (a), (b) )
	#define		STRICMP(a,b)	_stricmp( (a), (b) )
	#define		STRCMP(a,b)		strcmp( (a), (b) )
	#define		STRLOWER(a)		{ char *s; for ( s = buf; *s; s++ ) *s = tolower( *s ); }
	#define		STRUPPER(a)		{ char *s; for ( s = buf; *s; s++ ) *s = toupper( *s ); }
	#define		CharNextA(a)	( a + 1 )
	#define		CharPrevA(a,b)	( b > a ? ( b - 1 ) : b )
	#define		SPRINTF(a,b,c)	sprintf( (a), (b), (c) )

#elif defined(__linux__)

	#define		STRSTR(a,b)		strstr( (a), (b) )
	#define		STRCAT(a,b)		strcat( (a), (b) )
	#define		BYTELEN(a)		strlen( a )
	#define		CHARLEN(a)		strlen( a )
	#define		STRCPY(a,b)		strcpy( (a), (b) )
	#define		STRICMP(a,b)	strcasecmp( (a), (b) )
	#define		STRCMP(a,b)		strcmp( (a), (b) )
	#define		STRLOWER(a)		{ char *s; for ( s = buf; *s; s++ ) *s = tolower( *s ); }
	#define		STRUPPER(a)		{ char *s; for ( s = buf; *s; s++ ) *s = toupper( *s ); }
	#define		CharNextA(a)	( a + 1 )
	#define		CharPrevA(a,b)	( b > a ? ( b - 1 ) : b )
	#define		SPRINTF(a,b,c)	sprintf( (a), (b), (c) )

#endif

#if defined( DO_JNI )

#if defined(_WIN32)
	CStrA::CStrA( JNIEnv *pEnv, jstring pStr )
	{
		const wchar_t		*wideP;
		long				wideNumChars;
		jboolean			bIsCopy;

		wideP = pEnv->GetStringChars( pStr, &bIsCopy );
		wideNumChars = pEnv->GetStringLength( pStr );

		init( wideP, wideNumChars, _TXL( "CStrA.jni" ) );

		pEnv->ReleaseStringChars( pStr, wideP );
	}

	jstring CStrA::getJString( JNIEnv *pEnv )
	{
		jstring		jStr;
		wchar_t		*tempWideBuf;
		long		tempWideLen;

		convertToWide( &tempWideBuf, &tempWideLen, _TXL( "CStrA.getJString" ) );

		jStr = pEnv->NewString( tempWideBuf, tempWideLen );

		CMemory::mfree( tempWideBuf );

		return jStr;
	}

#elif defined(__linux__) || defined(macintosh) || defined(__osx__)

	CStrA::CStrA( JNIEnv *pEnv, jstring pStr )
	{
		const char		*strP;
		jboolean		bIsCopy;

		strP = pEnv->GetStringUTFChars( pStr, &bIsCopy );

		init( strP, pEnv->GetStringUTFLength( pStr ), _TXL( "cstra" ) );

		pEnv->ReleaseStringUTFChars( pStr, strP );
	}

	jstring CStrA::getJString( JNIEnv *pEnv )
	{
		return pEnv->NewStringUTF( buf );
	}
	
#endif

#elif defined( DO_RNI1 ) || defined( DO_RNI2 )

CStrA::CStrA( Hjava_lang_String *ps )
{
	const wchar_t		*wideP;
	long				wideNumChars;

	#if defined( DO_RNI1 )
		wideP = &ps->value->body[ ps->offset ];
	#else
		wideP = javaStringStart( ps );
	#endif

	wideNumChars = javaStringLength( ps );

	init( wideP, wideNumChars, _TXL( "CStrA.rni" ) );
}

Hjava_lang_String *CStrA::getJString()
{
	Hjava_lang_String		*jStr;
	wchar_t					*tempWideBuf;
	long					tempWideLen;

	convertToWide( &tempWideBuf, &tempWideLen, _TXL( "CStrA.getJString" ) );

	jStr = makeJavaStringW( tempWideBuf, tempWideLen );

	CMemory::mfree( tempWideBuf );

	return jStr;
}

#elif defined(DO_JRI)

CStrA::CStrA( JRIEnv *pEnv, JRIStringID pStr )
{
	init( JRI_GetStringUTFChars( pEnv, pStr ),
			JRI_GetStringUTFLength( pEnv, pStr ),
			_TXL( "cstra" ) );
}

JRIStringID CStrA::getJString( JRIEnv *pEnv )
{
	return JRI_NewStringUTF( pEnv, buf, getLength() );
}

#endif

#if defined(_WIN32)

	CStrA::CStrA( const wchar_t *s )
	{
		init( s, -1, _TXL( "CStr bad ws" ) );
	}

#endif

CStrA::CStrA( long len )
{
	if ( len < 0 )
		throw _TXL( "CStrA bad len" );

	bufLen = len + 1;

	buf = (char*) CMemory::mmalloc( NUMBYTESA( bufLen ), _TXL( "CStrA malloc" ) );

	buf[ 0 ] = 0;
}

#if defined(macintosh) || defined(__osx__)

CStrA::CStrA( const StringPtr s )
{
	char		buf[ 256 ];
	
	memmove( buf, &s[ 1 ], s[ 0 ] );
	buf[ s[ 0 ] ] = 0;

	init( buf, _TXL( "CStr bad s" ) );
}

#endif

CStrA::CStrA( const char *s )
{
	init( s, _TXL( "CStr bad s" ) );
}

CStrA::CStrA( const CStrA *cs )
{
	if ( cs == NULL || cs->bufLen < 0 )
		throw _TXL( "CStr bad cs" );

	init( cs->getBuf(), _TXL( "CStr bad cs" ) );
}

CStrA::~CStrA()
{
	if ( buf != NULL )
		CMemory::mfree( buf );
}

BOOL CStrA::isSubstringOf( const char *s ) const
{
	return ( strstr( s, buf ) != NULL );
}

long CStrA::replaceFrom( long startIndex, const char *searchString, const CStrA *csReplace )
{
	CStrA		*csRemainder;
	char		*insertionP, *startP, *remainderP;
	long		newStartIndex, i;

	if ( startIndex < 0 )
		return -1;

	for ( i = 0, startP = buf; i < startIndex; i++ ) {
		if ( *startP == 0 )
			return -1;

		startP = CharNextA( startP );
	}

	insertionP = (char*) STRSTR( startP, searchString );
	if ( insertionP == NULL )
		return -1;

	remainderP = insertionP + BYTELEN( searchString );
	csRemainder = new CStrA( remainderP );

	*insertionP = 0;

	concat( csReplace );
	
	newStartIndex = CHARLEN( buf );

	concat( csRemainder );

	return newStartIndex;
}

void CStrA::setBuf( const CStrA *cs )
{
	if ( cs == NULL )
		return;

	setBuf( cs->getBuf() );
}

void CStrA::concat( const CStrA *cs )
{
	if ( cs == NULL )
		throw _TXL( "CStr concat.cs==null" );

	concat( cs->buf );
}

void CStrA::concat( const char *s )
{
	long		newBufLen;

	if ( s == NULL )
		throw _TXL( "CStr concat.s==null" );

	newBufLen = bufLen + BYTELEN( s );
	if ( newBufLen > bufLen ) {
		bufLen = newBufLen;
		buf = (char*) CMemory::mrealloc( buf, NUMBYTESA( bufLen ), _TXL( "CStr realloc" ) );
	}

	STRCAT( buf, s );
}

BOOL CStrA::contains( const CStrA *cs ) const
{
	if ( cs == NULL )
		return FALSE;

	return contains( cs->buf );
}

BOOL CStrA::contains( const char *s ) const
{
	if ( s == NULL )
		return FALSE;

	return ( ( (char*) STRSTR( buf, s ) ) != NULL );
}

BOOL CStrA::startsWith( const CStrA *cs ) const
{
	if ( cs == NULL )
		return FALSE;

	return startsWith( cs->buf );
}

BOOL CStrA::startsWith( const char *s ) const
{
	if ( s == NULL )
		return FALSE;

	return ( buf == (char*) STRSTR( buf, s ) );
}

BOOL CStrA::isEqual( const CStrA *cs ) const
{
	if ( cs == NULL )
		return FALSE;

	return isEqual( cs->buf );
}

BOOL CStrA::isEqual( const char *s ) const
{
	if ( s == NULL )
		return FALSE;

	return ( STRCMP( buf, s ) == 0 );
}

BOOL CStrA::isEqualIgnoreCase( const CStrA *cs ) const
{
	if ( cs == NULL )
		return FALSE;

	return isEqualIgnoreCase( cs->buf );
}

BOOL CStrA::isEqualIgnoreCase( const char *s ) const
{
	if ( s == NULL )
		return FALSE;

	return ( STRICMP( buf, s ) == 0 );
}

void CStrA::truncateAt( long wh )
{
	if ( wh >= 0 && wh <= getLength() )
		buf[ wh ] = 0;
}

long CStrA::getLength() const
{
	return CHARLEN( buf );
}

char *CStrA::getBuf() const
{
	return buf;
}

void CStrA::toLower()
{
	STRLOWER( buf );
}

void CStrA::toUpper()
{
	STRUPPER( buf );
}

void CStrA::setBuf( const char *s )
{
	long		newBufLen;

	if ( s == NULL )
		return;

	newBufLen = BYTELEN( s ) + 1;
	if ( newBufLen > bufLen ) {
		bufLen = newBufLen;
		buf = (char*) CMemory::mrealloc( buf, NUMBYTESA( bufLen ), _TXL( "CStr realloc" ) );
	}

	STRCPY( buf, s );
}

void CStrA::ensureCharCapacity( long cap )
{
	if ( cap + 1 > bufLen ) {
		bufLen = cap + 1;
		buf = (char*) CMemory::mrealloc( buf, NUMBYTESA( bufLen ), _TXL( "CStr realloc" ) );
	}
}

void CStrA::copyInto( char *s )
{
	strcpy( s, buf );
}

long CStrA::getCharCapacity() const
{
	if ( bufLen < 1 )
		return 0;

	return bufLen - 1;
}

long CStrA::getByteCapacity() const
{
	if ( bufLen < 1 )
		return 0;

	return bufLen - 1;
}

void CStrA::formatInt( const char *lpszFormat, long num )
{
	if ( bufLen < 10 )
		return;

	SPRINTF( buf, lpszFormat, num );
}

long CStrA::countPackedStrings( const CStrA *ps )
{
	char		*pps;
	long		curI, count;

	pps = ps->buf;

	for ( curI = 0, count = 0; pps[ curI ] != 0; count++ )
		curI += ( BYTELEN( &pps[ curI ] ) + 1 );

	return count;
}

CStrA *CStrA::getPackedString( const CStrA *ps, long which )
{
	char		*pps;
	long		curI, count;

	pps = ps->buf;

	for ( curI = 0, count = 0; pps[ curI ] != 0; count++ ) {
		if ( count == which )
			return new CStrA( &pps[ curI ] );

		curI += ( BYTELEN( &pps[ curI ] ) + 1 );
	}

	return NULL;
}

BOOL CStrA::extractExeFileName( CStrA *csDest )
{
	char		*startP, *endP;
	long		newDestBufLen;

	startP = (char*) STRSTR( buf, ":" );
	if ( startP == NULL || startP == buf )
		return FALSE;

	endP = (char*) STRSTR( buf, ".exe" );
	if ( endP == NULL )
		endP = (char*) STRSTR( buf, ".EXE" );

	if ( endP == NULL || startP >= endP )
		return FALSE;

	startP = CharPrevA( buf, startP );
	endP = CharNextA( endP );
	endP = CharNextA( endP );
	endP = CharNextA( endP );
	endP = CharNextA( endP );		//	endP --> char after final 'e' in ".exe"

	newDestBufLen = endP - startP + 1;
	if ( newDestBufLen > csDest->bufLen ) {
		csDest->bufLen = newDestBufLen;
		csDest->buf = (char*) CMemory::mrealloc( csDest->buf, NUMBYTESA( newDestBufLen ), _TXL( "CStr realloc" ) );
	}

	memmove( csDest->buf, startP, newDestBufLen - 1 );
	csDest->buf[ newDestBufLen - 1 ] = 0;

	return TRUE;
}

void CStrA::init( const char *s, long strLen, LPCTSTR lpException )
{
	if ( s == NULL || strLen < 0 )
		throw lpException;

	bufLen = strLen + 1;
	buf = (char*) CMemory::mmalloc( NUMBYTESA( bufLen ), lpException );

	STRCPY( buf, s );
}

void CStrA::init( const char *s, LPCTSTR lpException )
{
	if ( s == NULL )
		throw lpException;

	init( s, BYTELEN( s ), lpException );
}

#if defined(_WIN32)


void CStrA::init( const wchar_t *wideP, long wideNumChars, LPCTSTR lpException )
{
	long		numConverted;

	bufLen = WideCharToMultiByte( CP_ACP, 0, wideP, wideNumChars, NULL, 0, NULL, NULL ) + 1;
	if ( bufLen <= 0 )
		throw lpException;

	buf = (char*) CMemory::mmalloc( NUMBYTESA( bufLen ), lpException );

	numConverted = WideCharToMultiByte( CP_ACP, 0, wideP, wideNumChars, buf, bufLen - 1, NULL, NULL );
	if ( numConverted != ( bufLen - 1 ) )
		throw lpException;

	buf[ bufLen - 1 ] = 0;
}

void CStrA::convertToWide( wchar_t **lplpWide, long *wideLen, LPCTSTR lpException )
{
	wchar_t		*tempWideBuf;
	long		tempWideLen, numConverted;

	*lplpWide = NULL;
	*wideLen = 0;

	tempWideLen = MultiByteToWideChar( CP_ACP, MB_PRECOMPOSED, buf, -1, NULL, 0 );
	if ( tempWideLen < 0 )
		throw lpException;

	tempWideBuf = (wchar_t*) CMemory::mmalloc( NUMBYTESW( tempWideLen ), _TXL( "CStr malloc" ) );

	numConverted = MultiByteToWideChar( CP_ACP, MB_PRECOMPOSED, buf, -1, tempWideBuf, tempWideLen );
	if ( numConverted != tempWideLen ) {
		CMemory::mfree( tempWideBuf );
		throw lpException;
	}

	*lplpWide = tempWideBuf;
	*wideLen = tempWideLen - 1;
}

#endif



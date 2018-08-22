/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CStrW.h"
#include "CMemory.h"
#include <wchar.h>

#define		NUMBYTESW(a)		( sizeof(wchar_t) * (a) )

#if defined( DO_JNI )

CStrW::CStrW( JNIEnv *pEnv, jstring pStr )
{
	const wchar_t		*strP;
	long				strLen;
	jboolean			bIsCopy;

	if ( pEnv == NULL || pStr == NULL )
		throw _TXL( "CStr bad jni" );

	strLen = pEnv->GetStringLength( pStr );

	strP = pEnv->GetStringChars( pStr, &bIsCopy );

	init( strP, strLen, _TXL( "CStr bad jni" ) );

	pEnv->ReleaseStringChars( pStr, strP );
}

jstring CStrW::getJString( JNIEnv *pEnv )
{
	return pEnv->NewString( buf, getLength() );
}

#elif defined( DO_RNI2 ) || defined( DO_RNI1 )

CStrW::CStrW( Hjava_lang_String *ps )
{
	const wchar_t		*strP;
	long				strLen;

	if ( ps == NULL )
		throw _TXL( "CStr bad rni" );

	strLen = javaStringLength( ps );

#if defined( DO_RNI2 )
	strP = javaStringStart( ps );
#else
	strP = &ps->value->body[ ps->offset ];
#endif

	init( strP, strLen, _TXL( "CStr bad rni" ) );
}

Hjava_lang_String *CStrW::getJString()
{
	return makeJavaStringW( buf, getLength() );
}

#endif

CStrW::CStrW( long len )
{
	if ( len < 0 )
		throw _TXL( "CStr bad len" );

	bufLen = len + 1;

	buf = (wchar_t*) CMemory::mmalloc( NUMBYTESW( bufLen ), _TXL( "CStr malloc" ) );

	buf[ 0 ] = 0;
}

CStrW::CStrW( const wchar_t *s )
{
	if ( s == NULL )
		throw _TXL( "CStr bad s" );

	init( s, wcslen( s ), _TXL( "CStr bad s" ) );
}

CStrW::CStrW( const char *s )
{
	long		numConverted;

	bufLen = MultiByteToWideChar( CP_ACP, MB_PRECOMPOSED, s, -1, NULL, 0 );
	if ( bufLen == 0 )
		throw _TXL( "CStr WCTMB error1" );

	buf = (wchar_t*) CMemory::mmalloc( NUMBYTESW( bufLen ), _TXL( "CStr malloc" ) );

	numConverted = MultiByteToWideChar( CP_ACP, MB_PRECOMPOSED, s, -1, buf, bufLen );
	if ( numConverted != bufLen )
		throw _TXL( "CStr WCTMB error2" );
}

CStrW::CStrW( const CStrW *cs )
{
	wchar_t			*s;

	if ( cs == NULL )
		throw _TXL( "CStr bad cs" );

	s = cs->getBuf();
	
	if ( s == NULL )
		throw _TXL( "CStr bad cs" );

	init( s, wcslen( s ), _TXL( "CStr bad cs" ) );
}

CStrW::~CStrW()
{
	CMemory::mfree( buf );
}

void CStrW::setBuf( const CStrW *cs )
{
	if ( cs == NULL )
		throw _TXL( "CStr setBuf.cs==null" );

	setBuf( cs->getBuf() );
}

void CStrW::setBuf( const wchar_t *s )
{
	long		newBufLen;

	if ( s == NULL )
		throw _TXL( "CStr setBuf.s==null" );

	newBufLen = wcslen( s ) + 1;
	if ( newBufLen > bufLen ) {
		bufLen = newBufLen;
		buf = (wchar_t*) CMemory::mrealloc( buf, NUMBYTESW( bufLen ), _TXL( "CStr realloc" ) );
	}

	wcscpy( buf, s );
}

void CStrW::concat( const CStrW *cs )
{
	if ( cs == NULL )
		throw _TXL( "CStr concat.cs==null" );

	concat( cs->buf );
}

void CStrW::concat( const wchar_t *s )
{
	long		newBufLen;

	if ( s == NULL )
		throw _TXL( "CStr concat.s==null" );

	newBufLen = getLength() + wcslen( s ) + 1;
	if ( newBufLen > bufLen ) {
		bufLen = newBufLen;
		buf = (wchar_t*) CMemory::mrealloc( buf, NUMBYTESW( bufLen ), _TXL( "CStr realloc" ) );
	}

	wcscat( buf, s );
}

long CStrW::getLength() const
{
	return wcslen( buf );
}

wchar_t *CStrW::getBuf() const
{
	return buf;
}

void CStrW::toUpper()
{
	DWORD		len;

	len = CharUpperBuffW( buf, getLength() );
}

void CStrW::toLower()
{
	DWORD		len;

	len = CharLowerBuffW( buf, getLength() );
}

void CStrW::ensureCharCapacity( long cap )
{
	if ( cap + 1 > bufLen ) {
		bufLen = cap + 1;
		buf = (wchar_t*) CMemory::mrealloc( buf, NUMBYTESW( bufLen ), _TXL( "CStr realloc" ) );
	}
}

long CStrW::getCharCapacity() const
{
	if ( bufLen < 2 )
		return 0;

	return bufLen - 1;
}

long CStrW::getByteCapacity() const
{
	if ( bufLen < 2 )
		return 0;

	return NUMBYTESW( bufLen - 1 );
}

BOOL CStrW::extractExeFileName( CStrW *csDest )
{
	wchar_t		*startP, *endP;
	long		newDestBufLen;

	startP = wcsstr( buf, L":" );
	if ( startP == NULL || startP == buf )
		return FALSE;

	endP = wcsstr( buf, L".exe" );
	if ( endP == NULL )
		endP = wcsstr( buf, L".EXE" );

	if ( endP == NULL || startP >= endP )
		return FALSE;

	--startP;		//	startP --> drive letter
	endP += 4;		//	endP --> char after final 'e' in ".exe"

	newDestBufLen = endP - startP + 1;
	if ( newDestBufLen > csDest->bufLen ) {
		csDest->bufLen = newDestBufLen;
		csDest->buf = (wchar_t*) CMemory::mrealloc( csDest->buf, NUMBYTESW( newDestBufLen ), _TXL( "CStr realloc" ) );
	}

	memmove( csDest->buf, startP, NUMBYTESW( newDestBufLen - 1 ) );
	csDest->buf[ newDestBufLen - 1 ] = 0;

	return TRUE;
}

void CStrW::formatInt( const wchar_t *lpszFormat, long num )
{
	if ( bufLen < 10 )
		return;

	wsprintfW( buf, lpszFormat, num );
}

long CStrW::countPackedStrings( const CStrW *ps )
{
	wchar_t		*pps;
	long		curI, count;

	pps = ps->buf;

	for ( curI = 0, count = 0; pps[ curI ] != 0; count++ )
		curI += ( wcslen( &pps[ curI ] ) + 1 );

	return count;
}

CStrW *CStrW::getPackedString( const CStrW *ps, long which )
{
	wchar_t		*pps;
	long		curI, count;

	pps = ps->buf;

	for ( curI = 0, count = 0; pps[ curI ] != 0; count++ ) {
		if ( count == which )
			return new CStrW( &pps[ curI ] );

		curI += ( wcslen( &pps[ curI ] ) + 1 );
	}

	return NULL;
}

void CStrW::init( const wchar_t *strP, long strLen, LPCTSTR lpException )
{
	if ( strP == NULL || strLen < 0 )
		throw lpException;

	bufLen = strLen + 1;
	buf = (wchar_t*) CMemory::mmalloc( NUMBYTESW( bufLen ), lpException );

	wcsncpy( buf, strP, strLen );
	buf[ strLen ] = 0;
}

long CStrW::replaceFrom( long startIndex, const wchar_t *searchString, const CStrW *csReplace )
{
	CStrW		*csRemainder;
	wchar_t		*insertionP, *startP, *remainderP;
	long		newStartIndex, strLen;

	if ( startIndex < 0 )
		return -1;

	strLen = getLength();
	if ( startIndex >= strLen )
		return -1;

	startP = buf + startIndex;

	insertionP = (wchar_t*) wcsstr( startP, searchString );
	if ( insertionP == NULL )
		return -1;

	remainderP = insertionP + wcslen( searchString );
	csRemainder = new CStrW( remainderP );

	*insertionP = 0;

	concat( csReplace );
	
	newStartIndex = wcslen( buf );

	concat( csRemainder );

	return newStartIndex;
}

BOOL CStrW::startsWith( const CStrW *cs ) const
{
	if ( cs == NULL )
		return FALSE;

	return startsWith( cs->buf );
}

BOOL CStrW::startsWith( const wchar_t *s ) const
{
	if ( s == NULL )
		return FALSE;

	return ( buf == wcsstr( buf, s ) );
}

BOOL CStrW::contains( const CStrW *cs ) const
{
	if ( cs == NULL )
		return FALSE;

	return contains( cs->buf );
}

BOOL CStrW::contains( const wchar_t *s ) const
{
	wchar_t		*p;

	if ( s == NULL )
		return FALSE;

	p = wcsstr( buf, s );

	return ( p != NULL );
}

BOOL CStrW::isEqual( const CStrW *cs ) const
{
	if ( cs == NULL )
		return FALSE;

	return isEqual( cs->buf );
}

BOOL CStrW::isEqual( const wchar_t *s ) const
{
	if ( s == NULL )
		return FALSE;

	return ( wcscmp( buf, s ) == 0 );
}

BOOL CStrW::isEqualIgnoreCase( const CStrW *cs ) const
{
	if ( cs == NULL )
		return FALSE;

	return isEqualIgnoreCase( cs->buf );
}

BOOL CStrW::isEqualIgnoreCase( const wchar_t *s ) const
{
	if ( s == NULL )
		return FALSE;

	return ( _wcsicmp( buf, s ) == 0 );
}

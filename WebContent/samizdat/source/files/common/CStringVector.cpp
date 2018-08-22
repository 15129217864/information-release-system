/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CStringVector.h"
#include "Debugger.h"

#if defined( DO_RNI1 ) || defined( DO_RNI2 )

CStringVector::CStringVector( long max, JOBJECTARRAY pStrs )
{
	vec = new CVector( max, max );
	initFrom( max, pStrs );
}

CStringVector::CStringVector( JOBJECTARRAY pStrs )
{
	long		max;

	max = GETINTARRAYLEN( pStrs );

	vec = new CVector( max, max );
	initFrom( max, pStrs );
}

void CStringVector::initFrom( long max, JOBJECTARRAY pStrs )
{
	JSTRING			jStr;
	CStr			*cStr;
	long			srcLen, i;
	
	if ( max < 1 )
		return;

	srcLen = GETOBJECTARRAYLEN( pStrs );
	if ( max > srcLen )
		max = srcLen;

	for ( i = 0; i < max; i++ ) {
		jStr = (JSTRING) GETSTRARRAYELEMENT( pStrs, i );

		if ( jStr == NULL )
			vec->append( NULL );
		else {
			cStr = new CStr( jStr );
			vec->append( cStr );
		}
	}
}

void CStringVector::slamInto( JOBJECTARRAY pStrs )
{
	CStr			*csStr;
	unsigned long	i, numToCopy;

	numToCopy = getNumStrings();

	if ( numToCopy > GETOBJECTARRAYLEN( pStrs ) )
		numToCopy = GETOBJECTARRAYLEN( pStrs );

	for ( i = 0; i < numToCopy; i++ ) {
		csStr = getString( i );
		SETSTRARRAYELEMENT( pStrs, i, csStr );
	}
}

#elif defined( DO_JNI )

CStringVector::CStringVector( JNIEnv *pEnv, long max, JOBJECTARRAY pStrs )
{
	vec = new CVector( max, max );
	initFrom( pEnv, max, pStrs );
}

CStringVector::CStringVector( JNIEnv *pEnv, JOBJECTARRAY pStrs )
{
	long		max;

	max = GETINTARRAYLEN( pStrs );

	vec = new CVector( max, max );
	initFrom( pEnv, max, pStrs );
}

void CStringVector::initFrom( JNIEnv *pEnv, long max, JOBJECTARRAY pStrs )
{
	JSTRING			jStr;
	CStr			*cStr;
	long			srcLen, i;
	
	if ( max < 1 )
		return;

	srcLen = GETOBJECTARRAYLEN( pStrs );
	if ( max > srcLen )
		max = srcLen;

	for ( i = 0; i < max; i++ ) {
		jStr = (JSTRING) GETSTRARRAYELEMENT( pStrs, i );
		if ( jStr == NULL ) {
			vec->append( NULL );
		}
		else {
			cStr = new CStr( pEnv, jStr );
			vec->append( cStr );
		}

	}
}

void CStringVector::slamInto( JNIEnv *pEnv, JOBJECTARRAY pStrs )
{
	CStr			*csStr;
	unsigned long	i, numToCopy;

	numToCopy = getNumStrings();

	if ( numToCopy > GETOBJECTARRAYLEN( pStrs ) )
		numToCopy = GETOBJECTARRAYLEN( pStrs );

	for ( i = 0; i < numToCopy; i++ ) {
		csStr = getString( i );

		SETSTRARRAYELEMENT( pStrs, i, csStr );
	}
}

#endif

CStringVector::CStringVector( long max )
{
	vec = new CVector( max, max );
}

CStringVector::~CStringVector()
{
	CStr		*cs;
	long		n, i;

	n = getNumStrings();
	for ( i = 0; i < n; i++ ) {
		cs = getString( i );
		if ( cs != NULL )
			delete cs;
	}

	delete vec;
}

void CStringVector::appendString( CStr *cs )
{
	vec->append( cs );
}

void CStringVector::removeString( CStr *cs )
{
	vec->remove( cs );
}

void CStringVector::appendPackedString( const CStr *ps )
{
	CStr		*newCStr;
	long		i, numStrs;

	numStrs = CStr::countPackedStrings( ps );
	for ( i = 0; i < numStrs; i++ ) {
		newCStr = CStr::getPackedString( ps, i );
		if ( newCStr == NULL )
			break;
		appendString( newCStr );
	}
}

CStr *CStringVector::getString( long i )
{
	CStr		*retStr;

	retStr = (CStr*) vec->elementAt( i );

	return retStr;
}

long CStringVector::getNumStrings()
{
	return vec->getSize();
}

void CStringVector::setString( CStr *cs, long i )
{
	CStr		*deleteCS;

	if ( i < 0 || i >= getNumStrings() )
		return;

	deleteCS = getString( i );

	vec->setAt( i, cs );

	if ( deleteCS != NULL )
		delete deleteCS;	
}

BOOL CStringVector::contains( const CStr *cs )
{
	CStr		*csTemp;
	long		num, i;

	num = getNumStrings();
	if ( num < 1 )
		return FALSE;

	for ( i = 0; i < num; i++ ) {
		csTemp = getString( i );

		if ( cs == NULL ) {
			if ( csTemp == NULL )
				return TRUE;
			else
				continue;
		}

		if ( cs->isEqual( csTemp ) )
			return TRUE;
	}

	return FALSE;
}

BOOL CStringVector::containsIgnoreCase( const CStr *cs )
{
	CStr		*csTemp;
	long		num, i;

	num = getNumStrings();
	if ( num < 1 )
		return FALSE;

	for ( i = 0; i < num; i++ ) {
		csTemp = getString( i );

		if ( cs == NULL ) {
			if ( csTemp == NULL )
				return TRUE;
			else
				continue;
		}

		if ( cs->isEqualIgnoreCase( csTemp ) )
			return TRUE;
	}

	return FALSE;
}

void CStringVector::dumpInfo()
{
	long			i;

	Debugger::debug( 0, _TXL( "for vector:" ), NULL, NULL, getNumStrings(), (long) this );

	for ( i = 0; i < getNumStrings(); i++ )
		Debugger::debug( 0, _TXL( "    at " ), (CStr*) vec->elementAt( i ), NULL, i, 0 );
}


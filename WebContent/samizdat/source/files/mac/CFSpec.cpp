/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CFSpec.h"
#include "CUtils.h"
#include "Debugger.h"

#if defined(DO_JNI)

CFSpec::CFSpec( JNIEnv *pEnv, long vr, long pi, JBYTEARRAY pSrc )
{
	unsigned char	*srcP;
	long			numToCopy, srcArrayLen;
	jboolean		dummy;

	theSpec.vRefNum = vr;
	theSpec.parID = pi;
	if ( pSrc == NULL ) {
		theSpec.name[ 0 ] = 0;
		return;
	}

	srcArrayLen = ( pEnv->GetArrayLength )( pSrc );
	srcP = (unsigned char*) ( pEnv->GetByteArrayElements )( pSrc, &dummy );

	numToCopy = srcP[ 0 ] + 1;
	if ( numToCopy > kSpecNameLen || numToCopy > srcArrayLen )
		throw _TXL( "CFS string too long" );

	BlockMoveData( srcP, &theSpec.name[ 0 ], numToCopy );

	( pEnv->ReleaseByteArrayElements )( pSrc, (jbyte*) srcP, 0 );
}

#elif defined(DO_JRI)

CFSpec::CFSpec( JRIEnv *pEnv, long vr, long pi, JBYTEARRAY pSrc )
{
	unsigned char	*srcP;
	long			numToCopy, srcArrayLen;

	theSpec.vRefNum = vr;
	theSpec.parID = pi;
	if ( pSrc == NULL ) {
		theSpec.name[ 0 ] = 0;
		return;
	}

	srcArrayLen = JRI_GetByteArrayLength( pEnv, pSrc );
	srcP = (unsigned char*) JRI_GetByteArrayElements( pEnv, pSrc );

	numToCopy = srcP[ 0 ] + 1;
	if ( numToCopy > kSpecNameLen || numToCopy > srcArrayLen )
		throw _TXL( "CFS string too long" );

	BlockMoveData( srcP, &theSpec.name[ 0 ], numToCopy );
}

#endif

CFSpec::CFSpec( long vr, long pi, StringPtr ps )
{
	theSpec.vRefNum = vr;
	theSpec.parID = pi;
	if ( ps == NULL )
		theSpec.name[ 0 ] = 0;
	else
		CUtils::pStrncpy( &( theSpec.name[ 0 ] ), ps, ( kSpecNameLen - 1 ) );
}

CFSpec::CFSpec( const CFSpec *cfs )
{
	BlockMoveData( &cfs->theSpec, &theSpec, sizeof(FSSpec) );
}

CFSpec::~CFSpec()
{
}

long CFSpec::getVRef() const
{
	return theSpec.vRefNum;
}

long CFSpec::getParID() const
{
	return theSpec.parID;
}

StringPtr CFSpec::getName()
{
	return &theSpec.name[ 0 ];
}

FSSpec *CFSpec::getSpecP()
{
	return &theSpec;
}

ErrCode CFSpec::verifySpec()
{
	ErrCode			theErr;

	theErr = FSMakeFSSpec( theSpec.vRefNum, theSpec.parID, &theSpec.name[ 0 ], &theSpec );

	if ( theErr != noErr ) {
		CStr tempStr( &theSpec.name[ 0 ] );

		Debugger::debug( __LINE__, _TXL( "CFSpec did not verify: pName, theErr, vRef, parID" ),
							&tempStr, NULL, theErr, theSpec.vRefNum, theSpec.parID );
	}
	
	return theErr;
}

BOOL CFSpec::isNameNull() const
{
	return ( theSpec.name[ 0 ] == 0 );
}

void CFSpec::setFrom( const CFSpec *cfs )
{
	BlockMoveData( &cfs->theSpec, &theSpec, sizeof(FSSpec) );
}

void CFSpec::setFrom( long vr, long pi, StringPtr ps )
{
	theSpec.vRefNum = vr;
	theSpec.parID = pi;
	if ( ps == NULL )
		theSpec.name[ 0 ] = 0;
	else
		CUtils::pStrncpy( &( theSpec.name[ 0 ] ), ps, ( kSpecNameLen - 1 ) );
}

void CFSpec::putInto( FSSpec *toSpec ) const
{
	BlockMoveData( &theSpec, toSpec, sizeof(FSSpec) );
	if ( toSpec->name[ 0 ] > 63 )
		toSpec->name[ 0 ] = 63;
}

void CFSpec::dumpInfo( long theErr, const LPCTSTR message ) const
{
	CStr		*tempStr;
	char		tempBuf[ 256 ];
	
	CUtils::pStrToCString( tempBuf, &theSpec.name[ 0 ] );
	tempStr = new CStr( tempBuf );

	Debugger::debug( __LINE__, message, tempStr, NULL, theErr, theSpec.vRefNum, theSpec.parID );

	delete tempStr;
}


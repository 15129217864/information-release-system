/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SVersionInfo.h"
#include "CMemory.h"
#include "XToolkit.h"
#include "Debugger.h"

const	CStr	*SVersionInfo::gcsTranslation = new CStr( _TXL( "\\VarFileInfo\\Translation" ) );

	//	the routines in this file will need to be changed for Win64

ErrCode SVersionInfo::iNativeGetFileVersionInfoStart( const CStr *csFullPath, long *versionH )
{
	DWORD		verSize, zero;
	long		theErr;
	char		*dataP;

	*versionH = 0;
	theErr = kErrNoErr;

	verSize = XToolkit::XGetFileVersionInfoSize( csFullPath, &zero );
	if ( verSize == 0 ) {
		theErr = kErrGetFileVersionInfoSize;
		goto bail;
	}

	dataP = (char*) CMemory::mmalloc( verSize, _TXL( "SVersionInfo.ingfvis" ) );

	if ( !XToolkit::XGetFileVersionInfo( csFullPath, 0, verSize, dataP ) ) {
		Debugger::debug( __LINE__, _TXL( "SVI.ingfvis: xgfvi false" ), csFullPath, NULL );
		theErr = kErrGetFileVersionInfo;
		goto bail;
	}

	*versionH = (long) dataP;

bail:

	return theErr;
}

ErrCode SVersionInfo::iNativeGetFileVersionInfoEnd( long nativeH )
{
	if ( nativeH == 0 )
		return kErrParamErr;

	CMemory::mfree( (char*) nativeH );

	return kErrNoErr;
}

/*
	VerQueryValue( versionH
	pBlock, 
	TEXT("\\StringFileInfo\\040904E4\\FileDescription"), 
	&lpBuffer, 
	&dwBytes); 
*/
	
ErrCode SVersionInfo::iNativeVerQueryValue( long versionH, const CStr *csKey, CStr *csValue )
{
	CStr		csTemp( 48 ), *csFullKey;
	char		*versP, *retValueP;
	long		theErr, bufferSize, charSet, codePage;

	bufferSize = 0;
	versP = (char*) versionH;
	theErr = kErrNoErr;

	if ( !XToolkit::XVerQueryValue( versP, gcsTranslation, (void**) &retValueP, (UINT*) &bufferSize ) ) {
		Debugger::debug( __LINE__, _TXL( "invqv: vqv returned false for xlat" ) );
		theErr = kErrVerQueryValue;
		goto bail;
	}

	charSet = *( (short*) retValueP );
	codePage = *( (short*) ( retValueP + 2 ) );

	csFullKey = new CStr( _TXL( "\\StringFileInfo\\" ) );

	csTemp.formatInt( _TXL( "%.4x" ), charSet );
	csFullKey->concat( &csTemp );

	csTemp.formatInt( _TXL( "%.4x" ), codePage );
	csFullKey->concat( &csTemp );

	csFullKey->concat( _TXL( "\\" ) );
	csFullKey->concat( csKey );

	if ( !XToolkit::XVerQueryValue( versP, csFullKey, (void**) &retValueP, (UINT*) &bufferSize ) ) {
		Debugger::debug( __LINE__, _TXL( "invqv: vqv returned false" ), csFullKey, NULL );
		theErr = kErrVerQueryValue;
		goto bail;
	}

#if defined(UNICODE)
	csValue->setBuf( (wchar_t*) retValueP );
#else
	csValue->setBuf( (char*) retValueP );
#endif

bail:

	return theErr;
}






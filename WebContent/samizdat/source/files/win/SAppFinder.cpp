/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SAppFinder.h"
#include "XToolkit.h"
#include "CFileUtils.h"
#include "CRegUtils.h"
#include "Debugger.h"

const CStr *SAppFinder::gcsRegKeyAppPath = new CStr( _TXL( "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\" ) );
const CStr *SAppFinder::gcsExeExtension = new CStr( _TXL( ".exe" ) );

/*
Calls FindExecutable 
*/

ErrCode SAppFinder::iNativeFindAppsByNameLevel0( const CStr *appName, const CStr *defaultDir, long maxToReturn,
											long flags, CStringVector *stringVec )
{
	long			theErr1, theErr2;

	theErr1 = iNativeFindAppsByNameFindExe( appName, defaultDir, maxToReturn, flags, stringVec );

	theErr2 = iNativeFindAppsByNameAppPaths( appName, defaultDir, maxToReturn, flags, stringVec );

	if ( theErr1 == kErrNoErr )
		return kErrNoErr;

	return theErr2;
}

/*
Looks in <HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths>
*/

ErrCode SAppFinder::iNativeFindAppsByNameAppPaths( const CStr *appName, const CStr *defaultDir, long maxToReturn,
											long flags, CStringVector *stringVec )
{
	CStr		*csKey = NULL, tempAppName( appName ), csFoundExe( CStr::kMaxPath ), csLowerFoundExe( CStr::kMaxPath );
	long		theErr;

	theErr = kErrNoErr;

	tempAppName.toLower();

	csKey = new CStr( gcsRegKeyAppPath->getCharCapacity() +
							tempAppName.getCharCapacity() +
							gcsExeExtension->getCharCapacity() + 20 );

	csKey->setBuf( gcsRegKeyAppPath );
	csKey->concat( &tempAppName );
	csKey->concat( gcsExeExtension );

	if ( XToolkit::XRegQueryValue( HKEY_LOCAL_MACHINE, csKey, &csFoundExe ) != ERROR_SUCCESS ) {
		theErr = kErrRegQueryValue;
	}
	else {

		if ( XToolkit::XGetShortPathName( &csFoundExe, &csLowerFoundExe ) ) {
			if ( !stringVec->containsIgnoreCase( &csLowerFoundExe ) ) {
				stringVec->appendString( new CStr( &csLowerFoundExe ) );
			}
		}
	}

	if ( csKey != NULL )
		delete csKey;

	return theErr;
}

/*
Calls FindExecutable 
*/

ErrCode SAppFinder::iNativeFindAppsByNameFindExe( const CStr *appName, const CStr *defaultDir, long maxToReturn,
											long flags, CStringVector *stringVec )
{
	HINSTANCE		instanceH;
	long			theErr;
	CStr			csFoundExe( CStr::kMaxPath ), csLowerFoundExe( CStr::kMaxPath );

	theErr = kErrNoErr;

	instanceH = XToolkit::XFindExecutable( appName, defaultDir, &csFoundExe );
	if ( ( instanceH <= (HINSTANCE) 32 ) || !CFileUtils::exists( &csFoundExe ) )
		theErr = kErrFindExecutable;
	else {

		if ( XToolkit::XGetShortPathName( &csFoundExe, &csLowerFoundExe ) ) {
			if ( !stringVec->containsIgnoreCase( &csLowerFoundExe ) ) {
				stringVec->appendString( new CStr( &csLowerFoundExe ) );
			}
		}
	}

	return theErr;
}

/*
Looks through the registry for matching entries; similar to the iNativeFindVerbs routine
*/

ErrCode SAppFinder::iNativeFindAppsByNameLevel1( const CStr *appName, const CStr *defaultDir, long maxToReturn,
													long flags, CStringVector *stringVec )
{
	HKEY			hRootKey, hShellKey;
	long			theErr, index, numDone, verbIndex;
	CStr			csLowerAppName( appName ), csFoundExe( CStr::kMaxPath ), csLowerFoundExe( CStr::kMaxPath ),
					csExtension( CStr::kMaxPath ), csRegKey( CStr::kMaxPath ), csShellKey( CStr::kMaxPath ),
					csTest( CStr::kMaxPath ), csVerb( CStr::kMaxPath ), csCommand( CStr::kMaxPath ),
					csLowerCommand( CStr::kMaxPath );

	numDone = 0;
	theErr = kErrNoErr;

	csLowerAppName.toLower();
	csLowerAppName.concat( gcsExeExtension );

	theErr = XToolkit::XRegOpenKeyEx( HKEY_CLASSES_ROOT, NULL, 0, KEY_READ, &hRootKey );
	if ( theErr != ERROR_SUCCESS ) {
		theErr = kErrRegOpenKeyEx;
		Debugger::debug( __LINE__, _TXL( "infabnl1: can't open root" ) );
		goto bail;
	}

	for ( index = 0, numDone = 0, theErr = kErrNoErr; theErr == kErrNoErr && numDone < maxToReturn; index++ ) {
		if ( XToolkit::XRegEnumKey( hRootKey, index, &csExtension ) != ERROR_SUCCESS )
			break;

		if ( !csExtension.startsWith( _TXL( "." ) ) )
			continue;

		if ( XToolkit::XRegQueryValue( HKEY_CLASSES_ROOT, &csExtension, &csRegKey ) != ERROR_SUCCESS )
			continue;

		csShellKey.setBuf( &csRegKey );
		csShellKey.concat( _TXL( "\\shell" ) );

		//	csShellKey = _TXL( "txtfile\\shell" )
		if ( XToolkit::XRegOpenKeyEx( HKEY_CLASSES_ROOT, &csShellKey, 0, KEY_READ, &hShellKey ) != ERROR_SUCCESS )
			continue;

		for ( verbIndex = 0; ; verbIndex++ ) {
			if ( XToolkit::XRegEnumKey( hShellKey, verbIndex, &csVerb ) != ERROR_SUCCESS )
				break;

			csTest.setBuf( &csShellKey );
			csTest.concat( _TXL( "\\" ) );
			csTest.concat( &csVerb );
			csTest.concat( _TXL( "\\command" ) );

			//	sTest = "txtfile\\shell\\open\\command"
			if ( XToolkit::XRegQueryValue( HKEY_CLASSES_ROOT, &csTest, &csCommand ) != ERROR_SUCCESS )
				continue;

			csLowerCommand.setBuf( &csCommand );
			csLowerCommand.toLower();

			if ( csLowerCommand.contains( &csLowerAppName ) ) {

				if ( csCommand.extractExeFileName( &csFoundExe ) ) {
					if ( XToolkit::XGetShortPathName( &csFoundExe, &csLowerFoundExe ) ) {
						if ( !stringVec->containsIgnoreCase( &csLowerFoundExe ) ) {
							stringVec->appendString( new CStr( &csLowerFoundExe ) );
							++numDone;
						}
					}
				}
			}
		}

		RegCloseKey( hShellKey );
	}

	RegCloseKey( hRootKey );

bail:

	return theErr;
}

/*
If ( flags & 3 ) is zero, just calls iNativeFindAppsByNameLevel0
Otherwise, calls both iNativeFindAppsByNameLevel0 and iNativeFindAppsByNameLevel1
If either or both routines return kErrNoErr, we return kErrNoErr
*/

ErrCode SAppFinder::iNativeFindAppsByName( const CStr *appName, const CStr *defaultDir, long maxToReturn,
											long flags, CStringVector *stringVec )
{
	long			theErr1, theErr2;

	theErr1 = iNativeFindAppsByNameLevel0( appName, defaultDir, maxToReturn, flags, stringVec );

	if ( ( flags & 3 ) == 0 )
		return theErr1;

	theErr2 = iNativeFindAppsByNameLevel1( appName, defaultDir, maxToReturn, flags, stringVec );

	if ( theErr1 == kErrNoErr )
		return kErrNoErr;

	return theErr2;
}

/*
put short form of fullPath into csShortPath and convert to lower case
put long form of fullPath into csLongPath and convert to lower case

for each entry in HKCR
	if it starts with a period
		periodValue = that key's value
		look at all the values in HKCR\<periodValue>\shell\???\command
			if the command contains either csShortPath or csLongPath
				it's a match

Example:

HKCR\.html = htmlfile

So, we look through HKCR\htmlfile for all entries of the form HKCR\htmlfile\shell\???\command, such as

	HKCR\htmlfile\shell\open\command = "E:\INTERN~1\iexplore.exe" -nohome

So, if csShortPath contained "e:\intern~1\iexplore.exe", we'd have a match

If we have a match, we add the app's VATs to vatVec:
In this case, { ".html", "htmlfile\shell\open\command", "open", ""E:\INTERN~1\iexplore.exe" -nohome" }

*/

ErrCode SAppFinder::createPathsVectors( CStringVector *csvPaths,
										CStringVector *csvLongPaths, CStringVector *csvShortPaths )
{
	CStr			tempPath( CStr::kMaxPath ), *currentString;
	ErrCode			theErr;
	long			i, numPaths;

	numPaths = csvPaths->getNumStrings();

	for ( i = 0; i < numPaths; i++ ) {
		currentString = csvPaths->getString( i );

		if ( !XToolkit::XGetShortPathName( currentString, &tempPath ) ) {
			Debugger::debug( __LINE__, _TXL( "cpv.xgspn" ), currentString, NULL );
			theErr = kErrGetShortPathName;
			goto bail;
		}

		tempPath.toLower();
		csvShortPaths->appendString( new CStr( &tempPath ) );

		theErr = CFileUtils::makeLongFileName( currentString, &tempPath );
		if ( theErr != kErrNoErr ) {
			Debugger::debug( __LINE__, _TXL( "cpv.mlfn" ), currentString, NULL, theErr, 0 );
			goto bail;
		}

		tempPath.toLower();
		csvLongPaths->appendString( new CStr( &tempPath ) );
	}

bail:

	return theErr;
}

ErrCode SAppFinder::iNativeFindVerbs( CStringVector *csvPaths, const CStr *fileName, CStringVector *vatVec,
									long maxToReturn, long *numReturned )
{
	CStringVector	csvShortPaths( csvPaths->getNumStrings() ), csvLongPaths( csvPaths->getNumStrings() );
	HKEY			hRootKey, hShellKey;
	ErrCode			theErr;
	long			index;
	CStr			csLowerCommand( CStr::kMaxPath ), csShellKey( CStr::kMaxPath ), csTest( CStr::kMaxPath ),
					csCommand( CStr::kMaxPath ), csVerb( CStr::kMaxPath ), csRegKey( CStr::kMaxPath ),
					csExtension( CStr::kMaxPath ), csShortPath( CStr::kMaxPath ), csLongPath( CStr::kMaxPath );

	theErr = kErrNoErr;

	theErr = createPathsVectors( csvPaths, &csvLongPaths, &csvShortPaths );
	if ( theErr != kErrNoErr ) {
		Debugger::debug( __LINE__, _TXL( "infv.cpv" ), NULL, NULL, theErr, 0 );
		goto bail;
	}

	theErr = XToolkit::XRegOpenKeyEx( HKEY_CLASSES_ROOT, NULL, 0, KEY_READ, &hRootKey );
	if ( theErr != ERROR_SUCCESS ) {
		theErr = kErrRegOpenKeyEx;
		Debugger::debug( __LINE__, _TXL( "infv: can't open root" ) );
		goto bail;
	}

	for ( index = 0, theErr = kErrNoErr; theErr == kErrNoErr; index++ ) {
		if ( XToolkit::XRegEnumKey( hRootKey, index, &csExtension ) != ERROR_SUCCESS )
			break;

		if ( !csExtension.startsWith( _TXL( "." ) ) )
			continue;

		if ( XToolkit::XRegQueryValue( HKEY_CLASSES_ROOT, &csExtension, &csRegKey ) != ERROR_SUCCESS )
			continue;

		csShellKey.setBuf( &csRegKey );
		csShellKey.concat( _TXL( "\\shell" ) );

		//	csShellKey = _TXL( "txtfile\\shell" )
		if ( XToolkit::XRegOpenKeyEx( HKEY_CLASSES_ROOT, &csShellKey, 0, KEY_READ, &hShellKey ) != ERROR_SUCCESS )
			continue;

		doVerbLists( &csvShortPaths, &csvLongPaths, vatVec, maxToReturn, numReturned,
						&csExtension, &csRegKey, &csShellKey, hShellKey );

		RegCloseKey( hShellKey );
	}

	RegCloseKey( hRootKey );

bail:

	return theErr;
}

ErrCode SAppFinder::doVerbLists( CStringVector *csvShortPaths, CStringVector *csvLongPaths,
									CStringVector *vatVec, long maxToReturn, long *numReturned,
									CStr *csExtension, CStr *csRegKey, CStr *csShellKey, HKEY hShellKey )
{
	long			i, verbIndex, numInVector, numPaths;
	CStr			csVerb( CStr::kMaxPath ), csTest( CStr::kMaxPath ), csCommand( CStr::kMaxPath ),
					csLowerCommand( CStr::kMaxPath ), *csShortPath, *csLongPath;

	for ( verbIndex = 0; ; verbIndex++ ) {
		if ( XToolkit::XRegEnumKey( hShellKey, verbIndex, &csVerb ) != ERROR_SUCCESS )
			break;

		csTest.setBuf( csShellKey );
		csTest.concat( _TXL( "\\" ) );
		csTest.concat( &csVerb );
		csTest.concat( _TXL( "\\command" ) );

		//	sTest = "txtfile\\shell\\open\\command"
		if ( XToolkit::XRegQueryValue( HKEY_CLASSES_ROOT, &csTest, &csCommand ) != ERROR_SUCCESS )
			continue;

		csLowerCommand.setBuf( &csCommand );
		csLowerCommand.toLower();

		numPaths = csvShortPaths->getNumStrings();

		for ( i = 0; i < numPaths; i++ ) {
			numInVector = numReturned[ i ];
			if ( numInVector >= maxToReturn )
				continue;

			csShortPath = csvShortPaths->getString( i );
			csLongPath = csvLongPaths->getString( i );

			if ( csLowerCommand.contains( csShortPath ) || csLowerCommand.contains( csLongPath ) ) {
				vatVec->setString( new CStr( csExtension ), ( i * 4 * maxToReturn ) + ( 4 * numInVector ) + 0 );
				vatVec->setString( new CStr( csRegKey ), ( i * 4 * maxToReturn ) + ( 4 * numInVector ) + 1 );
				vatVec->setString( new CStr( &csVerb ), ( i * 4 * maxToReturn ) + ( 4 * numInVector ) + 2 );
				vatVec->setString( new CStr( &csCommand ), ( i * 4 * maxToReturn ) + ( 4 * numInVector ) + 3 );

				++numInVector;
				numReturned[ i ] = numInVector;
			}
		}
	}

	return kErrNoErr;
}

ErrCode SAppFinder::iNativeFindAppsByExtension( const CStr *fileExt, const CStr *tempDir, long maxToReturn, long flags, CStringVector *stringVec )
{
	HINSTANCE		instanceH;
	CStr			*retExe = NULL, tempFilePath( CStr::kMaxPath );
	long			theErr;

	theErr = kErrNoErr;
	retExe = new CStr( CStr::kMaxPath );

	if ( !CFileUtils::createTempFile( tempDir, fileExt, &tempFilePath ) ) {
		Debugger::debug( __LINE__, _TXL( "infabe: can't create temp file" ), tempDir, NULL );
		theErr = kErrCreateTempFile;
		goto bail;
	}

	instanceH = XToolkit::XFindExecutable( &tempFilePath, NULL, retExe );
	if ( instanceH <= (HINSTANCE) 32 ) {
		theErr = kErrFindExecutable;
	}

	CFileUtils::deleteFile( &tempFilePath );

	if ( !CFileUtils::exists( retExe ) )
		theErr = altFindExecutable( fileExt, retExe );

bail:

	if ( theErr == kErrNoErr && !stringVec->containsIgnoreCase( retExe ) )
		stringVec->appendString( retExe );
	else {
		if ( retExe != NULL )
			delete retExe;
	}

	return theErr;
}

ErrCode SAppFinder::altFindExecutable( const CStr *fileExt, CStr *retExe )
{
	CStr			csTempExt( fileExt ), csRegKey( CStr::kMaxPath ), csCommand( CStr::kMaxPath );
	long			theErr;

	theErr = kErrNoErr;

	if ( XToolkit::XRegQueryValue( HKEY_CLASSES_ROOT, &csTempExt, &csRegKey ) != ERROR_SUCCESS ) {
		theErr = kErrRegQueryValue;
		goto bail;
	}

	csRegKey.concat( _TXL( "\\shell\\open\\command" ) );

	if ( XToolkit::XRegQueryValue( HKEY_CLASSES_ROOT, &csRegKey, &csCommand ) != ERROR_SUCCESS ) {
		theErr = kErrRegQueryValue;
		goto bail;
	}

	if ( !csCommand.extractExeFileName( retExe ) ) {
		theErr = kErrRegQueryValue;
		goto bail;
	}

	if ( !CFileUtils::exists( retExe ) )
		theErr = kErrFileNotFound;
	else
		theErr = kErrNoErr;

bail:

	return theErr;
}


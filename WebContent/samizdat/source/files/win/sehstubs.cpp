/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include <excpt.h>
#include "sehstubs.h"

/*
//See source/docs/distribution.html for information on the following defines

#define		LINK_OUT_nativeGetShortPathName
#define		LINK_OUT_nativeGetAllProcessInfo
#define		LINK_OUT_nativeFindFirstFile
#define		LINK_OUT_nativeFindNextFile
#define		LINK_OUT_nativeFindClose
#define		LINK_OUT_nativeGetVolumeInformation
#define		LINK_OUT_nativeSetVolumeLabel
#define		LINK_OUT_nativeGetVolumeCapInfo
#define		LINK_OUT_nativeGetVolumeDate
#define		LINK_OUT_nativeCreateVolumeAlias
#define		LINK_OUT_nativeGetVolumeFlags
#define		LINK_OUT_nativeGetVolumeReadFlagsMask
#define		LINK_OUT_nativeGetAllMonitorInfo
#define		LINK_OUT_nativeGetMainMonitorInfo
#define		LINK_OUT_nativeGetExecutableType
#define		LINK_OUT_nativeGetDriveDisplayName
#define		LINK_OUT_nativeGetFileAttributes
#define		LINK_OUT_nativeGetFileAttributesMask
#define		LINK_OUT_nativeSetFileAttributes
#define		LINK_OUT_nativeGetFileAttributesWriteMask
#define		LINK_OUT_nativeResolveLinkFile
#define		LINK_OUT_nativeCreateFileAlias
#define		LINK_OUT_nativeGetVolumes
#define		LINK_OUT_nativeVIPGetFileVersionInfoStart
#define		LINK_OUT_nativeVIPGetFileVersionInfoEnd
#define		LINK_OUT_nativeVIPVerQueryValue
#define		LINK_OUT_nativeGetVolumeIcon
#define		LINK_OUT_nativeGetFileIcon
#define		LINK_OUT_nativeGetExtIcon
#define		LINK_OUT_nativeLaunchApp
#define		LINK_OUT_nativeFindAppsByName
#define		LINK_OUT_nativeFindAppsByExtension
#define		LINK_OUT_nativeMoveApp
#define		LINK_OUT_nativeFindVerbs
#define		LINK_OUT_nativeVerifyNativeAppData
#define		LINK_OUT_nativeGetFileDate
#define		LINK_OUT_nativeQuitApp
#define		LINK_OUT_nativeLaunchURL
*/

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetShortPathName ),
JSTRING pInFileName,
JOBJECTARRAY pOutFileName )
{
#if !defined(LINK_OUT_nativeGetShortPathName)
	SEH_TRY {
		return CALL_INNER( nativeGetShortPathName )( INNER_PREFIX, pInFileName, pOutFileName );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetShortPathName\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetAllProcessInfo ),
JINTARRAY pcntUsageArray, 
JINTARRAY pth32ProcessIDArray,
JINTARRAY pth32DefaultHeapIDArray, 
JINTARRAY pth32ModuleIDArray,
JINTARRAY pcntThreadsArray, 
JINTARRAY pth32ParentProcessIDArray,
JINTARRAY ppcPriClassBaseArray, 
JINTARRAY pdwFlagsArray,
JINTARRAY pdwThreadIDArray, 
JINTARRAY phWndArray,
JOBJECTARRAY pfullPathArray, 
JINT maxToReturn, 
JINTARRAY pnumReturned )
{
#if !defined(LINK_OUT_nativeGetAllProcessInfo)
	SEH_TRY {
		return CALL_INNER( nativeGetAllProcessInfo )( INNER_PREFIX, pcntUsageArray, 
														pth32ProcessIDArray,
														pth32DefaultHeapIDArray, 
														pth32ModuleIDArray,
														pcntThreadsArray, 
														pth32ParentProcessIDArray,
														ppcPriClassBaseArray, 
														pdwFlagsArray,
														pdwThreadIDArray, 
														phWndArray,
														pfullPathArray, 
														maxToReturn, 
														pnumReturned );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetAllProcessInfo\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeFindFirstFile ),
JSTRING pSearchString,
JINTARRAY pRetHFind,
JINTARRAY pRetAttrs,
JOBJECTARRAY pRetFileName )
{
#if !defined(LINK_OUT_nativeFindFirstFile)
	SEH_TRY {
		return CALL_INNER( nativeFindFirstFile )( INNER_PREFIX, pSearchString, pRetHFind, pRetAttrs, pRetFileName );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeFindFirstFile\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeFindNextFile ),
JINT findH,
JINTARRAY pRetAttrs,
JOBJECTARRAY pRetFileName )
{
#if !defined(LINK_OUT_nativeFindNextFile)
	SEH_TRY {
		return CALL_INNER( nativeFindNextFile )( INNER_PREFIX, findH, pRetAttrs, pRetFileName );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeFindNextFile\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeFindClose ),
JINT findH )
{
#if !defined(LINK_OUT_nativeFindClose)
	SEH_TRY {
		return CALL_INNER( nativeFindClose )( INNER_PREFIX, findH );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeFindClose\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeInformation ),
JSTRING pDriveName,
JOBJECTARRAY pInfoStrs,
JINTARRAY pInfoInts )
{
#if !defined(LINK_OUT_nativeGetVolumeInformation)
	SEH_TRY {
		return CALL_INNER( nativeGetVolumeInformation )( INNER_PREFIX, pDriveName, pInfoStrs, pInfoInts );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetVolumeInformation\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeSetVolumeLabel ),
JSTRING pDriveName,
JSTRING pNewLabel )
{
#if !defined(LINK_OUT_nativeSetVolumeLabel)
	SEH_TRY {
		return CALL_INNER( nativeSetVolumeLabel )( INNER_PREFIX, pDriveName, pNewLabel );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeSetVolumeLabel\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeCapInfo ),
JSTRING pDriveName,
JLONGARRAY pCapInfo )
{
#if !defined(LINK_OUT_nativeGetVolumeCapInfo)
	SEH_TRY {
		return CALL_INNER( nativeGetVolumeCapInfo )( INNER_PREFIX, pDriveName, pCapInfo );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetVolumeCapInfo\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeDate ),
JINT which,
JSTRING pDriveName,
JINTARRAY pModDate )
{
#if !defined(LINK_OUT_nativeGetVolumeDate)
	SEH_TRY {
		return CALL_INNER( nativeGetVolumeDate )( INNER_PREFIX, which, pDriveName, pModDate );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetVolumeDate\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeCreateVolumeAlias ),
JSTRING pDriveName,
JSTRING pNewAliasPath,
JINT flags )
{
#if !defined(LINK_OUT_nativeCreateVolumeAlias)
	SEH_TRY {
		return CALL_INNER( nativeCreateVolumeAlias )( INNER_PREFIX, pDriveName, pNewAliasPath, flags );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeCreateVolumeAlias\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeFlags ),
JSTRING pDriveName,
JINTARRAY pFlags )
{
#if !defined(LINK_OUT_nativeGetVolumeFlags)
	SEH_TRY {
		return CALL_INNER( nativeGetVolumeFlags )( INNER_PREFIX, pDriveName, pFlags );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetVolumeFlags\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeReadFlagsMask ),
JSTRING pDriveName,
JINTARRAY pMask )
{
#if !defined(LINK_OUT_nativeGetVolumeReadFlagsMask)
	SEH_TRY {
		return CALL_INNER( nativeGetVolumeReadFlagsMask )( INNER_PREFIX, pDriveName, pMask );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetVolumeReadFlagsMask\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetAllMonitorInfo ),
JINTARRAY pMonitorInfo,
JINT maxToReturn,
JINTARRAY pNumReturned )
{
#if !defined(LINK_OUT_nativeGetAllMonitorInfo)
	SEH_TRY {
		return CALL_INNER( nativeGetAllMonitorInfo )( INNER_PREFIX, pMonitorInfo, maxToReturn, pNumReturned );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetAllMonitorInfo\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetMainMonitorInfo ),
JINTARRAY pMonitorInfo )
{
#if !defined(LINK_OUT_nativeGetMainMonitorInfo)
	SEH_TRY {
		return CALL_INNER( nativeGetMainMonitorInfo )( INNER_PREFIX, pMonitorInfo );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetMainMonitorInfo\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetExecutableType ),
JSTRING pFullPath,
JINTARRAY pVal )
{
#if !defined(LINK_OUT_nativeGetExecutableType)
	SEH_TRY {
		return CALL_INNER( nativeGetExecutableType )( INNER_PREFIX, pFullPath, pVal );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetExecutableType\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetDriveDisplayName ),
JSTRING pDriveName,
JOBJECTARRAY pDisplayName )
{
#if !defined(LINK_OUT_nativeGetDriveDisplayName)
	SEH_TRY {
		return CALL_INNER( nativeGetDriveDisplayName )( INNER_PREFIX, pDriveName, pDisplayName );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetDriveDisplayName\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributes ),
JSTRING pFullPath,
JINTARRAY pVal )
{
#if !defined(LINK_OUT_nativeGetFileAttributes)
	SEH_TRY {
		return CALL_INNER( nativeGetFileAttributes )( INNER_PREFIX, pFullPath, pVal );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetFileAttributes\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributesMask ),
JSTRING pFullPath,
JINTARRAY pVal )
{
#if !defined(LINK_OUT_nativeGetFileAttributesMask)
	SEH_TRY {
		return CALL_INNER( nativeGetFileAttributesMask )( INNER_PREFIX, pFullPath, pVal );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetFileAttributesMask\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeSetFileAttributes ),
JSTRING pFullPath,
JINT whichFlags,
JINT newValues )
{
#if !defined(LINK_OUT_nativeSetFileAttributes)
	SEH_TRY {
		return CALL_INNER( nativeSetFileAttributes )( INNER_PREFIX, pFullPath, whichFlags, newValues );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeSetFileAttributes\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributesWriteMask ),
JSTRING pFullPath,
JINTARRAY pVal )
{
#if !defined(LINK_OUT_nativeGetFileAttributesWriteMask)
	SEH_TRY {
		return CALL_INNER( nativeGetFileAttributesWriteMask )( INNER_PREFIX, pFullPath, pVal );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetFileAttributesWriteMask\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeResolveLinkFile ),
JSTRING pLinkFilePath,
JOBJECTARRAY pRetPath,
JINT flags )
{
#if !defined(LINK_OUT_nativeResolveLinkFile)
	SEH_TRY {
		return CALL_INNER( nativeResolveLinkFile )( INNER_PREFIX, pLinkFilePath, pRetPath, flags );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeResolveLinkFile\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeCreateFileAlias ),
JSTRING pTargetPath,
JSTRING pNewAliasPath,
JINT flags )
{
#if !defined(LINK_OUT_nativeCreateFileAlias)
	SEH_TRY {
		return CALL_INNER( nativeCreateFileAlias )( INNER_PREFIX, pTargetPath, pNewAliasPath, flags );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeCreateFileAlias\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumes ),
JINT maxToReturn,
JINTARRAY pNumReturned,
JOBJECTARRAY pDriveNames )
{
#if !defined(LINK_OUT_nativeGetVolumes)
	SEH_TRY {
		return CALL_INNER( nativeGetVolumes )( INNER_PREFIX, maxToReturn, pNumReturned, pDriveNames );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetVolumes\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeVIPGetFileVersionInfoStart ),
JSTRING pFileName )
{
#if !defined(LINK_OUT_nativeVIPGetFileVersionInfoStart)
	SEH_TRY {
		return CALL_INNER( nativeVIPGetFileVersionInfoStart )( INNER_PREFIX, pFileName );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeVIPGetFileVersionInfoStart\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeVIPGetFileVersionInfoEnd ),
JINT versionH )
{
#if !defined(LINK_OUT_nativeVIPGetFileVersionInfoEnd)
	SEH_TRY {
		return CALL_INNER( nativeVIPGetFileVersionInfoEnd )( INNER_PREFIX, versionH );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeVIPGetFileVersionInfoEnd\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeVIPVerQueryValue ),
JINT versionH, 
JSTRING pKey, 
JOBJECTARRAY pRetArray )
{
#if !defined(LINK_OUT_nativeVIPVerQueryValue)
	SEH_TRY {
		return CALL_INNER( nativeVIPVerQueryValue )( INNER_PREFIX, versionH, pKey, pRetArray );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeVIPVerQueryValue\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeIcon ),
JSTRING pDriveName, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData )
{
#if !defined(LINK_OUT_nativeGetVolumeIcon)
	SEH_TRY {
		return CALL_INNER( nativeGetVolumeIcon )( INNER_PREFIX, pDriveName, whichIcon, width, height, xform, align, pData );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetVolumeIcon\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetFileIcon ),
JSTRING pFileName, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData )
{
#if !defined(LINK_OUT_nativeGetFileIcon)
	SEH_TRY {
		return CALL_INNER( nativeGetFileIcon )( INNER_PREFIX, pFileName, whichIcon, width, height, xform, align, pData );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetFileIcon\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetExtIcon ),
JSTRING pExt, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData )
{
#if !defined(LINK_OUT_nativeGetExtIcon)
	SEH_TRY {
		return CALL_INNER( nativeGetExtIcon )( INNER_PREFIX, pExt, whichIcon, width, height, xform, align, pData );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetExtIcon\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeLaunchApp ),
JSTRING pAppPath, 
JSTRING pVerb, 
JSTRING pRegKey, 
JSTRING pCommandTemplate, 
JINTARRAY pRetData, 
JINT numArgs, 
JOBJECTARRAY pArgs )
{
#if !defined(LINK_OUT_nativeLaunchApp)
	SEH_TRY {
		return CALL_INNER( nativeLaunchApp )( INNER_PREFIX, pAppPath, pVerb, pRegKey, pCommandTemplate, pRetData, numArgs, pArgs );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeLaunchApp\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeFindAppsByName ),
JSTRING pAppName, 
JINT maxToReturn, 
JINT flags,
JINTARRAY pNumReturned, 
JOBJECTARRAY pPaths )
{
#if !defined(LINK_OUT_nativeFindAppsByName)
	SEH_TRY {
		return CALL_INNER( nativeFindAppsByName )( INNER_PREFIX, pAppName, maxToReturn, flags, pNumReturned, pPaths );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeFindAppsByName\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeFindAppsByExtension ),
JSTRING pFileExt, 
JSTRING pTempDir, 
JINT maxToReturn,
JINT flags, 
JINTARRAY pNumReturned, 
JOBJECTARRAY pPaths )
{
#if !defined(LINK_OUT_nativeFindAppsByExtension)
	SEH_TRY {
		return CALL_INNER( nativeFindAppsByExtension )( INNER_PREFIX, pFileExt, pTempDir, maxToReturn, flags, pNumReturned, pPaths );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeFindAppsByExtension\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeMoveApp ),
JINTARRAY pAppData, 
JINT selector, 
JINT flags )
{
#if !defined(LINK_OUT_nativeMoveApp)
	SEH_TRY {
		return CALL_INNER( nativeMoveApp )( INNER_PREFIX, pAppData, selector, flags );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeMoveApp\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeFindVerbs ),
JOBJECTARRAY pFullPaths, 
JSTRING pFileName, 
JINT maxToReturn, 
JINTARRAY pNumReturned, 
JOBJECTARRAY pQuads )
{
#if !defined(LINK_OUT_nativeFindVerbs)
	SEH_TRY {
		return CALL_INNER( nativeFindVerbs )( INNER_PREFIX, pFullPaths, pFileName, maxToReturn, pNumReturned, pQuads );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeFindVerbs\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeVerifyNativeAppData ),
JINTARRAY pAppData )
{
#if !defined(LINK_OUT_nativeVerifyNativeAppData)
	SEH_TRY {
		return CALL_INNER( nativeVerifyNativeAppData )( INNER_PREFIX, pAppData );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeVerifyNativeAppData\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeGetFileDate ),
JINT which,
JSTRING pFullPath, 
JINTARRAY pModDate )
{
#if !defined(LINK_OUT_nativeGetFileDate)
	SEH_TRY {
		return CALL_INNER( nativeGetFileDate )( INNER_PREFIX, which, pFullPath, pModDate );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeGetFileDate\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeQuitApp ),
JINTARRAY pAppData, 
JINT flags )
{
#if !defined(LINK_OUT_nativeQuitApp)
	SEH_TRY {
		return CALL_INNER( nativeQuitApp )( INNER_PREFIX, pAppData, flags );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeQuitApp\n" ) )
#else
	return -1;
#endif
}

SEH_EXP JINT SEH_SFUNC( jconfig, AppUtilsMSVM, nativeLaunchURL ),
JSTRING pURL,
JSTRING pTempDir,
JINT flags,
JOBJECTARRAY pPreferredBrowsers )
{
#if !defined(LINK_OUT_nativeLaunchURL)
	SEH_TRY {
		return CALL_INNER( nativeLaunchURL )( INNER_PREFIX, pURL, pTempDir, flags, pPreferredBrowsers );
	}
	SEH_EXCEPT( _TXL( "SEH in nativeLaunchURL\n" ) )
#else
	return -1;
#endif
}


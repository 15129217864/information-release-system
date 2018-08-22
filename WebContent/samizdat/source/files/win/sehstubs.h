/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetShortPathName ),
JSTRING pInFileName,
JOBJECTARRAY pOutFileName );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetAllProcessInfo ),
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
JINTARRAY pnumReturned );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindFirstFile ),
JSTRING pSearchString,
JINTARRAY pRetHFind,
JINTARRAY pRetAttrs,
JOBJECTARRAY pRetFileName );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindNextFile ),
JINT findH,
JINTARRAY pRetAttrs,
JOBJECTARRAY pRetFileName );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindClose ),
JINT findH );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeInformation ),
JSTRING pDriveName,
JOBJECTARRAY pInfoStrs,
JINTARRAY pInfoInts );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeSetVolumeLabel ),
JSTRING pDriveName,
JSTRING pNewLabel );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeCapInfo ),
JSTRING pDriveName,
JLONGARRAY pCapInfo );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeDate ),
JINT which,
JSTRING pDriveName,
JINTARRAY pModDate );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeCreateVolumeAlias ),
JSTRING pDriveName,
JSTRING pNewAliasPath,
JINT flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeFlags ),
JSTRING pDriveName,
JINTARRAY pFlags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeReadFlagsMask ),
JSTRING pDriveName,
JINTARRAY pMask );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetAllMonitorInfo ),
JINTARRAY pMonitorInfo,
JINT maxToReturn,
JINTARRAY pNumReturned );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetMainMonitorInfo ),
JINTARRAY pMonitorInfo );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetExecutableType ),
JSTRING pFullPath,
JINTARRAY pVal );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetDriveDisplayName ),
JSTRING pDriveName,
JOBJECTARRAY pDisplayName );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributes ),
JSTRING pFullPath,
JINTARRAY pVal );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributesMask ),
JSTRING pFullPath,
JINTARRAY pVal );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeSetFileAttributes ),
JSTRING pFullPath,
JINT whichFlags,
JINT newValues );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileAttributesWriteMask ),
JSTRING pFullPath,
JINTARRAY pVal );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeResolveLinkFile ),
JSTRING pLinkFilePath,
JOBJECTARRAY pRetPath,
JINT flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeCreateFileAlias ),
JSTRING pTargetPath,
JSTRING pNewAliasPath,
JINT flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumes ),
JINT maxToReturn,
JINTARRAY pNumReturned,
JOBJECTARRAY pDriveNames );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeVIPGetFileVersionInfoStart ),
JSTRING pFileName );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeVIPGetFileVersionInfoEnd ),
JINT versionH );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeVIPVerQueryValue ),
JINT versionH, 
JSTRING pKey, 
JOBJECTARRAY pRetArray );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetVolumeIcon ),
JSTRING pDriveName, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileIcon ),
JSTRING pFileName, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetExtIcon ),
JSTRING pExt, 
JINT whichIcon, 
JINT width, 
JINT height,
JINT xform,
JINT align,
JINTARRAY pData );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeLaunchApp ),
JSTRING pAppPath, 
JSTRING pVerb, 
JSTRING pRegKey, 
JSTRING pCommandTemplate, 
JINTARRAY pRetData, 
JINT numArgs, 
JOBJECTARRAY pArgs );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindAppsByName ),
JSTRING pAppName, 
JINT maxToReturn, 
JINT flags,
JINTARRAY pNumReturned, 
JOBJECTARRAY pPaths );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindAppsByExtension ),
JSTRING pFileExt, 
JSTRING pTempDir, 
JINT maxToReturn,
JINT flags, 
JINTARRAY pNumReturned, 
JOBJECTARRAY pPaths );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeMoveApp ),
JINTARRAY pAppData, 
JINT selector, 
JINT flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeFindVerbs ),
JOBJECTARRAY pFullPaths, 
JSTRING pFileName, 
JINT maxToReturn, 
JINTARRAY pNumReturned, 
JOBJECTARRAY pQuads );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeVerifyNativeAppData ),
JINTARRAY pAppData );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeGetFileDate ),
JINT which,
JSTRING pFullPath, 
JINTARRAY pModDate );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeQuitApp ),
JINTARRAY pAppData, 
JINT flags );

EXP JINT SFUNC( jconfig, AppUtilsMSVM, nativeLaunchURL ),
JSTRING pURL,
JSTRING pTempDir,
JINT flags,
JOBJECTARRAY pPreferredBrowsers );


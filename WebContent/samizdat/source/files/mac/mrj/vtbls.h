/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

	/*		ResFileMRJ		*/

static char* ResFileMRJNameArray[] = {
	"nGetResourceSize(III[I)I",
	"nGetResource(III[B)I",
	"nOpenExistingResFile(II[B)I",
	"nCloseResFile(I)V",
	nil
};

static void* ResFileMRJImplArray[] = {
	Java_com_jconfig_mac_ResFileMRJ_nGetResourceSize,
	Java_com_jconfig_mac_ResFileMRJ_nGetResource,
	Java_com_jconfig_mac_ResFileMRJ_nOpenExistingResFile,
	Java_com_jconfig_mac_ResFileMRJ_nCloseResFile,
	nil
};


	/*	AppFinderMRJ		*/

static char* AppFinderMRJNameArray[] = {
	"nFindAPPLSingle(I[I[BI)I",
	"nFindAPPLMultiple(I[I[I[BII[I)I",
	nil
};

static void* AppFinderMRJImplArray[] = {
	Java_com_jconfig_mac_AppFinderMRJ_nFindAPPLSingle,
	Java_com_jconfig_mac_AppFinderMRJ_nFindAPPLMultiple,
	nil
};
 

	/*	AppUtilsMRJ			*/

static char* AppUtilsMRJNameArray[] = {
	"nGetVolumeName(I[B)I",
	"nGetVolumeFinderInfo(I[I)I",
	"nGetFinderInfo(II[B[I)I",
	"nMoveApp([III)I",
	"nVerifyPSN([I)I",
	"nCreateFullPathName(II[B[Ljava/lang/String;)I",
	"nGetOpenableFileTypes(II[I[I)I",
	"nQuitApp([II)I",
	"nLaunchApp(II[B[II)I",
	"nLaunchWithDoc(III[B[Ljava/lang/String;[II)I",
	"nSendAppDocs(I[I[Ljava/lang/String;I)I",
	"nLaunchURLDirect(Ljava/lang/String;I[Ljava/lang/String;)I",
	"nFullPathToSpec(Ljava/lang/String;[I[B)I",
	"nGetFileDate(III[B[I)I",
	"nSetFileDate(III[B[I)I",
	"nGetVolumeDate(II[I)I",
	"nSetVolumeDate(II[I)I",
	"nSetCreatorAndType(II[B[I)I",
	"nVerifyFile(II[B)I",
	"nVerifyVolume(I)I",
	"nGetDiskFileFlags(II[B[I)I",
	"nSetDiskFileFlags(II[BII)I",
	"nGetDFReadFlagsMask(II[B[I)I",
	"nGetDFWriteFlagsMask(II[B[I)I",
	"nGetDiskVolumeFlags(I[I)I",
	"nSetDiskVolumeFlags(III)I",
	"nGetDVReadFlagsMask(I[I)I",
	"nGetDVWriteFlagsMask(I[I)I",
	"nGetVolumeIconSuite(II[I)I",
	"nGetFileIconSuite(II[BI[I)I",
	"nGetFTACIconSuite(IIII[I)I",
	"nDisposeIconSuite(II)I",
	"nPlotIcon(IIIIII[I)I",
	"nResolveAlias(II[B[I[BI)I",
	"nGetFileCategory(II[B[I)I",
	"nGetVolumes(I[I[I)I",
	"nUpdateContainer(II[B)I",
	"nGetForkSizes(II[B[J)I",
	"nRenameFile(II[B[BLjava/lang/String;)I",
	"nRenameVolume(ILjava/lang/String;)I",
	"nGetVolumeCapacity(I[J)I",
	"nGetVolumeFreeSpace(I[J)I",
	"nCreateAlias(II[BLjava/lang/String;II)I",
	"nSetColorCoding(II[BI)I",
	"nSetVolumeColorCoding(II)I",
	"nCreateVolumeAlias(ILjava/lang/String;II)I",
	"nIterateContents(II[B[I[I[BII)I",
	"nIVC(I[I[I[BII)I",
	"nGetContainer(II[B[I[B)I",
	"nGetMainMonitorInfo([I)I",
	"nGetAllMonitorInfo([II[I)I",
	"nGetProcesses(II[I[I[I[B[I[I[I)I",
	"nGetRawResourceFork(III[B[B)I",
	"nSetRawResourceFork(III[B[B)I",
	"nSetForkLength(IIII[BJ)I",
	NULL
};

static void* AppUtilsMRJImplArray[] = {
	Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeName,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeFinderInfo,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetFinderInfo,
	Java_com_jconfig_mac_AppUtilsMRJ_nMoveApp,
	Java_com_jconfig_mac_AppUtilsMRJ_nVerifyPSN,
	Java_com_jconfig_mac_AppUtilsMRJ_nCreateFullPathName,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetOpenableFileTypes,
	Java_com_jconfig_mac_AppUtilsMRJ_nQuitApp,
	Java_com_jconfig_mac_AppUtilsMRJ_nLaunchApp,
	Java_com_jconfig_mac_AppUtilsMRJ_nLaunchWithDoc,
	Java_com_jconfig_mac_AppUtilsMRJ_nSendAppDocs,
	Java_com_jconfig_mac_AppUtilsMRJ_nLaunchURLDirect,
	Java_com_jconfig_mac_AppUtilsMRJ_nFullPathToSpec,

	Java_com_jconfig_mac_AppUtilsMRJ_nGetFileDate,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetFileDate,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeDate,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetVolumeDate,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetCreatorAndType,
	Java_com_jconfig_mac_AppUtilsMRJ_nVerifyFile,
	Java_com_jconfig_mac_AppUtilsMRJ_nVerifyVolume,

	Java_com_jconfig_mac_AppUtilsMRJ_nGetDiskFileFlags,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetDiskFileFlags,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetDFReadFlagsMask,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetDFWriteFlagsMask,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetDiskVolumeFlags,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetDiskVolumeFlags,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetDVReadFlagsMask,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetDVWriteFlagsMask,

	Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeIconSuite,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetFileIconSuite,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetFTACIconSuite,
	Java_com_jconfig_mac_AppUtilsMRJ_nDisposeIconSuite,
	Java_com_jconfig_mac_AppUtilsMRJ_nPlotIcon,

	Java_com_jconfig_mac_AppUtilsMRJ_nResolveAlias,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetFileCategory,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumes,
	Java_com_jconfig_mac_AppUtilsMRJ_nUpdateContainer,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetForkSizes,
	Java_com_jconfig_mac_AppUtilsMRJ_nRenameFile,
	Java_com_jconfig_mac_AppUtilsMRJ_nRenameVolume,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeCapacity,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeFreeSpace,
	Java_com_jconfig_mac_AppUtilsMRJ_nCreateAlias,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetColorCoding,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetVolumeColorCoding,
	Java_com_jconfig_mac_AppUtilsMRJ_nCreateVolumeAlias,
	Java_com_jconfig_mac_AppUtilsMRJ_nIterateContents,
	Java_com_jconfig_mac_AppUtilsMRJ_nIVC,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetContainer,

	Java_com_jconfig_mac_AppUtilsMRJ_nGetMainMonitorInfo,
	Java_com_jconfig_mac_AppUtilsMRJ_nGetAllMonitorInfo,

	Java_com_jconfig_mac_AppUtilsMRJ_nGetProcesses,

	Java_com_jconfig_mac_AppUtilsMRJ_nGetRawResourceFork,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetRawResourceFork,
	Java_com_jconfig_mac_AppUtilsMRJ_nSetForkLength,

	NULL
};


	/*	IConfigMRJ			*/

static char* IConfigMRJNameArray[] = {
	"nICStart(I)I",
	"nICStop(I)V",
	"nICGetMapEntrySize()I",
	"nICGetSeed(I)I",
	"nICCountMapEntries(I)I",
	"nICGetIndMapEntry(II[B)I",
	"nFindMatchesExt(ILjava/lang/String;I[II[I[I)I",
	"nFindMatchesFInfo(IIII[II[B)I",
	"nFindAppByName(ILjava/lang/String;[I[I[I[BI[I)I",
	"nLaunchURL(ILjava/lang/String;I[Ljava/lang/String;)I",
	nil
};

static void* IConfigMRJImplArray[] = {
	Java_com_jconfig_mac_IConfigMRJ_nICStart,
	Java_com_jconfig_mac_IConfigMRJ_nICStop,
	Java_com_jconfig_mac_IConfigMRJ_nICGetMapEntrySize,
	Java_com_jconfig_mac_IConfigMRJ_nICGetSeed,
	Java_com_jconfig_mac_IConfigMRJ_nICCountMapEntries,
	Java_com_jconfig_mac_IConfigMRJ_nICGetIndMapEntry,
	Java_com_jconfig_mac_IConfigMRJ_nFindMatchesExt,
	Java_com_jconfig_mac_IConfigMRJ_nFindMatchesFInfo,
	Java_com_jconfig_mac_IConfigMRJ_nFindAppByName,
	Java_com_jconfig_mac_IConfigMRJ_nLaunchURL,
	nil
};

typedef struct {
	char		*className;
	char		**methodName;
	void		**methodImpl;
} NativeFuncs;

static NativeFuncs		allFuncList[] = {
	{ "com/jconfig/mac/AppUtilsMRJ", &AppUtilsMRJNameArray[ 0 ], &AppUtilsMRJImplArray[ 0 ] },
	{ "com/jconfig/mac/AppFinderMRJ", &AppFinderMRJNameArray[ 0 ], &AppFinderMRJImplArray[ 0 ] },
	{ "com/jconfig/mac/ResFileMRJ", &ResFileMRJNameArray[ 0 ], &ResFileMRJImplArray[ 0 ] },
	{ "com/jconfig/mac/IConfigMRJ", &IConfigMRJNameArray[ 0 ], &IConfigMRJImplArray[ 0 ] },
	{ nil, nil, nil }
};

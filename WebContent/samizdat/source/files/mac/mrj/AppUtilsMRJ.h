/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_APPUTILSMRJ_H
#define INC_APPUTILSMRJ_H

#include <NativeLibSupport.h>
#include <jri.h>

#ifdef __cplusplus
	extern "C" {
#endif

long Java_com_jconfig_mac_AppUtilsMRJ_nGetProcesses(
JRIEnv* env,
jref configObj,
long maxToReturn,
long flags,
jintArray pNumRet,
jintArray pVRefs,
jintArray pParIDs,
jbyteArray pNames,
jintArray pPSNLo,
jintArray pPSNHi,
jintArray pProFlags );



//int nativeGetContainer( int vRef, int parID, byte pName[],
//							int pContParID[], byte pContName[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetContainer(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jintArray pContParID,
jbyteArray pContName );

//int nativeResolveAlias( int inVRef, int inParID, byte pInName[],
//							int outVRefAndParID[], byte pOutName[], int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nResolveAlias(
JRIEnv* env,
jref configObj, 
long inVRef,
long inParID,
jbyteArray pInName,
jintArray pOutVRefAndParID,
jbyteArray pOutName,
long flags );

//int nativeUpdateContainer( int vRef, int parID, byte pName[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nUpdateContainer(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName );

//int nativeCreateAlias( int targetVRef, int targetParID, byte pTargetName[],
//							String newAlias, int creator, int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nCreateAlias(
JRIEnv* env,
jref configObj, 
long targetVRef,
long targetParID,
jbyteArray pTargetName,
JRIStringID pNewAliasID,
long creator,
long flags );

//int nativeCreateVolumeAlias( int targetVRef, String newAlias, int creator, int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nCreateVolumeAlias(
JRIEnv* env,
jref configObj, 
long targetVRef,
JRIStringID pNewAliasID,
long creator,
long flags );



//int nativeSetColorCoding( int vRef, int parID, byte pName[], int newCoding );
long Java_com_jconfig_mac_AppUtilsMRJ_nSetColorCoding( 
JRIEnv* env, 
jref configObj, 
long vRef, 
long parID, 
jbyteArray pName, 
long newCoding );

//int nativeGetFinderInfo( int vRef, int parID, byte pName[], int finfo[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetFinderInfo( 
JRIEnv* env, 
jref configObj, 
long vRef, 
long parID, 
jbyteArray pName, 
jintArray pFInfo );

//int nativeGetFileDate( int which, int vRef, int parID, byte pName[], int modDate[] );
//int nativeGetFileDate( int which, int vRef, int parID, byte pName[], int modDate[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetFileDate(
JRIEnv* env,
jref configObj, 
long which,
long vRef,
long parID,
jbyteArray pName,
jintArray pModDate );

//int nativeSetFileDate( int which, int vRef, int parID, byte pName[], int modDate[] );
//int nativeSetFileDate( int which, int vRef, int parID, byte pName[], int modDate[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nSetFileDate(
JRIEnv* env,
jref configObj, 
long which,
long vRef,
long parID,
jbyteArray pName,
jintArray pModDate );

//int nativeVerifyFile( int vRef, int parID, byte pName[] );
//int nativeVerifyFile( int vRef, int parID, byte pName[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nVerifyFile(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName );

//int nativeSetCreatorAndType( int vRef, int parID, byte pName[], int creatorAndType[] );
//int nativeSetCreatorAndType( int vRef, int parID, byte pName[], int creatorAndType[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nSetCreatorAndType(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jintArray pCreatorAndType );

//int nativeGetFileCategory( int vRef, int parID, byte pName[], int cat[] );
//int nativeGetFileCategory( int vRef, int parID, byte pName[], int cat[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetFileCategory(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jintArray pCat );

//int nativeGetForkSizes( int vRef, int parID, byte pName[], long len[] );
//int nativeGetForkSizes( int vRef, int parID, byte pName[], long len[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetForkSizes(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jlongArray pLen );

//int nativeRenameFile( int vRef, int parID, byte pName[], byte pOutName[], String newNameID );
//int nativeRenameFile( int vRef, int parID, byte pName[], byte pOutName[], String newNameID );
long Java_com_jconfig_mac_AppUtilsMRJ_nRenameFile(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jbyteArray pOutName,
JRIStringID newNameID );

//int nativeSetVolumeColorCoding( int vRef, int newCoding );
long Java_com_jconfig_mac_AppUtilsMRJ_nSetVolumeColorCoding(
JRIEnv* env,
jref configObj, 
long vRef,
long newCoding );

//int nativeGetVolumeFinderInfo( int vRef, int pFInfo[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeFinderInfo(
JRIEnv* env,
jref configObj, 
long vRef,
jintArray pFInfo );

//int nativeGetVolumeDate( int which, int vRef, int modDate[] );
//int nativeGetVolumeDate( int which, int vRef, int modDate[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeDate(
JRIEnv* env,
jref configObj, 
long which,
long vRef,
jintArray pModDate );

//int nativeSetVolumeDate( int which, int vRef, int modDate[] );
//int nativeSetVolumeDate( int which, int vRef, int modDate[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nSetVolumeDate(
JRIEnv* env,
jref configObj, 
long which,
long vRef,
jintArray pModDate );

//int nativeVerifyVolume( int vRef );
//int nativeVerifyVolume( int vRef );
long Java_com_jconfig_mac_AppUtilsMRJ_nVerifyVolume(
JRIEnv* env,
jref configObj, 
long vRef );

//int nativeGetVolumes( int maxToReturn, int numRet[], int vRefs[] );
//int nativeGetVolumes( int maxToReturn, int numRet[], int vRefs[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumes(
JRIEnv* env,
jref configObj, 
long maxToReturn,
jintArray pNumRet,
jintArray pVRefs );

//int nativeRenameVolume( int vRef, String newNameID );
//int nativeRenameVolume( int vRef, String newNameID );
long Java_com_jconfig_mac_AppUtilsMRJ_nRenameVolume(
JRIEnv* env,
jref configObj, 
long vRef,
JRIStringID newNameID );

//int nativeGetVolumeCapacity( int vRef, long cap[] );
//int nativeGetVolumeCapacity( int vRef, long cap[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeCapacity(
JRIEnv* env,
jref configObj, 
long vRef,
jlongArray pCap );

//int nativeGetVolumeFreeSpace( int vRef, long space[] );
//int nativeGetVolumeFreeSpace( int vRef, long space[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeFreeSpace(
JRIEnv* env,
jref configObj, 
long vRef,
jlongArray pSpace );

//int nativeGetVolumeName( int vRef, byte pName[] );
//int nativeGetVolumeName( int vRef, byte pName[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeName(
JRIEnv* env,
jref configObj, 
long vRef,
jbyteArray pName );

//int nativeFullPathToSpec( String fullPath, int vRefAndParID[], byte pName[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nFullPathToSpec( 
JRIEnv* env,
jref configObj, 
JRIStringID pFullPath,
jintArray pVRefAndParID, 
jbyteArray pName );
							
//int nativeCreateFullPathName( int vRef, int parID, byte pName[], String retArray[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nCreateFullPathName( 
JRIEnv* env, 
jref configObj, 
long vRef, 
long parID,
jbyteArray pName, 
jstringArray pOutNameArray );

//int nativeLaunchURLDirect( String url, int flags, String preferredBrowsers[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nLaunchURLDirect( 
JRIEnv* env, 
jref configObj, 
JRIStringID urlID, 
long flags,
jstringArray pPreferredBrowsers );

//int nativeSendAppDocs( int whichCommand, int appPSN[], String filePaths[], int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nSendAppDocs( 
JRIEnv* env, 
jref configObj, 
long whichCommand, 
jintArray appPSN,
jstringArray filePaths, 
long flags );

//int nativeMoveApp( int appPSN[], int selector, int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nMoveApp( 
JRIEnv* env, 
jref configObj, 
jintArray appPSN, 
long selector, 
long flags );

//int nativeVerifyPSN( int appPSN[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nVerifyPSN( 
JRIEnv* env, 
jref configObj, 
jintArray appPSN );

//int nativeGetOpenableFileTypes( int vRef, int creator, int numReturned[], int fileTypes[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetOpenableFileTypes( 
JRIEnv* env, 
jref configObj, 
long vRef, 
long creator, 
jintArray numRet, 
jintArray fileTypes );

//int nativeQuitApp( int appPSN[], int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nQuitApp( 
JRIEnv* env, 
jref configObj, 
jintArray appPSN, 
long flags );

//int nativeLaunchApp( int vRef, int parID, byte pName[], int retPSN[], int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nLaunchApp( 
JRIEnv* env, 
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jintArray retPSN, 
long flags );

//int nativeLaunchWithDoc( int whichCommand, int vRef, int parID, byte pName[],
//									String filePaths[], int retPSN[], int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nLaunchWithDoc( 
JRIEnv* env, 
jref configObj, 
long whichCommand, 
long vRef,
long parID,
jbyteArray pName,
jstringArray filePaths, 
jintArray retPSN, 
long flags );


//int nativePlotIcon( int which, int width, int height,
//						int hSuite, int xform, int align, int pData[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nPlotIcon(
JRIEnv* env,
jref configObj, 
long which,
long width,
long height,
long hSuite,
long xform,
long align,
jintArray pData );

//int nativeGetVolumeIconSuite( int vRef, int selector, int pHSuite[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetVolumeIconSuite(
JRIEnv* env,
jref configObj, 
long vRef,
long selector,
jintArray pHSuite );

//int nativeGetFileIconSuite( int vRef, int parID, byte pName[], int selector, int pHSuite[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetFileIconSuite(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
long selector,
jintArray pHSuite );

//int nativeGetFTACIconSuite( int vRef, int creator, int type, int selector, int pHSuite[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetFTACIconSuite(
JRIEnv* env,
jref configObj, 
long vRef,
long creator,
long type,
long selector,
jintArray pHSuite );

//int nativeDisposeIconSuite( int hSuite, int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nDisposeIconSuite(
JRIEnv* env,
jref configObj, 
long hSuite,
long flags );

//int nativeGetDiskFileFlags( int vRef, int parID, byte pName[], int flags[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetDiskFileFlags(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jintArray pFlags );

//int nativeSetDiskFileFlags( int vRef, int parID, byte pName[], int flagMask, int newFlags );
long Java_com_jconfig_mac_AppUtilsMRJ_nSetDiskFileFlags(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
long flagMask,
long newFlags );

//int nativeGetDFReadFlagsMask( int vRef, int parID, byte pName[], int masks[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetDFReadFlagsMask(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jintArray pMasks );

//int nativeGetDFWriteFlagsMask( int vRef, int parID, byte pName[], int masks[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetDFWriteFlagsMask(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jintArray pMasks );

//int nativeGetDiskVolumeFlags( int vRef, int flags[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetDiskVolumeFlags(
JRIEnv* env,
jref configObj, 
long vRef,
jintArray pFlags );

//int nativeSetDiskVolumeFlags( int vRef, int flagMask, int newFlags );
long Java_com_jconfig_mac_AppUtilsMRJ_nSetDiskVolumeFlags(
JRIEnv* env,
jref configObj, 
long vRef,
long flagMask,
long newFlags );

//int nativeGetDVReadFlagsMask( int vRef, int masks[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetDVReadFlagsMask(
JRIEnv* env,
jref configObj, 
long vRef,
jintArray pMasks );

//int nativeGetDVWriteFlagsMask( int vRef, int masks[] );
long Java_com_jconfig_mac_AppUtilsMRJ_nGetDVWriteFlagsMask(
JRIEnv* env,
jref configObj, 
long vRef,
jintArray pMasks );

//int nativeIterateContents( int vRef, int parID, byte pName[],
//								int dirIDArray[], int numRet[],
//								byte buffer[], int numEntries, int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nIterateContents(
JRIEnv* env,
jref configObj, 
long vRef,
long parID,
jbyteArray pName,
jintArray pDirIDArray,
jintArray pNumRet,
jbyteArray pBuffer,
long numEntries,
long flags );

//int nativeIVC( int vRef, int dirIDArray[], int numRet[],
//									byte buffer[], int numEntries, int flags );
long Java_com_jconfig_mac_AppUtilsMRJ_nIVC(
JRIEnv* env,
jref configObj, 
long vRef,
jintArray pDirIDArray,
jintArray pNumRet,
jbyteArray pBuffer,
long numEntries,
long flags );

long Java_com_jconfig_mac_AppUtilsMRJ_nGetMainMonitorInfo(
JRIEnv* env,
jref configObj, 
jintArray pData );

long Java_com_jconfig_mac_AppUtilsMRJ_nGetAllMonitorInfo(
JRIEnv* env,
jref configObj,
jintArray pData,
long maxToReturn,
jintArray pNumReturned );






long Java_com_jconfig_mac_AppUtilsMRJ_nGetRawResourceFork(
JRIEnv* env,
jref configObj,
jint flags,
jint vRef,
jint parID,
jbyteArray pName,
jbyteArray pBuffer );

long Java_com_jconfig_mac_AppUtilsMRJ_nSetRawResourceFork(
JRIEnv* env,
jref configObj,
jint flags,
jint vRef,
jint parID,
jbyteArray pName,
jbyteArray pBuffer );

long Java_com_jconfig_mac_AppUtilsMRJ_nSetForkLength(
JRIEnv* env,
jref configObj,
jint flags,
jint whichFork,
jint vRef,
jint parID,
jbyteArray pName,
jlong newLen );





#ifdef __cplusplus
	}
#endif

#endif




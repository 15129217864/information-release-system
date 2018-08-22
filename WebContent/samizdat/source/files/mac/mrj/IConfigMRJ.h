/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_ICONFIGMRJ_H
#define INC_ICONFIGMRJ_H

#ifdef __cplusplus
	extern "C" {
#endif

long Java_com_jconfig_mac_IConfigMRJ_nICStart(
JRIEnv* env,
jref configObj,
long creator );

void Java_com_jconfig_mac_IConfigMRJ_nICStop(
JRIEnv* env,
jref configObj,
long ourHandle );

long Java_com_jconfig_mac_IConfigMRJ_nICGetMapEntrySize(
JRIEnv* env,
jref configObj );

long Java_com_jconfig_mac_IConfigMRJ_nICGetSeed(
JRIEnv* env,
jref configObj,
long ourHandle );

long Java_com_jconfig_mac_IConfigMRJ_nICCountMapEntries(
JRIEnv* env,
jref configObj,
long ourHandle );

long Java_com_jconfig_mac_IConfigMRJ_nICGetIndMapEntry(
JRIEnv* env,
jref configObj,
long ourHandle,
long whichRecord,
jbyteArray record );

long Java_com_jconfig_mac_IConfigMRJ_nFindAppByName(
JRIEnv* env,
jref configObj,
long appCreator,
JRIStringID nameID,
jintArray creators,
jintArray vRefs,
jintArray parIDs,
jbyteArray pNames,
long maxToReturn,
jintArray numRet );
						
long Java_com_jconfig_mac_IConfigMRJ_nLaunchURL(
JRIEnv* env,
jref configObj,
long appCreator,
JRIStringID urlID,
long flags,
jobjectArray pPreferredBrowsers );

long Java_com_jconfig_mac_IConfigMRJ_nFindMatchesExt(
JRIEnv* env,
jref configObj,
long appCreator,
JRIStringID extensionID,
long direction,
jintArray numRet,
long maxToReturn,
jintArray cVals,
jintArray tVals );
							
long Java_com_jconfig_mac_IConfigMRJ_nFindMatchesFInfo(
JRIEnv* env,
jref configObj,
long appCreator,
long targetCreator,
long targetType,
long direction,
jintArray numRet,
long maxToReturn,
jbyteArray pExtensions );


#ifdef __cplusplus
	}
#endif

#endif

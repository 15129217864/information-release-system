/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_APPMAPPERMRJ_H
#define INC_APPMAPPERMRJ_H

#include <NativeLibSupport.h>
#include <jri.h>

#ifdef __cplusplus
	extern "C" {
#endif

long Java_com_jconfig_mac_AppFinderMRJ_nFindAPPLMultiple( 
JRIEnv* env, 
jref configObj, 
long creator, 
jintArray pVRefs,
jintArray pParIDs, 
jbyteArray pName, 
long maxToReturn,
long flags,
jintArray pNumRet );

long Java_com_jconfig_mac_AppFinderMRJ_nFindAPPLSingle( 
JRIEnv* env, 
jref configObj, 
long creator,
jintArray vRefAndParID, 
jbyteArray pName,
long flags );

#ifdef __cplusplus
	}
#endif

#endif

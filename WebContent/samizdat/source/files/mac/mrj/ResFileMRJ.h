/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_RESFILEMRJ_H
#define INC_RESFILEMRJ_H

#include <NativeLibSupport.h>
#include <jri.h>

#ifdef __cplusplus
	extern "C" {
#endif

long Java_com_jconfig_mac_ResFileMRJ_nOpenExistingResFile(
JRIEnv* env, 
jref configObj, 
long vRef,
long parID, 
jbyteArray pName );
								
void Java_com_jconfig_mac_ResFileMRJ_nCloseResFile(
JRIEnv* env, 
jref configObj, 
long fileFD );

long Java_com_jconfig_mac_ResFileMRJ_nGetResourceSize(
JRIEnv* env, 
jref configObj, 
long fileFD, 
long resName, 
long resID,
jintArray pSize );
							
long Java_com_jconfig_mac_ResFileMRJ_nGetResource(
JRIEnv* env, 
jref configObj, 
long fileFD, 
long resName, 
long resID,
jbyteArray pRetData );


#ifdef __cplusplus
	}
#endif

#endif

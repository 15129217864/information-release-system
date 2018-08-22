/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "RNIUtils.h"

void RNIGetAppData( AppDataType *setDataP, struct HArrayOfInt* pAppData )
{
	setDataP->hwnd = (HWND) ( pAppData->body )[ kAppDataHWNDOffset ];
	setDataP->pi.hProcess = (void*) ( pAppData->body )[ kAppDataHProcessOffset ];
	setDataP->pi.hThread = (void*) ( pAppData->body )[ kAppDataHThreadOffset ];
	setDataP->pi.dwProcessId = ( pAppData->body )[ kAppDataProcessIDOffset ];
	setDataP->pi.dwThreadId = ( pAppData->body )[ kAppDataThreadIDOffset ];

	setDataP->pecntUsage = ( pAppData->body )[ kAppDataCntUsageOffset ];
	setDataP->peth32DefaultHeapID = ( pAppData->body )[ kAppDataDefaultHeapIDOffset ];
	setDataP->peth32ModuleID = ( pAppData->body )[ kAppDataModuleIDOffset ];
	setDataP->pecntThreads = ( pAppData->body )[ kAppDataCntThreadsOffset ];
	setDataP->peth32ParentProcessID = ( pAppData->body )[ kAppDataParentProcessIDOffset ];
	setDataP->pepcPriClassBase = ( pAppData->body )[ kAppDataPriClassBaseOffset ];
	setDataP->pedwFlags = ( pAppData->body )[ kAppDataFlagsOffset ];
}

void RNISetAppData( AppDataType *appDataP, struct HArrayOfInt* pAppData )
{
	( pAppData->body )[ kAppDataHWNDOffset ] = (DWORD) appDataP->hwnd;
	( pAppData->body )[ kAppDataHProcessOffset ] = (DWORD) appDataP->pi.hProcess;
	( pAppData->body )[ kAppDataHThreadOffset ] = (DWORD) appDataP->pi.hThread;
	( pAppData->body )[ kAppDataProcessIDOffset ] = appDataP->pi.dwProcessId;
	( pAppData->body )[ kAppDataThreadIDOffset ] = appDataP->pi.dwThreadId;

	( pAppData->body )[ kAppDataCntUsageOffset ] = appDataP->pecntUsage;
	( pAppData->body )[ kAppDataDefaultHeapIDOffset ] = appDataP->peth32DefaultHeapID;
	( pAppData->body )[ kAppDataModuleIDOffset ] = appDataP->peth32ModuleID;
	( pAppData->body )[ kAppDataCntThreadsOffset ] = appDataP->pecntThreads;
	( pAppData->body )[ kAppDataParentProcessIDOffset ] = appDataP->peth32ParentProcessID;
	( pAppData->body )[ kAppDataPriClassBaseOffset ] = appDataP->pepcPriClassBase;
	( pAppData->body )[ kAppDataFlagsOffset ] = appDataP->pedwFlags;
}

void RNISetIntArrayRegion( struct HArrayOfInt* pArray, long start, long numToSet, long *dataP )
{
	long			i;

	for ( i = 0; i < numToSet; i++ )
		( pArray->body )[ i + start ] = *dataP++;
}

void RNISetLongArrayRegion( struct HArrayOfLong* pArray, long start, long numToSet, __int64 *dataP )
{
	long			i;

	for ( i = 0; i < numToSet; i++ )
		( pArray->body )[ i + start ] = *dataP++;
}

void RNIGetIntArrayRegion( struct HArrayOfInt* pArray, long start, long numToSet, long *dataP )
{
	long			i;

	for ( i = 0; i < numToSet; i++ )
		*dataP++ = ( pArray->body )[ i + start ];
}

void RNIGetLongArrayRegion( struct HArrayOfLong* pArray, long start, long numToSet, __int64 *dataP )
{
	long			i;

	for ( i = 0; i < numToSet; i++ )
		*dataP++ = ( pArray->body )[ i + start ];
}




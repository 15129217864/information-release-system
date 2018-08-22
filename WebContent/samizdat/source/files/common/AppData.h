/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_AppData_H
#define INC_AppData_H

/*
The AppDataType is used with processes on Windows.
It contains the main HWND of the process, the PROCESS_INFORMATION struct (returned from CreateProcess),
and fields from the PROCESSENTRY32, as applicable.
The PROCESSENTRY32.th32ProcessID field is not in AppDataType,
as the PROCESS_INFORMATION struct already contains that information.
This struct is passed between Java and native code as a series of jint's
*/

typedef struct tagAppDataType {
	HWND					hwnd;
	PROCESS_INFORMATION		pi;
	DWORD					pecntUsage;
	DWORD					peth32DefaultHeapID;
	DWORD					peth32ModuleID;
	DWORD					pecntThreads;
	DWORD					peth32ParentProcessID;
	LONG					pepcPriClassBase;
	DWORD					pedwFlags;
} AppDataType;

	//	when an AppDataType is stored as an jint array, the length of the array,
	//	and the offsets of each member of the array

enum {
	kAppDataHWNDOffset = 0,
	kAppDataHProcessOffset = 1,
	kAppDataHThreadOffset = 2,
	kAppDataProcessIDOffset = 3,
	kAppDataThreadIDOffset = 4,
	kAppDataCntUsageOffset = 5,
	kAppDataDefaultHeapIDOffset = 6,
	kAppDataModuleIDOffset = 7,
	kAppDataCntThreadsOffset = 8,
	kAppDataParentProcessIDOffset = 9,
	kAppDataPriClassBaseOffset = 10,
	kAppDataFlagsOffset = 11,
	kAppDataNumInts = 12
};

#endif


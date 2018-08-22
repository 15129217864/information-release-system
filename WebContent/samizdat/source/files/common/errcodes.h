/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

/*
	Error codes returned from various routines.
*/

#ifndef INC_ERRORS_H
#define INC_ERRORS_H

enum {
	kErrNoErr = 0,

	kErrGenericErr = 0x20000000,
	kErrUnimplementedErr,
	kErrFileOpen,
	kErrGetFileTime,
	kErrFileTimeToDosDateTime,

		//	5
	kErrGetFileVersionInfoSize,
	kErrMallocErr,
	kErrGetFileVersionInfo,
	kErrVerQueryValue,
	kErrParamErr,

		//	10
	kErrCreateTempFile,
	kErrFindExecutable,
	kErrSetForegroundWindow,
	kErrRegOpenKeyEx,
	kErrRegEnumKey,

		//	15
	kErrRegQueryValue,
	kErrFileNotFound,
	kErrSHGetFileInfo,
	kErrCreateProcess,
	kErrEnumThreadWindows,

		//	20
	kErrParseCommandLine,
	kErrGetShortPathName,
	kErrSetWindowPos,
	kErrCloseWindow,
	kErrShellExecute,

		//	25
	kErrBuildDIBSection,
	kErrCreateCompatibleDC,
	kErrShowWindow,
	kErrDDEInitialize,
	kErrDdeCreateStringHandle,

		//	30
	kErrDdeConnect,
	kErrWinExec,
	kErrGetVolumeInformation,
	kErrGetVolumeCapInfo,
	kErrGetDriveType,

		//	35
	kErrGetVolumes,
	kErrGetFileAttributes,
	kErrGetBinaryType,
	kErrSystemParametersInfo,
	kErrFindFirstFile,

		//	40
	kErrFindNextFile,
	kErrFindNextFileNoMoreFiles,
	kErrOLENotInited,
	kErrImageListDraw,
	kErrImageListGetIcon,

		//	45
	kErrDrawIcon,
	kErrCantCreateString,
	kErrException,
	kErrShortString,
	kErrSHGetPathFromIDList,

		//	50
	kErrParseDisplayName,
	kErrSHGetDesktopFolder,
	kErrSEH,
	kErrGetDriveDisplayName,
	kErrCantCreateStringVector,

		//	55
	kErrOpenMntFile,
	kErrStatFS,
	kErrEnumerateProcessesInit,
	kErrEnumerateProcessesSnapshot,
	kErrEnumerateProcessesFirst
};

#endif


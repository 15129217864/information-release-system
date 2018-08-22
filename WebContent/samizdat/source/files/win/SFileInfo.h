/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_iFiles_H
#define INC_iFiles_H

#include "comdefs.h"
#include "CString.h"
#include "CDateBundle.h"

/*------------------------------------------------------------------------
CLASS
	SFileInfo

	Used to get information on files.

DESCRIPTION
	Used to get information on files.

------------------------------------------------------------------------*/

class SFileInfo
{
public:
	
		///////////////////////
		//
		//  Returns the short file name of a file or directory.
		//	I.e., "c:\program files" becomes "c:\progra~1"
		//
		//  [in]	inFileName		The	name of the volume.
		//  [out]	outFileName		The display name.
		//
	static	ErrCode iGetShortPathName( const CStr *inFileName, CStr *outFileName );

		///////////////////////
		//
		//  Retrieves the three dates associated with a file, and puts them in the
		//	given CDateBundle
		//
		//  [in]	fullPath		The file name.
		//  [out]	dateBundle		On exit, contains the three dates for the file.
		//
	static	ErrCode iGetFileDates( const CStr *fullPath, CDateBundle *dateBundle );

		///////////////////////
		//
		//  Returns the attributes associated with a file
		//
		//  [in]	fullPath		The file name.
		//  [out]	val				On exit, contains the file attributes, one of the eFileAttributes constants.
		//
	static	ErrCode iGetFileAttributes( const CStr *fullPath, unsigned long *val );

		///////////////////////
		//
		//  Indicates which bits returned from iGetFileAttributes are valid.
		//
		//  [in]	fullPath		The file name.
		//  [out]	val				On exit, contains the mask.
		//
	static	ErrCode iGetFileAttributesReadMask( const CStr *fullPath, unsigned long *val );

		///////////////////////
		//
		//  Sets the attributes associated with a file
		//
		//  [in]	fullPath		The file name.
		//  [out]	flagMask		Indicates which bits in newFlags are significant
		//  [out]	newFlags		Contains the new values of each bit. (if that bit is set in flagMask.)
		//
	static	ErrCode	iSetFileAttributes( const CStr *fullPath, unsigned long flagMask, unsigned long newFlags );

		///////////////////////
		//
		//  Indicates which bits can be set by iSetFileAttributes.
		//
		//  [in]	fullPath		The file name.
		//  [out]	val				On exit, contains the mask.
		//
	static	ErrCode	iGetFileAttributesWriteMask( const CStr *fullPath, unsigned long *val );

		///////////////////////
		//
		//  Wraps GetExecutableType()
		//
		//  [in]	fullPath		The file name.
		//  [out]	val				On exit, contains the executable type, one of the eExecutableType constants.
		//
	static	ErrCode iGetExecutableType( const CStr *fullPath, unsigned long *val );

		///////////////////////
		//
		//  Returned by iGetFileAttributes.
		//

	enum {
		kFileAttributes_FILE_EXECUTABLE = 0x01,
		kFileAttributes_FILE_DIR = 0x02,
		kFileAttributes_FILE_HIDDEN = 0x04,
		kFileAttributes_FILE_STATIONERY = 0x08,
		kFileAttributes_FILE_HAS_BNDL = 0x10,
		kFileAttributes_FILE_BEEN_INITED = 0x20,
		kFileAttributes_FILE_NO_INITS = 0x40,
		kFileAttributes_FILE_SHARED = 0x80,
		kFileAttributes_FILE_NAME_LOCKED = 0x100,
		kFileAttributes_FILE_CUSTOM_ICON = 0x200,
		kFileAttributes_FILE_SYSTEM = 0x400,
		kFileAttributes_FILE_ARCHIVE = 0x800,
		kFileAttributes_FILE_DEVICE = 0x1000,
		kFileAttributes_FILE_TEMP = 0x2000,
		kFileAttributes_FILE_SPARSE = 0x4000,
		kFileAttributes_FILE_REPARSEPOINT = 0x8000,
		kFileAttributes_FILE_COMPRESSED = 0x10000,
		kFileAttributes_FILE_OFFLINE = 0x20000,
		kFileAttributes_FILE_NOT_CONTENT_INDEXED = 0x40000,
		kFileAttributes_FILE_ENCRYPTED = 0x80000,
		kFileAttributes_FILE_READONLY = 0x100000,

		kFileAttributesStandardReadMask = ( kFileAttributes_FILE_EXECUTABLE |
											kFileAttributes_FILE_DIR |
											kFileAttributes_FILE_HIDDEN |
											kFileAttributes_FILE_READONLY |
											kFileAttributes_FILE_SYSTEM |
											kFileAttributes_FILE_ARCHIVE |
											kFileAttributes_FILE_COMPRESSED ),

		kFileAttributesExReadMask = (		kFileAttributes_FILE_EXECUTABLE |
											kFileAttributes_FILE_DIR |
											kFileAttributes_FILE_HIDDEN |
											kFileAttributes_FILE_READONLY |
											kFileAttributes_FILE_SYSTEM |
											kFileAttributes_FILE_ARCHIVE |
											kFileAttributes_FILE_COMPRESSED |

											kFileAttributes_FILE_DEVICE |
											kFileAttributes_FILE_TEMP |
											kFileAttributes_FILE_SPARSE |
											kFileAttributes_FILE_REPARSEPOINT |
											kFileAttributes_FILE_OFFLINE |
											kFileAttributes_FILE_NOT_CONTENT_INDEXED |
											kFileAttributes_FILE_ENCRYPTED ),

		kFileAttributesStandardWriteMask = ( kFileAttributes_FILE_HIDDEN |
											kFileAttributes_FILE_READONLY |
											kFileAttributes_FILE_ARCHIVE |
											kFileAttributes_FILE_TEMP |
											kFileAttributes_FILE_NOT_CONTENT_INDEXED )
	} eFileAttributes;
	
		///////////////////////
		//
		//  Returned by iGetExecutableType.
		//
	enum {
		kExecutableTypeWin32Exe = 0,
		kExecutableTypeDOSExe = 1,
		kExecutableTypeWOWExe = 2,
		kExecutableTypePIFExe = 3,
		kExecutableTypePOSIXExe = 4,
		kExecutableTypeOS216Exe = 5,
		kExecutableTypeUnknown = 9
	} eExecutableType;
};

#endif


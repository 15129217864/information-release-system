/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_SAliases_H
#define	INC_SAliases_H

#include "CFSpec.h"
#include "CString.h"

/*------------------------------------------------------------------------
CLASS
	SAliases

	Used to create and resolve aliases.

DESCRIPTION
	Used to create and resolve aliases.

------------------------------------------------------------------------*/

class SAliases
{
public:

		///////////////////////
		//
		//	getFileCategory() returns one of these values.
		//
	typedef enum tageFileCategory {	
		kCategoryVolume = 0,
		kCategoryDirectory = 1,
		kCategoryFile = 2,
		kCategoryAlias = 3,
		kCategoryUnknown = 4,
		kCategoryOSXExtendedInfoBit  = 0x10000000
	} eFileCategory;

		///////////////////////
		//
		//	Indicates whether resolving an alias should cause interaction with the user.
		//
	typedef enum tageAliasInteraction {
		kResolveAliasNoUI = 1,
		kResolveAliasUI = 2
	} eAliasInteraction;

		///////////////////////
		//
		//	Get the type ( alias, volume, etc. ) of a disk object.
		//	One of the eFileCategory values will be placed in 'category'
		//
		//	[in]	theSpec		the file/folder/volume
		//	[out]	category	on return, will contain the category of 'theSpec'
		//
	static	ErrCode getFileCategory( const CFSpec *theSpec, eFileCategory *category );

		///////////////////////
		//
		//	Create an alias to a file or folder.
		//
		//	[in]	targetSpec		the target of the new alias
		//	[in]	csNewAliasPath	the full path of the new alias file
		//	[in]	creator			ignored
		//	[in]	flags			ignored
		//
	static	ErrCode createAlias( const CFSpec *targetSpec, const CStr *csNewAliasPath,
								long creator, long flags );

		///////////////////////
		//
		//	Create an alias to a volume.
		//
		//	[in]	targetVRef		the vRefNum of the target
		//	[in]	csNewAliasPath	the full path of the new alias file
		//	[in]	creator			ignored
		//	[in]	flags			ignored
		//
	static	ErrCode createVolumeAlias( long targetVRef, const CStr *csNewAliasPath,
									long creator, long flags );

		///////////////////////
		//
		//	Resolve an alias.
		//
		//	[in]	inSpec		the alias to resolve
		//	[out]	outSpec		on return, contains the resolved file
		//	[in]	flags		one of the eAliasInteraction values
		//
	static	ErrCode resolveAnAlias( const CFSpec *inSpec, CFSpec *outSpec,
									tageAliasInteraction flags );

protected:
	static	ErrCode makeAliasFile( const CFSpec *targetSpec, const CFSpec *newAliasSpec,
									long creator, long type );
	static	ErrCode isAliasFile( const FSSpec *theSpec,
								Boolean *aliasFileFlag, Boolean *folderFlag );
	static	ErrCode resolveAliasFileMountOption( FSSpec *theSpec,
												Boolean resolveAliasChains,
												Boolean *targetIsFolder,
												Boolean *wasAliased,
												Boolean mountRemoteVols);
private:
	static	ErrCode getFileCategoryOSX( const CFSpec *theSpec, eFileCategory *category );
};

#endif

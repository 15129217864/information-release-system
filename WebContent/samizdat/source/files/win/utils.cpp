/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "utils.h"
#include "SVolumes.h"

void setVolumeCapInfo(
unsigned __int64 *capP,
DWORD sectorsPerClst,
DWORD bytesPerSector,
DWORD numFreeClst,
DWORD totNumClst )
{
	unsigned __int64	llBytesPerClst;

	llBytesPerClst = (unsigned __int64) bytesPerSector * (unsigned __int64) sectorsPerClst;

	capP[ SVolumes::kVolumeCapInfoCapacityOffset ] = llBytesPerClst * (unsigned __int64) totNumClst;
	capP[ SVolumes::kVolumeCapInfoFreeSpaceOffset ] = llBytesPerClst * (unsigned __int64) numFreeClst;
}


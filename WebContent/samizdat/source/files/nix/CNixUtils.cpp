/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CNixUtils.h"
#include <stdio.h>
#include <unistd.h>
#include <mntent.h>
#include <sys/statfs.h>
#include <statbuf.h>

long CNixUtils::iStat( long selector, CStr *csFilePath, long *retArray, CDateBundle *dateBundle )
{
	struct stat		sbuf;
	long			err;
	
	if ( selector == 0 )
		err = stat( csFilePath->getBuf(), &sbuf );
	else
		err = lstat( csFilePath->getBuf(), &sbuf );
	if ( err != 0 ) {
		printf( "is: %ld for %s\n", err, csFilePath->getBuf() );
		return err;
	}

	retArray[ kStatOffs_dev ] = sbuf.st_dev;
	retArray[ kStatOffs_ino ] = sbuf.st_ino;
	retArray[ kStatOffs_mode ] = sbuf.st_mode;
	retArray[ kStatOffs_nlink ] = sbuf.st_nlink;
	retArray[ kStatOffs_uid ] = sbuf.st_uid;
	retArray[ kStatOffs_gid ] = sbuf.st_gid;
	retArray[ kStatOffs_rdev ] = sbuf.st_rdev;
	retArray[ kStatOffs_size ] = sbuf.st_size;
	retArray[ kStatOffs_blksize ] = sbuf.st_blksize;
	retArray[ kStatOffs_blocks ] = sbuf.st_blocks;
	
	dateBundle->setDate( CDateBundle::kModificationDate, &( sbuf.st_mtime ) );
	dateBundle->setDate( CDateBundle::kCreationDate, &( sbuf.st_ctime ) );
	dateBundle->setDate( CDateBundle::kAccessDate, &( sbuf.st_atime ) );

	return kErrNoErr;
}

long CNixUtils::iReadLink( const CStr *csLinkName, CStr *csResolvedName )
{
	long		numCopied;

	numCopied = readlink( csLinkName->getBuf(), csResolvedName->getBuf(), csResolvedName->getByteCapacity() - 1 );
	if ( numCopied < 0 ) {
		printf( "irl: %ld for %s\n", numCopied, csLinkName->getBuf() );
		return numCopied;
	}
	
	( csResolvedName->getBuf() )[ numCopied ] = 0;
	
	return kErrNoErr;	
}


/*
struct mntent
  {
    char *mnt_fsname;		//Device or server for filesystem.
    char *mnt_dir;		//Directory mounted on.
    char *mnt_type;		//Type of filesystem: ufs, nfs, etc.
    char *mnt_opts;		//Comma-separated options for fs.
    int mnt_freq;		//Dump frequency (in days).
    int mnt_passno;		//Pass number for `fsck'.
  };
*/

long CNixUtils::iGetMntEnt( const CStr *csMntFileName, CStringVector *retQuads, long maxToReturn, long *numReturned )
{
	FILE			*fp;
	struct mntent	*mp;
	long			numDone;

	fp = fopen( csMntFileName->getBuf(), "r" );
	if ( fp == NULL ) {
		printf( "igme: can't open %s\n", csMntFileName->getBuf() );
		return kErrOpenMntFile;
	}

	numDone = 0;
	while ( numDone < maxToReturn ) {
		mp = getmntent( fp );
		if ( mp == NULL )
			break;
		
		retQuads->appendString( new CStr( mp->mnt_fsname ) );
		retQuads->appendString( new CStr( mp->mnt_dir ) );
		retQuads->appendString( new CStr( mp->mnt_type ) );
		retQuads->appendString( new CStr( mp->mnt_opts ) );
		++numDone;
	}

	fclose( fp );

	*numReturned = numDone;

	return kErrNoErr;
}

long CNixUtils::iStatFS( const CStr *csFilePath, long *retArray )
{
	struct statfs	fileSysStats;
	long			err;

	err = statfs( csFilePath->getBuf(), &fileSysStats );
	if ( err != 0 ) {
		printf( "sfs: err for %s=%ld\n", csFilePath->getBuf(), err );
		return kErrStatFS;
	}

	retArray[ kStatFSOffs_type ] = fileSysStats.f_type;
	retArray[ kStatFSOffs_bsize ] = fileSysStats.f_bsize;
	retArray[ kStatFSOffs_blocks ] = fileSysStats.f_blocks;
	retArray[ kStatFSOffs_bfree ] = fileSysStats.f_bfree;
	retArray[ kStatFSOffs_bavail ] = fileSysStats.f_bavail;
	retArray[ kStatFSOffs_files ] = fileSysStats.f_files;
	retArray[ kStatFSOffs_ffree ] = fileSysStats.f_ffree;
	retArray[ kStatFSOffs_fsid0 ] = fileSysStats.f_fsid.__val[ 0 ];
	retArray[ kStatFSOffs_fsid1 ] = fileSysStats.f_fsid.__val[ 1 ];

	return kErrNoErr;
}


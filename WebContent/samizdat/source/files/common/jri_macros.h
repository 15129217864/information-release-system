/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_jri_macros_H
#define INC_jri_macros_H
		
#define JSTRING						JRIStringID
#define JINT						jint
#define JLONG						jlong
#define JBYTEARRAY					jbyteArray
#define JINTARRAY					jintArray
#define	JLONGARRAY					jlongArray
#define	JOBJECTARRAY				jobjectArray

#define	CHECKNULL(a)				{ if ( (a) == NULL ) { theErr = kErrParamErr; goto bail; } }
#define CHECKSIZE(a,b)				{ if ( (a) == NULL || JRI_GetScalarArrayLength( pEnv, (a) ) < (b) ) \
										{ theErr = kErrParamErr; goto bail; } }

#define	GETINTARRAYLEN(a)			JRI_GetScalarArrayLength( pEnv, (a) )
#define	LOCKINTARRAY(a)				JRI_GetIntArrayElements( pEnv, (a) )
#define	UNLOCKINTARRAY(a,b)

#define	GETBYTEARRAYLEN(a)			JRI_GetScalarArrayLength( pEnv, (a) )
#define	LOCKBYTEARRAY(a)			JRI_GetByteArrayElements( pEnv, (a) )
#define	UNLOCKBYTEARRAY(a,b)

#define COPYPSTRINGTOJBYTES(a,b)	copyPStringToJBytes( pEnv, (a), (b) )

#define	GETOBJECTARRAYLEN(a)		( (unsigned long) JRI_GetObjectArrayLength( pEnv, (a) ) )

#define SETJINT1(a,b)				setJint1( pEnv, (a), (b) )
#define SETJINT2(a,b,c)				setJint2( pEnv, (a), (b), (c) )
#define SETJINT4(a,b,c,d,e)			setJint4( pEnv, (a), (b), (c), (d), (e) )
#define GETJINT2(a,b,c)				getJint2( pEnv, (a), (b), (c) )
#define GETJINT4(a,b,c,d,e)			getJint4( pEnv, (a), (b), (c), (d), (e) )
#define SETJLONG1(a,b)				setJlong1( pEnv, (a), (b) )
#define SETJLONG2(a,b,c)			setJlong2( pEnv, (a), (b), (c) )
#define GETJLONG(a,b)				getJlong( pEnv, (a), (b) )

#define SETINTARRAYREGION(a,b,c,d)	setJintArrayRegion( pEnv, (a), (b), (c), (d) )
#define SETLONGARRAYREGION(a,b,c,d)	setLongArrayRegion( pEnv, (a), (b), (c), (d) )
#define GETINTARRAYREGION(a,b,c,d)	getJintArrayRegion( pEnv, (a), (b), (c), (d) )
#define GETLONGARRAYREGION(a,b,c,d)	getLongArrayRegion( pEnv, (a), (b), (c), (d) )

#define	SETSTRARRAYELEMENTUTF(a,b,c,d)	setStrArrayElementUTF( pEnv, (a), (b), (c), (d) )

#define	SLAMFTACS(a,b,c,d)			slamFTACs( pEnv, (a), (b), (c), (d) )

#define	SLAMSPECSTOARRAYS(a,b,c,d,e,f)		slamSpecsToArrays( pEnv, (a), (b), (c), (d), (e), (f) )
#define	SLAMSPECSTOARRAYSC(a,b,c,d,e,f,g,h)		slamSpecsToArraysC( pEnv, (a), (b), (c), (d), (e), (f), (g), (h) )

#define	FILENAMEARRAYTOSPECARRAY(a,b,c)		fileNameArrayToSpecArray( pEnv, (a), (b), (c) )


#endif	//	include switch

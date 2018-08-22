/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_jni_macros_H
#define INC_jni_macros_H

#define JOBJECT						jobject
#define JSTRING						jstring
#define	JVECTOR						jobject
#define JINT						jint
#define JLONG						jlong
#define JBYTEARRAY					jbyteArray
#define JINTARRAY					jintArray
#define	JLONGARRAY					jlongArray
#define	JOBJECTARRAY				jobjectArray

#define	CHECKNULL(a)				{ if ( (a) == NULL ) { theErr = kErrParamErr; goto bail; } }

#define	SETSTRARRAYELEMENT(a,b,c)	JNISetStrArrayElement( pEnv, (a), (b), (c) );
#define	GETAPPDATA(a,b)				JNIGetAppData( pEnv, (a), (b) );
#define	SETAPPDATA(a,b)				JNISetAppData( pEnv, (a), (b) );

#ifdef __cplusplus
	#define CHECKSIZE( a, b )						{ if ( ( (a) == NULL ) || ( pEnv->GetArrayLength( (a) ) < ( (long) (b) ) ) ) \
														{ theErr = kErrParamErr; goto bail; } }
	#define	SETINTARRAYREGION(a,b,c,d)				pEnv->SetIntArrayRegion( (a), (b), (c), (d) );	
	#define	SETLONGARRAYREGION(a,b,c,d)				pEnv->SetLongArrayRegion( (a), (b), (c), (d) );	
	#define	GETINTARRAYREGION(a,b,c,d)				pEnv->GetIntArrayRegion( (a), (b), (c), (d) );	
	#define	GETLONGARRAYREGION(a,b,c,d)				pEnv->GetLongArrayRegion( (a), (b), (c), (d) );	

	#define	GETBYTEARRAYLEN(a)						( (unsigned long) pEnv->GetArrayLength( (a) ) )
	#define	GETINTARRAYLEN(a)						( (unsigned long) pEnv->GetArrayLength( (a) ) )
	#define	GETOBJECTARRAYLEN(a)					( (unsigned long) pEnv->GetArrayLength( (a) ) )

	#define	LOCKBYTEARRAY(a)						pEnv->GetByteArrayElements( (a), 0 )
	#define	UNLOCKBYTEARRAY(a,b)					pEnv->ReleaseByteArrayElements( (a), (jbyte*) (b), 0 )
	#define	LOCKINTARRAY(a)							pEnv->GetIntArrayElements( (a), 0 )
	#define	UNLOCKINTARRAY(a,b)						pEnv->ReleaseIntArrayElements( (a), (b), 0 )

	#define	SETOBJECTARRAYELEMENT( a, b, c )		pEnv->SetObjectArrayElement( (a), (b), (c) )

	#define	NEWSTRINGUTF( a )						pEnv->NewStringUTF( (a) )
	#define	GETSTRINGUTFCHARS( a, b )				pEnv->GetStringUTFChars( (a), (b) )
	#define	RELEASESTRINGUTFCHARS( a, b )			pEnv->ReleaseStringUTFChars( (a), (b) )
	#define	GETOBJECTARRAYELEMENT( a, b )			pEnv->GetObjectArrayElement( (a), (b) )
	#define	GETSTRARRAYELEMENT( a, b )				pEnv->GetObjectArrayElement( (a), (b) )

	#define SETJINT1(a,b)							setJint1( pEnv, (a), (b) )
	#define SETJINT2(a,b,c)							setJint2( pEnv, (a), (b), (c) )
	#define SETJINT4(a,b,c,d,e)						setJint4( pEnv, (a), (b), (c), (d), (e) )
	#define GETJINT2(a,b,c)							getJint2( pEnv, (a), (b), (c) )
	#define GETJINT4(a,b,c,d,e)						getJint4( pEnv, (a), (b), (c), (d), (e) )
	#define SETJLONG1(a,b)							setJlong1( pEnv, (a), (b) )
	#define SETJLONG2(a,b,c)						setJlong2( pEnv, (a), (b), (c) )

	#define GETJLONG(a,b)							getJlong( pEnv, (a), (b) )

	#define COPYPSTRINGTOJBYTES(a,b)				copyPStringToJBytes( pEnv, (a), (b) )
	#define	SETSTRARRAYELEMENTUTF(a,b,c,d)			setStrArrayElementUTF( pEnv, (a), (b), (c), (d) )
	#define	SLAMFTACS(a,b,c,d)						slamFTACs( pEnv, (a), (b), (c), (d) )
	#define	SLAMSPECSTOARRAYS(a,b,c,d,e,f)			slamSpecsToArrays( pEnv, (a), (b), (c), (d), (e), (f) )
	#define	SLAMSPECSTOARRAYSC(a,b,c,d,e,f,g,h)		slamSpecsToArraysC( pEnv, (a), (b), (c), (d), (e), (f), (g), (h) )
	#define	FILENAMEARRAYTOSPECARRAY(a,b,c)			fileNameArrayToSpecArray( pEnv, (a), (b), (c) )
	
#else

	#define CHECKSIZE( a, b )						{ if ( ( (a) == NULL ) || ( ( *pEnv )->GetArrayLength( pEnv, (a) ) < ( (long) (b) ) ) ) \
														{ theErr = kErrParamErr; goto bail; } }
	#define	SETINTARRAYREGION(a,b,c,d)				( *pEnv )->SetIntArrayRegion( pEnv, (a), (b), (c), (d) );	
	#define	SETLONGARRAYREGION(a,b,c,d)				( *pEnv )->SetLongArrayRegion( pEnv, (a), (b), (c), (d) );	
	#define	GETINTARRAYREGION(a,b,c,d)				( *pEnv )->GetIntArrayRegion( pEnv, (a), (b), (c), (d) );	
	#define	GETLONGARRAYREGION(a,b,c,d)				( *pEnv )->GetLongArrayRegion( pEnv, (a), (b), (c), (d) );	

	#define	GETBYTEARRAYLEN(a)						( (unsigned long) ( *pEnv )->GetArrayLength( pEnv, (a) ) )
	#define	GETINTARRAYLEN(a)						( (unsigned long) ( *pEnv )->GetArrayLength( pEnv, (a) ) )
	#define	GETOBJECTARRAYLEN(a)					( (unsigned long) ( *pEnv )->GetArrayLength( pEnv, (a) ) )

	#define	LOCKBYTEARRAY(a)						( *pEnv )->GetByteArrayElements( pEnv, (a), 0 )
	#define	UNLOCKBYTEARRAY(a,b)					((*pEnv)->ReleaseByteArrayElements)( pEnv, (a), (jbyte*) (b), 0 )
	#define	LOCKINTARRAY(a)							( *pEnv )->GetIntArrayElements( pEnv, (a), 0 )
	#define	UNLOCKINTARRAY(a,b)						( *pEnv )->ReleaseIntArrayElements( pEnv, (a), (b), 0 )

	#define	SETOBJECTARRAYELEMENT( a, b, c )		(*pEnv)->SetObjectArrayElement( pEnv, (a), (b), (c) )
	#define	NEWSTRINGUTF( a )						(*pEnv)->NewStringUTF( pEnv, (a) )
	#define	GETSTRINGUTFCHARS( a, b )				(*pEnv)->GetStringUTFChars( pEnv, (a), (b) )
	#define	RELEASESTRINGUTFCHARS( a, b )			(*pEnv)->ReleaseStringUTFChars( pEnv, (a), (b) )
	#define	GETOBJECTARRAYELEMENT( a, b )			(*pEnv)->GetObjectArrayElement( pEnv, (a), (b) )
	#define	GETSTRARRAYELEMENT( a, b )				(*pEnv)->GetObjectArrayElement( pEnv, (a), (b) )

	#define SETJINT1(a,b)							setJint1( pEnv, (a), (b) )
	#define SETJINT2(a,b,c)							setJint2( pEnv, (a), (b), (c) )
	#define SETJINT4(a,b,c,d,e)						setJint4( pEnv, (a), (b), (c), (d), (e) )
	#define GETJINT2(a,b,c)							getJint2( pEnv, (a), (b), (c) )
	#define GETJINT4(a,b,c,d,e)						getJint4( pEnv, (a), (b), (c), (d), (e) )
	#define SETJLONG1(a,b)							setJlong1( pEnv, (a), (b) )
	#define SETJLONG2(a,b,c)						setJlong2( pEnv, (a), (b), (c) )

	#define GETJLONG(a,b)							getJlong( pEnv, (a), (b) )

	#define COPYPSTRINGTOJBYTES(a,b)				copyPStringToJBytes( pEnv, (a), (b) )
	#define	SETSTRARRAYELEMENTUTF(a,b,c,d)			setStrArrayElementUTF( pEnv, (a), (b), (c), (d) )
	#define	SLAMFTACS(a,b,c,d)						slamFTACs( pEnv, (a), (b), (c), (d) )
	#define	SLAMSPECSTOARRAYS(a,b,c,d,e,f)			slamSpecsToArrays( pEnv, (a), (b), (c), (d), (e), (f) )
	#define	SLAMSPECSTOARRAYSC(a,b,c,d,e,f,g,h)		slamSpecsToArraysC( pEnv, (a), (b), (c), (d), (e), (f), (g), (h) )
	#define	FILENAMEARRAYTOSPECARRAY(a,b,c)			fileNameArrayToSpecArray( pEnv, (a), (b), (c) )

#endif



#endif	//	include switch

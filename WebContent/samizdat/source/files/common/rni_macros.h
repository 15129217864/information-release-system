/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_rni_macros_H
#define INC_rni_macros_H


#define JOBJECT						struct Hjava_lang_Object*
#define JSTRING						struct Hjava_lang_String*
#define	JVECTOR						struct Hjava_util_Vector*
#define JINT						long
#define JINTARRAY					struct HArrayOfInt*
#define	JLONGARRAY					struct HArrayOfLong*
#define	JOBJECTARRAY				struct HArrayOfString*

#define	CHECKNULL(a)				{ if ( (a) == NULL ) { theErr = kErrParamErr; goto bail; } }
#define	CHECKSIZE( a, b )			{ if ( ( (a) == NULL ) || ( obj_length( (a) ) < (unsigned long) (b) ) ) \
										{ theErr = kErrParamErr; goto bail; } }

#define	SETSTRARRAYELEMENT(a,b,c)	( (a)->body )[ (b) ] = ( c == NULL ? NULL : (c)->getJString() )
#define	GETAPPDATA(a,b)				RNIGetAppData( (a), (b) );
#define	SETAPPDATA(a,b)				RNISetAppData( (a), (b) );

#define	SETINTARRAYREGION(a,b,c,d)	RNISetIntArrayRegion( (a), (b), (c), (d) );	
#define	SETLONGARRAYREGION(a,b,c,d)	RNISetLongArrayRegion( (a), (b), (c), (d) );	
#define	GETINTARRAYREGION(a,b,c,d)	RNIGetIntArrayRegion( (a), (b), (c), (d) );	
#define	GETLONGARRAYREGION(a,b,c,d)	RNIGetLongArrayRegion( (a), (b), (c), (d) );	
	
#define	GETINTARRAYLEN(a)			obj_length( (a) )
#define	LOCKINTARRAY(a)				((a)->body)
#define	UNLOCKINTARRAY(a,b)

#define	GETOBJECTARRAYLEN(a)		obj_length( (a) )

#define	GETSTRARRAYELEMENT(a,b)		( (a)->body )[ b ]


#endif		//	include switch

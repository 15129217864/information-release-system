 /****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CFSpec_H
#define INC_CFSpec_H

#include "comdefs.h"
#include "jmacros.h"

/*------------------------------------------------------------------------
CLASS
	CFSpec

	Represents an FSSpec.

DESCRIPTION
	Represents an FSSpec. This is constructed from a vRef, a parID, and either
	a JBYTEARRAY containing a Pascal string or a Pascal string.
	A set of macros is provided to make declaration, construction, and deletion easier

MACROS
<PRE>

DECLARECFSPEC(a)
	Allocates space for a CFSpec*, and sets it to null

MAKECFSPEC(a,b,c,d)
	Constructs a CFSpec from two jints and a JBYTEARRAY
	'a' is the CFSpec*
	'b' is the vRefNum
	'c' is the parID
	'd' is the JBYTEARRAY

DELETECFSPEC(a)
	If 'a' isn't null, calls 'delete' on it.

</PRE>

------------------------------------------------------------------------*/

class CFSpec
{
public:

#if defined(DO_JNI)

		///////////////////////
		//
		//	Construct using a JNI byte array. Available only in JNI builds.
		//	if pNm is NULL, the FSSpec's name will be set to an empty string.
		//
	CFSpec( JNIEnv *pEnv, long vr, long pi, JBYTEARRAY pNm );

#elif defined(DO_JRI)

		///////////////////////
		//
		//	Construct using a JRI byte array. Available only in JRI builds.
		//	if pNm is NULL, the FSSpec's name will be set to an empty string.
		//
	CFSpec( JRIEnv *pEnv, long vr, long pi, JBYTEARRAY pNm );

#endif

		///////////////////////
		//
		//	Construct using a Pascal string
		//	if ps is NULL, the FSSpec's name will be set to an empty string.
		//
	CFSpec( long vr, long pi, StringPtr ps );

		///////////////////////
		//
		//	Copy constructor
		//
	CFSpec( const CFSpec *cfs );

		///////////////////////
		//
		//	Destructor
		//
	virtual	~CFSpec();

		///////////////////////
		//
		//	Return vRefNum
		//
	virtual	long		getVRef() const;

		///////////////////////
		//
		//	Returns parID
		//
	virtual	long		getParID() const;

		///////////////////////
		//
		//	Returns a pointer to the name
		//
	virtual	StringPtr	getName();

		///////////////////////
		//
		//	Returns a pointer to the FSSpec
		//
	virtual	FSSpec		*getSpecP();

		///////////////////////
		//
		//	Calls FSMakeFSSpec(), returns return value.
		//
	virtual	ErrCode		verifySpec();

		///////////////////////
		//
		//	Indicates whether name[0] == 0
		//
	virtual	BOOL		isNameNull() const;

		///////////////////////
		//
		//	Reinitialize from another CFSpec.
		//
	virtual	void		setFrom( const CFSpec *cfs );

		///////////////////////
		//
		//	Reinitialize from a Pascal string.
		//
	virtual	void		setFrom( long vr, long pi, StringPtr ps );

		///////////////////////
		//
		//	Copy to an FSSpec
		//
	virtual	void		putInto( FSSpec *toSpec ) const;

		///////////////////////
		//
		//	Write info on the spec to the debugger
		//
	virtual	void		dumpInfo( long theErr, const LPCTSTR message ) const;

protected:

	FSSpec		theSpec;

	enum {
		kSpecNameLen = sizeof(Str63)	//	sizeof the name element in FSSpec
	};
};

#define		DECLARECFSPEC(a)	CFSpec	*a = NULL;
#define		DELETECFSPEC(a)		{ if (a != NULL ) delete a; }
#define		MAKECFSPEC(a,b,c,d)		a = new CFSpec( pEnv, b, c, d )

#endif


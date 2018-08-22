/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef INC_CVECTOR_H
#define INC_CVECTOR_H

#include "comdefs.h"

/*------------------------------------------------------------------------
CLASS
	CVector

	Stores a variable number of objects.

DESCRIPTION
	Stores a variable number of void*'s.

------------------------------------------------------------------------*/

class CVector {
	public:

		///////////////////////
		//
		//  Constructor
		//
		//	[in]	initNum		the number of elements initially
		//	[in]	incr		the number of elements by which the internal array will grow
		//						when necessary. These two arguments have the same meaning as those
		//						to the Java Vector class' constructor.
		//
	CVector( long initNum, long incr );

		///////////////////////
		//
		//  Destructor
		//
	virtual	~CVector( void );

		///////////////////////
		//
		//  Appends an object
		//
	virtual	ErrCode	append( void *obj );

		///////////////////////
		//
		//  Returns the i'th object, or NULL if i is not a valid index.
		//
	virtual	void	*elementAt( long i );

		///////////////////////
		//
		//  Sets the indicated element. If 'i' is out of range, does nothing.
		//
	virtual	void	setAt( long i, void *obj );

		///////////////////////
		//
		//  Returns the number of objects in this vector.
		//
	virtual	long	getSize( void );

		///////////////////////
		//
		//  Returns the index of the given object, of -1 if not found.
		//
	virtual	long	findIndex( void *obj );

		///////////////////////
		//
		//  Removes the given object.
		//
	virtual	void	remove( void *obj );

		///////////////////////
		//
		//  Inserts the given object after another object.
		//
	virtual	ErrCode	insertAfter( void *obj, void *afterWhich );

		///////////////////////
		//
		//  Prepends the given object.
		//
	virtual ErrCode	prepend( void *obj );

		///////////////////////
		//
		//  Inserts the given object at the given position.
		//
	virtual	ErrCode	insertAt( void *obj, long where );

protected:

	long	increment, numElements, numSlots, initialNum;
	void	**objs;
};

#endif

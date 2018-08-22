/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#ifndef	INC_CFilePathOSX_H
#define	INC_CFilePathOSX_H

#include "CString.h"
#include "CFSpec.h"

/*------------------------------------------------------------------------
CLASS
	CFilePathOSX

	Represents a file path on OSX.

DESCRIPTION
	Represents a file path. The argument to the constructor is assumed to be a
	path in POSIX format (i.e., quoted-printable form, with slashes as the filename
	separators.)
	When a CFilePathOSX object is constructed, the file path will be converted
	from POSIX format to HFS, by converting it from quoted-printable format, and replacing
	the slashes with colons.
	After constructing a CFilePathOSX object with a path name received from Java,
	the getPStr method can be called to get the path name as a Pascal string.

------------------------------------------------------------------------*/

class CFilePathOSX : public CStrA
{
public:

		///////////////////////
		//
		//	Construct from a CStr
		//
	CFilePathOSX( const CStr *cs );

		///////////////////////
		//
		//	Destructor
		//
	virtual	~CFilePathOSX();

		///////////////////////
		//
		//	Get the path into the given Pascal buffer, which is 'len' bytes long.
		//
	virtual	ErrCode	getPStr( StringPtr ps, long len );

protected:
	virtual	void			deQP();
	virtual	unsigned char	hexToByte( unsigned char cHigh, unsigned char cLow );
};

#endif


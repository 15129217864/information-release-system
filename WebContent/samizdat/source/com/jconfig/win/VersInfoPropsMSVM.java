/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.win;


import com.jconfig.*;
import java.util.Properties;

/**
Used by the VersionInfoMSVM class to retrieve the version information from a file.

<P>
This object is created with the full path of the executable; native code is used to get the version info for that file,
and each piece of version info, such as "CompanyName", etc. is retrieved from native code and stored into this object.
 
@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class VersInfoPropsMSVM extends Properties {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final String		keys[] = {	"CompanyName",
												"FileDescription",
												"FileVersion",
												"InternalName",
												"LegalCopyright",
												"OriginalFilename",
												"ProductName",
												"ProductVersion" };

	VersInfoPropsMSVM( String fileName ) {
		String		tempValue[];
		int			i, theErr, nativeH;

		nativeH = AppUtilsMSVM.vipGetFileVersionInfoStart( fileName );

		if ( nativeH == 0 )
			throw new IllegalArgumentException( "file has no version info " + fileName );

		tempValue = new String[ 1 ];

		for ( i = 0; i < keys.length; i++ ) {
			theErr = AppUtilsMSVM.vipVerQueryValue( nativeH, keys[ i ], tempValue );

			if ( theErr == ErrCodes.ERROR_NONE )
				put( keys[ i ], tempValue[ 0 ] );
		}

		AppUtilsMSVM.vipGetFileVersionInfoEnd( nativeH );
	}
}



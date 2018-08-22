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
import java.io.PrintStream;

/**
Used to obtain the version information for a file.

<P>
A VersInfoPropsMSVM object is used to obtain the version info; this object retrieves various items
from that object and returns them as indicated below.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class VersionInfoMSVM implements VersionInfo {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private VersionNumber	versionNumber;
	private String			cpString, internalName, fileDescription;

/**
'fileName' must be the full path of the file.
If this file doesn't have version info, or if an error occurs, throws an IllegalArgumentException
*/

	VersionInfoMSVM( String fileName ) {	
		VersInfoPropsMSVM	props;

		props = new VersInfoPropsMSVM( fileName );

		versionNumber = new VersionNumber( props.getProperty( "FileVersion" ) );

		cpString = props.getProperty( "LegalCopyright" );
 		internalName = props.getProperty( "InternalName" );
 		fileDescription = props.getProperty( "FileDescription" );
	}

	public String getVersionString() {
		return versionNumber.getVersionString();
	}

	public int getMajorVersion() {
		return versionNumber.getMajorVersion();
	}
	
	public int getMinorVersion() {
		return versionNumber.getMinorVersion();
	}
	
	public String getCopyrightNotice() {
		if ( cpString != null )
			return cpString;
		else
			return internalName;
	}

	public String getProductName() {
		return internalName;
	}
	
	public String getDescription() {
		return fileDescription;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + "VersionInfoMSVM: fileDescription=" + fileDescription );
		ps.println( indent + "  internalName=" + internalName +	", cpString=" + cpString +
							"  versionString=" + getVersionString() );
		ps.println( indent + "  versionNum=" + getMajorVersion() + "  revisionNum=" + getMinorVersion() +
							"  revisionStage=" + versionNumber.getRevisionStage() +
							"  buildNum=" + versionNumber.getBuildNumber() );
	}
}


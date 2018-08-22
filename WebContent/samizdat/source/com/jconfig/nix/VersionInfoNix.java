/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.nix;


import com.jconfig.*;
import java.io.PrintStream;

/**
This is a preliminary class derived from the Windows class of similar name. This class may be replaced
or modified in a future version.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class VersionInfoNix implements VersionInfo {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private VersionNumber	versionNumber;
	private String			cpString, internalName, fileDescription;


	VersionInfoNix( String fileName ) {	
		versionNumber = new VersionNumber( "1.0" );

		cpString = "unknown";
 		internalName = "unknown";
 		fileDescription = "unknown";
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
		ps.println( indent + "VersionInfoNix: fileDescription=" + fileDescription );
		ps.println( indent + "  internalName=" + internalName +	", cpString=" + cpString +
							"  versionString=" + getVersionString() );
		ps.println( indent + "  versionNum=" + getMajorVersion() + "  revisionNum=" + getMinorVersion() +
							"  revisionStage=" + versionNumber.getRevisionStage() +
							"  buildNum=" + versionNumber.getBuildNumber() );
	}
}


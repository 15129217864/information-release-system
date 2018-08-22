/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig.mac;

import com.jconfig.*;

import java.io.File;
import java.io.PrintStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Properties;

/**
This class reads and caches the Mac-specific properties. It's created from the global props object.

<P>
A set of accessor methods are provided, for example:

<PRE>
	getPropFindExtensions
		- returns the value of the "com.jconfig.FileRegistryFactoryMac.findExtensions"
		  property from the '
</PRE>

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class MacPropertiesMRJ {
	public static final int			kPropDefault = 0;
	public static final int			kPropIgnoreIC = 1;

	private static final String		kPropFindExtensions = "com.jconfig.mac.FileRegistryFactoryMac.findExtensions";
	private static final String		kPropFindFinderInfo = "com.jconfig.mac.FileRegistryFactoryMac.findFinderInfo";
	private static final String		kPropGetApps = "com.jconfig.mac.FileRegistryFactoryMac.getApps";
	private static final String		kPropIterate = "com.jconfig.mac.FileRegistryFactoryMac.iterate";
	private static final String		kPropLaunchURL = "com.jconfig.mac.FileRegistryFactoryMac.launchURL";
	private static final String		kPropFileUtilsClassName = "com.jconfig.mac.FileRegistryMRJ.FileUtilsClassName";

	private String					propFileUtilsClassName = null;
	private int						propFindExtensions = kPropDefault,
									propFindFinderInfo = kPropDefault,
									propGetApps = kPropDefault,
									propIterate = kPropDefault,
									propLaunchURL = kPropDefault;

	public MacPropertiesMRJ( File curDir, String prefsFileName ) {
		Properties			props;

		try {
			props = tryReadProperties( curDir, prefsFileName );
			if ( props != null )
				initProperties( props );
		}
		catch ( Exception e ) {
		}
	}

	public MacPropertiesMRJ( Properties p ) {
		initProperties( p );
	}

	public int getPropFindExtensions() {
		return propFindExtensions;
	}

	public int getPropFindFinderInfo() {
		return propFindFinderInfo;
	}

	public int getPropGetApps() {
		return propGetApps;
	}

	public int getPropIterate() {
		return propIterate;
	}

	public int getPropLaunchURL() {
		return propLaunchURL;
	}

	public String getPropFileUtilsClassName() {
		return propFileUtilsClassName;
	}

	public void dumpProperties( PrintStream ps ) {
		ps.println( "propFindExtensions=" + propFindExtensions );
		ps.println( "propFindFinderInfo=" + propFindFinderInfo );
		ps.println( "propGetApps=" + propGetApps );
		ps.println( "propIterate=" + propIterate );
		ps.println( "propLaunchURL=" + propLaunchURL );
		ps.println( "propFileUtilsClassName=" + propFileUtilsClassName );
	}

	private Properties tryReadProperties( File curDir, String prefsFileName ) {
		File				fl;
		DataInputStream		dis;
		Properties			props;

		try {
			fl = new File( curDir, prefsFileName );
			if ( !fl.exists() )
				return null;

			props = new Properties();

			dis = new DataInputStream( new FileInputStream( fl ) );

			props.load( dis );

			dis.close();
		}
		catch ( Exception e ) {
			return null;
		}
		
		return props;
	}
	
	private void initProperties( Properties props ) {
		String				propVal;

		try {
			propVal = props.getProperty( kPropFindExtensions );
			if ( propVal != null ) propFindExtensions = Integer.parseInt( propVal );
		}
		catch ( Exception e ) {}
		
		try {
			propVal = props.getProperty( kPropFindFinderInfo );
			if ( propVal != null ) propFindFinderInfo = Integer.parseInt( propVal );
		}
		catch ( Exception e ) {}
		
		try {
			propVal = props.getProperty( kPropGetApps );
			if ( propVal != null ) propGetApps = Integer.parseInt( propVal );
		}
		catch ( Exception e ) {}
		
		try {
			propVal = props.getProperty( kPropIterate );
			if ( propVal != null ) propIterate = Integer.parseInt( propVal );
		}
		catch ( Exception e ) {}
		
		try {
			propVal = props.getProperty( kPropLaunchURL );
			if ( propVal != null ) propLaunchURL = Integer.parseInt( propVal );
		}
		catch ( Exception e ) {}
		
		try {
			propFileUtilsClassName = props.getProperty( kPropFileUtilsClassName );
		}
		catch ( Exception e ) {
			propFileUtilsClassName = null;
		}
	}
}

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
import java.io.File;
import java.util.Vector;

/**
Represents a command which can be sent to a Windows application.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppCommandMSVM implements AppCommand {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
The RegCommandMSVM from which this object was created. For instance:
<PRE>
    { .txt, Txt_File, open, "C:\\windows\\notepad.exe %1" }
</PRE>
*/

	private RegCommandMSVM		vat[];

/**
The arguments which have been added to this object.
*/

	private Vector				theArgs;
	private	Class				permissibleClasses[];
	private int					maxNumArgs;

/**
Create from an array of RegCommandMSVM objects.
*/

	AppCommandMSVM( RegCommandMSVM v[] ) {
		int			i, valZ;

		theArgs = new Vector( 2, 2 );
		maxNumArgs = 0;

		if ( v == null ) {
			vat = null;
			return;
		}

		vat = new RegCommandMSVM[ v.length ];

		for ( i = 0; i < vat.length; i++ ) {
			vat[ i ] = v[ i ];

			valZ = figureOutMaxNumArgs( vat[ i ] );

			if ( valZ > maxNumArgs )
				maxNumArgs = valZ;
		}

		try {
			permissibleClasses = new Class[ 3 ];
			permissibleClasses[ 0 ] = Class.forName( "java.lang.String" );
			permissibleClasses[ 1 ] = Class.forName( "java.io.File" );
			permissibleClasses[ 2 ] = Class.forName( "com.jconfig.DiskObject" );
		}
		catch ( ClassNotFoundException e ) {
			permissibleClasses = null;
		}
	}

	public String getCommand() {
		return ( vat == null ? null : vat[ 0 ].getVerb() );
	}
	
	public String asString() {
		return ( vat == null ? null : vat[ 0 ].getVerb() );
	}

	public AppCommand redup() {
		return new AppCommandMSVM( vat );
	}

	public int getMaxNumArgs() {
		return maxNumArgs;
	}
	
	public void addArg( Object arg ) {
		theArgs.addElement( convertArgToString( arg ) );

//		if ( arg == null || !( arg instanceof String ) )
//			throw new IllegalArgumentException( "wrong argument type " + arg.getClass() );

//		theArgs.addElement( arg );
	}

	String getExtensionOfArguments() {
		if ( theArgs.size() < 1 )
			return null;

		return new FileExtension( (String) theArgs.elementAt( 0 ) ).getString();
	}

	String convertArgToString( Object arg ) {
		File			fl;

		if ( arg == null )
			throw new IllegalArgumentException( "arg is null" );

		if ( arg instanceof String )
			return (String) arg;

		if ( arg instanceof DiskObject )
			fl = ( (DiskObject) arg ).getFile();
		else if ( arg instanceof File )
			fl = (File) arg;
		else
			throw new IllegalArgumentException( "wrong arg type: it must be String, File, or DiskObject. type=" +
												arg.getClass() );

		if ( fl == null )
			return " ";
		else
			return fl.getPath();
	}

	public int getNumArgs() {
		return theArgs.size();
	}
	
	public void clearArgs() {
		theArgs.setSize( 0 );
	}

	public Class[] getPermissibleArgumentType( int position ) {
		Class		retVal[];
		int			i;
		
		if ( permissibleClasses == null )
			return null;

		retVal = new Class[ permissibleClasses.length ];

		for ( i = 0; i < permissibleClasses.length; i++ )
			retVal[ i ] = permissibleClasses[ i ];

		return retVal;
	}
	
	public Object getArg( int which ) {
		try {
			return (String) theArgs.elementAt( which );
		}
		catch ( Exception e ) {
			return null;
		}
	}

	public boolean isNumArgsUnlimited() {
		return false;
	}

	public boolean isSingleInstanceCapable() {
		return false;
	}
	
	public RegCommandMSVM getRegCommand( String ext ) {
		int			i;

		if ( vat == null )
			return null;

		if ( ext == null )
			return vat[ 0 ];

		for ( i = 0; i < vat.length; i++ ) {
			if ( ext.equalsIgnoreCase( vat[ i ].getExtension() ) )
				return vat[ i ];
		}

		return vat[ 0 ];
	}

	private int figureOutMaxNumArgs( RegCommandMSVM cmd ) {
		String		temp;
		int			num, index, len;
		char		c;

		temp = cmd.getTemplate();
		num = 0;
		index = 0;
		len = temp.length();

		while ( index < len ) {
			index = temp.indexOf( "%", index );
			if ( index < 0 )
				break;
			++index;
			if ( index < len ) {
				c = temp.charAt( index );
				if ( c >= '0' && c <= '9' )
					++num;
			}
		}

		return num;
	}

	private String fileTypesAsString() {
		String		retStr, tempStr;
		int			i;

		if ( vat == null )
			return "";

		retStr = "";

		for ( i = 0; i < vat.length; i++ ) {
			tempStr = vat[ i ].getExtension();
			if ( retStr.indexOf( tempStr ) < 0 )
				retStr = retStr + " " + tempStr;
		}

		return retStr;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		if ( vat == null )
			ps.println( indent + "no vat" );
		else
			ps.println( indent + "verb=" + vat[ 0 ].getVerb() + ", " + theArgs.size() +
						" of " + maxNumArgs + " args, types=" + fileTypesAsString() );
	}
}




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
Contains four strings which contain information retrieved from the Registry. Each object
represents a command stored in the Registry for a specific app.

The strings are:

<UL>
<LI>extension: the extension of the file type for this command
<LI>regKey: the Registry key under which the command was found
<LI>verb: the command to be performed
<LI>template: the command line used to perform the command
</UL>

For instance:
<PRE>
    { .txt, Txt_File, open, "C:\\windows\\notepad.exe %1" }
</PRE>

<P>
This is occasionally referred to as a 'VAT', as in 'Verb And Template'.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class RegCommandMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private String		extension, regKey, verb, template;

	RegCommandMSVM( String x, String k, String v, String t ) {
		extension = x;
		regKey = k;
		verb = v;
		template = t;
	}
	
	String getVerb() {
		return verb;
	}
	
	String getTemplate() {
		return template;
	}
	
	String getRegistryKey() {
		return regKey;
	}
	
	String getExtension() {
		return extension;
	}

	public String toString() {
		return "VAT " + hashCode() + ": " + asString();
	}
	
	private String asString() {
		return "{" + extension + ", " +
				regKey + ", " +
				verb + ", " +
				template + "}";
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		ps.println( indent + asString() );
	}
}

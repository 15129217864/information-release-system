package com.tolstoy.testjc;

import com.jconfig.*;
import java.io.PrintStream;

/**
Prints every 20th entry in the mappings database.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class BasicCEVisitor implements ConfigEntryVisitor {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	int				count;
	PrintStream		ps;

	BasicCEVisitor( PrintStream p ) {
		ps = p;
		count = 0;
	}

	public void visit( ConfigEntry fd ) {
		if ( ( count % 20 ) == 0 ) {
			ps.println( "at " + count + " is:" );
			fd.dumpInfo( ps, "  " );
		}

		++count;
	}
}


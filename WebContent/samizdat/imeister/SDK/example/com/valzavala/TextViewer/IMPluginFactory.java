package com.valzavala.TextViewer;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import com.jconfig.*;
import com.tolstoy.imagemeister.*;

/**
Copyright (c) 1998 Samizdat Productions. All Rights Reserved.
Feel free to modify this file as you wish.
*/

public class IMPluginFactory implements PluginFactoryI {
	private static final int		kLastKnownInterfaceVersion = 0x100;

	public int registerPlugins( int interfaceVersion ) {
		int			theErr;

		if ( interfaceVersion > kLastKnownInterfaceVersion )
			return 0;

		theErr = PluginManager.addPlugin( new TextViewerPlugin(), 0 );
		
		if ( theErr == 0 )
			return 1;
		else
			return 0;
	}

	public PluginWatcherI getPluginWatcher() {
		return null;
	}

	public int getInterfaceVersion() {
		return kLastKnownInterfaceVersion;
	}
}


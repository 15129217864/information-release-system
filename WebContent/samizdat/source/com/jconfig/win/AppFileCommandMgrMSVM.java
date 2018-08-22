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
import java.io.File;
import java.io.PrintStream;
import java.util.Vector;

/**
Used by AppFileMSVM to manage its commands.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class AppFileCommandMgrMSVM {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";
	private String				filePath;
	private AppCommand			theCommands[];
	private FileType			theFileTypes[];
	private int					numCommands;
	private boolean				bAlreadyRequestedFileTypes;
	private AppCommandWatcher	acWatcher;
	private AppFileMSVM			owner;

/**
CommandLineUtilsMSVM.findVerbs() is called to search through the Registry for shell commands
("\\shell\\commands\\open", etc.) which contain the path of this application.
These commands are used to initialize the array of commands, as well as the array of file types.

@param path the path of the app
@param name the name of the app
@param af the AppFileMSVM which owns this object
@param watch the object to which notifications will be sent
*/

	AppFileCommandMgrMSVM( String path, String name, RegCommandMSVM vat[], AppFileMSVM af, AppCommandWatcher watch ) {
		filePath = path;
		owner = af;
		acWatcher = watch;
		bAlreadyRequestedFileTypes = false;

		createCommands( vat );
		createFileTypes( vat );
	}

/**
CommandLineUtilsMSVM.findVerbs() is called to search through the Registry for shell commands
("\\shell\\commands\\open", etc.) which contain the path of this application.
These commands are used to initialize the array of commands, as well as the array of file types.

@param path the path of the app
@param name the name of the app
@param af the AppFileMSVM which owns this object
@param watch the object to which notifications will be sent
*/

	AppFileCommandMgrMSVM( String path, String name, AppFileMSVM af, AppCommandWatcher watch ) {
		RegCommandMSVM		vat[];

		filePath = path;
		owner = af;
		acWatcher = watch;
		bAlreadyRequestedFileTypes = false;

		vat = CommandLineUtilsMSVM.findVerbs( path, name );

		createCommands( vat );
		createFileTypes( vat );
	}

/**
If no commands could be found in the Registry, this creates a generic 'open' command.
*/
	
	private AppCommand createDefaultCommand() {
		RegCommandMSVM		tempVat[];

		Trace.println( "using default command for " + filePath );

		tempVat = new RegCommandMSVM[ 1 ];

		tempVat[ 0 ] = new RegCommandMSVM( "", "", "open", "\"" + filePath + "\" \"%1\"" );

		return new AppCommandMSVM( tempVat );
	}

/**
'vat' contains an array of verbs ("open",etc.) retrieved from the Registry, together with the command
lines of those verbs.
*/

	private void createCommands( RegCommandMSVM vat[] ) {
		RegCommandMSVM		initArray[], tempVat[];
		String				verbString;
		Vector				tempCommands;
		int					i, numFound, numVat, curVat, initArrayCount;

		numCommands = 0;
		theCommands = null;

			//	if no commands were found, give us one dummy 'open' command

		if ( vat == null ) {
			numCommands = 1;
			theCommands = new AppCommand[ 1 ];
			theCommands[ 0 ] = createDefaultCommand();
			return;
		}

		numVat = vat.length;
		tempVat = new RegCommandMSVM[ numVat ];
		for ( i = 0; i < numVat; i++ )
			tempVat[ i ] = vat[ i ];

		tempCommands = new Vector( 5, 5 );

			//	group the RegCommandMSVM objects with the same verb(i.e., "open") together, and then
			//	create an AppCommandMSVM object with that array of RegCommandMSVMs, and add it to
			//	our list of commands.

		for ( curVat = 0; curVat < numVat; curVat++ ) {
			if ( tempVat[ curVat ] == null )
				continue;

			verbString = tempVat[ curVat ].getVerb();

			for ( i = curVat + 1, numFound = 1; i < numVat; i++ ) {
				if ( tempVat[ i ] != null && tempVat[ i ].getVerb().equals( verbString ) )
					++numFound;
			}

			initArray = new RegCommandMSVM[ numFound ];
			for ( i = curVat, initArrayCount = 0; i < numVat; i++ ) {
				if ( tempVat[ i ] != null && tempVat[ i ].getVerb().equals( verbString ) ) {
					initArray[ initArrayCount++ ] = tempVat[ i ];
					tempVat[ i ] = null;
				}
			}

			tempCommands.addElement( new AppCommandMSVM( initArray ) );
		}

		numCommands = tempCommands.size();
		theCommands = new AppCommand[ numCommands ];
		for ( i = 0; i < numCommands; i++ )
			theCommands[ i ] = (AppCommand) tempCommands.elementAt( i );
	}

/**
Look through the array of RegCommandMSVM objects, and determine each file extension. For instance, this
application might be listed in the Registry as being able to open '.txt', '.doc', and '.readme' files.
*/

	private void createFileTypes( RegCommandMSVM vat[] ) {
		String				extString, tempStr;
		Vector				tempExts;
		int					i, tempSize, numVat, curVat;
		boolean				bFound;

		theFileTypes = null;

		if ( vat == null )
			return;

		numVat = vat.length;

		tempExts = new Vector( 5, 5 );

		for ( curVat = 0; curVat < numVat; curVat++ ) {
			if ( vat[ curVat ] == null )
				continue;

			extString = vat[ curVat ].getExtension();

			tempSize = tempExts.size();
			for ( i = 0, bFound = false; i < tempSize; i++ ) {
				tempStr = (String) tempExts.elementAt( i );
				if ( tempStr.equalsIgnoreCase( extString ) ) {
					bFound = true;
					break;
				}
			}

			if ( !bFound )
				tempExts.addElement( extString );
		}

		if ( tempExts.size() < 1 )
			return;

		theFileTypes = new FileType[ tempExts.size() ];
		for ( i = 0; i < tempExts.size(); i++ )
			theFileTypes[ i ] = new FileType( new FileExtension( (String) tempExts.elementAt( i ) ) );
	}

/**
	Return an array of this object's FileTypes.
*/

	FileType[] getFileTypes( int maxToReturn ) {
		FileType		retArray[];
		int				i;

		if ( theFileTypes == null || maxToReturn < 1 )
			return null;

		retArray = new FileType[ theFileTypes.length ];

		for ( i = 0; i < theFileTypes.length; i++ )
			retArray[ i ] = theFileTypes[ i ];

		return retArray;
	}

/**
Retrieves the arguments from the AppCommand, and calls AppUtilsMSVM.launchApp to launch the app with those arguments.
If the app was launched OK, an AppProcessMSVM object representing the process is returned.
*/

	AppProcess performCommand( AppCommand command, int flags ) {
		RegCommandMSVM		regCmd;
		Vector				argVector;
		String				commandLine, extensionToUse;
		int					i, theErr, retData[], numArgs;

		retData = new int[ AppProcessMSVM.kSizeofAppData ];
		argVector = new Vector( 10, 10 );

		synchronized( command ) {
			numArgs = command.getNumArgs();
			for ( i = 0; i < numArgs; i++ )
				argVector.addElement( (String) command.getArg( i ) );

			extensionToUse = ( (AppCommandMSVM) command ).getExtensionOfArguments(); 
		}

		regCmd = ( (AppCommandMSVM) command ).getRegCommand( extensionToUse );

		commandLine = CommandLineUtilsMSVM.createCommandLine( regCmd.getTemplate(), argVector );

		theErr = AppUtilsMSVM.launchApp( commandLine, null, null, null, retData, 0, null );

/*
			//	prior to 1.2.1, this contained instead:
		theErr = AppUtilsMSVM.launchApp( filePath, regCmd.getVerb(),
											regCmd.getRegistryKey(), regCmd.getTemplate(),
											retData, numArgs, args );
*/

		if ( theErr == ErrCodes.ERROR_NONE )
			return new AppProcessMSVM( owner, retData, acWatcher );
		else {
			Trace.println( "AppFileMSVM.performCommand, theErr=" + theErr );
			return null;
		}
	}

/**
Searches the list of command for one with the given name, and returns it. If one couldn't be found, returns null.
*/

	AppCommand getCommand( String commandName ) {
		int			i;
		
		for ( i = 0; i < numCommands; i++ ) {
			if ( commandName.equals( theCommands[ i ].asString() ) )
				return theCommands[ i ].redup();
		}

		return null;
	}

/**
Return an array of all the commands.
*/
	
	AppCommand[] getAllCommands() {
		AppCommand		retVal[];
		int				i;

		retVal = new AppCommand[ numCommands ];

		for ( i = 0; i < numCommands; i++ )
			retVal[ i ] = theCommands[ i ].redup();

		return retVal;
	}

/**
Returns a String array containing the 'command' object's commands, or null if it has no commands.
*/
 
	private String[] getArgsFromCommand( AppCommand command ) {
		String		retVal[];
		int			numArgs, i;
		
		numArgs = command.getNumArgs();

		if ( numArgs < 1 )
			return null;
		
		retVal = new String[ numArgs ];

		for ( i = 0; i < numArgs; i++ )
			retVal[ i ] = JUtils.javaPathToWinPath( (String) command.getArg( i ) );
		
		return retVal;
	}

	public void dumpInfo( PrintStream ps, String indent ) {
		int			i;

		if ( numCommands < 1 ) {
			ps.println( indent + "  no commands" );
			return;
		}

		for ( i = 0; i < numCommands; i++ )
			theCommands[ i ].dumpInfo( ps, indent + "  " );
	}
}




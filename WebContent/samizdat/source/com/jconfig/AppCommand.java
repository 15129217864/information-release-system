/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:23 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

package com.jconfig;

import java.io.PrintStream;

/**
Represents a command which can be sent to an application or one of its instances. After
retrieving an AppCommand from an AppFile or an AppProcess, you can add arguments to it,
if desired, and then tell the AppFile or AppProcess to execute the command. In the case
of an AppFile, if the command succeeds, it returns an AppProcess after executing the
command.

<P>
For instance:

<PRE>
    AppFile    appFile;
    AppCommand openCommand;

    //  first obtain the appFile, perhaps using one of the
    //  FileRegistry.getApps() methods.

    //  Then, try to get a command from the app for opening
    //  with a document.

    openCommand = appFile.getCommand( AppCommand.kAppCommandOpenDoc );

    //  If 'openCommand' is not null, this app can accept commands of
    //  this type
    
    if ( openCommand != null ) {
        //  Add the file we want to open to the command.

        appCommand.addArg( "c:\\myfile.txt" );

        //  Tell the app to perform the command, and spawn a new
        //  instance

        appProcess = appFile.performCommand( appCommand, 0 );

        //  if appProcess is non-null, the process was created successfully,
        //  and 'c:\myfile.txt' was used to open it
    }
</PRE>

<P>
Some classes which implement this interface do not take arguments;
if you try to add an argument to a class of this type, an IllegalArgumentException
exception will be thrown. After obtaining an AppCommand object, use the getMaxNumArgs()
method to determine how many arguments the object can take.

<P>
Currently, for those classes which implement this interface and which accept arguments,
'arg' must a String, a File, or a DiskObject, which is interpreted as follows:
<PRE>
    if ( arg == null )
        throw an IllegalArgumentException
    else if ( arg instanceof String )
        add it to the list of arguments
    else if ( arg instanceof File )
        add ( (File) arg ).getPath() to the list of arguments;
    else if ( arg instanceof DiskObject )
        add ( ( (DiskObject) arg ).getFile() ).getPath() to the list of arguments
    else
        throw an IllegalArgumentException
</PRE>

<B>IMPORTANT NOTE:</B> The classes of objects which a specific instance's addArg() method can take is subject
to change. Currently, several classes implement this interface; in the future, the objects
which can be passed to the addArg() method may change. Use the getPermissibleArgumentType()
method to determine exactly which type of object you can pass to the addArg() method.

<P>
<B>WINDOWS NOTE:</B> As of version 1.2.1, any arguments which are added to a command will be surrounded
with quotes when the command line which is used to perform the command is created. The arguments will not
be changed by this class, only when the command line is created.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

public interface AppCommand extends DumpInfo {
	static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

/**
Name of command to open an application with one or more documents.
*/

	public static final String kAppCommandOpenDoc = "open";

/**
Name of command to open an application with no documents.
*/

	public static final String kAppCommandOpenApp = "start";

/**
Name of command to tell an application to print one or more documents.
*/

	public static final String kAppCommandPrintDoc = "print";

/**
Name of command to tell an application to play one or more documents.
*/

	public static final String kAppCommandPlay = "play";

/**
Name of command to tell an application to quit.
*/

	public static final String kAppCommandQuit = "quit";

/**
Return the name of this command.
*/

	String getCommand();

/**
Return the command as a string.
*/

	String asString();

/**
Create a new instance of this command ( not a clone ).
*/

	AppCommand redup();

/**
Returns the maximum number of arguments this command can take. -1 is returned if this command
can take an unlimited number.
*/

	int getMaxNumArgs();

/**
Adds 'arg' to the end of the list of this command's arguments.
*/

	void addArg( Object arg );

/**
Returns the current number of arguments for this command.
*/

	int getNumArgs();

/**
Returns an array containing the classes which can be used as arguments for the indicated
position in the argument list. If this command does not take commands, null is returned.
*/

	Class[] getPermissibleArgumentType( int position );

/**
Delete any arguments previously added to the command.
*/

	void clearArgs();

/**
Returns the indicated argument in this command's argument list. If 'which' is out of range, null
is returned.
*/

	Object getArg( int which );

/**
Returns whether this command can take an unlimited number of arguments.
*/

	boolean isNumArgsUnlimited();

/**
Returns whether this command can be executed without creating a new process.
*/

	boolean isSingleInstanceCapable();
}


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


import com.apple.cocoa.foundation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import com.jconfig.JUtils;
import com.jconfig.FileType;
import com.jconfig.FileExtension;
import com.jconfig.FinderInfo;
import com.jconfig.Trace;

/**
Utilities using Apple's CocoaJava framework.

@author Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.
*/

class CocoaUtilsOSX {
	private static final String copyrightString = "JConfig Copyright (c) 1997-2002 Samizdat Productions. All Rights Reserved.";

	private static final String		KEY_BUNDLE_DOCUMENT_TYPES = "CFBundleDocumentTypes";
	private static final String		KEY_BUNDLE_TYPE_EXTENSIONS = "CFBundleTypeExtensions";
	private static final String		KEY_BUNDLE_TYPE_OSTYPES = "CFBundleTypeOSTypes";

/**
This method must be called before using the other methods of this class.
Calling mainBundle() causes the CocoaJava native code to be loaded.
*/

	public static void init() {
		NSBundle mainBundle = NSBundle.mainBundle();
		Trace.println( "mainBundle loaded " + mainBundle );
	}

/**
Gets the FileTypes for the given app.
<P>
Uses CocoaJava methods to parse the app's Info.plist file. That file
contains the file types with which the app is associated in its
CFBundleTypeExtensions and CFBundleTypeOSTypes subkeys of the
CFBundleDocumentTypes key.

@param fullPath the full path to the app, in POSIX format
@param creatorCode the app's creator code
@return an array of the file types. Some might have been created from extensions, some from OSTypes.
*/

	public static FileType[] getFileTypes( String fullPath, int creatorCode ) {
		int				autoreleasePool = 0;

		try {
			autoreleasePool = NSAutoreleasePool.push();
			
			return getFileTypes_inner( fullPath, creatorCode );
		}
		finally {
			NSAutoreleasePool.pop( autoreleasePool );
		}
	}

	private static FileType[] getFileTypes_inner( String fullPath, int creatorCode ) {
		ArrayList		extensions, osTypes, outputFileTypes;
		Iterator		iter;

		extensions = new ArrayList();
		osTypes = new ArrayList();
		outputFileTypes = new ArrayList();

		buildFileTypeLists( fullPath, extensions, osTypes );

		iter = extensions.iterator();
		while ( iter.hasNext() )
			outputFileTypes.add( new FileType( new FileExtension( "." + (String) iter.next() ) ) );

		iter = osTypes.iterator();
		while ( iter.hasNext() )
			outputFileTypes.add( new FileType( new FinderInfo( creatorCode, JUtils.asciiToInt( (String) iter.next() ) ) ) );

		return (FileType[]) outputFileTypes.toArray( new FileType[ outputFileTypes.size() ] );
	}

	private static void buildFileTypeLists( String fullPath, ArrayList extensions, ArrayList osTypes ) {
		NSBundle			bundle;
		NSDictionary		mainDict, innerDict;
		NSArray				dictArray;
		int					i, docCount;

		bundle = NSBundle.bundleWithPath( fullPath );
		if ( bundle == null )
			throw new IllegalArgumentException( "can't get bundle for " + fullPath );

		mainDict = bundle.infoDictionary();
		if ( mainDict == null )
			throw new IllegalArgumentException( "can't get mainDict for " + fullPath );
		  
		dictArray = (NSArray) mainDict.objectForKey( KEY_BUNDLE_DOCUMENT_TYPES );
		if ( dictArray == null )
			throw new IllegalArgumentException( "can't get dictArray for " + fullPath );

		docCount = dictArray.count();

		for ( i = 0; i < docCount; i++ ) {
			innerDict = (NSDictionary) dictArray.objectAtIndex( i );
			if ( innerDict == null )
				continue;

			addFileTypeLists( innerDict, extensions, osTypes );
		}
	}

/*
			<key>CFBundleTypeExtensions</key>
			<array>
				<string>rtf</string>
				<string>RTF</string>
			</array>
			<key>CFBundleTypeOSTypes</key>
			<array>
				<string>RTF </string>
			</array>
*/

	private static void addFileTypeLists( NSDictionary dict, ArrayList extensions, ArrayList osTypes ) {
		NSArray			tempArray;
		int				tempCount, i;

		tempArray = (NSArray) dict.objectForKey( KEY_BUNDLE_TYPE_EXTENSIONS );

		if ( tempArray != null ) {
			tempCount = tempArray.count();

			for ( i = 0; i < tempCount; i++ ) {
				addUniqueString( extensions, (String) tempArray.objectAtIndex( i ) );
			}
		}

		tempArray = (NSArray) dict.objectForKey( KEY_BUNDLE_TYPE_OSTYPES );

		if ( tempArray != null ) {
			tempCount = tempArray.count();

			for ( i = 0; i < tempCount; i++ ) {
				osTypes.add( (String) tempArray.objectAtIndex( i ) );
			}
		}
	}

	private static void addUniqueString( ArrayList stringList, String str ) {
		Iterator		iter;

		iter = stringList.iterator();
		while ( iter.hasNext() ) {
			if ( str.equalsIgnoreCase( (String) iter.next() ) ) {
				return;
			}
		}

		stringList.add( str );
	}

	public static void main( String args[] ) {
		FileType		ft[];

		init();

		//int autoreleasePool = NSAutoreleasePool.push();

		for ( int i = 0; i < 5; i++ ) {
			ft = getFileTypes( "/Applications/TextEdit.app", 0 );

			for ( int y = 0; y < ft.length; y++ )
				System.out.println( "ft=" + ft[ y ] );

			try {
				Thread.currentThread().sleep( 2000 );
			}
			catch ( Exception e ) {
			}
		}

		//NSAutoreleasePool.pop( autoreleasePool );
	}
}




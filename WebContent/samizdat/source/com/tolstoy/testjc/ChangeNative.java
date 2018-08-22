package com.tolstoy.testjc;

/*
	private static int callNGetMainMonitorInfo( xx ) {
		try {
			lockObject.lock()",
			return nGetMainMonitorInfo( xx )",
		}
		finally {
			lockObject.unlock()",
		}
	}

*/

import java.util.*;
import java.io.*;

class Func {
	Vector			args;
	String			name, argList;

	public void writeReplace( PrintStream ps ) {
		ps.println( "<replace dir=\".\" includes=\"*.java\" token=\"" + name + "\" value=\"call_" + name + "\"/>" );
	}

	public void writeWrapper( PrintStream ps ) {
		ps.println( "	private static int wrap_" + name + argList + " {" );
		ps.println( "		try {" );
		ps.println( "			lockObject.lock()," );
		ps.print( "			return " + name + "( " );

		for ( int i = 0; i < args.size(); i++ )
			ps.print( (String) args.elementAt( i ) + ", " );

		ps.println( " )," );
		ps.println( "		}" );
		ps.println( "		finally {" );
		ps.println( "			lockObject.unlock()," );
		ps.println( "		}" );
		ps.println( "	}" );
	}

	public Func( String s ) {
		StringTokenizer			st;
		String					token;
		int						index;

		args = new Vector( 1, 1 );

		index = s.indexOf( "(" );
		name = s.substring( 0, index );
		argList = s.substring( index, s.length() );

		st = new StringTokenizer( s.substring( index + 1, s.length() ) );
		while ( st.hasMoreTokens() ) {
			token = st.nextToken();
			if ( token.equals( "int" ) || token.equals( "byte" )  || token.equals( "long" ) || token.equals( "String" ) || token.equals( "," ) || token.equals( ")" ) )
				continue;

			if ( token.length() < 1 )
				continue;

			if ( token.endsWith( "," ) )
				token = token.substring( 0, token.length() - 1 );
			
			if ( token.endsWith( "[]" ) )
				token = token.substring( 0, token.length() - 2 );

			if ( token.endsWith( "," ) )
				token = token.substring( 0, token.length() - 1 );
			
			if ( token.endsWith( "[]" ) )
				token = token.substring( 0, token.length() - 2 );
			
			args.addElement( token );
		}	
	}
	
	public void dump( PrintStream ps ) {
		ps.print( name + ":" );
		for ( int i = 0; i < args.size(); i++ )
			ps.print( "   **" + (String) args.elementAt( i ) + "**   " );
	}
}

public class ChangeNative {
	public static void main( String args[] ) {
		Vector			vec = new Vector( 1, 1 );
		int				i;

		for ( i = 0; i < strs.length; i++ )
			vec.addElement( new Func( strs[ i ] ) );

		for ( i = 0; i < strs.length; i++ ) {
			( (Func) vec.elementAt( i ) ).writeReplace( System.out );
			System.out.println( "" );
		}
	}


	static String strs[] = {
	"nFindAPPLSingle( int creator, int vRefAndParID[], byte pName[], int flags )",
	"nFindAPPLMultiple( int creator, int vRefs[], int parIDs[], byte pNames[], int maxToReturn, int flags, int numReturned[] )",
	"nOpenExistingResFile( int vRef, int parID, byte[] pName )",
	"nCloseResFile( int fileFD )",
	"nGetResourceSize( int fileFD, int resName, int resID, int[] retSize )",
	"nGetResource( int fileFD, int resName, int resID, byte[] data )",

	"nICStart( int creator )",





	"nICStop( int ourHandle )",





	"nICGetMapEntrySize()",





	"nICGetSeed( int ourHandle )",





	"nICCountMapEntries( int ourHandle )",





	"nICGetIndMapEntry( int ourHandle, int whichRecord, byte[] record )",





	"nFindMatchesExt( int appCreator, String extension, int direction, int numReturned[], int maxToReturn, int cVals[], int tVals[] )",





	"nFindMatchesFInfo( int appCreator, int targetCreator, int targetType, int direction, int numReturned[], int maxToReturn, byte extensions[] )",





	"nFindAppByName( int appCreator, String matchName, int creators[], int vRefs[], int parIDs[], byte pNames[], int maxToReturn, int numRet[] )",





	"nLaunchURL( int appCreator, String url, int flags, String preferredBrowsers[] )"
	};
	
}












/*
closeresfile
icstop
	static String strs[] = {
		"nGetRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] )",
		"nSetRawResourceFork( int flags, int vRef, int parID, byte pName[], byte data[] )",
		"nSetForkLength( int flags, int whichFork, int vRef, int parID, byte pName[], long newLen )",


		"nSendAppDocs( int whichCommand, int appPSN[], String filePaths[], int flags )",
		"nQuitApp( int appPSN[], int flags )",
		"nLaunchApp( int vRef, int parID, byte pName[], int retPSN[], int flags )",
		"nLaunchWithDoc( int whichCommand, int vRef, int parID, byte pName[], String filePaths[], int retPSN[], int flags )",
		"nCreateFullPathName( int vRef, int parID, byte pName[], String retArray[] )",
		"nGetOpenableFileTypes( int vRef, int creator, int numReturned[], int fileTypes[] )",


		"nGetFileDate( int unused, int vRef, int parID, byte pName[], int datesArray[] )",

		"nSetFileDate( int unused, int vRef, int parID, byte pName[], int datesArray[] )",

		"nGetVolumeDate( int unused, int vRef, int datesArray[] )",

		"nSetVolumeDate( int unused, int vRef, int datesArray[] )",

		"nVerifyPSN( int appPSN[] )",
		"nMoveApp( int appPSN[], int selector, int flags )",
		"nLaunchURLDirect( String url, int flags, String preferredBrowsers[] )",

		"nFullPathToSpec( String fullPath, int vRefAndParID[], byte pName[] )",

		"nGetFinderInfo( int vRef, int parID, byte pName[], int finfo[] )",
		"nSetCreatorAndType( int vRef, int parID, byte pName[], int creatorAndType[] )",
		
		"nVerifyFile( int vRef, int parID, byte pName[] )",
		"nVerifyVolume( int vRef )",

		"nGetDiskFileFlags( int vRef, int parID, byte pName[], int flags[] )",
		"nSetDiskFileFlags( int vRef, int parID, byte pName[], int flagMask, int newFlags )",
		"nGetDFWriteFlagsMask( int vRef, int parID, byte pName[], int masks[] )",
		"nGetDFReadFlagsMask( int vRef, int parID, byte pName[], int masks[] )",

		"nGetDiskVolumeFlags( int vRef, int flags[] )",
		"nSetDiskVolumeFlags( int vRef, int flagMask, int newFlags )",
		"nGetDVReadFlagsMask( int vRef, int masks[] )",
		"nGetDVWriteFlagsMask( int vRef, int masks[] )",

		"nGetVolumes( int maxToReturn, int numRet[], int vRefs[] )",

		"nUpdateContainer( int vRef, int parID, byte pName[] )",

		"nGetFileCategory( int vRef, int parID, byte pName[], int cat[] )",

		"nGetForkSizes( int vRef, int parID, byte pName[], long len[] )",

		"nRenameFile( int vRef, int parID, byte pName[], byte pOutName[], String newName )",

		"nRenameVolume( int vRef, String newName )",

		"nGetVolumeCapacity( int vRef, long cap[] )",

		"nGetVolumeFreeSpace( int vRef, long space[] )",

		"nCreateAlias( int vRef, int parID, byte pName[], String newAlias, int creator, int flags )",

		"nGetVolumeName( int vRef, byte pName[] )",

		"nResolveAlias( int inVRef, int inParID, byte pInName[], int outVRefAndParID[], byte pOutName[], int flags )",

		"nSetColorCoding( int vRef, int parID, byte pName[], int newCoding )",

		"nSetVolumeColorCoding( int vRef, int newCoding )",

		"nGetVolumeFinderInfo( int vRef, int finfo[] )",

		"nGetVolumeIconSuite( int vRef, int selector, int pHSuite[] )",

		"nGetFileIconSuite( int vRef, int parID, byte pName[], int selector, int pHSuite[] )",

		"nGetFTACIconSuite( int vRef, int creator, int type, int selector, int pHSuite[] )",

		"nPlotIcon( int which, int width, int height,int hSuite, int xform, int align, int pData[] )",

		"nCreateVolumeAlias( int targetVRef, String newAlias, int creator, int flags )",

		"nIterateContents( int vRef, int parID, byte pName[], int dirIDArray[], int numRet[], byte buffer[], int numEntries, int flags )",

		"nIVC( int vRef, int dirIDArray[], int numRet[], byte buffer[], int numEntries, int flags )",

		"nGetContainer( int vRef, int parID, byte pName[], int pContParID[], byte pContName[] )",

		"nDisposeIconSuite( int hSuite, int flags )",


		"nGetAllMonitorInfo( int monitorInfo[], int maxToReturn, int numReturned[] )",

		"nGetMainMonitorInfo( int monitorInfo[] )",


		"nGetProcesses( int maxToReturn, int flags, int numRet[], int vRefs[], int parIDs[], byte pNames[], int psnLo[], int psnHi[], int proFlags[] )"
	};
*/

//============================================================================
//
//	This is the Think C 6.0 code to send a message to the DebugWindow server
//	(in other words, this is the code to DebugWindow.Lib that came with your
//	DebugWindow package).  I'm providing this code to document the AppleEvent
//	procedures necessary to communicate with DebugWindow; those of you who
//	need a specialized version, or need to port it to another environment
//	(such as Pascal or MacApp) should find everything that you need to know
//	right here.
//
//	If you implement this into a new environment, I'd appreciate it if you
//	could send me the new modules so that I can include them with the next
//	release of DebugWindow (with the proper credits going to you, of course!).
//
//
//	Here are the necessary steps to send a string to DebugWindow:
//
//		¥ create an AppleEvent for signature 'LdbW' with a type of 'misc/dmsg'
//		  (for a standard printf-type message), or 'misc/dmsh' for a hex dump.
//
//		¥ add a parameter of type 'keyDirectObject/typeChar' passing a pointer
//		  to the string to display and its length
//
//		¥ send it on its way
//
//
//	Modification history
//	--------------------
//
//    Date     Version				Changes
//	--------   -------      ---------------------------------
//	11-19-93	2.0			¥ Added the interface to the DebugHexDump
//							  routine in DebugWindow.
//
//							¥ Added the ability to bracket displays to the
//							  DebugWindow with a BeginDebug() and EndDebug()
//							  envelope.  If you are doing large numbers of
//							  Debug() calls, this can dramatically speed
//							  things up.  This does NOT add any benefit to
//							  the DebugHexDump() call, since all of the logic
//							  for that function is in DebugWindow itself.
//
//============================================================================


#include	<stdio.h>
#include	<stdarg.h>
#include	<string.h>
#include	<AppleEvents.h>
#include	"Debug.h"

#if defined(__osx__)
	#include <AEInteraction.h>
#endif

#define		MAX_STRING_SIZE		4096
#define		CACHE_SIZE			8192		// size of display cache to allocate



void __SendToDebugWindow ( char *stringToSend, short length );
void __FlushDebugWindowCache (void);
void DebugCommand ( char *buffer, short length );


short	__dbWinCount = 0;					// counts number of BeginDebug calls
char	*__dbCache  = nil;					// holds our local display cache
short	__cacheIndex = 0;					// tail pointer for display cache



void Debug ( char *format, ... )
{
	__SendToDebugWindow ( format, strlen( format ) );
}


/*
void Debug ( char *format, ... )
{
	va_list		argptr;
	long		len;
	OSErr		err;
	char		*tDebugString;

	tDebugString = NewPtr ( MAX_STRING_SIZE );
	if ( tDebugString ) {
		va_start ( argptr, format );
		len = (long)vsprintf ( tDebugString, format, argptr );
		va_end ( argptr );
		if ( len > 0 )
			__SendToDebugWindow ( tDebugString, len );
		DisposePtr ( tDebugString );
	}
}

*/


void BeginDebug ()
{
	++__dbWinCount;
}




void EndDebug ()
{
	--__dbWinCount;
	if ( __dbWinCount == 0 )
		__FlushDebugWindowCache();
}




void ClearDebugWindow ()
{
	DebugCommand ( "C", 1 );
}





void DebugTimestamp ()
{
	DebugCommand ( "T", 1 );
}






void __SendToDebugWindow ( char *stringToSend, short msgLength )
{
	if ( ! __dbCache )									// must be first time...
		__dbCache = NewPtr ( CACHE_SIZE );

	if ( __dbCache ) {
		if ( msgLength + __cacheIndex >= CACHE_SIZE )
			__FlushDebugWindowCache();
		memcpy ( __dbCache + __cacheIndex, stringToSend, msgLength );
		__cacheIndex += msgLength;
		if ( __dbWinCount == 0 )
			__FlushDebugWindowCache();
	}
}





void DebugCommand ( char *buffer, short length )
{
	AEAddressDesc	address;
	AppleEvent		appleEvent, reply;
	OSType			targetSig;

	targetSig = 'LdbW';
	if ( AECreateDesc ( typeApplSignature, (Ptr)&targetSig, 
						sizeof targetSig, &address ) == 0 ) {
		if ( AECreateAppleEvent ( 'misc', 'dmsc', &address, kAutoGenerateReturnID,
								   kAnyTransactionID, &appleEvent ) == 0 ) {
			if ( AEPutParamPtr ( &appleEvent, keyDirectObject, typeChar,
								 buffer, length ) == 0 ) {
				AESend ( &appleEvent, &reply, 
						 kAEWaitReply + kAENeverInteract,
						 kAENormalPriority, 
						 300, 								// up to 5 second wait..
						 nil, nil );
				AEDisposeDesc ( &reply );
			}
			AEDisposeDesc ( &appleEvent );
		}
		AEDisposeDesc ( &address );
	}
}



void DebugHexDump ( char *buffer, short length )
{
	AEAddressDesc	address;
	AppleEvent		appleEvent, reply;
	OSType			targetSig;

	targetSig = 'LdbW';
	if ( AECreateDesc ( typeApplSignature, (Ptr)&targetSig, 
						sizeof targetSig, &address ) == 0 ) {
		if ( AECreateAppleEvent ( 'misc', 'dmsh', &address, kAutoGenerateReturnID,
								   kAnyTransactionID, &appleEvent ) == 0 ) {
			if ( AEPutParamPtr ( &appleEvent, keyDirectObject, typeChar,
								 buffer, length ) == 0 ) {
				AESend ( &appleEvent, &reply, 
						 kAEWaitReply + kAENeverInteract,
						 kAENormalPriority, 
						 300, 								// up to 5 second wait..
						 nil, nil );
				AEDisposeDesc ( &reply );
			}
			AEDisposeDesc ( &appleEvent );
		}
		AEDisposeDesc ( &address );
	}
}





void __FlushDebugWindowCache ()
{
	AEAddressDesc	address;
	AppleEvent		appleEvent, reply;
	OSType			targetSig;

	if ( __dbCache ) {
		targetSig = 'LdbW';
		if ( AECreateDesc ( typeApplSignature, (Ptr)&targetSig, 
							sizeof targetSig, &address ) == 0 ) {
			if ( AECreateAppleEvent ( 'misc', 'dmsg', &address, kAutoGenerateReturnID,
									   kAnyTransactionID, &appleEvent ) == 0 ) {
				if ( AEPutParamPtr ( &appleEvent, keyDirectObject, typeChar,
									 __dbCache, __cacheIndex ) == 0 ) {
					AESend ( &appleEvent, &reply, 
							 kAEWaitReply + kAENeverInteract,
							 kAENormalPriority, 
							 300, 								// up to 5 second wait..
							 nil, nil );
					AEDisposeDesc ( &reply );
				}
				AEDisposeDesc ( &appleEvent );
			}
			AEDisposeDesc ( &address );
		}
	}
	__cacheIndex = 0;
}



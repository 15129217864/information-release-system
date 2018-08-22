/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "Debugger.h"

#if defined(_WIN32)
	#include "unicodeOn95.h"
	#ifndef UNICODE
		#include "w95trace.h"
	#endif
#endif

const	LPCTSTR	Debugger::lpNull = _TXL( "<null>" );

void Debugger::debug( long line, const LPCTSTR msg )
{
	debug1( line, ( msg == NULL ? lpNull : msg ), lpNull, lpNull, 0, 0, 0 );
}

void Debugger::debug( long line, const LPCTSTR msg, const LPCTSTR s1 )
{
	debug1( line, ( msg == NULL ? lpNull : msg ), ( s1 == NULL ? lpNull : s1 ), NULL, 0, 0, 0 );
}

void Debugger::debug( long line, const LPCTSTR msg, const CStr *cs1, const CStr *cs2 )
{
	debug1( line, ( msg == NULL ? lpNull : msg ), ( cs1 == NULL ? lpNull : cs1->getBuf() ),
						( cs2 == NULL ? lpNull : cs2->getBuf() ), 0, 0, 0 );
}

void Debugger::debug( long line, const LPCTSTR msg, const CStr *cs1, const CStr *cs2,
						long n1, long n2 )
{
	debug1( line, ( msg == NULL ? lpNull : msg ), ( cs1 == NULL ? lpNull : cs1->getBuf() ),
						( cs2 == NULL ? lpNull : cs2->getBuf() ), n1, n2, 0 );
}

void Debugger::debug( long line, const LPCTSTR msg, const CStr *cs1, const CStr *cs2,
						long n1, long n2, long n3 )
{
	debug1( line, ( msg == NULL ? lpNull : msg ), ( cs1 == NULL ? lpNull : cs1->getBuf() ),
						( cs2 == NULL ? lpNull : cs2->getBuf() ), n1, n2, n3 );
}

#if defined(_WIN32)

	#ifndef USING_UNICODE_ON_W95_FOR_TESTING_ONLY

		void Debugger::debug1( long line, const LPCTSTR msg, const LPCTSTR s1, const LPCTSTR s2,
								long n1, long n2, long n3 )
		{
			TCHAR		szBuf[ 2048 ], tempMsg[ 128 ], tempS1[ 128 ], tempS2[ 128 ];

			if ( ( lstrlen( msg ) + lstrlen( s1 ) + lstrlen( s2 ) ) > 2000 ) {
				lstrcpyn( tempMsg, msg, 127 );
				lstrcpyn( tempS1, s1, 127 );
				lstrcpyn( tempS2, s2, 127 );

				tempMsg[ 127 ] = 0;
				tempS1[ 127 ] = 0;
				tempS2[ 127 ] = 0;

				wsprintf( szBuf, _TXL( "%lu: %s %s %s 0x%lx 0x%lx 0x%lx 0x%lx\n" ), line, tempMsg,
							tempS1, tempS2, n1, n2, n3, GetLastError() );
			}
			else
				wsprintf( szBuf, _TXL( "%lu: %s %s %s 0x%lx 0x%lx 0x%lx 0x%lx\n" ), line, msg,
							s1, s2, n1, n2, n3, GetLastError() );

		#if defined(UNICODE)
			OutputDebugStringW( &szBuf[ 0 ] );
		#else
			OutputDebugString( &szBuf[ 0 ] );
		#endif
		}

	#else

		void OutputDebugStringW95( char *lpOutputString );

		void Debugger::debug1( long line, const LPCTSTR msg, const LPCTSTR s1, const LPCTSTR s2,
								long n1, long n2, long n3 )
		{
			CStrA		tMsg( msg ), tS1( s1 ), tS2( s2 );
			char		szBuf[ 2048 ];

			wsprintfA( szBuf, "%lu: %s %s %s 0x%lx 0x%lx 0x%lx 0x%lx\n", line, tMsg.getBuf(),
							tS1.getBuf(), tS2.getBuf(), n1, n2, n3, GetLastError() );
			OutputDebugStringW95( szBuf );
		}

		//	modified from w95trace.cpp
		void OutputDebugStringW95( char *lpOutputString)
		{
			HANDLE heventDBWIN;
			HANDLE heventData;
			HANDLE hSharedFile;
			LPSTR lpszSharedMem;

			heventDBWIN = OpenEventA(EVENT_MODIFY_STATE, FALSE, "DBWIN_BUFFER_READY");
			if ( !heventDBWIN )
				return;            

			heventData = OpenEventA(EVENT_MODIFY_STATE, FALSE, "DBWIN_DATA_READY");
			hSharedFile = CreateFileMappingA((HANDLE)-1, NULL, PAGE_READWRITE, 0, 4096, "DBWIN_BUFFER");
			lpszSharedMem = (LPSTR) MapViewOfFile( hSharedFile, FILE_MAP_WRITE, 0, 0, 512);
			WaitForSingleObject(heventDBWIN, INFINITE);

			*((LPDWORD)lpszSharedMem) = 0;
			lstrcpyA( lpszSharedMem + sizeof(DWORD), lpOutputString );

			SetEvent(heventData);

			CloseHandle(hSharedFile);
			CloseHandle(heventData);
			CloseHandle(heventDBWIN);

			return;
		}

	#endif

#elif defined(__linux__)

	void Debugger::debug1( long line, const LPCTSTR msg, const LPCTSTR s1, const LPCTSTR s2,
							long n1, long n2, long n3 )
	{
		printf( _TXL( "%lu: %s %s %s 0x%lx 0x%lx 0x%lx\n" ), line, msg, s1, s2, n1, n2, n3 );
	}

#elif defined(macintosh) || defined(__osx__)

	#include <string.h>
	#include <stdlib.h>
	#include <stdarg.h>
	#include <stdio.h>
	#include "Debug.h"

	void Debugger::debug1( long line, const LPCTSTR msg, const LPCTSTR s1, const LPCTSTR s2,
							long n1, long n2, long n3 )
	{
		char		szBuf[ 2048 ], tempMsg[ 128 ], tempS1[ 128 ], tempS2[ 128 ];

		if ( ( strlen( msg ) + strlen( s1 ) + strlen( s2 ) ) > 2000 ) {
			strncpy( tempMsg, msg, 127 );
			strncpy( tempS1, s1, 127 );
			strncpy( tempS2, s2, 127 );

			tempMsg[ 127 ] = 0;
			tempS1[ 127 ] = 0;
			tempS2[ 127 ] = 0;

			sprintf( szBuf, _TXL( "%lu: %s %s %s 0x%lx 0x%lx 0x%lx\n" ),
						line, tempMsg, tempS1, tempS2, n1, n2, n3 );
		}
		else
			sprintf( szBuf, _TXL( "%lu: %s %s %s 0x%lx 0x%lx 0x%lx\n" ),
						line, msg, s1, s2, n1, n2, n3 );

		Debug( &szBuf[ 0 ] );
	}

#endif

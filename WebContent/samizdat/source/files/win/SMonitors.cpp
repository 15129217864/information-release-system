/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "SMonitors.h"
#include "Debugger.h"

//	multimon.h is from MS, and is included with the latest Platform SDK
//	With this file, "the new multimonitor APIs will act like only one display is present
//	on a Win32 OS without multimonitor APIs."
//	If you don't have this file, download the platform SDK, or remove 'enumProc', and
//	replace 'iGetAllMonitorInfo' with the version at the end of this file.

#define	COMPILE_MULTIMON_STUBS
#include "e:\psdk\include\multimon.h"

typedef	struct	{
	long		curMonitor, maxMonitor, *monitorData;
} MonitorCallbackInfo;

ErrCode SMonitors::iGetAllMonitorInfo( long *monitorInfoP, long maxToReturn, long *numReturnedP )
{
	MonitorCallbackInfo		info;
	HDC						hDC;

	info.curMonitor = 0;
	info.maxMonitor = maxToReturn;
	info.monitorData = monitorInfoP;

	hDC = GetDC( NULL );
	EnumDisplayMonitors( hDC, NULL, (MONITORENUMPROC) enumProc, (DWORD) &info );
	ReleaseDC( NULL, hDC );

	*numReturnedP = info.curMonitor;

	return kErrNoErr;
}

ErrCode SMonitors::iGetMainMonitorInfo( long *monitorInfoP )
{
	HWND			hDesk;
	HDC				hDC;
	RECT			rcWorkarea, rcBounds;
	long			theErr, screenWidth, screenHeight, screenDepth;
	BOOL			bRet;

	theErr = kErrNoErr;

	bRet = SystemParametersInfo( SPI_GETWORKAREA, 0, &rcWorkarea, 0 );
	if ( !bRet ) {
		theErr = kErrSystemParametersInfo;
		goto bail;
	}

	screenWidth = GetSystemMetrics( SM_CXSCREEN );
	screenHeight = GetSystemMetrics( SM_CYSCREEN );
	SetRect( &rcBounds, 0, 0, screenWidth, screenHeight );
	
	hDesk = GetDesktopWindow();
	hDC = GetDC( hDesk );
	screenDepth = GetDeviceCaps( hDC, BITSPIXEL );
	ReleaseDC( hDesk, hDC );
	
	stuffMonitorInfo( &rcBounds, &rcWorkarea, screenDepth, TRUE, 0, monitorInfoP );

bail:

	return theErr;
}

BOOL CALLBACK SMonitors::enumProc( HANDLE hmn, HDC hdcMonitor, LPRECT lprcMonitor, DWORD dwData )
{
	MonitorCallbackInfo		*infoP;
	MONITORINFO				monInfo;
	HMONITOR				hMonitor;
	long					pixDepth, refNum, *pInts;
	BOOL					bIsMain;

	hMonitor = (HMONITOR) hmn;

	infoP = (MonitorCallbackInfo*) dwData;
	if ( infoP->curMonitor >= infoP->maxMonitor )
		return FALSE;

	monInfo.cbSize = sizeof(MONITORINFO);
	if ( !GetMonitorInfo( hMonitor, &monInfo ) )		
		return FALSE;

	pixDepth = GetDeviceCaps( hdcMonitor, BITSPIXEL );
	refNum = 0;
	bIsMain = ( ( monInfo.dwFlags & MONITORINFOF_PRIMARY ) != 0 );
	pInts = &( infoP->monitorData[ infoP->curMonitor * SMonitors::kMonitorInfoNumInts ] );

	SMonitors::stuffMonitorInfo( &monInfo.rcMonitor, &monInfo.rcWork, pixDepth, bIsMain, refNum, pInts );

	++infoP->curMonitor;

	return TRUE;
}

void SMonitors::stuffMonitorInfo( LPRECT bRectP, LPRECT wRectP, long pixDepth, BOOL bIsMain, long refNum, long *dataP )
{
	dataP[ kOffsBoundsTop ] = bRectP->top;
	dataP[ kOffsBoundsLeft ] = bRectP->left;
	dataP[ kOffsBoumdsBottom ] = bRectP->bottom;
	dataP[ kOffsBoundsRight ] = bRectP->right;

	dataP[ kOffsWorkareaTop ] = wRectP->top;
	dataP[ kOffsWorkareaLeft ] = wRectP->left;
	dataP[ kOffsWorkareaBottom ] = wRectP->bottom;
	dataP[ kOffsWorkareaRight ] = wRectP->right;

	dataP[ kOffsDepth ] = pixDepth;

	dataP[ kOffsIsMainMonitor ] = (long) bIsMain;

	dataP[ kOffsRefNum ] = refNum;
}

#if FALSE
//	see notes at top of file
long MonitorsSingle::iGetAllMonitorInfo( long *monitorInfoP, long maxToReturn, long *numReturnedP )
{
	long		theErr;
	
	theErr = iGetMainMonitorInfo( monitorInfoP );
	
	if ( theErr == kErrNoErr )
		*numReturnedP = 1;
	else
		*numReturnedP = 0;
		
	return theErr;
}
#endif



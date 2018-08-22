/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CIconUtils.h"
#include "Debugger.h"

/*
	- draw the indicated icon of the given icon list into a dib section
	- transfer each pixel of the icon into each element of the bitmapP array in Java format
	The icon is drawn with the indicated style using ImageList_Draw
	if it has a mask, the mask is applied
	bitmapP is assumed to be able to hold at least (width * height) longs
*/

ErrCode CIconUtils::slamImage( HIMAGELIST himl, long iconID, unsigned long style, long width, long height, long *bitmapP )
{
	long				theErr, *lpBits, i;
	HDC					hScreenDC, hMemDC;
	HBITMAP				hBitmap, hOldBitmap;
	void				*dibSectionBits;
	RECT				rect;
	BOOL				bRet;

	theErr = kErrNoErr;
	hOldBitmap = NULL;

	hScreenDC = GetDC( NULL );

	hMemDC = CreateCompatibleDC( hScreenDC );
	hBitmap = buildDIBSection( hScreenDC, width, height, &dibSectionBits );

	ReleaseDC( NULL, hScreenDC );

	if ( hMemDC == NULL ) {
		theErr = kErrCreateCompatibleDC;
		goto bail;
	}
	if ( hBitmap == NULL ) {
		theErr = kErrBuildDIBSection;
		goto bail;
	}

	hOldBitmap = (HBITMAP) SelectObject( hMemDC, hBitmap );

	SetRect( &rect, 0, 0, width, height );
	FillRect( hMemDC, &rect, (HBRUSH) GetStockObject( WHITE_BRUSH ) );

		//	draw the icon using the given style

	bRet = ImageList_Draw( himl, iconID, hMemDC, 0, 0, style );
	if ( !bRet ) {
		theErr = kErrImageListDraw;
		goto bail;
	}

		//	copy the RGB values of the icon into the bitmapP array

	memcpy( bitmapP, dibSectionBits, sizeof(long) * width * height );

		//	draw the icon's mask into the DIB section

	bRet = ImageList_Draw( himl, iconID, hMemDC, 0, 0, ILD_MASK );
	if ( !bRet ) {

			//	there is no mask; make each pixel fully opaque by setting the alpha channel to 0xFF

		for ( i = width * height - 1; i >= 0; i-- )
			bitmapP[ i ] |= 0xFF000000;

	}
	else {

			//	if a long of the mask is 0x??FFFFFF, set the corresponding member of the bitmapP array to 0x0,
			//	making it transparent
			//	otherwise, make it fully opaque by setting the alpha channel to 0xFF

		for ( lpBits = (long*) dibSectionBits, i = width * height - 1; i >= 0; i-- )
			if ( ( lpBits[ i ] & 0x00FFFFFF ) == 0x00FFFFFF )
				bitmapP[ i ] = 0;
			else
				bitmapP[ i ] |= 0xFF000000;

	}

bail:

	if ( hOldBitmap != NULL && hMemDC != NULL )
		SelectObject( hMemDC, hOldBitmap );

	if ( hMemDC != NULL )
		DeleteDC( hMemDC );

	if ( hBitmap != NULL )
		DeleteObject( hBitmap );

	return theErr;
}

//	create a 32-bit top-down (i.e., negative height) DIB section.

HBITMAP CIconUtils::buildDIBSection( HDC aDC, long width, long height, void **lpBytes )
{
	BITMAPINFO		bmi;
	
	bmi.bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
	bmi.bmiHeader.biPlanes = 1;
	bmi.bmiHeader.biBitCount = 32;
	bmi.bmiHeader.biCompression = BI_RGB;
	bmi.bmiHeader.biSizeImage = 0;
	bmi.bmiHeader.biClrUsed = 0;
	bmi.bmiHeader.biClrImportant = 0;
	bmi.bmiHeader.biWidth = width;
	bmi.bmiHeader.biHeight = -height;

	return CreateDIBSection( aDC, &bmi, DIB_RGB_COLORS, (void FAR *FAR *) lpBytes, NULL, 0 );
}



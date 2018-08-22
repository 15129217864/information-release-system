/***********************************************************************************************

		This file can be used to test the S-files portion of the Windows native code.

		If you compile this project as is, none of the portions of the native code
		which rely on Java support (i.e., JNI headers) will be included.

		You can't use this project to test AppUtilsMSVM.cpp (unless you wanted to write
		a JVM emulator or similar...), but you can test most of the other files.

		Modify the DoIt() or DoButton() routines, as illustrated below.

***********************************************************************************************/



#include <windows.h>   // required for all Windows applications
#include "resource.h"  // Windows resource IDs
#include "w95trace.h"
#include <stdio.h>
#include <commctrl.h>
#include "XToolkit.h"
#include "Debugger.h"

BOOL InitApplication(HANDLE);
BOOL InitInstance(HANDLE, int);
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
LRESULT CALLBACK About  (HWND, UINT, WPARAM, LPARAM);

HINSTANCE	hInst;          // current instance
HWND		hMainWnd;
HWND		hButton, hText1, hText2, hText3;

char szAppName[] = "Generic";   // The name of this application
char szTitle[]   = "Generic Sample Application"; // The title bar text

void DoIt( HWND hWnd );
void DoButton( HWND hWnd );
void tryit( void );
static void charToNulls( LPSTR s, char c );
BOOL DoDialog( HWND hWnd, LPSTR fileName );


/******************************************************

	DoIt() is called when you select Edit | Undo
	This is one place to put your test code.

******************************************************/

void DoIt( HWND hWnd )
{
	try {
		tryit();
	}
	catch ( LPCTSTR e ) {
		OutputDebugString( e );
	}
}

void tryit()
{
}


/******************************************************

	DoButton() is called when you click the button
	This is the other place to put your test code.

	As an example, this routine tests SAppFinder::iNativeFindAppsByName()

******************************************************/

#include "SAppFinder.h"

void DoButton( HWND hWnd )
{
	CStringVector	vec( 20 );
	CStr			*name, *foundPath, *startDirectory = NULL;
	char			buf[ 1001 ];
	ErrCode			theErr;
	long			len, i, level;

	SendMessage( hText1, WM_GETTEXT, 1000, (long) buf );
	name = new CStr( buf );
	
	SendMessage( hText2, WM_GETTEXT, 1000, (long) buf );
	startDirectory = new CStr( buf );
	
	SendMessage( hText3, WM_GETTEXT, 1000, (long) buf );
	level = ( buf[ 0 ] == '0' ) ? 0 : 1;

	Debugger::debug( 0, "**************************************************************" );
	Debugger::debug( 0, "looking for:", name, startDirectory, level, 0 );
	theErr = SAppFinder::iNativeFindAppsByName( name, startDirectory, 20, level, &vec );
	Debugger::debug( theErr, "<-- error" );

	len = vec.getNumStrings();
	Debugger::debug( len, "<-- number of strings" );
	for ( i = 0; i < len; i++ ) {
		foundPath = vec.getString( i );
		Debugger::debug( i, "    found it=", name, foundPath, theErr, 0 );
	}
}











/*
You don't need to modify the rest of this file.
This was modified from the MS 'Generic' sample app.
*/

static void charToNulls( LPSTR s, char c )
{
	long		i, nLong;

	nLong = strlen( s ) + 1;

	for ( i = 0; i < nLong; i++ ) {
		if ( *s == c )
			*s = 0;
		s++;
	}
}

BOOL DoDialog( HWND hWnd, LPSTR fileName )
{
	char			cOpenFilter[] = "All Files|*.*|";
	OPENFILENAME	ofn;
	BOOL			bRet;
	DWORD			err;

	charToNulls( cOpenFilter, '|' );
	*fileName = 0;

	ofn.lStructSize = sizeof(OPENFILENAME);
	ofn.hwndOwner = hWnd;
	ofn.hInstance = hInst;
	ofn.lpstrFilter = cOpenFilter;
	ofn.lpstrCustomFilter = NULL;
	ofn.nMaxCustFilter = 0;
	ofn.nFilterIndex = 1;
	ofn.lpstrFile = fileName;
	ofn.nMaxFile = 256;
	ofn.lpstrFileTitle = NULL;
	ofn.nMaxFileTitle = 0;
	ofn.lpstrInitialDir = NULL;
	ofn.lpstrTitle = NULL;
	ofn.Flags = OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST | OFN_HIDEREADONLY;
	ofn.nFileOffset = 0;
	ofn.nFileExtension = 0;
	ofn.lpstrDefExt = NULL;
	ofn.lCustData = NULL;
	ofn.lpfnHook = NULL;
	ofn.lpTemplateName = NULL;

	bRet = GetOpenFileName( &ofn );
	err = CommDlgExtendedError();

	return bRet;
}

int CALLBACK WinMain(
        HINSTANCE hInstance,
        HINSTANCE hPrevInstance,
        LPSTR lpCmdLine,
        int nCmdShow)
{

        MSG msg;
        HANDLE hAccelTable;

        if (!hPrevInstance) {       // Other instances of app running?
                        if (!InitApplication(hInstance)) { // Initialize shared things
                        return (FALSE);     // Exits if unable to initialize
                }
        }

        /* Perform initializations that apply to a specific instance */

        if (!InitInstance(hInstance, nCmdShow)) {
                return (FALSE);
        }

        hAccelTable = LoadAccelerators (hInstance, MAKEINTRESOURCE(IDR_GENERIC));

        /* Acquire and dispatch messages until a WM_QUIT message is received. */

        while (GetMessage(&msg, // message structure
           NULL,   // handle of window receiving the message
           0,      // lowest message to examine
           0))     // highest message to examine
        {
                if (!TranslateAccelerator (msg.hwnd, hAccelTable, &msg)) {
                        TranslateMessage(&msg);// Translates virtual key codes
                        DispatchMessage(&msg); // Dispatches message to window
                }
        }


        return (msg.wParam); // Returns the value from PostQuitMessage

        lpCmdLine; // This will prevent 'unused formal parameter' warnings
}


/****************************************************************************

        FUNCTION: InitApplication(HINSTANCE)

        PURPOSE: Initializes window data and registers window class

        COMMENTS:

                This function is called at initialization time only if no other
                instances of the application are running.  This function performs
                initialization tasks that can be done once for any number of running
                instances.

                In this case, we initialize a window class by filling out a data
                structure of type WNDCLASS and calling the Windows RegisterClass()
                function.  Since all instances of this application use the same window
                class, we only need to do this when the first instance is initialized.


****************************************************************************/

BOOL InitApplication(HINSTANCE hInstance)
{
        WNDCLASS  wc;

        // Fill in window class structure with parameters that describe the
        // main window.

        wc.style         = CS_HREDRAW | CS_VREDRAW;// Class style(s).
        wc.lpfnWndProc   = (WNDPROC)WndProc;       // Window Procedure
        wc.cbClsExtra    = 0;                      // No per-class extra data.
        wc.cbWndExtra    = 0;                      // No per-window extra data.
        wc.hInstance     = hInstance;              // Owner of this class
        wc.hIcon         = LoadIcon (hInstance, MAKEINTRESOURCE(IDI_APP)); // Icon name from .RC
        wc.hCursor       = LoadCursor(NULL, IDC_ARROW);// Cursor
        wc.hbrBackground = (HBRUSH)(COLOR_WINDOW+1);// Default color
        wc.lpszMenuName  = MAKEINTRESOURCE(IDR_GENERIC); // Menu from .RC
        wc.lpszClassName = szAppName;              // Name to register as

        // Register the window class and return success/failure code.
        return (RegisterClass(&wc));
}


/****************************************************************************

        FUNCTION:  InitInstance(HINSTANCE, int)

        PURPOSE:  Saves instance handle and creates main window

        COMMENTS:

                This function is called at initialization time for every instance of
                this application.  This function performs initialization tasks that
                cannot be shared by multiple instances.

                In this case, we save the instance handle in a static variable and
                create and display the main program window.

****************************************************************************/

BOOL InitInstance(
        HINSTANCE          hInstance,
        int             nCmdShow)
{
        HWND            hWnd; // Main window handle.

        // Save the instance handle in static variable, which will be used in
        // many subsequence calls from this application to Windows.

        hInst = hInstance; // Store instance handle in our global variable

        // Create a main window for this application instance.

        hMainWnd = hWnd = CreateWindow(
                szAppName,           // See RegisterClass() call.
                szTitle,             // Text for window title bar.
                WS_OVERLAPPEDWINDOW,// Window style.
                CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, // Use default positioning
                NULL,                // Overlapped windows have no parent.
                NULL,                // Use the window class menu.
                hInstance,           // This instance owns this window.
                NULL                 // We don't use any data in our WM_CREATE
        );

        // If window could not be created, return "failure"
        if (!hWnd) {
                return (FALSE);
        }


        // Make the window visible; update its client area; and return "success"
        ShowWindow(hWnd, nCmdShow); // Show the window
        UpdateWindow(hWnd);         // Sends WM_PAINT message


        return (TRUE);              // We succeeded...

}

/****************************************************************************

        FUNCTION: WndProc(HWND, UINT, WPARAM, LPARAM)

        PURPOSE:  Processes messages

        MESSAGES:

        WM_COMMAND    - application menu (About dialog box)
        WM_DESTROY    - destroy window

        COMMENTS:

        To process the IDM_ABOUT message, call MakeProcInstance() to get the
        current instance address of the About() function.  Then call Dialog
        box which will create the box according to the information in your
        generic.rc file and turn control over to the About() function.  When
        it returns, free the intance address.

****************************************************************************/

LRESULT CALLBACK WndProc(
                HWND hWnd,         // window handle
                UINT message,      // type of message
                WPARAM uParam,     // additional information
                LPARAM lParam)     // additional information
{
    PAINTSTRUCT      ps;
	HDC				hDC;
        FARPROC lpProcAbout;  // pointer to the "About" function
        int wmId, wmEvent;

        switch (message) {

		case WM_CREATE:
			hButton = CreateWindow( "BUTTON", "Find App",
									WS_CHILD | WS_VISIBLE | BS_DEFPUSHBUTTON,
									10, 10, 100, 40, hWnd, (HMENU) 101, (HANDLE) hInst, (void*) NULL );
			hText1 = CreateWindow( "EDIT", "name",
									WS_CHILD | WS_VISIBLE | WS_BORDER,
									150, 10, 400, 25, hWnd, (HMENU) 101, (HANDLE) hInst, (void*) NULL );
			hText2 = CreateWindow( "EDIT", "start",
									WS_CHILD | WS_VISIBLE | WS_BORDER,
									150, 100, 400, 25, hWnd, (HMENU) 101, (HANDLE) hInst, (void*) NULL );
			hText3 = CreateWindow( "EDIT", "level",
									WS_CHILD | WS_VISIBLE | WS_BORDER,
									150, 200, 400, 25, hWnd, (HMENU) 101, (HANDLE) hInst, (void*) NULL );
		break;

        case WM_PAINT:
			hDC = BeginPaint( hWnd, &ps );
			EndPaint( hWnd, &ps );
        break;

		case WM_COMMAND:  // message: command from application menu

            wmId    = LOWORD(uParam);
            wmEvent = HIWORD(uParam);

            switch (wmId) {
				case 101:
					if ( wmEvent == BN_CLICKED )
						DoButton( hWnd );
				break;

				case IDM_EXIT:
                        DestroyWindow (hWnd);
                        break;

                case IDM_HELPCONTENTS:
                        if (!WinHelp (hWnd, "GENERIC.HLP", HELP_KEY,(DWORD)(LPSTR)"CONTENTS")) {
                                MessageBox (GetFocus(),
                                        "Unable to activate help",
                                        szAppName, MB_SYSTEMMODAL|MB_OK|MB_ICONHAND);
                        }
                        break;

                case IDM_HELPSEARCH:
                        if (!WinHelp(hWnd, "GENERIC.HLP", HELP_PARTIALKEY, (DWORD)(LPSTR)"")) {
                                MessageBox (GetFocus(),
                                        "Unable to activate help",
                                        szAppName, MB_SYSTEMMODAL|MB_OK|MB_ICONHAND);
                        }
                        break;

                case IDM_HELPHELP:
                        if(!WinHelp(hWnd, (LPSTR)NULL, HELP_HELPONHELP, 0)) {
                                MessageBox (GetFocus(),
                                        "Unable to activate help",
                                        szAppName, MB_SYSTEMMODAL|MB_OK|MB_ICONHAND);
                        }
                        break;

                // Here are all the other possible menu options,
                // all of these are currently disabled:
                case IDM_NEW:
                case IDM_OPEN:
                case IDM_SAVE:
                case IDM_SAVEAS:
                case IDM_UNDO:
					DoIt( hWnd );
                case IDM_CUT:
                case IDM_COPY:
                case IDM_PASTE:
                case IDM_LINK:
                case IDM_LINKS:

                default:
                        return (DefWindowProc(hWnd, message, uParam, lParam));
            }
            break;

               case WM_DESTROY:  // message: window being destroyed
                        PostQuitMessage(0);
                        break;

                default:          // Passes it on if unproccessed
                        return (DefWindowProc(hWnd, message, uParam, lParam));
        }
        return (0);
}

/****************************************************************************

        FUNCTION: CenterWindow (HWND, HWND)

        PURPOSE:  Center one window over another

        COMMENTS:

        Dialog boxes take on the screen position that they were designed at,
        which is not always appropriate. Centering the dialog over a particular
        window usually results in a better position.

****************************************************************************/

BOOL CenterWindow (HWND hwndChild, HWND hwndParent)
{
        RECT    rChild, rParent;
        int     wChild, hChild, wParent, hParent;
        int     wScreen, hScreen, xNew, yNew;
        HDC     hdc;

        // Get the Height and Width of the child window
        GetWindowRect (hwndChild, &rChild);
        wChild = rChild.right - rChild.left;
        hChild = rChild.bottom - rChild.top;

        // Get the Height and Width of the parent window
        GetWindowRect (hwndParent, &rParent);
        wParent = rParent.right - rParent.left;
        hParent = rParent.bottom - rParent.top;

        // Get the display limits
        hdc = GetDC (hwndChild);
        wScreen = GetDeviceCaps (hdc, HORZRES);
        hScreen = GetDeviceCaps (hdc, VERTRES);
        ReleaseDC (hwndChild, hdc);

        // Calculate new X position, then adjust for screen
        xNew = rParent.left + ((wParent - wChild) /2);
        if (xNew < 0) {
                xNew = 0;
        } else if ((xNew+wChild) > wScreen) {
                xNew = wScreen - wChild;
        }

        // Calculate new Y position, then adjust for screen
        yNew = rParent.top  + ((hParent - hChild) /2);
        if (yNew < 0) {
                yNew = 0;
        } else if ((yNew+hChild) > hScreen) {
                yNew = hScreen - hChild;
        }

        // Set it, and return
        return SetWindowPos (hwndChild, NULL,
                xNew, yNew, 0, 0, SWP_NOSIZE | SWP_NOZORDER);
}


/*
    declarations for Win95 tracing facility
*/

//#ifdef _DEBUG

/*
9/16/98:
CK modified to accept only one argument, not variable number of args
*/

#ifndef __TRACEW95__
#define __TRACEW95__

// redefine all the MFC macros to point to us

#undef  TRACE
#define TRACE   OutputDebugStringW95

#undef  TRACE0
#define TRACE0   OutputDebugStringW95

#undef  TRACE1
#define TRACE1   OutputDebugStringW95

#undef  TRACE2
#define TRACE2   OutputDebugStringW95

#undef  TRACE3
#define TRACE3   OutputDebugStringW95

// redefine OutputDebugString so that it works with 
// API calls, but only on 95
#undef OutputDebugString
#define OutputDebugString   OutputDebugStringW95


// function declarations
void OutputDebugStringW95( LPCTSTR lpOutputString );

#endif  //__TRACEW95__

//#endif  // _DEBUG

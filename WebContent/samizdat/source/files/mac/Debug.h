
#ifndef _DEBUGWINDOW_H_
#define _DEBUGWINDOW_H_

void BeginDebug ( void );
void EndDebug ( void );
void Debug ( char *format, ... );
void DebugHexDump ( char *buffer, short length );
void ClearDebugWindow (void);
void DebugTimestamp ( void );
void __FlushDebugWindowCache( void );

#endif

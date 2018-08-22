/****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include "CLinkFileUtils.h"
#include "Debugger.h"
#include <shlguid.h>
#include <shlobj.h>

HRESULT CLinkFileUtils::createLink( const CStr *csTargetFile, const CStr *csLinkFile, const CStr *csDesc )
{
	HRESULT			hres, oleInitHR; 
	IShellLink		*pShellLink = NULL;
	IPersistFile	*pPersistFile = NULL;

	oleInitHR = CoInitialize( NULL );
	if ( FAILED(oleInitHR) ) {
		Debugger::debug( __LINE__, _TXL( "CL:cl: couldn't init OLE" ), NULL, NULL, oleInitHR, 0 );
		return oleInitHR;
	}

	hres = CoCreateInstance( CLSID_ShellLink, NULL, CLSCTX_ALL, IID_IShellLink, (void**) &pShellLink ); //CLSCTX_INPROC_SERVER
	if ( !SUCCEEDED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "CL:cci" ), NULL, NULL, hres, 0 );
		goto bail;
	}
	
	hres = pShellLink->SetPath( csTargetFile->getBuf() ); 
	if ( !SUCCEEDED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "CL:sp" ), NULL, NULL, hres, 0 );
		goto bail;
	}

	pShellLink->SetDescription( csDesc->getBuf() ); 

	hres = pShellLink->QueryInterface( IID_IPersistFile, (void**) &pPersistFile ); 
	if ( !SUCCEEDED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "CL:ipf=" ), NULL, NULL, hres, 0 );
		goto bail;
	}

#ifndef UNICODE
	CStrW		*csLinkFileW;
	csLinkFileW = new CStrW( csLinkFile->getBuf() );
	hres = pPersistFile->Save( csLinkFileW->getBuf(), TRUE );
	delete csLinkFileW;
#else
	hres = pPersistFile->Save( csLinkFile->getBuf(), TRUE ); 
#endif

bail:

	if ( pPersistFile != NULL )
		pPersistFile->Release(); 

	if ( pShellLink != NULL )
		pShellLink->Release(); 

	CoUninitialize();

	return hres; 
}

HRESULT CLinkFileUtils::resolveLink( HWND hwnd, const CStr *csLinkFile, CStr *csTargetFile, BOOL bUseUI )
{
	HRESULT				hres; 
	IShellLink			*pShellLink = NULL; 
	IPersistFile		*pPersistFile = NULL; 
	WIN32_FIND_DATA		wfd; 

	csTargetFile->ensureCharCapacity( CStr::kMaxPath );

	hres = CoInitialize( NULL );
	if ( FAILED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "RL: couldn't init OLE" ), NULL, NULL, hres, 0 );
		return hres;
	}

	hres = CoCreateInstance( CLSID_ShellLink, NULL, CLSCTX_INPROC_SERVER, IID_IShellLink, (void**) &pShellLink ); 
	if ( !SUCCEEDED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "RL.cci" ), NULL, NULL, hres, 0 );
		goto bail;
	}
	
	hres = pShellLink->QueryInterface( IID_IPersistFile, (void**) &pPersistFile ); 
	if ( !SUCCEEDED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "RL.qi" ), NULL, NULL, hres, 0 );
		goto bail;
	}
	
#ifndef UNICODE
	CStrW		*csLinkFileW;
	csLinkFileW = new CStrW( csLinkFile->getBuf() );
	hres = pPersistFile->Load( csLinkFileW->getBuf(), STGM_READ ); 
	delete csLinkFileW;
#else
	hres = pPersistFile->Load( csLinkFile->getBuf(), STGM_READ ); 
#endif

	if ( !SUCCEEDED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "RL.load" ), NULL, NULL, hres, 0 );
		goto bail;
	}
	
	if ( bUseUI )
		hres = pShellLink->Resolve( hwnd, SLR_ANY_MATCH | SLR_UPDATE );
	else
		hres = pShellLink->Resolve( hwnd, SLR_NO_UI | SLR_UPDATE );

	if ( !SUCCEEDED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "RL.rslv" ), NULL, NULL, hres, 0 );
		goto bail;
	}
	
	hres = pShellLink->GetPath( csTargetFile->getBuf(), csTargetFile->getByteCapacity(),
								(WIN32_FIND_DATA *) &wfd, SLGP_SHORTPATH ); 
	if ( !SUCCEEDED(hres) ) {
		Debugger::debug( __LINE__, _TXL( "RL.gp" ), NULL, NULL, hres, 0 );
		goto bail;
	}

bail:

	if ( pPersistFile != NULL )
		pPersistFile->Release(); 

	if ( pShellLink != NULL )
		pShellLink->Release(); 

	CoUninitialize();

	return hres; 
}


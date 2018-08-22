 /****************************************************

	JConfig Source Code Distribution
	version: 2.2.0      written: 08/23/2002 12:25 PM PDT

	Unless otherwise noted, all contents
	Copyright (c) 1997-2002 Samizdat Productions.
	All Rights Reserved.
	All contents are subject to the terms of the
	JConfig Source Code License Agreement

****************************************************/

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <dev/disk.h>
#include <errno.h>
#include <paths.h>
#include <sys/param.h>
#include <IOKit/IOKitLib.h>
#include <IOKit/IOBSD.h>
#include <IOKit/storage/IOMedia.h>
#include <IOKit/storage/IOCDMedia.h>
#include <IOKit/storage/IOCDTypes.h>
#include <CoreFoundation/CoreFoundation.h>
#include <CoreFoundation/CFBundle.h>

#define IOOBJECTRELEASE(x)		do { if ((x))	IOObjectRelease((x)); (x) = NULL; } while ( FALSE );

OSStatus vRefNumToBSDName( short vRefNum, char *buf )
{
	GetVolParmsInfoBuffer	vol_parms;
	OSStatus				err;
	HIOParam				opb;

	opb.ioBuffer = (Ptr) &vol_parms;
	opb.ioReqCount = sizeof(vol_parms);
	opb.ioVRefNum = vRefNum;
	opb.ioNamePtr = NULL;
	err = PBHGetVolParmsSync( (HParmBlkPtr) &opb );

	if ( vol_parms.vMVersion < 4 )
		return paramErr;

	strcpy( buf, (char*) vol_parms.vMDeviceID );
	
	return err;
}

io_object_t		IOKitObjectFindParentOfClass(io_object_t inService, io_name_t inClassName)
{
	io_object_t			rval 		= NULL;
	io_iterator_t		iter	 	= NULL;
	io_object_t			service 	= NULL;
	kern_return_t		kr;
	
	// sanity check
	if (inService == NULL)
		return NULL;

	if (inClassName == NULL)
		return NULL;
		
	kr = IORegistryEntryCreateIterator(
			inService,
			kIOServicePlane,
			kIORegistryIterateRecursively | kIORegistryIterateParents,
			&iter);
	if (kr != KERN_SUCCESS) {
		fprintf(stderr, "IOKitObjectFindParentOfClass: cannot create parent iterator: error 0x%08X (%d)\n", kr, kr);
		goto IORegistryEntryCreateIterator_FAILED;
	}

	// check to see if the iterator is valid
	if (!IOIteratorIsValid(iter))
		IOIteratorReset(iter);

	// traverse object tree upwards, recursively
	while( (service = IOIteratorNext(iter))) {
	
		//dumpObject( service );

		// loop until we find object we are interested in
		if ( IOObjectConformsTo(service, inClassName) ) {
			rval = service;
			break;
		}
		
		IOOBJECTRELEASE(service);	
	} // while service
	
	IOOBJECTRELEASE(iter);
	
IORegistryEntryCreateIterator_FAILED:
	return rval;
}

io_object_t		IOMediaToParentIOMediaObject( io_object_t inService, io_name_t inClassName )
{
	io_object_t	rval;
	
	rval = IOKitObjectFindParentOfClass(inService, inClassName );
	if (rval == NULL) {
		// aha we ARE the parent
		if ( IOObjectConformsTo( inService, inClassName ) ) {
			// add an extra retain since caller is responsible for releasing returned object
			IOObjectRetain(inService);
			rval = inService;
		}
			
	}
	return rval;
}

OSStatus MyDiskEntryToIOKitObject(const char *inDiskIdentifier, io_object_t *outIOKitObject )
{
	OSStatus				rval = noErr;
	mach_port_t				masterPort = NULL;
	io_iterator_t			iter = NULL;

	if ( outIOKitObject )
		*outIOKitObject = NULL;

	if ( !outIOKitObject )
		return EINVAL;

	if ( !inDiskIdentifier )
		return EINVAL;

	rval = IOMasterPort( bootstrap_port, &masterPort );
	if ( rval != KERN_SUCCESS ) {
		goto bail;
	}

	rval = IOServiceGetMatchingServices( masterPort, IOBSDNameMatching( masterPort, 0, inDiskIdentifier ), &iter );
	if ( rval != KERN_SUCCESS ) {
		goto bail;
	}

	if ( !IOIteratorIsValid( iter ) )
		IOIteratorReset( iter );

	*outIOKitObject = IOIteratorNext( iter );

bail:

	if ( iter )
		IOObjectRelease( iter );

	return rval;
}

OSStatus getCDROMAndEjectableStatus( char *drivePath, Boolean *isCDROM, Boolean *isEjectable )
{
    kern_return_t			kernResult;
	io_object_t				chidIOObject = NULL, parentIOObject = NULL;
	CFMutableDictionaryRef	dictRef = NULL;
	CFBooleanRef			value = NULL;
	OSStatus				theErr;

	*isCDROM = false;
	*isEjectable = false;
	theErr = noErr;

	kernResult = MyDiskEntryToIOKitObject( drivePath, &chidIOObject );
	if ( kernResult != KERN_SUCCESS || chidIOObject == NULL ) {
		theErr = ( kernResult == KERN_SUCCESS ? -1 : kernResult );
		goto bail;
	}

	parentIOObject = IOMediaToParentIOMediaObject( chidIOObject, kIOCDMediaClass );
	if ( parentIOObject == NULL ) {
		theErr = -2;
		goto bail;
	}

	kernResult = IORegistryEntryCreateCFProperties( parentIOObject, &dictRef, kCFAllocatorDefault, 0 );
	if ( kernResult != KERN_SUCCESS || dictRef == NULL ) {
		theErr = ( kernResult == KERN_SUCCESS ? -3 : kernResult );
		goto bail;
	}

	if ( !CFDictionaryGetValueIfPresent( dictRef, CFSTR(kIOMediaEjectableKey), (const void**) &value ) ) {
		theErr = -4;
		goto bail;
	}

	*isEjectable = CFBooleanGetValue( value );
	
	*isCDROM = (Boolean) IOObjectConformsTo( parentIOObject, kIOCDMediaClass );

bail:	
	
	IOOBJECTRELEASE( parentIOObject );
	IOOBJECTRELEASE( chidIOObject );
	if ( dictRef != NULL )
		CFRelease( dictRef );
	if ( value != NULL )
		CFRelease( value );

	return theErr;
}


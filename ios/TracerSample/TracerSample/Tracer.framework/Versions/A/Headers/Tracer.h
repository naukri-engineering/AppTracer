//
//  Tracer.h
//  Tracer
//
//  Created by Himanshu on 5/20/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import <UIKit/UIKit.h>

//! Project version number for Tracer.
FOUNDATION_EXPORT double TracerVersionNumber;

//! Project version string for Tracer.
FOUNDATION_EXPORT const unsigned char TracerVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <Tracer/PublicHeader.h>
#import<Tracer/AppTracer.h>
#import<Tracer/ATNewMonkTracer.h>
#import<Tracer/ATFileTracer.h>


//DIRECTORY AND FILE STRUCTURE NAMES

#define TRACER_DOC_DIRECTORY_FOLDER_NAME @"AppTracer Logs"
#define TRACER_DOC_DIRECTORY_API_LOGS @"API Logs"
#define TRACER_DOC_DIRECTORY_PAGE_LOAD_FILE_NAME @"Screen Logs"

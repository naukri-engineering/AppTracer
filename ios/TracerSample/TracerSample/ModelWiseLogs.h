//
//  ModelWiseLogs.h
//  TracerSample
//
//  Created by Himanshu Gupta on 11/24/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Tracer/Tracer.h>

@interface ModelWiseLogs : NSObject
+(void)fileLoggingOfCustomPageLoadTime;
+(void)fileLoggingOfExceptionCrashes;
+(void)fileLoggingOfApiInfo;

@end

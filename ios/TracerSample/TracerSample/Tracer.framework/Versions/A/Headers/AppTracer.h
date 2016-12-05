//
//  AppTracer.h
//  NaukriGulf
//
//  Created by Himanshu on 5/20/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//


#import <Foundation/Foundation.h>
#import "Tracer.h"

@interface AppTracer : NSObject
/**
 * Pass the desired logtype model viz. filelogger, newmonkLogger etc.
 */
+(void) initaliseTracerForLoggingOn:(id)logType;
/**
 * Turn TRACER ON or OFF
 */
+(void) enableTracerEngine:(BOOL) onOff;
/**
 * Show Console logs of all Activities tracer Engine does, it also toggle on/off the text file logging of AppTracer. By default debug mode is on
 */
+(void) enableDebug:(BOOL) onOff;

/**
 * Use these methods for logging the API and Page load time logging on New monk
 */
+(void)startOfPageLoad:(NSString*)pageId;
+(void)startOfApi:(NSString*)apiId onPage:(NSString*)pageId;
+(void)endOfApi:(NSString*)apiId onPage:(NSString*)pageId;
+(void)endOfPageLoad:(NSString*)pageId;

/**
 * Clear All the Saved data from DB/Local for New Monk Logging
 */
+(void)clearAllSavedDataFromDB;
/**
 * Set the time interval after which the logs should be posted on Newmonk dashboard. By default, the time interval is set to 4Hrs(4*60*60 seconds)
 */
+(void)batchLoggingIntervalOfLogs:(NSTimeInterval)timeInterval;
/**
 * Value indicates the days of data that need to be logged on New monk. Old page load data which are more than @days old from current-date will not be posted on New Monk and will be flushed from the local Database of apptracer as well.
 */
+(void)onlyLogPageLoadDataWhichAreLessThan:(NSUInteger)days;

/**
 * Method gives the path of log file saved in App Directory. Log file is in .text format
 */
+(NSString *)getFileLogFilePath;

@end

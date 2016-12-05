//
//  ATBaseTracer.h
//  Tracer
//
//  Created by Himanshu Gupta on 12/1/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ATBaseTracer : NSObject
-(void)startOfPageLoad:(NSString*)pageId;
-(void)startOfApi:(NSString*)apiId onPage:(NSString*)pageId;
-(void)endOfApi:(NSString*)apiId onPage:(NSString*)pageId;
-(void)endOfPageLoad:(NSString*)pageId;
@end

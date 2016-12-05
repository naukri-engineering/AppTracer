//
//  ATNewMonkTracer.h
//  Tracer
//
//  Created by Himanshu Gupta on 11/6/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ATBaseTracer.h"

@interface ATNewMonkTracer : ATBaseTracer

@property(nonatomic,retain) NSString *monkUrl;
@property(nonatomic) NSInteger monkAppId;
@property(nonatomic,retain) NSString *userId;

@end

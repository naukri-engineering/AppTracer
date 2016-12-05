//
//  ServiceClass.h
//  TracerSample
//
//  Created by Himanshu on 5/20/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ServiceClass : NSObject
+(void)getJsonResponse : (NSString *)urlStr success : (void (^)(NSDictionary *responseDict))success failure:(void(^)(NSError* error))failure;

@end

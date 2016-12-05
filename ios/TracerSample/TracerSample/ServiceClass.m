//
//  ServiceClass.m
//  TracerSample
//
//  Created by Himanshu on 5/20/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import "ServiceClass.h"

@implementation ServiceClass
+(void)getJsonResponse : (NSString *)urlStr success : (void (^)(NSDictionary *responseDict))success failure:(void(^)(NSError* error))failure
{
    
    
    NSURLSession * session = [NSURLSession sharedSession];
    NSURL * url = [NSURL URLWithString: urlStr];
    
    
    NSURLSessionDataTask * dataTask = [session dataTaskWithURL:url completionHandler:^(NSData *data, NSURLResponse *response, NSError *error)
                                       {
                                           NSDictionary * json  = [NSJSONSerialization JSONObjectWithData:data options:0 error:nil];
                                           success(json);
                                           
                                       }];
    
    [dataTask resume] ;
    
}
@end

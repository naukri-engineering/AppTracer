//
//  ViewController.m
//  TestTracer
//
//  Created by Himanshu on 5/20/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import "ViewController.h"
#import <Tracer/Tracer.h>

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    NSLog(@"%@",[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0]);
    
    [AppTracer enableTracerEngine:YES];
    [AppTracer enableDebug:YES];
    [self testNewMonkPageLoadTime];
    
}
- (IBAction)tapMe:(id)sender {
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"FeedViewController"] animated:YES];
}

-(void)testPageLoadTime{
    [AppTracer startOfPageLoad:@"My Profile"];
    [self performSelector:@selector(endOfLoadTime) withObject:nil afterDelay:1.0];
}
-(void)endOfLoadTime{
    [AppTracer endOfPageLoad:@"My Profile"];
}

-(void)testNewMonkCrashLoggings{

    [[NSMutableArray array] objectAtIndex:0];
}
-(void)testNewMonkPageLoadTime{

    [AppTracer startOfPageLoad:@"Dashboard"];
    [self performSelector:@selector(callAfterSomeTime1) withObject:nil afterDelay:1.0];

}
-(void)callAfterSomeTime1{
    [AppTracer startOfApi:@"RecommendedJobs" onPage:@"Dashboard"];
    [self performSelector:@selector(callAfterSomeTime2) withObject:nil afterDelay:1.0];

}
-(void)callAfterSomeTime2{
    [AppTracer endOfApi:@"RecommendedJobs" onPage:@"Dashboard"];
    [self performSelector:@selector(callAfterSomeTime3) withObject:nil afterDelay:1.0];
    
}
-(void)callAfterSomeTime3{
    [AppTracer endOfPageLoad:@"Dashboard"];
}
-(void)viewDidAppear:(BOOL)animated{
    
    
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
@end

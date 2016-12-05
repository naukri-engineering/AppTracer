//
//  FeedViewController.m
//  TracerSample
//
//  Created by Himanshu on 5/20/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//


#import "FeedViewController.h"
#import "FeedCellTableViewCell.h"
#import "ServiceClass.h"
#import <Tracer/Tracer.h>


@interface FeedViewController ()

@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *indicatorView;
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property(nonatomic,strong)NSMutableArray *tableArr;

@end

@implementation FeedViewController
#pragma mark - View life cycle
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    [AppTracer startOfPageLoad:NSStringFromClass(self.class)];
    
    _tableArr = [NSMutableArray array];
    self.title = @"Stocks Feeds";
    [_indicatorView setHidden:NO];
    [_indicatorView startAnimating];
    [self performSelector:@selector(getFeedItems) withObject:nil afterDelay:2.0];
    
}
- (IBAction)nextVCTap:(id)sender {
    [self.navigationController pushViewController:[self.storyboard instantiateViewControllerWithIdentifier:@"FeedDetailViewController"] animated:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table View Delegates and Datasource
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{


    return 130;
    
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _tableArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {

    static NSString *CellIdentifier = @"FeedCellTableViewCell";
    FeedCellTableViewCell *cell = [_tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    [cell configureCell:[_tableArr objectAtIndex:indexPath.row]];
    return cell;

}

#pragma mark - Call API
-(void)getFeedItems{
    
    NSString *feedUrl = @"http://json.bselivefeeds.indiatimes.com/ET_Community/Gain?pagesize=100";
    
    [AppTracer startOfApi:feedUrl onPage:NSStringFromClass(self.class)];
    
    [ServiceClass getJsonResponse:feedUrl success:^(NSDictionary *responseDict) {
        
        
        [AppTracer endOfApi:feedUrl onPage:NSStringFromClass(self.class)];

        [_tableArr addObjectsFromArray:[responseDict objectForKey:@"searchresult"]];
        
        dispatch_async(dispatch_get_main_queue(), ^{
            [self loadFeedDataOnView];
        });
        
    } failure:^(NSError *error) {
        
        
    }];
    
}
-(void)loadFeedDataOnView{
    
    [_indicatorView setHidden:YES];
    [_tableView reloadData];
    
    [AppTracer endOfPageLoad:NSStringFromClass(self.class)];
    
    
}
@end

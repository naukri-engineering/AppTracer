//
//  FeedCellTableViewCell.h
//  TracerSample
//
//  Created by Himanshu on 5/20/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FeedCellTableViewCell : UITableViewCell
{


    __weak IBOutlet UILabel *priceLbl;
    __weak IBOutlet UILabel *nameLbl;
    
    __weak IBOutlet UILabel *chnageLbl;
    
    __weak IBOutlet UILabel *positionLbl;
    
    __weak IBOutlet UILabel *priceValueLbl;
    
    __weak IBOutlet UILabel *changeValueLbl;
    
    __weak IBOutlet UILabel *positionValueLbl;
    
    
}

-(void)configureCell:(NSMutableDictionary *)dictionary;

@end

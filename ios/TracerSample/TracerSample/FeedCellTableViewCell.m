//
//  FeedCellTableViewCell.m
//  TracerSample
//
//  Created by Himanshu on 5/20/16.
//  Copyright © 2016 Info Edge India Ltd. All rights reserved.
//

#import "FeedCellTableViewCell.h"



@implementation FeedCellTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

-(void)configureCell:(NSMutableDictionary *)dictionary{
    
    nameLbl.text = [dictionary objectForKey:@"companyShortName"];
    priceValueLbl.text = [dictionary objectForKey:@"current"];
//    changeValueLbl.text = [NSString stringWithFormat:@"%@ (+%@)",[dictionary objectForKey:@"absolutechange"],[dictionary objectForKey:@"percentagechange"]] ;
    positionValueLbl.text = [NSString stringWithFormat:@"%@       %@",[dictionary objectForKey:@"low"],[dictionary objectForKey:@"high"]] ;
    
    
    NSString *firstStr = [NSString stringWithFormat:@"+%@ ",[dictionary objectForKey:@"absolutechange"]];
    NSString *secondStr = [NSString stringWithFormat:@"(+%@)",[dictionary objectForKey:@"percentagechange"]];

    NSMutableAttributedString *hintText = [[NSMutableAttributedString alloc] initWithString:[NSString stringWithFormat:@"%@%@",firstStr,secondStr]];
    
    //Red and large
    [hintText setAttributes:@{NSFontAttributeName:[UIFont fontWithName:@"HelveticaNeue" size:14.0f], NSForegroundColorAttributeName:[UIColor blackColor]} range:NSMakeRange(0, firstStr.length)];
    
    [hintText setAttributes:@{NSFontAttributeName:[UIFont fontWithName:@"HelveticaNeue" size:11.0f], NSForegroundColorAttributeName:[UIColor greenColor]} range:NSMakeRange(firstStr.length-1 ,[secondStr length]+1)];

   

    changeValueLbl.attributedText = hintText;

}

@end

//
//  Gyroscope.h
//  NativeModuleExperiment
//
//  Created by Nils Wloka on 21.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//
#import "RCTBridgeModule.h"
#import "CoreMotion/CoreMotion.h"

#ifndef Gyroscope_h
#define Gyroscope_h

@interface Gyroscope : NSObject <RCTBridgeModule>

@property (strong, nonatomic, readonly) CMMotionManager *sharedManager;

@end

#endif /* Gyroscope_h */

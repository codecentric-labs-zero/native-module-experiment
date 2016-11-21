//
//  Gyroscope.m
//  NativeModuleExperiment
//
//  Created by Nils Wloka on 21.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Gyroscope.h"
#import "RCTLog.h"
#import "RCTBridge.h"
#import "RCTEventDispatcher.h"

@interface Gyroscope ()
{
  CMMotionManager *motionmanager;
}
@end

@implementation Gyroscope

@synthesize bridge = _bridge;

static const NSTimeInterval accelerometerMin = 0.01;

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(start) {
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    motionmanager = [[CMMotionManager alloc] init];
  });
  if ([motionmanager isAccelerometerAvailable] == YES) {
    [motionmanager setAccelerometerUpdateInterval:accelerometerMin];
    [motionmanager startAccelerometerUpdatesToQueue:[NSOperationQueue mainQueue]
                                        withHandler:^(CMAccelerometerData *accelerometerData, NSError *error) {
      [self.bridge.eventDispatcher sendAppEventWithName:@"GRAVITY_SENSOR_CHANGED"
                                                   body:@{@"forceX":@(accelerometerData.acceleration.x*10),
                                                          @"forceY":@(accelerometerData.acceleration.y*10),
                                                          @"forceZ":@(accelerometerData.acceleration.z*10)}];
                                        }];
  }
}

RCT_EXPORT_METHOD(stop) {
  if ([motionmanager isAccelerometerActive] == YES) {
    [motionmanager stopAccelerometerUpdates];
  }
}

@end

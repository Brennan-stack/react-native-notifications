#import "RNCommandsHandler.h"
#import "RNNotifications.h"
#import "RNNotificationsStore.h"
#import "RCTConvert+RNNotifications.h"

@implementation RNCommandsHandler {
    RNNotificationCenter* _notificationCenter;
}

- (instancetype)init {
    self = [super init];
    _notificationCenter = [RNNotificationCenter new];
    return self;
}

- (void)requestPermissionsWithCategories:(NSArray *)json {
    [_notificationCenter requestPermissionsWithCategories:json];
}

- (void)getInitialNotification:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    resolve([RNNotificationsStore getInitialNotification]);
}

- (void)finishHandlingAction:(NSString *)completionKey {
    dispatch_async(dispatch_get_main_queue(), ^{
        [[RNNotificationsStore sharedInstance] completeAction:completionKey];
    }
}

- (void)finishPresentingNotification:(NSString *)completionKey presentingOptions:(NSDictionary *)presentingOptions {
    [[RNNotificationsStore sharedInstance] completePresentation:completionKey withPresentationOptions:[RCTConvert UNNotificationPresentationOptions:presentingOptions]];
}

- (void)abandonPermissions {
    [[UIApplication sharedApplication] unregisterForRemoteNotifications];
}

- (void)registerPushKit {
    [RNNotifications startMonitorPushKitNotifications];
}

- (void)getBadgesCount:(RCTResponseSenderBlock)callback {
    NSInteger count = [UIApplication sharedApplication].applicationIconBadgeNumber;
    callback(@[ [NSNumber numberWithInteger:count] ]);
}

- (void)setBadgesCount:(int)count {
    dispatch_async(dispatch_get_main_queue(), ^{
        [[UIApplication sharedApplication] setApplicationIconBadgeNumber:count];
    });
}

- (void)sendLocalNotification:(NSDictionary *)notification withId:(NSString *)notificationId {
    [_notificationCenter sendLocalNotification:notification withId:notificationId];
}

- (void)cancelLocalNotification:(NSString *)notificationId {
    [_notificationCenter cancelLocalNotification:notificationId];
}

- (void)cancelAllLocalNotifications {
    [_notificationCenter cancelAllLocalNotifications];
}

- (void)isRegisteredForRemoteNotifications:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    [_notificationCenter isRegisteredForRemoteNotifications:resolve];
}

- (void)checkPermissions:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    [_notificationCenter checkPermissions:resolve];
}

- (void)requestPermissions:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    [_notificationCenter requestPermissions:resolve reject:reject];
}


- (void)removeAllDeliveredNotifications {
    [_notificationCenter removeAllDeliveredNotifications];
}

- (void)removeDeliveredNotifications:(NSArray<NSString *> *)identifiers {
    [_notificationCenter removeDeliveredNotifications:identifiers];
}

- (void)getDeliveredNotifications:(RCTResponseSenderBlock)callback {
    [_notificationCenter getDeliveredNotifications:callback];
}

@end

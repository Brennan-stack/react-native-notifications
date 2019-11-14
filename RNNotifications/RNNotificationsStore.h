#import <Foundation/Foundation.h>
@import UserNotifications;

@interface RNNotificationsStore : NSObject

+ (instancetype)sharedInstance;

- (void)completeAction:(NSString *)completionKey;
- (void)completePresentation:(NSString *)completionKey withPresentationOptions:(UNNotificationPresentationOptions)presentationOptions;
- (void)setActionCompletionHandler:(void (^)(void))completionHandler withCompletionKey:(NSString *)completionKey;
- (void)setPresentationCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler withCompletionKey:(NSString *)completionKey;

- (void (^)(void))getActionCompletionHandler:(NSString *)key;
- (void (^)(UNNotificationPresentationOptions))getPresentationCompletionHandler:(NSString *)key;

+ (NSDictionary *)getInitialNotification;
+ (void)setInitialNotification:(NSDictionary *)initialNotification;

@end

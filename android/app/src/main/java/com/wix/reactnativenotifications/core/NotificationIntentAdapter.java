package com.wix.reactnativenotifications.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.TaskStackBuilder;
import android.util.Log;
import com.wix.reactnativenotifications.RNNotificationsModule;
import com.wix.reactnativenotifications.RNNotificationsPackage;
import com.wix.reactnativenotifications.core.notification.PushNotificationProps;
import static com.wix.reactnativenotifications.Defs.LOGTAG;

public class NotificationIntentAdapter {
    private static final String PUSH_NOTIFICATION_EXTRA_NAME = "pushNotification";

    public static PendingIntent createPendingNotificationIntent(Context appContext, PushNotificationProps notification) {
        //See https://github.com/wix/react-native-notifications/pull/812/files#diff-5e19e64ecc4213c362f384ece58cee190789b8703512d33b226fcec30e920f01R22
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            Intent mainActivityIntent = appContext.getPackageManager().getLaunchIntentForPackage(appContext.getPackageName());
            mainActivityIntent.putExtra(PUSH_NOTIFICATION_EXTRA_NAME, notification.asBundle());
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(appContext);
            taskStackBuilder.addNextIntentWithParentStack(mainActivityIntent);
            return taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            Intent intent = new Intent(appContext, ProxyService.class);
            intent.putExtra(PUSH_NOTIFICATION_EXTRA_NAME, notification.asBundle());
            return PendingIntent.getService(appContext, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT);
        }
    }

    public static Bundle extractPendingNotificationDataFromIntent(Intent intent) {
        return intent.getBundleExtra(PUSH_NOTIFICATION_EXTRA_NAME);
    }

    public static boolean canHandleIntent(Intent intent) {
        if (intent != null) {
            Bundle notificationData = intent.getExtras();
            return notificationData != null && 
                (intent.hasExtra(PUSH_NOTIFICATION_EXTRA_NAME) || 
                notificationData.getString("google.message_id", null) != null);  
        }

        return false;
    }
}

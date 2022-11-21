package com.wix.reactnativenotifications.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wix.reactnativenotifications.core.notification.PushNotificationProps;

public class NotificationIntentAdapter {
    private static final String PUSH_NOTIFICATION_EXTRA_NAME = "pushNotification";

    public static PendingIntent createPendingNotificationIntent(Context appContext, Intent intent, PushNotificationProps notification) {
        intent.putExtra(PUSH_NOTIFICATION_EXTRA_NAME, notification.asBundle());
        //See https://github.com/wix/react-native-notifications/pull/812/files#diff-5e19e64ecc4213c362f384ece58cee190789b8703512d33b226fcec30e920f01R22 
        //as source of PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getService(appContext, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
    }

    public static Bundle extractPendingNotificationDataFromIntent(Intent intent) {
        return intent.getBundleExtra(PUSH_NOTIFICATION_EXTRA_NAME);
    }

    public static boolean canHandleIntent(Intent intent) {
        if (intent != null) {
            Bundle notificationData = intent.getExtras();
            if (notificationData != null && intent.hasExtra(PUSH_NOTIFICATION_EXTRA_NAME)) {
                return true;
            }
        }

        return false;
    }
}

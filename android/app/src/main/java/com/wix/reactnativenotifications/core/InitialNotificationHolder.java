package com.wix.reactnativenotifications.core;


import com.wix.reactnativenotifications.core.notification.PushNotificationProps;

public class InitialNotificationHolder {

    private static InitialNotificationHolder sInstance;

    private PushNotificationProps mNotification;
    private String mAction;

    public static void setInstance(InitialNotificationHolder instance) {
        sInstance = instance;
    }

    /*package*/ InitialNotificationHolder() {
    }

    public static InitialNotificationHolder getInstance() {
        if (sInstance == null) {
            sInstance = new InitialNotificationHolder();
        }
        return sInstance;
    }

    public void set(PushNotificationProps pushNotificationProps) {
        mNotification = pushNotificationProps;
    }

    public PushNotificationProps get() {
        return mNotification;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public String getAction() {
        return mAction;
    }

    public void clear() {
        mNotification = null;
        mAction = null;
    }
}

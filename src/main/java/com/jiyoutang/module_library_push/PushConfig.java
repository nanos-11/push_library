package com.jiyoutang.module_library_push;

import android.content.Context;

/**
 * Created by panhao on 2015/10/8.
 */
public class PushConfig {
    private Context context;
    private boolean debug;// 是否Debug
    private BaseMessageHandler baseMessageHandler; // 通知栏展示形式
    private CustomNotificationClickHandler customNotificationClickHandler;// 通知栏点击处理回调

    public PushConfig(Context context) {
        this.context = context.getApplicationContext();
    }

    public PushConfig setMessageHandler(BaseMessageHandler baseMessageHandler) {
        this.baseMessageHandler = baseMessageHandler;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public boolean isDebug() {
        return debug;
    }

    public PushConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public BaseMessageHandler getBaseMessageHandler() {
        return baseMessageHandler;
    }

    public CustomNotificationClickHandler getCustomNotificationClickHandler() {
        return customNotificationClickHandler;
    }

    public PushConfig setCustomNotificationClickHandler(CustomNotificationClickHandler customNotificationClickHandler) {
        this.customNotificationClickHandler = customNotificationClickHandler;
        return this;
    }
}

package com.jiyoutang.module_library_push;

import android.app.Notification;
import android.content.Context;

import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by panhao on 2015/10/8.
 * <p/>
 * 基本通知栏展示形式
 */
public class BaseMessageHandler extends UmengMessageHandler {
    @Override
    public Notification getNotification(Context context, UMessage uMessage) {
        return super.getNotification(context, uMessage);
    }
}

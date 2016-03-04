package com.jiyoutang.module_library_push;

import android.content.Context;

import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONObject;

/**
 * Created by panhao on 2015/10/8.
 */
public abstract class CustomNotificationClickHandler extends UmengNotificationClickHandler {
    public static final String DEALWITHCUSTOMACTION = "00001";//打开app

    @Override
    public void dealWithCustomAction(Context context, UMessage uMessage) {
        dealWithAction(context, uMessage);
    }

    /**
     * 自定义处理事件分发
     *
     * @param context
     * @param uMessage
     */
    private void dealWithAction(Context context, UMessage uMessage) {
        String codeStr;
        if (uMessage.extra != null) {
            JSONObject jsonExtra = new JSONObject(uMessage.extra);
            codeStr = jsonExtra.optString("actionPushCode", DEALWITHCUSTOMACTION);
        } else {
            codeStr = DEALWITHCUSTOMACTION;
        }
        myDealWithCustomAction(context, uMessage, codeStr);
    }

    /**
     * 自定义处理
     */
    public void myDealWithCustomAction(Context context, UMessage uMessage, String codeStr) {

    }


}

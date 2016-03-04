package com.jiyoutang.module_library_push;

import android.app.Activity;
import android.content.SharedPreferences;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by panhao on 2015/10/8.
 */
public class CustomPushAgent {
    private static CustomPushAgent mAgent;
    private PushAgent mPushAgent;
    private SharedPreferences sp;

    private CustomPushAgent() {

    }

    public static CustomPushAgent getInstance() {
        if (mAgent == null) {
            synchronized (CustomPushAgent.class) {
                if (mAgent == null) {
                    mAgent = new CustomPushAgent();
                }
            }
        }
        return mAgent;
    }

    /**
     * 初始化Push
     *
     * @param config 配置信息
     */
    public void init(PushConfig config) {
        if (config.getContext() == null) {
            throw new RuntimeException("The context can't be null");
        }
        sp = config.getContext().getSharedPreferences("", Activity.MODE_PRIVATE);
        boolean isPushOpen = isPushOpen();
        mPushAgent = PushAgent.getInstance(config.getContext());
        mPushAgent.setDebugMode(config.isDebug());
        if (config.getBaseMessageHandler() != null) {
            mPushAgent.setMessageHandler(config.getBaseMessageHandler());
        }

        if (config.getCustomNotificationClickHandler() != null) {
            mPushAgent.setNotificationClickHandler(config.getCustomNotificationClickHandler());
        }

        if (isPushOpen) {
            mPushAgent.onAppStart();
            mPushAgent.enable();
        }
    }

    public void disable() {
        if (mPushAgent == null)
            return;
        commitPush(false);
        mPushAgent.disable();
    }

    public void enable() {
        if (mPushAgent == null)
            return;
        commitPush(true);
        mPushAgent.enable();
    }

    public void disable(IUmengUnregisterCallback callback) {
        if (mPushAgent == null)
            return;
        commitPush(false);
        mPushAgent.disable(callback);
    }

    public void enable(IUmengRegisterCallback callback) {
        if (mPushAgent == null)
            return;
        commitPush(true);
        mPushAgent.enable(callback);
    }

    public boolean isPushOpen() {
        return sp.getBoolean("isPush", true);
    }

    private void commitPush(boolean isPush) {
        if (sp == null) {
            return;
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isPush", isPush);
        edit.commit();
    }
}

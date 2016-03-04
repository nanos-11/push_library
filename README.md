# push_library
安卓推送模块集成说明

推送模块

集成说明
	对外API
1.	初始化PushConfig
PushConfig pushConfig = new PushConfig(Context context);
方法说明：
构造推送初始化类
参数说明：
Context上下文  // 
使用说明：
在应用启动时候构造

pushConfig.setDebug (boolean debug)
方法说明：
设置推送的调试模式
参数说明：
debug       调试模式
使用说明：
在构造初始化类的时候调用

pushConfig.setBaseMessageHandler(BaseMessageHandler baseMessageHandler)
方法说明：
初始化获取到推送后，返回的通知栏样式
参数说明：
baseMessageHandler      通知栏展示回调
使用说明：
在构造初始化类的时候调用

pushConfig.setCustomNotificationClickHandler(CustomNotificationClickHandler customNotificationClickHandler)
方法说明：
初始化获取到推送并展示后，通知栏点击事件的回调
参数说明：
CustomNotificationClickHandler通知栏点击回调
使用说明：
在构造初始化类的时候调用

2.	分享操作类CustomPushAgent
CustomPushAgent customPushAgent = CustomPushAgent.getInstance()
方法说明：
单例获取分享操作实例
参数说明：
	无
使用说明：
无

customPushAgent.init(PushConfig config)
方法说明：
对推送进行初始化
参数说明：
Config推送初始化内容
使用说明：
无

customPushAgent.disable()
customPushAgent.enable()
customPushAgent.disable(IUmengUnregisterCallback callback)
customPushAgent.enable(IUmengRegisterCallback callback)
方法说明：
开启关闭推送
参数说明：
IUmengUnregisterCallback关闭推送回调
IUmengRegisterCallback  开启推送回调
使用说明：
开启推送或者关闭推送的时候使用；
IUmengUnregisterCallback：
public interface IUmengRegisterCallback {
void onRegistered(String var1);
}
IUmengUnregisterCallback：
public interface IUmengUnregisterCallback {
    void onUnregistered(String var1);
}

bBoolean customPushAgent.isPushOpen()
方法说明：
获取推送是否开关
参数说明：
无
使用说明：
无

3.	推送通知UMessage
    public String message_id;			//消息ID
    public String title;				//消息的标题
    public String text;				//消息的内容
public String custom;			//自定义的内容，供应用解析
url             	//需要打开的H5页面
expireTime      	//H5过期时间，
isForceUpadate  		//是否是强制升级，
    public int builder_id;				//消息种类，可以用来定制不同的消息通知栏
public Map<String, String> extra;	//自定义参数最多只有10个
			actionPushCode    	//推送CODE

PS：客户端推送都是以extra中自定义推送种类，根据种类，custom中推送相应的字段。
4.	推送通知栏回调BaseMessageHandler
构造说明：
public class BaseMessageHandler extends UmengMessageHandler {
    @Override
    public Notification getNotification(Context context, UMessage uMessage) {
        return super.getNotification(context, uMessage);
    }
}

应用：
BaseMessageHandler baseMessageHandler = new BaseMessageHandler(){
@Override
public Notification getNotification(Context context, UMessage msg) {
switch (msg.builder_id) {
case 1:
	NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
	RemoteViews myNotificationView = 
new RemoteViews(context.getPackageName(), R.layout.notification_view);
	…………
	builder.setContent(myNotificationView);
	builder.setAutoCancel(true);
	Notification mNotification = builder.build();
	mNotification.contentView = myNotificationView;
	return mNotification;
default:
//默认为0，若填写的builder_id并不存在，也使用默认。
return super.getNotification(context, msg);
}
}
}

5.	推送点击事件回调CustomNotificationClickHandler
构造说明：
public abstract class CustomNotificationClickHandler extends UmengNotificationClickHandler {
	//项目用到的方法
    @Override
    public void myDealWithCustomAction(Context context, UMessage uMessage, String codeStr) {
super. dealWithCustomAction (context, uMessage, String codeStr);
		// codeStr 推送的种类
//目前公司推送种类只有以下两种：
//public static final String PUSHCODE_DEALWITHCUSTOMACTION = "000001";//打开app
//public static final String PUSHCODE_OPENACTIVITY = "000002";//打开activity
//public static final String PUSHCODE_OPENWEBVIEW = "000003";//打开端内webview
//public static final String PUSHCODE_LAUNCHAPP = "000004";//升级
JSONObject json = new JSONObject(msg.custom);
if (PUSHCODE_DEALWITHCUSTOMACTION.equals(codeStr)) {
	//打开app
	// codeStr默认为此CODE
} else if (PUSHCODE_OPENACTIVITY.equals(codeStr)) {
	//打开某一个界面
} else if (PUSHCODE_OPENWEBVIEW.equals(codeStr) {
	//打开webview
	String actionUrl = json.optString("url", "");
	String expireTime = json.optString("expireTime", "");
} else if (PUSHCODE_UPDATE.equals(codeStr)) {
	//升级
	boolean isForceUpdate = json.optBoolean("isForceUpdate", false);
} else {
}
}
// myDealWithCustomAction方法从此方法而来
@Override
public void dealWithCustomAction(Context context, UMessage msg) {
	super. dealWithCustomAction (context, uMessage);
}
//打开某一个界面
@Override
public void openActivity(Context context, UMessage uMessage) {
	super.openActivity(context, uMessage);
}
//使用浏览器打开某一个url
@Override
public void openUrl(Context context, UMessage uMessage) {
	LogUtils.d("openUrl");
	super.openUrl(context, uMessage);
}
//打开应用
@Override
public void launchApp(Context context, UMessage uMessage) {
	LogUtils.d("launchApp");
	super.launchApp(context, uMessage);
}
}

	混淆
-keep class com.umeng.message.* {
        public <fields>;
        public <methods>;
}
-keep class com.umeng.message.protobuffer.* {
        public <fields>;
        public <methods>;
}
-keep class com.squareup.wire.* {
        public <fields>;
        public <methods>;
}
-keep class com.umeng.message.local.* {
        public <fields>;
        public <methods>;
}
-keep class org.android.agoo.impl.*{
        public <fields>;
        public <methods>;
}
-keep class org.android.agoo.service.* {*;}
-keep class org.android.spdy.**{*;}
-keep public class [应用包名].R$*{
    public static final int *;
}
	AndroidManifest.xml
<!-- 必选 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.WRITE_SETTINGS" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" /> 
<!--【必选】用以设置前台是否显示通知> 
<uses-permission android:name="android.permission.GET_TASKS" /> 
<!-- 可选 -->
<uses-permission android:name="android.permission.BROADCAST_PACKAGE_ADDED" />
<uses-permission android:name="android.permission.BROADCAST_PACKAGE_CHANGED" />
<uses-permission android:name="android.permission.BROADCAST_PACKAGE_INSTALL" />
<uses-permission android:name="android.permission.BROADCAST_PACKAGE_REPLACED" />
<uses-permission android:name="android.permission.RESTART_PACKAGES" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />;
<!-- 监听通知点击或者忽略处理的广播 -->
<receiver
    android:name="com.umeng.message.NotificationProxyBroadcastReceiver"
    android:exported="false" >
</receiver>  
<!-- 监听开机运行、网络连接变化、卸载的广播 -->
<receiver
    android:name="com.umeng.message.SystemReceiver"
    android:process=":push" >
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
    <intent-filter>
        <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
    </intent-filter>
    <intent-filter>
        <action android:name="android.intent.action.PACKAGE_REMOVED" />
        <data android:scheme="package" />
    </intent-filter>
</receiver>  
<!-- 监听消息到达的广播 -->
<receiver
    android:name="com.umeng.message.MessageReceiver"
     android:exported="false" 
   android:process=":push" >
    <intent-filter>
        <action android:name="org.agoo.android.intent.action.RECEIVE" />
    </intent-filter>
</receiver>  
<!-- 监听宿主选举的广播 -->
<receiver
    android:name="com.umeng.message.ElectionReceiver"  
     android:process=":push" >
    <intent-filter>
        <action android:name="org.agoo.android.intent.action.ELECTION_RESULT_V4" />
        <category android:name="umeng" />
    </intent-filter>
</receiver>  
<!-- 监听注册的广播 --> <!-- 【应用包名】字符串需要替换成本应用的应用包名 -->
<receiver
    android:name="com.umeng.message.RegistrationReceiver"
    android:exported="false" >
    <intent-filter>
        <action android:name="【应用包名】.intent.action.COMMAND" />
    </intent-filter>
</receiver>
<receiver android:name="com.umeng.message.UmengMessageBootReceiver" >
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>
可以根据需要自行设置 android:label 中的服务名 ：
<!-- Umeng的长连服务，用来建立推送的长连接的 --> 
<!-- 【应用包名】字符串需要替换成本应用的应用包名 -->
<service
    android:name="com.umeng.message.UmengService"
    android:label="PushService"
    android:exported="true"
    android:process=":push" >
    <intent-filter>
        <action android:name="【应用包名】.intent.action.START" />
    </intent-filter>
    <intent-filter>
        <action android:name="【应用包名】.intent.action.COCKROACH" />
    </intent-filter>
    <intent-filter>
        <action android:name="org.agoo.android.intent.action.PING_V4" />
    <category android:name="umeng" />
    </intent-filter>
</service>
 <!-- Umeng的消息接收服务 --> 
<service android:name="com.umeng.message.UmengIntentService" 
    android:process=":push" />
 <!-- Umeng的消息路由服务 --> 
<service 
    android:name="com.umeng.message.UmengMessageIntentReceiverService"
    android:process=":push" 
    android:exported="true" >
    <intent-filter>
        <action android:name="org.android.agoo.client.MessageReceiverService" />
    </intent-filter>
    <intent-filter>
        <action android:name="org.android.agoo.client.ElectionReceiverService" />
    </intent-filter>
</service>  
<!-- v2.4.1添加的Service，Umeng的消息接收后的处理服务 --> 
<service android:name="com.umeng.message.UmengMessageCallbackHandlerService" 
    android:exported="false">
    <intent-filter>
        <action android:name="com.umeng.messge.registercallback.action" />
    </intent-filter>
    <intent-filter>
        <action android:name="com.umeng.message.unregistercallback.action"/>
    </intent-filter>
    <intent-filter>
        <action android:name="com.umeng.message.message.handler.action"/>
    </intent-filter>
    <intent-filter>
        <action android:name="com.umeng.message.autoupdate.handler.action"/>
    </intent-filter>
</service>  
<!-- V1.3.0添加的service，负责下载通知的资源 -->
<service android:name="com.umeng.message.UmengDownloadResourceService" />  <!-- V2.5.0添加的Service，用于本地通知 -->
<!-- 如果不使用本地通知，可以注释掉本地通知服务的配置 -->
<service android:name="com.umeng.message.local.UmengLocalNotificationService"  
android:exported="false" />
<meta-data android:name="UMENG_APPKEY" 
android:value="xxxxxxxxxxxxxxxxxxxxxxxxxxxx" > 
</meta-data> 
<meta-data android:name="UMENG_MESSAGE_SECRET" 
android:value="xxxxxxxxxxxxxxxxxxxxxxxxxxxx" > 
</meta-data>


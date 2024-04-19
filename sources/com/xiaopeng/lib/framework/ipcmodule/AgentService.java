package com.xiaopeng.lib.framework.ipcmodule;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
/* loaded from: classes.dex */
public class AgentService extends Service {
    private static final String CHANNEL_ID = "XPENG";
    private static final String CHANNEL_NAME = "IPC";
    private static final String CONTENT = "IPC application is running";
    private static final String TAG = "AgentService";
    private static final String TITLE = "Xmart";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.i(TAG, "onCreate");
        startNotification();
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand:\t" + startId + "\tflgs:\t" + flags);
        IpcServiceImpl.getInstance().init();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startNotification() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        Log.i(TAG, "startNotification");
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, 3);
        NotificationManager service = (NotificationManager) getApplicationContext().getSystemService("notification");
        if (service != null) {
            service.createNotificationChannel(channel);
        }
        Notification notification = new Notification.Builder(this, channel.getId()).setSmallIcon(17301595).setContentTitle(TITLE).setContentText(CONTENT).setCategory("service").build();
        startForeground(100, notification);
    }
}

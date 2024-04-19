package com.xpeng.xpcarremotecontrol.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xpeng.xpcarremotecontrol.utils.LogUtils;
/* loaded from: classes.dex */
public class RemoteControlService extends Service {
    private static final String TAG = "RemoteControlService";
    RemoteControlServiceImp mServiceImp;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogUtils.i(TAG, "onDestroy");
        super.onDestroy();
        this.mServiceImp.releaseInstance();
    }

    @Override // android.app.Service
    public void onCreate() {
        LogUtils.i(TAG, "onCreate");
        super.onCreate();
        this.mServiceImp = RemoteControlServiceImp.getInstance(this);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }
}

package com.xpeng.xpcarremotecontrol;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.xpeng.xpcarremotecontrol.carmanager.CarClientWrapper;
import com.xpeng.xpcarremotecontrol.utils.LogUtils;
/* loaded from: classes.dex */
public class App extends Application {
    private static final String TAG = "XpCarRemoteControl.App";
    private static App sAppInstance;

    public static Context getInstance() {
        return sAppInstance;
    }

    @Override // android.app.Application
    public void onCreate() {
        LogUtils.i(TAG, "onCreate");
        super.onCreate();
        sAppInstance = this;
        CarClientWrapper.getInstance().connectToCar();
        IpcAgent.registerModule(this);
        startRemoteControlService();
    }

    @Override // android.app.Application
    public void onTerminate() {
        CarClientWrapper.getInstance().disconnect();
        super.onTerminate();
    }

    private void startRemoteControlService() {
        Intent sendIntent = new Intent();
        sendIntent.setAction("com.xpeng.xpcarremotecontrol.RemoteControlService");
        sendIntent.setPackage("com.xpeng.xpcarremotecontrol");
        startServiceAsUser(sendIntent, UserHandle.SYSTEM);
    }
}

package com.xpeng.xpcarremotecontrol.service;

import android.content.Context;
import com.android.internal.annotations.GuardedBy;
import com.xpeng.xpcarremotecontrol.cameraremotecontrol.CameraRemoteControlService;
/* loaded from: classes.dex */
class RemoteControlServiceImp {
    @GuardedBy({"sInstanceLock"})
    private static volatile RemoteControlServiceImp sInstance;
    private static final Object sInstanceLock = new Object();
    private final ServiceBase[] mAllServices;
    private final CameraRemoteControlService mXpCameraRemoteControlService;

    public RemoteControlServiceImp(Context ctx) {
        this.mXpCameraRemoteControlService = new CameraRemoteControlService(ctx);
        this.mAllServices = new ServiceBase[]{this.mXpCameraRemoteControlService};
    }

    public static RemoteControlServiceImp getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (sInstanceLock) {
                if (sInstance == null) {
                    sInstance = new RemoteControlServiceImp(ctx);
                    sInstance.init();
                }
            }
        }
        return sInstance;
    }

    private void init() {
        ServiceBase[] serviceBaseArr;
        for (ServiceBase service : this.mAllServices) {
            if (service != null) {
                service.init();
            }
        }
    }

    public synchronized void releaseInstance() {
        if (sInstance == null) {
            return;
        }
        sInstance.release();
        sInstance = null;
    }

    private void release() {
        for (int i = this.mAllServices.length - 1; i >= 0; i--) {
            ServiceBase[] serviceBaseArr = this.mAllServices;
            if (serviceBaseArr[i] != null) {
                serviceBaseArr[i].release();
            }
        }
    }
}

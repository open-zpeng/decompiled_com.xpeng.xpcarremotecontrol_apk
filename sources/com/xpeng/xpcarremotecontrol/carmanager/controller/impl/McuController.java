package com.xpeng.xpcarremotecontrol.carmanager.controller.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.mcu.CarMcuManager;
import com.xpeng.xpcarremotecontrol.carmanager.BaseCarController;
import com.xpeng.xpcarremotecontrol.carmanager.controller.IMcuController;
import com.xpeng.xpcarremotecontrol.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class McuController extends BaseCarController implements IMcuController {
    public static final int REMOTE_CONTROL_COMPLETED = 2;
    public static final int REMOTE_CONTROL_PROCESSING = 1;
    private static final String TAG = McuController.class.getSimpleName();
    private CarMcuManager mCarManager;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    public synchronized void initCarManager(Car carClient) {
        LogUtils.i(TAG, "Init start");
        try {
            this.mCarManager = (CarMcuManager) carClient.getCarManager("xp_mcu");
        } catch (CarNotConnectedException | IllegalStateException e) {
            LogUtils.e(TAG, null, e);
        }
        LogUtils.i(TAG, "Init end");
    }

    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        List<Integer> propertyIds = new ArrayList<>();
        propertyIds.add(560993309);
        return propertyIds;
    }

    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    public synchronized void onCarDisconnected() {
        this.mCarManager = null;
    }

    @Override // com.xpeng.xpcarremotecontrol.carmanager.controller.IMcuController
    public void setRemoteControlFeedback(int status) {
        CarMcuManager mgr = getCarManager();
        if (mgr != null) {
            try {
                mgr.setRemoteControlFeedback(status);
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, null, e);
                return;
            }
        }
        LogUtils.w(TAG, "CarManager is null");
    }

    private CarMcuManager getCarManager() {
        CarMcuManager mgr;
        synchronized (this) {
            mgr = this.mCarManager;
        }
        return mgr;
    }
}

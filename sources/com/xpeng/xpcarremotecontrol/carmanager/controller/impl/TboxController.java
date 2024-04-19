package com.xpeng.xpcarremotecontrol.carmanager.controller.impl;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.tbox.CarTboxManager;
import com.xpeng.xpcarremotecontrol.carmanager.BaseCarController;
import com.xpeng.xpcarremotecontrol.carmanager.controller.ITboxCallback;
import com.xpeng.xpcarremotecontrol.carmanager.controller.ITboxController;
import com.xpeng.xpcarremotecontrol.utils.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class TboxController extends BaseCarController<ITboxCallback> implements ITboxController {
    private static final String TAG = TboxController.class.getSimpleName();
    private CarTboxManager mCarManager;
    private final CarTboxManager.CarTboxEventCallback mCarTboxEventCallback = new CarTboxManager.CarTboxEventCallback() { // from class: com.xpeng.xpcarremotecontrol.carmanager.controller.impl.TboxController.1
        public void onChangeEvent(CarPropertyValue carPropertyValue) {
            TboxController.this.handleCarEventsUpdate(carPropertyValue);
        }

        public void onErrorEvent(int propertyId, int errorCode) {
            String str = TboxController.TAG;
            LogUtils.e(str, "onErrorEvent: " + propertyId + ", errorCode: " + errorCode);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    public void initCarManager(Car carClient) {
        boolean register;
        LogUtils.i(TAG, "Init start");
        synchronized (this) {
            try {
                this.mCarManager = (CarTboxManager) carClient.getCarManager("xp_tbox");
            } catch (CarNotConnectedException | IllegalStateException e) {
                LogUtils.e(TAG, null, e);
            }
        }
        synchronized (this.mCallbackLock) {
            register = !this.mCallbacks.isEmpty();
        }
        if (register) {
            registerCallbackToCarService();
        }
        LogUtils.i(TAG, "Init end");
    }

    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    protected List<Integer> getRegisterPropertyIds() {
        List<Integer> propertyIds = new ArrayList<>();
        propertyIds.add(554700825);
        return propertyIds;
    }

    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    public synchronized void onCarDisconnected() {
        this.mCarManager = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    public void handleEventsUpdate(CarPropertyValue<?> value) {
        int propertyId = value.getPropertyId();
        if (propertyId == 554700825) {
            handleCameraRemoteCtrlMsg((String) getValue(value));
            return;
        }
        String str = TAG;
        LogUtils.w(str, "Received property event for unhandled propId=" + propertyId);
    }

    private void handleCameraRemoteCtrlMsg(String value) {
        synchronized (this.mCallbackLock) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ITboxCallback callback = (ITboxCallback) it.next();
                callback.onCameraRemoteControlChanged(value);
            }
        }
    }

    private CarTboxManager getCarManager() {
        CarTboxManager mgr;
        synchronized (this) {
            mgr = this.mCarManager;
        }
        return mgr;
    }

    @Override // com.xpeng.xpcarremotecontrol.carmanager.controller.ITboxController
    public void setCameraRemoteControlFeedback(String msg) {
        CarTboxManager mgr = getCarManager();
        if (mgr != null) {
            try {
                mgr.setCameraRemoteControlFeedback(msg);
                return;
            } catch (Exception e) {
                LogUtils.e(TAG, null, e);
                return;
            }
        }
        LogUtils.w(TAG, "CarManager is null");
    }

    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    protected void registerCallbackToCarService() {
        CarTboxManager mgr = getCarManager();
        if (mgr != null) {
            try {
                mgr.registerPropCallback(this.mPropertyIds, this.mCarTboxEventCallback);
            } catch (CarNotConnectedException | IllegalArgumentException e) {
                LogUtils.e(TAG, null, e);
            }
        }
    }

    @Override // com.xpeng.xpcarremotecontrol.carmanager.BaseCarController
    protected void unregisterCallbackToCarService() {
        CarTboxManager mgr = getCarManager();
        if (mgr != null) {
            try {
                mgr.unregisterPropCallback(this.mPropertyIds, this.mCarTboxEventCallback);
            } catch (CarNotConnectedException | IllegalArgumentException e) {
                LogUtils.e(TAG, null, e);
            }
        }
    }
}

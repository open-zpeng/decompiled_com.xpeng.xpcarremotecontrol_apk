package com.xpeng.xpcarremotecontrol.carmanager;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import com.xpeng.xpcarremotecontrol.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public abstract class BaseCarController<T> {
    protected final Object mCallbackLock = new Object();
    protected final ArrayList<T> mCallbacks = new ArrayList<>();
    protected final List<Integer> mPropertyIds = getRegisterPropertyIds();

    protected abstract List<Integer> getRegisterPropertyIds();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void initCarManager(Car car);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void onCarDisconnected();

    public final void registerCallback(T callback) {
        boolean register;
        if (callback != null) {
            synchronized (this.mCallbackLock) {
                register = this.mCallbacks.isEmpty();
                this.mCallbacks.add(callback);
            }
            if (register) {
                registerCallbackToCarService();
            }
        }
    }

    protected void registerCallbackToCarService() {
    }

    public final void unregisterCallback(T callback) {
        boolean unregister;
        synchronized (this.mCallbackLock) {
            this.mCallbacks.remove(callback);
            unregister = this.mCallbacks.isEmpty();
        }
        if (unregister) {
            unregisterCallbackToCarService();
        }
    }

    protected void unregisterCallbackToCarService() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: handleEventsUpdate */
    public void lambda$handleCarEventsUpdate$0$BaseCarController(CarPropertyValue<?> value) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void handleCarEventsUpdate(final CarPropertyValue<?> value) {
        ThreadUtils.execute(new Runnable() { // from class: com.xpeng.xpcarremotecontrol.carmanager.-$$Lambda$BaseCarController$43PRKmNUKDw_L3Y-9b399R1yyFg
            @Override // java.lang.Runnable
            public final void run() {
                BaseCarController.this.lambda$handleCarEventsUpdate$0$BaseCarController(value);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <E> E getValue(CarPropertyValue<?> value) {
        return (E) value.getValue();
    }
}

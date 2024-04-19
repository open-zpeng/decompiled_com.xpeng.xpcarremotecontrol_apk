package com.xpeng.xpcarremotecontrol.carmanager;

import android.car.Car;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.ArrayMap;
import com.android.internal.annotations.GuardedBy;
import com.xpeng.xpcarremotecontrol.App;
import com.xpeng.xpcarremotecontrol.carmanager.controller.impl.McuController;
import com.xpeng.xpcarremotecontrol.carmanager.controller.impl.TboxController;
import com.xpeng.xpcarremotecontrol.utils.LogUtils;
import com.xpeng.xpcarremotecontrol.utils.ThreadUtils;
import java.util.concurrent.CountDownLatch;
/* loaded from: classes.dex */
public class CarClientWrapper {
    private volatile Car mCarClient;
    private static final String TAG = CarClientWrapper.class.getSimpleName();
    private static final String[] CAR_SVC_ARRAY = {"xp_mcu", "xp_tbox"};
    private final Object mControllerLock = new Object();
    @GuardedBy({"mControllerLock"})
    private final ArrayMap<String, BaseCarController> mControllers = new ArrayMap<>();
    private volatile boolean mIsCarSvcConnected = false;
    private volatile CountDownLatch mSvcCountdown = new CountDownLatch(CAR_SVC_ARRAY.length);
    private final ServiceConnection mCarConnectionCb = new ServiceConnection() { // from class: com.xpeng.xpcarremotecontrol.carmanager.CarClientWrapper.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(CarClientWrapper.TAG, "onCarServiceConnected");
            CarClientWrapper.this.initCarControllers();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            String[] strArr;
            LogUtils.i(CarClientWrapper.TAG, "onCarServiceDisconnected");
            CarClientWrapper.this.mIsCarSvcConnected = false;
            synchronized (CarClientWrapper.this.mControllerLock) {
                for (String serviceName : CarClientWrapper.CAR_SVC_ARRAY) {
                    BaseCarController<?> controller = (BaseCarController) CarClientWrapper.this.mControllers.get(serviceName);
                    if (controller != null) {
                        controller.onCarDisconnected();
                    }
                }
            }
            CarClientWrapper.this.reconnectToCar();
        }
    };

    public static CarClientWrapper getInstance() {
        return SingleHolder.sInstance;
    }

    public void connectToCar() {
        if (!this.mIsCarSvcConnected) {
            this.mCarClient = Car.createCar(App.getInstance(), this.mCarConnectionCb);
            LogUtils.i(TAG, "Start to connect Car service");
            this.mCarClient.connect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reconnectToCar() {
        LogUtils.i(TAG, "Reconnect to car service");
        this.mSvcCountdown = new CountDownLatch(CAR_SVC_ARRAY.length);
    }

    public void disconnect() {
        if (this.mIsCarSvcConnected && this.mCarClient != null) {
            this.mCarClient.disconnect();
        }
    }

    public BaseCarController getController(String serviceName) {
        BaseCarController controller;
        String str = TAG;
        LogUtils.i(str, "getController: " + serviceName);
        getCarClient();
        synchronized (this.mControllerLock) {
            controller = this.mControllers.get(serviceName);
            if (controller == null) {
                LogUtils.i(TAG, "controller is null");
                controller = createCarController(serviceName);
                this.mControllers.put(serviceName, controller);
            }
        }
        return controller;
    }

    private Car getCarClient() {
        if (!this.mIsCarSvcConnected) {
            try {
                LogUtils.i(TAG, "Waiting car service ready internal");
                this.mSvcCountdown.await();
                this.mIsCarSvcConnected = true;
                LogUtils.i(TAG, "Car service has been ready internal");
            } catch (InterruptedException e) {
                LogUtils.e(TAG, null, e);
            }
        }
        return this.mCarClient;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCarControllers() {
        final Car carClient = this.mCarClient;
        final CountDownLatch countDownLatch = this.mSvcCountdown;
        ThreadUtils.execute(new Runnable() { // from class: com.xpeng.xpcarremotecontrol.carmanager.-$$Lambda$CarClientWrapper$7Ila_p1uXQ8wN96zAfXkhZ0WjwM
            @Override // java.lang.Runnable
            public final void run() {
                CarClientWrapper.this.lambda$initCarControllers$0$CarClientWrapper(carClient, countDownLatch);
            }
        });
    }

    public /* synthetic */ void lambda$initCarControllers$0$CarClientWrapper(Car carClient, CountDownLatch countDownLatch) {
        String[] strArr;
        LogUtils.i(TAG, "initCarControllers start");
        synchronized (this.mControllerLock) {
            for (String serviceName : CAR_SVC_ARRAY) {
                BaseCarController controller = this.mControllers.get(serviceName);
                if (controller == null) {
                    controller = createCarController(serviceName);
                    this.mControllers.put(serviceName, controller);
                } else {
                    LogUtils.i(TAG, "re-initCarControllers");
                }
                controller.initCarManager(carClient);
                countDownLatch.countDown();
            }
        }
        LogUtils.d(TAG, "initCarControllers end");
    }

    private BaseCarController createCarController(String serviceName) {
        char c;
        LogUtils.i(TAG, "createCarController: " + serviceName);
        int hashCode = serviceName.hashCode();
        if (hashCode != -1870955074) {
            if (hashCode == -753096744 && serviceName.equals("xp_mcu")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (serviceName.equals("xp_tbox")) {
                c = 0;
            }
            c = 65535;
        }
        if (c == 0) {
            BaseCarController controller = new TboxController();
            return controller;
        } else if (c == 1) {
            BaseCarController controller2 = new McuController();
            return controller2;
        } else {
            throw new IllegalArgumentException("Can not create controller for " + serviceName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class SingleHolder {
        private static final CarClientWrapper sInstance = new CarClientWrapper();

        private SingleHolder() {
        }
    }
}

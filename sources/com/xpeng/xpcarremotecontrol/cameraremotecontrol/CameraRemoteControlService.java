package com.xpeng.xpcarremotecontrol.cameraremotecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
import com.xiaopeng.libconfig.BuildConfig;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import com.xpeng.xpcarremotecontrol.IpcAgent;
import com.xpeng.xpcarremotecontrol.cameraremotecontrol.CameraRemoteControlService;
import com.xpeng.xpcarremotecontrol.carmanager.CarClientWrapper;
import com.xpeng.xpcarremotecontrol.carmanager.controller.ITboxCallback;
import com.xpeng.xpcarremotecontrol.carmanager.controller.impl.McuController;
import com.xpeng.xpcarremotecontrol.carmanager.controller.impl.TboxController;
import com.xpeng.xpcarremotecontrol.service.ServiceBase;
import com.xpeng.xpcarremotecontrol.utils.LogUtils;
import com.xpeng.xpcarremotecontrol.utils.ThreadUtils;
/* loaded from: classes.dex */
public class CameraRemoteControlService implements ServiceBase {
    private static final String ACTION_XMART_FEEDBACK = "com.xiaopeng.action.XMART_FEEDBACK";
    private static final String ACTION_XMART_FEEDBACK_VALUE = "xmart_feedback_value";
    private static final String CAR_CAMERA = "com.xiaopeng.xmart.camera";
    private static final boolean DBG = true;
    private static final int HEARTBEAT_MSG_TYPE = 4;
    private static final int PENDING = 2;
    private static final int REMOTE_LIVE_SERVICE_TYPE = 11;
    private static final int START = 1;
    private static final int STOP = 0;
    private static final String TAG = "CameraRemoteControlService";
    private static final int TICK_DELAY_MS = 30000;
    private static final int TYPE_CONTROL_COMMAND_IPC = 150003;
    private static final int WAITING = 3;
    private Handler mAppFeedbackHandler;
    private HandlerThread mAppFeedbackHandlerThread;
    private final Context mContext;
    private McuController mMcuController;
    private TboxController mTboxController;
    private Handler mTickHandler;
    private HandlerThread mTickHandlerThread;
    private final BroadcastReceiver mBroadcastReceiver = new AnonymousClass1();
    private int mRemoteControlStatus = 0;
    private int mWaitTimes = 0;
    private final Runnable mTickRunnable = new Runnable() { // from class: com.xpeng.xpcarremotecontrol.cameraremotecontrol.CameraRemoteControlService.2
        @Override // java.lang.Runnable
        public void run() {
            int status;
            int waitTimes;
            McuController mcuController;
            synchronized (CameraRemoteControlService.this) {
                status = CameraRemoteControlService.this.mRemoteControlStatus;
                waitTimes = CameraRemoteControlService.this.mWaitTimes;
                mcuController = CameraRemoteControlService.this.mMcuController;
            }
            if (status == 1 || status == 2) {
                Log.v(CameraRemoteControlService.TAG, "Keep CDU live");
                if (mcuController != null) {
                    mcuController.setRemoteControlFeedback(1);
                }
                CameraRemoteControlService.this.mTickHandler.postDelayed(this, 30000L);
                CameraRemoteControlService.this.setRemoteControlling(3);
            } else if (status == 3) {
                Log.v(CameraRemoteControlService.TAG, "Waiting for heartbeat:" + waitTimes);
                if (waitTimes <= 1) {
                    CameraRemoteControlService.this.setWaitTimes(waitTimes + 1);
                    CameraRemoteControlService.this.mTickHandler.postDelayed(this, 30000L);
                    return;
                }
                Log.v(CameraRemoteControlService.TAG, "stop mcu heartbeat");
                CameraRemoteControlService.this.setRemoteControlling(0);
            } else {
                Log.v(CameraRemoteControlService.TAG, "stop mcu heartbeat");
            }
        }
    };
    private ITboxCallback mTboxCallback = new ITboxCallback() { // from class: com.xpeng.xpcarremotecontrol.cameraremotecontrol.CameraRemoteControlService.3
        @Override // com.xpeng.xpcarremotecontrol.carmanager.controller.ITboxCallback
        public void onCameraRemoteControlChanged(String msg) {
            LogUtils.i(CameraRemoteControlService.TAG, "onCameraRemoteControlChanged :" + msg);
            try {
                MqttMsg<CameraRemoteControlReq> mqttMsgBase = (MqttMsg) new Gson().fromJson(msg, new TypeToken<MqttMsg<CameraRemoteControlReq>>() { // from class: com.xpeng.xpcarremotecontrol.cameraremotecontrol.CameraRemoteControlService.3.1
                }.getType());
                LogUtils.i(CameraRemoteControlService.TAG, "convert to:" + mqttMsgBase);
                int serviceType = mqttMsgBase.getServiceType();
                int msgType = mqttMsgBase.getMsgType();
                CameraRemoteControlReq commandItem = mqttMsgBase.getMsgContent();
                if (serviceType == 11) {
                    if (4 != msgType) {
                        if (commandItem != null) {
                            if (CameraRemoteControlService.this.isRemoteControlCompleted(commandItem)) {
                                CameraRemoteControlService.this.setRemoteControlling(0);
                                CameraRemoteControlService.this.mTickHandler.removeCallbacks(CameraRemoteControlService.this.mTickRunnable);
                            } else if (!CameraRemoteControlService.this.isRemoteControlling()) {
                                CameraRemoteControlService.this.setRemoteControlling(1);
                                CameraRemoteControlService.this.mTickHandler.post(CameraRemoteControlService.this.mTickRunnable);
                            } else {
                                CameraRemoteControlService.this.setRemoteControlling(2);
                            }
                            CameraRemoteControlService.this.sendMessageByIpc(serviceType, msg, "com.xiaopeng.xmart.camera");
                            return;
                        }
                        LogUtils.w(CameraRemoteControlService.TAG, "Msg content is null");
                        return;
                    }
                    CameraRemoteControlService.this.setRemoteControlling(2);
                    CameraRemoteControlService.this.sendMessageByIpc(serviceType, msg, "com.xiaopeng.xmart.camera");
                    return;
                }
                LogUtils.w(CameraRemoteControlService.TAG, "Unsupported service type");
            } catch (Exception e) {
                LogUtils.e(CameraRemoteControlService.TAG, null, e);
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xpeng.xpcarremotecontrol.cameraremotecontrol.CameraRemoteControlService$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, final Intent intent) {
            if ("com.xiaopeng.action.XMART_FEEDBACK".equals(intent.getAction())) {
                LogUtils.i(CameraRemoteControlService.TAG, "Receive camera feedback");
                CameraRemoteControlService.this.mAppFeedbackHandler.post(new Runnable() { // from class: com.xpeng.xpcarremotecontrol.cameraremotecontrol.-$$Lambda$CameraRemoteControlService$1$4EHoJFwUVWqyoIcscqeYC2RpCpw
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraRemoteControlService.AnonymousClass1.this.lambda$onReceive$0$CameraRemoteControlService$1(intent);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$onReceive$0$CameraRemoteControlService$1(Intent intent) {
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString("xmart_feedback_value");
            LogUtils.i(CameraRemoteControlService.TAG, "OnReceive msg: " + msg);
            synchronized (CameraRemoteControlService.this) {
                if (CameraRemoteControlService.this.mTboxController != null) {
                    CameraRemoteControlService.this.mTboxController.setCameraRemoteControlFeedback(msg);
                }
            }
        }
    }

    public CameraRemoteControlService(Context context) {
        LogUtils.i(TAG, "Create CameraRemoteControlService");
        this.mContext = context;
    }

    public synchronized void setWaitTimes(int waitTimes) {
        this.mWaitTimes = waitTimes;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isRemoteControlCompleted(CameraRemoteControlReq commandItem) {
        int cmdValue = (int) commandItem.getCmdValue();
        if (2 == commandItem.getCmdType()) {
            if (-1 != cmdValue && cmdValue != 0) {
                return false;
            }
            return DBG;
        }
        return false;
    }

    public synchronized boolean isRemoteControlling() {
        return this.mRemoteControlStatus != 0 ? DBG : false;
    }

    public synchronized void setRemoteControlling(int status) {
        this.mRemoteControlStatus = status;
        if (status != 3) {
            this.mWaitTimes = 0;
        } else {
            this.mWaitTimes = 1;
        }
    }

    @Override // com.xpeng.xpcarremotecontrol.service.ServiceBase
    public synchronized void init() {
        LogUtils.i(TAG, "init");
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xiaopeng.action.XMART_FEEDBACK");
        this.mContext.registerReceiver(this.mBroadcastReceiver, filter);
        initHandlerThreadAndHandler();
        ThreadUtils.execute(new Runnable() { // from class: com.xpeng.xpcarremotecontrol.cameraremotecontrol.-$$Lambda$CameraRemoteControlService$v_UGCgNJ0wGuioeZlAQrylsP_Rg
            @Override // java.lang.Runnable
            public final void run() {
                CameraRemoteControlService.this.initCarControllers();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void initCarControllers() {
        LogUtils.i(TAG, "initCarControllers");
        CarClientWrapper carClientWrapper = CarClientWrapper.getInstance();
        this.mMcuController = (McuController) carClientWrapper.getController("xp_mcu");
        this.mTboxController = (TboxController) carClientWrapper.getController("xp_tbox");
        this.mTboxController.registerCallback(this.mTboxCallback);
    }

    private void initHandlerThreadAndHandler() {
        this.mTickHandlerThread = new HandlerThread("Tick");
        this.mTickHandlerThread.start();
        this.mTickHandler = new Handler(this.mTickHandlerThread.getLooper());
        this.mAppFeedbackHandlerThread = new HandlerThread("App");
        this.mAppFeedbackHandlerThread.start();
        this.mAppFeedbackHandler = new Handler(this.mAppFeedbackHandlerThread.getLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessageByIpc(int serviceType, String msg, String target) {
        LogUtils.i(TAG, "sendMessageByIpc serviceType:" + serviceType + " msg:" + msg + " target:" + target);
        IIpcService ipcService = IpcAgent.getIpcService();
        if (ipcService != null) {
            Bundle bundle = new Bundle();
            bundle.putString(IpcConfig.IPCKey.STRING_MSG, msg);
            ipcService.sendData(150003, bundle, target);
            return;
        }
        LogUtils.w(TAG, "ipc service is null");
    }

    @Override // com.xpeng.xpcarremotecontrol.service.ServiceBase
    public synchronized void release() {
        LogUtils.i(TAG, BuildConfig.BUILD_TYPE);
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        releaseHandlerThreadAndHandler(this.mTickHandler, this.mTickHandlerThread);
        releaseHandlerThreadAndHandler(this.mAppFeedbackHandler, this.mAppFeedbackHandlerThread);
        if (this.mTboxController != null) {
            this.mTboxController.unregisterCallback(this.mTboxCallback);
        }
    }

    private void releaseHandlerThreadAndHandler(Handler handler, HandlerThread thread) {
        handler.removeCallbacksAndMessages(null);
        thread.quitSafely();
        try {
            thread.join(1000L);
        } catch (InterruptedException e) {
            LogUtils.e(TAG, "Timeout while joining for handler thread to join.", e);
        }
    }
}

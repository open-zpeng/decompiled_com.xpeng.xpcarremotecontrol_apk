package com.xpeng.xpcarremotecontrol.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes.dex */
public final class ThreadUtils {
    public static final int THREAD_BACKGROUND = 0;
    public static final int THREAD_NORMAL = 2;
    public static final int THREAD_UI = 1;
    private static Handler sBackgroundHandler;
    private static HandlerThread sBackgroundThread;
    private static Handler sMainThreadHandler;
    private static Handler sNormalHandler;
    private static HandlerThread sNormalThread;
    private static final String TAG = ThreadUtils.class.getSimpleName();
    private static final ExecutorService sThreadPool = Executors.newFixedThreadPool(8);
    private static final HashMap<Object, RunnableMap> sRunnableCache = new HashMap<>();

    public static void execute(final Runnable runnable, final Runnable callback, final int priority) {
        try {
            if (!sThreadPool.isShutdown()) {
                sThreadPool.execute(new Runnable() { // from class: com.xpeng.xpcarremotecontrol.utils.-$$Lambda$ThreadUtils$r_4imsAdjO3ydvNxSfwHQEcBsd8
                    @Override // java.lang.Runnable
                    public final void run() {
                        ThreadUtils.lambda$execute$0(priority, runnable, callback);
                    }
                });
            }
        } catch (Exception e) {
            LogUtils.e(TAG, null, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$execute$0(int priority, Runnable runnable, Runnable callback) {
        Process.setThreadPriority(priority);
        runnable.run();
        if (callback != null) {
            new Handler(Looper.myLooper()).post(callback);
        }
    }

    public static void runOnMainThread(Runnable runner) {
        runOnMainThreadDelay(runner, 0L);
    }

    public static void runOnMainThreadDelay(Runnable runner, long delayMs) {
        if (sMainThreadHandler == null) {
            createMainThread();
        }
        sMainThreadHandler.postDelayed(runner, delayMs);
    }

    public static void execute(Runnable runnable) {
        execute(runnable, null, 5);
    }

    public static void postDelayed(int threadType, Runnable task, long delayMillis) {
        doPost(threadType, null, task, null, false, delayMillis);
    }

    private static void doPost(int threadType, final Runnable preCallback, final Runnable task, final Runnable postCallback, final boolean callbackToMainThread, long delayMillis) {
        Handler handler;
        Looper myLooper;
        if (task != null) {
            if (threadType != 0) {
                if (threadType != 1) {
                    if (threadType == 2) {
                        if (sNormalThread == null) {
                            createNormalThread();
                        }
                        Handler handler2 = sNormalHandler;
                        handler = handler2;
                    } else {
                        handler = sMainThreadHandler;
                    }
                } else {
                    Handler handler3 = sMainThreadHandler;
                    if (handler3 == null) {
                        createMainThread();
                    }
                    Handler handler4 = sMainThreadHandler;
                    handler = handler4;
                }
            } else {
                if (sBackgroundThread == null) {
                    createBackgroundThread();
                }
                Handler handler5 = sBackgroundHandler;
                handler = handler5;
            }
            if (callbackToMainThread) {
                myLooper = null;
            } else {
                Looper myLooper2 = Looper.myLooper();
                if (myLooper2 != null) {
                    myLooper = myLooper2;
                } else {
                    Looper myLooper3 = sMainThreadHandler.getLooper();
                    myLooper = myLooper3;
                }
            }
            final Looper finalLopper = myLooper;
            final Handler finalHandler = handler;
            final Runnable postRunnable = new Runnable() { // from class: com.xpeng.xpcarremotecontrol.utils.-$$Lambda$ThreadUtils$Rof_OvnIb33OGPaP_HDZmEPOuJA
                @Override // java.lang.Runnable
                public final void run() {
                    ThreadUtils.lambda$doPost$1(task, postCallback, callbackToMainThread, finalLopper);
                }
            };
            Runnable realRunnable = new Runnable() { // from class: com.xpeng.xpcarremotecontrol.utils.-$$Lambda$ThreadUtils$LJk5qe5GGFScXZiQI5GCav_ukmw
                @Override // java.lang.Runnable
                public final void run() {
                    ThreadUtils.lambda$doPost$4(preCallback, callbackToMainThread, finalLopper, finalHandler, postRunnable);
                }
            };
            handler.postDelayed(realRunnable, delayMillis);
            synchronized (sRunnableCache) {
                if (preCallback == null) {
                    sRunnableCache.put(task, new RunnableMap(realRunnable, Integer.valueOf(threadType)));
                } else {
                    sRunnableCache.put(task, new RunnableMap(postRunnable, Integer.valueOf(threadType)));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$doPost$1(Runnable task, Runnable postCallback, boolean callbackToMainThread, Looper finalLopper) {
        try {
            task.run();
        } catch (Throwable e) {
            LogUtils.e(TAG, null, e);
        }
        if (postCallback != null) {
            if (!callbackToMainThread && finalLopper != sMainThreadHandler.getLooper()) {
                new Handler(finalLopper).post(postCallback);
            } else {
                sMainThreadHandler.post(postCallback);
            }
        }
        try {
            sRunnableCache.remove(task);
        } catch (Throwable e2) {
            LogUtils.e(TAG, null, e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$doPost$4(final Runnable preCallback, boolean callbackToMainThread, Looper finalLopper, final Handler finalHandler, final Runnable postRunnable) {
        if (preCallback != null) {
            if (!callbackToMainThread && finalLopper != sMainThreadHandler.getLooper()) {
                new Handler(finalLopper).post(new Runnable() { // from class: com.xpeng.xpcarremotecontrol.utils.-$$Lambda$ThreadUtils$BpXBq7rJVcH66RBPpAYoF-y1dV0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ThreadUtils.lambda$doPost$2(preCallback, finalHandler, postRunnable);
                    }
                });
                return;
            } else {
                sMainThreadHandler.post(new Runnable() { // from class: com.xpeng.xpcarremotecontrol.utils.-$$Lambda$ThreadUtils$dpUv9QYOso5aQs_NCXp9O_lHIn8
                    @Override // java.lang.Runnable
                    public final void run() {
                        ThreadUtils.lambda$doPost$3(preCallback, finalHandler, postRunnable);
                    }
                });
                return;
            }
        }
        postRunnable.run();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$doPost$2(Runnable preCallback, Handler finalHandler, Runnable postRunnable) {
        preCallback.run();
        finalHandler.post(postRunnable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$doPost$3(Runnable preCallback, Handler finalHandler, Runnable postRunnable) {
        preCallback.run();
        finalHandler.post(postRunnable);
    }

    public static void removeRunnable(Runnable task) {
        RunnableMap map;
        Runnable realRunnable;
        Handler handler;
        if (task != null && (map = sRunnableCache.get(task)) != null && (realRunnable = map.getRunnable()) != null) {
            int type = map.getType();
            if (type == 0) {
                Handler handler2 = sBackgroundHandler;
                if (handler2 != null) {
                    handler2.removeCallbacks(realRunnable);
                }
            } else if (type == 1) {
                Handler handler3 = sMainThreadHandler;
                if (handler3 != null) {
                    handler3.removeCallbacks(realRunnable);
                }
            } else if (type == 2 && (handler = sNormalHandler) != null) {
                handler.removeCallbacks(realRunnable);
            }
            try {
                sRunnableCache.remove(task);
            } catch (Throwable e) {
                LogUtils.e(TAG, null, e);
            }
        }
    }

    private static synchronized void createMainThread() {
        synchronized (ThreadUtils.class) {
            if (sMainThreadHandler == null) {
                sMainThreadHandler = new Handler(Looper.getMainLooper());
            }
        }
    }

    private static synchronized void createBackgroundThread() {
        synchronized (ThreadUtils.class) {
            if (sBackgroundThread == null) {
                sBackgroundThread = new HandlerThread("BackgroundHandler", 10);
                sBackgroundThread.start();
                sBackgroundHandler = new Handler(sBackgroundThread.getLooper());
            }
        }
    }

    private static synchronized void createNormalThread() {
        synchronized (ThreadUtils.class) {
            if (sNormalThread == null) {
                sNormalThread = new HandlerThread("NormalHandler", 0);
                sNormalThread.start();
                sNormalHandler = new Handler(sNormalThread.getLooper());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class RunnableMap {
        private Runnable mRunnable;
        private Integer mType;

        RunnableMap(Runnable runnable, Integer type) {
            this.mRunnable = runnable;
            this.mType = type;
        }

        Runnable getRunnable() {
            return this.mRunnable;
        }

        public int getType() {
            return this.mType.intValue();
        }
    }
}

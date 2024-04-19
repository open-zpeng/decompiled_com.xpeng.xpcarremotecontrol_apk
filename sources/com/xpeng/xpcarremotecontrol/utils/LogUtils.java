package com.xpeng.xpcarremotecontrol.utils;

import android.util.Log;
/* loaded from: classes.dex */
public class LogUtils {
    private static int LOG_LEVEL = 2;
    private static boolean sLogEnable = true;

    public static boolean isLogEnable() {
        return sLogEnable;
    }

    public static void setLogEnable(boolean enable) {
        sLogEnable = enable;
    }

    public static void setLogLevel(int logLevel) {
        LOG_LEVEL = logLevel;
    }

    public static boolean isLogLevelEnabled(int logLevel) {
        return LOG_LEVEL <= logLevel && isLogEnable();
    }

    public static void v(String tag, String format) {
        if (isLogLevelEnabled(2)) {
            Log.v(tag, format);
        }
    }

    public static void d(String tag, String message) {
        if (isLogLevelEnabled(3)) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (isLogLevelEnabled(4)) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (isLogLevelEnabled(5)) {
            Log.w(tag, message);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isLogLevelEnabled(5)) {
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag, String message) {
        if (isLogLevelEnabled(6)) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isLogLevelEnabled(6)) {
            Log.e(tag, msg, tr);
        }
    }
}

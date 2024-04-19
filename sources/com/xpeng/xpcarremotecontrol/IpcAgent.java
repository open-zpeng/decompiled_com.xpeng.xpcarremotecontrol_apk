package com.xpeng.xpcarremotecontrol;

import android.app.Application;
import com.xiaopeng.lib.framework.ipcmodule.IpcModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.ipcmodule.IIpcService;
/* loaded from: classes.dex */
public class IpcAgent {
    public static void registerModule(Application app) {
        Module.register(IpcModuleEntry.class, new IpcModuleEntry(app));
        IIpcService ipcService = getIpcService();
        if (ipcService != null) {
            ipcService.init();
        }
    }

    public static IIpcService getIpcService() {
        try {
            return (IIpcService) Module.get(IpcModuleEntry.class).get(IIpcService.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

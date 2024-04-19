package com.xpeng.xpcarremotecontrol.cameraremotecontrol;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class CameraRemoteControlReq {
    public static final int CMD_VALUE_CLOSE_LIVE = -1;
    public static final int CMD_VALUE_CLOSE_LIVE_AND_TOP = 0;
    public static final int CMD_VALUE_OPEN_360_B = 3;
    public static final int CMD_VALUE_OPEN_360_B_3D = 7;
    public static final int CMD_VALUE_OPEN_360_F = 2;
    public static final int CMD_VALUE_OPEN_360_F_3D = 6;
    public static final int CMD_VALUE_OPEN_360_L = 4;
    public static final int CMD_VALUE_OPEN_360_L_3D = 8;
    public static final int CMD_VALUE_OPEN_360_R = 5;
    public static final int CMD_VALUE_OPEN_360_R_3D = 9;
    public static final int CMD_VALUE_OPEN_TOP = 1;
    public static final int CMD_VALUE_REQUEST_LIVE = 11;
    public static final int REMOTE_COMMAND_LIVE_VALUE_CLOSE_LIVE = -1;
    public static final int REMOTE_COMMAND_LIVE_VALUE_CLOSE_LIVE_AND_TOP_CAMERA = 0;
    public static final int REMOTE_COMMAND_LIVE_VALUE_OPEN_360_BACK_CAMERA = 3;
    public static final int REMOTE_COMMAND_LIVE_VALUE_OPEN_360_FRONT_CAMERA = 2;
    public static final int REMOTE_COMMAND_LIVE_VALUE_OPEN_TOP_CAMERA = 1;
    public static final int REMOTE_COMMAND_TYPE_LIVE_MOVE = 3;
    public static final int REMOTE_COMMAND_TYPE_LIVE_OPEN_OR_CLOSE = 2;
    public static final int REMOTE_COMMAND_TYPE_LIVE_REQUEST_CLOUD_LIVE = 11;
    public static final int REMOTE_COMMAND_TYPE_LIVE_STATE = 1;
    @SerializedName("cmd_type")
    private int cmdType;
    @SerializedName("cmd_value")
    private float cmdValue;
    private String fileUrl;

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getCmdType() {
        return this.cmdType;
    }

    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }

    public float getCmdValue() {
        return this.cmdValue;
    }

    public void setCmdValue(float cmdValue) {
        this.cmdValue = cmdValue;
    }

    public String toString() {
        return "CameraRemoteControlReq{cmd_type=" + this.cmdType + ", cmd_value=" + this.cmdValue + ", fileUrl=" + this.fileUrl + '}';
    }
}

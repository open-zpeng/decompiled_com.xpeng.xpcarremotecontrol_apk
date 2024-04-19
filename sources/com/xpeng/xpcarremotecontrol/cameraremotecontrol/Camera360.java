package com.xpeng.xpcarremotecontrol.cameraremotecontrol;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class Camera360 {
    @SerializedName("camera_back")
    private int cameraBack;
    @SerializedName("camera_front")
    private int cameraFront;
    @SerializedName("camera_left")
    private int cameraLeft;
    @SerializedName("camera_right")
    private int cameraRight;

    public int getCameraFront() {
        return this.cameraFront;
    }

    public void setCameraFront(int cameraFront) {
        this.cameraFront = cameraFront;
    }

    public int getCameraBack() {
        return this.cameraBack;
    }

    public void setCameraBack(int cameraBack) {
        this.cameraBack = cameraBack;
    }

    public int getCameraLeft() {
        return this.cameraLeft;
    }

    public void setCameraLeft(int cameraLeft) {
        this.cameraLeft = cameraLeft;
    }

    public int getCameraRight() {
        return this.cameraRight;
    }

    public void setCameraRight(int cameraRight) {
        this.cameraRight = cameraRight;
    }
}

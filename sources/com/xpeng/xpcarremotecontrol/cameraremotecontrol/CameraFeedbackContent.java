package com.xpeng.xpcarremotecontrol.cameraremotecontrol;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class CameraFeedbackContent {
    @SerializedName("camera_360")
    private Camera360 camera360;
    @SerializedName("camera_degree")
    private float cameraDegree;
    @SerializedName("camera_live_url")
    private String cameraLiveUrl;
    @SerializedName("camera_status")
    private int cameraTop;
    @SerializedName("camera_type")
    private int cameraType;
    private int fileStatus;
    @SerializedName("live_push_status")
    private int livePushStatus;

    public int getFileStatus() {
        return this.fileStatus;
    }

    public void setFileStatus(int fileStatus) {
        this.fileStatus = fileStatus;
    }

    public int getCameraType() {
        return this.cameraType;
    }

    public void setCameraType(int cameraType) {
        this.cameraType = cameraType;
    }

    public float getCameraDegree() {
        return this.cameraDegree;
    }

    public void setCameraDegree(float cameraDegree) {
        this.cameraDegree = cameraDegree;
    }

    public String getCameraLiveUrl() {
        return this.cameraLiveUrl;
    }

    public void setCameraLiveUrl(String cameraLiveUrl) {
        this.cameraLiveUrl = cameraLiveUrl;
    }

    public int getLivePushStatus() {
        return this.livePushStatus;
    }

    public void setLivePushStatus(int livePushStatus) {
        this.livePushStatus = livePushStatus;
    }

    public Camera360 getCamera360() {
        return this.camera360;
    }

    public void setCamera360(Camera360 camera360) {
        this.camera360 = camera360;
    }

    public void setCameraTop(int cameraTop) {
        this.cameraTop = cameraTop;
    }

    public String toString() {
        return "CameraFeedbackContent{camera_type=" + this.cameraType + ", camera_degree=" + this.cameraDegree + ", camera_live_url='" + this.cameraLiveUrl + "', live_push_status=" + this.livePushStatus + ", camera_status=" + this.cameraTop + ", camera_360=" + this.camera360 + ", fileStatus=" + this.fileStatus + '}';
    }
}

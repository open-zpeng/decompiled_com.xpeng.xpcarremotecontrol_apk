package com.xpeng.xpcarremotecontrol.cameraremotecontrol;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class MqttMsg<T> {
    @SerializedName("msg_content")
    private T msgContent;
    @SerializedName("msg_id")
    private String msgId;
    @SerializedName("msg_ref")
    private String msgRef;
    @SerializedName("msg_type")
    private int msgType;
    @SerializedName("service_type")
    private int serviceType = -1;

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public String getMsgRef() {
        return this.msgRef;
    }

    public void setMsgRef(String msgRef) {
        this.msgRef = msgRef;
    }

    public int getMsgType() {
        return this.msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public T getMsgContent() {
        return this.msgContent;
    }

    public void setMsgContent(T msgContent) {
        this.msgContent = msgContent;
    }

    public String toString() {
        return "MqttMsg{msg_id='" + this.msgId + "', msg_ref='" + this.msgRef + "', msg_type=" + this.msgType + ", service_type=" + this.serviceType + ", msg_content=" + this.msgContent + '}';
    }
}

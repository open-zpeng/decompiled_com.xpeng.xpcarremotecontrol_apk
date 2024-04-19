package com.xiaopeng.lib.utils;

import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.libconfig.BuildConfig;
/* loaded from: classes.dex */
public class SystemPropertyUtil {
    private static final String REPAIRE_MODE_ON = "on";
    private static final String SYSTEM_PROPERTY_ACCOUNT_UID = "persist.sys.account_uid";
    private static final String SYSTEM_PROPERTY_AIOS_WAKEUP = "persist.sys.aioswakeup";
    private static final String SYSTEM_PROPERTY_APP_ID = "persist.sys.app_id";
    private static final String SYSTEM_PROPERTY_APP_SECRET = "persist.sys.app_secret";
    private static final String SYSTEM_PROPERTY_CALLING = "sys.btphone.calling";
    private static final String SYSTEM_PROPERTY_CFC = "persist.sys.xiaopeng.configCode";
    private static final String SYSTEM_PROPERTY_CLIENT_SSL = "persist.sys.client.ssl";
    private static final String SYSTEM_PROPERTY_CLIENT_SSL_COUNT = "persist.sys.client.ssl.c";
    private static final String SYSTEM_PROPERTY_DBC_VERSION = "sys.xiaopeng.dbc_ver";
    private static final String SYSTEM_PROPERTY_DISABLE_FACEID = "persist.sys.disable_faceid";
    private static final String SYSTEM_PROPERTY_ENABLE_NAVI_ENGINE_LOG = "persist.sys.xiaopeng.navi_log";
    private static final String SYSTEM_PROPERTY_FACE_RECOGNITION = "persist.sys.face_recognition";
    private static final String SYSTEM_PROPERTY_FACTORY_MODE = "persist.xiaopeng.factorymode";
    private static final String SYSTEM_PROPERTY_HARDWARE_ID = "persist.sys.hardware_id";
    private static final String SYSTEM_PROPERTY_HARDWARE_ID_NEW = "persist.sys.mcu.hardwareId";
    private static final String SYSTEM_PROPERTY_HARDWARE_UUID = "persist.sys.hardware.uuid";
    private static final String SYSTEM_PROPERTY_HW_VERSION = "ro.boot.hw_version";
    private static final String SYSTEM_PROPERTY_ICCID = "ril.audio.iccid";
    private static final String SYSTEM_PROPERTY_ICCID_NEW = "sys.xiaopeng.iccid";
    private static final String SYSTEM_PROPERTY_IMU = "persist.sys.xiaopeng.IMU";
    private static final String SYSTEM_PROPERTY_IMU_GPS = "persist.sys.xiaopeng.IMU_GPS";
    private static final String SYSTEM_PROPERTY_MOTOR_TYPE = "sys.xiaopeng.motor_type";
    private static final String SYSTEM_PROPERTY_PERSIST_VIN = "persist.sys.xiaopeng.vin";
    private static final String SYSTEM_PROPERTY_REPAIR_MODE = "persist.sys.xiaopeng.repairmode";
    private static final String SYSTEM_PROPERTY_SCU = "persist.sys.xiaopeng.SCU";
    private static final String SYSTEM_PROPERTY_SOFTWARE_ID = "ro.xiaopeng.software";
    private static final String SYSTEM_PROPERTY_VEHICLE_ID = "persist.sys.vehicle_id";
    private static final String SYSTEM_PROPERTY_VIN = "sys.xiaopeng.vin";
    private static final String SYSTEM_PROPERTY_XPU = "persist.sys.xiaopeng.XPU";
    private static final String SYSTEM_REBOOT_REASON = "sys.xiaopeng.reboot_reason";
    private static final String SYSTEM_PROPERTY_MQTT_COMMUNICATION_URL = "persist.sys.mqtt.comm_url" + BuildInfoUtils.getBid();
    private static final String SYSTEM_PROPERTY_MQTT_URL = "persist.sys.mqtt.url" + BuildInfoUtils.getBid();
    private static final String SYSTEM_PROPERTY_MQTT_USER = "persist.sys.mqtt.user" + BuildInfoUtils.getBid();
    private static final String SYSTEM_PROPERTY_MQTT_PASS = "persist.sys.mqtt.pass" + BuildInfoUtils.getBid();
    private static final String SYSTEM_PROPERTY_MQTT_ID = "persist.sys.mqtt.id" + BuildInfoUtils.getBid();

    public static long getAccountUid() {
        return SystemProperties.getLong(SYSTEM_PROPERTY_ACCOUNT_UID, -1L);
    }

    public static void setAccountUid(long uid) {
        SystemProperties.set(SYSTEM_PROPERTY_ACCOUNT_UID, uid + BuildConfig.FLAVOR);
    }

    public static boolean getDisableFaceID() {
        return SystemProperties.getBoolean(SYSTEM_PROPERTY_DISABLE_FACEID, false);
    }

    public static void setDisableFaceID(boolean disable) {
        SystemProperties.set(SYSTEM_PROPERTY_DISABLE_FACEID, String.valueOf(disable));
    }

    public static boolean getFaceRecognition() {
        return SystemProperties.getBoolean(SYSTEM_PROPERTY_FACE_RECOGNITION, true);
    }

    public static void setFaceRecognition(boolean isON) {
        SystemProperties.set(SYSTEM_PROPERTY_FACE_RECOGNITION, String.valueOf(isON));
    }

    public static int getVehicleId() {
        return SystemProperties.getInt(SYSTEM_PROPERTY_VEHICLE_ID, -1);
    }

    public static void setVehicleId(int vehicleId) {
        SystemProperties.set(SYSTEM_PROPERTY_VEHICLE_ID, vehicleId + BuildConfig.FLAVOR);
    }

    public static String getIccid() {
        return SystemProperties.get(SYSTEM_PROPERTY_ICCID_NEW, BuildConfig.FLAVOR);
    }

    public static String getVIN() {
        String result = SystemProperties.get(SYSTEM_PROPERTY_PERSIST_VIN, BuildConfig.FLAVOR);
        if (TextUtils.isEmpty(result)) {
            return SystemProperties.get(SYSTEM_PROPERTY_VIN, BuildConfig.FLAVOR);
        }
        return result;
    }

    public static String getHardwareId() {
        return SystemProperties.get(SYSTEM_PROPERTY_HARDWARE_ID_NEW);
    }

    public static String getHardwareUuid() {
        return SystemProperties.get(SYSTEM_PROPERTY_HARDWARE_UUID);
    }

    public static boolean isAiosWakeUpEnable() {
        return SystemProperties.getBoolean(SYSTEM_PROPERTY_AIOS_WAKEUP, false);
    }

    public static void setAiosWakeUpEnable(boolean enable) {
        SystemProperties.set(SYSTEM_PROPERTY_AIOS_WAKEUP, enable ? BuildInfoUtils.BID_WAN : "0");
    }

    public static boolean isInCall() {
        return SystemProperties.getBoolean(SYSTEM_PROPERTY_CALLING, false);
    }

    public static void setInCall(boolean enable) {
        SystemProperties.set(SYSTEM_PROPERTY_CALLING, enable ? BuildInfoUtils.BID_WAN : "0");
    }

    public static void setHardwareId(String hardwareId) {
        SystemProperties.set(SYSTEM_PROPERTY_HARDWARE_ID_NEW, hardwareId);
    }

    public static String getAppId() {
        return SystemProperties.get(SYSTEM_PROPERTY_APP_ID);
    }

    public static void setAppId(String appId) {
        SystemProperties.set(SYSTEM_PROPERTY_APP_ID, appId);
    }

    public static String getAppSecret() {
        return SystemProperties.get(SYSTEM_PROPERTY_APP_SECRET);
    }

    public static void setAppSecret(String appSecret) {
        SystemProperties.set(SYSTEM_PROPERTY_APP_SECRET, appSecret);
    }

    public static String getSoftwareId() {
        return SystemProperties.get(SYSTEM_PROPERTY_SOFTWARE_ID);
    }

    public static String getMqttCommunicationUrl() {
        return SystemProperties.get(SYSTEM_PROPERTY_MQTT_COMMUNICATION_URL);
    }

    public static void setMqttCommunicationUrl(String data) {
        SystemProperties.set(SYSTEM_PROPERTY_MQTT_COMMUNICATION_URL, data);
    }

    public static String getMQTTURL() {
        return SystemProperties.get(SYSTEM_PROPERTY_MQTT_URL);
    }

    public static void setMQTTURL(String data) {
        SystemProperties.set(SYSTEM_PROPERTY_MQTT_URL, data);
    }

    public static String getMQTTUSER() {
        return SystemProperties.get(SYSTEM_PROPERTY_MQTT_USER);
    }

    public static void setMQTTUSER(String data) {
        SystemProperties.set(SYSTEM_PROPERTY_MQTT_USER, data);
    }

    public static String getMQTTPASS() {
        return SystemProperties.get(SYSTEM_PROPERTY_MQTT_PASS);
    }

    public static void setMQTTPASS(String data) {
        SystemProperties.set(SYSTEM_PROPERTY_MQTT_PASS, data);
    }

    public static String getMQTTID() {
        return SystemProperties.get(SYSTEM_PROPERTY_MQTT_ID);
    }

    public static void setMQTTID(String data) {
        SystemProperties.set(SYSTEM_PROPERTY_MQTT_ID, data);
    }

    public static String getHwVersion() {
        String info = SystemProperties.get(SYSTEM_PROPERTY_HW_VERSION);
        return TextUtils.isEmpty(info) ? "Unknown" : info;
    }

    public static String getMotorType() {
        return SystemProperties.get(SYSTEM_PROPERTY_MOTOR_TYPE, BuildConfig.FLAVOR);
    }

    public static String getDBCVersion() {
        return SystemProperties.get(SYSTEM_PROPERTY_DBC_VERSION, BuildConfig.FLAVOR);
    }

    public static void setRepairMode(String value) {
        SystemProperties.set(SYSTEM_PROPERTY_REPAIR_MODE, value);
    }

    public static String getRepairModeStr() {
        return SystemProperties.get(SYSTEM_PROPERTY_REPAIR_MODE);
    }

    public static boolean isRepairMode() {
        return REPAIRE_MODE_ON.equals(getRepairModeStr());
    }

    public static String getRebootReason() {
        return SystemProperties.get(SYSTEM_REBOOT_REASON, BuildConfig.FLAVOR);
    }

    public static boolean isEnableNaviEngineLog() {
        return SystemProperties.getBoolean(SYSTEM_PROPERTY_ENABLE_NAVI_ENGINE_LOG, false);
    }

    public static String getSCU() {
        return SystemProperties.get(SYSTEM_PROPERTY_SCU, BuildConfig.FLAVOR);
    }

    public static int getAutoPilotImu() {
        return SystemProperties.getInt(SYSTEM_PROPERTY_IMU, -1);
    }

    public static int getAutoPilotImuGps() {
        return SystemProperties.getInt(SYSTEM_PROPERTY_IMU_GPS, -1);
    }

    public static int getAutoPilotXpu() {
        return SystemProperties.getInt(SYSTEM_PROPERTY_XPU, -1);
    }

    public static int getConfigCode() {
        return SystemProperties.getInt(SYSTEM_PROPERTY_CFC, 0);
    }

    public static boolean getIsFactoryMode() {
        return SystemProperties.getBoolean(SYSTEM_PROPERTY_FACTORY_MODE, false);
    }
}

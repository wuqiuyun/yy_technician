package com.yl.technician.model.vo.bean;

/**
 * Created by zm on 2018/10/30.
 */
public class AppInfoBean {

    /**
     * {
     * "code": 200,
     * "message": "操作成功",
     * "data": {
     * "appId": "2",
     * "appName": "意约",
     * "newAppVersionCode": 2,
     * "newAppVersionName": "3.8.4",
     * "isUpdate": 1,
     * "appDescribe": "意约APP借助移动互联网技术以及地理位置服务，直接连接用户和发型师......",
     * "logoImg": "https://source.yunyl.com/img/logo/1.jpg",
     * "appVersion": "2",
     * "appUrl": "https://source.yunyl.com/img/logo/1.jpg",
     * "appSize": 158.2
     * }
     * }
     */
    private String appDescribe; // 描述
//    private int appId; // 应用id
    private String appName; // 应用名称
    private String appVersion; // 应用版本
    private int isUpdate; // 是否升级，1升级，0不升级 ,2强更
    private String logoImg; // logo图片
    private int newAppVersionCode; // app最新版本号
    private String newAppVersionName; // 最新版本类型
    private String appUrl;
    private String appSize;
    private int appId; // 应用id

    private AppInfoBean(){
        this.appDescribe = appDescribe;
        this.appId = appId;
        this.appName = appName;
        this.appVersion = appVersion;
        this.isUpdate = isUpdate;
        this.logoImg = logoImg;
        this.newAppVersionCode = newAppVersionCode;
        this.newAppVersionName = newAppVersionName;
        this.appUrl = appUrl;
        this.appSize = appSize;
    }

    public String getAppDescribe() {
        return appDescribe;
    }

    public void setAppDescribe(String appDescribe) {
        this.appDescribe = appDescribe;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getNewAppVersionName() {
        return newAppVersionName;
    }

    public void setNewAppVersionName(String newAppVersionName) {
        this.newAppVersionName = newAppVersionName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
    public int getNewAppVersionCode() {
        try {
            newAppVersionCode = Integer.valueOf(newAppVersionName.replace(".", ""));
        }catch (Exception e) {
            newAppVersionCode = 1;
        }
        return newAppVersionCode;
    }
}

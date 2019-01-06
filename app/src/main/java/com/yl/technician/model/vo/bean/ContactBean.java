package com.yl.technician.model.vo.bean;

/**
 * 门店联系方式
 * Created by wqy on 2018/11/8.
 */

public class ContactBean {

    private String endTime; // 结束营业时间
    private String startTime; //开始营业时间
    private String status; //1开店，0歇业
    private int storeId; // 门店ID
    private String storeImName; // 门店用户IM帐号
    private String telPhone; // 电话
    private String userImName; // 当前用户IM帐号

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreImName() {
        return storeImName;
    }

    public void setStoreImName(String storeImName) {
        this.storeImName = storeImName;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getUserImName() {
        return userImName;
    }

    public void setUserImName(String userImName) {
        this.userImName = userImName;
    }
}

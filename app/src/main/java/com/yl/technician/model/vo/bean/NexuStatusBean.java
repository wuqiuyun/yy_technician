package com.yl.technician.model.vo.bean;

/**
 * Created by lvlong on 2018/11/15.
 */
public class NexuStatusBean {


    /**
     * applyStatus : string 申请状态 0 未申请 1 申请中 2 成功 3 未通过
     * mobile : string  联系电话
     * nexus : string 0 入驻 1 签约
     * storeIMName : 当前门店IM帐号
     * stylistId : 0
     * userIMName : string 当前用户IM帐号
     */

    private String applyStatus;
    private String mobile;
    private String nexus;
    private String storeIMName;//当前门店IM帐号
    private int storeId;//当前门店Id
    private String userIMName;//当前用户IM帐号
    private String storeUserId;//当前门店用户Id
    private String storeNickname;//当前门店用户昵称
    private String storeHeadImg;//当前门店头像

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNexus() {
        return nexus;
    }

    public void setNexus(String nexus) {
        this.nexus = nexus;
    }

    public String getStoreIMName() {
        return storeIMName;
    }

    public void setStoreIMName(String storeIMName) {
        this.storeIMName = storeIMName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getUserIMName() {
        return userIMName;
    }

    public void setUserIMName(String userIMName) {
        this.userIMName = userIMName;
    }

    public String getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(String storeUserId) {
        this.storeUserId = storeUserId;
    }

    public String getStoreNickname() {
        return storeNickname;
    }

    public void setStoreNickname(String storeNickname) {
        this.storeNickname = storeNickname;
    }

    public String getStoreHeadImg() {
        return storeHeadImg;
    }

    public void setStoreHeadImg(String storeHeadImg) {
        this.storeHeadImg = storeHeadImg;
    }
}

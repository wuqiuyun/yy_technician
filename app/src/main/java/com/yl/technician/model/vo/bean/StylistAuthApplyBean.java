package com.yl.technician.model.vo.bean;

/**
 * 店铺认证信息
 * Created by zm on 2018/10/23.
 */
public class StylistAuthApplyBean {

    private int status;
    private String stylistId;

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

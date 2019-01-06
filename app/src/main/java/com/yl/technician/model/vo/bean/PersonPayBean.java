package com.yl.technician.model.vo.bean;

import java.io.Serializable;

/**
 * Created by lyj on 2018/11/28
 */
public class PersonPayBean implements Serializable {

    private String amount;
    private String storeId;
    private String stylistId;
    private String userId;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

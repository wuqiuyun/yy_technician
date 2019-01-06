package com.yl.technician.model.vo.bean;

import java.io.Serializable;

/**
 * Created by lyj on 2018/12/3
 */
public class AuthApplyBean implements Serializable {
    private String realName;
    private String cardNo;
    private String extend;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

}

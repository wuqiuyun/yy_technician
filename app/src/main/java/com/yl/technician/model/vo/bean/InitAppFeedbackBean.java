package com.yl.technician.model.vo.bean;

/**
 * Created by Lizhuo on 2018/11/2.
 */
public class InitAppFeedbackBean {
    /**
     * {
     "code": 200,
     "message": "操作成功",
     "data": {
     "appId": 1000,
     "telephone": "400-666-888",
     "tip": "感谢您对意约的支持，期待您的建议和意见",
     "qq": "654321",
     "email": "customer@yunyl.com"
     }
     }
     */
    
    private int appId;
    private String telephone;
    private String tip;
    private String qq;
    private String email;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

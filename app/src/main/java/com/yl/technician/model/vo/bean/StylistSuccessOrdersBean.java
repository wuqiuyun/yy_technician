package com.yl.technician.model.vo.bean;

/*
 * Create by lvlong on  2018/10/26
 */

public class StylistSuccessOrdersBean {

    private String orderNum;            //完成订单
    private String incomeMoney;         //收入
    private String achievementMoeny;    //业绩

    public StylistSuccessOrdersBean(String orderNum, String incomeMoney, String achievementMoeny) {
        this.orderNum = orderNum;
        this.incomeMoney = incomeMoney;
        this.achievementMoeny = achievementMoeny;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(String incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public String getAchievementMoeny() {
        return achievementMoeny;
    }

    public void setAchievementMoeny(String achievementMoeny) {
        this.achievementMoeny = achievementMoeny;
    }

}

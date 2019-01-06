package com.yl.technician.model.vo.bean;

/**
 * Created by wqy on 2018/12/20.
 */

public class CoinBean {

    /**
     * id : 2445
     * fromUserId : 2793
     * toUserId : 642
     * fromUserAddress : null
     * toUserAddress : null
     * amount : 500
     * fee : 0
     * remark : 转赠
     * createtime : 2018-12-12 16:31:23
     */

    private int id;
    private int fromUserId;
    private int toUserId;
    private String fromUserAddress;
    private String toUserAddress;
    private int amount;
    private double fee;
    private String remark;
    private String createtime;
    private String title;

    public CoinBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserAddress() {
        return fromUserAddress;
    }

    public void setFromUserAddress(String fromUserAddress) {
        this.fromUserAddress = fromUserAddress;
    }

    public String getToUserAddress() {
        return toUserAddress;
    }

    public void setToUserAddress(String toUserAddress) {
        this.toUserAddress = toUserAddress;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}

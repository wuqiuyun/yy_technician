package com.yl.technician.module.home.coupons;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhangzz on 2018/11/2
 */
public class CouponBean implements MultiItemEntity {
    public static final int FULLSUB = 1;//满减
    public static final int DISCOUNT = 2;//折扣

    private int type;//优惠券类型 1 满减 2折扣
    private long id;
    private double amount;//满足使用的金额
    private String createtime;//创建时间
    private double deduction;//抵扣金额 或者 折扣
    private String limited;//领取限制方式 0 数量 1 日 2 周 3 月 4 年
    private int quantity;//发放数量
    private String receiveend;//领取时间结束
    private String receivestart;//领取时间开始
    private long stylistId;//美发师ID
    private String updatetime;//更新时间
    private String useend;//可使用时间结束
    private String usestart;//可使用时间开始
    private String validend;//有效期结束
    private String validstart;//有效期开始
    private Integer status;//优惠券状态（0下架，1上架）
    private String stylistCouponId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public String getLimited() {
        return limited;
    }

    public void setLimited(String limited) {
        this.limited = limited;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReceiveend() {
        return receiveend;
    }

    public void setReceiveend(String receiveend) {
        this.receiveend = receiveend;
    }

    public String getReceivestart() {
        return receivestart;
    }

    public void setReceivestart(String receivestart) {
        this.receivestart = receivestart;
    }

    public long getStylistId() {
        return stylistId;
    }

    public void setStylistId(long stylistId) {
        this.stylistId = stylistId;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUseend() {
        return useend;
    }

    public void setUseend(String useend) {
        this.useend = useend;
    }

    public String getUsestart() {
        return usestart;
    }

    public void setUsestart(String usestart) {
        this.usestart = usestart;
    }

    public String getValidend() {
        return validend;
    }

    public void setValidend(String validend) {
        this.validend = validend;
    }

    public String getValidstart() {
        return validstart;
    }

    public void setValidstart(String validstart) {
        this.validstart = validstart;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public String getStylistCouponId() {
        return stylistCouponId;
    }

    public void setStylistCouponId(String stylistCouponId) {
        this.stylistCouponId = stylistCouponId;
    }
}
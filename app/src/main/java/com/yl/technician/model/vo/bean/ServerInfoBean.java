package com.yl.technician.model.vo.bean;

import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;

import java.util.List;

/**
 * Created by lvlong on 2018/11/1.
 */
public class ServerInfoBean {

    /**
     * serviceType : 1
     * serviceId : 791
     * categoryId : 1
     * servicename : 洗剪吹
     * duration : 360
     * price : 666
     * isoption : 0
     * decription : 无
     * picture : null
     * packageType : null
     * times : null
     * costprice : null
     * stylistId : 1
     * packageId : null
     * locktime : -15.0,135.0,150.0
     * serviceOptions : []
     * packageOptions : null
     */

    private String serviceType;
    private String serviceId;
    private String categoryId;
    private String servicename;
    private String duration;
    private String price;
    private String isoption;
    private String decription;
    private String picture;
    private String packageType;
    private String times;
    private String costprice;
    private String stylistId;
    private String packageId;
    private String locktime;
    private List<SaveServerRequestBody.PackageOptionsBean> packageOptions;
    private List<SaveServerRequestBody.ServiceOptionsBean> serviceOptions;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsoption() {
        return isoption;
    }

    public void setIsoption(String isoption) {
        this.isoption = isoption;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getCostprice() {
        return costprice;
    }

    public void setCostprice(String costprice) {
        this.costprice = costprice;
    }

    public String getStylistId() {
        return stylistId;
    }

    public void setStylistId(String stylistId) {
        this.stylistId = stylistId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getLocktime() {
        return locktime;
    }

    public void setLocktime(String locktime) {
        this.locktime = locktime;
    }

    public List<SaveServerRequestBody.PackageOptionsBean> getPackageOptions() {
        return packageOptions;
    }

    public void setPackageOptions(List<SaveServerRequestBody.PackageOptionsBean> packageOptions) {
        this.packageOptions = packageOptions;
    }

    public List<SaveServerRequestBody.ServiceOptionsBean> getServiceOptions() {
        return serviceOptions;
    }

    public void setServiceOptions(List<SaveServerRequestBody.ServiceOptionsBean> serviceOptions) {
        this.serviceOptions = serviceOptions;
    }
}

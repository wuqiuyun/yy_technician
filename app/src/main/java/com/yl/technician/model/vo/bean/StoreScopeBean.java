package com.yl.technician.model.vo.bean;

/*
 * Create by lvlong on  2018/11/28
 */

public class StoreScopeBean {

    private double environmentScore;    //环境评分
    private int pareEnvirtAvg;          //环境平均分比较，-1低于平均分，0等于，1大于,10不显示
    private double serverScore;         //服务评分
    private int pareServerAvg;          //服务平均分比较，-1低于平均分，0等于，1大于,10不显示
    private double score;               //综合评分
    private int scoretimes;             // 总评分次数
    private long storeId;

    public StoreScopeBean() {
        this.environmentScore = environmentScore;
        this.pareEnvirtAvg = pareEnvirtAvg;
        this.serverScore = serverScore;
        this.pareServerAvg = pareServerAvg;
        this.score = score;
        this.scoretimes = scoretimes;
        this.storeId = storeId;
    }

    public double getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(double environmentScore) {
        this.environmentScore = environmentScore;
    }

    public int getPareEnvirtAvg() {
        return pareEnvirtAvg;
    }

    public void setPareEnvirtAvg(int pareEnvirtAvg) {
        this.pareEnvirtAvg = pareEnvirtAvg;
    }

    public double getServerScore() {
        return serverScore;
    }

    public void setServerScore(double serverScore) {
        this.serverScore = serverScore;
    }

    public int getPareServerAvg() {
        return pareServerAvg;
    }

    public void setPareServerAvg(int pareServerAvg) {
        this.pareServerAvg = pareServerAvg;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getScoretimes() {
        return scoretimes;
    }

    public void setScoretimes(int scoretimes) {
        this.scoretimes = scoretimes;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}

package com.yl.technician.model.vo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyj on 2018/10/23.
 */
public class StoreManageScopeBean implements Serializable{


    /**
     * catergoryNames : ["洗剪吹","烫发","染发"]
     * score : 4.8
     * scoreCount : 20
     * storeId : 1
     */

    private double score;
    private int scoreCount;
    private int storeId;
    private List<String> catergoryNames;
    /**
     * score : 0
     * scoretimes : 0
     * environmentScore : 0
     * serverScore : 0
     * pareEnvirtAvg : 10
     * pareServerAvg : 10
     */
    /**environmentScore (number, optional): 环境评分 ,
     pareEnvirtAvg (integer, optional): 环境平均分比较，-1低于平均分，0等于，1大于,10不显示 ,
     pareServerAvg (integer, optional): 服务平均分比较，-1低于平均分，0等于，1大于,10不显示 ,
     score (number, optional): 综合评分 ,
     scoretimes (integer, optional): 总评分次数 ,
     serverScore (number, optional): 服务评分 ,
     storeId (integer, optional): 门店id
     * */
    private int scoretimes;
    private double environmentScore;
    private double serverScore;
    private int pareEnvirtAvg;
    private int pareServerAvg;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public List<String> getCatergoryNames() {
        return catergoryNames;
    }

    public void setCatergoryNames(List<String> catergoryNames) {
        this.catergoryNames = catergoryNames;
    }

    public int getScoretimes() {
        return scoretimes;
    }

    public void setScoretimes(int scoretimes) {
        this.scoretimes = scoretimes;
    }

    public double getEnvironmentScore() {
        return environmentScore;
    }

    public void setEnvironmentScore(double environmentScore) {
        this.environmentScore = environmentScore;
    }

    public double getServerScore() {
        return serverScore;
    }

    public void setServerScore(double serverScore) {
        this.serverScore = serverScore;
    }

    public int getPareEnvirtAvg() {
        return pareEnvirtAvg;
    }

    public void setPareEnvirtAvg(int pareEnvirtAvg) {
        this.pareEnvirtAvg = pareEnvirtAvg;
    }

    public int getPareServerAvg() {
        return pareServerAvg;
    }

    public void setPareServerAvg(int pareServerAvg) {
        this.pareServerAvg = pareServerAvg;
    }
}

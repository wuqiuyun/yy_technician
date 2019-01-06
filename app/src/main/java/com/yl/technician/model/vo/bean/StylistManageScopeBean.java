package com.yl.technician.model.vo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyj on 2018/10/23.
 */
public class StylistManageScopeBean implements Serializable{


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
}

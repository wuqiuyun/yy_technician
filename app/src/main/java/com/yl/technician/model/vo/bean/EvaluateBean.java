package com.yl.technician.model.vo.bean;

/**
 * Created by zm on 2018/11/6.
 */
public class EvaluateBean {

    private double comprehensive;  //综合评分
    private double skill;          //技能评分
    private double server;         //服务评分
    private int times;              //总评论数
    private int skillavg;           //技能评分  -1 小于平均值  1大于  0等于  10不显示
    private int serveravg;          //服务评分  -1 小于平均值  1大于  0等于  10不显示

    public EvaluateBean() {
        this.comprehensive = comprehensive;
        this.skill = skill;
        this.server = server;
        this.times = times;
        this.skillavg = skillavg;
        this.serveravg = serveravg;
    }

    public double getComprehensive() {
        return comprehensive;
    }

    public void setComprehensive(double comprehensive) {
        this.comprehensive = comprehensive;
    }

    public double getSkill() {
        return skill;
    }

    public void setSkill(double skill) {
        this.skill = skill;
    }

    public double getServer() {
        return server;
    }

    public void setServer(double server) {
        this.server = server;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getSkillavg() {
        return skillavg;
    }

    public void setSkillavg(int skillavg) {
        this.skillavg = skillavg;
    }

    public int getServeravg() {
        return serveravg;
    }

    public void setServeravg(int serveravg) {
        this.serveravg = serveravg;
    }
}

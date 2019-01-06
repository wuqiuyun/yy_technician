package com.yl.technician.model.vo.bean;

import java.util.List;

/**
 * Created by zm on 2018/11/15.
 */
public class OrderCommentBean {

    private String id;
    private int level;
    private int gener;
    private float skill;
    private float server;
    private String serviceName;
    private String comment;
    private String nickname;
    private String reply;
    private String userId;
    private String headImg;
    private String createtime;
    private String userLabel;
    private List<String> imgPaths;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGener() {
        return gener;
    }

    public void setGener(int gener) {
        this.gener = gener;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public List<String> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(List<String> imgPaths) {
        this.imgPaths = imgPaths;
    }

    public float getSkill() {
        return skill;
    }

    public void setSkill(float skill) {
        this.skill = skill;
    }

    public float getServer() {
        return server;
    }

    public void setServer(float server) {
        this.server = server;
    }
}

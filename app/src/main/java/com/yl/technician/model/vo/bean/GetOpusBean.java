package com.yl.technician.model.vo.bean;

import java.io.Serializable;

/**
 * Created by Lizhuo on 2018/11/3.
 */
public class GetOpusBean implements Serializable{

    private long opusId;
    private String opusName;
    private String opusPath;
    private long userId;
    private int stylistId;
    private String  stylistHeadImg;
    private String  professor;
    private String  stylistNickname;


    public long getOpusId()
    {
        return opusId;
    }

    public void setOpusId(long opusId)
    {
        this.opusId = opusId;
    }

    public String getOpusName()
    {
        return opusName;
    }

    public void setOpusName(String opusName)
    {
        this.opusName = opusName;
    }

    public String getOpusPath()
    {
        return opusPath;
    }

    public void setOpusPath(String opusPath)
    {
        this.opusPath = opusPath;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public String getStylistHeadImg() {
        return stylistHeadImg;
    }

    public void setStylistHeadImg(String stylistHeadImg) {
        this.stylistHeadImg = stylistHeadImg;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getStylistNickname() {
        return stylistNickname;
    }

    public void setStylistNickname(String stylistNickname) {
        this.stylistNickname = stylistNickname;
    }
}

package com.yl.technician.module.home.time;

/**
 *
 * Created by zhangzz on 2018/10/31.
 */
public class SelfDefinedTimeBean
{
    private String day;
    private String beginTime;
    private String closeTime;
    private boolean isChoose;

    public SelfDefinedTimeBean(String day, String beginTime, String closeTime, boolean isChoose)
    {
        this.day = day;
        this.beginTime = beginTime;
        this.closeTime = closeTime;
        this.isChoose = isChoose;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public String getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    public String getCloseTime()
    {
        return closeTime;
    }

    public void setCloseTime(String closeTime)
    {
        this.closeTime = closeTime;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}

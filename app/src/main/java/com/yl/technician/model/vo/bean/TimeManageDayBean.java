package com.yl.technician.model.vo.bean;

/**
 * Created by lyj on 2018/12/26
 */
public class TimeManageDayBean {

    /**
     * stylistId : 1362
     * day : 20181211
     * state : 0
     */

    private int stylistId;
    private int day;
    private int state;
    /**
     * id : 224
     * createtime : 2018-12-17 13:43:02
     * locktime : ["23:00","21:00"]
     * updatetime : 2018-12-17 14:48:24
     * worktime : ["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00"]
     * resttime : []
     */

    private int id;
    private String createtime;
    private String locktime;
    private String updatetime;
    private String worktime;
    private String resttime;

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getLocktime() {
        return locktime;
    }

    public void setLocktime(String locktime) {
        this.locktime = locktime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getResttime() {
        return resttime;
    }

    public void setResttime(String resttime) {
        this.resttime = resttime;
    }
}

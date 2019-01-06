package com.yl.technician.model.vo.bean;

/**
 * Created by zhangzz on 2018/11/5
 */
public class TimeBean {

    @Override
    public String toString() {
        return "TimeBean{" +
                "endtime='" + endtime + '\'' +
                ", id=" + id +
                ", starttime='" + starttime + '\'' +
                ", status=" + status +
                ", stylistId=" + stylistId +
                ", workday=" + workday +
                '}';
    }

    /**
     * endtime : string
     * id : 0
     * starttime : string
     * status : 0
     * stylistId : 0
     * workday : 0
     */

    private String endtime;
    private long id;
    private String starttime;
    private int status;
    private long stylistId;
    private int workday;

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStylistId() {
        return stylistId;
    }

    public void setStylistId(long stylistId) {
        this.stylistId = stylistId;
    }

    public int getWorkday() {
        return workday;
    }

    public void setWorkday(int workday) {
        this.workday = workday;
    }

}

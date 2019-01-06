package com.yl.technician.model.vo.bean;

/**
 * Created by lyj on 2018/12/26
 */
public class TimeManageBean {
    private String time;
    private boolean lock;
    private boolean checked;
    private boolean enable;

    @Override
    public String toString() {
        return "TimeBean{" +
                "time='" + time + '\'' +
                ", lock=" + lock +
                ", checked=" + checked +
                ", enable=" + lock +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

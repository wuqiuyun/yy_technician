package com.yl.technician.model.vo.bean;

/**
 * Created by lvlong on 2018/11/8.
 */
public class SortBean {
    private String title;
    private int id;
    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public SortBean(String title, int id, boolean isChoose) {
        this.title = title;
        this.id = id;
        this.isChoose = isChoose;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

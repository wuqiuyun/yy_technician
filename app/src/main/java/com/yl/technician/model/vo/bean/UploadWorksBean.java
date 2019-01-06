package com.yl.technician.model.vo.bean;

/*
 * Create by lvlong on  2018/11/5
 */


public class UploadWorksBean {

    private int id ;
    private String describe;   //描述
    private String name;
    private boolean checked;

    public UploadWorksBean() {}

    public UploadWorksBean(int id, String describe, String name) {
        this.id = id;
        this.describe = describe;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

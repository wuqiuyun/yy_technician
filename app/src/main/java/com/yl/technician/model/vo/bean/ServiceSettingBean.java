package com.yl.technician.model.vo.bean;

/**
 * Created by lvlong on 2018/10/12.
 */
public class ServiceSettingBean {
    private String label;
    private boolean checked;
    private boolean enabled;
    private int id;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "ServiceSettingBean{" +
                "label='" + label + '\'' +
                ", checked=" + checked +
                '}';
    }

    public ServiceSettingBean(String label, boolean checked) {
        this.label = label;
        this.checked = checked;
    }
    public ServiceSettingBean(String label, boolean checked, boolean enabled) {
        this.label = label;
        this.checked = checked;
        this.enabled = enabled;
    }
    public ServiceSettingBean(String label, boolean checked,int id) {
        this.label = label;
        this.checked = checked;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

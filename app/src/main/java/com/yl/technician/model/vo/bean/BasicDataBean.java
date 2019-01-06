package com.yl.technician.model.vo.bean;

import java.io.Serializable;

/**
 * Created by zm on 2018/11/13.
 */
public class BasicDataBean implements Serializable{

    private String registerReward;
    private String inviteReward;
    private String sysCoinDefault;

    public String getRegisterReward() {
        return registerReward;
    }

    public void setRegisterReward(String registerReward) {
        this.registerReward = registerReward;
    }

    public String getInviteReward() {
        return inviteReward;
    }

    public void setInviteReward(String inviteReward) {
        this.inviteReward = inviteReward;
    }

    public String getSysCoinDefault() {
        return sysCoinDefault;
    }

    public void setSysCoinDefault(String sysCoinDefault) {
        this.sysCoinDefault = sysCoinDefault;
    }
}

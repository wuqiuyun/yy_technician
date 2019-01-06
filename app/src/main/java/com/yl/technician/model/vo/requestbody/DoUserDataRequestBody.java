package com.yl.technician.model.vo.requestbody;

import android.text.TextUtils;

import com.yl.technician.component.toast.ToastUtils;

/**
 * Created by zm on 2018/11/1.
 */
public class DoUserDataRequestBody {

    private String birthday; // 生日
    private int gender; // 性别
    private String headPortrait; // 头像
    private String password; // 密码
    private String position; // 职位
    private String realName; // 姓名
    private String selfIntroduction; // 个人介绍
    private String userId; // 用户id
    private String confirmPwd; // 确认密码

    public boolean checkParams() {
        if (TextUtils.isEmpty(realName)) {
            ToastUtils.shortToast("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.shortToast("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            ToastUtils.shortToast("确认密码不能为空");
            return false;
        }
        if (!password.equals(confirmPwd)) {
            ToastUtils.shortToast("两次密码输入不一致");
            return false;
        }
        if (TextUtils.isEmpty(selfIntroduction)) {
            ToastUtils.shortToast("个人介绍不能为空");
            return false;
        }
        if (TextUtils.isEmpty(position)) {
            ToastUtils.shortToast("请选择个人头衔");
            return false;
        }
        return true;
    }


    private DoUserDataRequestBody(Builder builder) {
        birthday = builder.birthday;
        gender = builder.gender;
        headPortrait = builder.headPortrait;
        password = builder.password;
        position = builder.position;
        realName = builder.realName;
        selfIntroduction = builder.selfIntroduction;
        userId = builder.userId;
        confirmPwd = builder.confirmPwd;
    }


    public static final class Builder {
        private String birthday;
        private int gender;
        private String headPortrait;
        private String password;
        private String position;
        private String realName;
        private String selfIntroduction;
        private String userId;
        private String confirmPwd;

        public Builder() {
        }

        public Builder birthday(String val) {
            birthday = val;
            return this;
        }

        public Builder gender(int val) {
            gender = val;
            return this;
        }

        public Builder headPortrait(String val) {
            headPortrait = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder position(String val) {
            position = val;
            return this;
        }

        public Builder realName(String val) {
            realName = val;
            return this;
        }

        public Builder selfIntroduction(String val) {
            selfIntroduction = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder confirmPwd(String val) {
            confirmPwd = val;
            return this;
        }

        public DoUserDataRequestBody build() {
            return new DoUserDataRequestBody(this);
        }
    }
}

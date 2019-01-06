package com.yl.technician.model.vo.requestbody;

import android.text.TextUtils;

import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.util.AccountValidatorUtil;

import java.util.List;

/**
 * Created by zm on 2018/10/18.
 */
public class StylistAuthApplyRequestBody {

    private String cardFront; // 身份证正面
    private String cardReverse; // 身份证反面
    private String cardno; // 身份证号码
    private String hardCard; // 手执证件照
    private String realName; // 真实姓名
    private String stylistId; // 美发师id
    private List<String> qualification; // 资质证明

    public boolean checkParams() {
        if (TextUtils.isEmpty(realName)) {
            ToastUtils.shortToast("真实姓名不能为空.");
            return false;
        }
        if (TextUtils.isEmpty(cardno)) {
            ToastUtils.shortToast("身份证号码不能为空.");
            return false;
        }
        if (!AccountValidatorUtil.isIDCard(cardno)) {
            ToastUtils.shortToast("请输入正确的身份证号码");
            return false;
        }
        if (TextUtils.isEmpty(hardCard)) {
            ToastUtils.shortToast("请上传手持证件照.");
            return false;
        }
        if (TextUtils.isEmpty(cardFront)) {
            ToastUtils.shortToast("请上传身份证正面.");
            return false;
        }
        if (TextUtils.isEmpty(cardReverse)) {
            ToastUtils.shortToast("请上传身份证反面.");
            return false;
        }
        return true;
    }


    private StylistAuthApplyRequestBody(Builder builder) {
        cardFront = builder.cardFront;
        cardReverse = builder.cardReverse;
        cardno = builder.cardno;
        hardCard = builder.hardCard;
        realName = builder.realName;
        stylistId = builder.stylistId;
        qualification = builder.qualification;
    }


    public static final class Builder {
        private String cardFront;
        private String cardReverse;
        private String cardno;
        private String hardCard;
        private String realName;
        private String stylistId;
        private List<String> qualification;

        public Builder() {
        }

        public Builder cardFront(String val) {
            cardFront = val;
            return this;
        }

        public Builder cardReverse(String val) {
            cardReverse = val;
            return this;
        }

        public Builder cardno(String val) {
            cardno = val;
            return this;
        }

        public Builder hardCard(String val) {
            hardCard = val;
            return this;
        }

        public Builder realname(String val) {
            realName = val;
            return this;
        }

        public Builder stylistId(String val) {
            stylistId = val;
            return this;
        }

        public Builder qualification(List<String> val) {
            qualification = val;
            return this;
        }

        public StylistAuthApplyRequestBody build() {
            return new StylistAuthApplyRequestBody(this);
        }
    }
}

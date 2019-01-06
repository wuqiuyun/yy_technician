package com.yl.technician.model.vo.requestbody;

import android.text.TextUtils;

import com.yl.technician.component.toast.ToastUtils;

/**
 * Created by zm on 2018/10/25.
 */
public class UpdateAddressRequestBody {

    private String cityGDId;
    private String districtGDId;
    private double latitude;
    private String location;
    private double longitude;
    private String provinceGDId;
    private String userId;

    private UpdateAddressRequestBody(Builder builder) {
        cityGDId = builder.cityGDId;
        districtGDId = builder.districtGDId;
        latitude = builder.latitude;
        location = builder.location;
        longitude = builder.longitude;
        provinceGDId = builder.provinceGDId;
        userId = builder.userId;
    }

    /**
     * 检查门店参数
     * @return
     */
    public boolean checkStoreParams() {
        if (TextUtils.isEmpty(location)) {
            ToastUtils.shortToast("详细地址不能为空");
            return false;
        }
        return true;
    }
    
    public static final class Builder {
        private String cityGDId;
        private String districtGDId;
        private double latitude;
        private String location;
        private double longitude;
        private String provinceGDId;
        private String userId;

        public Builder() {
        }

        public Builder cityGDId(String val) {
            cityGDId = val;
            return this;
        }

        public Builder districtGDId(String val) {
            districtGDId = val;
            return this;
        }

        public Builder latitude(double val) {
            latitude = val;
            return this;
        }

        public Builder location(String val) {
            location = val;
            return this;
        }

        public Builder longitude(double val) {
            longitude = val;
            return this;
        }

        public Builder provinceGDId(String val) {
            provinceGDId = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public UpdateAddressRequestBody build() {
            return new UpdateAddressRequestBody(this);
        }
    }
}

package com.yl.technician.model.vo.requestbody;

/**
 * Created by lvlong on 2018/11/11.
 */
public class SaveLocationBody {

    @Override
    public String toString() {
        return "SaveLocationBody{" +
                "cityId='" + cityId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", latitude='" + latitude + '\'' +
                ", location='" + location + '\'' +
                ", longitude='" + longitude + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", userId='" + userId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    /**
     * cityId : 0
     * districtId : 0
     * latitude : 0
     * location : string
     * longitude : 0
     * provinceId : 0
     * userId : 0
     */

    private String cityId;
    private String districtId;
    private String latitude;
    private String location;
    private String longitude;
    private String provinceId;
    private String userId;
    private String province;
    private String city;

    private SaveLocationBody(Builder builder) {
        cityId = builder.cityId;
        districtId = builder.districtId;
        latitude = builder.latitude;
        location = builder.location;
        longitude = builder.longitude;
        provinceId = builder.provinceId;
        userId = builder.userId;
        province = builder.province;
        city = builder.city;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public static final class Builder {
        public String cityId;
        private String districtId;
        public String latitude;
        private String location;
        public String longitude;
        private String provinceId;
        private String userId;
        private String province;
        private String city;

        public Builder() {
        }

        public Builder cityId(String val) {
            cityId = val;
            return this;
        }

        public Builder districtId(String val) {
            districtId = val;
            return this;
        }

        public Builder latitude(String val) {
            latitude = val;
            return this;
        }

        public Builder location(String val) {
            location = val;
            return this;
        }

        public Builder longitude(String val) {
            longitude = val;
            return this;
        }

        public Builder provinceId(String val) {
            provinceId = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder province(String val) {
            province = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public SaveLocationBody build() {
            return new SaveLocationBody(this);
        }
    }
}

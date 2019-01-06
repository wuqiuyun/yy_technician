package com.yl.technician.model.vo.bean;

/**
 * Created by Lizhuo on 2018/10/30.
 * 美发师————个人中心
 */
public class StylistCenterInfoBean {
    /**
     * 
     "imgPath": "www.baidu.com",
     "nickame": "永远永远永远",
     "stylistLocation": 
     {
        "privinceId": 110000,
        "cityId": 110100,
        "districtId": 110105,
        "location": "好地方"
     },
     "sex": 2,
     "birthday": 20160303,
     "hobby": "的反反复复反反复复发作起来吧",
     "selfIntroduction": "得到的反反复复分",
     "showLocation": "北京北京市朝阳区"
     */
    private String imgPath;
    private String nickame;
    private StylistLocation stylistLocation;
    private int sex;//1男 2女 3妖怪
    private String birthday;
    private String hobby;
    private String selfIntroduction;
    private String showLocation;
    private String position;
    private String portrait;

    private StylistCenterInfoBean() {

    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getNickame() {
        return nickame;
    }

    public void setNickame(String nickame) {
        this.nickame = nickame;
    }

    public StylistLocation getStylistLocation() {
        return stylistLocation;
    }

    public void setStylistLocation(StylistLocation stylistLocation) {
        this.stylistLocation = stylistLocation;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getShowLocation() {
        return showLocation;
    }

    public void setShowLocation(String showLocation) {
        this.showLocation = showLocation;
    }

    public static class StylistLocation {
        /**
         "privinceId": 110000,
         "cityId": 110100,
         "districtId": 110105,
         "location": "好地方"
         */
        private long privinceId;
        private long cityId;
        private long districtId;
        private String location;

        public long getPrivinceId() {
            return privinceId;
        }

        public void setPrivinceId(long privinceId) {
            this.privinceId = privinceId;
        }

        public long getCityId() {
            return cityId;
        }

        public void setCityId(long cityId) {
            this.cityId = cityId;
        }

        public long getDistrictId() {
            return districtId;
        }

        public void setDistrictId(long districtId) {
            this.districtId = districtId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}

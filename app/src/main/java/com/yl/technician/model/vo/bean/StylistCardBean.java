package com.yl.technician.model.vo.bean;

import java.util.ArrayList;

/**
 * Created by Lizhuo on 2018/11/5.
 * 美发师名片
 */
public class StylistCardBean {

    private String backGroundImg;
    private int gender;//1男2女3太监
    private String headPortrait;//"d:/file/yiyue/1540521752087020.jpg"
    private String lable;//"洗剪吹168元"
    private String mobile;
    private String nickname;
    private String position;//"总监"
    private String serverTypes;//洗剪吹、多项套餐、??1、??1、??1、????、单项套餐2
    private float point;
    private float star;
    private long stylistId;
    private long userId;
    private String yearbirth;//"00后/白羊座/吃鸡肉"
    private String description;//"得到的反反复复分不清零基础学员",
    private boolean isCollection;//是否收藏
    private long evaluatenumer; //评论数
    
    private ArrayList<CardCoupon> cardCouponDTOs;
    private ArrayList<CardGrade> cardGradeDTOs;
    private ArrayList<CardOpus> cardOpusDTOs;
    private ArrayList<CardPackage> cardPackages;
    private ArrayList<CardServerItem> cardServerItems;
    private ArrayList<CardStore> cardStoreDTOs;

    public long getEvaluatenumer() {
        return evaluatenumer;
    }

    public void setEvaluatenumer(long evaluatenumer) {
        this.evaluatenumer = evaluatenumer;
    }

    public String getBackGroundImg() {
        return backGroundImg;
    }

    public void setBackGroundImg(String backGroundImg) {
        this.backGroundImg = backGroundImg;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getServerTypes() {
        return serverTypes;
    }

    public void setServerTypes(String serverTypes) {
        this.serverTypes = serverTypes;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public long getStylistId() {
        return stylistId;
    }

    public void setStylistId(long stylistId) {
        this.stylistId = stylistId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getYearbirth() {
        return yearbirth;
    }

    public void setYearbirth(String yearbirth) {
        this.yearbirth = yearbirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public ArrayList<CardCoupon> getCardCouponDTOs() {
        return cardCouponDTOs;
    }

    public void setCardCouponDTOs(ArrayList<CardCoupon> cardCouponDTOs) {
        this.cardCouponDTOs = cardCouponDTOs;
    }

    public ArrayList<CardGrade> getCardGradeDTOs() {
        return cardGradeDTOs;
    }

    public void setCardGradeDTOs(ArrayList<CardGrade> cardGradeDTOs) {
        this.cardGradeDTOs = cardGradeDTOs;
    }

    public ArrayList<CardOpus> getCardOpusDTOs() {
        return cardOpusDTOs;
    }

    public void setCardOpusDTOs(ArrayList<CardOpus> cardOpusDTOs) {
        this.cardOpusDTOs = cardOpusDTOs;
    }

    public ArrayList<CardPackage> getCardPackages() {
        return cardPackages;
    }

    public void setCardPackages(ArrayList<CardPackage> cardPackages) {
        this.cardPackages = cardPackages;
    }

    public ArrayList<CardServerItem> getCardServerItems() {
        return cardServerItems;
    }

    public void setCardServerItems(ArrayList<CardServerItem> cardServerItems) {
        this.cardServerItems = cardServerItems;
    }

    public ArrayList<CardStore> getCardStoreDTOs() {
        return cardStoreDTOs;
    }

    public void setCardStoreDTOs(ArrayList<CardStore> cardStoreDTOs) {
        this.cardStoreDTOs = cardStoreDTOs;
    }

    public static class CardCoupon {
        private long couponId;
        private int type; //1 满减 2 折扣
        private int limited;//领取限制 0数量 1日 2周 3月 4年 
        private double amount;//满足使用的金额
        private double deduction;//抵扣金额  /  折扣
        private double usePercent;//已抢的量占比 0->1 0% -> 100%
        private int quantity;//总共发多少张

        public CardCoupon(int type, int amount, double deduction, double usePercent) {
            this.type = type;
            this.amount = amount;
            this.deduction = deduction;
            this.usePercent = usePercent;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public long getCouponId() {
            return couponId;
        }

        public void setCouponId(long couponId) {
            this.couponId = couponId;
        }

        public double getDeduction() {
            return deduction;
        }

        public void setDeduction(double deduction) {
            this.deduction = deduction;
        }

        public int getLimited() {
            return limited;
        }

        public void setLimited(int limited) {
            this.limited = limited;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getUsePercent() {
            return usePercent;
        }

        public void setUsePercent(double usePercent) {
            this.usePercent = usePercent;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class CardGrade {
        private String gradeDescrip;
        private String gradeType;
        private float point;

        public String getGradeDescrip() {
            return gradeDescrip;
        }

        public void setGradeDescrip(String gradeDescrip) {
            this.gradeDescrip = gradeDescrip;
        }

        public String getGradeType() {
            return gradeType;
        }

        public void setGradeType(String gradeType) {
            this.gradeType = gradeType;
        }

        public float getPoint() {
            return point;
        }

        public void setPoint(float point) {
            this.point = point;
        }
    }

    public static class CardOpus {
        private String stylistOpusCovers;
        private long stylistOpusId;

        public String getStylistOpusCovers() {
            return stylistOpusCovers;
        }

        public void setStylistOpusCovers(String stylistOpusCovers) {
            this.stylistOpusCovers = stylistOpusCovers;
        }

        public long getStylistOpusId() {
            return stylistOpusId;
        }

        public void setStylistOpusId(long stylistOpusId) {
            this.stylistOpusId = stylistOpusId;
        }
    }

    public static class CardPackage {
        private String name;
        private long packageId;
        private double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getPackageId() {
            return packageId;
        }

        public void setPackageId(long packageId) {
            this.packageId = packageId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public static class CardServerItem {
        private String name;
        private String picture;
        private double price;
        private long serviceId;
        private int type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public long getServiceId() {
            return serviceId;
        }

        public void setServiceId(long serviceId) {
            this.serviceId = serviceId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class CardStore {
        private double distance;//556434.58
        private String location;//"G94珠三角环线高速出口西200米"
        private String storename;//"门店0"
        private long storeId;
        private String picture;//null

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

}

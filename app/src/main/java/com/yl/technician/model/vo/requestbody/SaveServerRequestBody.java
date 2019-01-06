package com.yl.technician.model.vo.requestbody;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlong on 2018/11/1.
 */
public class SaveServerRequestBody {

    @Override
    public String toString() {
        return "SaveServerRequestBody{" +
                "categoryId='" + categoryId + '\'' +
                ", costprice='" + costprice + '\'' +
                ", decription='" + decription + '\'' +
                ", duration='" + duration + '\'' +
                ", isoption='" + isoption + '\'' +
                ", packageId='" + packageId + '\'' +
                ", packageType='" + packageType + '\'' +
                ", picture='" + picture + '\'' +
                ", price='" + price + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", servicename='" + servicename + '\'' +
                ", stylistId='" + stylistId + '\'' +
                ", times='" + times + '\'' +
                ", packageOptions=" + packageOptions +
                ", serviceOptions=" + serviceOptions +
                '}';
    }

    /**
     * categoryId : 0
     * costprice : 0
     * decription : string
     * duration : 0
     * isoption : string
     * packageId : 0
     * packageOptions : [{"categoryId":0,"optionId":0,"optionname":"string","optiontitle":"string","optionvalue":"string","price":0,"spcId":0}]
     * packageType : string
     * picture : string
     * price : 0
     * serviceId : 0
     * serviceOptions : [{"optionId":0,"optionname":"string","optiontitle":"string","optionvalue":"string","price":0,"serviceOptionId":0}]
     * serviceType : string
     * servicename : string
     * stylistId : 0
     * times : 0
     */

    private String categoryId;
    private String costprice;
    private String decription;
    private String duration;
    private String isoption;
    private String packageId;
    private String packageType;
    private String picture;
    private String price;
    private String serviceId;
    private String serviceType;
    private String servicename;
    private String stylistId;
    private String times;
    private String locktime;
    private ArrayList<PackageOptionsBean> packageOptions;
    private ArrayList<ServiceOptionsBean> serviceOptions;

    private SaveServerRequestBody(Builder builder) {
        categoryId = builder.categoryId;
        costprice = builder.costprice;
        decription = builder.decription;
        duration = builder.duration;
        isoption = builder.isoption;
        packageId = builder.packageId;
        packageType = builder.packageType;
        picture = builder.picture;
        price = builder.price;
        serviceId = builder.serviceId;
        serviceType = builder.serviceType;
        servicename = builder.servicename;
        stylistId = builder.stylistId;
        times = builder.times;
        locktime = builder.locktime;
        packageOptions = builder.packageOptions;
        serviceOptions = builder.serviceOptions;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCostprice() {
        return costprice;
    }

    public String getDecription() {
        return decription;
    }

    public String getDuration() {
        return duration;
    }

    public String getIsoption() {
        return isoption;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getPackageType() {
        return packageType;
    }

    public String getPicture() {
        return picture;
    }

    public String getPrice() {
        return price;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServicename() {
        return servicename;
    }

    public String getStylistId() {
        return stylistId;
    }

    public String getTimes() {
        return times;
    }

    public String getLocktime() {
        return locktime;
    }

    public ArrayList<PackageOptionsBean> getPackageOptions() {
        return packageOptions;
    }

    public ArrayList<ServiceOptionsBean> getServiceOptions() {
        return serviceOptions;
    }

    public static class PackageOptionsBean {
        /**
         * categoryId : 0
         * optionId : 0
         * optionname : string
         * optiontitle : string
         * optionvalue : string
         * price : 0
         * spcId : 0
         */

        private String categoryId;
        private String optionId;
        private String optionname;
        private String optiontitle;
        private String optionvalue;
        private String price;
        private boolean optionv;//optionvalue是否为空
        private String spcId;
        private int position;
        private ArrayList<PackageOptionDetails> pakageOptionDetails;

        public ArrayList<PackageOptionDetails> getPackageOptionDetails() {
            return pakageOptionDetails;
        }

        public void setPackageOptionDetails(ArrayList<PackageOptionDetails> packageOptionDetails) {
            this.pakageOptionDetails = packageOptionDetails;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {

            this.position = position;
        }

        @Override
        public String toString() {
            return "PackageOptionsBean{" +
                    "categoryId='" + categoryId + '\'' +
                    ", optionId='" + optionId + '\'' +
                    ", optionname='" + optionname + '\'' +
                    ", optiontitle='" + optiontitle + '\'' +
                    ", optionvalue='" + optionvalue + '\'' +
                    ", price='" + price + '\'' +
                    ", spcId='" + spcId + '\'' +
                    ", optionv='" + optionv + '\'' +
                    '}';
        }

        public boolean isOptionv() {
            return optionv;
        }

        public void setOptionv(boolean optionv) {
            this.optionv = optionv;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
            this.optionId = optionId;
        }

        public String getOptionname() {
            return optionname;
        }

        public void setOptionname(String optionname) {
            this.optionname = optionname;
        }

        public String getOptiontitle() {
            return optiontitle;
        }

        public void setOptiontitle(String optiontitle) {
            this.optiontitle = optiontitle;
        }

        public String getOptionvalue() {
            return optionvalue;
        }

        public void setOptionvalue(String optionvalue) {
            this.optionvalue = optionvalue;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSpcId() {
            return spcId;
        }

        public void setSpcId(String spcId) {
            this.spcId = spcId;
        }

        public static class PackageOptionDetails{
            private String serviceOptionId;
            private String optionvalue;
            private String price;
            private String spcId;

            public String getServiceOptionId() {
                return serviceOptionId;
            }

            public void setServiceOptionId(String serviceOptionId) {
                this.serviceOptionId = serviceOptionId;
            }

            public String getOptionvalue() {
                return optionvalue;
            }

            public void setOptionvalue(String optionvalue) {
                this.optionvalue = optionvalue;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSpcId() {
                return spcId;
            }

            public void setSpcId(String spcId) {
                this.spcId = spcId;
            }
        }

    }

    public static class ServiceOptionsBean {
        @Override
        public String toString() {
            return "ServiceOptionsBean{" +
                    "optionId=" + optionId +
                    ", optionname='" + optionname + '\'' +
                    ", optiontitle='" + optiontitle + '\'' +
                    ", optionvalue='" + optionvalue + '\'' +
                    ", price='" + price + '\'' +
                    ", serviceOptionId='" + serviceOptionId + '\'' +
                    ", serviceOptionDetails=" + serviceOptionDetails +
                    '}';
        }

        /**
         * optionId : 0
         * optionname : string
         * optiontitle : string
         * optionvalue : string
         * price : 0
         * serviceOptionId : 0
         */

        private int optionId;
        private String optionname;
        private String optiontitle;
        private String optionvalue;
        private String price;
        private String serviceOptionId;
        private ArrayList<ServiceOptionDetails> serviceOptionDetails;

        public ArrayList<ServiceOptionDetails> getServiceOptionDetails() {
            return serviceOptionDetails;
        }

        public void setServiceOptionDetails(ArrayList<ServiceOptionDetails> serviceOptionDetails) {
            this.serviceOptionDetails = serviceOptionDetails;
        }

        public int getOptionId() {
            return optionId;
        }

        public void setOptionId(int optionId) {
            this.optionId = optionId;
        }

        public String getOptionname() {
            return optionname;
        }

        public void setOptionname(String optionname) {
            this.optionname = optionname;
        }

        public String getOptiontitle() {
            return optiontitle;
        }

        public void setOptiontitle(String optiontitle) {
            this.optiontitle = optiontitle;
        }

        public String getOptionvalue() {
            return optionvalue;
        }

        public void setOptionvalue(String optionvalue) {
            this.optionvalue = optionvalue;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getServiceOptionId() {
            return serviceOptionId;
        }

        public void setServiceOptionId(String serviceOptionId) {
            this.serviceOptionId = serviceOptionId;
        }

        public static class ServiceOptionDetails{
            private String serviceOptionId;
            private String optionvalue;
            private String price;

            @Override
            public String toString() {
                return "ServiceOptionDetails{" +
                        "serviceOptionId='" + serviceOptionId + '\'' +
                        ", optionvalue='" + optionvalue + '\'' +
                        ", price='" + price + '\'' +
                        '}';
            }

            public String getServiceOptionId() {
                return serviceOptionId;
            }

            public void setServiceOptionId(String serviceOptionId) {
                this.serviceOptionId = serviceOptionId;
            }

            public String getOptionvalue() {
                return optionvalue;
            }

            public void setOptionvalue(String optionvalue) {
                this.optionvalue = optionvalue;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }

    public static final class Builder {
        private String categoryId;
        private String costprice;
        private String decription;
        private String duration;
        private String isoption;
        private String packageId;
        private String packageType;
        private String picture;
        private String price;
        private String serviceId;
        private String serviceType;
        private String servicename;
        private String stylistId;
        private String times;
        private String locktime;
        private ArrayList<PackageOptionsBean> packageOptions;
        private ArrayList<ServiceOptionsBean> serviceOptions;

        public Builder() {
        }

        public Builder categoryId(String val) {
            categoryId = val;
            return this;
        }

        public Builder costprice(String val) {
            costprice = val;
            return this;
        }

        public Builder decription(String val) {
            decription = val;
            return this;
        }

        public Builder duration(String val) {
            duration = val;
            return this;
        }

        public Builder isoption(String val) {
            isoption = val;
            return this;
        }

        public Builder packageId(String val) {
            packageId = val;
            return this;
        }

        public Builder packageType(String val) {
            packageType = val;
            return this;
        }

        public Builder picture(String val) {
            picture = val;
            return this;
        }

        public Builder price(String val) {
            price = val;
            return this;
        }

        public Builder serviceId(String val) {
            serviceId = val;
            return this;
        }

        public Builder serviceType(String val) {
            serviceType = val;
            return this;
        }

        public Builder servicename(String val) {
            servicename = val;
            return this;
        }

        public Builder stylistId(String val) {
            stylistId = val;
            return this;
        }

        public Builder times(String val) {
            times = val;
            return this;
        }
        public Builder locktime(String val) {
            locktime = val;
            return this;
        }

        public Builder packageOptions(ArrayList<PackageOptionsBean> val) {
            packageOptions = val;
            return this;
        }

        public Builder serviceOptions(ArrayList<ServiceOptionsBean> val) {
            serviceOptions = val;
            return this;
        }

        public SaveServerRequestBody build() {
            return new SaveServerRequestBody(this);
        }
    }
}

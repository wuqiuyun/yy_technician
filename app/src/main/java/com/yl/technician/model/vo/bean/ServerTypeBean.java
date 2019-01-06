package com.yl.technician.model.vo.bean;

import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlong on 2018/11/1.
 */
public class ServerTypeBean {
    @Override
    public String toString() {
        return "ServerTypeBean{" +
                "id=" + id +
                ", describe='" + describe + '\'' +
                ", name='" + name + '\'' +
                ", package1=" + package1 +
                ", package2=" + package2 +
                ", position=" + position +
                ", content='" + content + '\'' +
                ", options=" + options +
                ", price=" + price +
                ", duration=" + duration +
                ", locktime=" + locktime +
                '}';
    }

    /**
     * id : 3
     * describe : 染发
     * name : 染发
     * package1 : 1
     * package2 : 1
     * options : [{"id":1,"categoryId":3,"optionbutton":"添加药水","optionname":"药水名称","optiontitle":"短发"},{"id":2,"categoryId":3,"optionbutton":"添加药水","optionname":"药水名称","optiontitle":"中发"},{"id":3,"categoryId":3,"optionbutton":"添加药水","optionname":"药水名称","optiontitle":"长发"}]
     */

    private int id;
    private String describe;
    private String name;
    private int package1;
    private int package2;
    private int position;
    private String content;
    private String price;
    private String duration;
    private String locktime;
    private List<OptionsBean> options;

    public String getLocktime() {
        return locktime;
    }

    public void setLocktime(String locktime) {
        this.locktime = locktime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getPackage1() {
        return package1;
    }

    public void setPackage1(int package1) {
        this.package1 = package1;
    }

    public int getPackage2() {
        return package2;
    }

    public void setPackage2(int package2) {
        this.package2 = package2;
    }

    public List<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsBean> options) {
        this.options = options;
    }

    public static class OptionsBean {
        @Override
        public String toString() {
            return "OptionsBean{" +
                    "id=" + id +
                    ", categoryId=" + categoryId +
                    ", optionbutton='" + optionbutton + '\'' +
                    ", optionname='" + optionname + '\'' +
                    ", optiontitle='" + optiontitle + '\'' +
                    ", packageOptionsBeans=" + packageOptionsBeans +
                    ", serviceOptionsBeans=" + serviceOptionsBeans +
                    '}';
        }

        /**
         * id : 1
         * categoryId : 3
         * optionbutton : 添加药水
         * optionname : 药水名称
         * optiontitle : 短发
         */

        private int id;
        private int categoryId;
        private String optionbutton;
        private String optionname;
        private String optiontitle;
        private List<SaveServerRequestBody.PackageOptionsBean> packageOptionsBeans;
        private List<SaveServerRequestBody.ServiceOptionsBean> serviceOptionsBeans;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getOptionbutton() {
            return optionbutton;
        }

        public void setOptionbutton(String optionbutton) {
            this.optionbutton = optionbutton;
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

        public List<SaveServerRequestBody.PackageOptionsBean> getPackageOptionsBeans() {
            return packageOptionsBeans;
        }

        public void setPackageOptionsBeans(List<SaveServerRequestBody.PackageOptionsBean> packageOptionsBeans) {
            this.packageOptionsBeans = packageOptionsBeans;
        }

        public List<SaveServerRequestBody.ServiceOptionsBean> getServiceOptionsBeans() {
            return serviceOptionsBeans;
        }

        public void setServiceOptionsBeans(List<SaveServerRequestBody.ServiceOptionsBean> serviceOptionsBeans) {
            this.serviceOptionsBeans = serviceOptionsBeans;
        }
    }
}

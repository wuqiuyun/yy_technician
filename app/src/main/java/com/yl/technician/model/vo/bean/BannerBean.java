package com.yl.technician.model.vo.bean;

/**
 * Created by wqy on 2018/12/17.
 */

public class BannerBean {

    /**
     * id : 4
     * app : 2
     * title : 分享福利
     * image : https://yiyueuser.oss-cn-shenzhen.aliyuncs.com/banner/fxflbanner.jpg
     * url : https://api.51yiyue.com/user-api/explain/bannerfxfl.html
     * status : 2
     * starttime : 2018-11-14 11:12:45.0
     * endtime : 2018-11-23 11:12:49.0
     * remark : dsd
     * createtime : 2018-11-27 11:12:54
     * updatetime : 2018-11-27 14:10:08
     */

    private int id;
    private int app;
    private String title;
    private String image;
    private String url;
    private int status;
    private String starttime;
    private String endtime;
    private String remark;
    private String createtime;
    private String updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApp() {
        return app;
    }

    public void setApp(int app) {
        this.app = app;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}

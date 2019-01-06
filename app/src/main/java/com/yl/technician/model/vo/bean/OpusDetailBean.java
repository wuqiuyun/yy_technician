package com.yl.technician.model.vo.bean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/11/6.
 * 作品详情
 */
public class OpusDetailBean {
    
    private String describe;        //描述
    private int collection;         //收藏
    private int reposted;           //转发
    private int pageview;           //查看
    private long opusId;            //作品ID
    private long stylistId;         //作者的美发师ID
    private boolean isCollection;   //是否收藏
    private List<pictrue> pictrues;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public int getReposted() {
        return reposted;
    }

    public void setReposted(int reposted) {
        this.reposted = reposted;
    }

    public int getPageview() {
        return pageview;
    }

    public void setPageview(int pageview) {
        this.pageview = pageview;
    }

    public long getOpusId() {
        return opusId;
    }

    public void setOpusId(long opusId) {
        this.opusId = opusId;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public List<pictrue> getPictrues() {
        return pictrues;
    }

    public void setPictrues(List<pictrue> pictrues) {
        this.pictrues = pictrues;
    }

    public long getStylistId() {
        return stylistId;
    }

    public void setStylistId(long stylistId) {
        this.stylistId = stylistId;
    }

    public static class pictrue{
        private String path;
        private long pictureId;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public long getPictureId() {
            return pictureId;
        }

        public void setPictureId(long pictureId) {
            this.pictureId = pictureId;
        }
    }
}

package com.yl.technician.model.vo.bean;

/*
 * Create by lvlong on  2018/11/3
 */

public class MyWorksBean {

    private long id ;                //作品id
    private String describe;        //作品描述
    private int collection;         //收藏量
    private int reposted;           //转发量
    private int pageview;           //查看量
    private String[] opusPaths;       //图片地址

    public MyWorksBean(int id, String describe, int collection, int reposted, int pageview, String[] opusPaths) {
        this.id = id;
        this.describe = describe;
        this.collection = collection;
        this.reposted = reposted;
        this.pageview = pageview;
        this.opusPaths = opusPaths;
    }

    public String getId() {
        return id==0? "":String.valueOf(id);
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String[] getOpusPaths() {
        return opusPaths;
    }

    public void setOpusPaths(String[] opusPaths) {
        this.opusPaths = opusPaths;
    }
}

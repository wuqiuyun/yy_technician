package com.yl.technician.model.vo.requestbody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlong on 2018/11/5.
 */
public class SortSearchRequesetBody {

    @Override
    public String toString() {
        return "SortSearchRequesetBody{" +
                "cityId='" + cityId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", maxDistance='" + maxDistance + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", sortType='" + sortType + '\'' +
                ", userId='" + userId + '\'' +
                ", categoryIds=" + categoryIds +
                '}';
    }

    /**
     * categoryIds : [0]
     * cityId : 0
     * districtId : 0
     * lat : 0
     * lng : 0
     * maxDistance : 0
     * provinceId : 0
     * sortType : 0
     * userId : 0
     */

    private String cityId;
    private String districtId;
    private String lat;
    private String lng;
    private String maxDistance;
    private String provinceId;
    private String sortType;
    private String userId;
    private ArrayList<String> categoryIds;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(String maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(ArrayList<String> categoryIds) {
        this.categoryIds = categoryIds;
    }
}

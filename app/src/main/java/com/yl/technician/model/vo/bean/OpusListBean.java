package com.yl.technician.model.vo.bean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/11/7.
 * 美发师作品集
 */
public class OpusListBean {
   
    private List<Opus> opusList;
    private List<OpusHairstyle> opusHairstyleList;
    private List<OpusFeaTure> opusFeaTureList;

    public List<Opus> getOpusList() {
        return opusList;
    }

    public void setOpusList(List<Opus> opusList) {
        this.opusList = opusList;
    }

    public List<OpusHairstyle> getOpusHairstyleList() {
        return opusHairstyleList;
    }

    public void setOpusHairstyleList(List<OpusHairstyle> opusHairstyleList) {
        this.opusHairstyleList = opusHairstyleList;
    }

    public List<OpusFeaTure> getOpusFeaTureList() {
        return opusFeaTureList;
    }

    public void setOpusFeaTureList(List<OpusFeaTure> opusFeaTureList) {
        this.opusFeaTureList = opusFeaTureList;
    }

    public static class Opus{
        private long stylistOpusId;
        private String stylistOpusCovers;

        public long getStylistOpusId() {
            return stylistOpusId;
        }

        public void setStylistOpusId(long stylistOpusId) {
            this.stylistOpusId = stylistOpusId;
        }

        public String getStylistOpusCovers() {
            return stylistOpusCovers;
        }

        public void setStylistOpusCovers(String stylistOpusCovers) {
            this.stylistOpusCovers = stylistOpusCovers;
        }
    }
    
    public static class OpusHairstyle{
        private long hairstyleId;
        private int count;
        private String hairstyleName;

        public long getHairstyleId() {
            return hairstyleId;
        }

        public void setHairstyleId(long hairstyleId) {
            this.hairstyleId = hairstyleId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getHairstyleName() {
            return hairstyleName;
        }

        public void setHairstyleName(String hairstyleName) {
            this.hairstyleName = hairstyleName;
        }
    }

    public static class OpusFeaTure{
        private long feaTureId;
        private int count;
        private String feaTureName;

        public long getFeaTureId() {
            return feaTureId;
        }

        public void setFeaTureId(long feaTureId) {
            this.feaTureId = feaTureId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getFeaTureName() {
            return feaTureName;
        }

        public void setFeaTureName(String feaTureName) {
            this.feaTureName = feaTureName;
        }
    }
}

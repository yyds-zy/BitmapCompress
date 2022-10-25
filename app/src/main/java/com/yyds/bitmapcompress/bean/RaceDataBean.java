package com.yyds.bitmapcompress.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/24
 * Describe:
 */
public class RaceDataBean implements Serializable {
    private List<RaceInfo> raceInfoList;
    private int raceCount;
    private int ranking;

    public RaceDataBean() {}

    public RaceDataBean(List<RaceInfo> raceInfoList, int raceCount, int ranking) {
        this.raceInfoList = raceInfoList;
        this.raceCount = raceCount;
        this.ranking = ranking;
    }

    public List<RaceInfo> getRaceInfoList() {
        return raceInfoList;
    }

    public void setRaceInfoList(List<RaceInfo> raceInfoList) {
        this.raceInfoList = raceInfoList;
    }

    public int getRaceCount() {
        return raceCount;
    }

    public void setRaceCount(int raceCount) {
        this.raceCount = raceCount;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public static class RaceInfo implements Serializable {
        private long date;
        private String leftCountry;
        private String rightCountry;
        private String race;
        private String result;

        public RaceInfo(){}

        public RaceInfo(long date, String leftCountry, String rightCountry, String race, String result) {
            this.date = date;
            this.leftCountry = leftCountry;
            this.rightCountry = rightCountry;
            this.race = race;
            this.result = result;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getLeftCountry() {
            return leftCountry;
        }

        public void setLeftCountry(String leftCountry) {
            this.leftCountry = leftCountry;
        }

        public String getRightCountry() {
            return rightCountry;
        }

        public void setRightCountry(String rightCountry) {
            this.rightCountry = rightCountry;
        }

        public String getRace() {
            return race;
        }

        public void setRace(String race) {
            this.race = race;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}

package com.yyds.bitmapcompress.bean;

import java.util.List;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/24
 * Describe:
 */
public class RaceDataBean {

    private List<RaceInfo> raceInfoList;
    private int raceCount;
    private int ranking;




    public class RaceInfo {
        private long date;
        private String leftCountry;
        private String rightCountry;
        private int race;
        private String result;
    }

}

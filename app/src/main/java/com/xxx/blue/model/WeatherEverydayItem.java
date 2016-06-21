package com.xxx.blue.model;

import java.util.Calendar;

/**
 * Created by 张丽娟 on 2016/6/16.
 */
public class WeatherEverydayItem {

    public Calendar date;
    public int code;
    public int highTemp, lowTemp;
    public String highTempS, lowTempS;
    public String week;
    public String weatherIcon;
    private String[] countInChinese = {"日", "一", "二", "三", "四", "五", "六", "日"};

    public WeatherEverydayItem(Calendar date, int code, int highTemp, int lowTemp) {
        this.date = date;
        this.code =code;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.week = "周" + countInChinese[date.get(Calendar.DAY_OF_WEEK)];
        highTempS = highTemp + "℃";
        lowTempS = lowTemp + "℃";
    }
}

package com.xxx.blue.model;

import com.xxx.blue.R;

import java.util.Calendar;

/**
 * Created by 张丽娟 on 2016/6/16.
 */
public class WeatherEverydayItem {
    public Calendar date;
    public int iconResourceId;
    public String highTempS, lowTempS;
    public String week;
    public String description;
    private String[] countInChinese = {"日", "一", "二", "三", "四", "五", "六", "日"};

    public WeatherEverydayItem(Calendar date, String description, int highTemp, int lowTemp) {
        this.date = date;
        this.week = "周" + countInChinese[date.get(Calendar.DAY_OF_WEEK)];
        this.description = description;
        iconResourceId = getIconResourceIdFromDescription(description);
        highTempS = highTemp + "℃";
        lowTempS = lowTemp + "℃";
    }

    private int getIconResourceIdFromDescription(String description) {
        switch (description.split("转")[0]) {
            case "晴朗":
            case "晴":
                return R.mipmap.clear;
            case "多云":
                return R.mipmap.clouds;
            case "小雨":
            case "中雨":
            case "大雨":
            case "雷阵雨":
            case "阵雨":
            case "有雨":
                return R.mipmap.rain;
            default:
                return R.mipmap.clouds;
        }
    }
}

package com.xxx.blue.model;

import com.xxx.blue.R;

import java.util.Calendar;

/**
 * Created by 张丽娟 on 2016/6/18.
 */
public class WeatherEvery3HoursItem {

    public Calendar date;
    public int iconResourceId;
    public String description;
    public String hourS;
    public String dateS;
    public String tempS;
    public String rainPossibilityS;

    public WeatherEvery3HoursItem(Calendar date, String description, int temp, int rainPossibility) {
        this.date = date;
        this.description = description;
        tempS = temp + "℃";
        hourS = date.get(Calendar.HOUR) + ":00";
        dateS = date.get(Calendar.DAY_OF_MONTH) + "日";
        rainPossibilityS = rainPossibility + "%";
        iconResourceId = getIconResourceIdFromDescription(description);
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

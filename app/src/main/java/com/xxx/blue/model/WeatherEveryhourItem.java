package com.xxx.blue.model;

import java.util.Calendar;

/**
 * Created by 张丽娟 on 2016/6/18.
 */
public class WeatherEveryhourItem {

    public Calendar date;
    public String description;
    public String hour;
    public String dateS;
    public int temp;
    public String tempS;
    public String weatherIcon;
    public int rainPossibility;
    public String rainPossibilityS;

    public WeatherEveryhourItem(Calendar date, String description, int temp, int rainPossibility) {
        this.date = date;
        this.description = description;
        this.temp = temp;
        this.rainPossibility = rainPossibility;
        tempS = temp + "℃";
        hour = date.get(Calendar.HOUR) + ":00";
        dateS = date.get(Calendar.DAY_OF_MONTH) + "日";
//        double modifiedPercent = (rainPossibility * 0.8);
        rainPossibilityS = ((int)(rainPossibility * 0.8)) + "%";
    }
}

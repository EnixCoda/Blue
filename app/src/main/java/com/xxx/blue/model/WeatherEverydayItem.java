package com.xxx.blue.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.blue.R;

/**
 * Created by 张丽娟 on 2016/6/16.
 */
public class WeatherEverydayItem {

    private String week;
    private String weathericon;
    private String hightemp;
    private  String lowtemp;


    public String getweek() {
        return week;
    }
    public void setweek(String week) {
        this.week = week;
    }

    public String getweathericon() {
        return weathericon;
    }

    public void setweathericon(String weathericon) {
        this.weathericon = weathericon;
    }

    public String gethightemp() {
        return hightemp;
    }
    public void sethightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public String getlowtemp() {
        return lowtemp;
    }
    public void setlowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }


}

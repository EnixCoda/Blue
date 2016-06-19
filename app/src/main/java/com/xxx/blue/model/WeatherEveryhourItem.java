package com.xxx.blue.model;

/**
 * Created by 张丽娟 on 2016/6/18.
 */
public class WeatherEveryhourItem {

    private String hour;
    private String hourweathericon;
    private String temp;
    private  String wind;
    private String rainnum;
    private  String rainpercent;

    public String gethour() {
        return hour;
    }
    public void sethour(String hour) {
        this.hour = hour;
    }

    public String gethourweathericon() {
        return hourweathericon;
    }

    public void sethourweathericon(String hourweathericon) {
        this.hourweathericon = hourweathericon;
    }

    public String gettemp() {
        return temp;
    }
    public void settemp(String temp) {
        this.temp = temp;
    }

    public String getwind() {
        return wind;
    }
    public void setwind(String wind) {
        this.wind = wind;
    }
    public String getrainnum() {
        return rainnum;
    }
    public void setrainnum(String rainnum) {
        this.rainnum = rainnum;
    }
    public String getrainpercent() {
        return rainpercent;
    }
    public void setrainpercent(String rainpercent) {
        this.rainpercent = rainpercent;
    }
}

package getData;

import java.util.Calendar;

/**
 * Created by Exin on 2016/5/23.
 */
public class Forecast {
    public Calendar date;
    public String description;
    public int code;
    public int temp;
    public int tempMin;
    public int tempMax;
    public int humidity;
    public int rainPoss;
    public int rainAmount;
    public int pressure;

    public Forecast(Calendar date, String description, int temp, int rainPoss) {
        this.date = date;
        this.description = description;
        this.temp = temp;
        this.rainPoss = rainPoss;
    }

    public Forecast(Calendar date, int code, int temp, int hum, int rain, int pressure) {
        this.date = date;
        this.code = code;
        this.temp = temp;
        this.humidity = hum;
        this.rainPoss = rain;
        this.pressure = pressure;
    }

    public Forecast(Calendar date, String conclusion, int temp, int hum, int rain, int pressure) {
        this.date = date;
        this.description = conclusion;
        this.temp = temp;
        this.humidity = hum;
        this.rainPoss = rain;
        this.pressure = pressure;
    }

    public Forecast(Calendar date, String conclusion, int code, int tempMin, int tempMax, int hum, int rainPossibility, int rainAmount, int pressure) {
        this.date = date;
        this.description = conclusion;
        this.code = code;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = hum;
        this.rainPoss = rainPossibility;
        this.rainAmount = rainAmount;
        this.pressure = pressure;
    }

    public Forecast(Calendar date, String conclusion, int tempMin, int tempMax, int hum, int rainPossibility, int rainAmount, int pressure) {
        this.date = date;
        this.description = conclusion;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = hum;
        this.rainPoss = rainPossibility;
        this.rainAmount = rainAmount;
        this.pressure = pressure;
    }

    public static String translateConclusion(String conclusionE) {
        switch (conclusionE) {
            case "Clouds":
                return "多云";
            case "Rain":
                return "有雨";
            case "Clear":
                return "晴朗";
            default:
                return ":" + conclusionE;
        }
    }
}

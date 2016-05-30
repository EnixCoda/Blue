package getdata;

import java.util.Calendar;

/**
 * Created by Exin on 2016/5/23.
 *
 */
public class Forecast {
    Calendar date;
    public String description;
    public int temp;
    public int humidity;
    public int rainPoss;
    public int pressure;

    public Forecast(Calendar date, String conclusion, int temp, int hum, int rain, int pressure) {
        this.date = date;
        this.description = conclusion;
        this.temp = temp;
        this.humidity = hum;
        this.rainPoss = rain;
        this.pressure = pressure;
    }
}

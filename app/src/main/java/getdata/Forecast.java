package getdata;

import java.util.Calendar;

/**
 * Created by Exin on 2016/5/23.
 */
public class Forecast {
    Calendar date;
    String conclusion;
    int temp;
    int hum;
    int rainPoss;
    int pressure;

    public Forecast(Calendar date, String conclusion, int temp, int hum, int rain, int pressure) {
        this.date = date;
        this.conclusion = conclusion;
        this.temp = temp;
        this.hum = hum;
        this.rainPoss = rain;
        this.pressure = pressure;
    }
}

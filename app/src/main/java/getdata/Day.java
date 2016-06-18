package getData;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Exin on 2016/5/18.
 *
 */
public class Day {
    public Hashtable<String, IAQI> stringIAQIHashtable = new Hashtable<>();
    public String cityName;
    public String cityId;
    public String time;
    public int aqi;
    public ArrayList<IAQI> forecastAQI = new ArrayList<>();
    public ArrayList<Wind> forecastWind = new ArrayList<>();
    public ArrayList<SiteData> nearbySitesData = new ArrayList<>();
    public ArrayList<Forecast> hourlyForecasts = new ArrayList<>();
    public ArrayList<Forecast> dailyForecasts = new ArrayList<>();

    public Day (String name, String id, int aqi, String time) {
        this.cityName = name;
        this.cityId = id;
        this.aqi = aqi;
        this.time = time;
    }

    public void setIAQI(String name, IAQI iaqi) {
        stringIAQIHashtable.put(name, iaqi);
    }

    public void addSiteData(SiteData siteData) {
        nearbySitesData.add(siteData);
    }

    public void addForecast(IAQI pm25Forecast) {
        forecastAQI.add(pm25Forecast);
    }

    public void addForecast(Wind windForecast) {
        forecastWind.add(windForecast);
    }

    public void addHourlyForecast(Forecast weather) {
        hourlyForecasts.add(weather);
    }

    public void addDailyForecast(Forecast weather) {
        dailyForecasts.add(weather);
    }
}

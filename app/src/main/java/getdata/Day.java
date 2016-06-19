package getData;

import java.util.ArrayList;
import java.util.Hashtable;

import analyse.Instructor;
import analyse.Suggestion;

/**
 * Created by Exin on 2016/5/18.
 *
 */
public class Day {
    public String cityId;
    public String cityName;
    public String time;
    public int aqi;
    public String AQIConclusion="";
    public int weatherCode=0;
    public String weatherConclusion="";

    public Day tomorrow;

    public Hashtable<String, IAQI> stringIAQIHashtable = new Hashtable<>();
    public ArrayList<IAQI> forecastAQI = new ArrayList<>();
    public ArrayList<Wind> forecastWind = new ArrayList<>();
    public ArrayList<SiteData> nearbySitesData = new ArrayList<>();
    public ArrayList<Forecast> hourlyForecasts = new ArrayList<>();
    public ArrayList<Forecast> dailyForecasts = new ArrayList<>();

    Hashtable<String, Suggestion> instructions;

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

    public void calcTomorrow() {
        tomorrow.aqi = forecastAQI.get(30).max;
        tomorrow.AQIConclusion = Instructor.tellMeAboutAQI(tomorrow.aqi);
    }

    public Hashtable<String, Suggestion> getInstructions() {
        return instructions;
    }
}

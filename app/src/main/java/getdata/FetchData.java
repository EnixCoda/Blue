package getData;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Scanner;

import analyse.Instructor;
import analyse.Suggestion;
import utility.ChineseToPinyin;

/**
 * Created by Exin on 2016/5/18.
 * 获取此刻的AQI相关数据、天气预报
 */
public class FetchData extends AsyncTask<String, Void, Day> {
    final String urlPrefix = "http://aqicn.org/city/";
    final String urlSuffix = "/cn/m";

    final String head = "function getAqiModel( ){if (window.aqiModel) return aqiModel;var model= ";
    final String tail = ";return model;}function getMetricName(m)";

    AbbrTable abbrTable = new AbbrTable();

    public interface AsyncResponse {
        void processFinish(Day day);
    }

    public AsyncResponse delegate = null;

    public FetchData(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    /**
     * @param params params = [cityId];
     *               like: ["shanghai"], ["Shanghai"]
     */
    @Override
    protected Day doInBackground(String... params) {
        String cityId;
        String response;
        if (params.length > 0) cityId = ChineseToPinyin.getPinYin(params[0]);
        else return null;
        try {
            // get HTML page source
            response = askAPI(urlPrefix + cityId + urlSuffix);

            if (response.contains(head) && response.contains(tail)) {
                String dataJSON = response.substring(response.indexOf(head) + head.length(), response.indexOf(tail));
                JSONObject jsonObject = new JSONObject(dataJSON);
                JSONObject city = jsonObject.getJSONObject("city");
                String cityName = city.getString("name");
                String id = city.getString("id");
                int aqi = jsonObject.getInt("aqi");
                String time = jsonObject.getJSONObject("time").getJSONObject("s").getJSONObject("cn").getString("time").split(" ")[1];
                Day day = new Day(cityName, id, aqi, time);
                day.tomorrow = new Day(cityName, id, 0, "");
                day.AQIConclusion = Instructor.tellMeAboutAQI(aqi);
                JSONArray iaqisJSONArray = jsonObject.getJSONArray("iaqi");
                int i = 0;
                while (i < iaqisJSONArray.length()) {
                    JSONObject iaqiJSONObject = iaqisJSONArray.getJSONObject(i);

                    String name = abbrTable.translateAbbr(iaqiJSONObject.getString("p"));

                    JSONArray values = iaqiJSONObject.getJSONArray("v");
                    int cur = values.getInt(0),
                        min = values.getInt(1),
                        max = values.getInt(2);

                    String description = iaqiJSONObject.getString("i");

                    String dateString = iaqiJSONObject.getJSONArray("h").getString(0);
                    SimpleDateFormat sf = new SimpleDateFormat("y/M/d H:m:s");
                    Date recordDate = sf.parse(dateString);
                    Calendar date = Calendar.getInstance();
                    date.setTime(recordDate);
                    date.add(Calendar.HOUR, 8);

                    day.setIAQI(name, new IAQI(name, cur, date));
                    i++;
                }

                // forecast
                JSONObject forecast = jsonObject.getJSONObject("forecast");
                JSONArray forecastAQI = forecast.getJSONArray("aqi");
                i = 0;
                while (i < forecastAQI.length()) {
                    JSONObject f = forecastAQI.getJSONObject(i);
                    String timeS = f.getString("t");
                    SimpleDateFormat sdf = new SimpleDateFormat("y-M-d'T'H:m:s+00:00");
                    Date foreDate = sdf.parse(timeS);
                    Calendar date = Calendar.getInstance();
                    date.setTime(foreDate);
                    date.add(Calendar.HOUR, 8);
                    JSONArray values = f.getJSONArray("v");
                    int min = values.getInt(0), max = values.getInt(1);
                    day.addForecast(new IAQI("PM2.5", min, max, date));
                    i++;
                }

                JSONArray forecastWind = forecast.getJSONArray("wind");
                i = 0;
                while (i < forecastWind.length()) {
                    JSONObject f = forecastWind.getJSONObject(i);
                    String timeS = f.getString("t");
                    SimpleDateFormat sdf = new SimpleDateFormat("y-M-d'T'H:m:s+00:00");
                    Date foreDate = sdf.parse(timeS);
                    Calendar date = Calendar.getInstance();
                    date.setTime(foreDate);
                    date.add(Calendar.HOUR, 8);
                    JSONArray values = f.getJSONArray("w");
                    double min = values.getDouble(0), max = values.getDouble(1), degree = values.getDouble(2);
                    day.addForecast(new Wind(min, max, degree, date));
                    i++;
                }

                JSONArray nearSites = jsonObject.getJSONArray("nearest");
                i = 0;
                while (i < nearSites.length()) {
                    JSONObject siteObject = nearSites.getJSONObject(i);
                    String siteName = siteObject.getString("name");
                    String aqiInString = siteObject.getString("aqi");
                    int siteAqi = 0;
                    if (!aqiInString.equals("-")) {
                        siteAqi = Integer.valueOf(aqiInString);
                    }
                    day.addSiteData(new SiteData(siteName, siteAqi));
                    i++;
                }

                // from openweathermap.org
                response = askAPI("http://api.openweathermap.org/data/2.5/forecast?units=metric&apiKey=2af0d79061afbe47f5d591b80e7054ae&q=" + cityId);
                if (response.length() == 0) return null;
                jsonObject = new JSONObject(response);
                JSONArray hourlyForecasts = jsonObject.getJSONArray("list");
                for (i = 0; i < hourlyForecasts.length(); i++) {
                    JSONObject hourlyForecast = hourlyForecasts.getJSONObject(i);
                    String timeS = hourlyForecast.getString("dt_txt");
                    SimpleDateFormat sdf = new SimpleDateFormat("y-M-d H:m:s");
                    Date forecastDate = sdf.parse(timeS);
                    Calendar date = Calendar.getInstance();
                    date.setTime(forecastDate);
                    JSONObject weather = hourlyForecast.getJSONArray("weather").getJSONObject(0);
                    String weatherE = weather.getString("main");
                    JSONObject mainInfo = hourlyForecast.getJSONObject("main");
                    day.addHourlyForecast(new Forecast(date, Forecast.translateConclusion(weatherE), mainInfo.getInt("temp"), mainInfo.getInt("humidity")));
                }


                // from heweather.com
                response = askAPI("https://api.heweather.com/x3/weather?key=4882d5fa92124f2a959703aaf5b049be&city=" + cityId);
                if (response.length() == 0) return null;
                jsonObject = new JSONObject(response);
                jsonObject = jsonObject.getJSONArray("HeWeather data service 3.0").getJSONObject(0);

//                JSONArray hourlyForecasts = jsonObject.getJSONArray("hourly_forecast");
//                i = 0;
//                while (i < hourlyForecasts.length()) {
//                    JSONObject weatherForecast = hourlyForecasts.getJSONObject(i);
//                    SimpleDateFormat sdf = new SimpleDateFormat("y-M-d H:m");
//                    Calendar date = Calendar.getInstance();
//                    date.setTime(sdf.parse(weatherForecast.getString("date")));
//
//                    int humidity = Integer.valueOf(weatherForecast.getString("hum"));
//                    int rain = Integer.valueOf(weatherForecast.getString("pop"));
//                    int pressure = Integer.valueOf(weatherForecast.getString("pres"));
//                    int temp = Integer.valueOf(weatherForecast.getString("tmp"));
//                    JSONObject descriptions = jsonObject.getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("cond");
//                    int weatherCodeDay = descriptions.getInt("code_d");
//                    day.addHourlyForecast(new Forecast(date, weatherCodeDay, temp, humidity, rain, pressure));
//                    i++;
//                }

                JSONArray dailyForecasts = jsonObject.getJSONArray("daily_forecast");
                i = 0;
                while (i < dailyForecasts.length()) {
                    JSONObject weatherForecast = dailyForecasts.getJSONObject(i);
                    SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
                    Calendar date = Calendar.getInstance();
                    date.setTime(sdf.parse(weatherForecast.getString("date")));

                    JSONObject descriptions = weatherForecast.getJSONObject("cond");
                    String descriptionDay = descriptions.getString("txt_d");
                    int weatherCode = descriptions.getInt("code_d");
                    String descriptionNight = descriptions.getString("txt_n");
                    String description = descriptionDay.equals(descriptionNight) ? descriptionDay : descriptionDay + "转" + descriptionNight;
                    int humidity = Integer.valueOf(weatherForecast.getString("hum"));
                    int rainPossibility = Integer.valueOf(weatherForecast.getString("pop"));
                    int rainAmount = Integer.valueOf(weatherForecast.getString("pop"));
                    int pressure = Integer.valueOf(weatherForecast.getString("pres"));
                    int tempMin = Integer.valueOf(weatherForecast.getJSONObject("tmp").getString("min"));
                    int tempMax = Integer.valueOf(weatherForecast.getJSONObject("tmp").getString("max"));
                    day.addDailyForecast(new Forecast(date, description, weatherCode, tempMin, tempMax, humidity, rainPossibility, rainAmount, pressure));
                    i++;
                }

                // set weather code and conclusion
                JSONObject weatherForecastToday = dailyForecasts.getJSONObject(0).getJSONObject("cond");
                day.weatherCode = weatherForecastToday.getInt("code_d");
                day.weatherConclusion = weatherForecastToday.getString("txt_d");
                JSONObject weatherForecastTomorrow = dailyForecasts.getJSONObject(1).getJSONObject("cond");
                day.tomorrow.weatherCode = weatherForecastTomorrow.getInt("code_d");
                day.tomorrow.weatherConclusion = weatherForecastTomorrow.getString("txt_d");

                day.calcTomorrow();

                return day;
            }

        } catch (Exception e) {
            Log.e("FetchData", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Day day) {
        super.onPostExecute(day);
        if (day != null) {
            Instructor instructor = new Instructor();
            day.instructions = instructor.getInstructions(day);
        }
        delegate.processFinish(day);
    }

    private String askAPI(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            return response;
        } catch (Exception e) {
            return "";
        }
    }
}

class AbbrTable {
    Hashtable<String, String> map;

    public AbbrTable() {
        map = new Hashtable<>();
        map.put("pm25", "PM2.5");
        map.put("pm10", "PM10");
        map.put("o3", "O3");
        map.put("no2", "NO2");
        map.put("so2", "SO2");
        map.put("co", "CO");
        map.put("t", "温度");
        map.put("d", "露点");
        map.put("p", "空气压力");
        map.put("h", "湿度");
        map.put("w", "风");
    }

    public String translateAbbr(String abbr) {
        if (map.get(abbr) != null) return map.get(abbr);
        return abbr;
    }
}

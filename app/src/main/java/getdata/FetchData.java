package getdata;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Created by Exin on 2016/5/18.
 * 获取此刻的AQI
 */
public class FetchData extends AsyncTask<String, Void, Day> {
    final String urlPrefix = "http://aqicn.org/city/";
    final String urlSuffix = "/cn/m";

    final String head = "function getAqiModel( ){if (window.aqiModel) return aqiModel;var model= ";
    final String tail = ";return model;}function getMetricName(m)";

    public interface AsyncResponse {
        void processFinish(Day day);
    }

    public AsyncResponse delegate = null;

    public FetchData(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    /**
    *
    * @param params
     *          params = [cityId];
     *          like: ["shanghai"]
    */
    @Override
    protected Day doInBackground(String... params) {
        String cityId;
        if (params.length > 0) cityId = params[0];
        else return null;
        Day day = null;
        HttpURLConnection httpURLConnection;
        URL url;
        try {
            // get HTML page source
            url = new URL(urlPrefix + cityId + urlSuffix);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String response;
            response = s.hasNext() ? s.next() : "";

            if (response.contains(head) && response.contains(tail)) {
                String dataJSON = response.substring(response.indexOf(head) + head.length(), response.indexOf(tail));
                JSONObject jsonObject = new JSONObject(dataJSON);
                JSONObject city = jsonObject.getJSONObject("city");
                String cityName = city.getString("name");
                String id = city.getString("id");
                int aqi = jsonObject.getInt("aqi");
                day = new Day(cityName, id, aqi);
                JSONArray iaqisJSONArray = jsonObject.getJSONArray("iaqi");
                int i = 0;
                while (i < iaqisJSONArray.length()) {
                    JSONObject iaqiJSONObject = iaqisJSONArray.getJSONObject(i);

                    String name = iaqiJSONObject.getString("p");

                    JSONArray values = iaqiJSONObject.getJSONArray("v");
                    int cur = values.getInt(0),
                        min = values.getInt(1),
                        max = values.getInt(2);

                    String description = iaqiJSONObject.getString("i");

                    String dateString = iaqiJSONObject.getJSONArray("h").getString(0);
                    SimpleDateFormat sf = new SimpleDateFormat("y/m/d H:m:s");
                    Date recordDate = sf.parse(dateString);
                    Calendar date = Calendar.getInstance();
                    date.setTime(recordDate);
                    date.add(Calendar.HOUR, 8);

                    day.setIAQI(name, new IAQI(name, min, max, cur, date));
                    i++;
                }

                JSONArray nearSites = jsonObject.getJSONArray("nearest");
                i = 0;
                while (i < nearSites.length()) {
                    JSONObject siteObject = nearSites.getJSONObject(i);

                    String siteName = siteObject.getString("name");
                    int siteAqi = Integer.valueOf(siteObject.getString("aqi"));
                    String siteUrl = siteObject.getString("curl");

                    day.newSiteData(new SiteData(siteName, siteAqi, siteUrl));
                    i++;
                }
            }
        } catch (Exception e) {
            Log.e("FetchData", e.getMessage());
        }
        return day;
    }

    @Override
    protected void onPostExecute(Day day) {
        super.onPostExecute(day);
        delegate.processFinish(day);
    }
}

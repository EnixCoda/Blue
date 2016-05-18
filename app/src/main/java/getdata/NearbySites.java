package getdata;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Exin on 2016/5/18.
 */
public class NearbySites extends AsyncTask<String, Void, ArrayList<SiteData>>{
    final String urlPrefix = "http://aqicn.org/aqicn/json/android/";
    final String urlSuffix = "/json";

    public interface AsyncResponse {
        void processFinish(ArrayList<SiteData> siteDatas);
    }

    public AsyncResponse delegate = null;

    public NearbySites(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<SiteData> doInBackground(String... params) {
        String cityId;
        if (params.length > 0) cityId = params[0];
        else return null;

        ArrayList<SiteData> siteDatas = new ArrayList<>();

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

            JSONObject responseJSON = new JSONObject(response);
            JSONArray nearestSitesArray = responseJSON.getJSONArray("nearest");
            int i = 0;
            while (i < nearestSitesArray.length()) {
                JSONObject site = nearestSitesArray.getJSONObject(i);
                String siteName = site.getString("nna");
                int pm25 = Integer.valueOf(site.getString("v"));
                String nameE = site.getString("nlo");
                double lon = site.getJSONArray("g").getDouble(0);
                double lat = site.getJSONArray("g").getDouble(1);
                String siteUrl = site.getString("u");
                SiteData thisSite = new SiteData(siteName, pm25);
                thisSite.nameE = nameE;
                thisSite.lon = lon;
                thisSite.lat = lat;
                thisSite.pageUrl = siteUrl;
                siteDatas.add(thisSite);
                i++;
            }
        } catch (Exception e) {
            Log.v("NearbySites", e.getMessage());
        }

        return siteDatas;
    }

    @Override
    protected void onPostExecute(ArrayList<SiteData> siteDatas) {
        super.onPostExecute(siteDatas);
        delegate.processFinish(siteDatas);
    }
}

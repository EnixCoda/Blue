package utility;

import android.content.Context;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;

public class QueryLocation {

    private Context context;
    private JSONArray cities, hotCities;

    public QueryLocation(Context context) {
        this.context = context;
        String jsonCities = loadJSONFromAsset("cities.json");
        String jsonHotCities = loadJSONFromAsset("hotCities.json");
        try {
            cities = new JSONArray(jsonCities);
            hotCities = new JSONArray(jsonHotCities);
        } catch (Exception e) {
            try {
                cities = new JSONArray("[]");
                hotCities = new JSONArray("[]");
            } catch (Exception ee) {

            }
        }
    }

    public String[] query(String namePrefix) {
        int maxQueryLength = 20;
        String[] locationSet = new String[maxQueryLength];
        int countLocations = 0;
        try {
            if (namePrefix.length()==0) {
                while (countLocations < maxQueryLength && countLocations < hotCities.length()) {
                    locationSet[countLocations++] = hotCities.getString(countLocations);
                }
            }
            namePrefix = ChineseToPinyin.getPinYin(namePrefix);
            int size = cities.length();
            String cityPinyin, cityName;
            for (int i = 0; i < size && countLocations < maxQueryLength; i++) {
                JSONArray city = cities.getJSONArray(i);
                cityName = city.getString(0);
                cityPinyin = city.getString(1);
                if (cityPinyin.indexOf(namePrefix) == 0) {
                    locationSet[countLocations++] = cityName;
                }
            }
        } catch (Exception e) {

        }
        return locationSet;
    }

    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
package getdata;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Exin on 2016/5/18.
 */
public class Day {
    String cityName;
    String cityId;
    String time;
    int aqi;
    Hashtable<String, IAQI> stringIAQIHashtable = new Hashtable<>();
    ArrayList<SiteData> siteDatas = new ArrayList<>();

    public Day (String name, String id, int aqi, String time) {
        this.cityName = name;
        this.cityId = id;
        this.aqi = aqi;
        this.time = time;
    }

    public void setIAQI(String name, IAQI iaqi) {
        stringIAQIHashtable.put(name, iaqi);
    }

    public void newSiteData (SiteData siteData) {
        siteDatas.add(siteData);
    }

}

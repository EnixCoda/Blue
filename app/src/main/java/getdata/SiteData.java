package getdata;

/**
 * Created by Exin on 2016/5/18.
 */
public class SiteData {
    String name;
    int aqi;
    String pageUrl;

    public SiteData (String name, int aqi, String pageUrl) {
        this.name = name;
        this.aqi = aqi;
        this.pageUrl = pageUrl;
    }
}

package getdata;

/**
 * Created by Exin on 2016/5/18.
 */
public class SiteData {
    String name;
    int pm25;
    String nameE;
    String pageUrl;
    double lon, lat;

    public SiteData (String name, int pm25) {
        this.name = name;
        this.pm25 = pm25;
    }
}

package getData;

/**
 * Created by Exin on 2016/5/18.
 */
public class SiteData {
    public String name;
    public int pm25;
    public String nameE;
    public String pageUrl;
    public double lon, lat;

    public SiteData (String name, int pm25) {
        this.name = name;
        this.pm25 = pm25;
    }
}

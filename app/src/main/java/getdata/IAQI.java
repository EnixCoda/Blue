package getdata;

import java.util.Calendar;

/**
 * Created by Exin on 2016/5/18.
 */
public class IAQI {
    String name;
    int min, max, cur;
    Calendar date;

    public IAQI (String name, int min, int max, int cur, Calendar date) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.cur = cur;
        this.date = date;
    }
}

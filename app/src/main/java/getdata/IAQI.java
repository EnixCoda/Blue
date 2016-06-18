package getData;

import java.util.Calendar;

/**
 * Created by Exin on 2016/5/18.
 *
 */
public class IAQI {
    public int cur;
    public int min, max;
    public Calendar date;
    String name;

    public IAQI (String name, int min, int max, Calendar date) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.date = date;
    }

    public IAQI (String name, int cur, Calendar date) {
        this.name = name;
        this.cur = cur;
        this.date = date;
    }
}
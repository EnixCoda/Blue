package getdata;

import java.util.Calendar;

/**
 * Created by Exin on 2016/5/23.
 *
 */
public class Wind {
    Calendar time;
    public double min, max;
    public double degree;

    public Wind(double min, double max, double degree, Calendar time) {
        this.min = min;
        this.max = max;
        this.degree = degree;
        this.time = time;
    }
}

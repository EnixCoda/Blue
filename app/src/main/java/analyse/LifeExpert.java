package analyse;

import java.util.Hashtable;

import getData.Day;

/**
 * Created by Exin on 2016/6/22.
 */
public class LifeExpert {

    // TODO: 重新整理建议逻辑

    final String[] RespirableParticulateMatters = {"PM2.5", "PM10"};//可吸入颗粒物
    final String[] GaseousPollutants = {"O3", "NO2", "SO2", "CO"};//气体污染物

    Day mDay;

    public Hashtable<String, Suggestion> getInstructions(Day day) {
        if (day == null) return null;
        mDay = day;
        Hashtable<String, Suggestion> instructions = new Hashtable<>();
        instructions.put("uv", analyse("uv"));
        instructions.put("out", analyse("out"));
        instructions.put("clothes", analyse("clothes"));
        instructions.put("cold", analyse("cold"));
        instructions.put("umbrella", analyse("umbrella"));
        instructions.put("sports", analyse("sports"));
        instructions.put("washCar", analyse("washCar"));
        instructions.put("airCleaner", analyse("airCleaner"));
        instructions.put("feeling", analyse("feeling"));
        return instructions;
    }

    private Suggestion analyse(String type) {
        String description = "";
        int code = 0;
        switch (type) {
            case "":
                break;
        }
        return new Suggestion(description, code);
    }

    public static String tellMeAboutAQI(int aqi) {
        String msg = "空气质量";
        if (aqi < 50) {
            msg += "优良";
        } else if (aqi < 100) {
            msg += "尚可";
        } else if (aqi < 150) {
            msg += "不佳";
        } else if (aqi < 200) {
            msg += "较差";
        } else {
            msg += "极差";
        }
        return msg;
    }
}

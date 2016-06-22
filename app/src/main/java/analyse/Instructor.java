package analyse;

import java.util.Hashtable;

import getData.Day;


public class Instructor {
    String clothes;                          //  0
    String outSuggestion = "适宜出行";       //-1 1
    String umbrella = "不需带伞";            //-1 1
    String sports = "适合运动";              //-1 1
    String washCars = "适宜洗车";            //-1 1
    String airCleaner = "不需开启";          //-1 1
    String feeling = "体感一般";             //-1 0 1
    String cold = "感冒不易发";              //-1 1
    String uv = "不需防晒";

    int ifOut = 1;
    int ifUmbrella = -1;
    int ifSports = 1;
    int ifWashCars = 1;
    int ifAirCleaner = -1;
    int ifCold = -1;
    int ifFeeling = 0;

    final String[] RespirableParticulateMatters = {"PM2.5", "PM10"};//可吸入颗粒物
    final String[] GaseousPollutants = {"O3", "NO2", "SO2", "CO"};//气体污染物
    Hashtable<String, Suggestion> instructions;

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

    public Hashtable<String, Suggestion> getInstructions(Day day) {
        if (day == null) return null;
        if (ifOutside("湿度", day.hourlyForecasts.get(0).humidity) == 1 && ifOutside("温度", day.hourlyForecasts.get(0).temp) == 1) {
            ifFeeling = 1;
            feeling = "体感舒适";
        } else if (ifOutside("温度", day.hourlyForecasts.get(0).temp) != -1) {
            ifFeeling = 0;
            feeling = "体感一般";
        } else {
            ifFeeling = -1;
            feeling = "体感不适";
        }

        //验证可吸入颗粒物
        for (String pollution : RespirableParticulateMatters) {
            if (ifOutside(pollution, day.stringIAQIHashtable.get(pollution).cur) == -1) {
                ifOut = -1;
                ifWashCars = -1;
                ifSports = -1;
                ifAirCleaner = 1;
                ifFeeling = -1;
                outSuggestion = "不宜出行";
                washCars = "不宜洗车";
                airCleaner = "建议开启";
                sports = "不宜运动";
                feeling = "体感不适";
            }
        }

        //验证空气污染物
        for (String pollution : GaseousPollutants) {
            if (ifOutside(pollution, day.stringIAQIHashtable.get(pollution).cur) == -1) {
                ifOut = -1;
                ifWashCars = -1;
                ifSports = -1;
                ifAirCleaner = 1;
                ifFeeling = -1;
                outSuggestion = "不宜出行";
                washCars = "不宜洗车";
                airCleaner = "建议开启";
                sports = "不宜运动";
                feeling = "体感不适";
            }
        }
        //验证温度
        if (day.hourlyForecasts.size() > 0 && ifOutside("温度", day.hourlyForecasts.get(0).temp) == -1) {
            ifOut = -1;
            ifSports = -1;
            ifFeeling = -1;
            outSuggestion = "不宜出行";
            sports = "不宜运动";
            feeling = "体感不适";
        }

        //验证风力
        if (ifOutside("风", day.forecastWind.get(0).max) == -1) {
            ifOut = -1;
            ifSports = -1;
            ifWashCars = -1;
            ifFeeling = -1;
            outSuggestion = "不宜出行";
            sports = "不宜运动";
            washCars = "不宜洗车";
            feeling = "体感不适";
        }

        //验证降雨可能
        if (day.hourlyForecasts.size() > 0 && day.hourlyForecasts.get(0).rainPoss > 50) {
            ifOut = -1;
            ifSports = -1;
            ifWashCars = -1;
            ifUmbrella = 1;
            outSuggestion = "不宜出行";
            sports = "不宜运动";
            washCars = "不宜洗车";
            umbrella = "建议带伞";
        }

        if ((day.stringIAQIHashtable.get("温度").max - day.stringIAQIHashtable.get("温度").min) >= 10) {
            ifCold = -1;
            cold = "感冒易发";
        }

        clothes = getInstruction("温度", day.hourlyForecasts.size() > 0 ? day.hourlyForecasts.get(0).temp : 26);

        instructions = new Hashtable<>();
        instructions.put("uv", new Suggestion(uv, 0));
        instructions.put("outSuggestion", new Suggestion(outSuggestion, ifOut));
        instructions.put("clothes", new Suggestion(clothes, 0));
        instructions.put("cold", new Suggestion(cold, ifCold));
        instructions.put("umbrella", new Suggestion(umbrella, ifUmbrella));
        instructions.put("sports", new Suggestion(sports, ifSports));
        instructions.put("washCars", new Suggestion(washCars, ifWashCars));
        instructions.put("airCleaner", new Suggestion(airCleaner, ifAirCleaner));
        instructions.put("feeling", new Suggestion(feeling, ifFeeling));
        return instructions;
    }

    //验证用方法，输入空气质量信息返回是否建议出门
    int ifOutside(String type, double val) {
        switch (type) {
            case "PM2.5":
                if (val < 150) {
                    return 1;
                }
                return -1;
            case "PM10":
                if (val < 150) {
                    return 1;
                }
                return -1;
            case "O3":
                if (val < 200) {
                    return 1;
                }
                return -1;
            case "NO2":
                if (val < 200) {
                    return 1;
                }
                return -1;
            case "SO2":
                if (val < 200) {
                    return 1;
                }
                return -1;
            case "CO":
                if (val < 50) {
                    return 1;
                }
                return -1;
            case "温度":
                if (15 <= val && val <= 25) {
                    return 1;
                } else if (-15 < val && val < 30) {
                    return 0;
                }
                return -1;
            case "露点":
                break;
            case "空气压力":
                break;
            case "湿度":
                if (40 <= val && val <= 50) {
                    return 1;
                }
                return -1;
            case "风":
                if (val < 10) {
                    return 1;
                }
                return -1;
            default:
                return 0;
        }
        return 0;
    }

    //根据输入空气质量信息返回单条建议
    String getInstruction(String type, int val) {
        switch (type) {
            case "PM2.5":
                if (val < 100) {
                    return "适宜出行";
                }
                return "不宜出行";
            case "PM10":
                if (val < 150) {
                    return "适宜出行";
                }
                return "不宜出行";
            case "O3":
                return "";
            case "NO2":
                return "";
            case "SO2":
                return "";
            case "CO":
                return "";
            case "温度":
                if (val < 0) {
                    return "适合羽绒服类";
                }
                if (val < 10) {
                    return "适合皮夹克，风衣";
                }
                if (val < 20) {
                    return "适合长袖服装";
                }
                if (val < 30) {
                    return "适合衬衫，T恤";
                } else {
                    return "适合短衣裤短裙";
                }
            case "空气压力":
                /*if()
				else ;*/
                return "";
            case "湿度":
                /*if()
				else ;*/
                return "";
            case "风":
                /*if()
				else ;*/
                return "";
            default:
                return "";
        }
    }
}

package analyse;

import java.util.Hashtable;

import getdata.Day;

public class Instructor {

    String outSuggestion;
    String clothes;
    String mask = "不需口罩";
    String oldAndChild = "可以出行";
    String dryClothes = "适合晾晒";
    String airCleaner = "不需开启";
    boolean ifOut = true;
    boolean ifMask = false;
    boolean ifOldAndChild = true;
    boolean ifDryClothes = false;
    boolean ifAirCleaner = false;

    Hashtable<String, Integer> pollutionNameToIndex;

    String[] RespirableParticulateMatters = {"PM2.5", "PM10"};
    String[] GaseousPollutants = {"O3", "NO2", "SO2", "CO"};

    public Instructor() {
        pollutionNameToIndex = new Hashtable<>();
        pollutionNameToIndex.put("PM2.5", 1);
        pollutionNameToIndex.put("PM10", 2);
        pollutionNameToIndex.put("O3", 3);
        pollutionNameToIndex.put("NO2", 4);
        pollutionNameToIndex.put("SO2", 5);
        pollutionNameToIndex.put("CO", 6);
        pollutionNameToIndex.put("温度", 7);
        pollutionNameToIndex.put("露点", 8);
        pollutionNameToIndex.put("空气压力", 9);
        pollutionNameToIndex.put("湿度", 10);
        pollutionNameToIndex.put("风", 11);
    }

    public Hashtable<String, String> getInstructions(Day day) {
        for (String pollution : RespirableParticulateMatters) {
            if (!ifOutside(pollution, day.stringIAQIHashtable.get(pollution).cur)) {
                ifOut = false;
                ifMask = true;
                ifOldAndChild = false;
                ifAirCleaner = true;
                mask = "建议佩戴口罩";
                airCleaner = "建议开启";
                oldAndChild = "不建议出行";
            }
        }
        for (String pollution : GaseousPollutants) {
            if (!ifOutside(pollution, day.stringIAQIHashtable.get(pollution).cur)) {
                ifOut = false;
                ifOldAndChild = false;
                ifAirCleaner = true;
                airCleaner = "建议开启";
                oldAndChild = "不建议出行";
            }
        }
        if (!ifOutside("温度", day.hourlyForecasts.get(0).temp)) {
            ifOut = false;
            ifOldAndChild = false;
            oldAndChild = "不建议出行";
        }
        if (!ifOutside("湿度", day.hourlyForecasts.get(0).humidity)) {
            ifDryClothes = false;
            dryClothes = "不适合晾晒";
        }
        if (!ifOutside("风", day.forecastWind.get(0).max)) {
            ifOut = false;
            ifOldAndChild = false;
            ifDryClothes = false;
            oldAndChild = "不建议出行";
            dryClothes = "不适合晾晒";
        }
        if (day.hourlyForecasts.get(0).rainPoss > 50) {
            ifOut = false;
            ifOldAndChild = false;
            ifDryClothes = false;
            oldAndChild = "不建议出行";
            dryClothes = "不适合晾晒";
        }

        if (ifOut) {
            for (String pollution : RespirableParticulateMatters) {
                outSuggestion = getInstruction(pollution, day.stringIAQIHashtable.get(pollution).cur);
            }
        } else {
            for (String pollution : RespirableParticulateMatters) {
                outSuggestion = "建议避免出行";
            }
        }
        clothes = getInstruction("温度", day.hourlyForecasts.get(0).temp);

        Hashtable<String, String> instructions = new Hashtable<>();
        instructions.put("outSuggestion", outSuggestion);
        instructions.put("clothes", clothes);
        instructions.put("mask", mask);
        instructions.put("oldAndChild", oldAndChild);
        instructions.put("dryClothes", dryClothes);
        instructions.put("airCleaner", airCleaner);
        return instructions;
    }

    public boolean ifOutside(String type, double val) {
        switch (pollutionNameToIndex.get(type)) {
            case 1:
                return val < 150;
            case 2:
                /*if()
                else ;*/
                return val < 150;
            case 3:
                return val < 200;
            case 4:
                return val < 200;
            case 5:
                return val < 150;
            case 6:
                return val < 50;
            case 7:
                return -20 < val && val < 40;
            case 8:
            /*if()
                else ;*/
            case 9:
            /*if()
                else ;*/
            case 10:
                return val < 80;
            case 11:
                /*if()
                else ;*/
                return val < 10;
            default:
                return true;
        }
    }

    String getInstruction(String type, int val) {
        switch (pollutionNameToIndex.get(type)) {
            case 1:
                if (val < 50) {
                    return "建议进行户外运动";
                } else if (val < 100) {
                    return "可正常进行户外运动";
                } else if (val < 150) {
                    return "敏感人群应减少户外活动";
                } else if (val < 200) {
                    return "建议减少外出";
                }
                return "建议避免外出";
            case 2:
                /*if()
				else ;*/
                if (val < 50) {
                    return "建议进行户外运动";
                } else if (val < 100) {
                    return "可正常进行户外运动";
                } else if (val < 150) {
                    return "敏感人群应减少户外活动";
                } else if (val < 200) {
                    return "建议减少外出";
                }
                return "建议避免外出";
            case 3:
                if (val < 160) {
                    return "";
                }
                if (val < 200) {
                    return "";
                }
                if (val < 300) {
                    return "";
                } else {
                    return "";
                }

            case 4:
                if (val < 100) {
                    return "";
                }
                if (val < 200) {
                    return "";
                }
                if (val < 300) {
                    return "";
                }
                return "";

            case 5:
                if (val < 100) {
                    return "";
                }
                if (val < 150) {
                    return "";
                }
                if (val < 500) {
                    return "";
                }
                if (val < 650) {
                    return "";
                }
                return "";
            case 6:
                if (val < 5) {
                    return "";
                }
                if (val < 10) {
                    return "";
                }
                if (val < 50) {
                    return "";
                }
                if (val < 100) {
                    return "";
                } else {
                    return "";
                }

            case 7:
                if (val < 0) {
                    return "建议穿羽绒服类厚衣物";
                }
                if (val < 10) {
                    return "适合穿皮夹克，风衣";
                }
                if (val < 20) {
                    return "适合长袖休闲装，运动服";
                }
                if (val < 30) {
                    return "适合穿衬衫，T恤，短套装";
                }

                return "适合穿短衣裤，短裙";
            case 8:
                /*if()
				else ;*/
                return "";
            case 9:
                /*if()
				else ;*/
                return "";
            case 10:
                /*if()
				else ;*/
                return "";
            case 11:
                /*if()
				else ;*/
                if (val < 5) {
                    return "";
                } else if (val < 10) {
                    return "";
                } else if (val < 20) {
                    return "";
                } else {
                    return "";
                }
            default:
                return "";
        }
    }
}

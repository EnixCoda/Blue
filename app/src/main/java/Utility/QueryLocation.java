package Utility;

import org.json.JSONArray;

public class QueryLocation {
    public static String[] query(String namePrefix) {
        String[] LocationSet = new String[300];
        int LocationNum = 0;
        char[] location;
        char[] target = namePrefix.toCharArray();
        try {
            //TODO
            String jsonContent = "";//jsonContent为能运行暂设为空
            JSONArray jsonArray = new JSONArray(jsonContent);
            int size = jsonArray.length();
            for (int i = 0; i < size; i++) {
                JSONArray jsonObject = jsonArray.getJSONArray(i);
                location = jsonObject.getString(0).toCharArray();
                int flag = 0;
                for (int i1 = 0; i1 < location.length; i1++) {
                    if ((location.length - i1) >= target.length) {
                        if (target[0] == location[i1]) {
                            if (target.length == 1)
                                flag = 1;
                            for (int i2 = 1; i2 < target.length; i2++) {
                                if (target[i2] == location[i2])
                                    flag = 1;
                                else {
                                    flag = 0;
                                    break;
                                }
                            }
                        }
                        if (flag == 1)
                            break;
                    }
                }
                StringBuffer buf = new StringBuffer();
                buf.append(location);
                if (flag == 1) {
                    LocationSet[LocationNum] = buf.toString();
                    LocationNum++;
                }
            }
        } catch (Exception e) {

        }
        return LocationSet;
    }
}
package Utility;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseToPinyin {
    public static String getPinYin(String src) {
        src = src.toLowerCase();
        char[] t1;
        t1 = src.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t3 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], format);
                    t3 += t2[0];
                } else
                    t3 += java.lang.Character.toString(t1[i]);
            }
            return t3;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t3;
    }
}
package cn.zzzyuan.util;

import org.elasticsearch.common.text.Text;

public class StringUtil {
    public static String truncated(String str,String beg,String end){
        int i = str.lastIndexOf(beg);
        int length = str.length();
        if(i == -1){
            return str.substring(0, Math.min(100, length - 1));
        } else {
            return str.substring(Math.max(0,i - 50),Math.min(length - 1,i + 50 ));
        }
    }
    public static String truncated(Text[] texts){
        StringBuilder str = new StringBuilder();
        for (Text fragment : texts) {
            str.append(fragment);
        }
        return str.toString();
    }
    public static String truncated(Text[] texts,int num){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < num; i++) {
            str.append(texts[i]);
        }
        return str.toString();
    }
}

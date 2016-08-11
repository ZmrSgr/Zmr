package cn.sgr.zmr.com.sgr.Utils.util;

import java.util.StringTokenizer;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class Utils {


    public static int[] StringtoInt(String str) {
        int ret[] = new int[str.length()];
        StringTokenizer toKenizer = new StringTokenizer(str, "-");
        int i = 0;
        while (toKenizer.hasMoreElements()) {
            ret[i++] = Integer.valueOf(toKenizer.nextToken());
        }
        return ret;
    }
}

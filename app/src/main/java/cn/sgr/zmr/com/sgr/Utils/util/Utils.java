package cn.sgr.zmr.com.sgr.Utils.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class Utils {

    private static Intent intent;

    /**
     * 截取日期
     *
     * @param
     * @return
     */

    public static int[] StringtoInt(String str) {
        int ret[] = new int[str.length()];
        StringTokenizer toKenizer = new StringTokenizer(str, "-");
        int i = 0;
        while (toKenizer.hasMoreElements()) {
            ret[i++] = Integer.valueOf(toKenizer.nextToken());
        }
        return ret;
    }

    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^0?(13[0-9]|15[012356789]|18[012356789]|14[57]|17[0678])[0-9]{8}"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * Intent跳转
     *
     * @param  packageContext 当前activity，cls 跳转的activity
     * @return String  时间
     */
    public static void toNextActivity(Activity packageContext, Class<?> cls) {
        intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);
    }
    /**
     * 时间格式化
     *
     * @param  date 日期
     * @return String  时间
     */

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 时间格式化
     *
     * @param  date 日期
     * @return String  时间
     */

    public static String getTime_hm(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd hh:mm");
        return format.format(date);
    }



}

package cn.sgr.zmr.com.sgr.Utils.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bean.entity.SearchRecent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class Utils {

    private static Intent intent;
     static long lastClickTime;

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


    /**
     * 增加fragment 到 activity里
     *
     * @param
     * @return S
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    /**
     * 增加fragment 到 activity里 传递String 内容
     *
     * @param
     * @return S
     */
    public static void addFragmentToActivityAddContent (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId,String content) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        Bundle bundle = new Bundle();
        bundle.putString(UtilKey.VOICE_KEY, content);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();

    }



    //防止被快速多次点击
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD <= 500;
    }
    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
//				如果需要多候选结果，解析数组其他字段
//				for(int j = 0; j < items.length(); j++)
//				{
//					JSONObject obj = items.getJSONObject(j);
//					ret.append(obj.getString("w"));
//				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }


    public static List<SearchRecent> orderDes (List<SearchRecent>  data){
        List<SearchRecent> change = new ArrayList<>();
        if(data!=null&&data.size()>0){
            for(int i=data.size()-1;i>=0;i--){
                change.add(data.get(i));
            }

        }

        return change;
    }

}

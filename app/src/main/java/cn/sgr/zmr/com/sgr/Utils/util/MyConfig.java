package cn.sgr.zmr.com.sgr.Utils.util;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class MyConfig {

    //指定图片，媒体的缓存路径
    public static String FILEPATH = Environment.getExternalStorageDirectory() + "/.makeDreamer/.cache/.baby/";
    public static String FILEPATH1 = Environment.getExternalStorageDirectory() + "/makeDreamer/cache/baby/";

    //指定图片，我的头像的路径
    public static String FILEPATH_MY = Environment.getExternalStorageDirectory() + "/.makeDreamer/.cache/.user/";
    public static String imageName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        String time = format.format(date);
        String imageName = "IMG_" + time + ".jpg";
        return imageName;
    }
}

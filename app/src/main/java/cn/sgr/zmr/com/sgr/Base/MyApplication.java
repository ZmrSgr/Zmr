package cn.sgr.zmr.com.sgr.Base;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 沈国荣 on 2016/8/4 0004.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private List<Activity> activitys = null;
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }
    private MyApplication() {
        activitys = new LinkedList();
    }
    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
    // ���Activity��������
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if(!activitys.contains(activity)){
                activitys.add(activity);
            }
        }else{
            activitys.add(activity);
        }

    }

    // ��������Activity��finish
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
        System.exit(0);
    }
}

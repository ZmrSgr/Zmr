package cn.sgr.zmr.com.sgr.Base;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;

import com.activeandroid.ActiveAndroid;

import java.util.LinkedList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Utils.util.BluetoothSet;

/**
 * Created by 沈国荣 on 2016/8/4 0004.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private List<Activity> activitys = null;
    public static String DeviceName;
    private BluetoothSet mBluetoothSet = null;			//传递蓝牙对象
    private Handler mHandler = null;
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ActiveAndroid.initialize(this);

    }
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if(!activitys.contains(activity)){
                activitys.add(activity);
            }
        }else{
            activitys.add(activity);
        }

    }

    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    public BluetoothSet getBluetoothSet(){
        return mBluetoothSet;
    }

    public void setBluetoothSet(BluetoothSet mbts) {
        this.mBluetoothSet = mbts;
    }

    public void setHandler(Handler handler)
    {
        this.mHandler = handler;
    }

    public Handler getHandler()
    {
        return mHandler;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}

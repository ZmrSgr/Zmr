package cn.sgr.zmr.com.sgr.Base;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;

import com.activeandroid.ActiveAndroid;
import com.testin.agent.TestinAgent;
import com.umeng.socialize.PlatformConfig;

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
        //初始化云测
        TestinAgent.init(this, "019cc0a31ffab5740c685c6cf3a2ae18", null);
        //初始化activite数据库
        ActiveAndroid.initialize(this);
//        友盟分享和登录
        //微信 appid appsecret
        PlatformConfig.setWeixin("wxafceb940b5fd67a0", "ca3653563244ab1ab4056464446403ab");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("1109305130","12bf679612df94be5a7bc6459fb93f75");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105570889", "vx7p91NQqmKW9YAD");


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

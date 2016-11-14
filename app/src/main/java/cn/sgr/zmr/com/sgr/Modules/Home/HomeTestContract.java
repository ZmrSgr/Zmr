package cn.sgr.zmr.com.sgr.Modules.Home;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.annotation.ColorRes;
import android.view.View.OnClickListener;

import com.bean.entity.Baby;

import java.util.Date;
import java.util.List;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;
import cn.sgr.zmr.com.sgr.View.MsgDialog;

/**
 * Created by zuky on 2016/9/7 0007.
 */
public class HomeTestContract {

    interface View extends BaseView<Presenter> {

        void showProgressDialog(FragmentManager manager);//显示进度条

        void cancelProgressDialog();//隐藏进度条

        void clearData();//清楚数据

        void setDate(Baby baby);//设置宝宝数据

//        void initBlue();//初始化蓝牙

        // TODO
        void setBoundedBabyData(Baby baby);

        // TODO 参数待改，可能有变化
        void showChooseBabyDialog(List<Baby> babyList, String positiveBtnText, String negativeBtnText);

        // TODO
        /**
         * 显示消息对话框
         * @param title
         *          标题
         * @param optimisticBtnText
         *          右边确定按钮文本
         * @param negativeBtnText
         *          左边取消按钮文本
         * @param positiveBtnListener
         *          右边确定按钮监听器
         * @param negativeBtnListener
         *          左边取消按钮监听器
         */
        void showMsgDialog(String title, String message, String optimisticBtnText, String negativeBtnText,
                           DialogInterface.OnClickListener positiveBtnListener,
                           DialogInterface.OnClickListener negativeBtnListener);

        void setTemperatureDisplay(int temperature, @ColorRes int themeColor);

        void setTemperatureStateDisplay(String temperatureState, @ColorRes int themeColor);

        void setBatteryInfoDisplay(String batteryInfo, String batteryNum, @ColorRes int themeColor);

        void setCircularBarStatus(int startNum, int endNum, int duration, @ColorRes int themeColor);

        void setTopBarLeftText(String text);

        void setHeight(int height);

        void setWeight(int weight);

        void setBirthday(Date date);

        boolean isActive();//目的是为了解决内存泄漏
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化蓝牙
         * @param activity
         */
        void initBlueTooth(Activity activity);//初始化蓝牙

        /**
         * 连接设备
         * @param activity
         */
        void linkDevice(Activity activity);//连接设备

        void initDevice(Activity activity);

        /**
         * 设置要绑定的宝宝
         * @param boundedBaby
         */
        void setBoundedBaby(Baby boundedBaby);

        /**
         * 绑定宝宝
         */
        void boundBaby();//绑定宝宝

        /**
         *
         */
        void boundBabyandDevice(String DeviceName);

        /**
         * 新建宝宝
         */
        void createBaby();// 新建宝宝

        /**
         * 发送指令
         * @param activity
         * @param deviceAddress
         */
        void sendCmd(Activity activity, String deviceAddress);// 发送指令

        void closeDevice();


    }
}

package cn.sgr.zmr.com.sgr.Modules.Home.Module.Device;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import java.util.List;

import cn.sgr.zmr.com.sgr.Base.BasePresenter;
import cn.sgr.zmr.com.sgr.Base.BaseView;
import cn.sgr.zmr.com.sgr.Common.Model.data.User;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public interface DeviceListContract {

    interface View extends BaseView<Presenter> {


        boolean isActive();//目的是为了解决内存泄漏

        void showProgressDialog();//显示进度条

        void cancelProgressDialog();//隐藏进度条

        void showData( List<String> lstDevices );//搜索到设备，然后显示

        void setTitle(String title);//设置标题

        void nextActivity(String address);//连接跳转
    }

    interface Presenter extends BasePresenter {

        void doDiscovery();//查找硬件设备

        void doContract(int postion);//连接硬件

        void startDiscovery();//蓝牙开始搜索

        void cancelDiscovery();//蓝牙取消搜索

        void unregisterReceiver();//取消广播监听
    }
}

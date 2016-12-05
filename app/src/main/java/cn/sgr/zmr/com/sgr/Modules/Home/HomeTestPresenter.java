package cn.sgr.zmr.com.sgr.Modules.Home;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bean.entity.Baby;
import com.example.administrator.scannerlib.DeviceSanListActivity;

import java.lang.annotation.Inherited;

import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Common.ShowAlarm;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBaby_Activity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.BluetoothUtil.BleDeviceHelp;
import cn.sgr.zmr.com.sgr.Utils.BluetoothUtil.ICmdModel;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.MsgDialog;

/**
 * Created by zuky on 2016/9/7 0007.
 */
public class HomeTestPresenter implements HomeTestContract.Presenter {
    @NonNull
    private final HomeTestContract.View registerView;
    Context context;

    public HomeTestPresenter(Context contexts, @NonNull HomeTestContract.View registerView) {
        this.context = contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象

    }

    @Override
    public void start() {
        registerView.showUserInfo(context, UserInfo.getInstance(context).getNickName(),UserInfo.getInstance(context).getMyPhone(),UserInfo.getInstance(context).getAvatar());
    }

}

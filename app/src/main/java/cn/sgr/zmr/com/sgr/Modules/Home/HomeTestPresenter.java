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
public class HomeTestPresenter implements HomeTestContract.Presenter, BleDeviceHelp.BleReceiver {
    private static final int REQUEST_SELECT_DEVICE = 1;

    private BleDeviceHelp bleRe;//蓝牙帮助类

    public static boolean isConnState = true;//是否连接
    public static boolean isAlarm = true;//true表示可以弹出闹铃，
    public static boolean isAway = true;//true表示可以弹出闹铃，
    public static boolean isBoundedBaby = false;// 是否已经绑定了宝宝

    TimeAway AwayTime;//30分钟之后 才允许防丢失闹钟才能再次弹出
    TimeAway AlarmTime;//30分钟之后 才允许温度闹钟才能再次弹出

    private int startInt, endInt;

    @NonNull
    private final HomeTestContract.View registerView;
    Context context;
    private DaoCacheManage daoManage;

    private Baby boundedBaby;

    public HomeTestPresenter(Context contexts, @NonNull HomeTestContract.View registerView) {
        this.context = contexts;
        this.registerView = registerView;
        this.daoManage = new DaoCacheManage(contexts);
        registerView.setPresenter(this);//互相拥有对象
        bleRe = new BleDeviceHelp(contexts, this);
    }

    @Override
    public void start() {
        if (daoManage.getCurryBaby() == null) {
            registerView.clearData();
        } else {
            registerView.setDate(daoManage.getCurryBaby());
        }
    }

    @Override
    public void initBlueTooth(Activity activity) {
        if (isBLEEnabled(activity)) {
            if (Setting.getInstance(activity).getDeviceAddress().equals("0")) {

            } else {

                String deviceAddress = Setting.getInstance(activity).getDeviceAddress();



        /*       if(isConnState){


               }else{
                   bleRe.disconnect();
               }*/
//                        String mac = showMacTv.getText().toString().trim();
                if (bleRe.mBluetoothDeviceAddress != null) {
                    bleRe.close();
                }
                if (!TextUtils.isEmpty(deviceAddress)) {
                    bleRe.mBluetoothDeviceAddress = deviceAddress;
                    Setting.getInstance(activity).setDeviceAddress(deviceAddress);//保存硬件地址
                    bleRe.connect();
                }
                isConnState = false;
//                        top_view_left_text.setText("已连接");

            }
        } else {
            showBLEDialog(activity);
        }
    }

    @Override
    public void linkDevice(final Activity activity) {

        if (isBLEEnabled(activity)) {
            if (isConnState) {
                if (daoManage.getBabys().size() == 0) {
                    registerView.showMsgDialog("提示", "您未新建宝宝信息，是否新建", "是", "否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                } else if (daoManage.getBabys().size() == 1) {
                    Intent newIntent = new Intent(activity, DeviceSanListActivity.class);
                    activity.startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);

                } else {
                    registerView.showChooseBabyDialog(daoManage.getBabys(), "绑定", "取消");
                }
            } else {
                Intent newIntent = new Intent(activity, DeviceSanListActivity.class);
                activity.startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
            }
        } else {
//                    Toast.makeText(getActivity(), "Please Open Bluetooth !", Toast.LENGTH_SHORT).show();
            showBLEDialog(activity);
        }

    }

    @Override
    public void initDevice(Activity activity) {

    }

    @Override
    public void setBoundedBaby(Baby boundedBaby) {
        this.boundedBaby = boundedBaby;
//        registerView.setBoundedBabyData(boundedBaby);
    }

    @Override
    public void boundBaby() {
        if (boundedBaby != null){
            daoManage.setCurryBaby(boundedBaby, "0");// 暂时设置为-1作为绑定了宝宝的标识，用于启动界面时设置页面数据
            registerView.setBoundedBabyData(boundedBaby);
        } else {

        }
    }

    @Override
    public void boundBabyandDevice(String deviceName) {
        if(!TextUtils.isEmpty(deviceName) && deviceName.equals("0")){
            daoManage.setCurryBaby(boundedBaby, deviceName);
        } else {
            Log.e("boundBabyandDevice", "boundBabyandDevice Error: deviceName 为空或为0" );
        }

    }

    @Override
    public void createBaby() {
//        Intent i = new Intent(activity, AddBaby_Activity.class);
//        activity.startActivity(i);
    }

    @Override
    public void sendCmd(Activity activity, String deviceAddress) {

        if (isConnState) {
//              String mac = showMacTv.getText().toString().trim();
            if (bleRe.mBluetoothDeviceAddress != null) {
                bleRe.close();
            }
            if (!TextUtils.isEmpty(deviceAddress)) {
                bleRe.mBluetoothDeviceAddress = deviceAddress;
                Setting.getInstance(activity).setDeviceAddress(deviceAddress);//保存硬件地址
                bleRe.connect();
            }
            isConnState = false;
//                        top_view_left_text.setText("已连接");
        } else {
            bleRe.disconnect();
            isConnState = true;
            registerView.setTopBarLeftText("连接");
        }
    }

    @Override
    public void closeDevice() {
        bleRe.close();
    }

    /**
     * 判断蓝牙是否开启
     *
     * @return
     */
    public boolean isBLEEnabled(Context context) {
        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter != null && adapter.isEnabled();
    }

    public void isDistantDisconnect(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Setting.getInstance(activity).IsLose()) {//防丢功能已经开启
                    if (isAway) {//防丢功能没有处理
                        isAway = false;
                        //计算30分钟之后闹钟继续闹
                        AlarmTime = new TimeAway(1800000, 1000);// 构造CountDownTimer对象
                        AlarmTime.start();
                        Intent intent = new Intent(activity, ShowAlarm.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(UtilKey.ALARM_KEY_ALARM, "1");
                        activity.startActivity(intent);
                    }
                }
            }
        });
    }

    private void showBLEDialog(Activity activity) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 2);
    }

    @Override
    public void getConnState(final int mConnectionState) {
        ((HomeTestFragment) registerView).getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (mConnectionState) {
                    case BleDeviceHelp.CON_STATE_DISCONNECTED://断开连接
                        DisConnectState();

                        break;

                    case BleDeviceHelp.CON_STATE_CLOSED://device closed. never can be used anymore.
                        DisConnectState();
                        break;
                    case BleDeviceHelp.CON_STATE_CONNECTED:
//                        top_view_left_text.setText(getString(R.string.home_connected));
                        registerView.setTopBarLeftText(((HomeTestFragment) registerView).getActivity().getString(R.string.home_connected));
                        ICmdModel.CmTxRequestBatteryInfo ctrbi = new ICmdModel.CmTxRequestBatteryInfo();
                        bleRe.sendCmd(ctrbi);

                        ICmdModel.CmTxRequestTemperatureOn ctrbi2 = new ICmdModel.CmTxRequestTemperatureOn();
                        bleRe.sendCmd(ctrbi2);
                        break;
                    case BleDeviceHelp.CON_STATE_CONNECTING:
                    case BleDeviceHelp.CON_STATE_DISCOVERING:
                    case BleDeviceHelp.CON_STATE_ENABLING:
//                        top_view_left_text.setText(getString(R.string.home_connectting));
                        registerView.setTopBarLeftText(((HomeTestFragment) registerView).getActivity().getString(R.string.home_connectting));
                        break;
                    default:
                        DisConnectState();
                }
            }
        });
    }

    private ICmdModel.CmRxBatteryInfo mBatteryInfo = null;
    private ICmdModel.CmRxTemperature mTemperature = null;

    @Override
    public void getCmdData(byte[] characteristic) {
        int id = ICmdModel.cmdIdFromBytes(characteristic, 0);

        switch (id) {
            case ICmdModel.CmRxBatteryInfo.ID:
                mBatteryInfo = new ICmdModel.CmRxBatteryInfo();
                mBatteryInfo.fromCmdBytes(characteristic);
                registerView.setBatteryInfoDisplay(((HomeTestFragment) registerView).getActivity().getString(R.string.home_battery), mBatteryInfo.toString(), 0);
                Setting.getInstance(((HomeTestFragment) registerView).getActivity()).setBattery(mBatteryInfo.toString());
//                ((HomeTestFragment)registerView).getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        user_top_textview.setText(getString(R.string.home_battery) + mBatteryInfo.toString());
//                        Setting.getInstance(getActivity()).setBattery(mBatteryInfo.toString());
//                    }
//                });
                break;
            case ICmdModel.CmRxTemperature.ID:
                mTemperature = new ICmdModel.CmRxTemperature();
                mTemperature.fromCmdBytes(characteristic);
                if (Utils.isNumber(mTemperature.toString())) {
                    endInt = (int) Math.floor(Float.valueOf(mTemperature.toString()));
                    //闹铃
                    if (Setting.getInstance(((HomeTestFragment) registerView).getActivity()).IsAlarm()) {
                        if (isAlarm) {
                            if (Float.valueOf(mTemperature.toString()) > Float.valueOf(Setting.getInstance(((HomeTestFragment) registerView).getActivity()).getTemp())) {//报警字体显示的是红色
                                //计算十分钟之后闹钟继续闹
                                AwayTime = new TimeAway(1800000, 1000);// 构造CountDownTimer对象
                                AwayTime.start();

                                isAlarm = false;
                                Intent intent = new Intent(((HomeTestFragment) registerView).getActivity(), ShowAlarm.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(UtilKey.ALARM_KEY_ALARM, "0");
                                ((HomeTestFragment) registerView).getActivity().startActivity(intent);

                            }
                        }
                    }
                    if (Float.valueOf(mTemperature.toString()) > 35.9 && Float.valueOf(mTemperature.toString()) < 37.5) {
                        registerView.setTemperatureStateDisplay(((HomeTestFragment) registerView).getActivity().getString(R.string.home_bottom), R.color.them_bg);
                        registerView.setBatteryInfoDisplay("", "", R.color.them_bg);
                        registerView.setTemperatureDisplay(Integer.parseInt(mTemperature.toString()), R.color.them_bg);
                        registerView.setCircularBarStatus(startInt, endInt, 900, R.color.them_bg);
                    } else if (Float.valueOf(mTemperature.toString()) <= 35.9) {
                        registerView.setTemperatureStateDisplay(((HomeTestFragment) registerView).getActivity().getString(R.string.home_state_low), R.color.them_text);
                        registerView.setBatteryInfoDisplay("", "", R.color.them_text);
                        registerView.setTemperatureDisplay(Integer.parseInt(mTemperature.toString()), R.color.them_text);
                        registerView.setCircularBarStatus(startInt, endInt, 900, R.color.them_text);
                    } else if (Float.valueOf(mTemperature.toString()) > 37.5) {
                        registerView.setTemperatureStateDisplay(((HomeTestFragment) registerView).getActivity().getString(R.string.home_state_high), R.color.red_light);
                        registerView.setBatteryInfoDisplay("", "", R.color.red_light);
                        registerView.setTemperatureDisplay(Integer.parseInt(mTemperature.toString()), R.color.red_light);
                        registerView.setCircularBarStatus(startInt, endInt, 900, R.color.red_light);
                    }
                }
                break;
        }
    }

    @Override
    public void isDistantDisconnect() {
        ((HomeTestFragment) registerView).getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Setting.getInstance(((HomeTestFragment) registerView).getActivity()).IsLose()) {//防丢功能已经开启
                    if (isAway) {//防丢功能没有处理
                        isAway = false;
                        //计算30分钟之后闹钟继续闹
                        AlarmTime = new TimeAway(1800000, 1000);// 构造CountDownTimer对象
                        AlarmTime.start();
                        Intent intent = new Intent(((HomeTestFragment) registerView).getActivity(), ShowAlarm.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(UtilKey.ALARM_KEY_ALARM, "1");
                        ((HomeTestFragment) registerView).getActivity().startActivity(intent);
                    }
                }
            }
        });
    }

    private void DisConnectState() {
        registerView.setTopBarLeftText(((HomeTestFragment) registerView).getActivity().getString(R.string.home_disconnectting));
        registerView.setBatteryInfoDisplay(((HomeTestFragment) registerView).getActivity().getString(R.string.home_battery), "", R.color.them_bg);
        registerView.setTemperatureDisplay(0, R.color.them_bg);
        registerView.setTemperatureStateDisplay(((HomeTestFragment) registerView).getActivity().getString(R.string.home_bottom), R.color.them_bg);
//        top_view_left_text.setText(getString(R.string.home_disconnectting));
//        user_top_textview.setText(getString(R.string.home_battery));
//        value_info_textview.setText("0");
//        user_bottom_textview.setText(getString(R.string.home_bottom));
    }

    /**
     * 定义一个倒计时的内部类   计算多久没有应答
     */
    class TimeAway extends CountDownTimer {
        public TimeAway(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (isAway) {

            } else {
                isAway = true;
            }


            if (isAlarm) {

            } else {
                isAlarm = true;
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示

        }
    }
}

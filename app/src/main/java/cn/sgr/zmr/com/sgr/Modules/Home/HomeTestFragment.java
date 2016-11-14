package cn.sgr.zmr.com.sgr.Modules.Home;

import android.app.Activity;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bean.entity.Baby;
import com.example.administrator.scannerlib.DeviceSanListActivity;
import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.Common.ShowAlarm;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.CirclePagerAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.SettingDevice.SettingDeviceActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.BluetoothUtil.BleDeviceHelp;
import cn.sgr.zmr.com.sgr.Utils.BluetoothUtil.ICmdModel;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.DemoView;
import cn.sgr.zmr.com.sgr.View.MsgDialog;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeTestFragment extends BaseFragment implements HomeTestContract.View
//        , BleDeviceHelp.BleReceiver
{


    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;


    @BindView(R.id.tv_update)
    ImageView tv_update;

    TextView home_unit_top;
    TextView user_center_unit;
    TextView home_unit_midle;

    //    性别
    @BindView(R.id.iv_gender)
    ImageView iv_gender;

    @BindView(R.id.iv_location)
    ImageView iv_location;


    @BindView(R.id.tv_username)
    TextView tv_username;

    @BindView(R.id.tv_hight)
    TextView tv_hight;

    @BindView(R.id.tv_weight)
    TextView tv_weight;

    @BindView(R.id.tv_date)
    TextView tv_date;

    private static final int REQUEST_SELECT_DEVICE = 1;

//    public static boolean isConnState = true;//是否连接

//    private BleDeviceHelp bleRe;//蓝牙帮助类

//    private int startInt, endInt;//进度条的终始

//    TimeAway AwayTime;//30分钟之后 才允许防丢失闹钟才能再次弹出
//    TimeAway AlarmTime;//30分钟之后 才允许温度闹钟才能再次弹出



/*    @BindView(R.id.home_temp)
    JellyToggleButton home_temp;*/

//    private CircularBarPager mCircularBarPager;

    @BindView(R.id.circularBarPager)
    CircularBarPager mCircularBarPager;
    TextView value_info_textview, user_top_textview, user_bottom_textview;
    //    Button button;
    DemoView ViewT;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    //    public static boolean isAlarm = true;//true表示可以弹出闹铃，
//    public static boolean isAway = true;//true表示可以弹出闹铃，
    HomeTestContract.Presenter mPresenter;

    //单例 模式
    public static HomeTestFragment newInstance() {
        return new HomeTestFragment();
    }

    //   构造方法
    public HomeTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        intView();

        return view;
    }

    private void intView() {
//        bleRe = new BleDeviceHelp(getActivity(), this);
        ViewT = new DemoView(getActivity());
        mCircularBarPager.setViewPagerAdapter(new CirclePagerAdapter(getActivity(), ViewT));
        top_view_title.setText(getString(R.string.check_temp));
        top_view_back.setVisibility(View.GONE);
        top_view_left_text.setVisibility(View.VISIBLE);
        top_view_left_text.setText(getString(R.string.connected));
        top_view_right_text.setVisibility(View.VISIBLE);
        top_view_right_text.setText(getString(R.string.set_more));
        user_top_textview = (TextView) ViewT.findViewById(R.id.user_top_textview);
        value_info_textview = (TextView) ViewT.findViewById(R.id.value_info_textview);
        user_bottom_textview = (TextView) ViewT.findViewById(R.id.user_bottom_textview);
        user_center_unit = (TextView) ViewT.findViewById(R.id.user_center_unit);
        home_unit_top = (TextView) ViewT.findViewById(R.id.user_top_unit);
        home_unit_midle = (TextView) ViewT.findViewById(R.id.user_center_unit);
        mCircularBarPager.getCircularBar().setStartLineEnabled(false);
//        initBlue();
        // TODO 完成后去掉注释给上面加注释
        mPresenter.initBlueTooth(getActivity());
    }

    //当绑定宝宝的时候，显示数据
    // TODO 不调用， 可以去掉
    @Override
    public void setDate(Baby baby) {
        tv_weight.setText(baby.getWeight());
        tv_username.setText(baby.getName());
        tv_hight.setText(baby.getHight());
        tv_date.setText(baby.getAge());
        if (baby.getSex() != null && baby.getSex().equals(getString(R.string.homeboy))) {

            iv_gender.setImageResource(R.drawable.baby_boy);
        } else {
            iv_gender.setImageResource(R.drawable.baby_girl);
        }

    }


    @Override
    public void showProgressDialog(FragmentManager manager) {
        super.showProgressDialog(manager);
    }

//    // TODO 不调用
//    @Override
//    public void initBlue() {
//        if (isBLEEnabled()) {
//            if (Setting.getInstance(getActivity()).getDeviceAddress().equals("0")) {
//
//            } else {
//
//                String deviceAddress = Setting.getInstance(getActivity()).getDeviceAddress();
//
//        /*       if(isConnState){
//
//               }else{
//                   bleRe.disconnect();
//               }*/
////                        String mac = showMacTv.getText().toString().trim();
//                if (bleRe.mBluetoothDeviceAddress != null) {
//                    bleRe.close();
//                }
//                if (!TextUtils.isEmpty(deviceAddress)) {
//                    bleRe.mBluetoothDeviceAddress = deviceAddress;
//                    Setting.getInstance(getActivity()).setDeviceAddress(deviceAddress);//保存硬件地址
//                    bleRe.connect();
//                }
//                isConnState = false;
////                        top_view_left_text.setText("已连接");
//
//            }
//        } else {
//            showBLEDialog();
//        }
//    }

    @Override
    public void clearData() {
        tv_date.setText("0");
        tv_hight.setText("0");
        tv_username.setText("");
        tv_weight.setText("0");
        iv_gender.setVisibility(View.GONE);
        mCircularBarPager.getCircularBar().animateProgress(0, 0, 900);
    }

    @Override
    public void setBoundedBabyData(Baby baby) {
        tv_weight.setText(baby.getWeight());
        tv_username.setText(baby.getName());
        tv_hight.setText(baby.getHight());
        tv_date.setText(baby.getAge());
        if (baby.getSex() != null && baby.getSex().equals(getString(R.string.homeboy))) {

            iv_gender.setImageResource(R.drawable.baby_boy);
        } else {
            iv_gender.setImageResource(R.drawable.baby_girl);
        }
    }

    @Override
    public void showChooseBabyDialog(final List<Baby> babyList, String positiveBtnText, String negativeBtnText) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_content_boundbaby, null);
        // 显示可供选择的宝宝列表
        MsgDialog.Builder builder = new MsgDialog.Builder(getActivity());
        builder.setTitle("请先绑定宝宝")
                .setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.boundBaby();
                        mPresenter.linkDevice(getActivity());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setContentView(view, new MsgDialog.Builder.ContentViewIniter() {
                    @Override
                    public void initContentView(View contentView) {
                        RadioGroup rgChooseBaby = (RadioGroup) contentView.findViewById(R.id.dialog_content_bound_baby_rg);

                        if (babyList != null){
                            for(int i = 0; i < babyList.size(); i++){
                                RadioButton rbtn = new RadioButton(getActivity());
                                rbtn.setText(babyList.get(i).getName());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(0, 20, 0, 0);
                                rbtn.setLayoutParams(lp);
                                final int boundedPosition = i;
                                rbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked){
                                            mPresenter.setBoundedBaby(babyList.get(boundedPosition));
                                        }
                                    }
                                });
                                rgChooseBaby.addView(rbtn);
                            }
                        }
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showMsgDialog(String title, String message, String positiveBtnText, String negativeBtnText,
                              DialogInterface.OnClickListener positiveBtnListener,
                              DialogInterface.OnClickListener negativeBtnListener) {
        MsgDialog.Builder builder = new MsgDialog.Builder(getActivity());
        builder.setTitle("消息")
                .setPositiveButton(positiveBtnText, positiveBtnListener)
                .setNegativeButton(negativeBtnText, negativeBtnListener)
                .setMessage(message)
                .create()
                .show();
    }

    @Override
    public void setTemperatureDisplay(final int temperature, @ColorRes final int themeColor) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (temperature >= 0 && temperature <= 50) {
                    value_info_textview.setText(String.valueOf(temperature));
                }
                user_top_textview.setTextColor(getResources().getColor(themeColor));
                user_center_unit.setTextColor(getResources().getColor(themeColor));
            }
        });
    }

    @Override
    public void setTemperatureStateDisplay(final String temperatureState, @ColorRes final int themeColor) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(temperatureState)) {
                    user_bottom_textview.setText(temperatureState);
                }
                user_bottom_textview.setTextColor(getResources().getColor(themeColor));

            }
        });
    }

    @Override
    public void setBatteryInfoDisplay(final String batteryInfo, final String batteryNum, @ColorRes final int themeColor) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(batteryInfo)) {
                    user_top_textview.setText(batteryInfo + batteryNum);
                }
                user_top_textview.setTextColor(getResources().getColor(themeColor));
                Setting.getInstance(getActivity()).setBattery(batteryInfo);
            }
        });
    }

    @Override
    public void setCircularBarStatus(final int startNum, final int endNum, final int duration, @ColorRes final int themeColor) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (endNum > 0) {
                    mCircularBarPager.getCircularBar().animateProgress(startNum, endNum, duration);
                }
                mCircularBarPager.getCircularBar().setClockwiseOutlineArcColor(getResources().getColor(themeColor));
                mCircularBarPager.getCircularBar().setCounterClockwiseArcColor(getResources().getColor(themeColor));
                mCircularBarPager.getCircularBar().setClockwiseReachedArcColor(getResources().getColor(themeColor));
            }
        });

    }

    @Override
    public void setTopBarLeftText(String text) {
        if (!TextUtils.isEmpty(text)) {
            top_view_left_text.setText(text);
        }
    }

    @Override
    public void setHeight(int height) {
        if (height > 0) {
            tv_hight.setText(String.valueOf(height));
        }
    }

    @Override
    public void setWeight(int weight) {
        if (weight > 0) {
            tv_weight.setText(String.valueOf(weight));
        }
    }

    @Override
    public void setBirthday(Date date) {
        if (date != null) {
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            tv_date.setText(formater.format(date));
        }
    }

    @OnClick({R.id.top_view_right_text, R.id.top_view_left_text, R.id.iv_avatar, R.id.top_view_title, R.id.tv_update, R.id.iv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_right_text:
                Utils.toNextActivity(getActivity(), SettingDeviceActivity.class);
                break;
            case R.id.top_view_left_text:

//                if (isBLEEnabled()) {
//                    Intent newIntent = new Intent(getActivity(), DeviceSanListActivity.class);
//                    startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
//                } else {
////                    Toast.makeText(getActivity(), "Please Open Bluetooth !", Toast.LENGTH_SHORT).show();
//                    showBLEDialog();
//                }

                mPresenter.linkDevice(getActivity());


           /*     cancelProgressDialog();

               Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);*/
                break;

            case R.id.iv_avatar:
                Utils.toNextActivity(getActivity(), ChartActivity.class);
                break;

            case R.id.iv_location:
//                Utils.toNextActivity(getActivity(),LocationActivity.class);
//                tv_update.setTargetProgress(360);
                break;

            case R.id.tv_update:
//                if(UserInfo.getInstance(getActivity()).hasSignIn()){
//                    Utils.toNextActivity(getActivity(), SynchronizeActivity.class);
               /* }else{
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    Utils.toNextActivity(getActivity(),LoginActivity.class);
                }*/

                break;


        }
    }

//    private void showBLEDialog() {
//        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//        startActivityForResult(enableBtIntent, 2);
//    }


    /**
     * 判断蓝牙是否开启
     *
     * @return
     */
    public boolean isBLEEnabled() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter != null && adapter.isEnabled();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datae) {
        switch (requestCode) {
            case REQUEST_SELECT_DEVICE:
                if (resultCode == Activity.RESULT_OK && datae != null) {
                    String deviceAddress = datae.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = datae.getStringExtra(BluetoothDevice.EXTRA_NAME);
                    String deviceRssi = datae.getStringExtra(BluetoothDevice.EXTRA_RSSI);

                    top_view_left_text.setText(deviceName);

                    user_bottom_textview.setText(deviceRssi);

                    mPresenter.sendCmd(getActivity(), deviceAddress);

                    mPresenter.boundBabyandDevice(deviceName);
//                    if (isConnState) {
////                        String mac = showMacTv.getText().toString().trim();
//                        if (bleRe.mBluetoothDeviceAddress != null) {
//                            bleRe.close();
//                        }
//                        if (!TextUtils.isEmpty(deviceAddress)) {
//                            bleRe.mBluetoothDeviceAddress = deviceAddress;
//                            Setting.getInstance(getActivity()).setDeviceAddress(deviceAddress);//保存硬件地址
//                            bleRe.connect();
//                        }
//                        isConnState = false;
////                        top_view_left_text.setText("已连接");
//                    } else {
//                        bleRe.disconnect();
//                        isConnState = true;
//                        top_view_left_text.setText(getString(R.string.connected));
//                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(HomeTestContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        bleRe.close();
        mPresenter.closeDevice();
    }

//    @Override
//    public void getConnState(final int mConnectionState) {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                switch (mConnectionState) {
//                    case BleDeviceHelp.CON_STATE_DISCONNECTED://断开连接
//                        DisConnectState();
//
//                        break;
//
//                    case BleDeviceHelp.CON_STATE_CLOSED://device closed. never can be used anymore.
//                        DisConnectState();
//                        break;
//                    case BleDeviceHelp.CON_STATE_CONNECTED:
//                        top_view_left_text.setText(getString(R.string.home_connected));
//
//                        ICmdModel.CmTxRequestBatteryInfo ctrbi = new ICmdModel.CmTxRequestBatteryInfo();
//                        bleRe.sendCmd(ctrbi);
//
//
//                        ICmdModel.CmTxRequestTemperatureOn ctrbi2 = new ICmdModel.CmTxRequestTemperatureOn();
//                        bleRe.sendCmd(ctrbi2);
//                        break;
//                    case BleDeviceHelp.CON_STATE_CONNECTING:
//                    case BleDeviceHelp.CON_STATE_DISCOVERING:
//                    case BleDeviceHelp.CON_STATE_ENABLING:
//                        top_view_left_text.setText(getString(R.string.home_connectting));
//                        break;
//                    default:
//                        DisConnectState();
//                }
//            }
//        });
//
//    }

    private void DisConnectState() {
        top_view_left_text.setText(getString(R.string.home_disconnectting));
        user_top_textview.setText(getString(R.string.home_battery));
        value_info_textview.setText("0");
        user_bottom_textview.setText(getString(R.string.home_bottom));
    }


//    private ICmdModel.CmRxBatteryInfo mBatteryInfo = null;
//    private ICmdModel.CmRxTemperature mTemperature = null;

//    @Override
//    public void getCmdData(byte[] characteristic) {
//        int id = ICmdModel.cmdIdFromBytes(characteristic, 0);
//
//        switch (id) {
//            case ICmdModel.CmRxBatteryInfo.ID:
//
//
//                mBatteryInfo = new ICmdModel.CmRxBatteryInfo();
//                mBatteryInfo.fromCmdBytes(characteristic);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        user_top_textview.setText(getString(R.string.home_battery) + mBatteryInfo.toString());
//                        Setting.getInstance(getActivity()).setBattery(mBatteryInfo.toString());
//                    }
//                });
//                break;
//            case ICmdModel.CmRxTemperature.ID:
//                mTemperature = new ICmdModel.CmRxTemperature();
//                mTemperature.fromCmdBytes(characteristic);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        value_info_textview.setText(mTemperature.toString());
//                        if (Utils.isNumber(mTemperature.toString())) {
//                            endInt = (int) Math.floor(Float.valueOf(mTemperature.toString()));
//
//                            //闹铃
//                            if (Setting.getInstance(getActivity()).IsAlarm()) {
//                                if (isAlarm) {
//                                    if (Float.valueOf(mTemperature.toString()) > Float.valueOf(Setting.getInstance(getActivity()).getTemp())) {//报警字体显示的是红色
//                                        //计算十分钟之后闹钟继续闹
//                                        AwayTime = new TimeAway(1800000, 1000);// 构造CountDownTimer对象
//                                        AwayTime.start();
//
//
//                                        isAlarm = false;
//                                        Intent intent = new Intent(getActivity(), ShowAlarm.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        intent.putExtra(UtilKey.ALARM_KEY_ALARM, "0");
//                                        getActivity().startActivity(intent);
//
//                                    }
//                                }
//                            }
//
//
//                            //生病情况
//                            if (Float.valueOf(mTemperature.toString()) > 35.9 && Float.valueOf(mTemperature.toString()) < 37.5) {
//                                user_top_textview.setTextColor(getResources().getColor(R.color.them_bg));
//                                user_bottom_textview.setText(getString(R.string.home_bottom));
//                                user_bottom_textview.setTextColor(getResources().getColor(R.color.them_bg));
//                                user_center_unit.setTextColor(getResources().getColor(R.color.them_bg));
//                                value_info_textview.setTextColor(getResources().getColor(R.color.them_bg));
//                                mCircularBarPager.getCircularBar().setClockwiseOutlineArcColor(getResources().getColor(R.color.them_bg));
//                                mCircularBarPager.getCircularBar().setCounterClockwiseArcColor(getResources().getColor(R.color.them_bg));
//                                mCircularBarPager.getCircularBar().setClockwiseReachedArcColor(getResources().getColor(R.color.them_bg));
//
//
//                            }
//                            if (Float.valueOf(mTemperature.toString()) <= 35.9) {
//                                user_top_textview.setTextColor(getResources().getColor(R.color.them_text));
//                                user_bottom_textview.setText(getString(R.string.home_state_low));
//                                user_bottom_textview.setTextColor(getResources().getColor(R.color.them_text));
//                                user_center_unit.setTextColor(getResources().getColor(R.color.them_text));
//                                value_info_textview.setTextColor(getResources().getColor(R.color.them_text));
//                                mCircularBarPager.getCircularBar().setClockwiseOutlineArcColor(getResources().getColor(R.color.them_text));
//                                mCircularBarPager.getCircularBar().setCounterClockwiseArcColor(getResources().getColor(R.color.them_text));
//                                mCircularBarPager.getCircularBar().setClockwiseReachedArcColor(getResources().getColor(R.color.them_text));
//                            }
//                            if (Float.valueOf(mTemperature.toString()) > 37.5) {
//                                user_top_textview.setTextColor(getResources().getColor(R.color.red_light));
//                                user_bottom_textview.setText(getString(R.string.home_state_high));
//                                user_bottom_textview.setTextColor(getResources().getColor(R.color.red_light));
//                                value_info_textview.setTextColor(getResources().getColor(R.color.red_light));
//                                user_center_unit.setTextColor(getResources().getColor(R.color.red_light));
//                                mCircularBarPager.getCircularBar().setCounterClockwiseOutlineArcColor(getResources().getColor(R.color.red_light));
//                                mCircularBarPager.getCircularBar().setClockwiseOutlineArcColor(getResources().getColor(R.color.red_light));
//                                mCircularBarPager.getCircularBar().setCounterClockwiseArcColor(getResources().getColor(R.color.red_light));
//                                mCircularBarPager.getCircularBar().setClockwiseReachedArcColor(getResources().getColor(R.color.red_light));
//
//                            }
////                            35.9℃～37.5℃//正常体温
//
//                            //显示温度
//                            if (mCircularBarPager != null) {
//
//
//                                mCircularBarPager.getCircularBar().animateProgress(startInt, endInt * 2, 900);
//                            }
//                            startInt = endInt * 2;
//
//                        }
//
//
//                    }
//                });
//                break;
//        }
//    }

//    @Override
//    public void isDistantDisconnect() {
//        getActivity(). runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if(Setting.getInstance(getActivity()).IsLose()) {//防丢功能已经开启
//                    if (isAway) {//防丢功能没有处理
//                        isAway = false;
//                        //计算30分钟之后闹钟继续闹
//                        AlarmTime = new TimeAway(1800000, 1000);// 构造CountDownTimer对象
//                        AlarmTime.start();
//                        Intent intent = new Intent(getActivity(), ShowAlarm.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra(UtilKey.ALARM_KEY_ALARM, "1");
//                        getActivity().startActivity(intent);
//                    }
//                }
//            }
//        });
//
//    }

//    /**
//     * 定义一个倒计时的内部类   计算多久没有应答
//     */
//    class TimeAway extends CountDownTimer {
//        public TimeAway(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
//        }
//
//        @Override
//        public void onFinish() {// 计时完毕时触发
//            if(isAway){
//
//            }else {
//                isAway=true;
//            }
//
//
//            if(isAlarm){
//
//            }else {
//                isAlarm=true;
//            }
//
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {// 计时过程显示
//
//        }
//    }
}

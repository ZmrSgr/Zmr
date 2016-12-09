package cn.sgr.zmr.com.sgr.Modules.Home.Device;

import android.app.Activity;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.AlarmWay.AlarmWayActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Device.DeviceActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.SettingDevice.SettingDeviceActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Synchronize.SynchronizeActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.BuilderManager;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.MsgDialog;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeDeviceFragment extends BaseFragment implements HomeDeviceContract.View,OnBoomListener
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
    TextView user_center_unit;


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

    @BindView(R.id.charts)
    LineChart mChart;

    @BindView(R.id.bmb)
    BoomMenuButton bmb;

    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    //    public static boolean isAlarm = true;//true表示可以弹出闹铃，
//    public static boolean isAway = true;//true表示可以弹出闹铃，
    HomeDeviceContract.Presenter mPresenter;
    //单例 模式
    public static HomeDeviceFragment newInstance() {
        return new HomeDeviceFragment();
    }
    //   构造方法
    public HomeDeviceFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home_device, container, false);
        ButterKnife.bind(this, view);
        intView();
        return view;
    }
    private void intView() {
        bmb.setButtonEnum(ButtonEnum.TextInsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_4_1);
        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) addBuilder(i);
        bmb.setOnBoomListener(this);
        top_view_title.setText(getString(R.string.check_temp));
        top_view_back.setVisibility(View.VISIBLE);
        top_view_right_text.setVisibility(View.VISIBLE);
        top_view_right_text.setText(getString(R.string.connected));
        // TODO 完成后去掉注释给上面加注释
        mPresenter.initBlueTooth(getActivity());
        initialChart(mChart);
        addLineDataSet(mChart);
    }
    private void addBuilder(final int index) {
        bmb.addBuilder(BuilderManager.getTextInsideCircleButtonBuilder());
    }

    // 为LineChart增加LineDataSet
    private void addLineDataSet(LineChart mChart) {
        LineData data = getData(24, 2);
        mChart.setData(data);
    }
    private LineData getData(int count, float range) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 37;
            yVals.add(new Entry(i, val));
        }
        LineDataSet set1 = new LineDataSet(yVals, "时间体温");
        set1.setLineWidth(1.75f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(  getResources().getColor(R.color.them_bg));
        set1.setCircleColor(  getResources().getColor(R.color.them_bg));//圆圈的颜色
        set1.setLabel("时间体温");
        set1.setDrawValues(false);
        LineData data = new LineData(set1);
        return data;
    }
    // 初始化图表
    private void initialChart(LineChart mChart) {
        mChart.setDescription(" ");
        mChart.setNoDataTextDescription("暂时尚无数据");
        mChart.setTouchEnabled(true);
        // 可拖曳
        mChart.setDragEnabled(true);
        // 可缩放
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setPinchZoom(true);
        // 设置图表的背景颜色
//        mChart.setBackgroundColor(0xfff5f5f5);
        // 图表的注解(只有当数据集存在时候才生效)
        Legend l = mChart.getLegend();

        // 可以修改图表注解部分的位置
        // l.setPosition(LegendPosition.LEFT_OF_CHART);

        // 线性，也可是圆
        l.setForm(Legend.LegendForm.LINE);
        // 颜色
        l.setTextColor(  getResources().getColor(R.color.them_bg));
        // x坐标轴
        XAxis xl = mChart.getXAxis();
//        xl.setTextColor(0xff00897b);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMaxValue(24f);
        xl.setAxisMinValue(0f);


        // 几个x坐标轴之间才绘制？
//        xl.setSpaceBetweenLabels(5);
//        xl.set
        // 如果false，那么x坐标轴将不可见
        xl.setEnabled(true);

        // 将X坐标轴放置在底部，默认是在顶部。
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        // 图表左边的y坐标轴线
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(0xff37474f);

//        // 最大值
//        leftAxis.setAxisMaxValue(45f);

        // 最小值
        leftAxis.setAxisMinValue(35f);

        // 不一定要从0开始
        leftAxis.setStartAtZero(false);

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        // 不显示图表的右边y坐标轴线
        rightAxis.setEnabled(false);
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



    @Override
    public void clearData() {
        tv_date.setText("0");
        tv_hight.setText("0");
        tv_username.setText("");
        tv_weight.setText("0");
        iv_gender.setVisibility(View.GONE);
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
//                    value_info_textview.setText(String.valueOf(temperature));
                }
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
//                    user_bottom_textview.setText(temperatureState);
                }
//                user_bottom_textview.setTextColor(getResources().getColor(themeColor));

            }
        });
    }

    @Override
    public void setBatteryInfoDisplay(final String batteryInfo, final String batteryNum, @ColorRes final int themeColor) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(batteryInfo)) {
//                    user_top_textview.setText(batteryInfo + batteryNum);
                }
//                user_top_textview.setTextColor(getResources().getColor(themeColor));
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
                }
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

    @OnClick({R.id.top_view_right_text, R.id.top_view_left_text, R.id.iv_avatar, R.id.top_view_title, R.id.tv_update, R.id.iv_location,R.id.top_view_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
            getActivity().finish();
                break;
            case R.id.top_view_right_text:
                mPresenter.linkDevice(getActivity());
                break;

            case R.id.iv_avatar:
                Utils.toNextActivity(getActivity(), ChartActivity.class);
                break;

        }
    }

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
                    mPresenter.sendCmd(getActivity(), deviceAddress);
                    mPresenter.boundBabyandDevice(deviceName);
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
    public void setPresenter(HomeDeviceContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.closeDevice();
    }

    private void DisConnectState() {
        top_view_left_text.setText(getString(R.string.home_disconnectting));
    }

    @Override
    public void onClicked(int index, BoomButton boomButton) {
        if(index==0){
            Utils.toNextActivity(getActivity(), BabyActivity.class);
        }else if(index==1){
            Utils.toNextActivity(getActivity(), SynchronizeActivity.class);
        }else if(index==2){
            Utils.toNextActivity(getActivity(), SettingDeviceActivity.class);
        }else if(index==3){
            Utils.toNextActivity(getActivity(), DeviceActivity.class);;
        }

    }

    @Override
    public void onBackgroundClick() {

    }

    @Override
    public void onBoomWillHide() {

    }

    @Override
    public void onBoomDidHide() {

    }

    @Override
    public void onBoomWillShow() {

    }

    @Override
    public void onBoomDidShow() {

    }
}

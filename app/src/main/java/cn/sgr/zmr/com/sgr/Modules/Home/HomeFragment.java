package cn.sgr.zmr.com.sgr.Modules.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Base.MyApplication;
import cn.sgr.zmr.com.sgr.Modules.Home.Activity.BabyActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Activity.DeviceActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Activity.DeviceListActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.CirclePagerAdapter;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.BluetoothSet;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.DemoView;

public class HomeFragment extends BaseFragment {


    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;


    TextView home_unit_top;


    TextView home_unit_midle;


    @BindView(R.id.home_temp)
    JellyToggleButton home_temp;

//    private CircularBarPager mCircularBarPager;

    @BindView(R.id.circularBarPager)
    CircularBarPager mCircularBarPager;
    TextView value_info_textview,user_top_textview,user_bottom_textview;
    Button button;
    DemoView ViewT;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private BluetoothSet mBluetoothSet = null;		//蓝牙对象
    MyApplication mBluetoothSetSession = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        intView();
        return view;
    }
    private void intView() {
        ViewT=   new DemoView(getActivity());
        mCircularBarPager.setViewPagerAdapter(new CirclePagerAdapter(getActivity(),ViewT));
        top_view_title.setText("体温检测");
        top_view_back.setVisibility(View.GONE);
        top_view_left_text.setVisibility(View.VISIBLE);
        top_view_left_text.setText("连接");
        top_view_right_text.setVisibility(View.VISIBLE);
        top_view_right_text.setText("电子病历");
        user_top_textview=(TextView)ViewT.findViewById(R.id.user_top_textview);
        value_info_textview=(TextView)ViewT.findViewById(R.id.value_info_textview);
        user_bottom_textview=(TextView)ViewT.findViewById(R.id.user_bottom_textview);

        home_unit_top=(TextView) ViewT.findViewById(R.id.user_top_unit);
        home_unit_midle=(TextView) ViewT.findViewById(R.id.user_center_unit);

        mCircularBarPager.getCircularBar().setStartLineEnabled(false);
        user_top_textview.setText("室温：28");
        value_info_textview.setText("37.5");
        user_bottom_textview.setText("正常");
        mCircularBarPager.getCircularBar().animateProgress(0, 100, 1500);



        //蓝牙初始化
        initBluetooth();

        home_temp.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.withe));
        home_temp.setTextSize(40);
//        home_temp.setTextColor( ContextCompat.getColor(getActivity(),R.color.them_bg));
        home_temp.setTextMarginLeft(20);
        home_temp.setTextMarginRight(20);
        home_temp.setChecked(true);
        home_temp.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    home_unit_midle.setText(getResources().getString(R.string.hua_unit));
                    home_unit_top.setText(getResources().getString(R.string.hua_unit));
                }
                if (state.equals(State.RIGHT)) {
                    home_unit_midle.setText(getResources().getString(R.string.shishi_unit));
                    home_unit_top.setText(getResources().getString(R.string.shishi_unit));
                }

            }
        });

    }

    @OnClick({R.id.top_view_right_text, R.id.top_view_left_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_right_text:
                Intent intent1=new Intent();
                intent1.setClass(getActivity(), BabyActivity.class);
                startActivity(intent1);
                break;
            case R.id.top_view_left_text:
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                break;
        }
    }

    private void initBluetooth() {
        mBluetoothSet = new BluetoothSet(getActivity(),top_view_left_text);

        //设置共享对象，后面的Activity对象只需读取对象数据即可
        mBluetoothSetSession = (MyApplication) getActivity().getApplication();
        mBluetoothSetSession.setBluetoothSet(mBluetoothSet);
        mBluetoothSetSession.setHandler(mHandler);
        mBluetoothSet.openBluetooth();

        if (!mBluetoothSet.isSupported()){
            Toast.makeText(getActivity(), "该设备部支持蓝牙", Toast.LENGTH_SHORT).show();

//	        	return;
        }

        if (!mBluetoothSet.isConnected()){
            Toast.makeText(getActivity(), "没有可连接的蓝牙设备!", Toast.LENGTH_SHORT).show();
//				return;
        }else{
            //显示数据的线程
            Toast.makeText(getActivity(), "显示数据!", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * 显示数据
     * @param iFlag
     */

    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Bundle bundle = msg.getData();
            String strArray[] = null;
            switch (msg.what) {
                default:
                    break;
            }
        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    mBluetoothSet.ConnectDevices(address);
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

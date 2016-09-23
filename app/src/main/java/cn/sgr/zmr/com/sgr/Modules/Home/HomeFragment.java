package cn.sgr.zmr.com.sgr.Modules.Home;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;


import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.entity.Baby;
import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Base.MyApplication;
import cn.sgr.zmr.com.sgr.Common.Login.LoginActivity;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Modules.Home.Location.LocationActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.BabyContract;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Device.DeviceListActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.CirclePagerAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Device.DeviceListFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Synchronize.SynchronizeActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.BluetoothSet;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;
import cn.sgr.zmr.com.sgr.View.DemoView;
import cn.sgr.zmr.com.sgr.View.LoadingButton;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeFragment extends BaseFragment implements HomeContract.View{


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





/*    @BindView(R.id.home_temp)
    JellyToggleButton home_temp;*/

//    private CircularBarPager mCircularBarPager;

    @BindView(R.id.circularBarPager)
    CircularBarPager mCircularBarPager;
    TextView value_info_textview,user_top_textview,user_bottom_textview;
    Button button;
    DemoView ViewT;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private BluetoothSet mBluetoothSet = null;		//蓝牙对象
    MyApplication mBluetoothSetSession = null;
    HomeContract.Presenter mPresenter;

    //单例 模式
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    //   构造方法
    public HomeFragment() {
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
        initBlue();

    }

    //当绑定宝宝的时候，显示数据
    @Override
    public void setDate(Baby baby){
        tv_weight.setText(baby.getWeight());
        tv_username.setText(baby.getName());
        tv_hight.setText(baby.getHight());
        tv_date.setText(baby.getAge());
        if(baby.getSex()!=null&&baby.getSex().equals("男")){

            iv_gender.setImageResource(R.drawable.baby_boy);
        }else{
            iv_gender.setImageResource(R.drawable.baby_girl);
        }

    }


    @Override
    public void showProgressDialog(FragmentManager manager) {
        super.showProgressDialog(manager);
    }

    @Override
    public void initBlue() {
        mBluetoothSet = new BluetoothSet(getActivity(),top_view_left_text);
        //设置共享对象，后面的Activity对象只需读取对象数据即可
        mBluetoothSetSession = (MyApplication) getActivity().getApplication();
        mBluetoothSetSession.setBluetoothSet(mBluetoothSet);
        mBluetoothSetSession.setHandler(mHandler);
        if(mBluetoothSet!=null){
            mBluetoothSet.openBluetooth();
        }else{
            Toast.makeText(getActivity(), "该设备不支持蓝牙", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clearData() {
        user_top_textview.setText("室温：0");
        value_info_textview.setText("0");
        user_bottom_textview.setText("正常");
        tv_date.setText("0");
        tv_hight.setText("0");
        tv_username.setText("");
        tv_weight.setText("0");
        iv_gender.setVisibility(View.GONE);
        mCircularBarPager.getCircularBar().animateProgress(0, 0, 1500);
    }

    @OnClick({R.id.top_view_right_text, R.id.top_view_left_text,R.id.iv_avatar,R.id.top_view_title,R.id.tv_update,R.id.iv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_right_text:
                Utils.toNextActivity(getActivity(), BabyActivity.class);
                break;
            case R.id.top_view_left_text:
                cancelProgressDialog();

               Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                break;

            case R.id.iv_avatar:
                Utils.toNextActivity(getActivity(), ChartActivity.class);
                break;

            case R.id.iv_location:
                Utils.toNextActivity(getActivity(),LocationActivity.class);
//                tv_update.setTargetProgress(360);
                break;

            case R.id.tv_update:
//                if(UserInfo.getInstance(getActivity()).hasSignIn()){
                    Utils.toNextActivity(getActivity(), SynchronizeActivity.class);
               /* }else{
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    Utils.toNextActivity(getActivity(),LoginActivity.class);
                }*/

                break;


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
                    String address = data.getExtras().getString(DeviceListFragment.EXTRA_DEVICE_ADDRESS);
                    mBluetoothSet.ConnectDevices(address);
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean isActive() {
        return  isAdded();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}

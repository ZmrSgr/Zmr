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
import com.bumptech.glide.Glide;
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
import cn.sgr.zmr.com.sgr.Common.Login.LoginActivity;
import cn.sgr.zmr.com.sgr.Common.Model.Setting;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Common.ShowAlarm;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.CirclePagerAdapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Device.HomeDeviceActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.SettingDevice.SettingDeviceActivity;
import cn.sgr.zmr.com.sgr.Modules.My.Profile.ProfileActivity;
import cn.sgr.zmr.com.sgr.Modules.My.QrCode.MyQrCodeActivity;
import cn.sgr.zmr.com.sgr.Modules.My.QrCode.ScanCodeActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.BluetoothUtil.BleDeviceHelp;
import cn.sgr.zmr.com.sgr.Utils.BluetoothUtil.ICmdModel;
import cn.sgr.zmr.com.sgr.Utils.util.GlideCircleTransform;
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

    @BindView(R.id.iv_right)
    ImageView iv_right;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.rel_my)
    View   rel_my;

    @BindView(R.id.rel_temp)
    View   rel_temp;

    @BindView(R.id.nickname)
    TextView nickname;

    @BindView(R.id.phone)
    TextView Tvphone;


    HomeTestContract.Presenter mPresenter;
    //单例 模式
    public static HomeTestFragment newInstance() {
        return new HomeTestFragment();
    }
    //   构造方法
    public HomeTestFragment() {
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
        top_view_title.setText(getString(R.string.app_name));
        top_view_back.setVisibility(View.GONE);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.drawable.ic_qrcode);

    }
    @Override
    public void showProgressDialog(FragmentManager manager) {
        super.showProgressDialog(manager);
    }

    @Override
    public void showUserInfo(Context context, String nickName, String phone, String avatar) {
        if (!UserInfo.getInstance(getActivity()).hasSignIn()) {//没有登录
            nickname.setText(getString(R.string.set_unlogin));
            Tvphone.setVisibility(View.GONE);
        }else{

            if(phone!=null&&phone.length()<3){
                Tvphone.setVisibility(View.GONE);
            }else{
                Tvphone.setVisibility(View.VISIBLE);
                Tvphone.setText(phone);
            }
            Glide.with(getActivity()).load(avatar).error(R.drawable.no_avatar).dontAnimate().thumbnail(0.1f).transform(new GlideCircleTransform(getActivity())).into(iv_avatar);
//            Glide.with(context).load(avatar).into(iv_avatar);
            nickname.setText(nickName);
        }
    }


    @OnClick({R.id.top_view_right_text, R.id.iv_right,  R.id.rel_my,R.id.rel_temp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_my:
                if (!UserInfo.getInstance(getActivity()).hasSignIn()) {//没有登录
                    Utils.toNextActivity(getActivity(), LoginActivity.class);
                }else{
                    Utils.toNextActivity(getActivity(), ProfileActivity.class);
                }
                break;
            case R.id.iv_right:
                if (!UserInfo.getInstance(getActivity()).hasSignIn()) {//没有登录
                    Utils.toNextActivity(getActivity(), LoginActivity.class);
                }else{
                    Utils.toNextActivity(getActivity(), ScanCodeActivity.class);
                }


                break;
            case R.id.rel_temp:
                Utils.toNextActivity(getActivity(), HomeDeviceActivity.class);
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

}

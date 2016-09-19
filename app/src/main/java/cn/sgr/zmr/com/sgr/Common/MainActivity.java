package cn.sgr.zmr.com.sgr.Common;

import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Base.MyApplication;
import cn.sgr.zmr.com.sgr.Common.Login.LoginActivity;
import cn.sgr.zmr.com.sgr.Common.Model.UserInfo;
import cn.sgr.zmr.com.sgr.Modules.Health.HealhFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.HomeFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.HomePresenter;
import cn.sgr.zmr.com.sgr.Modules.Messages.MessageFragment;
import cn.sgr.zmr.com.sgr.Modules.Setting.More.MorePresenter;
import cn.sgr.zmr.com.sgr.Modules.Setting.SettingFragment;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class MainActivity extends Activity {

    @BindView(R.id.home_layout)
    View home_layout;

    @BindView(R.id.message_layout)
    View message_layout;

    @BindView(R.id.health_layout)
    View health_layout;

    @BindView(R.id.set_layout)
    View set_layout;

    @BindView(R.id.home_image)
    ImageView home_image;

    @BindView(R.id.message_image)
    ImageView message_image;

    @BindView(R.id.healthr_image)
    ImageView healthr_image;

    @BindView(R.id.set_image)
    ImageView set_image;


    @BindView(R.id.home_text)
    TextView home_text;

    @BindView(R.id.health_text)
    TextView health_text;

    @BindView(R.id.set_text)
    TextView set_text;

    @BindView(R.id.message_text)
    TextView  message_text;

    private HealhFragment healthFragment;
    private HomeFragment homeFragment;
    private MessageFragment messagesFragment;
    private SettingFragment settingFragment;
    private FragmentTransaction transaction;
    long firstTime;
    private boolean iscycle;
    private int chooseIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setTabSelection(chooseIndex);
//        MyApplication.getInstance().addActivity(this);
    }
    @OnClick({R.id.home_layout, R.id.health_layout, R.id.message_layout, R.id.set_layout})
    public void onTabClick(View view) {
        switch (view.getId()) {
            case R.id.home_layout:
                chooseIndex = 0;
                setTabSelection(0);
                break;
            case R.id.health_layout:
                chooseIndex = 1;
                setTabSelection(1);
                break;
            case R.id.message_layout:
                chooseIndex =2;
                setTabSelection(2);
                break;
            case R.id.set_layout:
                chooseIndex = 3;
                setTabSelection(3);
                break;
        }
    }
    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *
     */
    private void setTabSelection(int index) {

        clearSelection();

        transaction = getFragmentManager().beginTransaction();

        hideFragments(transaction);
        switch (index) {
            case 0:
                home_image.setImageResource(R.drawable.tab_ask_doctor_pressed);
                home_text.setTextColor(getResources().getColorStateList(R.color.them_bg));
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    if (homeFragment == null) {
                        homeFragment = homeFragment.newInstance();
                        Utils.addFragmentToActivity(getFragmentManager(),homeFragment, R.id.contentFrame);
                    }
                    // Create the presenter
                    new HomePresenter(this,homeFragment);
                    transaction.add(R.id.content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                transaction.commit();
                break;
            case 1:

                healthr_image.setImageResource(R.drawable.tab_discover_pressed);
                health_text.setTextColor(getResources().getColorStateList(
                        R.color.them_bg));
                if (healthFragment == null) {

                    healthFragment = new HealhFragment();

                    transaction.add(R.id.content, healthFragment);
                } else {

                    transaction.show(healthFragment);
                }
                transaction.commit();
                break;
            case 2:
                if(!UserInfo.getInstance(this).hasSignIn()){
                    Utils.toNextActivity(this, LoginActivity.class);
                }else{
                    message_image.setImageResource(R.drawable.tab_message_pressed);
                    message_text.setTextColor(getResources().getColorStateList(
                            R.color.them_bg));

                    if (messagesFragment == null) {

                        messagesFragment = new MessageFragment();
                        transaction.add(R.id.content, messagesFragment);
                    } else {

                        transaction.show(messagesFragment);
                    }
                    transaction.commit();
                }


                break;
            case 3:

                set_image.setImageResource(R.drawable.tab_i_pressed);
                set_text.setTextColor(getResources().getColorStateList(
                        R.color.them_bg));
                if (settingFragment == null) {

                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                } else {

                    transaction.show(settingFragment);
                }
                transaction.commit();
                break;



        }

    }

    private void setImageText(int index) {

        clearSelection();
        switch (index) {
            case 0:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                home_image.setImageResource(R.drawable.tab_ask_doctor_pressed);
                home_text.setTextColor(getResources().getColorStateList(
                        R.color.them_bg));
                break;
            case 1:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                healthr_image.setImageResource(R.drawable.tab_discover_pressed);
                health_text.setTextColor(getResources().getColorStateList(
                        R.color.them_bg));
                break;
            case 2:




                // 当点击了消息tab时，改变控件的图片和文字颜色
                message_image.setImageResource(R.drawable.tab_message_pressed);
                message_text.setTextColor(getResources().getColorStateList(
                        R.color.them_bg));



                break;

            case 3:
                set_image.setImageResource(R.drawable.tab_i_pressed);
                set_text.setTextColor(getResources().getColorStateList(
                        R.color.them_bg));

                break;

        }

    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        message_image.setImageResource(R.drawable.tab_message_normal);
        message_text.setTextColor(getResources().getColor(
                R.color.them_text));
        home_image.setImageResource(R.drawable.tab_ask_doctor_normal);
        home_text.setTextColor(getResources().getColor(
                R.color.them_text));
        healthr_image.setImageResource(R.drawable.tab_discover_normal);
        health_text.setTextColor(getResources().getColor(
                R.color.them_text));
        set_image.setImageResource(R.drawable.tab_i_normal);
        set_text.setTextColor(getResources().getColor(
                R.color.them_text));
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (healthFragment != null) {
            transaction.hide(healthFragment);
        }
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }

        if (messagesFragment != null) {

            transaction.hide(messagesFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        iscycle = savedInstanceState.getBoolean("iscycle");
        chooseIndex = savedInstanceState.getInt("chooseIndex");
        setTabSelection(chooseIndex);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putBoolean("iscycle", true);
        outState.putInt("chooseIndex", chooseIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iscycle) {
            setImageText(chooseIndex);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1000) {
                Toast.makeText(MainActivity.this, "在按一次退出客戶端", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
            } else {
                MyApplication.getInstance().exit();
            }
        }
        return true;
    }


}

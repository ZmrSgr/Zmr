package cn.sgr.zmr.com.sgr.Modules.Home.Module.SettingDevice;

import android.os.Bundle;

import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class SettingDeviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        initPV();

    }

    //初始化presenter和view
    private void initPV() {
        SettingDeviceFragment settingDeviceFragment =
                (SettingDeviceFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (settingDeviceFragment == null) {
            settingDeviceFragment = settingDeviceFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(), settingDeviceFragment, R.id.contentFrame);
        }
        // Create the presenter
        new SettingDevicePresenter(this, settingDeviceFragment);

    }
}

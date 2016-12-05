package cn.sgr.zmr.com.sgr.Modules.Home.Device;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Fragment;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Presenter;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class HomeDeviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        initPV();
    }
    //初始化presenter和view
    private void initPV() {
        HomeDeviceFragment homeDevideFragment =
                (HomeDeviceFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (homeDevideFragment == null) {
            homeDevideFragment = homeDevideFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),homeDevideFragment, R.id.contentFrame);
        }
        // Create the presenter
        new HomeDevicePresenter(this,homeDevideFragment);

    }
}

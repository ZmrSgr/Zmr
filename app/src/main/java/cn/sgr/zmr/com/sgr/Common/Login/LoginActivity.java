package cn.sgr.zmr.com.sgr.Common.Login;

import android.os.Bundle;

import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        initVP();

    }
    //初始化   presenter 和 fregment（view）
    private void initVP() {
        LoginFragment loginFragment =
       (LoginFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            loginFragment = loginFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),loginFragment, R.id.contentFrame);
        }
        // Create the presenter
        new LoginPresenter(this,loginFragment);
    }
}

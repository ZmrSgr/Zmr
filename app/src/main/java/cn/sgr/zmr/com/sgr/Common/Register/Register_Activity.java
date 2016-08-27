package cn.sgr.zmr.com.sgr.Common.Register;

import android.os.Bundle;

import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class Register_Activity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        initPV();
    }

    //初始化presenter和view
    private void initPV() {
        Register_Fragment registerFragment =
                (Register_Fragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (registerFragment == null) {
            registerFragment = registerFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),registerFragment, R.id.contentFrame);
        }
        // Create the presenter
        new Register_Presenter(this,registerFragment);

    }

}

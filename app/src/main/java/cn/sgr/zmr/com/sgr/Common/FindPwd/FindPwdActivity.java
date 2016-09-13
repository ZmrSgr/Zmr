package cn.sgr.zmr.com.sgr.Common.FindPwd;

import android.os.Bundle;

import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class FindPwdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        initPV();

    }

    //初始化presenter和view
    private void initPV() {
        FindPwdFragment moreFragment =
                (FindPwdFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (moreFragment == null) {
            moreFragment = moreFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),moreFragment, R.id.contentFrame);
        }
        // Create the presenter
        new FindPwdPresenter(this,moreFragment);

    }
}

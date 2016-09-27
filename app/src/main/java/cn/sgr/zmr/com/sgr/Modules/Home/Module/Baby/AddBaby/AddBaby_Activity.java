package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby;

import android.os.Bundle;

import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class AddBaby_Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        ButterKnife.bind(this);
        initPV();

    }
    //初始化presenter和view
    private void initPV() {
        AddBabyFragment babyFragment = (AddBabyFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (babyFragment == null) {
            babyFragment = babyFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(), babyFragment, R.id.contentFrame);
        }
        // Create the presenter
        new AddBabyPresenter(this, babyFragment);
    }

}

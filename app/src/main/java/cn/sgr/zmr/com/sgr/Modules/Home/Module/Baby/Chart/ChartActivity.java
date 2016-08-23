package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;

import butterknife.ButterKnife;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class ChartActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_activity);
        ButterKnife.bind(this);
        initPV();

    }
    //初始化presenter和view
    private void initPV() {
        ChartFragment chartFragment = (ChartFragment)getFragmentManager().findFragmentById(R.id.contentFrame);
        if (chartFragment == null) {
            chartFragment = chartFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),chartFragment, R.id.contentFrame);
        }
        // Create the presenter
        new ChartPresenter(this,chartFragment);
    }


}
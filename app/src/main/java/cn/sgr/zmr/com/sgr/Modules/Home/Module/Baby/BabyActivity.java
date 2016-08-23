package cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.AddBaby.AddBaby_Activity;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Baby.Chart.ChartActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Baby_Adapter;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.Baby;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Device.DeviceListFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Module.Device.DeviceListPresenter;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.View.MyDecoration;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class BabyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        ButterKnife.bind(this);
        initPV();

    }
    //初始化presenter和view
    private void initPV() {
        BabyFragment babyFragment = (BabyFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (babyFragment == null) {
            babyFragment = babyFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),babyFragment, R.id.contentFrame);
        }
        // Create the presenter
        new BabyPresenter(this,babyFragment);
    }

}

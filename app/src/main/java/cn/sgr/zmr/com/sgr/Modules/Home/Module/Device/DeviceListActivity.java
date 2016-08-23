package cn.sgr.zmr.com.sgr.Modules.Home.Module.Device;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Fragment;
import cn.sgr.zmr.com.sgr.Common.Register.Register_Presenter;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Device_Item_Adapter;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.Utils;

public class DeviceListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity);
        ButterKnife.bind(this);
        initPV();


    }
    //初始化presenter和view
    private void initPV() {
        DeviceListFragment deviceListFragment =
                (DeviceListFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (deviceListFragment == null) {
            deviceListFragment = deviceListFragment.newInstance();
            Utils.addFragmentToActivity(getFragmentManager(),deviceListFragment, R.id.contentFrame);
        }
        // Create the presenter
        new DeviceListPresenter(this,deviceListFragment);
    }



}

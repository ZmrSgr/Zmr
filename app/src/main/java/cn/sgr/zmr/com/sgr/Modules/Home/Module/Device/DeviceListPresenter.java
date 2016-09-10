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
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.sgr.zmr.com.sgr.Common.Register.Register_Contract;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.View.MyDialog;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class DeviceListPresenter implements DeviceListContract.Presenter {

    @NonNull
    private final DeviceListContract.View registerView;

    Context context;
    private BluetoothAdapter mBtAdapter;//数据源
    private static Boolean hasDevices;
    private List<String> lstDevices = new ArrayList<String>();

    public DeviceListPresenter(Context contexts, @NonNull DeviceListContract.View registerView) {
        this.registerView = registerView;
        this.context = contexts;
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        this.registerView.setPresenter(this);
    }

    @Override
    public void doDiscovery() {
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        startDiscovery();
        //显示进度条
        registerView.showProgressDialog();


    }

    @Override
    public void doContract(int position) {
        if (hasDevices) {
            String info = lstDevices.get(position);
            String address = info.substring(info.length() - 17);
            // Create the result Intent and include the MAC address
            registerView.nextActivity(address);
        }

    }

    @Override
    public void startDiscovery() {
        lstDevices.clear();
        registerView.showData(lstDevices);

        mBtAdapter.startDiscovery();
    }

    @Override
    public void cancelDiscovery() {
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

    }

    @Override
    public void unregisterReceiver() {
        context.unregisterReceiver(mReceiver);
    }

    @Override
    public void start() {
        //注册广播
        IntentFilter found_filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, found_filter);
        IntentFilter discovery_filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(mReceiver, discovery_filter);
        //开始搜索
        if (mBtAdapter != null&&mBtAdapter.isEnabled()) {//表示已经打开蓝牙
            doDiscovery();
        }else{//没打开的话 先打开蓝牙在判断
            final MyDialog dialog = new MyDialog(context,context.getResources().getString(R.string.is_open_blue),null,null);
            dialog.show();
            dialog.positive.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    mBtAdapter.enable();
                    if(mBtAdapter.isEnabled()){
                        doDiscovery();
                    }else{
                        Toast.makeText(context,"您的系统已对该应用禁用了蓝牙权限",Toast.LENGTH_LONG).show();
                    }

                }
            });
            dialog.negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });



        }

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action))//查找中
            {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String tempString = "";
                //添加设备
                tempString += device.getName() + "\n" + device.getAddress();
                //防止重复添加
                if (lstDevices.indexOf(tempString) == -1) {
                    lstDevices.add(tempString);
                    registerView.showData(lstDevices);
                }
                hasDevices = true;
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))//查找完
            {
                registerView.setTitle(context.getResources().getString(R.string.select_device));
                if (lstDevices.size() == 0) {
                    String noDevices = context.getResources().getText(
                            R.string.none_found).toString();
                    lstDevices.add(noDevices);
                    registerView.showData(lstDevices);
                    hasDevices = false;
                }
                registerView.cancelProgressDialog();
            }
        }
    };

}

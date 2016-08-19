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
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Device_Item_Adapter;
import cn.sgr.zmr.com.sgr.R;

public class DeviceListActivity extends Activity implements Device_Item_Adapter.OnRecyclerViewListener{
    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.recycler_device)
    RecyclerView recycler_device;

    List<String> lstDevices = new ArrayList<String>();

    @BindView(R.id.recycler_view_linear_srl)
    SwipeRefreshLayout refreshLayout;


    // Debugging
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
    Device_Item_Adapter DeviceAdapter;

    private static Boolean hasDevices;
    private ProgressDialog mpDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);
        initView();

        IntentFilter found_filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, found_filter);

        // Register for broadcasts when discovery has finished
        IntentFilter discovery_filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, discovery_filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter != null)
            doDiscovery();
    }

    private void initView() {
        top_view_title.setText("设备列表");
        top_view_back.setVisibility(View.VISIBLE);
        top_view_right_text.setVisibility(View.VISIBLE);
        top_view_right_text.setText("搜索");


        recycler_device.setHasFixedSize(true);
        recycler_device.setLayoutManager(new LinearLayoutManager(this));

        DeviceAdapter=new Device_Item_Adapter(this,lstDevices);
        recycler_device.setAdapter(DeviceAdapter);
        DeviceAdapter.setOnRecyclerViewListener(this);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.them_bg));
//        refreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lstDevices.clear();
                mBtAdapter.startDiscovery();
            }
        });
    }


    @OnClick({R.id.top_view_back,R.id.top_view_right_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                finish();
                break;
            case R.id.top_view_right_text:
                lstDevices.clear();
                doDiscovery();
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null)
        {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery()
    {
        if (D) Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        //setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering())
        {
            mBtAdapter.cancelDiscovery();
        }
        hasDevices = false;
        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
        mpDialog = new ProgressDialog(DeviceListActivity.this);
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
        mpDialog.setTitle("提醒");//设置标题
        mpDialog.setMessage("扫描蓝牙设备...");
        mpDialog.setIndeterminate(false);//设置进度条是否为不明确
        mpDialog.setCancelable(true);//设置进度条是否可以按退回键取消
        mpDialog.setButton("停止", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (mBtAdapter.isDiscovering()){
                    mBtAdapter.cancelDiscovery();
                }
            }
        });
        mpDialog.show();
    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = (BluetoothDevice)intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String tempString = "";
         /*       if(device.getBondState() == BluetoothDevice.BOND_NONE){
                    tempString = "Status: UnPaired\n";
                }
                else {
                    tempString = "Status: Paired\n";
                }*/

                //添加设备
                tempString += device.getName() + "\n"
                        + device.getAddress();
                //防止重复添加
                if (lstDevices.indexOf(tempString) == -1){
                    lstDevices.add(tempString);
                    DeviceAdapter.notifyDataSetChanged();
                }
                //mNewDevicesArrayAdapter.add(device.getName() + "\n"
                //		+ device.getAddress());
                hasDevices = true;

            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                // When discovery is finished, change the Activity title
                //setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                if (DeviceAdapter.getItemCount()==0)
                {
                    String noDevices = getResources().getText(
                            R.string.none_found).toString();
                    lstDevices.add(noDevices);
                    DeviceAdapter.notifyDataSetChanged();
                    hasDevices = false;
                }
                mpDialog.cancel();
                if(refreshLayout!=null){
                   if (refreshLayout.isRefreshing()){
                       refreshLayout.setRefreshing(false);
                    }
                }
            }
        }
    };

    @Override
    public void onItemClick(int position) {
        // Cancel discovery because it's costly and we're about to connect
        mBtAdapter.cancelDiscovery();

        // Get the device MAC address, which is the last 17 chars in the
        // View
        if (hasDevices){

            String info = lstDevices.get(position);
            String address = info.substring(info.length() - 17);
            // Create the result Intent and include the MAC address
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            // Set result and finish this Activity
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }


    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}

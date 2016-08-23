package cn.sgr.zmr.com.sgr.Modules.Home.Module.Device;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseFragment;
import cn.sgr.zmr.com.sgr.Modules.Home.Adatpter.Device_Item_Adapter;
import cn.sgr.zmr.com.sgr.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 沈国荣 on 2016/8/23 0023.
 */
public class DeviceListFragment extends BaseFragment implements DeviceListContract.View, Device_Item_Adapter.OnRecyclerViewListener {

    @BindView(R.id.top_view_right_text)
    TextView top_view_right_text;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.recycler_device)
    RecyclerView recycler_device;

    @BindView(R.id.recycler_view_linear_srl)
    SwipeRefreshLayout refreshLayout;
    // Debugging
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;
    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    Device_Item_Adapter DeviceAdapter;
    private ProgressDialog mpDialog = null;
    DeviceListContract.Presenter mPresenter;
    //单例 模式
    public static DeviceListFragment newInstance() {
        return new DeviceListFragment();
    }
    //   构造方法
    public DeviceListFragment() {
        // Required empty public constructor
    }
    @Override
    public void showProgressDialog() {
        mpDialog = new ProgressDialog(getActivity());
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
        mpDialog.setTitle("提醒");//设置标题
        mpDialog.setMessage("扫描蓝牙设备...");
        mpDialog.setIndeterminate(false);//设置进度条是否为不明确
        mpDialog.setCancelable(true);//设置进度条是否可以按退回键取消
        mpDialog.setButton("停止", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                mPresenter.cancelDiscovery();
            }
        });
        mpDialog.show();
    }
    @Override
    public void cancelProgressDialog() {
        mpDialog.cancel();
        if (refreshLayout != null) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
        }
    }
    @Override
    public void showData(List<String> lstDevices) {
        DeviceAdapter.applyData(lstDevices);
    }
    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }
    @Override
    public void nextActivity(String address) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_list_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    //初始化控件
    private void initView() {
        top_view_title.setText("设备列表");
        top_view_back.setVisibility(View.VISIBLE);
        top_view_right_text.setVisibility(View.VISIBLE);
        top_view_right_text.setText("搜索");
        recycler_device.setHasFixedSize(true);
        recycler_device.setLayoutManager(new LinearLayoutManager(getActivity()));
        DeviceAdapter = new Device_Item_Adapter(getActivity(), new ArrayList<String>());
        recycler_device.setAdapter(DeviceAdapter);
        DeviceAdapter.setOnRecyclerViewListener(this);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.them_bg));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.startDiscovery();
            }
        });
    }
    @OnClick({R.id.top_view_back, R.id.top_view_right_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_back:
                getActivity().finish();
                break;
            case R.id.top_view_right_text:
                setTitle(getActivity().getResources().getString(R.string.scanning));
                mPresenter.doDiscovery();
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.cancelDiscovery();
        mPresenter.unregisterReceiver();
    }
    @Override
    public void onItemClick(int position) {
        mPresenter.cancelDiscovery();
        mPresenter.doContract(position);

    }
    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull DeviceListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}

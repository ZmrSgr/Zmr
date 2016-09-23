package cn.sgr.zmr.com.sgr.Modules.Home.Location;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Base.MyApplication;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.LocationService;

public class LocationActivity extends BaseActivity {
    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;


    @BindView(R.id.btn_location)
    TextView btn_location;

    private BaiduMap mBaiduMap;
    // 定位相关
    LocationClient mLocClient;
    LocationClientOption option;
    private LocationService locService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        //初始化头部控件
        top_view_back.setVisibility(View.VISIBLE);
        top_view_left_text.setVisibility(View.VISIBLE);
        mBaiduMap = mapView.getMap();
       mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);


        StartLocation();
//        getLocation();
    }
   //获取位置并且显示
    private void getLocation() {
        top_view_left_text.setText("宝宝位置");

        //定义Maker坐标点
        LatLng point = new LatLng(22.255253, 113.567137);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_launcher);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.clear();
        mBaiduMap.addOverlay(option);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
    }
   //开始定位
    private void StartLocation() {
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        mLocClient = new LocationClient(this);

        mLocClient.registerLocationListener(listener);

        locService = ((MyApplication) getApplication()).locationService;
        option = locService.getDefaultLocationClientOption();

        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    /***
     * 定位结果回调，在此方法中处理定位结果
     */
    BDLocationListener listener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub

            if (location != null && (location.getLocType() == 161 || location.getLocType() == 66)) {
                Message locMsg = locHander.obtainMessage();
                Bundle locData = new Bundle();
/*                Bundle locData;
//                locData = Algorithm(location);
                if (locData != null) {
//                    locData.putParcelable("loc", location);
//                    locMsg.setData(locData);
                    locHander.sendMessage(locMsg);
                }*/
                locData.putParcelable("loc", location);
                locMsg.setData(locData);
                locHander.sendMessage(locMsg);
            }
        }
    };

    /***
     * 接收定位结果消息，并显示在地图上
     */
    private Handler locHander = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            try {
                BDLocation location = msg.getData().getParcelable("loc");
                top_view_left_text.setText(location.getAddrStr()+","+location.getLocationDescribe());
                if (location != null) {
                    LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                    // 构建Marker图标
                    BitmapDescriptor bitmap = null;
                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_focuse_mark); // 非推算结果
                    // 构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
                    mBaiduMap.clear();
                    // 在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    };

    @OnClick({R.id.top_view_back,R.id.top_view_left_text,R.id.btn_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_left_text:
                Toast.makeText(this,top_view_left_text.getText().toString(),Toast.LENGTH_LONG).show();
                break;

            case R.id.top_view_back:
                finish();
                break;
            case R.id.btn_location:
                if(btn_location.getText().equals(getString(R.string.baby_location))){
                    btn_location.setText(getString(R.string.my_location));
                    getLocation();
                }else{
                    btn_location.setText(getString(R.string.baby_location));
                    StartLocation();
                }

                break;



        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//		WriteLog.getInstance().close();
        locService.unregisterListener(listener);
        locService.stop();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();

    }
}

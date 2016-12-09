package cn.sgr.zmr.com.sgr.Modules.Home.Location;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Base.MyApplication;
import cn.sgr.zmr.com.sgr.Modules.Home.Location.NearByDrug.DrugActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Location.NearByStore.StoreActivity;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.LocationService;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;

public class LocationActivity extends BaseActivity {
    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.top_view_left_text)
    TextView top_view_left_text;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.relocation)
    ImageView relocation;

    MyLocationData locData_curry;
    private Marker mMarkerA;//宝宝位置标签
    private InfoWindow mInfoWindow;

    @BindView(R.id.nearby_baby)
    TextView nearby_baby;

    @BindView(R.id.nearby_store)
    TextView nearby_store;

    @BindView(R.id.nearby_medice)
    TextView nearby_medice;

    private BaiduMap mBaiduMap;
    // 定位相关
    LocationClient mLocClient;
    LocationClientOption option;
    private LocationService locService;

    private String Mylat,Mylng;

    BDLocation location;

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
        mapView.showZoomControls(false);
        mBaiduMap = mapView.getMap();
       mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(12));
        //        设置是否允许楼块效果
        mBaiduMap.setBuildingsEnabled(true);


        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        StartLocation();
        getLocation();
    }
        //获取位置并且显示
    private void getLocation() {
        //定义Maker坐标点
        LatLng point = new LatLng(22.255253, 113.567137);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.baby_icon);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
       //在地图上添加Marker，并显示
        mMarkerA = (Marker) (mBaiduMap.addOverlay(option));
        //maker监听事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                Button button = new Button(getApplicationContext());
                button.setBackgroundResource(R.drawable.popup);
                InfoWindow.OnInfoWindowClickListener listener = null;
                if (marker == mMarkerA ) {
                    button.setTextColor(Color.BLACK);
                    button.setText("宝宝位置");
                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            LatLng ll = marker.getPosition();
                            LatLng llNew = new LatLng(ll.latitude + 0.005,
                                    ll.longitude + 0.005);
                            marker.setPosition(llNew);
                            mBaiduMap.hideInfoWindow();
                        }
                    };
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });
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
                Mylat=String.valueOf(location.getLatitude());
                Mylng=String.valueOf(location.getLongitude());
                locData_curry = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData_curry);
                top_view_left_text.setText(location.getAddrStr()+","+location.getLocationDescribe());
            }
        }
    };
    @OnClick({R.id.top_view_back,R.id.top_view_left_text,R.id.relocation,R.id.nearby_baby,R.id.nearby_medice,R.id.nearby_store})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_view_left_text:
                Toast.makeText(this,top_view_left_text.getText().toString(),Toast.LENGTH_LONG).show();
                break;
            case R.id.top_view_back:
                finish();
                break;
            case R.id.nearby_baby:
                break;

            case R.id.nearby_medice://跳转到附近医疗
                Intent intent = new Intent();
                intent.setClass(this, DrugActivity.class);
                if(Mylat!=null){
                    intent.putExtra(UtilKey.DRUG_LAT, Mylat);
                    intent.putExtra(UtilKey.DRUG_LNG, Mylng);
                }
                startActivity(intent);
                break;

            case R.id.nearby_store:
                Intent i = new Intent(this, StoreActivity.class);
                if(Mylat != null){
                    i.putExtra(UtilKey.STORE_LAT, Mylat);
                    i.putExtra(UtilKey.STORE_LNG, Mylng);
                    i.putExtra(UtilKey.DRUG_LAT, Mylat);
                    i.putExtra(UtilKey.DRUG_LNG, Mylng);
                }
                startActivity(i);
                break;

            case R.id.relocation:
                mBaiduMap.setMyLocationData(locData_curry);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
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

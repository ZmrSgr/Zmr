package cn.sgr.zmr.com.sgr.Modules.Home.Location;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sgr.zmr.com.sgr.Base.BaseActivity;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.Drug;
import cn.sgr.zmr.com.sgr.R;
import cn.sgr.zmr.com.sgr.Utils.util.UtilKey;

public class CommonMapActivity extends BaseActivity {
    @BindView(R.id.mapViews)
    MapView mapView;

    @BindView(R.id.top_view_title)
    TextView top_view_title;

    @BindView(R.id.top_view_back)
    ImageView top_view_back;

    @BindView(R.id.drug_phone)
    ImageView drug_phone;

    MyLocationData locData_curry;
    private Marker mMarkerA;//地图标签
    private InfoWindow mInfoWindow;

    @BindView(R.id.drug_adress)
    TextView drug_adress;

    private BaiduMap mBaiduMap;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_common_map);
        ButterKnife.bind(this);
        initViews();
    }
    private void initViews() {
        //初始化头部控件
        top_view_back.setVisibility(View.VISIBLE);
        mapView.showZoomControls(false);
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
        // 设置是否允许楼块效果
        mBaiduMap.setBuildingsEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        getData();
    }
    //获取从上一个界面跳转过来的数据并且显示
    private void getData() {
        Drug datas = (Drug)getIntent().getSerializableExtra(UtilKey.DRUG_DATA_KEY);
        if(getIntent().hasExtra(UtilKey.DRUG_DATA_KEY)){
            Drug data = (Drug)getIntent().getSerializableExtra(UtilKey.DRUG_DATA_KEY);
            if(data!=null){
                drug_adress.setText(data.getAddress());
                top_view_title.setText(data.getDurgstoreName());
                phone=data.getTelephone();
                getLocation(Float.parseFloat(data.getLatitude()), Float.parseFloat(data.getLongitude()));
            }
        }

    }
    @OnClick({R.id.top_view_back,R.id.drug_phone,R.id.top_view_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drug_phone:

                if(phone!=null&&phone.length()<7){
                    Toast.makeText(CommonMapActivity.this,"电话有误",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    //url:统一资源定位符
                    intent.setData(Uri.parse("tel:" + phone));
                    //开启系统拨号器
                    startActivity(intent);
                }
                break;
            case R.id.top_view_back:
                finish();
                break;
            case R.id.top_view_title:
               Toast.makeText(CommonMapActivity.this,top_view_title.getText().toString(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //获取坐标位置并且显示
    private void getLocation(float lat,float lon) {
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lon);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_focuse_mark);
       //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
       //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        //把该点移动到地图中心
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
        //点击marker 监听事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                Button button = new Button(getApplicationContext());
                button.setBackgroundResource(R.drawable.popup);
                InfoWindow.OnInfoWindowClickListener listener = null;
                if (marker == mMarkerA ) {
                    button.setTextColor(Color.BLACK);
                    button.setText(top_view_title.getText().toString());
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
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

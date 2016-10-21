package cn.sgr.zmr.com.sgr.Modules.Home.Location.NearByStore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.Result;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.NearByModel;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.Drug;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.Store;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.StoreResult;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import okhttp3.Call;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class StorePresenter implements StoreContract.Presenter {


    @NonNull
    private final StoreContract.View registerView;
    Context context;
    NearByModel nearByModel;
    private int page=1;
    private double lat = 0;// 纬度
    private double lng = 0;// 经度

    public StorePresenter(Context contexts, @NonNull StoreContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        registerView.setPresenter(this);//互相拥有对象
        nearByModel = new NearByModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void getStoreList(String lat, String lng) {
        nearByModel.getStoreList(context,lat,lng,1, new HttpRequestCallback<Result<StoreResult>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResponse(Result<StoreResult> storeResult) {
                registerView.showNearByStore(storeResult.data.getHospital());
            }

            @Override
            public void onFailure(Call call, HttpException e) {

            }
        });
    }

    @Override
    public void getStoreMoreList(String lat, String lng) {
        nearByModel.getStoreList(context,lat,lng,page++, new HttpRequestCallback<Result<Drug>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResponse(Result<Drug> drugResult) {

            }

            @Override
            public void onFailure(Call call, HttpException e) {

            }
        });
    }
}

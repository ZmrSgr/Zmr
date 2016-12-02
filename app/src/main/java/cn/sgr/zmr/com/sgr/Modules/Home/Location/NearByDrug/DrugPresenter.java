package cn.sgr.zmr.com.sgr.Modules.Home.Location.NearByDrug;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import cn.sgr.zmr.com.sgr.Modules.Health.Model.HealthModel;
import cn.sgr.zmr.com.sgr.Modules.Health.Model.bean.Result;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.NearByModel;
import cn.sgr.zmr.com.sgr.Modules.Home.Model.bean.Drug;
import cn.sgr.zmr.com.sgr.Utils.http.HttpException;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import okhttp3.Call;

/**
 * Created by 沈国荣 on 2016/9/7 0007.
 */
public class DrugPresenter implements DrugContract.Presenter {

    private int page=1;

    @NonNull
    private final DrugContract.View registerView;
    NearByModel nearByModel;
    Context context;
    public DrugPresenter(Context contexts, @NonNull DrugContract.View registerView) {
        this.context=contexts;
        this.registerView = registerView;
        nearByModel=new NearByModel();
        registerView.setPresenter(this);//互相拥有对象
    }

    @Override
    public void start() {
//        getDrugList
    }

    @Override
    public void getDrugList(String lat, String lng) {
        nearByModel.getDrugList(context,lat,lng,1, new HttpRequestCallback<Result<List<Drug>>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResponse(Result<List<Drug>> drugResult) {
//                System.out.println("Result<Drug>"+drugResult);
                registerView.showDrugList(drugResult.data);
            }

            @Override
            public void onFailure(Call call, HttpException e) {

            }
        });

    }

    @Override
    public void getMoreDrugList( String lat, String lng) {
        nearByModel.getDrugList(context,lat,lng,page++, new HttpRequestCallback<Result<Drug>>() {
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

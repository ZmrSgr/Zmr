package cn.sgr.zmr.com.sgr.Modules.Home.Model;

import android.content.Context;

import cn.sgr.zmr.com.sgr.Base.BaseModel;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.http.RequestParams;
import cn.sgr.zmr.com.sgr.Utils.util.Constant;

/**
 * Created by 沈国荣 on 2016/10/10 0010.
 */
public class NearByModel extends BaseModel implements NearByInterfaces{
    @Override
    public void getStoreList(Context context,String lat, String lng, int pageIndex, HttpRequestCallback callback) {
        RequestParams params = new RequestParams();
        params.put("os" ,"1");
        params.put("sign" ,"9781ce3198fd5c2ecd367d7583659697");
        params.put("lat" ,lat);
        params.put("lng" ,lng);
        params.put("pageIndex" ,String.valueOf(pageIndex));
        sendPostRequest(context, Constant.URL_YUN_STORE, params, callback);

    }

    @Override
    public void getDrugList(Context context,  String lat, String lng, int pageIndex, HttpRequestCallback callback) {
        RequestParams params = new RequestParams();
        params.put("os" ,"1");
        params.put("sign" ,"9781ce3198fd5c2ecd367d7583659697");
        params.put("lat" ,lat);
        params.put("lng" ,lng);
        params.put("pageIndex" ,String.valueOf(pageIndex));
        sendPostRequest(context, Constant.URL_YUN_DRUG, params, callback);

    }
}

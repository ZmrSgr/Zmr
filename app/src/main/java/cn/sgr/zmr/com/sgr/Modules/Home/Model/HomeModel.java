package cn.sgr.zmr.com.sgr.Modules.Home.Model;

import android.content.Context;


import cn.sgr.zmr.com.sgr.Base.BaseModel;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.http.RequestParams;
import cn.sgr.zmr.com.sgr.Utils.util.Constant;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class HomeModel extends BaseModel implements HomeInterfaces {
    @Override
    public void getSearchList(Context context, Search search, HttpRequestCallback callback) {
        RequestParams params = new RequestParams();
        params.put("kw" ,search.getKw());
        params.put("page" ,search.getPage());
        params.put("t" ,search.getT());
        params.put("test" ,search.getTest());
        params.put("is_yuyin" ,search.getIs_yuyin());
        params.put("version" ,search.getVersion());
        sendPostRequest(context, Constant.URL_YUN_SEARCH, params, callback);
    }
}

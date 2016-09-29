package cn.sgr.zmr.com.sgr.Modules.Health.Model;

import android.content.Context;

import cn.sgr.zmr.com.sgr.Base.BaseModel;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.http.RequestParams;
import cn.sgr.zmr.com.sgr.Utils.util.Constant;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class HealthModel extends BaseModel implements HealthInterfaces {
    @Override
    public void getSearchList(Context context, String kw, HttpRequestCallback callback) {
        RequestParams params = new RequestParams();
        params.put("kw" ,kw);
        params.put("page" ,"1");
        params.put("t" ,"limit");
        params.put("os" ,"1");
        params.put("is_yuyin" ,"2");
        params.put("version" ,"9.1.5");
        sendPostRequest(context, Constant.URL_YUN_SEARCH, params, callback);
    }
}

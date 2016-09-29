package cn.sgr.zmr.com.sgr.Modules.Health.Model;

import android.content.Context;

import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public interface HealthInterfaces {

    void getSearchList(Context context, String kw, HttpRequestCallback callback);
}

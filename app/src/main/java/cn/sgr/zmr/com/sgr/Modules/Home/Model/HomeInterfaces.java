package cn.sgr.zmr.com.sgr.Modules.Home.Model;

import android.content.Context;

import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public interface HomeInterfaces {

    void getSearchList(Context context, Search search, HttpRequestCallback callback);
}

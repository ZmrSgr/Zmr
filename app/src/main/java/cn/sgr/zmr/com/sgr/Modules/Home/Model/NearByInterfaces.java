package cn.sgr.zmr.com.sgr.Modules.Home.Model;

import android.content.Context;

import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;

/**
 * Created by 沈国荣 on 2016/10/10 0010.
 */
public interface NearByInterfaces {
    void getStoreList(Context context,String lat,String lng,int pageIndex, HttpRequestCallback callback);//获取医院列表
    void getDrugList(Context context,String lat,String lng,int pageIndex, HttpRequestCallback callback);//获取药店列表



}

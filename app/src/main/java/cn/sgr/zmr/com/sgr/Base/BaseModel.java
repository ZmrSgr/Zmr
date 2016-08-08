package cn.sgr.zmr.com.sgr.Base;

import android.content.Context;

import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestUtil;
import cn.sgr.zmr.com.sgr.Utils.http.RequestParams;
import cn.sgr.zmr.com.sgr.Utils.util.Constant;


/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 16:25
 * ------------- Description -------------
 * ---------------------------------------
 */
public abstract class BaseModel

{
    /**
     * 发送http get 请求
     *
     * @param context
     * @param url
     * @param callback
     */
    protected void sendGetRequest(Context context, String url, HttpRequestCallback callback)
    {
        HttpRequestUtil.getInstance().getRequest(context, getAbsUrl(url), callback);
    }

    /**
     * 发送http post 请求
     *
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    protected void sendPostRequest(Context context, String url, RequestParams params, HttpRequestCallback callback)
    {
        HttpRequestUtil.getInstance().postRequest(context, getAbsUrl(url), params == null ? null : params.toParams(), callback);
    }

    private String getAbsUrl(String url)
    {
        return Constant.APIURL + url;
    }
}

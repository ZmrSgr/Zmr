package cn.sgr.zmr.com.sgr.Common.Model.interfaces;

import android.content.Context;

import com.bean.entity.User;

import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;

/**
 * Created by s沈国荣 on 2016/8/19 0019.
 */
public interface CommonInterface {
//   登录接口
    void Login(Context context, User user, HttpRequestCallback callback);
//   注册接口
    void Register(Context context, User user, HttpRequestCallback callback);
}

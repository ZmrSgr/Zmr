package cn.sgr.zmr.com.sgr.Common.Model.imp;

import android.content.Context;

import com.bean.entity.User;

import cn.sgr.zmr.com.sgr.Common.Model.interfaces.CommonInterface;
import cn.sgr.zmr.com.sgr.Utils.GreenDao.DaoCacheManage;
import cn.sgr.zmr.com.sgr.Utils.http.HttpRequestCallback;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class CommonImp implements CommonInterface{
    public CommonImp() {
    }


    //登录操作
    @Override
    public void Login(Context context, User user, HttpRequestCallback callback) {

    }
    // 注册操作
    @Override
    public void Register(Context context, User user, HttpRequestCallback callback) {

    }
    //保存到或者更新本地的数据库
    private  void SaveUser(Context context,User user){
        new DaoCacheManage(context).updateUser(user);

    }

}
